package exercise.automation.pages;

import exercise.automation.base.BasePage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewPage extends BasePage {

    private static List<String> accountsNumbers;
    private static Map<String,Double> accountsInfo= new HashMap<>();

    By accountsTable= By.cssSelector("table[id=\"accountTable\"] tbody tr");
    By accountNumberColumn= By.cssSelector("td:nth-child(1)");
    By accountBalanceColumn= By.cssSelector("td:nth-child(2)");
    By addNewAccountButton= By.cssSelector("#leftPanel ul a[href=\"openaccount.htm\"]");
    By transferFundsButton= By.cssSelector("#leftPanel ul a[href=\"transfer.htm\"]");
    By billPayButton= By.cssSelector("#leftPanel ul a[href=\"billpay.htm\"]");
    By findTransButton= By.cssSelector("#leftPanel ul a[href=\"findtrans.htm\"]");

    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that the user logged-in successfully")
    public boolean checkAccountsTableDisplayed(){
        wait.waitForVisibility(accountsTable);
        return driver.findElement(accountsTable).isDisplayed();
    }

    public boolean accountsTableContainsData(){
        wait.waitForVisibility(accountsTable);
        List<WebElement> accounts= driver.findElements(accountsTable);
        return !accounts.isEmpty();
    }

    @Step("Click on the add new account button")
    public AddNewAccountPage clickOnAddNewAccount(){
        setAccountsInfo();
        setAccountsNumbers();
        driver.findElement(addNewAccountButton).click();
        return new AddNewAccountPage(driver);
    }

    @Step("Click on the transfer funds button")
    public TransferFundsPage clickTransferFunds(){
        setAccountsInfo();
        if(accountsInfo.size() <=1){
            throw new RuntimeException("there is only one account available");
        }
        driver.findElement(transferFundsButton).click();
        return new TransferFundsPage(driver);
    }

    @Step("Click on the bill pay button")
    public BillPayPage clickBillPayButon(){
        setAccountsInfo();
        driver.findElement(billPayButton).click();
        return new BillPayPage(driver);
    }

    @Step("Click on the find transactions button")
    public FindTransactionsPage clickFindTransButon(){
        driver.findElement(findTransButton).click();
        return new FindTransactionsPage(driver);
    }


    @Step("Check that the new Accounts is added to the accounts overview table")
    public boolean newAccountAddedToTable(String newAccountId){
        wait.waitForVisibility(accountsTable);
        List<WebElement> accounts= driver.findElements(accountsTable);
        return accounts
                .stream()
                .filter(x-> !x.getText().contains("Total"))
                .map(x-> x.findElement(accountNumberColumn).getText().trim())
                .anyMatch(x-> x.equals(newAccountId.trim()));
    }

    public boolean transAmountDeducted(String fromAcc, Double fromAmount, String toAcc, Double toAmount){
        wait.waitForVisibility(accountsTable);
        setAccountsInfo();
        return accountsInfo.get(fromAcc).equals(fromAmount) &&
                accountsInfo.get(toAcc).equals(toAmount);
    }

    @Step("Check that the account balance deducted")
    public boolean billAmountDeducted(String accountId, Double newBalance){
        wait.waitForVisibility(accountsTable);
        setAccountsInfo();
        return accountsInfo.get(accountId).equals(newBalance);
    }

    public static List<String> getAccountsNumbers() {
        return accountsNumbers;
    }

    public static Double getAccountBalanceBasedAccountId(String accountId){
        return accountsInfo.get(accountId);
    }

    //Set account numbers in a list to check that it's displayed correctly while adding new account
    public void setAccountsNumbers(){
        wait.waitForVisibility(accountsTable);
        accountsNumbers= new ArrayList<>(accountsInfo.keySet());
    }

    public void setAccountsInfo(){
        wait.waitForVisibility(accountsTable);
        List<WebElement> accounts= driver.findElements(accountsTable);
        for(WebElement account: accounts){
            //Skip the total row
            if(account.getText().contains("Total")){continue;}
            String accountNumber= account.findElement(accountNumberColumn).getText().trim();
            String accountBalance= account.findElement(accountBalanceColumn)
                    .getText()
                    .trim()
                    .replace("$","");

            accountsInfo.put(accountNumber,Double.parseDouble(accountBalance));
        }
    }


    public boolean checkAccountsDetails(){
        wait.waitForVisibility(accountsTable);
        Map<String, String> expectedAccounts= ConfigUtils.getInstance().getAccountsData();
        List<WebElement> accounts= driver.findElements(accountsTable);
        for(WebElement account: accounts){
            //Skip the total row
            if(account.getText().contains("Total")){continue;}

            String accountNumber= account.findElement(accountNumberColumn).getText().trim();
            String accountBalance= account.findElement(accountBalanceColumn)
                    .getText()
                    .trim()
                    .replace("$","");
            if(!expectedAccounts.containsKey(accountNumber)){
                return false;
            }

            if(!expectedAccounts.get(accountNumber).equals(accountBalance)){
                return false;
            }
        }
        return true;
    }
}
