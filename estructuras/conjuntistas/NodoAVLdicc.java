package conjuntistas;

public class NodoAVLdicc {
    private Object dato;
    private Comparable clave;
    private int altura;
    private NodoAVLdicc izquierdo;
    private NodoAVLdicc derecho;
    
    public NodoAVLdicc (Comparable elem, Object undato, NodoAVLdicc izquierdo, NodoAVLdicc derecho){
        this.dato=undato;
        this.clave=elem;
        this.izquierdo=izquierdo;
        this.derecho=derecho;
        this.altura=0;
    }
    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }
    
    public Comparable getClave (){
        return this.clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public int getAltura (){
        return altura;
    }

    public void recalcularAltura (){
        int alturaIzq=0;
        int alturaDer=0;
        if(izquierdo!=null){
            alturaIzq=this.izquierdo.getAltura()+1;
        }
        if(derecho!=null){
            alturaDer=this.derecho.getAltura()+1;
        }

        this.altura=Math.max(alturaIzq, alturaDer);
    }
    
    
    public NodoAVLdicc getIzquierdo (){
        return this.izquierdo;
    }

    public void setIzquierdo (NodoAVLdicc izquierdo){
        this.izquierdo=izquierdo;
    }
    
    public NodoAVLdicc getDerecho (){
        return this.derecho;
    }
    
    public void setDerecho (NodoAVLdicc derecho){
        this.derecho=derecho;
    }

}