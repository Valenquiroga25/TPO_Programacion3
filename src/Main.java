import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static List<Integer> construirCentros(List<List<Nodo>> mapa){
        PriorityQueue<CO> cola = new PriorityQueue<>();
        List<Integer> x = new ArrayList<>();
        List<Integer> costosClientes  = new ArrayList<>();

        for (int i = 0; i < mapa.size(); i++) {
            x.add(0);
        }

        CO base = new CO(x,mapa,0);
        CO centro;
        cola.add(base);

        while(!cola.isEmpty()){
            centro = cola.poll();

        }
        return null;
    }

    public static void main(String[] args){
        int V = 58;
        List<List<Integer>> caminosACentros = new ArrayList<>(); // Lista que guarda los Dijkstra de cada cliente.

        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(V); // Lista que establece las conexiones entre nodos.

        for (int i=50; i < 58; i++){
            //System.out.println("\nCamino más corto al centro " + i + " cara cada cliente: ");

            grafo.dijkstra(conexiones, i); // Acá es donde se hace el Dikstra con cada cliente a cada centro. Lo que hace que cambien los valores de la lista 'distancias'.

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

        //Lista de costos de operacion
        List<Integer> costosOperacion = grafo.costosDeOperacion();

        // En esta impresión cada fila es el centro y cada columna el cliente que llega.
        System.out.println();
        for(int i=0; i < caminosACentros.size(); i++) {
            for (int j = 0; j < caminosACentros.get(0).size(); j++){
                System.out.print(caminosACentros.get(i).get(j) + " ");
            }
            System.out.println();
        }

        System.out.println();
        for (int i=0;i<costosOperacion.size();i++)
            System.out.print(costosOperacion.get(i) + " ");
    }
}
