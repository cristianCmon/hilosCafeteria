import java.util.ArrayList;
import java.util.List;

public class Camarero extends Thread {

    private static List<Cliente> clientesCafeteria = new ArrayList<>();


    public Camarero() {
//        try {
//            join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }


    public static void saludarCliente(Cliente cliente, String mensaje) {
        System.out.println(mensaje + " // El camarero le saluda");
        clientesCafeteria.add(cliente);
        prepararCafe(cliente);
    }

    public static void prepararCafe(Cliente cliente) {
        long tiempoPreparacionCafe = (long)(Math.random() * 5000) + 10000; // de 10 a 15 segundos

        try {
            System.out.println("atendiendo...");
            Thread.sleep(tiempoPreparacionCafe);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("café servido a " + cliente.getNombre());
        cliente.setFueAtendido(true);
        clientesCafeteria.remove(cliente);

        System.out.println(">> " + cliente.getNombre() + " toma el café");
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } while (clientesCafeteria.isEmpty());

        prepararCafe(clientesCafeteria.getFirst());
//        try {
//            join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //try {
            //System.out.println("atendiendo...");
            //Thread.sleep();
//            if (!clientesCafeteria.isEmpty() && clientesCafeteria != null) {
//                prepararCafe(clientesCafeteria.getFirst());
//                System.out.println("hola");
//            }
//                System.out.println("hola");
//                prepararCafe(clientesCafeteria.getFirst());
//        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }
}
