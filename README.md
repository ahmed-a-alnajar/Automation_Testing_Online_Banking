# Parabank Online Banking Automation Project

This project contains automated UI tests for the **Parabank Online Banking Application**.  
The framework validates end‑to‑end banking flows such as registration, login, opening new accounts, transferring funds, paying bills, and finding transactions.

---

## Application Under Test

**URL:**  
https://parabank.parasoft.com/parabank/index.htm

---

## Tech Stack

- Java 26
- Selenium WebDriver 4.45.0
- TestNG 7.12.0
- WebDriverManager 6.3.4
- Rest-Assured 6.0.1
- JavaFaker 1.0.2 (used to generate random registration data)
- Allure TestNG 2.35.4
- AspectJ Weaver 1.9.25.1

---

## Project Structure

```
src
 ├── main
 │    └── java
 └── test
      ├── java
      │    └── exercise.automation
      │         ├── base
      │         ├── config
      │         ├── factory
      │         ├── pages
      │         ├── testcases
      │         └── utils
      └── resources

```

### Folder Responsibilities

- **base** → BaseTest, BasePage
- **config** → ConfigUtils, properties
- **factory** → WebDriver initialization
- **pages** → Page Object Model (Login, Overview, Add Account, Transfer Funds, Bill Pay, Find Transactions)
- **testcases** → All TestNG test classes
- **utils** → Waits, helpers

---

## Test Suite (TestNG)

Suite file: `src/test/resources/testng.xml`

Included test classes:

### **1. RegisterTest**
- Register with valid data
- Validate success message

### **2. LoginTest**
- Login with valid credentials
- Login with invalid credentials

### **3. AddNewAccountTest**
- Accounts IDs list matches overview table
- New account success message
- New account appears in accounts overview table

### **4. TransferFundsTest**
- Transfer partial amount
- Transfer full amount
- Transfer exceed amount (should fail)

### **5. BillPayTest**
- Bill payment success message
- Bill amount deducted from account balance

### **6. FindTransactionsTest**
- All transactions displayed
- Transaction description & type (sender/receiver)
- Transaction details displayed correctly

---

## How to Run Tests

Run the TestNG suite using Maven:

### mvn clean test


---

## Browser Selection

Default browser: **Chrome**

Run with Edge:

### mvn clean test -Dbrowser=EDGE


---

## Configuration

Environment and test data values are stored in:

### src/test/java/exercise/automation/config/production.properties


Loaded through `ConfigUtils`.

---

## Screenshots

After each test, a screenshot is saved to:

target/screenshots/


Screenshots are also attached to Allure reports.

---

## Allure Reporting

Generate and view the report:

### allure serve target/allure-results


---

## Notes

- JavaFaker is used to generate dynamic registration data.
- AspectJ Weaver is required for Allure TestNG integration.
- The project follows Page Object Model (POM) with clean separation of concerns.

