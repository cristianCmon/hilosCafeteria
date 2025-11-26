package com.joincafeteria_interfaz;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.Frequency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrincipalController {
    @FXML
    private GridPane gpClientes;

    @FXML
    private Button btnPasarCliente, btnFinalizarEjecucion;

    @FXML
    private Label lbCamarero1, lbCamarero2, lbFin;

    private int indiceCliente = 0, indiceRejilla = 0;

    @FXML
    private void initialize() {
        mostrarModal();
        lbFin.setVisible(false);
        abrirCafeteria();
    }

    public void abrirCafeteria() {
        System.out.println("\n -- CAFETERÍA ABIERTA -- \n");

        Camarero cm1 = new Camarero("Roberto");
        Camarero cm2 = new Camarero("Teresa");

        cm1.start();
        cm2.start();
    }

    public void clicPasarCliente(ActionEvent actionEvent) {
        Cliente cliente = new Cliente("C" + indiceCliente, 20);
        System.out.println("PASA " + cliente.getNombre());
        cliente.start();

        // Si se alcanza el límite máximo de la rejilla se empieza desde el principio
        if (indiceRejilla >= gpClientes.getChildren().size()) {
            indiceRejilla = 0;
        }

        Node node = gpClientes.getChildren().get(indiceRejilla);
        if (node instanceof Label) {
            Label etiquetaActual = (Label) node;
            etiquetaActual.setText(cliente.getNombre());
            cambiarColor(cliente, "amarillo");
        }

        indiceCliente++;
        indiceRejilla++;
    }

    public void clicFinalizarEjecucion(ActionEvent actionEvent) {
        System.out.println("CLICK FINALIZAR");
        Platform.exit();
        System.exit(0);
    }

    public void cambiarColor(Cliente cliente, String color) {
        for (Node node : gpClientes.getChildren()) {
            if (node instanceof Label) {
                Label etiquetaActual = (Label) node;
                if (etiquetaActual.getText().equals(cliente.getNombre())) {

                    switch (color) {
                        case "amarillo":
                            etiquetaActual.setStyle("-fx-border-color: gold; -fx-border-width: 2;");
                            break;
                        case "azul":
                            etiquetaActual.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2;");
                            break;
                        case "azulPuntos":
                            etiquetaActual.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2; -fx-border-style: dotted;");
                            break;
                        case "verde":
                            etiquetaActual.setStyle("-fx-border-color: green; -fx-border-width: 2;");
                            break;
                        case "rojo":
                            etiquetaActual.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                            break;
                    }
                }
            }
        }
    }

    public void cambiarColor(Camarero camarero, String color) {
        if (camarero.getNombre().equals("Roberto")) {
            switch (color) {
                case "azul":
                    lbCamarero1.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2;");
                    break;
                case "azulPuntos":
                    lbCamarero1.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2; -fx-border-style: dotted;");
                    break;
            }
        } else {
            switch (color) {
                case "azul":
                    lbCamarero2.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2;");
                    break;
                case "azulPuntos":
                    lbCamarero2.setStyle("-fx-border-color: RoyalBlue; -fx-border-width: 2; -fx-border-style: dotted;");
                    break;
            }
        }
    }

    public void mostrarModal() {
        try {
            String rutaVista = "/com/joincafeteria_interfaz/vistaModal.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaVista));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("INFORMACIÓN FUNCIONAMIENTO");

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("ERROR - " + e);
            //e.printStackTrace();
        }
    }


    class Camarero extends Thread {

        public static List<Cliente> clientesCafeteria = new ArrayList<>();
        public static List<Cliente> clientesAtendidos = new ArrayList<>();
        private String nombre;
        private static int hiloTerminado = 0;

        public Camarero(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public static void saludarCliente(Cliente cliente) {
            clientesCafeteria.add(cliente);
        }

        public void prepararCafe(Cliente cliente) {
            long tiempoPreparacionCafe = (long)(Math.random() * 4000) + 4000; // de 4 a 8 segundos

            cambiarColor(this, "azulPuntos");
            cambiarColor(cliente, "azulPuntos");

            try {
                System.out.println(cliente.getNombre() + " << Camarero PREPARA café...");
                join(tiempoPreparacionCafe);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            cambiarColor(this, "azul");

            if (clientesAtendidos.contains(cliente)) {
                System.out.println(cliente.getNombre() + " << Camarero SIRVE café... " + (tiempoPreparacionCafe / 1000) + "s");
                cambiarColor(cliente, "verde");
                cliente.setFueAtendido(true);
            } else {
                cambiarColor(cliente, "rojo");
                System.out.println("Camarero DESECHA café de " + cliente.getNombre() + "... " + (tiempoPreparacionCafe / 1000) + "s");
            }
        }

        @Override
        public void run() {
            long tiempoReaccionCamarero = (long)(Math.random() * 500) + 1000; // de 1 a 1,5 segundos

            do { // Este bucle determina la jornada laboral de los camareros, TERMINA CUANDO NO HAY CLIENTES
                do { // Este bucle activa la BÚSQUEDA DE CLIENTES de los camareros
                    try {
                        Thread.sleep(tiempoReaccionCamarero);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (clientesCafeteria.isEmpty());

                Cliente cliente = clientesCafeteria.getFirst();
                clientesCafeteria.remove(cliente);
                clientesAtendidos.add(cliente);
                prepararCafe(cliente);

            } while (true); // termina cuando cerremos programa o pulsemos botón finalizar
/*
            hiloTerminado++;

            if (hiloTerminado == 2) {
                System.out.println("FIN");
                lbFin.setVisible(true);
            }
            */
        }

    }

    class Cliente extends Thread {

        private String nombre;
        private int tiempoEspera;
        private boolean fueAtendido;


        public Cliente(String nombre, int tiempoEspera) {
            this.nombre = nombre;
            this.tiempoEspera = tiempoEspera * 1000;
            this.fueAtendido = false;
        }


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getTiempoEspera() {
            return tiempoEspera;
        }

        public void setTiempoEspera(int tiempoEspera) {
            this.tiempoEspera = tiempoEspera;
        }

        public boolean isFueAtendido() {
            return fueAtendido;
        }

        public void setFueAtendido(boolean fueAtendido) {
            this.fueAtendido = fueAtendido;
        }

        @Override
        public void run() {

            try {
                Camarero.saludarCliente(this);
                this.join(getTiempoEspera());

                if (this.fueAtendido) {
                    System.out.println(this.getNombre() + " se marcha. SÍ le atendieron (" + (this.getTiempoEspera() / 1000) + "s dentro)");
                    cambiarColor(this, "verde");
                } else {
                    Camarero.clientesCafeteria.remove(this);
                    Camarero.clientesAtendidos.remove(this);
                    cambiarColor(this, "rojo");
                    System.out.println(this.getNombre() + " se marcha. NO le atendieron (" + (this.getTiempoEspera() / 1000) + "s dentro)");
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
