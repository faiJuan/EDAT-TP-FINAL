package conjuntistas;

import lineales.dinamicas.Lista;

public class Diccionario {

    private NodoAVLdicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVLdicc(clave, dato, null, null);
        } else {
            exito = insertarAux(this.raiz, clave, dato, null);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLdicc n, Comparable clave, Object dato, NodoAVLdicc padre) {
        boolean exito = true;
        if (clave.compareTo(n.getClave()) == 0) {
            // Reporta error:elemento repetido
            exito = false;
        } else if (clave.compareTo(n.getClave()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), clave, dato, n);
            } else {
                n.setIzquierdo(new NodoAVLdicc(clave, dato, null, null));
            }
        } else {
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), clave, dato, n);
            } else {
                n.setDerecho(new NodoAVLdicc(clave, dato, null, null));
            }
        }

        if (exito) {
            balancear(n, padre);
            n.recalcularAltura();
        }

        return exito;
    }

    private void balancear(NodoAVLdicc n, NodoAVLdicc padre) {
        int balance;
        boolean rotar = false;
        NodoAVLdicc resultado = null;
        balance = calcularBalance(n);
        if (balance == 2) {
            if (calcularBalance(n.getIzquierdo()) == -1) {
                // rotacion doble izquierda-derecha
                resultado = rotacionIzquierdaDerecha(n);
                rotar = true;

            } else if (calcularBalance(n.getIzquierdo()) >= 0) {
                // rotacion simple a derecha
                resultado = rotacionSimpleDer(n);
                rotar = true;
            }
        } else if (balance == -2) {
            if (calcularBalance(n.getDerecho()) == 1) {
                // rotacion doble derecha-izquierda
                resultado = rotacionDerechaIzquierda(n);
                rotar = true;
            } else if (calcularBalance(n.getDerecho()) <= 0) {
                // rotacion simple a izquierda
                resultado = rotacionSimpleIzq(n);
                rotar = true;
            }
        }

        if (rotar) {
            if (padre != null) {
                if (n.getClave().compareTo(padre.getClave()) < 0) {
                    padre.setIzquierdo(resultado);
                } else {
                    padre.setDerecho(resultado);
                }
            } else {
                this.raiz = resultado;
            }
        }

    }

    private int calcularBalance(NodoAVLdicc n) {
        int balance = 0, izq = -1, der = -1;
        if (n.getIzquierdo() != null) {
            izq = n.getIzquierdo().getAltura();
        }
        if (n.getDerecho() != null) {
            der = n.getDerecho().getAltura();
        }
        balance = izq - der;
        return balance;
    }

    private NodoAVLdicc rotacionSimpleIzq(NodoAVLdicc n) {

        NodoAVLdicc h, temporaria;
        h = n.getDerecho();
        temporaria = h.getIzquierdo();
        h.setIzquierdo(n);
        n.setDerecho(temporaria);

        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVLdicc rotacionSimpleDer(NodoAVLdicc n) {
        NodoAVLdicc h, temporaria;

        h = n.getIzquierdo();
        temporaria = h.getDerecho();
        h.setDerecho(n);
        n.setIzquierdo(temporaria);

        n.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    private NodoAVLdicc rotacionDerechaIzquierda(NodoAVLdicc n) {
        // rotacion doble derecha-izquierda
        NodoAVLdicc resultado, aux;
        aux = rotacionSimpleDer(n.getDerecho());
        n.setDerecho(aux);
        resultado = rotacionSimpleIzq(n);
        return resultado;
    }

    private NodoAVLdicc rotacionIzquierdaDerecha(NodoAVLdicc n) {
        // rotacion doble izquierda-derecha
        NodoAVLdicc resultado, aux;
        aux = rotacionSimpleIzq(n.getIzquierdo());
        n.setIzquierdo(aux);
        resultado = rotacionSimpleDer(n);

        return resultado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public boolean pertenece(Comparable elem) {
        return obtenerNodo(this.raiz, elem) != null;
    }

    private NodoAVLdicc obtenerNodo(NodoAVLdicc n, Comparable buscado) {
        // metodo PRIVADO que busca el elemento y devuelve el nodo que
        // lo contiene. Si no se encuentra buscado devuelve null
        NodoAVLdicc resultado = null;
        if (n != null) {
            if (n.getClave().compareTo(buscado) == 0) {
                // si el buscado es n, lo devuelve
                resultado = n;
            } else if (n.getClave().compareTo(buscado) > 0) {
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
            } else {
                resultado = obtenerNodo(n.getDerecho(), buscado);
            }

        }
        return resultado;
    }

    public Lista listarInorden() {
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoAVLdicc nodo, Lista lis) {

        if (nodo != null) {

            listarInordenAux(nodo.getIzquierdo(), lis);

            lis.insertar(nodo.getClave(), lis.longitud() + 1);

            listarInordenAux(nodo.getDerecho(), lis);
        }
    }

    public Comparable minimoElem() {
        Comparable temp = null;
        if (!esVacio()) {
            temp = minimoAux(this.raiz);
        }
        return temp;
    }

    private Comparable minimoAux(NodoAVLdicc n) {
        Comparable elem;
        if (n.getIzquierdo() != null) {
            elem = minimoAux(n.getIzquierdo());
        } else {
            elem = n.getClave();
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem = null;
        if (this.raiz != null) {
            NodoAVLdicc aux = this.raiz;
            while (aux.getDerecho() != null) {
                aux = aux.getDerecho();
            }
            elem = aux.getClave();
        }
        return elem;
    }

    public boolean eliminar(Comparable clave) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, clave, null);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLdicc n, Comparable eliminar, NodoAVLdicc padre) {
        boolean exito = false;
        if (n.getClave().compareTo(eliminar) == 0) {
            exito = true;
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                eliminarHoja(n, padre);
            } else {
                if (n.getIzquierdo() != null && n.getDerecho() != null) {
                    NodoAVLdicc aux = n;
                    eliminarCon2Hijos(n, n.getDerecho(), aux, padre);
                } else {
                    if (n.getIzquierdo() != null) {
                        eliminarCon1Hijo(n, padre, n.getIzquierdo());
                    } else {
                        eliminarCon1Hijo(n, padre, n.getDerecho());
                    }
                }
            }

        } else {
            if (n.getClave().compareTo(eliminar) > 0) {
                if (n.getIzquierdo() != null) {
                    exito = eliminarAux(n.getIzquierdo(), eliminar, n);
                }
            } else if (n.getDerecho() != null) {
                exito = eliminarAux(n.getDerecho(), eliminar, n);
            }
        }

        if (exito) {
            n.recalcularAltura();
            balancear(n, padre);
            n.recalcularAltura();
        }

        return exito;
    }

    private void eliminarHoja(NodoAVLdicc n, NodoAVLdicc padre) {
        if (padre == null) {
            this.raiz = null;
        } else {
            if (padre.getIzquierdo() != null) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
        }
    }

    private void eliminarCon1Hijo(NodoAVLdicc n, NodoAVLdicc padre, NodoAVLdicc hijo) {
        if (padre == null) {
            if (this.raiz.getIzquierdo() != null) {
                this.raiz = n.getIzquierdo();
            } else {
                this.raiz = n.getDerecho();
            }
        } else {
            if (padre.getClave().compareTo(n.getClave()) < 0) {
                padre.setDerecho(hijo);
            } else {
                padre.setIzquierdo(hijo);
            }
        }
    }
    /* 
    private void eliminarCon2HijosViejo(NodoAVLdicc n) {
        NodoAVLdicc aux1, aux2;
        aux1 = n.getDerecho();
        aux2 = n;
        if (aux1 != null) {
            // o recursiva
            while (aux1.getIzquierdo() != null) {
                // busco el mas a la izq de la derecha
                aux2 = aux1;
                aux1 = aux1.getIzquierdo();
            }
        }
        n.setClave(aux1.getClave());
        n.setDato(aux1.getDato());

        NodoAVLdicc hijo = aux1.getDerecho();

        if (aux2.getIzquierdo().getClave().equals(aux1.getClave())) {
            aux2.setIzquierdo(hijo);
        } else {
            aux2.setDerecho(hijo);
        }

    }
        */
    private void eliminarCon2Hijos(NodoAVLdicc n, NodoAVLdicc reemplazo, NodoAVLdicc aux, NodoAVLdicc padre) {
        if (reemplazo.getIzquierdo() != null) {
            eliminarCon2Hijos(n, reemplazo.getIzquierdo(), reemplazo, aux);
        } else {
            n.setClave(reemplazo.getClave());
            n.setDato(reemplazo.getDato());

            NodoAVLdicc hijo = reemplazo.getDerecho();

            if (aux.getIzquierdo().getClave().equals(reemplazo.getClave())) {
                aux.setIzquierdo(hijo);
            } else {
                aux.setDerecho(hijo);
            }
        }
        aux.recalcularAltura();
        balancear(aux, padre);
        aux.recalcularAltura();
    }

    public String toString() {

        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLdicc nodo) {
        String cadenaAux = "", cad = "Arbol vacio";
        if (nodo != null) {
            cad = "";
            cad += "\n" + nodo.getClave() + " ";
            if (nodo.getIzquierdo() != null) {
                cad += "HI: " + nodo.getIzquierdo().getClave() + " ";
            } else {
                cad += "HI: - ";
            }
            if (nodo.getDerecho() != null) {
                cad += "HD: " + nodo.getDerecho().getClave() + "\n";
            } else {
                cad += "HD: - \n";
            }

            if (nodo.getIzquierdo() != null) {
                cadenaAux = toStringAux(nodo.getIzquierdo());
                cad += cadenaAux;
            }
            if (nodo.getDerecho() != null) {
                cadenaAux = toStringAux(nodo.getDerecho());
                cad += cadenaAux;
            }

        }
        return cad;
    }

    public Object obtener(Comparable clave) {
        NodoAVLdicc nodo = obtenerNodo(raiz, clave);
        Object encontrado = null;
        if (nodo != null) {
            encontrado = nodo.getDato();
        }
        return encontrado;
    }

}
