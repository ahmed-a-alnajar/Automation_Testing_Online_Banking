package exercise.automation.base;

import exercise.automation.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected ThreadLocal<WebDriver> driver= new ThreadLocal<>();

//    @BeforeMethod
    @BeforeClass
    public void initDriver() {
        WebDriver driver = new DriverFactory().initWebDriver().getDriver();
        setDriver(driver);
    }

//    @AfterMethod
    @AfterClass
    public void quitDriver(){
//        getDriver().quit();
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }
}
