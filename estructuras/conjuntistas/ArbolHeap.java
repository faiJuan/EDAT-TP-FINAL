
package conjuntistas;


public class ArbolHeap {

    //arbol heap min
    private int TAMANIO = 20;
    private Comparable[] heap;
    private int ultimo;

    public ArbolHeap() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    public boolean eliminarCima() {
        boolean exito;
        if (this.ultimo == 0) {
            //estructura vacia
            exito = false;
        } else {
            //saca la raiz y pone la ultima hoja en su lugar
            this.heap[1] = this.heap[ultimo];
            this.ultimo--;
            //reestablece la propiedad de heap al minimo
            hacerBajar(1);
            exito = true;
        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;

        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                //temp tiene al menos un hijo izquierdo y lo considera menor
                if (posH < this.ultimo) {
                    //hijoMenor tiene hermano derecho
                    if (this.heap[posH + 1].compareTo(posH) < 0) {
                        posH++;
                    }
                }
                //compara al hijo menor con el padre
                if (this.heap[posH].compareTo(temp) < 0) {
                    //el hijo es menor que el padre, los intercambia
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    salir = true;
                }
            } else {
                //el temp es hoja, esta bien ubicado
                salir = true;
            }
        }
    }

    public boolean insertar(Comparable elem) {
        boolean exito;
        if (this.ultimo == 0) {
            this.heap[1] = elem;
            this.ultimo++;
            exito = true;
        } else {
            this.heap[this.ultimo] = elem;
            exito = true;
            this.ultimo++;
            hacerSubir(this.ultimo);
        }
        return exito;
    }
    //arrreglar, ni idea que paso
    private void hacerSubir(int posH) {
        Comparable aux;
        int posP = posH / 2;
        boolean salir = false;
        while (!salir) {
            aux = this.heap[posP];
            if (this.heap[posH].compareTo(aux)<0) {
                salir=true;
            } else {
                this.heap[posP] = this.heap[posH];
                this.heap[posH] = aux;
                posP = posP / 2;
                posH = posP;
            }
        }
    }

    public String toString() {
        String cadena = "";
        int x = 1;
        while (x <= this.ultimo) {
            cadena = cadena + this.heap[x];
            x++;
        }
        return cadena;
    }
}
