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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.Frequency;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrincipalController {
    @FXML
    private GridPane gpClientes, gpCafetera;

    @FXML
    private Button btnPasarCliente, btnFinalizarEjecucion;

    @FXML
    private Image cafe;
    @FXML
    private ImageView img0, img1, img2, img3;
    List<ImageView> imagenes = new ArrayList<>();

    @FXML
    private Label lbCamarero1, lbCamarero2, lbBarista, lbFin;

    private int indiceCliente = 0, indiceRejilla = 0;

    @FXML
    private void initialize() {
        cafe = new Image(getClass().getResourceAsStream("/imagenes/coffee50x50.png"));
        imagenes.add(img0);
        imagenes.add(img1);
        imagenes.add(img2);
        imagenes.add(img3);

        for (ImageView img : imagenes) {
            img.setImage(cafe);
            img.setVisible(false);
        }

//        imagenes.get(2).setVisible(false);
//        imagenes.get(1).setVisible(false);

//        img0.setImage(cafe);
//        img1.setImage(cafe);
//        img2.setImage(cafe);
//        img3.setImage(cafe);
//        img3.setVisible(false);
//        img0 = new ImageView(getClass().getResourceAsStream("/imagenes/coffee50x50.png").toString());

        mostrarModal();
        lbFin.setVisible(false);
        abrirCafeteria();
    }

    public void abrirCafeteria() {
        System.out.println("\n -- CAFETERÍA ABIERTA -- \n");

        Cafetera cafetera = new Cafetera();
        Barista barista = new Barista(cafetera);

        barista.start();

        Camarero cm1 = new Camarero("Roberto", cafetera);
        Camarero cm2 = new Camarero("Teresa", cafetera);

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
                case "naranjaPuntos":
                    lbBarista.setStyle("-fx-border-color: #E28A78; -fx-border-width: 2; -fx-border-style: dotted;");
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
                case "naranjaPuntos":
                    lbBarista.setStyle("-fx-border-color: #E28A78; -fx-border-width: 2; -fx-border-style: dotted;");
                    break;
            }
        }
    }

    public void cambiarColor(Barista barista, String color) {
        switch (color) {
            case "naranja":
                lbBarista.setStyle("-fx-border-color: #E28A78; -fx-border-width: 2;");
                break;
            case "naranjaPuntos":
                lbBarista.setStyle("-fx-border-color: #E28A78; -fx-border-width: 2; -fx-border-style: dotted;");
                break;
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

    class Barista extends Thread {
        private Cafetera cafetera;


        public Barista() {};

        public Barista(Cafetera c) {
            this.cafetera = c;
        }


        private int getPosicionCafe() {
            // TODO OJO -1
            int posicion = -1;

            for (int i = 0; i < imagenes.size(); i++) {
                if (!imagenes.get(i).isVisible()) {
                    posicion = i;
                    break;
                }
            }

            return posicion;
        }

        @Override
        public void run() {
            long tiempoPreparacionCafe = (long)(Math.random() * 3000) + 2000; // de 3 a 5 segundos
            int posicionCafe;

            do {
                cambiarColor(this, "naranjaPuntos");
    //            cambiarColor(cliente, "azulPuntos");

                try {
                    System.out.println("Barista PREPARA café...");
                    join(tiempoPreparacionCafe);
                    posicionCafe = getPosicionCafe();

//                    if (posicionCafe != -1) {
                        cafetera.put(posicionCafe);
//                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Barista PREPARÓ café...");
//                cambiarColor(this, "naranja");
            } while (true);
        }
    }

    class Cafetera {
        int cafe;
        int cantidad = 0;
        private boolean disponible = false;

        public synchronized int get(Camarero camarero) {
            while (!disponible) {
                try {
                    System.out.println("Camarero esperando cafetera...");
                    cambiarColor(camarero, "naranjaPuntos");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (cantidad > 0) {

            cafe = cogerPrimerCafeDisponible();
            }

            if (cafe != -1) {
//                cambiarColor(new Camarero(), "naranja");
                System.out.println("Sirviendo café...");
//                imagenes.get(cafe).setVisible(false);
                disponible = estaCafeteraLLena();
    //            if (cafe != -1) {
    //                imagenes.get(cafe).setVisible(false);
    //                disponible = estaCafeteraLLena();
    //            }
                cantidad--;
                notifyAll();
            }

            return cafe;
        }

        public synchronized void put(int valor) {
            while (disponible) {
                try {
                    System.out.println("cafetera llena, barista en espera...");
                    cambiarColor(new Barista(), "naranja");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cafe = valor;

            if (cafe != -1) {
                // pintar cafe en gridPane
                cantidad++;
                imagenes.get(cafe).setVisible(true);


                disponible = estaCafeteraLLena();
                notifyAll();
            }
        }

        private boolean estaCafeteraLLena() {
//            boolean estaLLena = true;

//            for (ImageView img : imagenes) {
//                if (!img.isVisible()) {
//                    estaLLena = false;
//                    break;
//                }
//            }

            return cantidad >= 4;
        }

        private int cogerPrimerCafeDisponible() {
            int cafe = -1;

            for (int i = 0; i < imagenes.size(); i++) {
                if (imagenes.get(i).isVisible()) {
                    cafe = i;
                    imagenes.get(i).setVisible(false);
                    break;
                }
            }

            return cafe;
        }
    }

    class Camarero extends Thread {

        public static List<Cliente> clientesCafeteria = new ArrayList<>();
        public static List<Cliente> clientesAtendidos = new ArrayList<>();
        private String nombre;
        private Cafetera cafetera;


        public Camarero() {};

        public Camarero(String nombre) {
            this.nombre = nombre;
        }

        public Camarero(String nombre, Cafetera cafetera) {
            this.nombre = nombre;
            this.cafetera = cafetera;
        }

        public String getNombre() {
            return nombre;
        }

        public static void saludarCliente(Cliente cliente) {
            clientesCafeteria.add(cliente);
        }

        public void recogerCafe(Cliente cliente) {
            int posicionCafe;
//            long tiempoPreparacionCafe = (long)(Math.random() * 4000) + 4000; // de 4 a 8 segundos
            // TODO NARANJA MIENTRAS BUSCA CAFÉ
            cambiarColor(this, "azulPuntos");
            posicionCafe = cafetera.get(this);
//            imagenes.get(posicionCafe).setVisible(false);


            cambiarColor(cliente, "azulPuntos");

//            try {
//                System.out.println(cliente.getNombre() + " << Camarero PREPARA café...");
//                join(tiempoPreparacionCafe);
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            cambiarColor(this, "azul");

            if (clientesAtendidos.contains(cliente)) {
//                System.out.println(cliente.getNombre() + " << Camarero SIRVE café... " + (tiempoPreparacionCafe / 1000) + "s");
                System.out.println(cliente.getNombre() + " << Camarero SIRVE café... ");

                cambiarColor(cliente, "verde");
                cliente.setFueAtendido(true);
            } else {
                cambiarColor(cliente, "rojo");
//                System.out.println("Camarero DESECHA café de " + cliente.getNombre() + "... " + (tiempoPreparacionCafe / 1000) + "s");
                System.out.println("Camarero DESECHA café de " + cliente.getNombre() + "...");

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
                recogerCafe(cliente);

            } while (true); // termina cuando cerremos programa o pulsemos botón finalizar
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
