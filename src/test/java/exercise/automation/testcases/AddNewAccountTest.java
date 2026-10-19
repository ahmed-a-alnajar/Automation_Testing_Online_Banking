package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pases.AddNewAccountPage;
import exercise.automation.pases.LoginPage;
import exercise.automation.pases.OverviewPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddNewAccountTest extends BaseTest {
    private boolean accountsIdsShownCorrectly;
    private boolean newAccountAddedToAccountsTable;
    private String openedAccountResultMessage;

    @BeforeClass
    public void setupAddAccountFlow(){
        LoginPage loginPage = new LoginPage(getDriver());
        accountsIdsShownCorrectly =
                loginPage.loadApp()
                        .login(ConfigUtils.getInstance().getPassword())
                        .clickOnAddNewAccount()
                        .checkAccountsIdsMatches(OverviewPage.getAccountsNumbers());

        AddNewAccountPage addNewAccountPage = new AddNewAccountPage(getDriver());
        openedAccountResultMessage= addNewAccountPage
                .clickOpenNewAccountButton()
                .checkOpenedAccountMessage();

        newAccountAddedToAccountsTable= addNewAccountPage
                .backToOverview()
                .newAccountAddedToTable(addNewAccountPage.getNewAccountId());
    }


    @Test(description = "Accounts ids list contains all accounts ids in overview table")
    @Feature("Add new account")
    @Description("Check that all accounts ids in the overview accounts table are displayed in the accounts ids list")
    public void accountsIdsListShownCorrectly(){
        Assert.assertTrue(accountsIdsShownCorrectly);
    }

    @Test(description = "the user add a new account")
    @Feature("Add new account")
    @Description("Check that the user can add new account and success message is displayed")
    public void newAccountOpenedSuccessMessaged(){
        Assert.assertEquals(
                openedAccountResultMessage,
                ConfigUtils.getInstance().getOpenedAccountResultMes());
    }

    @Test(description = "The new account is added to the account overview table")
    @Feature("Add new account")
    @Description("Check that the new account id is added to the accounts overview table")
    public void newAccountIdAddedToTheAccountsOverviewTable(){
        Assert.assertTrue(newAccountAddedToAccountsTable);
    }
}
