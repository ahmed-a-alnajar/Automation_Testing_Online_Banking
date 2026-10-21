package exercise.automation.testcases;

import exercise.automation.base.BaseTest;
import exercise.automation.pases.BillPayPage;
import exercise.automation.pases.LoginPage;
import exercise.automation.pases.OverviewPage;
import exercise.automation.utils.ConfigUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BillPayTest extends BaseTest {
    private LoginPage loginPage;
    private OverviewPage overviewPage;
    private BillPayPage billPayPage;

    @BeforeClass()
    public void SetUp(){
        loginPage = new LoginPage(getDriver());
        overviewPage = new OverviewPage(getDriver());
        billPayPage= new BillPayPage(getDriver());

        loginPage.loadApp().login(ConfigUtils.getInstance().getPassword());
    }

    @Test(priority = 1,description = "Check success message after bill payment")
    @Feature("Bill Pay Feature")
    @Description("Check that the success message after paying the bill")
    public void successMessageShouldBeDisplayedAfterBillPayment(){

        String successMessage = overviewPage
                .clickBillPayButon()
                .enterBillData()
                .clickSentPayment()
                .checkPaymentResultMes();
        Assert.assertEquals(
                successMessage,
                ConfigUtils.getInstance().getPaymentResultMes(),
                "Bill Payment completed message hasn't been displayed");
    }

    @Test(priority = 2, description = "Check bill amount deduction")
    @Feature("Bill Pay Feature")
    @Description("Check that the bill amount has been deducted after completed payment")
    public void billAmountShouldBeDeductedAfterBillPayment(){
        Assert.assertTrue(
                billPayPage.verifyBalanceUpdated(),
                "the account balance hasn't been deducted by the bill amount"
        );

    }
}
