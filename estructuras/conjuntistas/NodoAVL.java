package conjuntistas;

public class NodoAVL {
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    
    public NodoAVL (Comparable elem, NodoAVL izquierdo, NodoAVL derecho){
        this.elem=elem;
        this.izquierdo=izquierdo;
        this.derecho=derecho;
        this.altura=0;
    }
    
    public Comparable getElem (){
        return this.elem;
    }

    public void setElem (Comparable elem){
        this.elem=elem;
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
    
    
    public NodoAVL getIzquierdo (){
        return this.izquierdo;
    }

    public void setIzquierdo (NodoAVL izquierdo){
        this.izquierdo=izquierdo;
    }
    
    public NodoAVL getDerecho (){
        return this.derecho;
    }
    
    public void setDerecho (NodoAVL derecho){
        this.derecho=derecho;
    }

}