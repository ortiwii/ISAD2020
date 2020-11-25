package ehu.isad.controller.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
  private Pane izen_panela;

  @FXML
  private Label azpititulua;

  @FXML
  private Label titulua;

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

  @FXML
  void leihoa_aldatu(ActionEvent event) {
    if (event.getSource().equals(whatWeb_button)) {
      cms_button.setEffect(new Blend());
      izen_panela.setBackground(new Background(new BackgroundFill(Color.rgb(252,238,37),CornerRadii.EMPTY,Insets.EMPTY)));
      titulua.setText("WHATWEB");
      azpititulua.setText("Web orrialdeak eskanerra");
      whatWeb_button.toFront();
      zerbitzariak.toBack();
      cms.toBack();
    }
    if (event.getSource().equals(zerbitzari_button)) {
      izen_panela.setBackground(new Background(new BackgroundFill(Color.rgb(156,156,156), CornerRadii.EMPTY, Insets.EMPTY)));
      cms_button.setEffect(new Blend());
      titulua.setText("ZERBITZARIEN ZERRENDA");
      azpititulua.setText("Kontsultatu diren zerbitzarien zerrenda");
      zerbitzariak.toFront();
      cms.toBack();
      WhatWeb.toBack();
    }
    if (event.getSource().equals(cms_button)) {
      izen_panela.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
      cms_button.setEffect(new Blend());
      titulua.setText("CMS");
      azpititulua.setText("WordPress, Joomla, PhpMyAdmin, Drupal");
      cms.toFront();
      WhatWeb.toBack();
      zerbitzariak.toBack();
    }
  }
  public void hasieraketak_egin(){
    titulua.setText("CMS");
    azpititulua.setText("WordPress, Joomla, PhpMyAdmin, Drupal");
    izen_panela.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
    cms_button.setEffect(new Blend(BlendMode.ADD));

  }
  @FXML
  void handleClose(MouseEvent event) {
    System.exit(0);

  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }


}