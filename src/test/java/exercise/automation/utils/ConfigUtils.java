package exercise.automation.utils;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {
    private Properties properties;
    private static ConfigUtils configUtils;

    public ConfigUtils(){
        properties = PropertiesUtils.readFile("src/test/java/exercise/automation/config/production.properties");
    }

    public static ConfigUtils getInstance(){
        if(configUtils == null){
            configUtils = new ConfigUtils();
        }
        return configUtils;
    }

    public String getBaseUrl(){
        String baseUrl= properties.getProperty("baseUrl");
        if(baseUrl != null){ return baseUrl; }

        throw new RuntimeException("the baseUrl value not found");
    }

    public String getSuccessRegistrationMessage(){
        String successMessage= properties.getProperty("successRegistrationMessage");
        if(successMessage != null){ return successMessage; }

        throw new RuntimeException("the success registration message value not found");
    }

    public String getUserName(){
        String userName= properties.getProperty("username");
        if(userName != null){ return userName; }

        throw new RuntimeException("the user name value not found");
    }

    public String getPassword(){
        String password= properties.getProperty("password");
        if(password != null){ return password; }

        throw new RuntimeException("the password value not found");
    }

    public String getLoginErrorMes(){
        String LoginErrorMes= properties.getProperty("loginErrorMessage");
        if(LoginErrorMes != null){ return LoginErrorMes; }

        throw new RuntimeException("the error message value not found");
    }

    public String getOpenedAccountResultMes(){
        String openedAccountResultMes= properties.getProperty("openedAccountResultMessage");
        if(openedAccountResultMes != null){ return openedAccountResultMes; }

        throw new RuntimeException("the opened account result message value not found");
    }

    public String getPaymentResultMes(){
        String paymentResultMes= properties.getProperty("paymentResultMessage");
        if(paymentResultMes != null){ return paymentResultMes; }

        throw new RuntimeException("the payment result message value not found");
    }

    /*
    * Account Overview Data
    * */
    public String getPasswordCheckAccounts(){
        String passwordCheckAccounts= properties.getProperty("checkAccountsPassword");
        if(passwordCheckAccounts != null){ return passwordCheckAccounts; }

        throw new RuntimeException("the password value not found");
    }

    public String getUsernameCheckAccounts(){
        String UsernameCheckAccounts= properties.getProperty("checkAccountsUsername");
        if(UsernameCheckAccounts != null){ return UsernameCheckAccounts; }

        throw new RuntimeException("the username value not found");
    }

    public Map<String, String> getAccountsData(){
        String accounts= properties.getProperty("accounts");
        if(accounts== null){throw new RuntimeException("the accounts value not found");}

        Map<String, String> map= new HashMap<>();
        for(String account: accounts.split(",")){
            String[] accountParts = account.split(":");
            map.put(accountParts[0].trim(), accountParts[1].trim());
        }
        return map;
    }

    /*
    * Set registered username
    * Set registered password
    * Store them in the file
    * */
    public void setUserName(String userName){
        properties.setProperty("username", userName);
        saveProperties();
    }

    public void setPassword(String pass){
        properties.setProperty("password", pass);
        saveProperties();
    }

    public void saveProperties() {
        try{
            FileOutputStream out = new FileOutputStream("src/test/java/exercise/automation/config/production.properties");
            properties.store(out, "Updated credentials");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save properties file", e);
        }
    }

}
