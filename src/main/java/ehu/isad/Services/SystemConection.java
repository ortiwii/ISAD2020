package ehu.isad.Services;

import java.io.*;
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
        if (!url.equals("") ) {
            try{
                String line;
                Process p = null;
                Properties properties = null;
                InputStream in = null;
                in = this.getClass().getResourceAsStream("/setup.properties");
                properties = new Properties();
                properties.load(in);
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    if(System.getProperty("os.name").toLowerCase().contains("8.1")){
                        System.out.println(System.getProperty("os.name").toLowerCase());
                        p = Runtime.getRuntime().exec(properties.getProperty("pathPlink")+"\\plink.exe -batch -pw dbs dbs@"+properties.getProperty("ip_Addr")+" whatweb --color=never --log-sql=insert.sql "+url);
                        Runtime.getRuntime().exec(properties.getProperty("pathPlink")+"\\pscp.exe -pw dbs dbs@"+properties.getProperty("ip_Addr")+":insert.sql "+properties.getProperty("pathToInsertWin"));
                    }else {
                        p = Runtime.getRuntime().exec
                                (System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never --aggression 1 --log-sql=/tmp/insert.sql "+url);
                    }
                    } else {
                    p = Runtime.getRuntime().exec("whatweb --colour=never --aggression 1 --log-sql=/tmp/insert.sql "+url);
                }
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    processes.add(line);
                }
                input.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return processes;
    }

    public List<String> execWhatWebMongo(String url) {
        List<String> processes = new LinkedList<String>();
        if (!url.equals("") ) {
            try{
                String line;
                Process p = null;
                Properties properties = null;
                InputStream in = null;
                in = this.getClass().getResourceAsStream("/setup.properties");
                properties = new Properties();
                properties.load(in);
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    if(System.getProperty("os.name").toLowerCase().contains("8.1")){
                        System.out.println(System.getProperty("os.name").toLowerCase());
                        p = Runtime.getRuntime().exec(properties.getProperty("pathPlink")+"\\plink.exe -batch -pw dbs dbs@"+properties.getProperty("ip_Addr")+" whatweb --color=never --aggression 1 --log-mongo-database whatweb "+url);
                    }else {
                        p = Runtime.getRuntime().exec
                                (System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never --aggression 1 --log-mongo-database whatweb "+url);
                    }
                } else {
                    p = Runtime.getRuntime().exec("whatweb --colour=never --aggression 1 --log-mongo-database whatweb "+url);
                }
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    processes.add(line);
                }
                input.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return processes;
    }
    public void deleteFile () {
        try {
//            System.out.println(System.getProperty("os.name").toLowerCase());
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                if (System.getProperty("os.name").toLowerCase().contains("8.1")){
                    Properties properties = null;
                    InputStream in = null;
                    in = this.getClass().getResourceAsStream("/setup.properties");
                    properties = new Properties();
                    properties.load(in);
                    Runtime.getRuntime().exec(properties.getProperty("pathPlink")+"\\plink.exe -batch -pw dbs dbs@"+properties.getProperty("ip_Addr")+" rm -rf insert.sql");
                    File f=new File(properties.getProperty("pathToInsert"));
                    f.delete();
                }else {
                    Runtime.getRuntime().exec
                            (System.getenv("windir") + "\\system32\\" + "wsl rm -r " + "/tmp/insert.sql");
                }
            } else {
                Runtime.getRuntime().exec("rm -r " +"/tmp/insert.sql");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
