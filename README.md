\# Webhook Solver (Spring Boot)



\## 🧩 Project Overview

This Spring Boot application was developed as part of the \*\*HealthRx Hiring Challenge\*\*.



The app automatically:

1\. Sends a POST request on startup to generate a \*\*webhook URL\*\* and \*\*access token\*\*.

2\. Based on the response, identifies which SQL question (odd/even regNo) applies.

3\. Solves the SQL problem.

4\. Submits the final SQL query back to the webhook using the provided JWT token.



---



\## ⚙️ Tech Stack

\- \*\*Java 17\*\*

\- \*\*Spring Boot 3.1.0\*\*

\- \*\*Maven\*\*

\- \*\*WebClient\*\* (for API calls)

\- \*\*Jackson\*\* (for JSON)

\- \*\*Lombok\*\*



---



\## 🧠 Logic Summary

1\. The application starts automatically — no manual controller calls.

2\. It performs a `POST` request to:

&nbsp;  ```

&nbsp;  https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA

&nbsp;  ```

3\. Reads the `accessToken` and `webhook URL` from the response.

4\. Builds and sends another `POST` to the returned webhook with:

&nbsp;  ```json

&nbsp;  {

&nbsp;    "finalQuery": "SELECT ...your SQL query..."

&nbsp;  }

&nbsp;  ```

5\. Prints the response message to confirm successful submission.



---



\## 🚀 How to Run Locally

\### 1️⃣ Build the JAR

```bash

mvn clean package

```



\### 2️⃣ Run the Application

```bash

java -jar target/webhook-solver-0.0.1-SNAPSHOT.jar

```



You should see:

```

Submission response: {"success":true,"message":"Webhook processed successfully"}

```



---



\## 🧾 Files

| Folder/File | Description |

|--------------|-------------|

| `src/main/java/com/example/webhooksolver` | Main application code |

| `pom.xml` | Maven dependencies |

| `artifacts/` | Contains the final runnable JAR for submission |



---



\## 📦 Downloadable JAR

👉 \[Click here to download the runnable JAR](https://raw.githubusercontent.com/shuklavreddy/webhook-solver/main/artifacts/webhook-solver-0.0.1-SNAPSHOT.jar)



---



\## 👨‍💻 Author

\*\*Shuklav Reddy\*\*   

📘 GitHub: \[@shuklavreddy](https://github.com/shuklavreddy)



