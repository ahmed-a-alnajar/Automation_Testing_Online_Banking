package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pages.LoginPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test(description = "the user should be able to register with valid data")
    @Feature("Registration")
    @Description("the user should be able to register with valid data")
    public void shouldBeAbleToRegisterWithValidData(){
        LoginPage loginPage= new LoginPage(getDriver());
        String checkSuccessRegistrationMes=
                loginPage.loadApp()
                .clickOnRegisterButton()
                .fillRegisterForm()
                .submitRegisterForm()
                .checkSuccessRegistration();
        Assert.assertEquals(
                checkSuccessRegistrationMes,
                ConfigUtils.getInstance().getSuccessRegistrationMessage());


    }
}
