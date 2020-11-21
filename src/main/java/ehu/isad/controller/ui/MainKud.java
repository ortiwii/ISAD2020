package ehu.isad.controller.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
  private AnchorPane cms;
  @FXML
  private Button cms_button;

  @FXML
  private Button zerbitzari_button;

  @FXML
  private Button whatWeb_button;
  @FXML
  private AnchorPane zerbitzariak;

  @FXML
  private AnchorPane WhatWeb;
  @FXML
  private FontAwesomeIconView btnClose;

  public void setMainApp(Main main) {
    this.mainApp = mainApp;
  }

//  @FXML
//  public void onClick(ActionEvent actionEvent) {
//
//
//  }
  @FXML
  void leihoa_aldatu(ActionEvent event) {
    if (event.getSource().equals(whatWeb_button)) {
      whatWeb_button.toFront();
      zerbitzariak.toBack();
      cms.toBack();
    }
    if (event.getSource().equals(zerbitzari_button)) {
      zerbitzariak.toFront();
      cms.toBack();
      WhatWeb.toBack();
    }
    if (event.getSource().equals(cms_button)) {
      cms.toFront();
      WhatWeb.toBack();
      zerbitzariak.toBack();
    }
  }
  @FXML
  void handleClose(MouseEvent event) {
    System.exit(0);

  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }


}