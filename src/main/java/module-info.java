module com.clase {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql; // Es necesario para la conexion con la bbdd

    opens com.clase to javafx.fxml, com.google.gson;
    opens com.clase.modelo to javafx.fxml, javafx.base;
    exports com.clase;
}
