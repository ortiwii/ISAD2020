package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.Services.SystemConection;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class WhatWebKud implements Initializable {

    private Main main;

    @FXML
    private TextField url_txt_1;

    @FXML
    private TextArea log_txt_1;

    @FXML
    private Button eskaneatu_button_1;

    @FXML
    private Button garbitu_button_1;

    @FXML
    private TextField url_txt_2;

    @FXML
    private TextArea log_txt_2;

    @FXML
    private Button eskaneatu_button_2;

    @FXML
    private TextField erabiltzailea;

    @FXML
    private TextField pasahitza;

    @FXML
    private Button garbitu_button_2;

    public WhatWebKud(Main main) {
        this.main = main;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void onClick(ActionEvent event) {
        if (event.getSource() == eskaneatu_button_1){
            this.bilatu();
        }else if (event.getSource() == eskaneatu_button_2){
            this.bilatuMongo();
        }else if (event.getSource() == garbitu_button_1){
            this.garbitu_1();
        }else if (event.getSource() == garbitu_button_2){
            this.garbitu_2();
        }

    }
    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
           this.bilatu();
        }
    }


    @FXML
    void keyPressedMongo(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            this.bilatuMongo();
        }
    }
    private void bilatuMongo(){
        log_txt_2.setWrapText(true);
        log_txt_2.setText("WhatWeb Mongo kargatzen itxaron mesedez ........");
       //
        Thread thread = new Thread(() -> {

            this.main.aldatuArratoia(Cursor.WAIT);
            this.url_txt_2.setEditable(false);
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            String url = url_txt_2.getText();

            SystemConection.getInstance().execWhatWebMongo(url).forEach(line -> {
                emaitza.append(line + newLine);
            });
            Platform.runLater(() -> {
                log_txt_2.setText(emaitza.toString());
                url_txt_2.setText(url);
                this.main.aldatuArratoia(Cursor.DEFAULT);
                this.url_txt_2.setEditable(true);

            });
        });
        thread.start();
    }
    private void bilatu (){
        log_txt_1.setWrapText(true);
        log_txt_1.setText("Kargatzen. Itxaron, mesedez....");

        Thread thread = new Thread(() -> {

            this.main.aldatuArratoia(Cursor.WAIT);
            this.url_txt_1.setEditable(false);
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            String url = url_txt_1.getText();

            SystemConection.getInstance().execWhatWeb(url).forEach(line -> {
                emaitza.append(line + newLine);
            });

            Platform.runLater(() -> {
                boolean flag = WhatWebDBKud.getInstance().txertatu(url);
                if (flag){
                    log_txt_1.setText(emaitza.toString());
                }else{
                    log_txt_1.setText("EZ DUZU URL ONDO SARTU !");
                }
                url_txt_1.setText(url);
                this.main.aldatuArratoia(Cursor.DEFAULT);
                this.url_txt_1.setEditable(true);

            });
        });
        thread.start();
    }
    private void garbitu_1 (){
        this.log_txt_1.setText("");
        this.url_txt_1.setText("");
    }
    private void garbitu_2(){
        url_txt_2.setText("");
        log_txt_2.setText("");
        erabiltzailea.setText("");
        pasahitza.setText("");

    }
}
