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

    public static void impresionResultados(List<List<Integer>> mapa, List<Integer> centrosFinales){
        int columnas = mapa.get(0).size();
        List<Integer> centros = new ArrayList<>(); // Lista con centros que deben construirse.
        List<Integer> valoresMinimos = new ArrayList<>(); // Lista para saber el costo mínimo de cada cliente a cada centro construido.
        List<Integer> costoMinimoParaCadaCliente = new ArrayList<>(); // Lista para almacenar indice de fila con menor costo (centro conveniente)

        // Se almacenan los centros que se deben construir.
        for(int i=0;i<centrosFinales.size();i++){
            if (centrosFinales.get(i) == 1)
                centros.add(i);
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
                        costoMinimoParaCadaCliente.add(i);
                    }
                }
            }
        }

        System.out.println("\nLos centros que deben construirse son los siguientes: " + centros);
        System.out.println("\nCentro conveniente para cada cliente:");
        for(int k=0;k < columnas;k++)
            System.out.println("Cliente " + k + ", centro: " + costoMinimoParaCadaCliente.get(k));
    }

    public static void main(String[] args) {
        int V = 58;

        Grafo grafo = new Grafo(V);
        List<List<Nodo>> conexiones = grafo.establecerConexiones(V); // Lista que establece las conexiones entre nodos.

        List<List<Integer>> caminosACentros = new ArrayList<>(); // Lista que guarda los Dijkstra de cada cliente.


        for (int i = 50; i < 58; i++) {

            grafo.dijkstra(conexiones, i); // Acá es donde se hace el Dikstra con cada cliente a cada centro. Lo que hace que cambien los valores de la lista 'distancias'.
            // El método Dijkstra inicializa la lista 'distancias' por cada llamada (en este caso se hace una llamada por fila) y le va asignando valores segun los caminos del nodo de origen que se le pasa.

            List<Integer> caminosPorCliente = new ArrayList<>(49); // Inicializamos la lista que guarda los Dijkstra de un cliente solo.

            for (int j = 0; j <= 49; j++) {
                caminosPorCliente.add(grafo.getDistancias()[j]); // Le asignamos los valores a la lista previmente creada.
            }
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
        impresionResultados(caminosACentros,construccion);
    }

    /* Ejemplo de clase

        List<List<Integer>> matriz = new ArrayList<>();

        List<Integer> fila0 = new ArrayList<>();
        List<Integer> fila1 = new ArrayList<>();
        List<Integer> fila2 = new ArrayList<>();
        List<Integer> fila3 = new ArrayList<>();

        fila0.add(3);
        fila0.add(10);
        fila0.add(8);
        fila0.add(18);
        fila0.add(14);

        fila1.add(9);
        fila1.add(4);
        fila1.add(6);
        fila1.add(5);
        fila1.add(5);

        fila2.add(12);
        fila2.add(6);
        fila2.add(10);
        fila2.add(4);
        fila2.add(8);

        fila3.add(8);
        fila3.add(6);
        fila3.add(5);
        fila3.add(12);
        fila3.add(9);

        matriz.add(fila0);
        matriz.add(fila1);
        matriz.add(fila2);
        matriz.add(fila3);

        List<Integer> costosFijos = new ArrayList<>();
        costosFijos.add(4);
        costosFijos.add(6);
        costosFijos.add(6);
        costosFijos.add(8);

        List<Integer> resultado = construirCentros(matriz,costosFijos);

        for (int i=0;i<matriz.size();i++){
            System.out.println(matriz.get(i) + " ");
        }

        System.out.println();
        System.out.print("Centros a construir: ");
        for(int i=0;i<resultado.size();i++)
            System.out.print(resultado.get(i) + " ");

        System.out.println();
        impresionResultados(matriz,resultado);
    }
    */
}

