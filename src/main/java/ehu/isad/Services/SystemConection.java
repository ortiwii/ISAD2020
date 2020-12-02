package ehu.isad.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

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
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    p = Runtime.getRuntime().exec
                            (System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never --log-sql=/tmp/insert.sql "+url);
                } else {
                    p = Runtime.getRuntime().exec("whatweb --colour=never --log-sql=/tmp/insert.sql "+url);
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
