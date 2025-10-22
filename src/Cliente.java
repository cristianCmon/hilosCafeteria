public class Cliente {

    private String nombre;
    private int tiempoEspera;


    public Cliente(String nombre, int tiempoEspera) {
        this.nombre = nombre;
        this.tiempoEspera = tiempoEspera;
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
}
