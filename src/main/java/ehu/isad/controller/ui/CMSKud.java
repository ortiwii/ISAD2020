package ehu.isad.controller.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ehu.isad.Main;

import ehu.isad.CMSTaulaModel;
import ehu.isad.Services.Services;
import ehu.isad.controller.db.WhatWebDBKud;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        this.addButtonToTable();
    }
    private void addButtonToTable() {
        TableColumn<CMSTaulaModel, Void> colBtn = new TableColumn("Irudiak");

        Callback<TableColumn<CMSTaulaModel, Void>, TableCell<CMSTaulaModel, Void>> cellFactory = new Callback<TableColumn<CMSTaulaModel, Void>, TableCell<CMSTaulaModel, Void>>() {
            @Override
            public TableCell<CMSTaulaModel, Void> call(final TableColumn<CMSTaulaModel, Void> param) {
                final TableCell<CMSTaulaModel, Void> cell = new TableCell<CMSTaulaModel, Void>() {

                    private final Button btn = new Button("Web Orria");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            Thread thread = new Thread(() -> {
                                this.getScene().setCursor(Cursor.WAIT);
                                CMSTaulaModel data = getTableView().getItems().get(getIndex());
                                Image image = Services.getInstance().getURLImage(data.getUrl());
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, " ");
                                    alert.setTitle(data.getUrl());
                                    alert.setHeaderText(" ");
                                    this.getScene().setCursor(Cursor.DEFAULT);
                                    ImageView imageView = new ImageView(image);
                                    alert.setGraphic(imageView);
//                                    alert.setHeight(200);
//                                    alert.setWidth(200);
                                    alert.showAndWait();
                                });
                            });
                            thread.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tbData.getColumns().add(colBtn);

    }
    private void aldatuWhatWebPantailara () {
        this.main.aldatuPantaila(2);
    }
}