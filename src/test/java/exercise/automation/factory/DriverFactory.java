package exercise.automation.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private WebDriver driver;

    public DriverFactory initWebDriver() {
        String browser = System.getProperty("browser", "CHROME");

        switch (browser){
            case "CHROME":
               // WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("-incognito");
//                chromeOptions.addArguments("--auto-open-devtools-for-tabs"); //Network inspect
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().maximize();
                break;
            case "FIREFOX":
                //WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fireOptions = new FirefoxOptions();
                fireOptions.addArguments("-incognito");
                driver = new FirefoxDriver(fireOptions);
                driver.manage().window().maximize();
                break;
            default:
                throw new RuntimeException("there is an issue in the driver initiation");
        }

        return this;
    }

    public WebDriver getDriver(){
        return driver;
    }
}
