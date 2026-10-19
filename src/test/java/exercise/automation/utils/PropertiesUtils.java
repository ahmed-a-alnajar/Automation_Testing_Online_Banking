package exercise.automation.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties readFile(String filePath){
        try {
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);
            Properties properties= new Properties();
            properties.load(inputStream);
            inputStream.close();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
