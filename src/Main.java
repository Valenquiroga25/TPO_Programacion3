import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int V = 58;
        int origen = 0;

        // Se hace el Dijktra
        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(V);
        grafo.dijkstra(conexiones, origen);

        System.out.println("\nCamino más corto para el cliente " + origen + " a cada nodo: ");

        for (int i = 50; i < grafo.getDistancias().length; i++)
            System.out.println("Costo al centro " + i + " es: " + grafo.getDistancias()[i]);
    }
}
