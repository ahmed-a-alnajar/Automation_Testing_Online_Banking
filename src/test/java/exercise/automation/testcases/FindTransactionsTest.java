package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pases.FindTransactionsPage;
import exercise.automation.pases.LoginPage;
import exercise.automation.pases.OverviewPage;
import exercise.automation.pases.TransferFundsPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindTransactionsTest extends BaseTest {

    private LoginPage loginPage;
    private OverviewPage overviewPage;
    private FindTransactionsPage findTransactionsPage;

    @BeforeClass()
    public void SetUp(){
        loginPage = new LoginPage(getDriver());
        overviewPage = new OverviewPage(getDriver());
        findTransactionsPage= new FindTransactionsPage(getDriver());

        loginPage.loadApp().login(ConfigUtils.getInstance().getPassword());
    }

    @Test(priority = 1,description = "Check all transactions")
    @Feature("Find Transactions Feature")
    @Description("Check that all transactions are shown successfully")
    public void AllTransactionsShouldShownSuccessfully(){
        boolean transactionsData= overviewPage.clickFindTransButon()
                .findByDate()
                .transData();

        Assert.assertTrue(transactionsData, "Transactions data mismatch");
    }

    @Test(priority = 2,description = "Check transactions description and type")
    @Feature("Find Transactions Feature")
    @Description("Check that transactions description and type shown correctly based on sender and receiver account")
    public void AllTransactionDescriptionAndTypeShouldShownSuccessfully(){
        boolean transactionsSenderType= overviewPage.clickFindTransButon()
                .findByAmount("sender", "150")
                .transDescriptionAndType("sender", "150");
        Assert.assertTrue(transactionsSenderType, "Transactions sender account shown incorrectly");

        boolean transactionsReceiverType= overviewPage.clickFindTransButon()
                .findByAmount("Receiver", "150")
                .transDescriptionAndType("Receiver", "150");
        Assert.assertTrue(transactionsReceiverType, "Transactions Receiver account shown incorrectly");

    }
}
