package com.joincafeteria_interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label lbCamarero1, lbCamarero2, lbFin;

    private List<Cliente> clientes = new ArrayList<>();

    @FXML
    private void initialize() {
        mostrarModal();
        lbFin.setVisible(false);
        abrirCafeteria();
    }

    public void abrirCafeteria() {
        System.out.println("\n -- CAFETERÍA ABIERTA -- \n");

        Cliente cl1 = new Cliente("Eva", 30);
        Cliente cl2 = new Cliente("Santiago", 29);
        Cliente cl3 = new Cliente("Jose María", 35);
        Cliente cl4 = new Cliente("Amanda", 28);
        Cliente cl5 = new Cliente("Diego", 34);
        Cliente cl6 = new Cliente("Silvia", 26);
        Cliente cl7 = new Cliente("Ana", 27);
        Cliente cl8 = new Cliente("Adán", 25);

        clientes.add(cl1);
        clientes.add(cl2);
        clientes.add(cl3);
        clientes.add(cl4);
        clientes.add(cl5);
        clientes.add(cl6);
        clientes.add(cl7);
        clientes.add(cl8);

        Camarero cm1 = new Camarero("Roberto");
        Camarero cm2 = new Camarero("Teresa");
        cm1.start();
        cm2.start();

        irCafeteria();
    }

    public void irCafeteria() {
        for (Cliente c : clientes) {
            System.out.println(c.getNombre() + " >> ...");
            c.start();
        }

        int indiceRejilla = 0;

        for (Node node : gpClientes.getChildren()) {
            if (node instanceof Label) {
                Label etiquetaActual = (Label) node;
                etiquetaActual.setText(clientes.get(indiceRejilla).getNombre());
                indiceRejilla++;
            }
        }

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

            stage.setTitle("INFORMACIÓN");

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

        public static void saludarCliente(Cliente cliente, String mensaje) {
            System.out.println(mensaje);
            clientesCafeteria.add(cliente);
        }

        public void prepararCafe(Cliente cliente) {
            long tiempoPreparacionCafe = (long)(Math.random() * 5000) + 10000; // de 10 a 15 segundos

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

            } while (!clientesCafeteria.isEmpty());

            hiloTerminado++;

            if (hiloTerminado == 2) {
                System.out.println("FIN");
                lbFin.setVisible(true);
            }
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
            long tiempoIda = (int)(Math.random() * 10000) + 5000; // de 10 a 15 segundos

            try {
                Thread.sleep(tiempoIda);
                String mensaje = this.getNombre() + " >> Cafetería (" + (tiempoIda / 1000) + "s caminata)";
                cambiarColor(this, "amarillo");
                Camarero.saludarCliente(this, mensaje);
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





