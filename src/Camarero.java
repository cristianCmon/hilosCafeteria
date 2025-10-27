import java.util.ArrayList;
import java.util.List;

public class Camarero extends Thread {

    public static List<Cliente> clientesCafeteria = new ArrayList<>();
    //public static boolean

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
    }

    public void prepararCafe(Cliente cliente) {
        long tiempoPreparacionCafe = (long)(Math.random() * 5000) + 10000; // de 10 a 15 segundos

        try {
            System.out.println("atendiendo...");
            join(tiempoPreparacionCafe);

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
        System.out.println("Thread started:::"+Thread.currentThread().getName());
        long comienzoJornada = System.currentTimeMillis();
        long finJornada = comienzoJornada + 60000;
        long tiempoReaccionCamarero = (long)(Math.random() * 500) + 1000; // de 1 a 1,5 segundos

        do {
            do {

                try {
                    Thread.sleep(tiempoReaccionCamarero);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            // TODO ESTA CONDICIÓN NUNCA ACABA
            } while (clientesCafeteria.isEmpty());

            //System.out.println("alguien entra... hay que atenderlo");

            // TODO NUNCA TERMINA, METER EN BUCLE
//            try {
                Cliente cliente = clientesCafeteria.getFirst();
                clientesCafeteria.remove(cliente);
                prepararCafe(cliente);
//                this.join();
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            System.out.println("Thread ended:::"+Thread.currentThread().getName());

        } while(System.currentTimeMillis() < finJornada);

        System.out.println("bucle terminado");

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
