package grafos;

import lineales.dinamicas.Lista;



public class GrafoEtiquetado {
    private NodoVert inicio;


    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object elem) {
        //inserta un nodo al inicio del grafo, si el nodo ya se encuentra repetido no inserta nada y retorna false
        boolean exito=false;
        NodoVert aux= this.ubicarVertice(elem);
        if (aux==null) {
            this.inicio=new NodoVert(elem, inicio);
            exito=true;
        }
        return exito;
    }

    private NodoVert ubicarVertice (Object buscado){
        //busca si existe el nodo en el grafo, caso contrario devuelve nulo
        NodoVert aux=this.inicio;
        while (aux!= null && !aux.getElem().equals(buscado)) {
            aux=aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice (Object elem){
        boolean exito=false;
        NodoVert nodo=ubicarVertice(elem);
        if(nodo!=null){
            NodoVert aux=inicio;
            while(aux!=null){
                if(!aux.getElem().equals(elem)){
                    eliminarVerticeAux(elem, aux);
                }
                aux=aux.getSigVertice();
            }
            //se eliminaron todos los arcos que lo apuntaban
            //se pasa a eliminar el nodo, no se podia eliminar antes ya que 
            //si se lo referencia despues de eliminarlo no se iba a poder sacar ese arco
            NodoVert buscado=inicio;
            NodoVert buscadoAux=inicio.getSigVertice();
            if(buscado.getElem().equals(elem)){
                inicio.setSigVertice(inicio.getSigVertice());
            }else{
                while(!exito && buscadoAux!=null){
                    if(buscadoAux.getElem().equals(elem)){
                        exito=true;
                        buscado.setSigVertice(buscadoAux.getSigVertice());
                    }else{
                        buscado=buscadoAux;
                        buscadoAux=buscadoAux.getSigVertice();
                    }
                }
            }
        }
        return exito;
    }

    private void eliminarVerticeAux(Object elem, NodoVert nodo){
        boolean exito=false;
        NodoAdy adyacente= nodo.getPrimerAdy();
        if(adyacente!=null){
            if(adyacente.getVertice().getElem().equals(elem)){
            nodo.setPrimerAdy(adyacente.getSigAdyacente());
            }else{
                NodoAdy aux=adyacente.getSigAdyacente();
                while(aux!=null && !exito){
                    if(aux.getVertice().getElem().equals(elem)){
                        adyacente.setSigAdyacente(aux.getSigAdyacente());
                        exito=true;
                    }
                    else{
                        adyacente=aux;
                        aux=aux.getSigAdyacente();
                    }
                }
            }
        }  
    }
        

    public boolean insertarArco(Object etiqueta, Object nodoInicial, Object nodoDestino){
        boolean exito=false;
        NodoVert origen=ubicarVertice(nodoInicial);
        NodoVert destino=ubicarVertice(nodoDestino);
        if(origen!=null && destino!=null){
            origen.setPrimerAdy(new NodoAdy(destino, origen.getPrimerAdy(), etiqueta));
            destino.setPrimerAdy(new NodoAdy(origen, destino.getPrimerAdy(), etiqueta));
            exito=true;
        }
        return exito;
    }

    

    public boolean eliminarArco(Object nodoInicial, Object nodoDestino){
        boolean exito=false;
        NodoVert origen=ubicarVertice(nodoInicial);
        NodoVert destino=ubicarVertice(nodoDestino);
        if(origen!=null && destino!=null){
            eliminarArcoAux (origen,destino);
            exito=eliminarArcoAux(destino, origen);
        }
        return exito;
    }

    private boolean eliminarArcoAux (NodoVert origen, NodoVert destino){
        boolean exito=false;
        NodoAdy adyacente=origen.getPrimerAdy();
        Object nodoBuscado= destino.getElem();
        if(adyacente.getVertice().getElem().equals(nodoBuscado)){
            origen.setPrimerAdy(adyacente.getSigAdyacente());
        }else{
            NodoAdy aux=adyacente.getSigAdyacente();

            while(aux!=null && !exito){
                if(aux.getVertice().getElem().equals(nodoBuscado)){
                    adyacente.setSigAdyacente(aux.getSigAdyacente());
                    exito=true;
                }
                else{
                    adyacente=aux;
                    aux=aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco (Object inicial, Object nodoDest){
        //devuelve true si existe un arco que conecte 2 nodos
        boolean exito=false;
        NodoVert origen= ubicarVertice(inicial);
        NodoVert destino= ubicarVertice(nodoDest);

        if (origen!=null && destino!=null){
            NodoAdy ady= origen.getPrimerAdy();
            while(ady!=null && !exito){
                if(ady.getVertice().getElem().equals(destino.getElem())){
                    exito=true;
                    
                }else{
                    ady=ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean modificarArco (Object origen, Object destino,Object etiquetaNueva){
        boolean exito=false;
        NodoVert nodoOrigen=ubicarVertice(origen);
        NodoVert nodoDestino=ubicarVertice(destino);

        if(nodoOrigen!=null && nodoDestino!=null){
            exito=modoficarArcoAux(nodoOrigen,nodoDestino,etiquetaNueva);
        }
        return exito;
    }

    public boolean modoficarArcoAux(NodoVert nodoOrigen, NodoVert nodoDestino,Object etiqueta){
        boolean exito=false;
        boolean aux=false;
        NodoAdy adyOrigen=nodoOrigen.getPrimerAdy();
        NodoAdy adyDestino=nodoDestino.getPrimerAdy();

        if(adyOrigen!=null && adyDestino!=null){
            while(adyOrigen!=null && !exito){
                if(adyOrigen.getVertice().getElem().equals(nodoDestino.getElem())){
                    exito=true;
                }else{
                    adyOrigen=adyOrigen.getSigAdyacente();
                }
            }
            while(adyDestino!=null && !aux){
                if(adyDestino.getVertice().getElem().equals(nodoOrigen.getElem())){
                    aux=true;
                }else{
                    adyDestino=adyDestino.getSigAdyacente();
                }
            }

            if(exito && aux){
                adyOrigen.setEtiqueta(etiqueta);
                adyDestino.setEtiqueta(etiqueta);
            }
        }
        return exito;
    }

    public boolean esVacio (){
        return inicio==null;
    }

    public boolean existeVertice (Object elem){
        
        return ubicarVertice(elem)!=null;

    }

    /*Se fija si existe un camino entre 2 nodos existentes */

    public boolean existeCamino (Object nodoorigen, Object nododestino){
        boolean exito=false;
        NodoVert origen=ubicarVertice(nodoorigen);
        NodoVert destino=ubicarVertice(nododestino);

        if(origen!=null && destino!=null){
            exito=existeCaminoAux(origen, destino.getElem(), origen.getElem(),origen.getPrimerAdy());
        }
        return exito;
    }


    private boolean existeCaminoAux (NodoVert origen, Object destino, Object nodoLimite, NodoAdy ady){
        boolean exito=false;
        
        if(ady!=null){
            if(!ady.getVertice().getElem().equals(nodoLimite)){
                if(ady.getVertice().getElem().equals(destino)){
                exito=true;
                }else{
                    exito=existeCaminoAux(ady.getVertice(), destino, nodoLimite, ady.getVertice().getPrimerAdy());
                    if(!exito){
                        exito=existeCaminoAux(origen, destino, nodoLimite, ady.getSigAdyacente());
                    }
                }
            }else{
                if(ady.getSigAdyacente()!=null){
                    exito=existeCaminoAux(origen, destino, nodoLimite, ady.getSigAdyacente());
                }
            }
        }
        return exito;
    }


    public Lista caminoMasCorto (Object origen,Object destino){
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino=ubicarVertice(destino);
        Lista visitados=new Lista();
        Lista caminoMasCorto=new Lista();

        if(nodoOrigen!=null && nodoDestino!=null){
            caminoMasCorto=caminoMasCortoAux(nodoOrigen, destino,visitados,caminoMasCorto);
        }
        return caminoMasCorto;
    }


    private Lista caminoMasCortoAux(NodoVert n , Object destino, Lista visitados, Lista caminoMasCorto){
        if(n!=null){
            visitados.insertar(n.getElem(), visitados.longitud()+1);

            NodoAdy ady=n.getPrimerAdy();
            while(ady!=null){
                if(ady.getVertice().getElem().equals(destino)){
                    //lo encontro
                    if(caminoMasCorto.esVacia() || (visitados.longitud() < caminoMasCorto.longitud())){
                        caminoMasCorto=visitados.clone();
                        
                        caminoMasCorto.insertar(destino, caminoMasCorto.longitud()+1);
                    }

                }else{
                    //no lo encontro
                    if(visitados.localizar(ady.getVertice().getElem()) < 0){
                        caminoMasCorto=caminoMasCortoAux(ady.getVertice(), destino, visitados, caminoMasCorto);
                        visitados.eliminar(visitados.longitud());
                    }

                }
                ady=ady.getSigAdyacente();
            }

        }
        return caminoMasCorto;
    }



    public Lista listarEnProfundidad (){
        Lista visitados = new Lista();
        //define un nodo desde donde empezar
        NodoVert aux = this.inicio;
        while(aux!=null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnProfundidadAux(aux,visitados);
            }
            aux= aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux (NodoVert n, Lista vis){
        if(n!=null){
            vis.insertar(n.getElem(), vis.longitud()+1);
            NodoAdy ady=n.getPrimerAdy();
            while(ady!=null){
                if(vis.localizar(ady.getVertice().getElem())<0){
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady=ady.getSigAdyacente();
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




    public boolean existeCAmino2 (Object origen, Object destino){
        boolean exito=false;
        NodoVert auxD=null;
        NodoVert auxO = null;
        NodoVert aux =this.inicio;

        while((auxO==null || auxD==null) && aux!=null){
            if(aux.getElem().equals(origen)){
                auxO=aux;
            } 
            if(aux.getElem().equals(destino)){
                auxD=aux;
            } 
            aux=aux.getSigVertice();
        }

        if((auxO !=null && auxD!=null)){
            Lista visitados=new Lista();
            exito=existeCaminoAux2(auxO, destino,  visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux2 (NodoVert n , Object dest, Lista vis){
        boolean exito=false;
        if(n!=null){
            if(n.getElem().equals(dest)){
                exito=true;
            }else{
                vis.insertar(n.getElem(),vis.longitud()+1);
                NodoAdy ady=n.getPrimerAdy();
                while(!exito && ady !=null){
                   if(vis.localizar(ady.getVertice().getElem()) < 0){
                       exito=existeCaminoAux2(ady.getVertice(), dest, vis);
                   }
                  ady=ady.getSigAdyacente();
                }
            }
        }
    return exito;
    }

    //punto 8.b busca el camino que recorra menos km
    public Lista caminoMenorPeso(Object origen, Object destino){
        NodoVert nodoOrigen=ubicarVertice(origen);
        NodoVert nodoDestino=ubicarVertice(destino);
        Lista visitados=new Lista();
        Lista menorPeso=new Lista();
        int [] a,b;
        a=new int[1];
        b=new int[1];
        b[0]=0;
        a[0]=0;
        int pesoFinal=0,pesoActual=0;

        if(nodoDestino!=null && nodoOrigen!=null){
            menorPeso=menorPesoAux(nodoOrigen,destino,visitados,menorPeso,a,b);
        }
        return menorPeso;
    }

    private Lista menorPesoAux(NodoVert n , Object destino, Lista visitados, Lista menorPeso, int[] pesoActual,int[] pesoFinal){
        if(n!=null){
            visitados.insertar(n.getElem(), visitados.longitud()+1);
            NodoAdy ady=n.getPrimerAdy();
            while(ady!=null){
                
                pesoActual[0]=pesoActual[0]+(int) ady.getEtiqueta();

                if(ady.getVertice().getElem().equals(destino)){
                    //lo encontro
                    if(menorPeso.esVacia() || (pesoActual[0]<pesoFinal[0])){
                        menorPeso=visitados.clone();
                        pesoFinal[0]=pesoActual[0];
                        menorPeso.insertar(destino, menorPeso.longitud()+1);
                    }
                    //pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();

                }else{
                    //no lo encontro
                    if(visitados.localizar(ady.getVertice().getElem()) < 0){
                        menorPeso=menorPesoAux(ady.getVertice(), destino, visitados, menorPeso,pesoActual,pesoFinal);
                        visitados.eliminar(visitados.longitud());
                    }
                    //pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();

                }
                pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();
                ady=ady.getSigAdyacente();
            }
        }
        return menorPeso;
    }
    

    //punto 8.c Obtener todos los caminos posibles para llegar de A a B sin pasar por una
    //estación C dada
    public Lista caminosSinUnVertice(Object origen, Object destino, Object evitable){
        NodoVert nodoOrigen=ubicarVertice(origen);
        NodoVert nodoDestino=ubicarVertice(destino);
        NodoVert nodoEvitable=ubicarVertice(evitable);
        Lista visitados=new Lista();
        Lista caminos=new Lista();

        if(nodoDestino!=null && nodoOrigen!=null && destino!=evitable && origen!=evitable){
            //si el nodo origen o destino es el mismo nodo a evitar se devuelve la lista vacia
            caminos=caminosSinUnVerticeAux(nodoOrigen,destino,visitados,caminos,nodoEvitable);
        }
        return caminos;
    }

    private Lista caminosSinUnVerticeAux (NodoVert n, Object destino,Lista visitados,Lista caminos,NodoVert evitable){
        //devuelve una lista de listas ya que se requieren todos los caminos posibles
        if(n!=null){
            visitados.insertar(n.getElem(), visitados.longitud()+1);
            NodoAdy ady=n.getPrimerAdy();
            while(ady!=null){
                if(!ady.getVertice().getElem().equals(evitable.getElem())){   
                    if(ady.getVertice().getElem().equals(destino)){
                    //lo encontro
                        Lista aux=visitados.clone();
                        aux.insertar(destino, aux.longitud()+1);
                        caminos.insertar(aux, caminos.longitud()+1);
                    }else{
                    //no lo encontro
                        if(visitados.localizar(ady.getVertice().getElem()) < 0){
                        caminos=caminosSinUnVerticeAux(ady.getVertice(), destino, visitados,caminos,evitable);
                        visitados.eliminar(visitados.longitud());
                        }
                    }   
                }    
            ady=ady.getSigAdyacente();
            }
        }
        return caminos;
    }

    public Lista caminoConLimiteKm(Object origen, Object destino, int limiteKm){
        //devuelve un camino que no supere el limite de kms, si no devuelve la lista vacia
        //El camino que encontro no necesariamente va a ser el mas corto, va a ser el primero que cumpla la condicion
        NodoVert nodoOrigen=ubicarVertice(origen);
        NodoVert nodoDestino=ubicarVertice(destino);
        Lista visitados=new Lista();
        Lista camino=new Lista();
        int peso[]=new int[1];
        peso[0]=0;

        if(nodoDestino!=null && nodoOrigen!=null && limiteKm>0){
            camino=caminoConLimiteKmAux(nodoOrigen,destino,visitados,camino,peso,limiteKm);
        }
        return camino;
    }


    private Lista caminoConLimiteKmAux(NodoVert n , Object destino, Lista visitados,Lista camino,int[] pesoActual,int limite){
        if(n!=null){
            visitados.insertar(n.getElem(), visitados.longitud()+1);
            NodoAdy ady=n.getPrimerAdy();
            while(ady!=null){
                pesoActual[0]=pesoActual[0]+(int) ady.getEtiqueta();
                if(ady.getVertice().getElem().equals(destino)){
                    //lo encontro
                    if(pesoActual[0]<=limite){
                    System.out.println(pesoActual[0]+" "+ady.getVertice().getElem());

                        camino=visitados.clone();
                        camino.insertar(destino, camino.longitud()+1);
                    }else{
                        pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();
                        ady=ady.getSigAdyacente();
                    }
                }else{
                    //no lo encontro
                    if(visitados.localizar(ady.getVertice().getElem()) < 0){
                        camino=caminoConLimiteKmAux(ady.getVertice(), destino, visitados, camino,pesoActual,limite);
                        visitados.eliminar(visitados.longitud());
                    }
                    if(!camino.esVacia()){
                        ady=null;
                    }else{
                       pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();
                    ady=ady.getSigAdyacente(); 
                    }
                    //pesoActual[0]=pesoActual[0]-(int) ady.getEtiqueta();
                    //ady=ady.getSigAdyacente();
                }
                
                
            }
        }
        return camino;
    }
    

}