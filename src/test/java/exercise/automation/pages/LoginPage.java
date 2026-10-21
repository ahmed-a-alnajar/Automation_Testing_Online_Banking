package exercise.automation.pages;

import exercise.automation.base.BasePage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    By registerButton= By.xpath("//a[text()='Register']");
    By userName= By.cssSelector("[name=\"username\"]");
    By password= By.cssSelector("[name=\"password\"]");
    By loginButton= By.cssSelector("input[value=\"Log In\"]");
    By loginErrorMessage= By.cssSelector("[id=\"rightPanel\"] p");
    By loginFormBy= By.cssSelector("#loginPanel form[name=\"login\"]");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

//    @Step("Open the application URL")
    public LoginPage loadApp(){
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        return this;
    }

    public RegisterPage clickOnRegisterButton(){
        driver.findElement(registerButton).click();
        return new RegisterPage(driver);
    }

    @Step("Add the login credentials and Submit")
    public OverviewPage login(String pass){
        wait.waitForVisibility(loginFormBy);
        driver.findElement(userName).sendKeys(ConfigUtils.getInstance().getUserName());
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();

        return new OverviewPage(driver);
    }

    @Step("Check the error message is shown")
    public String checkTheErrorMessage(){
        wait.waitForVisibility(loginErrorMessage);
        return driver.findElement(loginErrorMessage).getText();
    }

}
