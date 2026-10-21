package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pages.LoginPage;
import io.qameta.allure.Description;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Login with valid credentials")
    @Feature("Auth Feature")
    @Description("the user should be able to login with valid credentials")
    public void shouldBeAbleToLoginWithValidCredentials(){
        LoginPage loginPage= new LoginPage(getDriver());
        boolean checkAccountsTableDisplayed= loginPage.loadApp()
                .login(ConfigUtils.getInstance().getPassword()) //valid password
                .checkAccountsTableDisplayed();
        Assert.assertTrue(checkAccountsTableDisplayed);

    }

    @Test(description = "Login with invalid credentials")
    @Feature("Auth Feature")
    @Description("the user shouldn't be able to login with invalid credentials")
    public void notAbleToLoginWithInvalidCredentials(){
        LoginPage loginPage= new LoginPage(getDriver());
        loginPage.loadApp().login("test");//invalid password
        String errorMessage= loginPage.checkTheErrorMessage();
        Assert.assertEquals(errorMessage, ConfigUtils.getInstance().getLoginErrorMes());
    }
}
