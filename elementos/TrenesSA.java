package elementos;

import java.util.HashMap;
import java.util.Scanner;
import conjuntistas.Diccionario;
import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;

public class TrenesSA {
    private static Diccionario estaciones = new Diccionario();
    private static Diccionario trenes = new Diccionario();
    private static GrafoEtiquetado vias = new GrafoEtiquetado();
    private static HashMap<String, Lista> lineas = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        ManejadorDeArchivos.escribirLog("inicio");
        while (opcion != 0) {
            menuPrincipal();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    ManejadorDeArchivos.cargarDatos(estaciones, trenes, lineas, vias);
                    System.out.println("Datos cargados exitosamente");
                    ManejadorDeArchivos.escribirLog("Se realizo la carga inicial de datos");
                    break;
                case 2:
                    ABMtrenes();
                    break;
                case 3:
                    ABMestaciones();
                    break;
                case 4:
                    ABMlineas();
                    break;
                case 5:
                    ABMvias();
                    break;
                case 6:
                    consultaTrenes();
                    break;
                case 7:
                    consultaEstaciones();
                    break;
                case 8:
                    consultasViajes();
                    break;
                case 9:
                    mostrarSistema();
                    break;
                default:
                    break;
            }
        }
    }

    public static void menuPrincipal() {
        System.out.println("Bienvenido a Trenes Argentinos");
        System.out.println("Elija una opcion para continuar:");
        System.out.println("1. Carga inicial de datos");
        System.out.println("2. ABM de trenes");
        System.out.println("3. ABM de estaciones");
        System.out.println("4. ABM de lineas");
        System.out.println("5. ABM de red de rieles");
        System.out.println("6. Consulta sobre trenes");
        System.out.println("7. Consulta sobre estaciones");
        System.out.println("8. Consulta sobre viajes");
        System.out.println("9. Mostrar sistema");
        System.out.println("0. Para salir");
    }

    // metodos del punto 2 para ABM de trenes

    public static void ABMtrenes() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("Que desea hacer con un tren?");
            System.out.println("1: Dar de alta un tren");
            System.out.println("2: Dar de baja un tren");
            System.out.println("3: Modificar un tren");
            System.out.println("4: Volver al menu principal");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaTren();
                    break;
                case 2:
                    bajaTren();
                    break;
                case 3:
                    modificarTren();
                    break;
                default:
                    break;
            }
        }
    }

    public static void altaTren() {
        int id;
        System.out.println("Ingrese el id den tren");
        id = sc.nextInt();
        sc.nextLine();
        if (trenes.pertenece(id)) {
            System.out.println("No es posible registrar el tren con el id ingresado");
        } else {
            String propulsion, linea;
            int vagonesP, vagonesC;
            System.out.println("Ingrese el tipo de propulsion del tren");
            propulsion = sc.nextLine();
            System.out.println("Ingrese la cantidad de vagones para personas");
            vagonesP = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de vagones para carga");
            vagonesC = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la linea a la que pertenece");
            linea = sc.nextLine();

            if (lineas.containsKey(linea)) {
                Tren tren = new Tren(id, propulsion, vagonesP, vagonesC, linea);
                trenes.insertar(id, tren);
                System.out.println("El tren con id: " + id + " se agrego exitosamente");
                ManejadorDeArchivos.escribirLog("Se agrego un tren al sistema, id: " + id);
            } else {
                // si se ingresa una linea no valida igual se crea el tren pero no se asigna a
                // ninguna linea
                Tren tren = new Tren(id, propulsion, vagonesP, vagonesC, "no asignado");
                trenes.insertar(id, tren);
                System.out.println("El tren con id: " + id + " se agrego exitosamente");
                ManejadorDeArchivos.escribirLog("Se agrego un tren al sistema, id: " + id);
            }
        }
    }

    public static void bajaTren() {
        int id;
        System.out.println("ingrese id del tren a eliminar");
        id = sc.nextInt();
        sc.nextLine();
        if (trenes.pertenece(id)) {
            if (trenes.eliminar(id)) {
                System.out.println("Tren eliminado exitosamente");
                ManejadorDeArchivos.escribirLog("Se elimino el tren " + id);
            } else {
                System.out.println("Ocurrio un error al eliminar el tren, intente nuevamente");
            }
        } else {
            System.out.println("El tren que se quiere eliminar no se encuentra registrado");
        }
    }

    public static void modificarTren() {
        int id;
        System.out.println("ingrese id del tren a modificar");
        id = sc.nextInt();
        sc.nextLine();
        if (trenes.pertenece(id)) {
            System.out.println("Que desea modificar?");
            System.out.println("1: La cantidad de vagones para pasajeros");
            System.out.println("2: La cantidad de vagones de carga");
            System.out.println("3: La linea a la que pertenece");
            System.out.println("4: El tipo de propulsion");
            System.out.println("5: Salir");
            int opcion = sc.nextInt();
            sc.nextLine();
            Tren tren = (Tren) trenes.obtener(id);

            switch (opcion) {
                case 1:
                    System.out.println("ingrese la nueva cantidad de vagones de pasajeros");
                    int vagones = sc.nextInt();
                    sc.nextLine();
                    if (vagones > 0) {
                        tren.setVagonesPasajeros(vagones);
                        ManejadorDeArchivos.escribirLog(
                                "Al tren " + id + " se le modifican los vagones de pasajeros a " + vagones);
                    }
                    break;
                case 2:
                    System.out.println("ingrese la nueva cantidad de vagones de carga");
                    int carga = sc.nextInt();
                    sc.nextLine();
                    if (carga > 0) {
                        tren.setVagonesCarga(carga);
                        ManejadorDeArchivos
                                .escribirLog("Al tren " + id + " se le modifican los vagones de carga a " + carga);
                    }
                    break;
                case 3:
                    System.out.println("ingrese la nueva linea");
                    String linea = sc.nextLine();
                    tren.setLinea(linea);
                    ManejadorDeArchivos.escribirLog("El tren " + id + " fue asignado a la linea " + linea);

                    break;
                case 4:
                    System.out.println("Ingrese la nueva propulsion");
                    String propulsion = sc.nextLine();
                    tren.setPropulsion(propulsion);
                    ManejadorDeArchivos.escribirLog("Al tren " + id + " Se le modifican la propulsion a " + propulsion);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("El tren que se quiere modificar no se encuentra registrado");
        }
    }

    // Metodos del punto 3 para ABM de las estaciones

    public static void ABMestaciones() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("Que desea hacer con la estacion?");
            System.out.println("1: Dar de alta una estacion");
            System.out.println("2: Dar de baja una estacion");
            System.out.println("3: Modificar una estacion");
            System.out.println("4: Volver al menu principal");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaEstacion();
                    break;
                case 2:
                    bajaEstacion();
                    break;
                case 3:
                    modificarEstacion();
                    break;
                default:
                    break;
            }
        }
    }

    public static void altaEstacion() {
        String clave;
        System.out.println("Ingrese el nombre de la estacion");
        clave = sc.nextLine();
        if (estaciones.pertenece(clave)) {
            System.out.println("No es posible registrar la estacion con el nombre ingresado");
        } else {
            String calle, ciudad;
            int numero, codPostal, cantvias, cantplataformas;
            System.out.println("Ingrese el nombre de la calle");
            calle = sc.nextLine();
            System.out.println("Ingrese el numero de la calle");
            numero = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la ciudad en la que se encuentra");
            ciudad = sc.nextLine();
            System.out.println("Ingrese el codigo postal");
            codPostal = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de vias");
            cantvias = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la cantidad de plataformas");
            cantplataformas = sc.nextInt();
            sc.nextLine();

            Estacion estacion = new Estacion(clave, calle, numero, ciudad, codPostal, cantvias, cantplataformas);

            if (estaciones.insertar(clave, estacion)) {
                vias.insertarVertice(estacion);// al momento de agregar una nueva estacion tabien se agrega al grafo
                System.out.println("Se agrego una nueva estacion");
                ManejadorDeArchivos.escribirLog("La estacion: " + clave + " se registro exitosamente");
            } else {
                System.out.println("Ocurrio un error al incertar la estacion");
            }
        }
    }

    public static void bajaEstacion() {
        String clave;
        System.out.println("ingrese el nombre de la estacion a eliminar");
        clave = sc.nextLine();
        if (estaciones.pertenece(clave)) {
            if (estaciones.eliminar(clave) && vias.eliminarVertice(clave)) {
                System.out.println("Estacion eliminada exitosamente");
                ManejadorDeArchivos.escribirLog("Se elimino la estacion " + clave);
            } else {
                System.out.println("Ocurrio un error al eliminar la estacion, intente nuevamente");
            }
        } else {
            System.out.println("La estacion que se quiere eliminar no se encuentra registrada");
        }
    }

    public static void modificarEstacion() {
        String nombre;
        System.out.println("ingrese en nombre de la estacion a modificar");
        nombre = sc.nextLine();
        if (estaciones.pertenece(nombre)) {
            System.out.println("Que desea modificar?");
            System.out.println("1: La calle");
            System.out.println("2: El numero de callle");
            System.out.println("3: La ciudad");
            System.out.println("4: El codigo postal");
            System.out.println("5: La cantidad de vias");
            System.out.println("6: La cantidad de plataformas");
            System.out.println("7: Salir");
            int opcion = sc.nextInt();
            sc.nextLine();
            Estacion estacion = (Estacion) estaciones.obtener(nombre);
            switch (opcion) {
                case 1:
                    System.out.println("ingrese la nueva calle");
                    String calle = sc.nextLine();
                    estacion.setCalle(calle);
                    ManejadorDeArchivos.escribirLog("A la estacion " + nombre + " se le modifico la calle a " + calle);
                    break;
                case 2:
                    System.out.println("Ingrese el nuevo numero");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    if (numero > 0) {
                        estacion.setNumero(numero);
                        ManejadorDeArchivos.escribirLog(
                                "A la estacion " + nombre + " se le modifico el numero de calle a " + numero);
                    }
                    break;
                case 3:
                    System.out.println("ingrese la nueva ciudad");
                    String ciudad = sc.nextLine();
                    estacion.setCiudad(ciudad);
                    ManejadorDeArchivos
                            .escribirLog("A la estacion " + nombre + " se le modifico la ciudad a " + ciudad);
                    break;
                case 4:
                    System.out.println("Ingrese el nuevo codigo postal");
                    int codigo = sc.nextInt();
                    sc.nextLine();
                    estacion.setCodPostal(codigo);
                    ManejadorDeArchivos
                            .escribirLog("A la estacion " + nombre + " se le modifico el codigo postal a " + codigo);
                    break;
                case 5:
                    System.out.println("Ingrese la cantidad de vias");
                    int vias = sc.nextInt();
                    sc.nextLine();
                    estacion.setCantVias(vias);
                    ManejadorDeArchivos.escribirLog("A la estacion " + nombre + " se le modifico las vias a " + vias);
                    break;
                case 6:
                    System.out.println("Ingrese la cantidad de plataformas");
                    int plataformas = sc.nextInt();
                    sc.nextLine();
                    estacion.setCantPlataformas(plataformas);
                    ManejadorDeArchivos.escribirLog(
                            "A la estacion " + nombre + " se le modifico las plataformas a " + plataformas);
                    break;
                default:
                    break;
            }
            System.out.println("Se modifico la estacion "+nombre+" correctamente");
        } else {
            System.out.println("La estacion que se quiere modificar no esta registrada");
        }
    }

    // metodo del punto 4 para ABM de lineas
    public static void ABMlineas() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("Que desea hacer con las lineas");
            System.out.println("1: Dar de alta una linea");
            System.out.println("2: Dar de baja una linea");
            System.out.println("3: Modificar una linea");
            System.out.println("4: Volver al menu principal");

            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    altaLinea();
                    break;
                case 2:
                    bajaLinea();
                    break;
                case 3:
                    modificarLinea();
                    break;
                default:
                    break;
            }
        }
    }

    public static void altaLinea() {
        String nombre;
        System.out.println("Ingrese el nombre de la linea");
        nombre = sc.nextLine();
        if (!lineas.containsKey(nombre)) {
            Lista estacionesLinea = new Lista();
            System.out.println(
                    "Ingrese las estaciones pertenecientes a la linea o fin para terminar la carga de estaciones");
            System.out.println("Nombre de la estacion: ");
            String estacion = sc.nextLine();
            while (!estacion.equalsIgnoreCase("fin")) {
                if (estaciones.pertenece(estacion)) {
                    estacionesLinea.insertar(estacion, estacionesLinea.longitud() + 1);
                    System.out.println("Ingrese el nombre de otra estacion para continuar o fin");
                    estacion = sc.nextLine();
                } else {
                    System.out.println(
                            "La estacion no se encuentra carga, pruebe nuevamente con otra estacion o finalice");
                    estacion = sc.nextLine();
                }
            }
            if (!estacionesLinea.esVacia()) {
                lineas.put(nombre, estacionesLinea);
                System.out.println("la linea " + nombre + " fue agregada correctamente");
                ManejadorDeArchivos.escribirLog("La nueva linea " + nombre + " se agrego exitosamente");
            } else {
                //Las lineas deben tener al menos una estacion
                System.out.println("No se agregaron estaciones correctamente");
            }
        } else {
            System.out.println("No es posible registrar la linea, ya existe una con el mismo nombre");
        }
    }

    public static void bajaLinea() {
        String nombre;
        System.out.println("Ingrese el nombre de la linea a eliminar");
        nombre = sc.nextLine();
        if (lineas.containsKey(nombre)) {
            lineas.remove(nombre);
            System.out.println("La linea: " + nombre + " fue eliminada exitosamente");
            ManejadorDeArchivos.escribirLog("Se elimino la linea: " + nombre);
        } else {
            System.out.println("La linea: " + nombre + " no existe");
        }
    }

    public static void modificarLinea() {
        String linea;
        System.out.println("ingrese en nombre de la linea a modificar");
        linea = sc.nextLine();
        if (lineas.containsKey(linea)) {
            System.out.println("Que desea modificar?");
            System.out.println("1: Eliminar una estacion");
            System.out.println("2: Agregar una estacion");
            System.out.println("3: Salir");
            int opcion = sc.nextInt();
            sc.nextLine();
            Lista lista = lineas.get(linea);
            String nombreE;
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre de la estacion a eliminar");
                    nombreE = sc.nextLine();
                    if (lista.localizar(nombreE) < 0) {
                        System.out.println("La estacion: " + nombreE + " no pertenece a esta linea");
                    } else {
                        if (lista.eliminar(lista.localizar(nombreE))) {
                            System.out.println("La estacion: " + nombreE + " fue eliminada exitosamente");
                            ManejadorDeArchivos
                                    .escribirLog("Se elimino la estacion: " + nombreE + " de la linea: " + linea);
                        } else {
                            System.out.println("Ocurrio un error al emilinar la estacion, intente nuevamente");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el nombre de la estacion que se va a agregar");
                    nombreE = sc.nextLine();
                    if (estaciones.pertenece(nombreE)) {
                        if (lista.insertar(nombreE, lista.longitud() + 1)) {
                            System.out.println("La estacion: " + nombreE + " fue agregada exitosamente");
                            ManejadorDeArchivos
                                    .escribirLog("Se agrego la estacion: " + nombreE + " a la linea: " + linea);
                        } else {
                            System.out.println("Ocurrio un error al agregar la estacion, intente nuevamente");
                        }
                    } else {
                        System.out.println("La estacion: " + nombreE + " no existe");
                    }
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("La linea que se quiere modificar no esta registrada");
        }
    }

    // metodo del punto 5 ABM de la red de rieles
    public static void ABMvias() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("Que desea hacer con las vias:");
            System.out.println("1: Dar de alta una via");
            System.out.println("2: Dar de baja una via");
            System.out.println("3: Modificar una via");
            System.out.println("4: Volver al menu principal");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaVia();
                    break;
                case 2:
                    bajaVia();
                    break;
                case 3:
                    modificarVia();
                    break;
                default:
                    break;
            }
        }
    }

    public static void altaVia() {
        String estacion1, estacion2;
        int kmVia;
        System.out.println("Ingrese la primera estacion que se va a unir");
        estacion1 = sc.nextLine();
        System.out.println("Ingrese la segunda estacion a unir");
        estacion2 = sc.nextLine();
        System.out.println("Ingrese la cantidad de km con los que cuenta la via");
        kmVia = sc.nextInt();
        sc.nextLine();
        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no esta registrada");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no esta registrada");
        } else {
            vias.insertarArco(kmVia, estacion1, estacion2);
            System.out.println("Via nueva colocado exitosamente");
            ManejadorDeArchivos
                    .escribirLog("Se agrego un riel de " + kmVia + " km entre " + estacion1 + " y " + estacion2);
        }
    }

    public static void bajaVia() {
        String estacion1, estacion2;
        System.out.println("Ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("Ingrese la segunda estacion");
        estacion2 = sc.nextLine();
        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no esta registrada");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no esta registrada");
        } else {
            vias.eliminarArco(estacion1, estacion2);
            System.out.println("Via eliminada exitosamente");
            ManejadorDeArchivos.escribirLog("Se elimino la via entre " + estacion1 + " y " + estacion2);
        }
    }

    public static void modificarVia() {
        String estacion1, estacion2;
        int kmNuevo;
        System.out.println("Ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("Ingrese la segunda estacion");
        estacion2 = sc.nextLine();
        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no esta registrada");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no esta registrada");
        } else {
            System.out.println("Ingrese la cantidad de km nuevos");
            kmNuevo = sc.nextInt();
            sc.nextLine();
            if (kmNuevo > 0) {
                vias.modificarArco(estacion1, estacion2, kmNuevo);
                System.out.println("Km via modificado exitosamente");
                ManejadorDeArchivos.escribirLog(
                        "Se modifico un riel entre " + estacion1 + " y " + estacion2 + " a " + kmNuevo + " km");
            } else {
                System.out.println("kilometros incorrectos");
            }
        }
    }

    // punto 6:consulta sobre trenes
    public static void consultaTrenes() {
        int opcion, id;
        System.out.println("Que consulta desea hacer");
        System.out.println("1: Consultar la informacion de un tren");
        System.out.println("2: Consultar las ciudades por las que pasa");
        System.out.println("3: Para salir");
        opcion = sc.nextInt();
        System.out.println("Ingrese el id del tren");
        id = sc.nextInt();
        sc.nextLine();
        if (trenes.pertenece(id)) {
            Tren tren = (Tren) trenes.obtener(id);
            switch (opcion) {
                case 1:
                    System.out.println(tren.toString());
                    ManejadorDeArchivos.escribirLog("Consulta: informacion del tren: " + id);
                    break;
                case 2:
                    ciudadesPorDondePasaTren(tren);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("El tren que ingreso no existe");
        }
    }

    public static void ciudadesPorDondePasaTren(Tren tren) {

        String linea = tren.getLinea();
        Lista listaEstaciones;
        if (lineas.containsKey(linea)) {
            listaEstaciones = lineas.get(linea);
            Lista ciudades=new Lista();
            String nombreE;
            Estacion estacion;
            for (int i = 1; i <= listaEstaciones.longitud(); i++) {
                nombreE = (String) listaEstaciones.recuperar(i);
                estacion = (Estacion) estaciones.obtener(nombreE);
                if (estacion != null && ciudades.localizar(estacion.getCiudad())==-1) {
                    ciudades.insertar(estacion.getCiudad(), ciudades.longitud()+1);
                }
            }
            System.out.println("Ciudades por las que pasa el tren: ");
            System.out.println(ciudades.toString());
            ManejadorDeArchivos.escribirLog("Consulta obtener las ciudades por las que pasa en tren: " + tren.getId());
        } else {
            System.out.println("El tren no esta asociado a ninguna linea");
        }
    }

    // punto 7: consulta sobre estaciones

    public static void consultaEstaciones() {
        int opcion;
        String nombre;
        System.out.println("Que consulta desea hacer");
        System.out.println("1: Consultar la informacion de una estacion");
        System.out.println("2: Consultar las estaciones que contiene una subcadena");
        System.out.println("3: Para salir");
        opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nombre de la estacion");
                nombre = sc.nextLine();
                if (estaciones.pertenece(nombre)) {
                    Estacion estacion = (Estacion) estaciones.obtener(nombre);
                    System.out.println(estacion.toString());
                    ManejadorDeArchivos.escribirLog("Consulta: informacion de la estacion " + nombre);
                } else {
                    System.out.println("La estacion con el nombre: " + nombre + " no existe");
                }
                break;
            case 2:
                infoEstacionComienzanCon();
                break;
            default:
                break;
        }

    }

    public static void infoEstacionComienzanCon() {
        String nombre;
        System.out.println("Ingrese el nombre de la estacion o una parte de su nombre");
        nombre = sc.nextLine();
        Lista filtrados = new Lista();
        Lista nombresEstaciones = estaciones.listarInorden();// pido todas las claves para compararlas ya que son string
        // Se utilizo el metodo listarInorden porque ya se encontraba implementado desde
        // el arbol binario
        String aux= nombre.toLowerCase();
        String cadena, cadenaAux;
        for (int i = 1; i <= nombresEstaciones.longitud(); i++) {
            cadena = (String) nombresEstaciones.recuperar(i);
            cadenaAux = cadena.toLowerCase();
            if (cadenaAux.startsWith(aux)) {
                filtrados.insertar(cadena, filtrados.longitud() + 1);
            }
        }
        // Con las cadenas filtradas recupero las estaciones y muestro su informacion
        if (!filtrados.esVacia()) {
            System.out.println("Las estaciones que empiezan con: " + nombre + " son:");
            Estacion encontrado;
            for (int i = 1; i <= filtrados.longitud(); i++) {
                encontrado = (Estacion) estaciones.obtener((String)filtrados.recuperar(i));
                System.out.println("[" + encontrado.toString() + "]");
            }
            ManejadorDeArchivos.escribirLog("Consulta de las estaciones que inician con: " + nombre);
        } else {
            System.out.println("No existen estaciones que comiencen con: " + nombre + "");
        }
    }

    // punto 8 consultas sobre viajes de una estacion A a una estacion B

    public static void consultasViajes() {
        int opcion;
        System.out.println("Que consulta desea hacer");
        System.out.println("1: Consultar un viaje que pase por la menor cantidad de estaciones");
        System.out.println("2: Consultar el viaje qque recorra menos kilometros");
        System.out.println("3: Consultar todos los caminos posibles sin pasar por X estacion");
        System.out.println("4: Consultar un recorrido con una cantidad maxima de kilometros");
        System.out.println("5: Para salir");
        opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                menorCamino();
                break;
            case 2:
                caminoConMenosDistancia();
                break;
            case 3:
                caminosSinEstacion();
                break;
            case 4:
                caminoConLimiteKm();
                break;
            default:
                break;
        }

    }

    // camino que llega de A a B y pasa por menos estaciones
    public static void menorCamino() {
        String estacion1, estacion2;
        System.out.println("ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("ingrese la segunda estacion");
        estacion2 = sc.nextLine();

        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no existe");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no existe");
        } else {
            Lista recorrido = vias.caminoMasCorto(estacion1, estacion2);
            if (!recorrido.esVacia()) {
                System.out.println(
                        "El camino que pasa por menos estaciones entre: " + estacion1 + " y " + estacion2 + " es:");
                System.out.println(recorrido.toString());
                ManejadorDeArchivos.escribirLog(
                        "Consulta: camino que pasa por menos estaciones entre " + estacion1 + " y " + estacion2);
            }
        }
    }

    // camino que llega de A a B en menor distancia en kilómetros
    public static void caminoConMenosDistancia() {
        String estacion1, estacion2;
        System.out.println("ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("ingrese la segunda estacion");
        estacion2 = sc.nextLine();

        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no existe");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no existe");
        } else {
            Lista recorrido = vias.caminoMenorPeso(estacion1, estacion2);
            if (!recorrido.esVacia()) {
                System.out.println("El camino mas corto entre: " + estacion1 + " y " + estacion2 + " es:");
                System.out.println(recorrido.toString());
                ManejadorDeArchivos
                        .escribirLog("Consulta: camino con menos km entre: " + estacion1 + " y " + estacion2);

            }
        }
    }

    /*
     * Obtener todos los caminos posibles para llegar de A a B sin pasar por una
     * estación C dada
     */
    // preguntar si es necesario todos los caminos o con 1 es suficiente
    public static void caminosSinEstacion() {
        String estacion1, estacion2, estacionEvitar;
        System.out.println("ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("ingrese la segunda estacion");
        estacion2 = sc.nextLine();
        System.out.println("ingrese la estacion que se quiere evitar");
        estacionEvitar = sc.nextLine();

        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no existe");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no existe");
        } else if (!estaciones.pertenece(estacionEvitar)) {
            System.out.println("La estacion: " + estacionEvitar + ", que se quiere evitar no existe");
        } else {
            Lista caminos = vias.caminosSinUnVertice(estacion1, estacion2, estacionEvitar);
            if (!caminos.esVacia()) {
                System.out.println("Los caminos posibles son:");
                Lista aux = new Lista();
                for (int i = 1; i <= caminos.longitud(); i++) {
                    aux = (Lista) caminos.recuperar(i);
                    System.out.println(aux.toString() + "\n");
                }
                ManejadorDeArchivos.escribirLog("Consulta: todos los caminos que pasa por " + estacion1 + " y "
                        + estacion2 + " sin pasar por " + estacionEvitar);
            } else {
                System.out.println("No existen caminos posibles desde " + estacion1 + " a " + estacion2
                        + " sin pasar por " + estacionEvitar);
            }
        }
    }

    // Verificar si es posible llegar de A a B recorriendo como máximo una cantidad
    // X de kilómetros
    public static void caminoConLimiteKm() {
        String estacion1, estacion2;
        int limite;
        System.out.println("ingrese la primera estacion");
        estacion1 = sc.nextLine();
        System.out.println("ingrese la segunda estacion");
        estacion2 = sc.nextLine();
        System.out.println("Ingrese la cantidad limite de kilometros");
        limite = sc.nextInt();

        if (!estaciones.pertenece(estacion1)) {
            System.out.println("La estacion: " + estacion1 + " no existe");
        } else if (!estaciones.pertenece(estacion2)) {
            System.out.println("La estacion: " + estacion2 + " no existe");
        } else if (limite < 0) {
            System.out.println("kilometraje erroneo, pruebe otra vez");
        } else {
            Lista resultado = vias.caminoConLimiteKm(estacion1, estacion2, limite);
            if (!resultado.esVacia()) {
                System.out.println("Si existe un camino que tenga menos de " + limite + " kilometros:");
                System.out.println(resultado.toString());
                ManejadorDeArchivos.escribirLog(
                        "Consulta: camino entre " + estacion1 + " y " + estacion2 + " con limite de " + limite + " km");
            } else {
                System.out.println("No existe un camino que tenga como limite, " + limite + " kilometros");
            }
        }
    }

    // punto 9, muestra como esta organizado el sistema
    public static void mostrarSistema() {
        int opcion;
        System.out.println("-----Informacion del sistema-----");
        System.out.println("1: Consultar informacion de trenes");
        System.out.println("2: Consultar informacion de las estaciones");
        System.out.println("3: Consultar informacion de las vias");
        System.out.println("4: Consultar informacion de las lineas");
        System.out.println("5: Volver al menu principal");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println(trenes.toString());
                ManejadorDeArchivos.escribirLog("Consulta informacion de trenes");
                break;
            case 2:
                System.out.println(estaciones.toString());
                ManejadorDeArchivos.escribirLog("Consulta informacion de estaciones");
                break;
            case 3:
                System.out.println(vias.toString());
                ManejadorDeArchivos.escribirLog("Consulta informacion de vias");

                break;
            case 4:
                if (lineas.isEmpty()) {
                    System.out.println("No existe informacion de las lineas");
                } else {
                    lineas.forEach((linea, valor) -> {
                        System.out.println("Linea: " + linea + ", Estaciones: " + valor.toString());
                    });
                ManejadorDeArchivos.escribirLog("Consulta informacion de lineas");

                }
                break;
            default:
                break;
        }
    }

}
