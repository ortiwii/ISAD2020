package ehu.isad.controller.db;


import ehu.isad.CMSTaulaModel;
import ehu.isad.Services.Services;
import ehu.isad.Services.SystemConection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WhatWebDBKud {
    private static WhatWebDBKud instantzia = new WhatWebDBKud();

    private WhatWebDBKud () { }

    public static WhatWebDBKud getInstance(){
        return instantzia;
    }

    public void txertatu(String url){
        if (!WhatWebDBKud.getInstance().bilatutaDago(url)){
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
    public List<CMSTaulaModel> getAukerak (String url, String cms) {

        String query = "";
        List<CMSTaulaModel>emaitza = new ArrayList<>();

        if( (url == null || url.equals("")) && (cms == null || cms.equals("")) ){ //Bi aldagaiak hutsik
            System.out.println("1, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, S.version FROM targets T, Scans S, completeCMS c WHERE T.target_id = S.target_id AND S.plugin_id = c.plugin_id";
        }else if ( url == null || url.equals("") ){ // url hutsik baina cms ez hutsik
            System.out.println("2, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, S.version FROM targets T, Scans S, completeCMS c WHERE c.name LIKE '%"+cms+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id;";
        }else if ( cms == null || cms.equals("") ){ // url beteta baina cms hutsik
            System.out.println("3, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, S.version FROM targets T, Scans S, completeCMS c WHERE T.target LIKE '%"+url+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id";
        }else{ //Bi aldagaiak beteta
            System.out.println("4, "+url+", "+cms);
            query = "SELECT DISTINCT T.target, c.name, S.version FROM targets T, Scans S, completeCMS c WHERE c.name LIKE '%"+cms+"%' AND T.target LIKE '%"+url+"%' AND T.target_id = S.target_id AND S.plugin_id = c.plugin_id";
        }

        ResultSet rs = DBKudeatzaile.getInstantzia().execSQL(query);
        try {
            while(rs.next()){
                String target = rs.getString("target");
                String plugin_name = rs.getString("name");
                String version = rs.getString("version");
                String date = java.time.LocalDate.now().toString();
                CMSTaulaModel actual = new CMSTaulaModel(target, plugin_name, version, date);
                emaitza.add(actual);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return emaitza;
    }
    public List<CMSTaulaModel> getAukerak (String url){
        List<CMSTaulaModel>emaitza = new ArrayList<>();

        return emaitza;
    }
}
