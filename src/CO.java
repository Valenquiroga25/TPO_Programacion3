import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CO implements Comparable<CO> {
    //Lista que reprenta si los centros estan en construidos (x[i]=1),eventuales (x[i]=0) y descartados(x[i]=-1)
    List<Integer> x;
    //Matriz de con los caminos
    List<List<Integer>> mapa;
    // Costos fijos de cada centro
    List<Integer> costosFijos;

    //Lista con el costo Minimo de cada cliente a un centro construido
    List<Integer> costosMinimos; /*Lista que uso para calcular u*/
    //Lista con el costo Minimo de cada cliente a un centro construido y posible
    List<Integer> costoMinimosPosibles;
    //suma de los costos mínimos de los COs construidos + costos hijos de los COs construidos
    int u;
    //suma de los costos mínimos de los COs construidos y eventuales + costos hijos de los COs construidos
    int c;
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
        setReduccionMaxima();
        setReduccionMinima();
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
            if (x.get(indice) !=-1 )
                listaParaC.add(indice); // Se guardan los centros != -1.
        }

        for (int i = 0; i < listaParaC.size() ; i++) { // Itera para cada centro construido.
            for (int j = 0; j < columnas; j++) {
                if(costoMinimosPosibles.get(j) > mapa.get(listaParaC.get(i)).get(j)) // 'mapa.get(listaParaC.get(i))' representa que quiero analizar los valores de las columnas de esas filas.
                    costoMinimosPosibles.set(j, mapa.get(listaParaC.get(i)).get(j));
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
        List<Integer> valoresMinimos = new ArrayList<>(columnas); // Lista con valores minimos de cada columna.

        for(int k=0;k < mapa.get(0).size();k++)
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
            if (Objects.equals(mapa.get(centroAContruir).get(k), valoresMinimos.get(k))) // Si el valor de la matriz en la fila del centro actual es la menor de la columna podemos sacar la RedMin y RedMax.
                /*reduccionMinima += segundo valor minimo - valor minimo*/ ;
        }

        // Lo de mateo
        /*
        for (int i = 0; i < mapa.get(i).size(); i++) {
            if (costosMinimos.get(i) == mapa.get(centroAContruir).get(i)) {
                int segundoMinimo = Integer.MAX_VALUE;
                for (int j = 0; j < mapa.size(); j++) {
                    if (j != centroAContruir && segundoMinimo > mapa.get(j).get(i) && x.get(j)!=-1) {
                        segundoMinimo = mapa.get(j).get(i);
                    }
                }
                this.reduccionMinima += segundoMinimo - mapa.get(centroAContruir).get(i);
            }
        }
         */
    }

    private void setReduccionMaxima(){
        this.reduccionMaxima = 0;

        List<Integer> valoresMaximos = new ArrayList<>(columnas); // Lista con valores máximos de cada columna.

        for(int k=0;k < mapa.get(0).size();k++)
            valoresMaximos.add(0);

        // Todo este for se usa para calcular el valor máximo de cada columna de centros != -1.
        for(int j=0;j < columnas;j++) {
            for (int i=0;i < filas;i++){
                if (x.get(i) != -1){ // Se comparan los valores de los centros construido o por construir.
                    if(mapa.get(i).get(j) > valoresMaximos.get(j)) // Se pregunta si el valor de el centro en el que nos encontramos es igual al valor máximo de los centros != -1.
                        valoresMaximos.set(j, mapa.get(i).get(j));
                }
            }
        }

        for(int k=0;k < columnas;k++) {
            if (!Objects.equals(mapa.get(centroAContruir).get(k), valoresMaximos.get(k))) // Si no es el valor maximo de la columna. Hacer el valor maximo menos el valor de este centro.
                reduccionMaxima += (valoresMaximos.get(k) - mapa.get(centroAContruir).get(k));
        }

        // Lo de mateo
        /*
        for (int i = 0; i < mapa.get(i).size(); i++) {
            if (costosMinimos.get(i) == mapa.get(centroAContruir).get(i)){
                int maximo = 0;
                if(centroAContruir==0) {
                    for (int j = 0; j < mapa.size(); j++) {
                        if (j != centroAContruir && maximo > mapa.get(j).get(i) && x.get(j)!=-1) {
                            maximo = mapa.get(j).get(i);
                        }
                    }
                    this.reduccionMaxima += maximo - mapa.get(centroAContruir).get(i);
                }else{
                    for (int j = 0; j < centroAContruir-1; j++) {
                        if (maximo > mapa.get(j).get(i) && x.get(j)!=-1) {
                            maximo = mapa.get(j).get(i);
                        }
                    }
                    this.reduccionMaxima += maximo - mapa.get(centroAContruir).get(i);
                }
            }
        }
        */
    }

    @Override
    public int compareTo(CO o) {
        return Integer.compare(o.u,this.u);
    }
}