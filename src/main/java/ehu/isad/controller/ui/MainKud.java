package ehu.isad.controller.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.Main;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKud implements Initializable {

  private Main main;

  public MainKud(Main main){
    this.main = main;
  }
  @FXML
  private AnchorPane Cms;
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
  private AnchorPane Zerbitzariak;

  @FXML
  private AnchorPane WhatWeb;

  @FXML
  private FontAwesomeIconView btnClose;



  @FXML
  private CMSKud cmsController;

  @FXML
  private WhatWebKud whatwebController;

  @FXML
  private ZerbitzariakKud zerbitzariakController;




//  public void setMainApp(Main main) {
//    this.mainApp = mainApp;
//  }

  @FXML
  void onClick(ActionEvent event) {
    if (event.getSource().equals(whatWeb_button)) {
      this.aldatu_lehioa(2);
    }
    if (event.getSource().equals(zerbitzari_button)) {
      this.aldatu_lehioa(1);
    }
    if (event.getSource().equals(cms_button)) {
      this.aldatu_lehioa(0);
    }
  }
  public void aldatu_lehioa (int index){
    PseudoClass aukeratua = PseudoClass.getPseudoClass("aukeratua");

    if (index == 2) {
      izen_panela.setBackground(new Background(new BackgroundFill(Color.rgb(33,132,154),CornerRadii.EMPTY,Insets.EMPTY)));
      titulua.setText("WHATWEB");
      azpititulua.setText("Web orrialdeak eskanerra");
      whatWeb_button.toFront();
      Zerbitzariak.toBack();
      Cms.toBack();
      whatWeb_button.pseudoClassStateChanged(aukeratua,true);
      cms_button.pseudoClassStateChanged(aukeratua,false);
      zerbitzari_button.pseudoClassStateChanged(aukeratua,false);
    }else
    if (index == 1) {
      izen_panela.setBackground(new Background(new BackgroundFill(Color.rgb(156,156,156), CornerRadii.EMPTY, Insets.EMPTY)));
      cms_button.setEffect(new Blend());
      titulua.setText("ZERBITZARIEN ZERRENDA");
      azpititulua.setText("Kontsultatu diren zerbitzarien zerrenda");
      this.main.zerbitzariakBerriztatu();
      Zerbitzariak.toFront();
      zerbitzariakController.eguneratu();
      Cms.toBack();
      WhatWeb.toBack();
      zerbitzari_button.pseudoClassStateChanged(aukeratua,true);
      cms_button.pseudoClassStateChanged(aukeratua,false);
      whatWeb_button.pseudoClassStateChanged(aukeratua,false);
    }else
    if (index == 0) {
      izen_panela.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
      titulua.setText("CMS");
      azpititulua.setText("WordPress, Joomla, PhpMyAdmin, Drupal");
      Cms.toFront();
      WhatWeb.toBack();
      Zerbitzariak.toBack();
      cms_button.pseudoClassStateChanged(aukeratua,true);
      zerbitzari_button.pseudoClassStateChanged(aukeratua,false);
      whatWeb_button.pseudoClassStateChanged(aukeratua,false);
    }
  }


  @FXML
  void handleClose(MouseEvent event) {
    System.exit(0);
  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    PseudoClass aukeratua = PseudoClass.getPseudoClass("aukeratua");

    titulua.setText("CMS");
    azpititulua.setText("WordPress, Joomla, PhpMyAdmin, Drupal");
    izen_panela.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
    cms_button.pseudoClassStateChanged(aukeratua,true);
  }
}