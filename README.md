# 📄 Incident Classification System

A Spring Boot-based backend application that processes textual data (or PDF content), extracts meaningful information, and classifies it into predefined topics based on keywords.

---

# 🚀 Features

* Create and manage topics with keywords
* Process large text documents
* Automatically classify text into topics
* Store and retrieve classification results
* Dashboard for analytics (topic distribution, total documents, etc.)

---

# 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Apache PDFBox (for PDF processing)

---

# ⚙️ Setup Instructions

## 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/incident-classification.git
cd incident-classification
```

---

## 2️⃣ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/incident_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 3️⃣ Run Application

```bash
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

# 📡 API Usage

---

## 🟢 1. Create Topics

### 📌 Endpoint

```
POST /api/topics
```

### 📥 Request

```json
[
  {
    "title": "Delhi Bomb Blast",
    "keywords": ["Delhi", "blast", "explosion", "bomb"]
  },
  {
    "title": "Kanpur Bomb Blast",
    "keywords": ["Kanpur", "blast", "explosion", "bomb"]
  }
]
```

### 📤 Response

```json
{
  "success": true,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "title": "Delhi Bomb Blast",
      "keywords": "Delhi,blast,explosion,bomb",
      "keywordList": ["delhi", "blast", "explosion", "bomb"]
    }
  ],
  "timestamp": "2026-04-16T08:27:20.7189376"
}
```

---

## 🟢 2. Get All Topics

### 📌 Endpoint

```
GET /api/topics
```

### 📤 Response

```json
{
  "success": true,
  "data": [...]
}
```

---

## 🟢 3. Get Topic By ID

### 📌 Endpoint

```
GET /api/topics/{id}
```

---

## 🟢 4. Process Document Text

### 📌 Endpoint

```
POST /api/documents/text
```

### 📥 Request

```json
{
  "text": "A massive explosion rocked the heart of Delhi..."
}
```

### 📤 Response

```json
{
  "success": true,
  "data": {
    "id": 1,
    "originalText": "...",
    "createdAt": "2026-04-16T08:29:10.1490907"
  }
}
```

---
### 📌 Endpoint

```
POST /api/documents/file
```
### 📥 Request
```
POST /api/documents
Content-Type: multipart/form-data

Form Data:

Key	Type	Description
file	File	PDF file to upload

```

### 📤 Response
```
{
"success": true,
"message": "Success",
"data": {
"id": 2,
"originalText": "Extracted text from PDF...",
"createdAt": "2026-04-16T08:56:04.4259334"
},
"timestamp": "2026-04-16T08:56:08.1446976"
}
```
---

## 🟢 5. Get Document Classification Result

### 📌 Endpoint

```
GET /api/documents/{id}/results
```

### 📤 Response

```json
{
  "success": true,
  "data": {
    "documentId": 1,
    "results": [
      {
        "assignedTopic": "Delhi Bomb Blast",
        "confidenceScore": 0.5,
        "textChunk": "A massive explosion rocked..."
      }
    ]
  }
}
```

---

## 🟢 6. Dashboard API

### 📌 Endpoint

```
GET /api/dashboard
```

### 📤 Response

```json
{
  "success": true,
  "data": {
    "topicDistribution": {
      "Delhi Bomb Blast": 13,
      "Kanpur Bomb Blast": 3
    },
    "totalChunks": 21,
    "totalDocuments": 1
  }
}
```

---

# 🗄️ Database Schema

## 📌 Topic Table

| Column   | Type   | Description              |
| -------- | ------ | ------------------------ |
| id       | Long   | Primary Key              |
| title    | String | Topic name               |
| keywords | TEXT   | Comma-separated keywords |

---

## 📌 Document Table

| Column       | Type      | Description     |
| ------------ | --------- | --------------- |
| id           | Long      | Primary Key     |
| originalText | TEXT      | Full input text |
| createdAt    | Timestamp | Created time    |

---

## 📌 ClassifiedText Table

| Column          | Type   | Description      |
| --------------- | ------ | ---------------- |
| id              | Long   | Primary Key      |
| textChunk       | TEXT   | Part of document |
| assignedTopic   | String | Classified topic |
| confidenceScore | Double | Matching score   |
| document_id     | Long   | FK to Document   |

---

# 🧠 How It Works

1. Topics are created with keywords
2. Document text is split into chunks
3. Each chunk is matched with topic keywords
4. Best matching topic is assigned with a confidence score
5. Results are stored and retrieved via APIs

---

# ⚠️ Notes

* Keywords are stored as comma-separated strings in DB
* `@Transient` is used to convert keywords into List dynamically
* Basic keyword matching is implemented (can be improved using NLP/ML)

---

# 👨‍💻 Author

**Sumit Kumar**

---

