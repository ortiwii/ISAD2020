package ehu.isad.controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import ehu.isad.Services.WhatWebConection;
import ehu.isad.controller.db.DBKudeatzaile;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class CMSKud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField urlArea;

    @FXML
    private TextArea txtArea;

    @FXML
    void onClick(ActionEvent event) {

        this.bilatu();

    }
    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            this.bilatu();
        }
    }
    @FXML
    void initialize() {

    }
    private void bilatu (){
        txtArea.setWrapText(true);
        txtArea.setText("Kargatzen. Itxaron, mesedez....");

        Thread thread = new Thread(() -> {
            //TODO --> SOILIK BILAKETA EGIN BEHAR DA DATU BASEAN EZ DAGOENEAN, BERAZ LEHENIK ETA BEHIN bilatutaDago() ??
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            String url = urlArea.getText();
            WhatWebConection.getInstance().allProcesses(url).forEach(line -> {
                emaitza.append(line + newLine);
            });

            Platform.runLater(() -> {
                txtArea.setText(emaitza.toString());
                WhatWebDBKud.getInstance().txertatu(url);
            });
        });

        thread.start();
    }
}