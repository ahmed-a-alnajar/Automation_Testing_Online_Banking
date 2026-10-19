package exercise.automation.base;

import exercise.automation.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver){
        this.driver= driver;
        this.wait= new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
}
