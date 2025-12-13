
package lineales.dinamicas;


public class Pila {

    private Nodo tope;

    //constructor
    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem) {
        Nodo nuevo = new Nodo(nuevoElem, this.tope);

        this.tope = nuevo;

        return true;
    }

    public boolean desapilar() {
        boolean desapilado = false;

        if (!esVacia()) {
            this.tope = this.tope.getEnlace();
            desapilado = true;
        }
        return desapilado;
    }

    public Object obtenerTope() {
        Object elemento = null;
        if (!esVacia()) {
            elemento = this.tope.getElem();
        }

        return elemento;
    }

    public boolean esVacia() {
        return this.tope == null;
    }

    public void vaciar() {
        this.tope = null;
    }

    public Pila clone() {
        Pila clon = new Pila();
        Nodo aux = this.tope;
        Nodo auxClon;
        if (!esVacia()) {
            Nodo nuevo = new Nodo(aux.getElem(), null);
            clon.tope = nuevo;
            auxClon = clon.tope;
            aux = aux.getEnlace();
            
            while (aux != null) {
                nuevo = new Nodo(aux.getElem(), null);
                auxClon.setEnlace(nuevo);
                auxClon = auxClon.getEnlace();
                aux = aux.getEnlace();
            }
        }
        return clon;
    }

    public String toString() {
        String s;
        if (this.tope == null) {
            s = "Pila vacia";
        } else {
            Nodo aux = this.tope;
            s = "]";

            while (aux != null) {
                s = aux.getElem().toString()+s;
                aux = aux.getEnlace();
                if (aux != null) {
                    s = ","+s ;
                }
            }
            s = "["+s;
        }
        return s;
    }
}
