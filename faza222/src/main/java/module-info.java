module org.example.faza222 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens org.example.faza222.domain to javafx.base;


    exports org.example.faza222;
    exports org.example.faza222.controller;
    opens org.example.faza222.controller to javafx.fxml;

}