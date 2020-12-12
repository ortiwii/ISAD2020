package ehu.isad.Services;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class SystemConection {

    private static SystemConection instantzia = new SystemConection();

    private SystemConection(){ }

    public static SystemConection getInstance(){
        return instantzia;
    }

    public List<String> execWhatWeb(String url) {
        List<String> processes = new LinkedList<String>();
        String comand = "";
        if (!url.equals("")) {
            String line;
            Process p = null;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                comand = System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never --aggression 1 --log-sql=/tmp/insert.sql " + url;

            } else {
                comand = "whatweb --colour=never --aggression 1 --log-sql=/tmp/insert.sql " + url;
            }
            processes = this.exeqComand(comand);
        }
        return processes;
    }
    private List<String> exeqComand (String comand){
        List<String> processes = new LinkedList<String>();
        try{
            String line;
            Process p = Runtime.getRuntime().exec (comand);
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
            }
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return processes;
    }
    public void deleteFile () {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec
                        (System.getenv("windir") + "\\system32\\" + "wsl rm -r " +"/tmp/insert.sql");
            } else {
                Runtime.getRuntime().exec("rm -r " +"/tmp/insert.sql");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
