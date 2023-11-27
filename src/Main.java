import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static List<Integer> construirCentros(List<List<Integer>> mapa, List<Integer> costosFijos){
        PriorityQueue<CO> cola = new PriorityQueue<>();
        List<Integer> x = new ArrayList<>(8);
        List<Integer> costosClientes  = new ArrayList<>();

        int centroAConstruir = 0;
        CO base = new CO(x,mapa,costosFijos,centroAConstruir);
        cola.add(base);

        CO centro = null;

        while(!cola.isEmpty()){
            //Seleccionamos el primer nodo de la cola
            centro = cola.poll();

            //
            if(centro.u > centro.c){

                List<Integer> x1 = new ArrayList<>(centro.x);
                x1.add(centro.centroAContruir,1);
                cola.add(new CO(x1,mapa,costosFijos,centroAConstruir+1));

                if(costosFijos.get(centro.centroAContruir)>centro.u){
                    List<Integer> x2 = new ArrayList<>(centro.x);
                    x2.add(centro.centroAContruir,-1);
                    cola.add(new CO(x2,mapa,costosFijos,centroAConstruir+1));
                }
            }
        }
        return centro.x;
    }

    public static void main(String[] args){
        int V = 58;
        List<List<Integer>> caminosACentros = new ArrayList<>(); // Lista que guarda los Dijkstra de cada cliente.

        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(V); // Lista que establece las conexiones entre nodos.

        for (int i=50; i < 58; i++){
            //System.out.println("\nCamino más corto al centro " + i + " cara cada cliente: ");

            grafo.dijkstra(conexiones, i); // Acá es donde se hace el Dikstra con cada cliente a cada centro. Lo que hace que cambien los valores de la lista 'distancias'.
            // El método Dijkstra inicializa la lista 'distancias' por cada llamada (en este caso se hace una llamada por fila) y le va asignando valores segun los caminos del nodo de origen que se le pasa.

            List<Integer> caminosPorCliente = new ArrayList<>(49); // Inicializamos la lista que guarda los Dijkstra de un cliente solo.

            for (int j = 0; j <= 49; j++) {
                caminosPorCliente.add(grafo.getDistancias()[j]); // Le asignamos los valores a la lista previmente creada.
                //System.out.println("Costo para cliente " + j + " es: " + grafo.getDistancias()[j]);
            }
            //System.out.println();
            caminosACentros.add(caminosPorCliente); // Se agrega a la lista de listas.
        }

        // Se suman los costos de transporte al puerto.
        grafo.agregarCostosAlPuerto(caminosACentros);

        // Se multiplican el volumen por cliente a cada elemento.
        grafo.agregarCostosVolumenCliente(caminosACentros);

        //Lista de costos fijos de centros.
        List<Integer> costosFijos = grafo.costosDeOperacion();

        // En esta impresión cada fila es el centro y cada columna el cliente que llega.
        System.out.println();
        for(int i=0; i < caminosACentros.size(); i++) {
            for (int j = 0; j < caminosACentros.get(0).size(); j++){
                System.out.print(caminosACentros.get(i).get(j) + " ");
            }
            System.out.println();
        }

        System.out.println();
        for (int i=0;i<costosFijos.size();i++)
            System.out.print(costosFijos.get(i) + " ");

        List<Integer> construccion = construirCentros(caminosACentros,costosFijos);

        System.out.println("Centros a construir: ");
        for(int i=0;i<construccion.size();i++)
            System.out.print(construccion.get(i) + " ");
    }
}
