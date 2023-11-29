import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CO implements Comparable<CO> {
    List<Integer> x; //Lista que reprenta si los centros estan en construidos (x[i]=1),eventuales (x[i]=0) y descartados(x[i]=-1)
    List<List<Integer>> mapa; //Matriz de con los caminos
    List<Integer> costosFijos; // Costos fijos de cada centro
    List<Integer> costosMinimos; //Lista con el costo Minimo de cada cliente a un centro construido (Lista que uso para calcular u)
    List<Integer> costoMinimosPosibles; //Lista con el costo Minimo de cada cliente a un centro construido y posible
    List<Integer> valoresMinimos; // Lista con valores minimos de las columnas de los centros construidos o posibles
    int u; //suma de los costos mínimos de los COs construidos + costos hijos de los COs construidos
    int c; //suma de los costos mínimos de los COs construidos y eventuales + costos hijos de los COs construidos
    int reduccionMinima;
    int reduccionMaxima;
    int centroAContruir;
    int filas;
    int columnas;

    public CO(List<Integer> x,List<List<Integer>> mapa,List<Integer> costosFijos,int centroAConstruir){
        this.x = x;
        this.mapa = mapa;
        this.costosFijos=costosFijos;
        this.centroAContruir = centroAConstruir;
        this.filas = mapa.size();
        this.columnas = mapa.get(0).size();
        setCostosMinimos();
        setCostosMinimosPosibles();
        setReduccionMinima();
        setReduccionMaxima();
    }
    private void setCostosMinimos(){ // Valor de u
        costosMinimos = new ArrayList<>();
        for(int l=0; l < columnas;l++) // Se inicializa la lista con valores infinitos para ir guardando los menores de cada columna.
            costosMinimos.add(Integer.MAX_VALUE);

        if(centroAContruir==0){
            u = Integer.MAX_VALUE;
        }else{
            for(int i=0;i < filas;i++){ // Se agregan a la lista los valores minimos de cada columna (cliente) de los centros construidos.
                if(x.get(i) == 1)
                    for (int j = 0; j < columnas; j++) {
                        if(costosMinimos.get(j) > mapa.get(i).get(j))
                            costosMinimos.set(j,mapa.get(i).get(j));
                }
            }

            this.u = 0;
            for (int w = 0; w < costosMinimos.size(); w++) {
                this.u += costosMinimos.get(w);
            }

            // Se suman los costos fijos de los centros a u.
            for(int k=0;k<x.size();k++){
                if (x.get(k)==1)
                    this.u+=costosFijos.get(k);
            }
        }
    }

    private void setCostosMinimosPosibles(){ // Valor de c.
        costoMinimosPosibles = new ArrayList<>(); // Lista con cantidad de elementos que de columnas en matriz.
        List<Integer> listaParaC = new ArrayList<>(); // Se crea lista para almacenar los indices de los centros != -1. Luego se itera por columna de filas de centros != -1.

        for(int k=0;k < columnas;k++)
            costoMinimosPosibles.add(Integer.MAX_VALUE); // Como necesitamos encontrar valores minimos se inicializa con valores 'infinitos'. Sino nunca se modifica.

        for(int indice=0;indice < filas;indice++){
            if (x.get(indice) == 1)
                listaParaC.add(indice); // Se guardan los centros == 1.
        }

        for (int i = 0; i < filas ; i++) { // Itera para cada centro construido o por construir.
            if (x.get(i) != -1) {
                for (int j = 0; j < columnas; j++) {
                    if (costoMinimosPosibles.get(j) > mapa.get(i).get(j)) // 'mapa.get(listaParaC.get(i))' representa que quiero analizar los valores de las columnas de esas filas.
                        costoMinimosPosibles.set(j, mapa.get(i).get(j));
                }
            }
        }

        this.c = 0;
        for (int i = 0; i < costoMinimosPosibles.size(); i++) {
            this.c += costoMinimosPosibles.get(i);
        }

        for(int costo=0;costo<listaParaC.size();costo++){ // Se agregan los costos de los centros construidos.
            this.c += costosFijos.get(listaParaC.get(costo));
        }
        // listaParaC guarda los valores de los indices de los centros construidos (!=-1). Seria algo así: [centro1, centro5, centro8]
    }

    private void setReduccionMinima() {
        this.reduccionMinima = 0;
        valoresMinimos = new ArrayList<>(); // Lista con valores minimos de cada columna.

        for(int k=0;k < columnas;k++)
            valoresMinimos.add(Integer.MAX_VALUE);

        // Todo este for se usa para calcular el valor minimo de cada columna de centros != -1.
        for(int j=0;j < columnas;j++) {
            for (int i=0;i < filas;i++){
                if (x.get(i) != -1){ // Se comparan los valores de los centros construido o por construir. Por eso no uso costosMinimos.
                    if(mapa.get(i).get(j) < valoresMinimos.get(j)) // Se pregunta si el valor de el centro en el que nos encontramos es igual al valor minimo de los centros != -1.
                        valoresMinimos.set(j, mapa.get(i).get(j));
                }
            }
        }

        for(int k=0;k < columnas;k++) {
            if (Objects.equals(mapa.get(centroAContruir).get(k), valoresMinimos.get(k))){ // Si el valor es el más chico de la columna.
                int valorMinimo = valoresMinimos.get(k);
                int segundoValorMinimo = Integer.MAX_VALUE;

                for (int i = 0; i < filas; i++) { // Iteramos por cada fila de la columna actual para encontrar el segundo valor minimo.
                    if(mapa.get(i) != mapa.get(centroAContruir)) {
                        if (x.get(i) != -1 && valorMinimo <= mapa.get(i).get(k)) { // Si el centro es posible o construido y si el valor no es el minimo.
                            if (mapa.get(i).get(k) < segundoValorMinimo)
                                segundoValorMinimo = mapa.get(i).get(k);
                        }
                    }
                }

                // Si encontramos un segundo valor mínimo, actualizamos la reducción mínima
                if (segundoValorMinimo != Integer.MAX_VALUE) {
                    reduccionMinima += segundoValorMinimo - valorMinimo;
                }
            }
        }
    }

    private void setReduccionMaxima(){ // Implementacion que vimos con Seba.
        this.reduccionMaxima = 0;

        List<Integer> valoresMinimosConstruidos = new ArrayList<>(columnas); // Lista con valores minimos de los centros construidos. Lo usan los centros > 0.

        for(int k=0;k < mapa.get(0).size();k++)
            valoresMinimosConstruidos.add(Integer.MAX_VALUE);

        List<Integer> valoresMaximos = new ArrayList<>(columnas); // Lista con valores máximos de cada columna. Lo usa el centro 0.

        for(int k=0;k < mapa.get(0).size();k++)
            valoresMaximos.add(0);

        if(centroAContruir != 0) {
            for (int j = 0; j < columnas; j++) {
                for (int i = 0; i < centroAContruir; i++) {
                    if (x.get(i) == 1) {
                        if (mapa.get(i).get(j) < valoresMinimosConstruidos.get(j)) {
                            valoresMinimosConstruidos.set(j, mapa.get(i).get(j));
                        }
                    }
                }
            }
        }else{
            for (int j = 0; j < columnas; j++) {
                for (int i=0;i < filas;i++){
                    if (mapa.get(i).get(j) > valoresMaximos.get(j)){
                        valoresMaximos.set(j,mapa.get(i).get(j));
                    }
                }
            }
        }

        if (centroAContruir != 0)
            for (int k=0;k<columnas;k++){
                if (mapa.get(centroAContruir).get(k) < valoresMinimosConstruidos.get(k))
                    this.reduccionMaxima += (valoresMinimosConstruidos.get(k) - mapa.get(centroAContruir).get(k));
            }
        else
            for(int k=0;k < columnas;k++) {
                if (!Objects.equals(mapa.get(centroAContruir).get(k), valoresMaximos.get(k))) // Si no es el valor maximo de la columna. Hacer el valor maximo menos el valor de este centro (si es el minimo).
                    reduccionMaxima += (valoresMaximos.get(k) - mapa.get(centroAContruir).get(k));
            }
    }

    @Override
    public int compareTo(CO o) {
        return Integer.compare(this.c,o.c);
    }
}