package exercise.automation.pases;

import exercise.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindTransactionsPage extends BasePage {

    By selectAccountBy= By.cssSelector("#transactionForm #accountId");
    By accountFirstOptionBy = By.cssSelector("#transactionForm #accountId option:first-child");
    By accountSecondOptionBy = By.cssSelector("#transactionForm #accountId option:last-child");
    By findByDateBy = By.cssSelector("#transactionForm input#transactionDate");
    By findByAmountBy = By.cssSelector("#transactionForm input#amount");
    By dateSubmitButtonBy = By.cssSelector("#transactionForm button#findByDate");
    By amountSubmitButtonBy = By.cssSelector("#transactionForm button#findByAmount");

    //Transactions Table
    By transTableDataBy= By.cssSelector("#transactionTable #transactionBody tr");
    By transDetailsButton = By.cssSelector("#transactionBody tr td:nth-child(2) a");

    //Transaction details
    By transDetailsDataBy= By.cssSelector("#rightPanel table tr");


    public FindTransactionsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Select the transactions account")
    public void selectAccount(By selectedAccount){
        wait.waitForVisibility(selectAccountBy);
        driver.findElement(selectAccountBy).click();
        driver.findElement(selectedAccount).click();
    }

    /*
    * Check filtration using date
    * * * Enter date and submit
    * * * Check the transactions date shown correctly
    * * * Check the transactions count shown correctly
    * */

    @Step("Enter Date and submit filtration")
    public FindTransactionsPage findByDate(){
        selectAccount(accountSecondOptionBy);
        driver.findElement(findByDateBy).sendKeys(todayDateFormate());
        driver.findElement(dateSubmitButtonBy).click();

        return this;
    }

    public boolean transData(){
        return isTransDateMatched() && isTransCountMatched();
    }

    @Step("Check the transactions date shown correctly")
    public boolean isTransDateMatched(){
        return driver.findElements(transTableDataBy)
                .stream()
                .map(x -> x.findElement(By.cssSelector("td:nth-child(1)")).getText())
                .allMatch(actualDate -> actualDate.equals(todayDateFormate()));
    }

    @Step("Check the transactions count shown correctly")
    public boolean isTransCountMatched(){
        wait.waitForVisibility(transTableDataBy);
        return driver.findElements(transTableDataBy).size() == 5;
    }


    /*
     * Check filtration type
     * * * Enter amount and submit
     * * * Check the transactions description shown correctly with sender and receiver accounts
     * * * Check the transactions type shown correctly with sender and receiver accounts
     */

    @Step("Enter amount and submit filtration")
    public FindTransactionsPage findByAmount(String accountType, String amount){

        if (accountType.equals("sender")){ selectAccount(accountSecondOptionBy);
        }else { selectAccount(accountFirstOptionBy);}

        driver.findElement(findByAmountBy).sendKeys(amount);
        driver.findElement(amountSubmitButtonBy).click();

        return this;
    }

    public boolean transDescriptionAndType(String transType, String expectedAmount){
        wait.waitForVisibility(transTableDataBy);
        return transDescription(transType)
                && transactionsType(transType, expectedAmount);
    }

    @Step("Check the transactions description shown correctly with sender and receiver accounts")
    public boolean transDescription(String transType){
        return driver.findElements(transTableDataBy)
                .stream()
                .map(x -> x.findElement(By.cssSelector("td:nth-child(2)")).getText())
                .anyMatch(actualDate -> {
                    if (transType.equals("sender")){
                        return actualDate.equals("Funds Transfer Sent");
                    }else{
                        return actualDate.equals("Funds Transfer Received");
                    }
                });
    }

    @Step("Check the transactions type shown correctly with sender and receiver accounts")
    public boolean transactionsType(String transType, String expectedAmount){
        return driver.findElements(transTableDataBy)
                .stream()
                .map(x ->{
                    if (transType.equals("sender")){
                        return x.findElement(By.cssSelector("td:nth-child(3)")).getText();
                    }else{
                        return x.findElement(By.cssSelector("td:nth-child(4)")).getText();
                    }
                })
                .anyMatch(actualDate -> actualDate
                           .trim()
                           .replace("$","")
                           .replace(".00", "")
                           .equals(expectedAmount)
                );
    }

    //Check transactions details screen
    @Step("Click on the transaction details button")
    public FindTransactionsPage clickTransDetailsButton(){
        wait.waitForVisibility(transTableDataBy);
        driver.findElement(transDetailsButton).click();
        return this;
    }

    @Step("Check that all transaction details shown as expected")
    public boolean isAllDetailShownCorrectly(){
        wait.waitForVisibility(transDetailsDataBy);
        Map<String, String> actualTransDetails= getTransactionDetails();
        return isTransIdAndDateEmpty(actualTransDetails) && isTransDetailsMatched(actualTransDetails);
    }

    public String todayDateFormate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formateDate= DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return today.format(formateDate);
    }

    //Check that the transaction ID and data isn't empty
    public boolean isTransIdAndDateEmpty(Map<String, String> actualTransDetails){
        return !actualTransDetails.get("Transaction ID").isEmpty()
                && !actualTransDetails.get("Date").isEmpty();
    }

    //Check that the transactions details is match the expected value
    public boolean isTransDetailsMatched(Map<String, String> actualTransDetails){
        Map<String, String> expectedTransDetails = Map.of(
                "Description", "Bill Payment to STC Comp",
                "Type", "Debit",
                "Amount", "$50.00"
        );

        return expectedTransDetails.entrySet()
                .stream()
                .allMatch(x -> x.getValue().equals(actualTransDetails.get(x.getKey())));
    }

    //Set Transactions details in a map
    public Map<String, String> getTransactionDetails(){
        Map<String, String> details= new HashMap<>();

        List<WebElement> transDetailsData= driver.findElements(transDetailsDataBy);
        for(WebElement transData: transDetailsData){
            String transDetailName= transData.findElement(By.cssSelector("td:nth-child(1)"))
                    .getText()
                    .trim()
                    .replace(":","");
            String transDetailValue= transData.findElement(By.cssSelector("td:nth-child(2)")).getText();
            details.put(transDetailName, transDetailValue);
        }
        return details;
    }

}
