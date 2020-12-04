open module javafx {
  requires javafx.graphics;
  requires javafx.fxml;
  requires javafx.controls;
  requires java.sql;
  requires javafx.base;
  requires de.jensd.fx.fontawesomefx.fontawesome;
    requires sqlite.jdbc;

    exports ehu.isad;
}
