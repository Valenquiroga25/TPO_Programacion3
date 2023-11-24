
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
    }

    // Algoritmo Dijkstra
    public void dijkstra(List<List<Nodo>> adj, int origen) { // Se le pasa la lista de adyacentes de cada nodo del grafo y el nodo de origen.
        this.adj = adj;
        colaPrioridad = new PriorityQueue<Nodo>(V, new Nodo()); // Se crea la instancia dentro del Dijkstra para que no se trabaje con la lista del primer nodo (sino los costos del primer cliente dan bien pero a partir del segundo no).
        visitados = new HashSet<Integer>(); // Se crea la instancia dentro del Dijkstra para que no se trabaje con la lista del primer nodo (sino los costos del primer cliente dan bien pero a partir del segundo no).

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
    public List<List<Nodo>> establecerConexiones(int numeroNodos) {

        this.adj = new ArrayList<List<Nodo>>();

        // Se inicializa una lista de adyacencia vacía por nodo.
        for (int i = 0; i < numeroNodos; i++) {
            List<Nodo> item = new ArrayList<Nodo>();
            adj.add(item);
        }

        // Se establecen conexiones entre nodos.
        adj.get(1).add(new Nodo(2, 10));
        adj.get(2).add(new Nodo(1, 10));
        adj.get(2).add(new Nodo(3, 10));
        adj.get(3).add(new Nodo(2, 10));
        adj.get(1).add(new Nodo(28, 8));
        adj.get(28).add(new Nodo(1, 8));
        adj.get(28).add(new Nodo(50, 11));
        adj.get(50).add(new Nodo(28, 11));
        adj.get(1).add(new Nodo(3, 12));
        adj.get(3).add(new Nodo(1, 12));
        adj.get(3).add(new Nodo(50, 10));
        adj.get(50).add(new Nodo(3, 10));
        adj.get(3).add(new Nodo(4, 8));
        adj.get(4).add(new Nodo(3, 8));
        adj.get(51).add(new Nodo(4, 9));
        adj.get(4).add(new Nodo(51, 9));
        adj.get(3).add(new Nodo(5, 4));
        adj.get(5).add(new Nodo(3, 4));
        adj.get(5).add(new Nodo(51, 3));
        adj.get(51).add(new Nodo(5, 3));
        adj.get(6).add(new Nodo(5, 9));
        adj.get(5).add(new Nodo(6, 9));
        adj.get(6).add(new Nodo(7, 8));
        adj.get(7).add(new Nodo(6, 8));
        adj.get(7).add(new Nodo(8, 4));
        adj.get(8).add(new Nodo(7, 4));
        adj.get(8).add(new Nodo(51, 10));
        adj.get(51).add(new Nodo(8, 10));
        adj.get(8).add(new Nodo(10, 12));
        adj.get(10).add(new Nodo(8, 12));
        adj.get(9).add(new Nodo(10, 9));
        adj.get(10).add(new Nodo(9, 9));
        adj.get(10).add(new Nodo(11, 6));
        adj.get(11).add(new Nodo(10, 6));
        adj.get(11).add(new Nodo(12, 5));
        adj.get(12).add(new Nodo(11, 5));
        adj.get(12).add(new Nodo(13, 6));
        adj.get(13).add(new Nodo(12, 6));
        adj.get(11).add(new Nodo(14, 8));
        adj.get(14).add(new Nodo(11, 8));
        adj.get(14).add(new Nodo(15, 8));
        adj.get(15).add(new Nodo(14, 8));
        adj.get(15).add(new Nodo(16, 9));
        adj.get(16).add(new Nodo(15, 9));
        adj.get(15).add(new Nodo(20, 6));
        adj.get(20).add(new Nodo(15, 6));
        adj.get(13).add(new Nodo(20, 4));
        adj.get(20).add(new Nodo(13, 4));
        adj.get(20).add(new Nodo(18, 2));
        adj.get(18).add(new Nodo(20, 2));
        adj.get(17).add(new Nodo(18, 6));
        adj.get(18).add(new Nodo(17, 6));
        adj.get(18).add(new Nodo(19, 7));
        adj.get(19).add(new Nodo(18, 7));
        adj.get(19).add(new Nodo(23, 6));
        adj.get(23).add(new Nodo(19, 6));
        adj.get(18).add(new Nodo(55, 9));
        adj.get(55).add(new Nodo(18, 9));
        adj.get(20).add(new Nodo(55, 7));
        adj.get(55).add(new Nodo(20, 7));
        adj.get(20).add(new Nodo(21, 6));
        adj.get(21).add(new Nodo(20, 6));
        adj.get(21).add(new Nodo(56, 9));
        adj.get(56).add(new Nodo(21, 9));
        adj.get(22).add(new Nodo(56, 7));
        adj.get(56).add(new Nodo(22, 7));
        adj.get(22).add(new Nodo(23, 5));
        adj.get(23).add(new Nodo(22, 5));
        adj.get(23).add(new Nodo(55, 4));
        adj.get(55).add(new Nodo(23, 4));
        adj.get(23).add(new Nodo(24, 4));
        adj.get(24).add(new Nodo(23, 4));
        adj.get(24).add(new Nodo(25, 5));
        adj.get(25).add(new Nodo(24, 5));
        adj.get(24).add(new Nodo(26, 6));
        adj.get(26).add(new Nodo(24, 6));
        adj.get(24).add(new Nodo(27, 7));
        adj.get(27).add(new Nodo(24, 7));
        adj.get(27).add(new Nodo(57, 7));
        adj.get(57).add(new Nodo(27, 7));
        adj.get(26).add(new Nodo(57, 6));
        adj.get(57).add(new Nodo(26, 6));
        adj.get(29).add(new Nodo(31, 5));
        adj.get(31).add(new Nodo(29, 5));
        adj.get(31).add(new Nodo(32, 7));
        adj.get(32).add(new Nodo(31, 7));
        adj.get(31).add(new Nodo(53, 4));
        adj.get(53).add(new Nodo(31, 4));
        adj.get(50).add(new Nodo(30, 5));
        adj.get(30).add(new Nodo(50, 5));
        adj.get(30).add(new Nodo(53, 7));
        adj.get(53).add(new Nodo(30, 7));
        adj.get(32).add(new Nodo(33, 8));
        adj.get(33).add(new Nodo(32, 8));
        adj.get(33).add(new Nodo(52, 4));
        adj.get(52).add(new Nodo(33, 4));
        adj.get(31).add(new Nodo(52, 8));
        adj.get(52).add(new Nodo(31, 8));
        adj.get(52).add(new Nodo(34, 8));
        adj.get(34).add(new Nodo(52, 8));
        adj.get(34).add(new Nodo(35, 6));
        adj.get(35).add(new Nodo(34, 6));
        adj.get(35).add(new Nodo(36, 7));
        adj.get(36).add(new Nodo(35, 7));
        adj.get(34).add(new Nodo(39, 4));
        adj.get(39).add(new Nodo(34, 4));
        adj.get(39).add(new Nodo(38, 3));
        adj.get(38).add(new Nodo(39, 3));
        adj.get(38).add(new Nodo(37, 9));
        adj.get(37).add(new Nodo(38, 9));
        adj.get(39).add(new Nodo(40, 8));
        adj.get(40).add(new Nodo(39, 8));
        adj.get(40).add(new Nodo(41, 7));
        adj.get(41).add(new Nodo(40, 7));
        adj.get(52).add(new Nodo(41, 6));
        adj.get(41).add(new Nodo(52, 6));
        adj.get(41).add(new Nodo(53, 9));
        adj.get(53).add(new Nodo(41, 9));
        adj.get(41).add(new Nodo(13, 7));
        adj.get(13).add(new Nodo(41, 7));
        adj.get(39).add(new Nodo(43, 5));
        adj.get(43).add(new Nodo(39, 5));
        adj.get(39).add(new Nodo(54, 4));
        adj.get(54).add(new Nodo(39, 4));
        adj.get(40).add(new Nodo(54, 5));
        adj.get(54).add(new Nodo(40, 5));
        adj.get(41).add(new Nodo(49, 6));
        adj.get(49).add(new Nodo(41, 6));
        adj.get(43).add(new Nodo(42, 5));
        adj.get(42).add(new Nodo(43, 5));
        adj.get(43).add(new Nodo(54, 4));
        adj.get(54).add(new Nodo(43, 4));
        adj.get(43).add(new Nodo(44, 9));
        adj.get(44).add(new Nodo(43, 9));
        adj.get(44).add(new Nodo(45, 7));
        adj.get(45).add(new Nodo(44, 7));
        adj.get(45).add(new Nodo(54, 6));
        adj.get(54).add(new Nodo(45, 6));
        adj.get(45).add(new Nodo(46, 9));
        adj.get(46).add(new Nodo(45, 9));
        adj.get(45).add(new Nodo(47, 7));
        adj.get(47).add(new Nodo(45, 7));
        adj.get(47).add(new Nodo(48, 8));
        adj.get(48).add(new Nodo(47, 8));
        adj.get(48).add(new Nodo(49, 9));
        adj.get(49).add(new Nodo(48, 9));
        adj.get(49).add(new Nodo(56, 3));
        adj.get(56).add(new Nodo(49, 3));
        adj.get(48).add(new Nodo(0, 6));
        adj.get(0).add(new Nodo(48, 6));
        adj.get(0).add(new Nodo(56, 6));
        adj.get(56).add(new Nodo(0, 6));
        adj.get(0).add(new Nodo(57, 4));
        adj.get(57).add(new Nodo(0, 4));
        adj.get(29).add(new Nodo(50, 3));
        adj.get(50).add(new Nodo(29, 3));

        return adj;
    }

    public int[] getDistancias(){
        return this.distancias;
    }
}

