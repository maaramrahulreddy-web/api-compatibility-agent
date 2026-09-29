from dotenv import load_dotenv
import os
import re

from hindsight_client import Hindsight
from groq import Groq


load_dotenv()

BANK_ID = os.getenv("HINDSIGHT_BANK_ID")


def create_hindsight_client():
    return Hindsight(
        base_url=os.getenv("HINDSIGHT_BASE_URL"),
        api_key=os.getenv("HINDSIGHT_API_KEY")
    )


def create_groq_client():
    return Groq(
        api_key=os.getenv("GROQ_API_KEY")
    )


def remember_dependency(api_name, consumer, field):

    client = create_hindsight_client()

    try:
        client.retain(
            bank_id=BANK_ID,
            content=(
                f"API: {api_name}. "
                f"Consumer: {consumer}. "
                f"Dependency: {consumer} depends on the {field} field."
            ),
            context="api-consumer-dependency"
        )

    finally:
        client.close()


def analyze_change(change):

    hindsight_client = create_hindsight_client()

    try:

        match = re.search(
            r"remove\s+([A-Za-z0-9_]+)",
            change,
            re.IGNORECASE
        )

        if not match:
            return (
                "UNKNOWN",
                "Could not identify the API field being removed."
            )

        field = match.group(1)

        result = hindsight_client.recall(
            bank_id=BANK_ID,
            query=(
                f"Which API consumers depend on the {field} field? "
                f"Return only direct consumer dependencies."
            )
        )

        matching_memories = []

        for memory in result.results:

            text = memory.text.lower()
            field_lower = field.lower()

            if (
                field_lower in text
                and (
                    "depends on" in text
                    or "dependency on" in text
                    or "relies on" in text
                    or "rely on" in text
                )
            ):
                matching_memories.append(memory.text)

        matching_memories = list(dict.fromkeys(matching_memories))

        if matching_memories:

            status = "POTENTIALLY_BREAKING"

            memory_text = "\n".join(
                f"- {memory}"
                for memory in matching_memories
            )

        else:

            status = "NO_KNOWN_IMPACT"

            memory_text = "No known consumer dependency was found."

        groq_client = create_groq_client()

        prompt = f"""
You are an API compatibility analysis assistant.

Proposed API change:
{change}

Field being changed:
{field}

Compatibility status:
{status}

Relevant historical memories from Hindsight:
{memory_text}

Provide a concise developer-focused explanation.

Include:
1. Compatibility assessment
2. Affected consumers
3. Why the change may cause problems
4. A recommended migration action

Do not invent consumers or dependencies that are not present in the Hindsight memories.
"""

        response = groq_client.chat.completions.create(
            model="openai/gpt-oss-20b",
            messages=[
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            temperature=0.2
        )

        ai_reason = response.choices[0].message.content

        hindsight_client.retain(
            bank_id=BANK_ID,
            content=(
                f"API compatibility analysis: "
                f"Proposed change: {change}. "
                f"Field: {field}. "
                f"Result: {status}. "
                f"AI analysis: {ai_reason}"
            ),
            context="api-compatibility-analysis"
        )

        return status, ai_reason

    finally:

        hindsight_client.close()