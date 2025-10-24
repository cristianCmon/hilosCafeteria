import java.util.ArrayList;
import java.util.List;

public class Camarero extends Thread {

    private static List<Cliente> clientesCafeteria = new ArrayList<>();


    public Camarero() {}


    public static void saludarCliente(Cliente cliente, String mensaje) {
        System.out.println(mensaje + " // El camarero le saluda");
        clientesCafeteria.add(cliente);
        prepararCafe(cliente);
    }

    public static void prepararCafe(Cliente cliente) {
        System.out.println(">> " + cliente.getNombre() + " pide un café");
    }

    @Override
    public void run() {
        long tiempoPreparacionCafe = (long)(Math.random() * 5000) + 10000; // de 10 a 15 segundos

        try {
            Thread.sleep(tiempoPreparacionCafe);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
