package ehu.isad.controller.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ehu.isad.Main;

import ehu.isad.CMSTaulaModel;
import ehu.isad.Services.Services;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class CMSKud {

    private Main main;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane cms;

    @FXML
    private TextField urlArea;

    @FXML
    private ComboBox<String> cbox;

    @FXML
    private Button addUrlButton;

    @FXML
    private TableView<CMSTaulaModel> tbData;

    @FXML
    private TableColumn<CMSTaulaModel, String> urlColumn;

    @FXML
    private TableColumn<CMSTaulaModel, String> cmsColumn;

    @FXML
    private TableColumn<CMSTaulaModel, String> versionColumn;

    @FXML
    private TableColumn<CMSTaulaModel, String> lastUpdatedColumn;

    private ObservableList<CMSTaulaModel> taulaModels = FXCollections.observableArrayList(new ArrayList<CMSTaulaModel>());

    public CMSKud(Main main) {
        this.main = main;
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            List<CMSTaulaModel>aukerak = WhatWebDBKud.getInstance().getAukerak(this.urlArea.getText(), this.cbox.getValue());
            taulaModels.setAll(aukerak);
            tbData.refresh();
        }
    }
    @FXML
    void suprPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE){
            cbox.getSelectionModel().clearSelection();
        }
    }
    @FXML
    void onClick(ActionEvent event) {
        if (event.getSource() == addUrlButton){
            this.aldatuWhatWebPantailara();
            Services.getInstance().getURLImage("");
        }
    }
    @FXML
    void initialize() {

        List<String> list = WhatWebDBKud.getInstance().getCMS();
        ObservableList<String> cms = FXCollections.observableArrayList(list);
        this.cbox.setItems(cms);

        tbData.setEditable(false);

        urlColumn.setCellValueFactory(new PropertyValueFactory<>("Url"));
         cmsColumn.setCellValueFactory(new PropertyValueFactory<>("Cms"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("Version"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        tbData.setItems(this.taulaModels);
    }
    private void aldatuWhatWebPantailara () {
        this.main.aldatuPantaila(2);
    }
}