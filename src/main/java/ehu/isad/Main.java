package ehu.isad;

import ehu.isad.controller.ui.MainKud;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

  private Parent mainUI;

  private Scene mainScene;
  private Stage stage;

  private MainKud mainKud;

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
    mainKud.hasieraketak_egin();
    stage.show();
  }
  public void close (){
    this.stage.close();
  }
  private void pantailakKargatu() throws IOException {

    FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/Main.fxml"));
    mainUI = (Parent) loaderMain.load();
    mainKud = loaderMain.getController();
    mainScene = new Scene(mainUI);

    mainKud.setMainApp(this);

  }


  public static void main(String[] args) {
    launch(args);
  }

//  public void mainErakutsi() {
//    stage.setScene(new Scene(mainUI));
//    stage.show();
//  }
}
