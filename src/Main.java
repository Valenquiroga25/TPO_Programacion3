import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class Main {
    public static List<Integer> construirCentros(List<List<Integer>> mapa, List<Integer> costosFijos){
        PriorityQueue<CO> cola = new PriorityQueue<>();
        List<Integer> x = new ArrayList<>();

        for (int i = 0; i < costosFijos.size(); i++) {
            x.add(i,0);
        }

        CO base = new CO(x,mapa,costosFijos,0);
        cola.add(base);
        CO centro = null;

        while(!cola.isEmpty()) {

            //Seleccionamos el primer nodo de la cola
                centro = cola.poll();

            if (centro.centroAContruir < costosFijos.size()) {
                if (centro.reduccionMinima >= costosFijos.get(centro.centroAContruir)) { // Primer poda. Si la reducción minima es mayor al costo fijo del centro se decide construir sin comparación.
                    List<Integer> x1 = new ArrayList<>(centro.x);
                    x1.set(centro.centroAContruir, 1);
                    if(centro.centroAContruir != costosFijos.size()-1)
                        cola.add(new CO(x1, mapa, costosFijos, centro.centroAContruir + 1));
                    else{
                        CO centroFinal = new CO(x1, mapa, costosFijos, centro.centroAContruir); // Si el centro en analisis es el último el número de centro no se hace +1 porque sino se rompe por OutOfIndex.
                        if (centroFinal.c == centroFinal.u)
                            return centroFinal.x;
                    }

                } else if(centro.reduccionMaxima < costosFijos.get(centro.centroAContruir) ){ // Segundo poda. Si la reducción máxima es menor al costo fijo del centro se decide no construir sin comparar.
                    List<Integer> x2 = new ArrayList<>(centro.x);
                    x2.set(centro.centroAContruir, -1);

                    if(centro.centroAContruir != costosFijos.size()-1)
                        cola.add(new CO(x2, mapa, costosFijos, centro.centroAContruir + 1));
                    else{
                        CO centroFinal = new CO(x2, mapa, costosFijos, centro.centroAContruir); // Si el centro en analisis es el último el número de centro no se hace +1 porque sino se rompe por OutOfIndex.
                        if (centroFinal.c == centroFinal.u)
                            return centroFinal.x;
                    }

                }else{ // En este else hay q ver las dos posibilidades y añadir ambas a la cola.

                    List<Integer> x3 = new ArrayList<>(centro.x); // Lista con situación de construido
                    x3.set(centro.centroAContruir, 1);

                    List<Integer> x4 = new ArrayList<>(centro.x); // Lista con situacion de no construido.
                    x4.set(centro.centroAContruir, -1);

                    if(centro.centroAContruir != costosFijos.size()-1) {
                        cola.add(new CO(x3, mapa, costosFijos, centro.centroAContruir + 1));
                        cola.add(new CO(x4, mapa, costosFijos, centro.centroAContruir + 1));
                    }else{
                        CO c3 = new CO(x3, mapa, costosFijos, centro.centroAContruir); // Si el centro en analisis es el último el número de centro no se hace +1 porque sino se rompe por OutOfIndex.
                        CO c4 = new CO(x4, mapa, costosFijos, centro.centroAContruir);

                        if (c3.c == c3.u)
                            return c3.x;
                        else if (c4.c == c4.u)
                            return c4.x;
                    }
                }
            }
        }

        return centro.x;
    }

    public static void impresionResultados(List<List<Integer>> mapa, List<Integer> centrosFinales, List<Integer> costosFijos){
        int columnas = mapa.get(0).size();
        List<Integer> centros = new ArrayList<>(); // Lista con centros que deben construirse.
        List<Integer> valoresMinimos = new ArrayList<>(); // Lista para saber el costo mínimo de cada cliente a cada centro construido.
        List<Integer> costoMinimoParaCadaCliente = new ArrayList<>(); // Lista para almacenar indice de fila con menor costo (centro conveniente)
        int costoFinal = 0;

        // Se almacenan los centros que se deben construir.
        for(int i=0;i<centrosFinales.size();i++){
            if (centrosFinales.get(i) == 1)
                centros.add(i + 50);
        }


        // Inicializamos lista con valores máximos
        for(int k=0;k < columnas;k++){
            valoresMinimos.add(Integer.MAX_VALUE);
        }

        // Guardamos el valor minimo de cada columna (cliente) y cada fila == 1 (centro construido)
        for (int j=0;j < columnas;j++){
                for (int i = 0; i < centrosFinales.size(); i++) {
                    if (centrosFinales.get(i) == 1) {
                        if (mapa.get(i).get(j) < valoresMinimos.get(j)){
                            valoresMinimos.set(j,mapa.get(i).get(j));
                        }
                }
            }
        }

        // Recorremos de nuevo, si el elemento actual de la matriz es el menor de la columna, guardamos en la lista el indice (centro)

        for (int j=0;j < columnas;j++){
            for (int i = 0; i < centrosFinales.size(); i++) {
                if (centrosFinales.get(i) == 1) {
                    if (Objects.equals(mapa.get(i).get(j), valoresMinimos.get(j))){
                        costoMinimoParaCadaCliente.add(i + 50);
                        costoFinal+=mapa.get(i).get(j); // Sumamos los valores minimos de cada columna de los centros construidos
                    }
                }
            }
        }

        for(int k=0;k < costosFijos.size();k++) // Se suman los costos fijos de centros construidos al costo total.
            if (centrosFinales.get(k) == 1)
                costoFinal += costosFijos.get(k);

        System.out.println("\nLos centros que deben construirse son los siguientes: " + centros);
        System.out.println("\nCentro conveniente para cada cliente:");

        for(int k=0;k < columnas;k++)
            System.out.println("Cliente " + k + ", centro: " + costoMinimoParaCadaCliente.get(k));

        System.out.println("\nCosto total final: " + costoFinal);
    }

    public static void main(String[] args) {
        int V = 58;

        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(); // Lista que establece las conexiones entre nodos. Devuelve la matriz.

        List<List<Integer>> caminosACentros = new ArrayList<>(); // Lista que guarda los Dijkstra de cada cliente.

        // Se calculan los caminos de cada centro a cada cliente para que sea más liviano el procesamiento (al ser menos).
        for (int i = 50; i < 58; i++) {

            grafo.dijkstra(conexiones, i); // Acá es donde se hace el Dikstra con cada cliente a cada centro. Lo que hace que cambien los valores de la lista 'distancias'.

            List<Integer> caminosPorCliente = new ArrayList<>(49); // Inicializamos la lista que guarda los Dijkstra de un cliente solo.

            for (int j = 0; j <= 49; j++) {
                caminosPorCliente.add(grafo.getDistancias()[j]); // Le asignamos los valores a la lista previmente creada.
            }
            caminosACentros.add(caminosPorCliente); // Se agrega a la lista de listas. Agregamos los minimos caminos por cliente.
        }

        // Se suman los costos de transporte al puerto.
        grafo.agregarCostosAlPuerto(caminosACentros);

        // Se multiplican el volumen por cliente a cada elemento.
        grafo.agregarCostosVolumenCliente(caminosACentros);

        //Lista de costos fijos de centros.
        List<Integer> costosFijos = grafo.costosDeOperacion();

        // En esta impresión cada fila es el centro y cada columna el cliente que llega.
        System.out.println();
        for (int i = 0; i < caminosACentros.size(); i++) {
            for (int j = 0; j < caminosACentros.get(0).size(); j++) {
                System.out.print(caminosACentros.get(i).get(j) + " ");
            }
            System.out.println();
        }

        System.out.println();
        for (int i = 0; i < costosFijos.size(); i++)
            System.out.print(costosFijos.get(i) + " ");

        List<Integer> construccion = construirCentros(caminosACentros, costosFijos);

        System.out.println();
        System.out.print("\nCentros a construir: ");
        for (int i = 0; i < construccion.size(); i++)
            System.out.print(construccion.get(i) + " ");

        System.out.println();
        impresionResultados(caminosACentros,construccion, costosFijos);
    }
}

