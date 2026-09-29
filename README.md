# API Sentinel

## AI-Powered API Compatibility Agent with Persistent Memory

API Sentinel is an AI-powered developer tool that helps identify potentially breaking API changes by remembering historical API consumer dependencies.

It uses **Hindsight** as a persistent memory layer so the agent can recall which applications depend on specific API fields before analyzing a proposed API change.

---

## The Problem

API changes can unintentionally break applications that consume existing fields.

For example, consider a Course API:

```text
Course
├── id
├── title
├── description
├── category
└── instructor

An E-Learning application may depend on the description field to display course information.

If a developer proposes:

Remove description from Course API

a normal AI assistant may not know that the E-Learning application depends on that field.

API Sentinel solves this by maintaining persistent dependency memory.

How API Sentinel Works
Developer
    |
    | Proposed API Change
    v
API Sentinel Dashboard
    |
    v
Spring Boot Backend
    |
    | HTTP
    v
Python AI Agent
    |
    +-------------------+
    |                   |
    v                   v
Hindsight            Groq LLM
Memory
    |
    v
Historical Consumer Dependencies
    |
    v
Compatibility Analysis

The agent:

Receives a proposed API change.
Identifies the API field involved.
Queries Hindsight for historical consumer dependencies.
Determines whether known consumers may be affected.
Sends the relevant memory and compatibility context to the LLM.
Generates a developer-focused compatibility analysis.
Stores the resulting analysis back into Hindsight.
Real-World Use Case

API Sentinel is demonstrated using a real E-Learning application.

The E-Learning application consumes course information and uses the description field when displaying course details.

The application registers this dependency with API Sentinel:

Application: E-Learning App
API: Course API
Field: description

API Sentinel stores this dependency in Hindsight.

Later, a developer proposes:

Remove description from Course API

API Sentinel recalls the historical dependency:

E-Learning App depends on description

and reports:

POTENTIALLY_BREAKING

The agent then explains the compatibility impact and provides migration guidance.

Why Hindsight Matters

Hindsight is the memory layer of API Sentinel.

Without persistent memory, the agent would only analyze the API change based on the information provided in the current request.

With Hindsight, API Sentinel can recall previously registered dependencies and previous compatibility analyses.

For example:

Interaction 1

E-Learning App
    |
    | depends on
    v
Course API → description

        |
        v
    Hindsight

Persistent Memory

Later:

Developer:

"Remove description from Course API"

The agent recalls:

E-Learning App depends on description

and uses that historical knowledge during its compatibility analysis.

Key Idea

The API change didn't change. The agent's knowledge changed.

Current Hindsight Memory Flow
1. Remember

The E-Learning application sends its dependency to:

POST /api/ai/remember

Example:

{
  "application": "E-Learning App",
  "api": "Course API",
  "field": "description"
}

Spring Boot forwards the dependency to the Python agent.

The Python agent stores it in Hindsight using the context:

api-consumer-dependency
2. Recall

When analyzing an API change, the Python agent queries Hindsight:

Which API consumers depend on the description field?

The returned memories are filtered for relevant dependencies.

3. Analyze

The relevant Hindsight memories are passed to the Groq LLM together with the proposed API change.

The LLM produces:

Compatibility assessment
Affected consumers
Why the change may cause problems
Recommended migration action
4. Remember the Analysis

The resulting compatibility analysis is also retained in Hindsight using:

api-compatibility-analysis

This allows the system to maintain historical compatibility knowledge.

Architecture
                    API Sentinel UI
                         |
                         | HTTP
                         v
              Spring Boot Backend
                    Port 8080
                         |
              +----------+----------+
              |                     |
              v                     v
        MySQL Database       Python AI Agent
                                  Port 5000
                                    |
                          +---------+---------+
                          |                   |
                          v                   v
                     Hindsight            Groq LLM
                       Memory
Technology Stack
Backend
Java
Spring Boot
Spring Web
Spring Data JPA
Maven
AI Agent
Python
Flask
Hindsight Client
Groq
Database
MySQL
Frontend
HTML5
CSS3
JavaScript
Fetch API
External Services
Hindsight Cloud
Groq
Project Structure
api-compatibility-agent/
│
├── README.md
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/api_compatibility_agent/
│       │       ├── controller/
│       │       │   ├── AiAnalysisController.java
│       │       │   ├── ApiEndpointController.java
│       │       │   ├── ApiChangeController.java
│       │       │   └── CompatibilityController.java
│       │       │
│       │       ├── model/
│       │       │   ├── ApiEndpoint.java
│       │       │   ├── ApiChange.java
│       │       │   └── CompatibilityResult.java
│       │       │
│       │       ├── repository/
│       │       │   ├── ApiEndpointRepository.java
│       │       │   └── ApiChangeRepository.java
│       │       │
│       │       ├── service/
│       │       │   ├── ApiEndpointService.java
│       │       │   ├── ApiChangeService.java
│       │       │   ├── CompatibilityService.java
│       │       │   └── PythonAgentService.java
│       │       │
│       │       └── ApiCompatibilityAgentApplication.java
│       │
│       └── resources/
│           ├── application.properties
│           └── static/
│               └── index.html
│
├── ai-agent/
│   ├── agent.py
│   ├── api.py
│   ├── requirements.txt
│   ├── .env
│   └── venv/
│
└── target/
API Endpoints
Register API Dependency
POST /api/ai/remember

Example:

{
  "application": "E-Learning App",
  "api": "Course API",
  "field": "description"
}

Example response:

{
  "message": "Dependency remembered successfully",
  "api_name": "Course API",
  "consumer": "E-Learning App",
  "field": "description"
}
Analyze API Change
POST /api/ai/analyze

Example:

{
  "change": "Remove description from Course API"
}

Example result:

{
  "change": "Remove description from Course API",
  "status": "POTENTIALLY_BREAKING",
  "reason": "..."
}
Example Demonstration
Step 1 — Register the dependency

The E-Learning application reports:

E-Learning App
      |
      v
Course API
      |
      v
description

API Sentinel stores this relationship in Hindsight.

Step 2 — Propose an API change

The developer enters:

Remove description from Course API
Step 3 — Hindsight Recall

API Sentinel queries its persistent memory and recalls:

E-Learning App depends on description
Step 4 — AI Analysis

The agent generates:

Compatibility Status:
POTENTIALLY_BREAKING

Affected Consumer:
E-Learning App

It then explains the potential impact and recommends migration actions.

Running the Project
Prerequisites

Install:

Java 21
MySQL 8+
Python 3.12+
Eclipse or another Java IDE
Git

You also need:

Hindsight Cloud account/API key
Groq API key
1. Configure MySQL

Create the database:

CREATE DATABASE api_compatibility_db;

Configure your Spring Boot database connection in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/api_compatibility_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
2. Configure Environment Variables

Create:

ai-agent/.env

with:

HINDSIGHT_API_KEY=YOUR_HINDSIGHT_API_KEY
HINDSIGHT_BASE_URL=https://api.hindsight.vectorize.io
HINDSIGHT_BANK_ID=api-compatibility-agent
GROQ_API_KEY=YOUR_GROQ_API_KEY

Never commit .env or API keys to GitHub.

3. Install Python Dependencies

Navigate to:

ai-agent/

Create and activate the virtual environment:

Windows
python -m venv venv
venv\Scripts\activate

Install dependencies:

pip install -r requirements.txt
4. Start the Python AI Agent

From:

ai-agent/

run:

python api.py

The Flask agent runs on:

http://localhost:5000
5. Start the Spring Boot Backend

From the project root:

api-compatibility-agent/

run:

mvnw.cmd spring-boot:run

The Spring Boot backend runs on:

http://localhost:8080
6. Open API Sentinel

Open:

http://localhost:8080/

Enter:

Remove description from Course API

Click:

Analyze Compatibility

The agent retrieves relevant Hindsight memories and generates the compatibility analysis.

E-Learning Integration

The project includes a real E-Learning application integration.

The E-Learning application uses:

ApiSentinelClient

to register its dependency with API Sentinel.

The integration communicates with:

POST http://localhost:8080/api/ai/remember

The application registers:

E-Learning App
Course API
description

API Sentinel then stores this information in Hindsight.

Important Implementation Detail

The current prototype explicitly registers the known dependency from the E-Learning application.

It does not automatically scan arbitrary application source code to discover dependencies.

Future versions can automate dependency discovery using:

OpenAPI specifications
API gateway logs
Runtime instrumentation
Static code analysis
CI/CD integration
Security

API credentials are stored in:

ai-agent/.env

The repository .gitignore excludes:

ai-agent/.env
ai-agent/venv/
ai-agent/__pycache__/

API keys should never be committed to source control.

Future Improvements

API Sentinel can be extended with:

Automatic OpenAPI contract comparison
Automatic breaking-change detection
GitHub/GitLab CI integration
Pull request compatibility checks
API version history
Automatic consumer discovery
Dependency graphs
Migration plan generation
Compatibility analysis history
Slack/Teams notifications
Runtime API usage analysis
Multiple API consumers and organizations

A future CI/CD workflow could look like:

Developer creates API change
          |
          v
Pull Request
          |
          v
API Sentinel
          |
          v
Hindsight recalls consumer dependencies
          |
          v
Compatibility analysis
          |
          v
CI/CD compatibility check
          |
          v
Developer receives migration guidance
Why This Project Uses Memory

Traditional API compatibility tools can detect structural differences between API versions.

API Sentinel adds historical context.

Instead of only asking:

"What changed?"

the agent can also ask:

"Who depended on this before?"

That historical knowledge is stored and recalled using Hindsight.

This allows the compatibility analysis to incorporate information accumulated over time.

Demo Scenario

A developer proposes:

Remove description from Course API

API Sentinel recalls:

E-Learning App depends on description

The agent responds:

POTENTIALLY_BREAKING

and explains the affected consumer and migration considerations.

This demonstrates how persistent memory can improve an AI agent's understanding of API compatibility.

Hackathon

This project was built for the AI Agents That Learn Using Hindsight hackathon.

The project uses Hindsight as its persistent memory layer for storing and recalling API consumer dependencies and historical compatibility analyses.

License

This project is created as a hackathon project.


**Paste all of that into `README.md`, save with `Ctrl + S`, and don't make any other changes yet.**