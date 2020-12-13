package ehu.isad.controller.db;


import ehu.isad.CMSTaulaModel;
import ehu.isad.Services.Services;
import ehu.isad.Services.SystemConection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WhatWebDBKud {
    private static WhatWebDBKud instantzia = new WhatWebDBKud();

    private WhatWebDBKud () { }

    public static WhatWebDBKud getInstance(){
        return instantzia;
    }

    public void txertatu(String target){
        if (WhatWebDBKud.getInstance().bilatutaDago(target)) {
            this.ezabatu(target);
        }
        InputStream in = null;
        try {
            String path = Services.getInstance().getPathToInsert();
            File insert = new File(path);

            Scanner sc = new Scanner(insert);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                line = line.replace("INSERT IGNORE INTO", "INSERT OR IGNORE INTO");
                DBKudeatzaile.getInstantzia().execSQL(line);
            }
            SystemConection.getInstance().deleteFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ezabatu (String target){

        String query1 = "delete from request_configs where config_id in (SELECT DISTINCT S.config_id FROM targets T, scans S WHERE T.target LIKE '%"+target+"%' AND T.target_id = S.target_id);";
        String query2 = "delete from scans where target_id like (SELECT T.target_id FROM targets T WHERE T.target LIKE '%"+target+"%');";
        String query3 = "delete from targets where target LIKE '%"+target+"%';";
        String query4 = "delete from urlKaptura where url = '%"+target+"%';";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(query1);
        dbKudeatzaile.execSQL(query2);
        dbKudeatzaile.execSQL(query3);
        dbKudeatzaile.execSQL(query4);

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
        String query = "SELECT target FROM targets where status like 200;";
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
    public List<CMSTaulaModel> getAukerak (String url, String cms) {

        String query = "";
        List<CMSTaulaModel>emaitza = new ArrayList<>();

        if( (url == null || url.equals("")) && (cms == null || cms.equals("")) ){ //Bi aldagaiak hutsik
            System.out.println("1, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, 0 as version, T.date FROM targets T, Scans S, completeCMS c WHERE T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND c.name LIKE '%unknown%' AND T.status LIKE 200\n" +
                    "union\n" +
                    "SELECT DISTINCT T.target, c.name, S.version,T.date FROM targets T, Scans S, completeCMS c WHERE T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND c.name NOT LIKE '%unknown%' AND T.status LIKE 200";

        }else if ( url == null || url.equals("") ){ // url hutsik baina cms ez hutsik
            System.out.println("2, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, T.date, S.version FROM targets T, Scans S, completeCMS c WHERE c.name LIKE '%"+cms+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND T.status LIKE 200;" ;
        }else if ( cms == null || cms.equals("") ){ // url beteta baina cms hutsik
            System.out.println("3, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, 0 as version, T.date FROM targets T, Scans S, completeCMS c WHERE T.target LIKE '%"+url+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND c.name LIKE '%unknown%' AND T.status LIKE 200\n" +
                    "union\n" +
                    "SELECT DISTINCT T.target, c.name, S.version,T.date FROM targets T, Scans S, completeCMS c WHERE T.target LIKE '%"+url+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND c.name NOT LIKE '%unknown%' AND T.status LIKE 200";
        }else{ //Bi aldagaiak beteta
            System.out.println("4, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, T.date, S.version FROM targets T, Scans S, completeCMS c WHERE c.name LIKE '%"+cms+"%' AND T.target LIKE '%"+url+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id AND T.status LIKE 200";
        }

        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try {
            while(rs.next()){
                String target = rs.getString("target");
                String plugin_name = rs.getString("name");
                String version = rs.getString("version");
                String date = rs.getString("date");
                CMSTaulaModel actual = new CMSTaulaModel(target, plugin_name, version, date);
                emaitza.add(actual);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return emaitza;
    }
    public List<String> getCMS (){
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT p.name FROM plugins p inner join cms c on p.plugin_id = c.plugin_id";
        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try {
            while(rs.next()){
                list.add(rs.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    public String getIrudiPath (String url){
        String query = "SELECT path FROM urlKaptura WHERE url LIKE '%"+url+"%';";
        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try {
            if (rs.next()){
                return rs.getString("path");
            }else{
                return "";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
    public void irudiaKargatu(String url, String path){
        String query = "insert into urlKaptura values ('"+url+"', '"+path+"');";
        DBKudeatzaile.getInstantzia().execSQL(query);
    }
}
