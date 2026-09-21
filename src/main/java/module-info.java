module com.clase {
<<<<<<< HEAD
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
=======
    requires javafx.controls;
    requires javafx.fxml;
>>>>>>> d9d769909b6a4619de033dbb9c95f3c524cea898

    opens com.clase to javafx.fxml;
    exports com.clase;
}
