package testbase;

import java.io.IOException;

import static testbase.StaticObjectRepo.envProp;

public class ConfigReader {
    public static String GetConfigValue(String key) throws IOException {
        String value = "";
        try{
            value = envProp.getProperty(key);
        }
        catch (Exception ex)
        {
            System.out.println("\n Exception occured when geting value from config \n Check Key : {0}" + key + "\n" + ex.getMessage());
        }
        return value;
    }
}
