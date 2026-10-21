package exercise.automation.pages;

import exercise.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddNewAccountPage extends BasePage {

    private List<String> actualAccountsIds;
    private String newAccountId;

    By newAccountFormLoaded= By.cssSelector(" .title");
    By accountTypeForm= By.cssSelector("#openAccountForm form #type");
    By selectedAccountType= By.cssSelector("form #type option[value=\"1\"]");
    By accountIDForm= By.cssSelector("#openAccountForm form #fromAccountId option");
    By selectedAccountID= By.cssSelector("form #fromAccountId option:nth-child(1)");
    By openNewAccountButton= By.cssSelector("#openAccountForm form input[value=\"Open New Account\"]");
    By accountResultMessage= By.cssSelector("#openAccountResult p");
    By newAccountIdBy= By.cssSelector("#openAccountResult #newAccountId");
    By overviewButton= By.cssSelector("#leftPanel ul a[href=\"overview.htm\"]");


    public AddNewAccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that all accounts in the account overview table are shown correctly while adding new account")
    public boolean checkAccountsIdsMatches(List<String> expectedAccountId){
        setAccountsIds();
         return IntStream.range(0, expectedAccountId.size())
                .allMatch(i-> actualAccountsIds.get(i).equals(expectedAccountId.get(i)));
    }

    @Step("Select the account type then submit")
    public AddNewAccountPage clickOpenNewAccountButton(){
        //Select account type from the accounts types list
        driver.findElement(accountTypeForm).click();
        driver.findElement(selectedAccountType).click();

        //Select account id from the accounts ids list
        driver.findElement(accountIDForm).click();
        driver.findElement(selectedAccountID).click();

        driver.findElement(openNewAccountButton).click();

        return this;
    }

    @Step("Check if the opened new account success message is displayed")
    public String checkOpenedAccountMessage(){
        wait.waitForVisibility(accountResultMessage);
        return driver.findElement(accountResultMessage).getText();
    }

    @Step("Navigate to the accounts overview page")
    public OverviewPage backToOverview(){
        setNewAccountId();
        driver.findElement(overviewButton).click();
        return new OverviewPage(driver);
//        return getNewAccountId();
    }

    public void setAccountsIds(){
        wait.waitForVisibility(accountIDForm);
        actualAccountsIds= driver.findElements(accountIDForm)
                .stream()
                .map(x-> x.getText().trim())
                .collect(Collectors.toList());
    }


    public String getNewAccountId(){
        return this.newAccountId;
    }

    public void setNewAccountId(){
        this.newAccountId= driver.findElement(newAccountIdBy).getText();
    }



}
