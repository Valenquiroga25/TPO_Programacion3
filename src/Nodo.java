import java.util.Comparator;

public class Nodo implements Comparator<Nodo>{

    private int nodo;
    private int costo;
    private int costoAlPuerto = Integer.MAX_VALUE;

    // Constructor 1
    public Nodo() {}

    // Constructor 2
    public Nodo(int nodo, int costo) {
        this.nodo = nodo;
        this.costo = costo;
    }

    // Método de comparación de nodos para ordenar cola de prioridad.
    @Override
    public int compare(Nodo nodo1, Nodo nodo2) {

        if (nodo1.costo < nodo2.costo)
            return -1;

        if (nodo1.costo > nodo2.costo)
            return 1;

        return 0;
    }

    public int getNodo(){
        return this.nodo;
    }

    public int getCosto(){
        return this.costo;
    }

    public int getCostoAlPuerto() {
        return this.costoAlPuerto;
    }

    public void setCostoAlPuerto(int costoAlPuerto) {
        this.costoAlPuerto = costoAlPuerto;
    }
}

