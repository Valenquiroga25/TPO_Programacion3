
//Programa de Java que implementa Dijkstra usando cola de prioridad

import java.util.*;

public class Grafo {

    private int distancias[]; // Arreglo que almacena distancia más cortas entre vértice de origen a cada uno de los demás vértices
    private Set<Integer> visitados; // Arreglo de vértices visitados.
    private PriorityQueue<Nodo> colaPrioridad; // Cola de prioridad para ver posibles nodos que nos den un costo menor.
    private int V; // Número de vertices.
    List<List<Nodo>> adj; // Lista de adyacencia para cada nodo. Cada elemento de la lista 'adj' es una lista que representa los nodos adyacentes de cada elemento.

    // Constructor
    public Grafo(int V) {
        this.V = V;
        distancias = new int[V];
        visitados = new HashSet<Integer>();
        colaPrioridad = new PriorityQueue<Nodo>(V, new Nodo());
    }

    // Algoritmo Dijkstra
    public void dijkstra(List<List<Nodo>> adj, int origen) { // Se le pasa la lista de adyacentes de cada nodo del grafo y el nodo de origen.
        this.adj = adj;

        for (int i = 0; i < V; i++)
            distancias[i] = Integer.MAX_VALUE; // Se inicializa un arreglo con distancias iniciales infinitas para todos los nodos.

        // Se inicializa cola con el nodo de origen y su costo en 0 (no tiene costo a si mismo)
        colaPrioridad.add(new Nodo(origen, 0));

        distancias[origen] = 0;

        while (visitados.size() != V) {

            if (colaPrioridad.isEmpty())
                return;

            // Se extrae con la mínima distancia 'u' de la cola.
            int u = colaPrioridad.remove().nodo;

            // Si visitados ya contiene 'u' (que ya se contempló ese nodo) se lo ignora.
            if (visitados.contains(u))
                continue;

            // Sino se lo agrega y se llama a la funcíon que analiza el costo desde 'u' a sus adyacentes.
            visitados.add(u);
            u_vecinos(u);
        }
    }

    // Método que procesa vecinos
    private void u_vecinos(int u) { // Se le pasa el nodo actual a procesar.

        int edgeDistance = -1; // Distancia que representa costo desde nodo de origen a nodo en proceso.
        int newDistance = -1; // Suma de el valor anterior y el costo desde el nodo en proceso hasta un nodo determinado.

        // La variable 'v' hace referencia a todos los vecinos de 'u'
        for (int i = 0; i < adj.get(u).size(); i++) {
            Nodo v = adj.get(u).get(i);

            // Si el nodo actual 'v' aún no fue procesado
            if (!visitados.contains(v.nodo)) {
                edgeDistance = v.costo;
                newDistance = distancias[u] + edgeDistance;

                // Si el nuevo costo en menor al actual.
                if (newDistance < distancias[v.nodo])
                    distancias[v.nodo] = newDistance;

                // Reingresar el nodo con su costo actualizado.
                colaPrioridad.add(new Nodo(v.nodo, distancias[v.nodo]));
            }
        }
    }

    // Main driver method
    public static void main(String arg[]) {

        int V = 5;
        int origen = 0;

        List<List<Nodo>> adj = new ArrayList<List<Nodo>>();

        // Se inicializa una lista de adyacencia vacía por nodo.
        for (int i = 0; i < V; i++) {
            List<Nodo> item = new ArrayList<Nodo>();
            adj.add(item);
        }

        // Se establecen conexiones entre nodos.
        adj.get(0).add(new Nodo(1, 9));
        adj.get(0).add(new Nodo(2, 6));
        adj.get(0).add(new Nodo(3, 1));
        adj.get(0).add(new Nodo(4, 3));

        adj.get(2).add(new Nodo(1, 2));
        adj.get(2).add(new Nodo(3, 4));

        adj.get(3).add(new Nodo(2, 1));

        // Se hace el Dijktra
        Grafo grafo = new Grafo(V);
        grafo.dijkstra(adj, origen);

        System.out.println("Camino más corto a cada nodo: ");

        for (int i = 0; i < grafo.distancias.length; i++)
            System.out.println(origen + " a " + i + " es " + grafo.distancias[i]);
    }
}

