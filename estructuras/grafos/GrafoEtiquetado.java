package grafos;

import lineales.dinamicas.Lista;

public class GrafoEtiquetado {
    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object elem) {
        // inserta un nodo al inicio del grafo, si el nodo ya se encuentra repetido no
        // inserta nada y retorna false
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        // busca si existe el nodo en el grafo, caso contrario devuelve nulo
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object elem) {
        boolean exito = false;
        NodoVert nodo = ubicarVertice(elem);
        if (nodo != null) {
            NodoAdy adyacente = nodo.getPrimerAdy();
            while (adyacente != null) {
                eliminarVerticeAux(elem, adyacente.getVertice());
                adyacente = adyacente.getSigAdyacente();
            }
            // se eliminaron todos los arcos que lo apuntaban
            // se pasa a eliminar el vertice
            NodoVert buscado = inicio;
            NodoVert buscadoAux = inicio.getSigVertice();
            if (buscado.getElem().equals(elem)) {
                inicio.setSigVertice(inicio.getSigVertice());
            } else {
                while (!exito && buscadoAux != null) {
                    if (buscadoAux.getElem().equals(elem)) {
                        exito = true;
                        buscado.setSigVertice(buscadoAux.getSigVertice());
                    } else {
                        buscado = buscadoAux;
                        buscadoAux = buscadoAux.getSigVertice();
                    }
                }
            }
        }
        return exito;
    }

    private void eliminarVerticeAux(Object elem, NodoVert nodo) {
        // metodo privado para eliminar la la referencia el vertice que se quiere
        // eliminar
        boolean exito = false;
        NodoAdy adyacente = nodo.getPrimerAdy();
        if (adyacente != null) {
            if (adyacente.getVertice().getElem().equals(elem)) {
                nodo.setPrimerAdy(adyacente.getSigAdyacente());
            } else {
                NodoAdy aux = adyacente.getSigAdyacente();
                while (aux != null && !exito) {
                    if (aux.getVertice().getElem().equals(elem)) {
                        adyacente.setSigAdyacente(aux.getSigAdyacente());
                        exito = true;
                    } else {
                        adyacente = aux;
                        aux = aux.getSigAdyacente();
                    }
                }
            }
        }
    }

    public boolean insertarArco(Object etiqueta, Object nodoInicial, Object nodoDestino) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(nodoInicial);
        NodoVert destino = ubicarVertice(nodoDestino);
        if (origen != null && destino != null) {
            origen.setPrimerAdy(new NodoAdy(destino, origen.getPrimerAdy(), etiqueta));
            destino.setPrimerAdy(new NodoAdy(origen, destino.getPrimerAdy(), etiqueta));
            exito = true;
        }
        return exito;
    }

    public boolean eliminarArco(Object nodoInicial, Object nodoDestino) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(nodoInicial);
        NodoVert destino = ubicarVertice(nodoDestino);
        if (origen != null && destino != null) {
            eliminarArcoAux(origen, destino);
            exito = eliminarArcoAux(destino, origen);
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert origen, NodoVert destino) {
        boolean exito = false;
        NodoAdy adyacente = origen.getPrimerAdy();
        Object nodoBuscado = destino.getElem();
        if (adyacente.getVertice().getElem().equals(nodoBuscado)) {
            origen.setPrimerAdy(adyacente.getSigAdyacente());
        } else {
            NodoAdy aux = adyacente.getSigAdyacente();

            while (aux != null && !exito) {
                if (aux.getVertice().getElem().equals(nodoBuscado)) {
                    adyacente.setSigAdyacente(aux.getSigAdyacente());
                    exito = true;
                } else {
                    adyacente = aux;
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object inicial, Object nodoDest) {
        // devuelve true si existe un arco que conecte 2 nodos
        boolean exito = false;
        NodoVert origen = ubicarVertice(inicial);
        NodoVert destino = ubicarVertice(nodoDest);

        if (origen != null && destino != null) {
            NodoAdy ady = origen.getPrimerAdy();
            while (ady != null && !exito) {
                if (ady.getVertice().getElem().equals(destino.getElem())) {
                    exito = true;

                } else {
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean modificarArco(Object origen, Object destino, Object etiquetaNueva) {
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            exito = modoficarArcoAux(nodoOrigen, nodoDestino, etiquetaNueva);
        }
        return exito;
    }

    public boolean modoficarArcoAux(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        boolean exito = false;
        boolean aux = false;
        NodoAdy adyOrigen = nodoOrigen.getPrimerAdy();
        NodoAdy adyDestino = nodoDestino.getPrimerAdy();

        if (adyOrigen != null && adyDestino != null) {
            while (adyOrigen != null && !exito) {
                if (adyOrigen.getVertice().getElem().equals(nodoDestino.getElem())) {
                    exito = true;
                } else {
                    adyOrigen = adyOrigen.getSigAdyacente();
                }
            }
            while (adyDestino != null && !aux) {
                if (adyDestino.getVertice().getElem().equals(nodoOrigen.getElem())) {
                    aux = true;
                } else {
                    adyDestino = adyDestino.getSigAdyacente();
                }
            }

            if (exito && aux) {
                adyOrigen.setEtiqueta(etiqueta);
                adyDestino.setEtiqueta(etiqueta);
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return inicio == null;
    }

    public boolean existeVertice(Object elem) {

        return ubicarVertice(elem) != null;

    }

    /* Se fija si existe un camino entre 2 nodos existentes */

    public boolean existeCamino(Object nodoorigen, Object nododestino) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(nodoorigen);
        NodoVert destino = ubicarVertice(nododestino);

        if (origen != null && destino != null) {
            exito = existeCaminoAux(origen, destino.getElem(), origen.getElem(), origen.getPrimerAdy());
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert origen, Object destino, Object nodoLimite, NodoAdy ady) {
        boolean exito = false;

        if (ady != null) {
            if (!ady.getVertice().getElem().equals(nodoLimite)) {
                if (ady.getVertice().getElem().equals(destino)) {
                    exito = true;
                } else {
                    exito = existeCaminoAux(ady.getVertice(), destino, nodoLimite, ady.getVertice().getPrimerAdy());
                    if (!exito) {
                        exito = existeCaminoAux(origen, destino, nodoLimite, ady.getSigAdyacente());
                    }
                }
            } else {
                if (ady.getSigAdyacente() != null) {
                    exito = existeCaminoAux(origen, destino, nodoLimite, ady.getSigAdyacente());
                }
            }
        }
        return exito;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // define un nodo desde donde empezar
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public String toString() {
        String msj = stringAux("", inicio);
        return msj;
    }

    private String stringAux(String msj, NodoVert recorre) {

        if (recorre != null) {

            NodoAdy adyacente = recorre.getPrimerAdy();

            if (adyacente == null) {
                msj += recorre.getElem().toString();
            }

            while (adyacente != null) {

                if (adyacente.getSigAdyacente() != null) {
                    msj += recorre.getElem().toString() + " --" + adyacente.getEtiqueta() + "->";
                    msj += " " + adyacente.getVertice().getElem().toString() + ", ";
                } else {
                    msj += recorre.getElem().toString() + " --" + adyacente.getEtiqueta() + "->";
                    msj += " " + adyacente.getVertice().getElem().toString();
                }
                adyacente = adyacente.getSigAdyacente();
            }
            msj += "\n";
            msj = stringAux(msj, recorre.getSigVertice());
        }

        return msj;
    }

    // punto 8.a busca el camino mas corto entre 2 nodos, si no lo encuentra
    // devuelve la lista vacia

    public Lista caminoCorto(Object origen, Object destino) {
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        Lista visitados = new Lista();
        Lista caminoMasCorto = new Lista();

        if (nodoOrigen != null && nodoDestino != null && nodoOrigen != nodoDestino) {
            caminoMasCorto = caminoCortoAux(nodoOrigen, nodoDestino, caminoMasCorto, visitados);
        }
        return caminoMasCorto;
    }

    private Lista caminoCortoAux(NodoVert n, NodoVert destino, Lista caminoCorto, Lista visitados) {

        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        if (n.getElem().equals(destino.getElem())) {
            caminoCorto = visitados.clone();
        } else {
            NodoAdy adyacente = n.getPrimerAdy();
            // visitados.longitud()+1 < caminoCorto.longitud() es la "poda" para que no
            // ingrese al vertice
            // ya que suponiendo que si entra y encuentra el elem, la longitud sera como
            // minimo igual a la lista del camino corto.
            // verifica que este vacia para encontrar al menos el primer camino
            // verifica que no este pasando por un nodo ya visitado
            while (adyacente != null) {
                NodoVert siguiente = adyacente.getVertice();
                if (visitados.localizar(siguiente.getElem()) == -1
                        && (caminoCorto.esVacia() || visitados.longitud() + 1 < caminoCorto.longitud())) {
                    caminoCorto = caminoCortoAux(adyacente.getVertice(), destino, caminoCorto, visitados);
                }
                adyacente = adyacente.getSigAdyacente();
            }

        }
        visitados.eliminar(visitados.longitud());

        return caminoCorto;
    }

    // punto 8.b devuelve el camino con menor peso o en su defecto el unico camino
    // que existe
    public Lista caminoMenorPeso(Object origen, Object destino) {
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        Lista visitados = new Lista();
        Lista caminoMasCorto = new Lista();
        int[] pesoActual = new int[1];
        pesoActual[0] = 0;
        int[] pesoFinal = new int[1];
        pesoFinal[0] = 0;

        if (nodoOrigen != null && nodoDestino != null && nodoOrigen != nodoDestino) {
            caminoMasCorto = caminoMenorPesoAux(nodoOrigen, nodoDestino, caminoMasCorto, visitados, pesoActual,
                    pesoFinal, 0);
        }
        return caminoMasCorto;
    }

    private Lista caminoMenorPesoAux(NodoVert n, NodoVert destino, Lista caminoCorto, Lista visitados,
            int[] pesoActual, int[] pesoFinal, int peso) {

        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        pesoActual[0] = pesoActual[0] + peso;
        if (n.getElem().equals(destino.getElem())) {
            caminoCorto = visitados.clone();
            pesoFinal[0] = pesoActual[0];
        } else {

            NodoAdy adyacente = n.getPrimerAdy();
            while (adyacente != null) {
                NodoVert siguiente = adyacente.getVertice();
                int nuevoPeso = (int) adyacente.getEtiqueta();
                if (visitados.localizar(siguiente.getElem()) == -1 &&
                        (pesoActual[0] + nuevoPeso < pesoFinal[0] || caminoCorto.esVacia())) {
                    caminoCorto = caminoMenorPesoAux(siguiente, destino, caminoCorto,
                            visitados,
                            pesoActual,
                            pesoFinal, nuevoPeso);
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        pesoActual[0] = pesoActual[0] - peso;
        visitados.eliminar(visitados.longitud());

        return caminoCorto;
    }

    // punto 8.c Obtener todos los caminos posibles para llegar de A a B sin pasar
    // por una estación C dada
    public Lista caminosSinVertice(Object origen, Object destino, Object evitable) {
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        NodoVert nodoEvitable = ubicarVertice(evitable);
        Lista visitados = new Lista();
        Lista caminos = new Lista();

        if (nodoDestino != null && nodoOrigen != null && destino != evitable && origen != evitable) {
            // si el nodo origen o destino es el mismo nodo a evitar se devuelve la lista
            // vacia
            caminoSinVerticeAux(nodoOrigen, nodoDestino, nodoEvitable, caminos, visitados);
        }
        return caminos;
    }

    private void caminoSinVerticeAux(NodoVert n, NodoVert destino, NodoVert nodoEvitable, Lista caminosEncontrados,
            Lista visitados) {
        if (n != null) {
            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            if (n.getElem().equals(destino.getElem())) {
                caminosEncontrados.insertar(visitados.clone(), caminosEncontrados.longitud() + 1);
            } else {
                NodoAdy adyacente = n.getPrimerAdy();
                while (adyacente != null) {
                    // verifica que el proximo nodo a visitar no sea el que se quiere evitar
                    boolean evitarNodo = !adyacente.getVertice().getElem().equals(nodoEvitable.getElem());
                    if (visitados.localizar(adyacente.getVertice().getElem()) == -1 && evitarNodo) {
                        caminoSinVerticeAux(adyacente.getVertice(), destino, nodoEvitable, caminosEncontrados,
                                visitados);
                    }
                    adyacente = adyacente.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
    }

    // punto 8.d encontrar un camino que no supere el limite de peso, no
    // necesariamente es el camino con menos peso
    // solo es el primer camino que se encuentra dentro del limite
    public Lista limitePeso(Object origen, Object destino, int limiteKm) {
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        Lista visitados = new Lista();
        Lista camino = new Lista();
        int peso[] = new int[1];
        peso[0] = 0;

        if (nodoDestino != null && nodoOrigen != null && limiteKm > 0 && nodoOrigen != nodoDestino) {
            camino = limitePesoAux(nodoOrigen, nodoDestino, camino, visitados, peso, limiteKm, 0);
        }
        return camino;
    }

    private Lista limitePesoAux(NodoVert n, NodoVert destino, Lista caminoCorto, Lista visitados, int[] cantPeso,
            int limite, int pesoAnterior) {

        visitados.insertar(n.getElem(), visitados.longitud() + 1);
        cantPeso[0] = cantPeso[0] + pesoAnterior;
        if (n.getElem().equals(destino.getElem())) {
            caminoCorto = visitados.clone();

        } else {
            NodoAdy adyacente = n.getPrimerAdy();
            while (adyacente != null) {
                NodoVert siguiente = adyacente.getVertice();
                int nuevoPeso = (int) adyacente.getEtiqueta();
                if (visitados.localizar(siguiente.getElem()) == -1 && cantPeso[0] + nuevoPeso <= limite) {
                    caminoCorto = limitePesoAux(siguiente, destino, caminoCorto, visitados,
                            cantPeso, limite, nuevoPeso);
                }
                if (!caminoCorto.esVacia()) {
                    adyacente = null;
                } else {
                    adyacente = adyacente.getSigAdyacente();
                }
            }

        }
        visitados.eliminar(visitados.longitud());
        cantPeso[0] = cantPeso[0] - pesoAnterior;

        return caminoCorto;
    }

}