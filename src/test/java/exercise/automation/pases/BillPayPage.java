package exercise.automation.pases;

import com.github.javafaker.Faker;
import exercise.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage extends BasePage {

    private static String accountId;
    private static Double accountBalance;

    By billFormBy= By.cssSelector(".form2");
    By payeeNameBy= By.cssSelector(".form2 [name=\"payee.name\"]");
    By addressBy= By.cssSelector(".form2 [name=\"payee.address.street\"]");
    By cityBy= By.cssSelector(".form2 [name=\"payee.address.city\"]");
    By stateBy= By.cssSelector(".form2 [name=\"payee.address.state\"]");
    By zCodeBy= By.cssSelector(".form2 [name=\"payee.address.zipCode\"]");
    By phoneNumberBy= By.cssSelector(".form2 [name=\"payee.phoneNumber\"]");
    By accountNumberBy= By.cssSelector(".form2 [name=\"payee.accountNumber\"]");
    By verifyAccountBy= By.cssSelector(".form2 [name=\"verifyAccount\"]");
    By amountBy= By.cssSelector(".form2 [name=\"amount\"]");
    By fromAccountListBy= By.cssSelector(".form2 [name=\"fromAccountId\"]");
    By fromAccountOptionBy= By.cssSelector(".form2 [name=\"fromAccountId\"] option:first-child");
    By sendPaymentBy= By.cssSelector(".form2 [value=\"Send Payment\"]");
    By paymentResultBy= By.cssSelector("#billpayResult");
    By paymentResultMessageBy= By.cssSelector("#billpayResult .title");
    By overviewButton= By.cssSelector("#leftPanel ul a[href=\"overview.htm\"]");

    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter the bill pay information")
    public BillPayPage enterBillData(){
        wait.waitForVisibility(billFormBy);

        driver.findElement(payeeNameBy).sendKeys("STC Comp");
        driver.findElement(addressBy).sendKeys("Saad St");
        driver.findElement(cityBy).sendKeys("CTA");
        driver.findElement(stateBy).sendKeys("Stat");
        driver.findElement(zCodeBy).sendKeys("45662");
        driver.findElement(phoneNumberBy).sendKeys("8546215479");
        driver.findElement(accountNumberBy).sendKeys("56452321");
        driver.findElement(verifyAccountBy).sendKeys("56452321");
        driver.findElement(amountBy).sendKeys("50");
        selectFromAccountId();

        return this;
    }

    @Step("Select the account id")
    public void selectFromAccountId(){
        driver.findElement(fromAccountListBy).click();
        driver.findElement(fromAccountOptionBy).click();

        setAccountId(driver.findElement(fromAccountOptionBy).getText());
        setAccountBalance();
    }

    @Step("Click on the send payment button")
    public BillPayPage clickSentPayment(){
        driver.findElement(sendPaymentBy).click();
        return this;
    }

  @Step("the completed message should be displayed")
    public String checkPaymentResultMes(){
        wait.waitForVisibility(paymentResultBy);
        return driver.findElement(paymentResultMessageBy).getText();
    }

    @Step("Navigate to the overview page")
    public OverviewPage backToOverview(){
        wait.waitForVisibility(paymentResultBy);
        driver.findElement(overviewButton).click();
        return new OverviewPage(driver);
    }

//    @Step("Check that the account balance deducted")
    public boolean verifyBalanceUpdated(){
        return backToOverview()
                .billAmountDeducted(
                        getAccountId(),
                        getAccountBalance()-50
                );
    }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public Double getAccountBalance() { return accountBalance; }

    public void setAccountBalance() {
        this.accountBalance = OverviewPage.getAccountBalanceBasedAccountId(getAccountId());
    }



}
