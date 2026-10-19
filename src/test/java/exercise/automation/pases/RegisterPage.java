package exercise.automation.pases;

import com.github.javafaker.Faker;
import exercise.automation.base.BasePage;
import exercise.automation.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    //generate fake data
    Faker faker= new Faker();
    private String pass;
    private String loginUserName;

    By registerFormLoaded= By.cssSelector("[id=\"rightPanel\"]");
    By firstName= By.cssSelector("[id=\"customer.firstName\"]");
    By lastName= By.cssSelector("[id=\"customer.lastName\"]");
    By addressStreet= By.cssSelector("[id=\"customer.address.street\"]");
    By addressCity= By.cssSelector("[id=\"customer.address.city\"]");
    By addressState= By.cssSelector("[id=\"customer.address.state\"]");
    By addressZipCode= By.cssSelector("[id=\"customer.address.zipCode\"]");
    By phoneNumber= By.cssSelector("[id=\"customer.phoneNumber\"]");
    By ssn= By.cssSelector("[id=\"customer.ssn\"]");
    By userName= By.cssSelector("[id=\"customer.username\"]");
    By password= By.cssSelector("[id=\"customer.password\"]");
    By confirmPassword= By.cssSelector("[id=\"repeatedPassword\"]");
    By submitRegisterButton= By.cssSelector("input[value='Register']");
    By successRegistration= By.cssSelector("[id=\"rightPanel\"] p");


    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage fillRegisterForm(){
        wait.waitForVisibility(registerFormLoaded);

        driver.findElement(firstName).sendKeys(faker.name().firstName());
        driver.findElement(lastName).sendKeys(faker.name().lastName());
        driver.findElement(addressStreet).sendKeys(faker.address().streetAddress());
        driver.findElement(addressCity).sendKeys(faker.address().cityName());
        driver.findElement(addressState).sendKeys(faker.address().state());
        driver.findElement(addressZipCode).sendKeys(faker.address().zipCode());
        driver.findElement(phoneNumber).sendKeys(faker.phoneNumber().phoneNumber());
        driver.findElement(ssn).sendKeys(faker.idNumber().ssnValid());

        enterUserName();
        enterPassword();

        return this;
    }

    public void enterUserName(){
        setUserName();
        driver.findElement(userName).sendKeys(getUserName());
    }

    public void enterPassword(){
        setPass();
        driver.findElement(password).sendKeys(getPass());
        driver.findElement(confirmPassword).sendKeys(getPass());
    }
    public RegisterPage submitRegisterForm(){
        driver.findElement(submitRegisterButton).click();
        wait.waitForVisibility(successRegistration);
        ConfigUtils.getInstance().setUserName(getUserName());
        ConfigUtils.getInstance().setPassword(getPass());
        return this;
    }

    public String checkSuccessRegistration(){
        return driver.findElement(successRegistration).getText();
    }

    public String getPass() {
        return pass;
    }

    public void setPass() {
        this.pass = faker.internet().password();
    }

    public String getUserName() {
        return loginUserName;
    }

    public void setUserName() {
        this.loginUserName = faker.name().username();
    }
}
