package ehu.isad.controller.db;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class WhatWebDBKud {
    private static WhatWebDBKud instantzia = new WhatWebDBKud();

    private WhatWebDBKud () { }

    public static WhatWebDBKud getInstance(){
        return instantzia;
    }

    public void txertatu(String url){
        Properties properties = null;
        InputStream in = null;

        try {
            in = this.getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            String tmp = properties.getProperty("pathToInsert");
            String root = System.getProperty("user.dir");
            String path = root+tmp;
            System.out.println(path);
            File insert = new File(path);

            Scanner sc = new Scanner(insert);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                line = line.replace("INSERT IGNORE INTO", "INSERT OR IGNORE INTO");
                DBKudeatzaile.getInstantzia().execSQL(line);
            }
            insert.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean bilatutaDago (String url){
        String query = "SELECT * FROM targets WHERE target = '"+url+"';";
        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try{
            return rs.next();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public List<String> getBilaketak(){
        List<String> emaitza = new ArrayList<>();
        String query = "SELECT target FROM targets;";
        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try {
            while(rs.next()){
                emaitza.add(rs.getString("target"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return emaitza;
    }
}