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
        Cliente cl2 = new Cliente("Santiago", 40);
        Cliente cl3 = new Cliente("Jose María", 35);
        Cliente cl4 = new Cliente("Amanda", 50);
        Cliente cl5 = new Cliente("Diego", 45);
        Cliente cl6 = new Cliente("Silvia", 60);
        Cliente cl7 = new Cliente("Ana", 55);
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

//        cm1.prepararCafe(cl1);
//        cm1.prepararCafe(cl1);

        irCafeteria();
    }

    public static void irCafeteria() {
        for (Cliente c : clientes) {
            System.out.println(c.getNombre() + " >> ...");
            c.start();
        }
    }
}

/*
Escribe un programa en Java que simule el funcionamiento de una cafetería. Debes crear clases que representen a los
clientes y a los camareros de la cafetería, y utilizar threads para modelar la interacción entre ellos.
El programa debe seguir estas pautas:

Crea una clase Cliente con los siguientes atributos:
    nombre (String): El nombre del cliente.
    tiempoEspera (int): Tiempo que el cliente está dispuesto a esperar su café antes de irse si la cafetería está ocupada.

Crea una clase Camarero que represente a un camarero de la cafetería.
Cada camarero debe ser un thread separado. La clase Camarero debe tener un metodo prepararCafe que tome como argumento
un objeto Cliente y simule el tiempo que le lleva preparar el café para ese cliente.
Puedes usar Thread.sleep para simular el tiempo de preparación.


En la clase principal, crea varios objetos Cliente con diferentes nombres y tiempos de espera,
así como varios objetos Camarero.


Simula la llegada de los clientes a la cafetería como threads separados.

Cada cliente debe intentar pedir un café y esperar el tiempo especificado en tiempoEspera.Los camarero deben preparar
los cafés para los clientes en orden de llegada. Si un cliente se va antes de que su café esté listo,
el camarero debe continuar con el siguiente cliente. Cuando un cliente recibe su café, muestra un mensaje indicando
que ha recibido su pedido y cuánto tiempo tomó la preparación.

El programa debe continuar ejecutándose hasta que todos los clientes hayan sido atendidos.
*/