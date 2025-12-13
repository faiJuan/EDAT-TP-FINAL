
package lineales.estaticas;


public class Cola {
    
    private Object [] arreglo;
    private int frente;
    private int fin;
    private static final int tamanio=10;
    
    public Cola(){
        this.arreglo=new Object[tamanio];
        this.frente=0;
        this.fin=0;
    }
    
    public boolean poner (Object nuevoElem){
         boolean exito=true;
         if ((this.fin+1)%tamanio==this.frente){
             exito=false;
         }else {
             this.arreglo[this.fin]=nuevoElem;
             this.fin=(this.fin+1)%tamanio;
         }
         return exito;
    }
    
    public boolean sacar (){
        boolean exito;
        if (esVacia()){
            exito=false;
        }else {
            this.arreglo[this.frente]=null;
            this.frente= (this.frente+1)%tamanio;
            exito=true;
        }
        return exito;
    }
    
    public Object obtenerFrente (){
        Object elem=null;
        if (!esVacia()){
            elem=this.arreglo[this.frente];
        }
        return elem;
    }
    
    public boolean esVacia (){
        return this.frente==this.fin;
    }
    
    public void vaciar (){
        int x=this.frente;
        while (this.fin!=x){
            this.arreglo[x]=null;
            x=(x+1)%tamanio;
        }
        this.frente=0;
        this.fin=0;
    }
    
    public Cola clone (){
        Cola clon= new Cola();
        int x = this.frente;
        while (x!=this.fin){
            clon.arreglo[x]= this.arreglo[x];
            x=(x+1)%tamanio;
        }
        clon.frente=this.frente;
        clon.fin=this.fin;
        return clon;
    }
    
    public String toString (){
        String s;
        int x=this.frente;
        int y=this.fin;
        if (esVacia()){
            s="Cola vacia";
        }else{
            s="[";
           while (x!=y) {
               s=s+this.arreglo[x]+"-";
               x=(x+1)%tamanio;
           }
           s=s+"]";
        }
        return s;
    }
}
