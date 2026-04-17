🚀 Name Gender Classifier API

A RESTful API built with Java (Spring Boot) that classifies a given name by gender using the Genderize API, processes the response, and returns a structured result with confidence evaluation.

📌 Overview

This service exposes a single endpoint:

GET /api/classify?name={name}

It:

Calls the external Genderize API Extracts relevant data Applies custom business logic Returns a clean, structured JSON response ✨ Features 🔍 Gender prediction using external API 📊 Confidence scoring logic ⚡ Fast response time (< 500ms excluding API latency) 🛡️ Robust error handling 🌍 CORS enabled (Access-Control-Allow-Origin: *) 🔁 Handles multiple concurrent requests 🛠️ Tech Stack Language: Java 17 Framework: Spring Boot HTTP Client: RestTemplate / WebClient Build Tool: Maven Deployment: AWS EC2 (or preferred platform) ⚙️ Installation & Setup

Clone Repository git clone https://github.com/OchigboDaniel/HNG14-01.git cd name-classifier
Build Project mvn clean install
Run Application mvn spring-boot:run
App will start on:

http://localhost:8080 🚀 API Endpoint 🔹 Classify Name GET /api/classify?name={name} ✅ Success Response (200) { "status": "success", "data": { "name": "john", "gender": "male", "probability": 0.99, "sample_size": 1234, "is_confident": true, "processed_at": "2026-04-01T12:00:00Z" } } 🧠 Processing Logic Extract: gender probability count → sample_size Compute: is_confident = (probability >= 0.7) AND (sample_size >= 100) Generate: processed_at → current UTC timestamp (ISO 8601 format) ❌ Error Handling 400 Bad Request Missing or empty name { "status": "error", "message": "Name parameter is required" } 422 Unprocessable Entity Invalid data type for name { "status": "error", "message": "Name must be a valid string" } 500 / 502 Server Errors External API failure or server issue { "status": "error", "message": "Unable to process request" } ⚠️ Edge Case (Genderize API)

If:

gender = null OR count = 0

Return:

{ "status": "error", "message": "No prediction available for the provided name" } 🌐 CORS Configuration

CORS is enabled globally:

Access-Control-Allow-Origin: *

This ensures compatibility with external grading systems.

🔌 External API Genderize API https://api.genderize.io/?name={name} 🧪 Testing

You can test using:

Browser Postman Curl

Example:

curl "http://localhost:8080/api/classify?name=john" 🚢 Deployment

This project can be deployed on:

AWS EC2 Railway Heroku Vercel (backend via serverless) Any Java-supported hosting platform 📁 Project Structure src/ ├── controller/ ├── service/ ├── dto/ ├── exception/ └── config/ 📄 License

This project is for educational and assessment purposes.

👤 Author Your Name GitHub: https://github.com/OchigboDaniel/HNG14-01
