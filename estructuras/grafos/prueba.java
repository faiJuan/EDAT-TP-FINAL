package grafos;
import lineales.dinamicas.Lista;

public class prueba {
    public static void main(String[] args) {
        GrafoEtiquetado grafo= new GrafoEtiquetado();
        Lista l2=new Lista();
        int elemento=1;

        while (elemento<=5) {
            grafo.insertarVertice(elemento);
            elemento++;
        }
        grafo.insertarArco(6, 5, 2);

        grafo.insertarArco(6, 5, 4);
        grafo.insertarArco(6, 5, 3);

        grafo.insertarArco(6, 3, 1);
        grafo.insertarArco(6, 3, 2);
        grafo.insertarArco(6, 4, 3);
        grafo.insertarArco(6, 4, 2);
        


        //grafo.insertarArco(6, 2, 5);
        
        //l2=grafo.caminoMasCorto(5, 2);
        //l2=grafo.caminosSinUnVertice(5, 2,4);
        //l2=grafo.caminoConLimiteKm(5, 2,4);
        //l2=grafo.caminoMenorPeso(5, 2);
        //System.out.println(grafo.toString());

        //grafo.modificarArco(5, 2, 20);
        //System.out.println(grafo.toString());

        System.out.println(l2.toString());
      


        
}
}
