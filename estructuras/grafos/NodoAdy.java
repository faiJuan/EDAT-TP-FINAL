package grafos;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdy, Object nuevaEtiqueta) {
        this.vertice = vertice;
        this.sigAdyacente = sigAdy;
        this.etiqueta=nuevaEtiqueta;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

    public Object getEtiqueta (){
        return this.etiqueta;
    }

    public void setEtiqueta (Object nuevaE){
        this.etiqueta=nuevaE;
    }
}
