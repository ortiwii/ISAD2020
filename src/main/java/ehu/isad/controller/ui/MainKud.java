package ehu.isad.controller.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainKud implements Initializable {

  private Main mainApp;

  @FXML
  private FontAwesomeIconView btnClose;

  @FXML
  private TextArea txtArea;

  @FXML
  private TextField urlArea;

  public void setMainApp(Main main) {
    this.mainApp = mainApp;
  }

  @FXML
  public void onClick(ActionEvent actionEvent) {

    txtArea.setWrapText(true);
    txtArea.setText("Kargatzen. Itxaron, mesedez....");


    Thread taskThread = new Thread(() -> {

      String newLine = System.getProperty("line.separator");
      final StringBuilder emaitza = new StringBuilder();
      this.allProcesses().forEach(line -> {
        emaitza.append(line + newLine);
      });

      Platform.runLater(() -> {
        txtArea.setText(emaitza.toString());

//        txertatu();

      });

    });

    taskThread.start();

  }
  @FXML
  void handleClose(MouseEvent event) {
    System.exit(0);
  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public List<String> allProcesses() {
    List<String> processes = new LinkedList<String>();
    String url = this.urlArea.getText();
    if (!url.equals("")) {
      try {
        String line;
        Process p = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
          p = Runtime.getRuntime().exec
                  (System.getenv("windir") + "\\system32\\" + "wsl whatweb --colour=never "+url);
        } else {
          p = Runtime.getRuntime().exec("whatweb "+url);
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