package ehu.isad.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class WhatWebConection {

    private static WhatWebConection instantzia = new WhatWebConection();

    private WhatWebConection (){ }

    public static WhatWebConection getInstance(){
        return instantzia;
    }

    public List<String> allProcesses(String url) {
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


}
