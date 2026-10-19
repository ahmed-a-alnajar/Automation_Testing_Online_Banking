package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pases.LoginPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OverviewAccountsTest extends BaseTest {

    @Test(description = "Check that accounts displayed")
    @Feature("Accounts overview")
    @Description("Check that the accounts shown in the accounts table")
    public void checkAccountsOverview(){
        LoginPage loginPage= new LoginPage(getDriver());
        boolean isAccountsDetailsShownCorrectly= loginPage.loadApp()
                .login(ConfigUtils.getInstance().getPassword())
                .accountsTableContainsData();
        Assert.assertTrue(isAccountsDetailsShownCorrectly);
    }
}
