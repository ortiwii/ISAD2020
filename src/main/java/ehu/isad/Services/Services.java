package ehu.isad.Services;

import it.grabz.grabzit.GrabzItClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class Services {

    //Singleton
    private static Services services = new Services();
    private Services (){
//        System.setProperty("javax.xml.bind.JAXBContextFactory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
    }
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

    public void getURLImage(String url){
//        //Create the GrabzItClient class
//        //Replace "APPLICATION KEY", "APPLICATION SECRET" with the values from your account!
//        GrabzItClient grabzIt = new GrabzItClient("YjI1NmQxYzk1MGI1NGMxYjg1MDk2M2JmN2Q4MGUxNTI=", "PwBZM1Y/P0w/PxM/fz8QPz8bPzQ3Oz8/PwRfP28/Pz8=");
//        System.out.println(0);
//        try {
//            grabzIt.URLToImage("https://ikasten.io/");
//            System.out.println(1);
//            String filepath = "C:\\img.jpg";
//            System.out.println(grabzIt.SaveTo(filepath));
//            System.out.println(2);
//
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("e1");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("e2");
//            e.printStackTrace();
//        }
    }
}
