
package conjuntistas;

import lineales.dinamicas.Lista;

public class ArbolBB {

    private NodoABB raiz;

    //constructor
    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoABB(elem, null, null);
        } else {
            exito = insertarAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean insertarAux(NodoABB n, Comparable elem) {
        boolean exito = true;
        if (elem.compareTo(n.getElem()) == 0) {
            //Reporta error:elemento repetido
            exito = false;
        } else if (elem.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elem);
            } else {
                n.setIzquierdo(new NodoABB(elem, null, null));
            }
        } else {
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), elem);
            } else {
                n.setDerecho(new NodoABB(elem, null, null));
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public boolean pertenece(Comparable elem) {
        return obtenerNodo(this.raiz, elem) != null;
    }

    private NodoABB obtenerNodo(NodoABB n, Comparable buscado) {
        //metodo PRIVADO que busca el elemento y devuelve el nodo que
        //lo contiene. Si no se encuentra buscado devuelve null
        NodoABB resultado = null;
        if (n != null) {
            if (n.getElem().compareTo(buscado) == 0) {
                //si el buscado es n, lo devuelve
                resultado = n;
            } else {
                //no es el buscado: busca el primero en el HI
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

    private void listarInordenAux(NodoABB nodo, Lista lis) {

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

    private Comparable minimoAux(NodoABB n) {
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
            NodoABB aux = this.raiz;
            while (aux.getDerecho() != null) {
                aux = aux.getDerecho();
            }
            elem = aux.getElem();
        }
        return elem;
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        Lista lis = new Lista();
        listarRangoAux(this.raiz, elemMinimo, elemMaximo, lis);
        return lis;
    }
    //areglar
    private Lista listarRangoAux(NodoABB n, Comparable elemMinimo, Comparable elemMaximo, Lista lis) {
        if (n != null) {
            if (n.getElem().compareTo(elemMinimo) <= 0) {
                listarRangoAux(n.getIzquierdo(), elemMinimo, elemMaximo, lis);
            }
            lis.insertar(n.getElem(), lis.longitud() + 1);

            if (n.getElem().compareTo(elemMaximo) <= 0) {
                listarRangoAux(n.getDerecho(), elemMinimo, elemMaximo, lis);
            }
        }

        return lis;
    }

    public boolean eliminar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, elemento, null);
        }
        return exito;
    }

    private boolean eliminarAux(NodoABB n, Comparable elem, NodoABB padre) {
        boolean exito = false;
        if (n.getElem().compareTo(elem) == 0) {
            exito = true;
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                eliminarHoja(n, padre);
            } else {
                if (n.getIzquierdo() != null && n.getDerecho() != null) {
                    eliminarCon2Hijos(n, n.getDerecho());
                } else {
                    if (n.getIzquierdo() != null) {
                        eliminar1Hijo(n, padre, n.getIzquierdo());
                    } else {
                        eliminar1Hijo(n, padre, n.getDerecho());
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
        return exito;
    }

    private void eliminarHoja(NodoABB n, NodoABB padre) {
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

    private void eliminar1Hijo(NodoABB n, NodoABB padre, NodoABB hijo) {
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

    private void eliminarCon2Hijos(NodoABB n, NodoABB hijoD) {
        Comparable aux = null;
        if (hijoD.getIzquierdo() != null) {

            NodoABB aux2 = hijoD.getIzquierdo();
            NodoABB aux3 = hijoD;
            while (aux2.getIzquierdo() != null) {
                aux2 = aux2.getIzquierdo();
                aux3 = aux3.getIzquierdo();
            }
            aux = aux2.getElem();
            if (aux2.getDerecho() == null) {
                aux3.setIzquierdo(null);
            } else {
                aux3.setIzquierdo(aux2.getDerecho());
            }
            n.setElem(aux);
        } else {
            aux = hijoD.getElem();
            n.setElem(aux);
            n.setDerecho(hijoD.getDerecho());
        }
    }

    public String toString() {

        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoABB nodo) {
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

    //ejercicios del simulacro 2 parcial
    public void eliminarMinimo() {
        //elimina el elemento minimo del arbol
        if (this.raiz != null) {
            eliminarMinimoAux(this.raiz, null);
        }
    }

    private void eliminarMinimoAux(NodoABB n, NodoABB padre) {
        if (n.getIzquierdo() == null) {
            //busca al menor
            if (padre == null) {
                //si padre es null significa que es raiz, setea la raiz con su hijo derecho
                this.raiz = n.getDerecho();
            } else {
                if (n.getDerecho() == null) {
                    //si el menor no tiene hijo derecho, significa es que una hoja
                    padre.setIzquierdo(null);
                } else {
                    //si tiene hijo derecho debe setear al padre con el derecho
                    padre.setIzquierdo(n.getDerecho());
                }
            }
        } else {
            eliminarMinimoAux(n.getIzquierdo(), n);
        }
    }

    public ArbolBB clonarParteInvertida(Comparable elem) {
        ArbolBB clon = new ArbolBB();
        NodoABB n;
        if (this.raiz != null) {
            n = obtenerNodo(this.raiz, elem);
            if (n != null) {
                clon.raiz = new NodoABB(n.getElem(), null, null);
                clonarAux(n, clon.raiz);
            }
        }
        return clon;
    }

    private void clonarAux(NodoABB n, NodoABB clon) {
        if (n != null) {
            if (n.getIzquierdo() != null && n.getDerecho() != null) {
                clon.setDerecho(new NodoABB(n.getIzquierdo().getElem(), null, null));
                clon.setIzquierdo(new NodoABB(n.getDerecho().getElem(), null, null));
            } else {
                if (n.getIzquierdo() == null && n.getDerecho() != null) {
                    clon.setIzquierdo(new NodoABB(n.getDerecho().getElem(), null, null));
                } else if (n.getDerecho() == null && n.getIzquierdo() != null) {
                    clon.setDerecho(new NodoABB(n.getIzquierdo().getElem(), null, null));
                }

            }
            clonarAux(n.getIzquierdo(), clon.getDerecho());

            clonarAux(n.getDerecho(), clon.getIzquierdo());
        }
    }
    
}
