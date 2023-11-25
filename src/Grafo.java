
//Programa de Java que implementa Dijkstra usando cola de prioridad

import java.io.File;
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
        // Esto se hace ya que al principio no se ha explorado ninguna ruta y no se conoce la longitud mínima de ninguna ruta.

        // Se inicializa cola con el nodo de origen y su costo en 0 (no tiene costo a si mismo). Indica el nodo de partida.
        colaPrioridad.add(new Nodo(origen, 0));

        distancias[origen] = 0;

        while (visitados.size() != V) { // Mientras la lista de visitados no esté vacía

            if (colaPrioridad.isEmpty())
                return;

            // Se extrae con la mínima distancia 'u' de la cola.
            int u = colaPrioridad.remove().getNodo();

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
            if (!visitados.contains(v.getNodo())) {
                edgeDistance = v.getCosto();
                newDistance = distancias[u] + edgeDistance;

                // Si el nuevo costo en menor al actual.
                if (newDistance < distancias[v.getNodo()])
                    distancias[v.getNodo()] = newDistance;

                // Reingresar el nodo con su costo actualizado.
                colaPrioridad.add(new Nodo(v.getNodo(), distancias[v.getNodo()]));
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

        try{
            File doc = new File("C:\\Users\\54345\\OneDrive\\Escritorio\\Facu\\2do_Cuatri\\Programacion3\\TPO_Programacion3\\rutas.txt");
            Scanner obj = new Scanner(doc);
            while (obj.hasNextLine()){
                String data = obj.nextLine();
                String[] dataSplit = data.split(",");
                int v1 = Integer.parseInt(dataSplit[0]);
                int v2 = Integer.parseInt(dataSplit[1]);
                int costo = Integer.parseInt(dataSplit[2]);
                adj.get(v1).add(new Nodo(v2, costo));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return adj;
    }
    public void agregarCostosAlPuerto(List<List<Integer>> caminosACentros){
        try{
            File doc = new File("C:\\Users\\54345\\OneDrive\\Escritorio\\Facu\\2do_Cuatri\\Programacion3\\TPO_Programacion3\\clientesYCentros.txt");
            Scanner obj = new Scanner(doc);
            int lineaActual = 0; // Inicializa el contador de líneas

            while (obj.hasNextLine() && lineaActual<=7){
                String data = obj.nextLine();
                String[] dataSplit = data.split(",");
                int costoExtra = Integer.parseInt(dataSplit[1]);

                List<Integer> filaActual = caminosACentros.get(lineaActual);
                for(int j=0;j<filaActual.size();j++){
                    int valorActual = filaActual.get(j);
                    filaActual.set(j, valorActual + costoExtra);
                }
                lineaActual++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void agregarCostosVolumenCliente(List<List<Integer>> caminosACentros) {
        try {
            File doc = new File("C:\\Users\\54345\\OneDrive\\Escritorio\\Facu\\2do_Cuatri\\Programacion3\\TPO_Programacion3\\clientesYCentros.txt");
            Scanner obj = new Scanner(doc);
            int columna=0;

            // Omitir las primeras 8 líneas
            for (int k = 0; k < 8; k++) {
                obj.nextLine();
            }

            while (obj.hasNextLine()) {
                String data = obj.nextLine();
                String[] dataSplit = data.split(",");
                int costoExtra = Integer.parseInt(dataSplit[1]);

                for (int fila = 0; fila < caminosACentros.size(); fila++) {
                    if (columna < caminosACentros.get(fila).size()) {
                        int valorActual = caminosACentros.get(fila).get(columna);
                        caminosACentros.get(fila).set(columna, valorActual * costoExtra);
                    }
                }
                columna++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

public List<Integer> costosDeOperacion(){
    try{
        List<Integer> lista = new ArrayList<>(8);
        File doc = new File("C:\\Users\\54345\\OneDrive\\Escritorio\\Facu\\2do_Cuatri\\Programacion3\\TPO_Programacion3\\clientesYCentros.txt");
        Scanner obj = new Scanner(doc);
        int lineaActual = 0; // Inicializa el contador de líneas

        while (obj.hasNextLine() && lineaActual<=7){
            String data = obj.nextLine();
            String[] dataSplit = data.split(",");
            int costoOperacion = Integer.parseInt(dataSplit[2]);
            lista.add(costoOperacion);
            lineaActual++;
        }
        return lista;
    }catch (Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return null;
    }
}

    public int[] getDistancias(){
        return this.distancias;
    }
}

