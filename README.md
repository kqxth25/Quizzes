# 🎓 Trivial Quiz — CSC207 Course Project

<img width="931" height="555" alt="image" src="https://github.com/user-attachments/assets/2a17a5aa-63b0-4835-8aa6-5dc0ff581285" />

#### Trivial Quiz is a Java desktop application built following the principles of **Clean Architecture**.  

#### The system allows users to log in, select quizzes, answer questions, and view results.

#### Creators can retrieve quizzes from a public API (OpenTDB) and load them into the system.

---

## 🚀 Features

### 🧑‍💻 User Features

- User Login / Signup
- Browse available quizzes  
- Answer quizzes with previous/next navigation  
- Progress tracking  
- Submit quiz & view results  
- (Optional) View quiz history  

---

### 🧑‍🏫 Creator Features

- Creator Login  
- Retrieve quiz data from an external API  
- Convert API results into Quiz & Question entities  
- Display imported quizzes in the UI  

---

## 👀 Demo

If you click on **"I am a User"** on HomePage:

---

### 🧑‍💻 User Signup and Login

<p float="left">
  <img src="https://github.com/user-attachments/assets/c2c25995-9c26-4442-8f88-d97abedb0219" height="380px" style="margin-right:10px;" />
  <img src="https://github.com/user-attachments/assets/082b0540-c85c-4595-8a6e-5966803a76e4" height="380px" />
</p>

---

### 📚 Select Quiz to Start

<img src="https://github.com/user-attachments/assets/86ee3a8c-48c0-41a6-8787-7049558e2c5a" width="75%" />

---

### 📝 Answering the Quiz

<p float="left">
  <img src="https://github.com/user-attachments/assets/27100414-d197-4511-8c89-be568a16aeb6" height="380px" style="margin-right:10px;" />
  <img src="https://github.com/user-attachments/assets/adb96b9c-d694-435a-bf53-211517ea02a6" height="380px" />
</p>

---

### 📊 Submit and Get Your Result

<img src="https://github.com/user-attachments/assets/1d381cde-c162-4f74-a182-72d2a8b0a2a4" width="85%" />

---

### 🔍 View the Detail and Share It

<p float="left">
  <img src="https://github.com/user-attachments/assets/6036f008-ed22-44c2-9e7c-451f14ac6ee2" height="380px" style="margin-right:10px;" />
  <img src="https://github.com/user-attachments/assets/ac07951a-2099-4199-8043-be91462b90ed" height="380px" />
</p>

---

If you click on **"I am a Creator"** on HomePage:

---

### 🔑 Creator Login  
*(The password for creator is `kfc` BTW)*

<img src="https://github.com/user-attachments/assets/1c579a29-969f-4d0b-b6da-ab8c6ab094c8" width="70%" />

---

### 🧰 Creator Managing the Quiz

<p float="left">
  <img src="https://github.com/user-attachments/assets/6f8abc83-d1d0-4df7-9ef9-e083bcaeb907" height="330px" style="margin-right:10px;" />
  <img src="https://github.com/user-attachments/assets/a2e5d194-a218-4e47-a05d-84a8baba7106" height="330px" />
</p>

<p float="left" style="margin-top:20px;">
  <img src="https://github.com/user-attachments/assets/42374dce-d2b5-4e48-96e9-1e9ee00ad54b" height="330px" style="margin-right:10px;" />
  <img src="https://github.com/user-attachments/assets/cb46721e-bb93-401a-aa73-1e97d7a81fcf" height="330px" />
</p>

---


## 🌐 Public Quiz API (External Data Source)

This project retrieves quiz data dynamically from the:
### 🔗 **Open Trivia Database (OpenTDB)**  https://opentdb.com/api_config.php  

No API key is required.

**Import Flow:**

1. Creator selects **Import Quiz**  
2. System sends an HTTP request to OpenTDB  
3. JSON results are parsed  
4. Quiz & Question entities are created  
5. Quiz is stored in the repository and shown in the UI  

---

## 🧱 Architecture (Clean Architecture)

### 🧠 This project strictly adopts a layered design:

<img width="298" height="322" alt="image" src="https://github.com/user-attachments/assets/18d09aab-a102-4098-bfc3-5cf6a4b16eb2" />

### ⭐ Benefits of Clean Architecture

- UI is independent from business logic  
- Data sources are independent and can be swapped (API ↔ local CSV files)  
- Use Cases are pure and framework-agnostic  
- High modularity & testability  


---

## 🛠 Running the Application

### ▶ Run with IntelliJ IDEA (Recommended)

1. Clone the repository  
2. Open the project in IntelliJ IDEA  
3. Navigate to:
``` bash
src/main/java/app/Main.java
```
4. Right–click the file → **Run 'Main.main()'**  
5. The GUI application will start

---

## 🧪 Testing

Use Case Interactors prepared for unit testing

Mockito can be used to mock data access and presenter layers

Tests belong in:

```bash
src/test/java
```

---

## 👥 Team Members


| Name       | Role                           |
| ---------- | ------------------------------ |
| Johnny Mao | (Usecase 1) User Login and Selectquiz     |
|  Ivan Wen  |   (Usecase 2) Answer Quiz              |
| Kevin Qiu   |(Usecase 3) Submit and View result         |
| Aiden Choo  |(Usecase 4)  Manage Quiz   |
| Tomas Bartholo Souto |  (Usecase 5) Creator Login and User veiw History|

