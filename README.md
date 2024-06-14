# Backend implemetation for speech recognition app

To implement a backend part of the app I used microservice architecture

<img src="https://github.com/SpeechReco/AnalyzerService/assets/63159095/78e0b4ca-ed57-430a-a9a6-9ee5c47f6b74" height="450">

## Services implementation
- The code for main API gateway is inside current repo
- Frontend repo + project demo: https://github.com/SpeechReco/SpeechRecFE
- STT Service repo: https://github.com/SpeechReco/SttService
- ChatGPT service repo: https://github.com/SpeechReco/GPTService
- To implement communication between services, I used RabbitMQ queueing service
- For the data storage, all the user info, audio info and analysis info is stored in PostgreSQL
- For the actual audio storage and audio translation files, I've used firebase storage and stored the links to them inside Postgre DB

