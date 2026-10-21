package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pages.LoginPage;
import exercise.automation.pages.OverviewPage;
import exercise.automation.pages.TransferFundsPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TransferFundsTest extends BaseTest {

    private LoginPage loginPage;
    private OverviewPage overviewPage;
    private TransferFundsPage transferFundsPage;

    @BeforeClass
    public void setUp(){
        loginPage= new LoginPage(getDriver());
        overviewPage= new OverviewPage(getDriver());
        transferFundsPage= new TransferFundsPage(getDriver());


        loginPage.loadApp().login(ConfigUtils.getInstance().getPassword());
    }

    @Test(description = "Transfer partial amount", priority = 1)
    @Feature("Transfer Funds")
    @Description("Check that the user can transfer partial amount from the account balance")
    public void shouldBeAbleToTransferPartialAmount(){
        Assert.assertTrue(
                transferFundsPage.performTransfer(overviewPage, "Partial"),
                "the transfer process doesn't completed while transfer partial amount");

        Assert.assertTrue(
                transferFundsPage.verifyBalanceUpdated()
                , "the accounts balance isn't updated");
    }

    @Test(description = "Transfer full amount", priority = 2)
    @Feature("Transfer Funds")
    @Description("Check that the user can transfer full amount from the account balance")
    public void shouldBeAbleToTransferFullAmount(){
        Assert.assertTrue(
                transferFundsPage.performTransfer(overviewPage, "Full"),
                "the transfer process doesn't completed while transfer full amount");

        Assert.assertTrue(
                transferFundsPage.verifyBalanceUpdated()
                , "the accounts balance isn't updated");
    }

    @Test(description = "Transfer exceed amount", priority = 3)
    @Feature("Transfer Funds")
    @Description("Check that the user can't transfer exceed amount from the account balance")
    public void shouldBeAbleToTransferExceedAmount(){
        Assert.assertFalse(
                transferFundsPage.performTransfer(overviewPage, "Exceed"),
                "the transfer process is completed while transfer exceed amount");
    }

}
