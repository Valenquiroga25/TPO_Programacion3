import java.util.List;

public class CO implements Comparable<CO> {
    //Lista que reprenta si los centros estan en construidos (x[i]=1),eventuales (x[i]=0) y descartados(x[i]=-1)
    List<Integer> x;
    //Matriz de con los caminos
    List<List<Nodo>> mapa;
    //Lista con el costo Minimo de cada cliente a un centro construido
    List<Integer> costosMinimos;
    //Lista con el costo Minimo de cada cliente a un centro construido y posible
    List<Integer> costoMinimosPosibles;
    //suma de los costos mínimos de los COs construidos + costos hijos de los COs construidos
    int u;
    //suma de los costos mínimos de los COs construidos y eventuales + costos hijos de los COs construidos
    int c;
    int reduccionMinima;
    int reduccionMaxima;
    int centroAContruir;
    public CO(List<Integer> x,List<List<Nodo>> mapa,int centroAConstruir){
        this.x = x;
        this.mapa = mapa;
        this.centroAContruir = centroAConstruir;
    }
    private void setCostosMinimos(){
        if(centroAContruir==0){
            u = Integer.MAX_VALUE;
        }else{
            int i = 0;
            while(x.get(i)!=0 &&  i < x.size()){
                for (int j = 0; j < mapa.get(i).size(); j++) {
                    if(x.get(i)==-1)
                        break;
                    else{
                        if(costosMinimos.get(j) > mapa.get(i).get(j).costo)
                            costosMinimos.set(j,mapa.get(i).get(j).costo);
                    }
                }
                i++;
            }
            this.u = 0;
            for (i = 0; i < costosMinimos.size(); i++) {
                this.u += costosMinimos.get(i);
            }
        }
    }
    private void setCostosMinimosPosibles(){
        for (int i = 0; i < mapa.size() ; i++) {
            for (int j = 0; j < mapa.get(i).size(); j++) {
                if(costoMinimosPosibles.get(j) > mapa.get(i).get(j).costo)
                    costoMinimosPosibles.set(j,mapa.get(i).get(j).costo);
            }
        }
        this.c = 0;
        for (int i = 0; i < costoMinimosPosibles.size(); i++) {
            this.c += costoMinimosPosibles.get(i);
        }
    }
    private void setReduccionMinima() {
        this.reduccionMinima = 0;
        for (int i = 0; i < mapa.get(i).size(); i++) {
            if (costosMinimos.get(i) == mapa.get(centroAContruir).get(i).costo) {
                int segundoMinimo = Integer.MAX_VALUE;
                for (int j = 0; j < mapa.size(); j++) {
                    if (j != centroAContruir && segundoMinimo > mapa.get(j).get(i).costo && x.get(j)!=-1) {
                        segundoMinimo = mapa.get(j).get(i).costo;
                    }
                }
                this.reduccionMinima += segundoMinimo - mapa.get(centroAContruir).get(i).costo;
            }
        }
    }

    private void setReduccionMaxima(){
        this.reduccionMaxima = 0;
        for (int i = 0; i < mapa.get(i).size(); i++) {
            if (costosMinimos.get(i) == mapa.get(centroAContruir).get(i).costo){
                int maximo = 0;
                if(centroAContruir==0) {
                    for (int j = 0; j < mapa.size(); j++) {
                        if (j != centroAContruir && maximo > mapa.get(j).get(i).costo && x.get(j)!=-1) {
                            maximo = mapa.get(j).get(i).costo;
                        }
                    }
                    this.reduccionMaxima += maximo - mapa.get(centroAContruir).get(i).costo;
                }else{
                    for (int j = 0; j < centroAContruir-1; j++) {
                        if (maximo > mapa.get(j).get(i).costo && x.get(j)!=-1) {
                            maximo = mapa.get(j).get(i).costo;
                        }
                    }
                    this.reduccionMaxima += maximo - mapa.get(centroAContruir).get(i).costo;
                }
            }
        }
    }

    @Override
    public int compareTo(CO o) {
        return Integer.compare(o.u,this.u);
    }
}