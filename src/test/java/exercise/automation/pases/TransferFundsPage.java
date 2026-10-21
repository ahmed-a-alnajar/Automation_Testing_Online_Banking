package exercise.automation.pases;

import exercise.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferFundsPage extends BasePage {

    private static String fromAccountId;
    private static String toAccountId;
    private Double fromAccountIdBalanceBeforeTransfer;
    private Double toAccountIdBalanceBeforeTransfer;
    private static Double fromAccountIdBalanceAfterTransfer;
    private static Double toAccountIdBalanceAfterTransfer;


    By fromAccountFormBy= By.cssSelector("#fromAccountId");
    By toAccountFormBy= By.cssSelector("#toAccountId");
    By fromAccountFormOptionBy= By.cssSelector("#fromAccountId option");
    By toAccountFormOptionBy= By.cssSelector("#toAccountId option");
    By fromAccountBy= By.cssSelector("#fromAccountId option:nth-child(1)");
    By toAccountBy= By.cssSelector("#toAccountId option:nth-child(2)");
    By amountFieldBy= By.cssSelector("#amount");
    By transferButtonBy= By.cssSelector("#transferForm input[value=\"Transfer\"]");
    By showResultMessageBy= By.cssSelector("#showResult h1");
    By overviewButton= By.cssSelector("#leftPanel ul a[href=\"overview.htm\"]");

    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter the transfer amount")
    public void enterTransferAmount(Double amount){
        driver.findElement(amountFieldBy).sendKeys(amount.toString());
    }

    @Step("Select the sender account id")
    public void selectSenderAccountId(){
        driver.findElement(fromAccountFormBy).click();
        wait.waitForVisibility(fromAccountFormOptionBy);
        driver.findElement(fromAccountBy).click();
        setFromAccountId(driver.findElement(fromAccountBy).getText());
        setFromAccountIdBalanceBeforeTransfer();
    }

    @Step("Select the receiver account id")
    public void selectReceiverAccountId(){
        driver.findElement(toAccountFormBy).click();
        wait.waitForVisibility(toAccountFormOptionBy);
        driver.findElement(toAccountBy).click();
        setToAccountId(driver.findElement(toAccountBy).getText());
        setToAccountIdBalanceBeforeTransfer();
    }

    //Select Accounts Ids
    public TransferFundsPage selectAccounts(){
        selectSenderAccountId();
        selectReceiverAccountId();
        return this;
    }

    @Step("Click on the transfer button")
    public TransferFundsPage clickTransferButton(String transType){
        Double amount= TransAmountBasedType(transType);
        setFromAccountIdBalanceAfterTransfer(amount);
        setToAccountIdBalanceAfterTransfer(amount);
        enterTransferAmount(amount);
        driver.findElement(transferButtonBy).click();

        return this;
    }

    @Step("Check that the transfer success message is displayed")
    public boolean checkTransSuccessMessage(){
        wait.waitForVisibility(showResultMessageBy);
        return driver.findElement(showResultMessageBy).isDisplayed();
    }

    @Step("Navigate to the overview page")
    public OverviewPage backToOverview(){
        wait.waitForVisibility(showResultMessageBy);
        driver.findElement(overviewButton).click();
        return new OverviewPage(driver);
    }

    public Double TransAmountBasedType(String transType){
        return switch (transType) {
            case "Full" -> fromAccountIdBalanceBeforeTransfer;
            case "Partial" -> fromAccountIdBalanceBeforeTransfer-150;
            case "Exceed" -> fromAccountIdBalanceBeforeTransfer+100;
            default -> throw new RuntimeException("the transfer type not found");
        };
    }

    /**
    * General methods
    *** Perform transfer
    *** Verify balance updated
    **/
    public boolean performTransfer(OverviewPage overviewPage,String transType){
        return overviewPage
                .clickTransferFunds()
                .selectAccounts()
                .clickTransferButton(transType)
                .checkTransSuccessMessage();
    }

    public boolean verifyBalanceUpdated(){
        return backToOverview()
                .transAmountDeducted(
                        getFromAccountId(),
                        getFromAccountIdBalanceAfterTransfer(),
                        getToAccountId(),
                        getToAccountIdBalanceAfterTransfer());
    }

    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId;}
    public Double getFromAccountIdBalanceAfterTransfer() { return fromAccountIdBalanceAfterTransfer;}
    public Double getToAccountIdBalanceAfterTransfer() { return toAccountIdBalanceAfterTransfer;}

    public static void setFromAccountId(String accID) { fromAccountId = accID; }
    public static void setToAccountId(String accID) { toAccountId = accID; }

    public void setFromAccountIdBalanceBeforeTransfer() {
        this.fromAccountIdBalanceBeforeTransfer=
                OverviewPage.getAccountBalanceBasedAccountId(fromAccountId);
    }

    public void setToAccountIdBalanceBeforeTransfer() {
         this.toAccountIdBalanceBeforeTransfer =
                 OverviewPage.getAccountBalanceBasedAccountId(toAccountId);
    }


    public void setFromAccountIdBalanceAfterTransfer(Double amount) {
        fromAccountIdBalanceAfterTransfer = fromAccountIdBalanceBeforeTransfer-amount;
    }


    public void setToAccountIdBalanceAfterTransfer(Double amount) {
        toAccountIdBalanceAfterTransfer = toAccountIdBalanceBeforeTransfer+amount;
    }

}
