import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Cliente> clientes = new ArrayList<>();


    public static void main(String[] args) {

        abrirCafeteria();
    }


    public static void abrirCafeteria() {
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

        Camarero cm1 = new Camarero();
        Camarero cm2 = new Camarero();
        cm1.start();
        cm2.start();

        irCafeteria();
    }

    public static void irCafeteria() {
        for (Cliente c : clientes) {
            System.out.println(c.getNombre() + " >> ...");
            c.start();
        }
    }

}
