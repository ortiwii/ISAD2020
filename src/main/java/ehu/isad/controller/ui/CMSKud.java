package ehu.isad.controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import ehu.isad.Services.WhatWebConection;
import ehu.isad.controller.db.DBKudeatzaile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        txtArea.setWrapText(true);
        txtArea.setText("Kargatzen. Itxaron, mesedez....");


        Thread taskThread = new Thread(() -> {
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            WhatWebConection.getInstance().allProcesses(urlArea.getText()).forEach(line -> {
                emaitza.append(line + newLine);
            });

            Platform.runLater(() -> {
                System.out.println("Aurkituta");
                txtArea.setText(emaitza.toString());




            });

        });

        taskThread.start();

    }

    @FXML
    void initialize() {

    }
}