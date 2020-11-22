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
            try {
                Properties properties = null;
                InputStream in = null;

                in = this.getClass().getResourceAsStream("/setup.properties");
                properties = new Properties();
                properties.load(in);
                String root = System.getProperty("user.dir");
                String tmp = properties.getProperty("pathToInsert");
                String path = root+tmp;

                String line;
                Process p = null;
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    path = path.replace("\\","/" );
                    path = path.replace("D:", "/mnt/d");
                    path = path.replace(" ", "\\ ");
                    p = Runtime.getRuntime().exec
                            (System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never --log-sql="+path+" "+url);
                } else {
                    path = path.replace(" ", "\\ ");
                    p = Runtime.getRuntime().exec("whatweb --colour=never --log-sql="+path+" "+url);
                }
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    processes.add(line);
                }
                input.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        return processes;
    }


}
