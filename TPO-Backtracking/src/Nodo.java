import java.util.Comparator;

public class Nodo implements Comparator<Nodo>{

    public int nodo;
    public int costo;

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
}
