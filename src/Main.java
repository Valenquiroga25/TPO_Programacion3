import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int V = 58;
        List<List<Integer>> caminosACentros = new ArrayList<>();

        // Se hace el Dijktra (Consultar por qué hay que crear un grafo por Dijkstra)
        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(V);

        for (int i=0 ;i <= 49 ;i++){
            System.out.println("\nCamino más corto para el cliente " + i + " a cada nodo: ");
            grafo.dijkstra(conexiones, i);
            List<Integer> caminosPorCliente = new ArrayList<>(8);

            for (int j = 50; j < grafo.getDistancias().length; j++) {
                caminosPorCliente.add(grafo.getDistancias()[j]);
                System.out.println("Costo al centro " + j + " es: " + grafo.getDistancias()[j]);
            }
            System.out.println();
            caminosACentros.add(caminosPorCliente);
        }

        // En esta impresión cada fila es el cliente y cada columna el centro al que se dirige.
        for(int i=0;i<caminosACentros.size();i++) {
            for (int j = 0; j < 8; j++){
                System.out.print(caminosACentros.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}
