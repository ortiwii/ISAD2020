package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.MongoDBKudeatzailea;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ZerbitzariakKud {

    private Main main;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> web_zerrenda;

    private ObservableList<String> targets = FXCollections.observableArrayList();

    public ZerbitzariakKud(Main main) {
        this.main = main;
    }


    @FXML
    void initialize() {
        eguneratu();
    }

    public void eguneratu(){
        List<String> list = WhatWebDBKud.getInstance().getBilaketak();
        List<String> list_mongo= MongoDBKudeatzailea.getInstantzia().getZerbitzariak();
        targets.setAll(list);
        targets.addAll(list_mongo);
        web_zerrenda.setItems(targets);
    }
}
