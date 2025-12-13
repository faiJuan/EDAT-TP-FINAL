package conjuntistas;

import lineales.dinamicas.Lista;

public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem, null, null);
        } else {
            exito = insertarAux(this.raiz, elem, null);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL n, Comparable elem, NodoAVL padre) {
        boolean exito = true;
        if (elem.compareTo(n.getElem()) == 0) {
            // Reporta error:elemento repetido
            exito = false;
        } else if (elem.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elem, n);
            } else {
                n.setIzquierdo(new NodoAVL(elem, null, null));
            }
        } else {
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), elem, n);
            } else {
                n.setDerecho(new NodoAVL(elem, null, null));
            }
        }

        if (exito) {
            balancear(n, padre);
            n.recalcularAltura();
        }

        return exito;
    }

    private void balancear(NodoAVL n, NodoAVL padre) {
        int balance;
        boolean rotar = false;
        NodoAVL resultado = null;
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
                if (n.getElem().compareTo(padre.getElem()) < 0) {
                    padre.setIzquierdo(resultado);
                } else {
                    padre.setDerecho(resultado);
                }
            } else {
                this.raiz = resultado;
            }
        }

    }

    private int calcularBalance(NodoAVL n) {
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

    private NodoAVL rotacionSimpleIzq(NodoAVL n) {

        NodoAVL h, temporaria;
        h = n.getDerecho();
        temporaria = h.getIzquierdo();
        h.setIzquierdo(n);
        n.setDerecho(temporaria);

        n.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVL rotacionSimpleDer(NodoAVL n) {
        NodoAVL h, temporaria;

        h = n.getIzquierdo();
        temporaria = h.getDerecho();
        h.setDerecho(n);
        n.setIzquierdo(temporaria);

        n.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    private NodoAVL rotacionDerechaIzquierda(NodoAVL n) {
        // rotacion doble derecha-izquierda
        NodoAVL resultado, aux;
        aux = rotacionSimpleDer(n.getDerecho());
        n.setDerecho(aux);
        resultado = rotacionSimpleIzq(n);
        return resultado;
    }

    private NodoAVL rotacionIzquierdaDerecha(NodoAVL n) {
        // rotacion doble izquierda-derecha
        NodoAVL resultado, aux;
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

    private NodoAVL obtenerNodo(NodoAVL n, Comparable buscado) {
        // metodo PRIVADO que busca el elemento y devuelve el nodo que
        // lo contiene. Si no se encuentra buscado devuelve null
        NodoAVL resultado = null;
        if (n != null) {
            if (n.getElem().compareTo(buscado) == 0) {
                // si el buscado es n, lo devuelve
                resultado = n;
            } else {
                // no es el buscado: busca el primero en el HI
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public Lista listarInorden() {
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoAVL nodo, Lista lis) {

        if (nodo != null) {

            listarInordenAux(nodo.getIzquierdo(), lis);

            lis.insertar(nodo.getElem(), lis.longitud() + 1);

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

    private Comparable minimoAux(NodoAVL n) {
        Comparable elem;
        if (n.getIzquierdo() != null) {
            elem = minimoAux(n.getIzquierdo());
        } else {
            elem = n.getElem();
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem = null;
        if (this.raiz != null) {
            NodoAVL aux = this.raiz;
            while (aux.getDerecho() != null) {
                aux = aux.getDerecho();
            }
            elem = aux.getElem();
        }
        return elem;
    }

    public boolean eliminar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, elemento, null);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVL n, Comparable elem, NodoAVL padre) {
        boolean exito = false;
        if (n.getElem().compareTo(elem) == 0) {
            exito = true;
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                eliminarHoja(n, padre);
            } else {
                if (n.getIzquierdo() != null && n.getDerecho() != null) {
                    eliminarCon2Hijos(n);
                } else {
                    if (n.getIzquierdo() != null) {
                        eliminarCon1Hijo(n, padre, n.getIzquierdo());
                    } else {
                        eliminarCon1Hijo(n, padre, n.getDerecho());
                    }
                }
            }

        } else {
            if (n.getElem().compareTo(elem) > 0) {
                if (n.getIzquierdo() != null) {
                    exito = eliminarAux(n.getIzquierdo(), elem, n);
                }
            } else if (n.getDerecho() != null) {
                eliminarAux(n.getDerecho(), elem, n);
            }
        }

        if (exito) {
            n.recalcularAltura();
            balancear(n, padre);
            n.recalcularAltura();
        }

        return exito;
    }

    private void eliminarHoja(NodoAVL n, NodoAVL padre) {
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

    private void eliminarCon1Hijo(NodoAVL n, NodoAVL padre, NodoAVL hijo) {
        if (padre == null) {
            if (this.raiz.getIzquierdo() != null) {
                this.raiz = n.getIzquierdo();
            } else {
                this.raiz = n.getDerecho();
            }
        } else {
            if (padre.getElem().compareTo(n.getElem()) < 0) {
                padre.setDerecho(hijo);
            } else {
                padre.setIzquierdo(hijo);
            }
        }
    }

    private void eliminarCon2Hijos(NodoAVL n) {
        NodoAVL aux1, aux2;
        aux1 = n.getDerecho();
        aux2 = null;
        if (aux1 != null) {
            while (aux1.getIzquierdo() != null) {
                // busco el mas a la izq de la derecha
                aux2 = aux1;
                aux1 = aux1.getIzquierdo();
            }
        }
        n.setElem(aux1.getElem());
        if (aux1.getDerecho() != null) {
            aux2.setIzquierdo(aux1.getDerecho());
        }

        if (aux2 == null) {
            n.setDerecho(aux1.getDerecho());
        }
    }

    public String toString() {

        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVL nodo) {
        String cadenaAux = "", cad = "Arbol vacio";
        if (nodo != null) {
            cad = "";
            cad += "\n" + nodo.getElem() + " ";
            if (nodo.getIzquierdo() != null) {
                cad += "HI: " + nodo.getIzquierdo().getElem() + " ";
            } else {
                cad += "HI: - ";
            }
            if (nodo.getDerecho() != null) {
                cad += "HD: " + nodo.getDerecho().getElem() + "\n";
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

}
