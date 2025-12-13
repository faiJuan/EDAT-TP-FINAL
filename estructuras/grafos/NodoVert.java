package grafos;

public class NodoVert {
    private Object elem;
    private NodoAdy primerAdy;
    private NodoVert sigVertice;

    public NodoVert(Object elem1, NodoVert sigVertice1) {
        this.elem = elem1;
        this.sigVertice = sigVertice1;
    }

    public Object getElem() {
        return elem;
    }
    public void setElem(Object elem) {
        this.elem = elem;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primerAdy1) {
        this.primerAdy = primerAdy1;
    }
    public NodoVert getSigVertice() {
        return sigVertice;
    }
    public void setSigVertice(NodoVert sigVertice1) {
        this.sigVertice = sigVertice1;
    }

}
