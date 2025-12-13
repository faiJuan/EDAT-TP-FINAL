
package lineales.estaticas;

    public class Pila {
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 10;
    
    public Pila (){
        this.arreglo = new Object [TAMANIO];
        this.tope=-1;
    }
    
    public boolean apilar (Object nuevoElem){
        boolean exito;
        
        if (this.tope+1>=this.TAMANIO){
            exito=false;
        }else {
            this.tope++;
            this.arreglo[tope]=nuevoElem;
            exito=true;
        }
        return exito;
    }
    public boolean desapilar (){
        boolean exito;
        
        if (esVacia()){
            exito=false;
        }else{
            this.arreglo[tope]=null;
            this.tope--;
            exito=true;
        }
        return exito;
    }
    
     public boolean esVacia (){    
        return this.tope==-1;
    }
    
    public Object obtenerTope (){
  
        Object elem=null;
        if (!esVacia ()){
            elem=this.arreglo [this.tope];
        }
        return elem;
    }
    
    public void vaciar (){
        while (tope>-1){
            arreglo[this.tope]=null;
            this.tope--;
        }
        
    }
    
    public Pila clone (){
        
        Pila clon = new Pila();
        
        int x = this.tope + 1;
        for (int i = 0; i < x; i++) {
            clon.arreglo[i]=this.arreglo[i];
        }
        
        return clon;
    }
    
    public String toString() {
        String s="";
        if(!esVacia()){
            s="[";
            for (int i=0; i<this.tope+1 ;i++){
                s+=this.arreglo[i];
                if (i<this.tope) {
                    s+=",";
                }
            }
            s+="]";
            
        }
        return s;
    }
    
    }
