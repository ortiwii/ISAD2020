open module javafx {

  requires javafx.graphics;
  requires javafx.fxml;
  requires javafx.controls;
  requires java.sql;
  requires javafx.base;
  requires de.jensd.fx.fontawesomefx.fontawesome;
  requires java.desktop;
  requires sqlite.jdbc;
  requires mongo.java.driver;
  requires org.mongodb.driver.core;

  exports ehu.isad;
}
