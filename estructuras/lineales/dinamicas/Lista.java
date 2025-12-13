
package lineales.dinamicas;


public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        longitud = 0;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        boolean exito = true;
        int long1 = this.longitud;

        if (pos < 1 || pos > long1 + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = false;
        if (!esVacia()) {
            if (1 <= pos && pos <= this.longitud) {
                if (pos == 1) {
                    this.cabecera = this.cabecera.getEnlace();
                } else {
                    Nodo aux = this.cabecera;
                    int i = 2;
                    while (i < pos) {
                        aux = aux.getEnlace();
                        i++;
                    }
                    aux.setEnlace(aux.getEnlace().getEnlace());
                }
                this.longitud--;
                exito = true;
            }
        }
        return exito;

    }

    public Object recuperar(int pos) {
        Object elemento = null;
        int i = 1;
        if (1 <= pos && pos <= this.longitud) {
            Nodo aux = this.cabecera;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            elemento = aux.getElem();
        }
        return elemento;
    }

    public int localizar(Object elem) {
        int pos = -1, i;
        if (!esVacia()) {
            i = 1;
            Nodo aux = this.cabecera;
            while (pos == -1 && aux != null) {
                if (aux.getElem().equals(elem)) {
                    pos = i;
                } else {
                    aux = aux.getEnlace();
                    i++;
                }
            }

        }
        return pos;
    }

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean esVacia() {
        return this.cabecera == null && this.longitud == 0;
    }

    public Lista clone() {
        Lista clon = new Lista();
        Nodo aux = this.cabecera;
        int long1 = 0;
        if (!esVacia()) {
            clon.cabecera = new Nodo(aux.getElem(), null);
            Nodo aux1 = clon.cabecera;
            long1++;
            while (aux.getEnlace() != null) {
                aux = aux.getEnlace();
                aux1.setEnlace(new Nodo(aux.getElem(), null));
                aux1 = aux1.getEnlace();
                long1++;
            }
            clon.longitud = long1;
        }
        return clon;
    }

    public int longitud() {

        return this.longitud;
    }

    public String toString() {
        String s;
        if (esVacia()) {
            s = "Lista vacia";
        } else {
            Nodo aux = this.cabecera;
            s = "[";
            while (aux != null) {
                s = s + aux.getElem() + "-";
                aux = aux.getEnlace();
            }
            s = s + "]";
        }
        return s;
    }

    //Ejercicio simulacro
    public Lista obtenerMultiplos(int num) {
        Lista lis = new Lista();
        int x = 1;
        int long1 = 0;
        if (!esVacia()) {
            Nodo aux = this.cabecera;
            Nodo aux1 = null;
            while (aux != null) {
                if (x % num == 0) {
                    if (aux1 == null) {
                        lis.cabecera = new Nodo(aux.getElem(), null);
                        long1++;
                        aux1 = lis.cabecera;
                    } else {
                        aux1.setEnlace(new Nodo(aux.getElem(), null));
                        long1++;
                        aux1 = aux1.getEnlace();
                    }
                }
                aux = aux.getEnlace();

                x++;
            }
            lis.longitud = long1;
        }
        return lis;
    }

    public void eliminarApariciones(Object elem) {
        if (!esVacia()) {
            Nodo anterior = null;
            Nodo n = this.cabecera;
            int long1 = this.longitud;
            while (n != null) {
                if (n.getElem().equals(elem)) {
                    if (anterior == null) {
                        n = n.getEnlace();
                        this.cabecera = n;
                        long1--;
                    } else {
                        n = n.getEnlace();
                        anterior.setEnlace(n);
                        long1--;
                    }
                } else {
                    anterior = n;
                    n = n.getEnlace();
                }
            }
            this.longitud=long1;
        }
    }

}
