package ehu.isad.Services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Services {

    //Singleton
    private static Services services = new Services();
    private Services (){ }
    public static Services getInstance(){
        return services;
    }

    public String getPathToInsert(){
        String path = "";
        try {
            Properties properties = null;
            InputStream in = null;
            in = this.getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);
            if (System.getProperty("os.name").toLowerCase().contains("win")) { //windows-en dago
                path = properties.getProperty("pathToInsertWin");
            }else{ //Ubuntun dago
                path = properties.getProperty("pathToInsertUbu");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
