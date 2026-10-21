package exercise.automation.base;

import exercise.automation.factory.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseTest {

    protected ThreadLocal<WebDriver> driver= new ThreadLocal<>();

//    @BeforeMethod
    @BeforeClass
    public void initDriver() {
        WebDriver driver = new DriverFactory().initWebDriver().getDriver();
        setDriver(driver);
    }

    @AfterMethod
    public void screenshot(ITestResult result){
        String testCaseName= result.getMethod().getMethodName();
        File file = new File("target" + File.separator + "screenshots" + File.separator + testCaseName+ ".PNG");
        takeScreenShot(file);
    }

    @AfterClass
    public void quitDriver(){
        getDriver().quit();
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    private void takeScreenShot(File destfile) {

        File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, destfile);

            InputStream inputStream = new FileInputStream(destfile);
            Allure.addAttachment("screenshot", inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
