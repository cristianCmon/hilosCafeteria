import java.util.ArrayList;
import java.util.List;

public class Camarero extends Thread {

    public static List<Cliente> clientesCafeteria = new ArrayList<>();
    public static List<Cliente> clientesAtendidos = new ArrayList<>();

    public Camarero() {}


    public static void saludarCliente(Cliente cliente, String mensaje) {
        System.out.println(mensaje);
        clientesCafeteria.add(cliente);
    }

    public void prepararCafe(Cliente cliente) {
        long tiempoPreparacionCafe = (long)(Math.random() * 5000) + 10000; // de 10 a 15 segundos

        try {
            System.out.println(cliente.getNombre() + " << Camarero PREPARA café...");
            join(tiempoPreparacionCafe);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (clientesAtendidos.contains(cliente)) {
            System.out.println(cliente.getNombre() + " << Camarero SIRVE café... " + (tiempoPreparacionCafe / 1000) + "s");
            cliente.setFueAtendido(true);
        } else {
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
    }

}
