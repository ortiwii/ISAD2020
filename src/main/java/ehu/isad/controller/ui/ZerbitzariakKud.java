package ehu.isad.controller.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ehu.isad.Main;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ZerbitzariakKud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> web_zerrenda;

    @FXML
    private Button eguneratu_button;

    private ObservableList<String> targets = FXCollections.observableArrayList();

    public ZerbitzariakKud(Main main) {
        System.out.println("ZerbitzariakKud instantziatu da.");
    }

    @FXML
    void OnClick(ActionEvent event) {
        this.eguneratu();
    }

    @FXML
    void initialize() {
        this.eguneratu();
    }
    private void eguneratu(){
        List<String> list = WhatWebDBKud.getInstance().getBilaketak();
        targets.setAll(list);
        web_zerrenda.setItems(targets);
    }
}
