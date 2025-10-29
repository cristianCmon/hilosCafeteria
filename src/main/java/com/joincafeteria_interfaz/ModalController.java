package com.joincafeteria_interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModalController {

    @FXML
    private Button btnIniciar;

    public void clicIniciar(ActionEvent actionEvent) {
        Stage stage = (Stage) btnIniciar.getScene().getWindow();
        stage.close();
    }
}
