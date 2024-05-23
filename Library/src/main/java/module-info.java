module ba.unsa.rpr.predavanje.dal_i_mvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.library to javafx.fxml;
    exports com.example.library;
}