public class Cliente extends Thread {

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
            Camarero.saludarCliente(this, mensaje);
            this.join(getTiempoEspera());

            if (this.fueAtendido) {
                System.out.println(this.getNombre() + " se marcha. SÍ le atendieron (" + (this.getTiempoEspera() / 1000) + "s dentro)");
            } else {
                Camarero.clientesCafeteria.remove(this);
                Camarero.clientesAtendidos.remove(this);
                System.out.println(this.getNombre() + " se marcha. NO le atendieron (" + (this.getTiempoEspera() / 1000) + "s dentro)");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
