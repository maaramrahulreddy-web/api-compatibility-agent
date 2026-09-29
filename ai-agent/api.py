from flask import Flask, request, jsonify
from agent import analyze_change, remember_dependency

app = Flask(__name__)


@app.post("/analyze")
def analyze():
    data = request.get_json()

    change = data.get("change")

    if not change:
        return jsonify({
            "error": "change is required"
        }), 400

    status, reason = analyze_change(change)

    return jsonify({
        "change": change,
        "status": status,
        "reason": reason
    })


@app.post("/remember")
def remember():
    data = request.get_json()

    api_name = data.get("api_name")
    consumer = data.get("consumer")
    field = data.get("field")

    if not api_name or not consumer or not field:
        return jsonify({
            "error": "api_name, consumer and field are required"
        }), 400

    remember_dependency(api_name, consumer, field)

    return jsonify({
        "message": "Dependency remembered successfully",
        "api_name": api_name,
        "consumer": consumer,
        "field": field
    })


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)