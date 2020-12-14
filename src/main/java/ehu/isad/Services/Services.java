package ehu.isad.Services;

import ehu.isad.controller.db.WhatWebDBKud;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;


public class Services {

    //Singleton
    private static Services services = new Services();
    private Services (){    }
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

    public Image getURLImage(String url) {

        String path = WhatWebDBKud.getInstance().getIrudiPath(url);

        if (path == ""){
            URL con = null;
            try {
                con = new URL("http://elbarto.bar:3000/?page="+url);
                URLConnection yc = con.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    if (inputLine.contains("img src=")){
                        path = inputLine;
                    }
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            path="http://elbarto.bar:3000/"+path.split("'")[1];
            WhatWebDBKud.getInstance().irudiaKargatu(url, path);
        }

        if (path != ""){
            return this.createImage(path);
        }else{
            return null;
        }
    }
    private Image createImage(String url) {

        Image image= null;
        try{
            url = url.replace("-S", "-M");
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            try (InputStream stream = conn.getInputStream()) {
                image = new Image(stream, 700, 700, true, false);

            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
