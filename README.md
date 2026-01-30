# Online BookStore Automation Framework

This project is a proof-of-concept automation framework built with Java and RestAssured. It is designed for automated testing of a bookstore API or web service.

## Features

- Automated API tests using Java and RestAssured
- Maven build system for easy dependency management
- CI/CD integration with GitHub Actions
- Allure reporting for test results

## Prerequisites

- Java 21 
- Maven 3.6+
- Git
- TestNG
- RestAssured
- Github Actions
- Allure Reporting

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Alexandr220v/onlinebookstore-api-automation.git
```

### Running Tests via GitHub Actions

Automated tests are configured to run in a GitHub Actions workflow. You can trigger the workflow manually from the "Actions" tab in your repository.

1. You can trigger the workflow manually or view previous runs from the ["Run tests"](https://github.com/Alexandr220v/onlinebookstore-api-automation/blob/master/.github/workflows/ci.yml) in the Actions tab.
2. Wait for [Report deploy](https://github.com/Alexandr220v/onlinebookstore-api-automation/actions/workflows/pages/pages-build-deployment/) job is completed (deployment of Allure report to GitHub Pages)
3. [Open latest Allure report](https://Alexandr220v.github.io/onlinebookstore-api-automation/)
    https://github.com/Alexandr220v/onlinebookstore-api-automation

### Running Tests Locally

1. **Install dependencies and run tests:**

   ```bash
   mvn clean test -Dsuite=regression
   ```

2. **View Allure Reports (optional):**

   You can generate and open the test report:

   ```bash
   mvn allure:report
   ```
3. **Open report file**

    - Open `target/site/allure-maven-plugin/index.html` in browser
