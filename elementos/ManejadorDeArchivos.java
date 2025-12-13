package elementos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import conjuntistas.Diccionario;
import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;

public class ManejadorDeArchivos {
    // clase para realizar la carga iniciar a partir de un txt y
    // escribir en el log por cada accion que sucede
    private static final String RUTA_LOG = "datos/log.txt";
    private static final String RUTA_CARGA_INICIAL = "datos/cargaInicial.txt";

    

    public static void cargarDatos(Diccionario estaciones, Diccionario trenes, HashMap<String, Lista> lineas,
            GrafoEtiquetado vias) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CARGA_INICIAL))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                switch (linea.charAt(0)) {
                    case 'E':
                        Estacion estacion = cargarEstacion(linea);
                        estaciones.insertar(estacion.getNombre(), estacion);
                        vias.insertarVertice(estacion.getNombre());
                        break;
                    case 'T':
                        Tren tren = cargaTren(linea);
                        trenes.insertar(tren.getId(), tren);
                        break;
                    case 'L':
                        // como cargar lista devuleve todo los elemento en 1 sola lista
                        // primero se carga el nombre al hasmap y luego se elimina de la lista para que
                        // solo queden estaciones
                        Lista lis = cargarLinea(linea);
                        if (!lis.esVacia()) {
                            String nombre = (String) lis.recuperar(1);
                            lis.eliminar(1);
                            lineas.put(nombre, lis);
                        }
                        break;
                    case 'R':
                        String[] datos = linea.split(";");
                        if (datos.length == 4) {
                            // verifico que esta cargada correctamente la via
                            // con el formato R;estacion;estacion;etiqueta

                            vias.insertarArco(Integer.parseInt(datos[3]), datos[1], datos[2]);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Estacion cargarEstacion(String info) {
        String[] datos = info.split(";");
        String nombre = datos[1];
        String calle = datos[2];
        int numero = Integer.parseInt(datos[3]);
        String ciudad = datos[4];
        int codPostal = Integer.parseInt(datos[5]);
        int cantVias = Integer.parseInt(datos[6]);
        int cantPlataformas = Integer.parseInt(datos[7]);
        Estacion estacion = new Estacion(nombre, calle, numero, ciudad, codPostal, cantVias, cantPlataformas);

        return estacion;
    }

    private static Tren cargaTren(String info) {
        String[] datos = info.split(";");
        int id = Integer.parseInt(datos[1]);
        String propulsion = datos[2];
        int vagonesPasajeros = Integer.parseInt(datos[3]);
        int vagonesCarga = Integer.parseInt(datos[4]);
        ;
        String linea = datos[5];
        Tren tren = new Tren(id, propulsion, vagonesPasajeros, vagonesCarga, linea);
        return tren;
    }

    private static Lista cargarLinea(String info) {
        // el metodo va a devolver el nombre de la linea al inicio y luego las
        // estaciones que recorre
        String[] datos = info.split(";");
        Lista elementos = new Lista();
        if (datos.length >= 3) {
            // corroboro que la linea tenga al menos la L inicial, el nombre y una estacion
            for (int i = 1; i < datos.length; i++) {
                elementos.insertar(datos[i], i);
            }
        }
        return elementos;
    }

    public static void escribirLog(String escribir) {
        //fecha y hora actual para el registro
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String tiempoActual = dtf.format(LocalDateTime.now());
        String lineaLog;
        if(escribir.equals("inicio")){
            lineaLog="--------Inicia la ejecucion del programa--------";
        }else{
            lineaLog = String.format("[%s] - %s", tiempoActual, escribir);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_LOG, true))) {
            bw.newLine();
            bw.write(lineaLog);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}
