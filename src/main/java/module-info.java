module com.joincafeteria_interfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.jfr;


    opens com.joincafeteria_interfaz to javafx.fxml;
    exports com.joincafeteria_interfaz;
}