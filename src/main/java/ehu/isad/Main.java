package ehu.isad;

import ehu.isad.controller.ui.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;

public class Main extends Application {

  private Parent mainUI;

  private Scene mainScene;
  private Stage stage;

  private MainKud mainKud;
  private WhatWebKud whatWebKud;
  private ZerbitzariakKud zerbitzariakKud;
  private CMSKud cmsKud;

  private double xOffset = 0;
  private double yOffset = 0;

  @Override
  public void start(Stage primaryStage) throws Exception {

    stage = primaryStage;
    pantailakKargatu();
    stage.initStyle(StageStyle.UNDECORATED);

    mainUI.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
      }
    });
    mainUI.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
      }
    });

    stage.setTitle("WhatWEB");
    stage.setScene(mainScene);
    stage.show();
  }
  public void close (){
    this.stage.close();
  }
  private void pantailakKargatu() throws IOException {

//    FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/Main.fxml"));
//    mainUI = (Parent) loaderMain.load();
//    mainKud = loaderMain.getController();
//    mainScene = new Scene(mainUI);
//
//    mainKud.setMainApp(this);


      FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/Main.fxml"));

      mainKud=new MainKud(this);
      whatWebKud=new WhatWebKud(this);
      cmsKud=new CMSKud(this);
      zerbitzariakKud=new ZerbitzariakKud(this);

      Callback<Class<?>, Object> controllerFactory = type -> {
          if (type == MainKud.class) {
              return mainKud ;
          } else if (type == WhatWebKud.class) {
              return whatWebKud;
          } else if (type == CMSKud.class) {
              return cmsKud;
          }else if (type == ZerbitzariakKud.class) {
              return zerbitzariakKud;
          } else {
              // default behavior for controllerFactory:
              try {
                  return type.newInstance();
              } catch (Exception exc) {
                  exc.printStackTrace();
                  throw new RuntimeException(exc); // fatal, just bail...
              }
          }
      };

      loaderMain.setControllerFactory(controllerFactory);

      mainUI = (Parent) loaderMain.load();
      mainScene = new Scene(mainUI);

  }
  public void aldatuPantaila(int index){
      this.mainKud.aldatu_lehioa(index);
  }
  public void zerbitzariakBerriztatu(){
      this.zerbitzariakKud.eguneratu();
  }
  public void aldatuArratoia (Cursor cursor){
    this.stage.getScene().setCursor(cursor);
  }
  public static void main(String[] args) {
    launch(args);
  }

//  public void mainErakutsi() {
//    stage.setScene(new Scene(mainUI));
//    stage.show();
//  }
}
