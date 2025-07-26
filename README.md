<img src="assets/icon.png" alt="Project Icon" width="23" height="23"/> **Slotegrator Test Automation**

This project automates player api running Gradle-based Java tests inside a Docker container and generates an Allure report for inspection.

🔧 Prerequisites
- Docker installed and running
- Allure CLI installed globally
- Unix-compatible shell (for run.sh)

- Docker 
    - [for windows](https://docs.docker.com/desktop/setup/install/windows-install/)
    - [for ubuntu](https://docs.docker.com/engine/install/ubuntu/)
- Java 22.0
- Allure CLI
    - [windows](https://allurereport.org/docs/install-for-windows/)
    - [linux](https://allurereport.org/docs/install-for-linux/) 

🚀 Local run:

```./gradlew clean test -DLOG_LEVEL="${LOG_LEVEL}" -DTEST_EMAIL="${YOUR_EMAIL}" -DTEST_PASSWORD="${PASSWORD}"```

📁 To see report:

```allure serve ./build/allure-results```


🐳 Docker run:

1. start docker engine
2. inside project root folder

```chmod +X run.sh```

```sh run.sh ${log_level} ${email} ${password}```

🧪 Example:

```sh run.sh info email@email.com password```


⚠️ Some discrepancies with api specifications and task:

1. request to `/api/tester/login` - returns 201, instead of 200
2. request to `/api/automationTask/getOne` returns 201 instead of 200
3. response of `/api/automationTask/getOne` uses id instead _id like PlayerDTO
4. request to `/api/automationTask/deleteOne/{id}` could fail with empty response body, see @Test deleteAllPlayers()