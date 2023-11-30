import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CO implements Comparable<CO> {
    List<Integer> x; //Lista que reprenta si los centros estan en construidos (x[i]=1),eventuales (x[i]=0) y descartados(x[i]=-1)
    List<List<Integer>> mapa; //Matriz de con los caminos
    List<Integer> costosFijos; // Costos fijos de cada centro
    List<Integer> costosMinimos; //Lista con el costo Minimo de los centros construidos. (Lista que uso para calcular u)
    List<Integer> costoMinimosPosibles; //Lista con los costos minimos de centros construidos y eventuales. (Lista para calcular c)
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

            // Se suman los costos fijos de los centros construidos a u.
            for(int k=0;k<x.size();k++){
                if (x.get(k)==1)
                    this.u+=costosFijos.get(k);
            }
        }
    }

    private void setCostosMinimosPosibles(){ // Valor de c.
        costoMinimosPosibles = new ArrayList<>(); // Lista con cantidad de elementos que de columnas en matriz.
        List<Integer> listaParaC = new ArrayList<>(); // Se crea lista para almacenar los indices de los centros != -1. Esto es para sumar los costos fijos de los centros construidos.

        for(int k=0;k < columnas;k++)
            costoMinimosPosibles.add(Integer.MAX_VALUE); // Como necesitamos encontrar valores minimos se inicializa con valores 'infinitos'. Sino nunca se modifica.

        for(int indice=0;indice < filas;indice++){
            if (x.get(indice) == 1)
                listaParaC.add(indice); // Se guardan los centros == 1.
        }

        for (int i = 0; i < filas ; i++) { // Itera para cada centro construido o por construir.
            if (x.get(i) != -1) {
                for (int j = 0; j < columnas; j++) {
                    if (costoMinimosPosibles.get(j) > mapa.get(i).get(j))
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

        // Acá utilizamos la costosMinimosPosibles porque contiene los valores minimos de los centros construidos o eventuales.
        for(int k=0;k < columnas;k++) {
            if (Objects.equals(mapa.get(centroAContruir).get(k), costoMinimosPosibles.get(k))){ // Si el valor es el más chico de la columna.
                int valorMinimo = costoMinimosPosibles.get(k);
                int segundoValorMinimo = Integer.MAX_VALUE;

                for (int i = 0; i < filas; i++) { // Iteramos por cada fila de la columna actual para encontrar el segundo valor minimo de centros construidos o eventuales.

                    if(mapa.get(i) != mapa.get(centroAContruir)) { // Si no nos encontramos en la fila del centro que estamos analizando. Esto lo agregamos porque más adelante debemos hacer el segundo valor más chico - el primero. Y el segundo valor minimo puede tener el mismo valor que el actual.

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

    private void setReduccionMaxima(){
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