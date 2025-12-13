package elementos;

public class Tren {
    /*
    se almacenará para cada tren un
identificador numérico único, el tipo de propulsión (electricidad, diesel, fuel oil, gasolina,
híbrido), cantidad de vagones para pasajeros, cantidad de vagones para carga y la línea en la
que está siendo utilizado (si el tren no está destinado a ninguna línea se considerará libre).

    */
   private int id;
   private String propulsion;
   private int vagonesPasajeros;
   private int vagonesCarga;
   private String linea;

   public Tren (int id, String prop, int vagonesp, int vagonesc, String lin){
    this.id=id;
    this.propulsion=prop;
    this.vagonesPasajeros=vagonesp;
    this.vagonesCarga=vagonesc;
    this.linea=lin;
   }

   public int getId() {
       return id;
   }

   public String getPropulsion() {
       return propulsion;
   }

   public int getVagonesPasajeros() {
       return vagonesPasajeros;
   }

   public int getVagonesCarga() {
       return vagonesCarga;
   }

   public String getLinea() {
       return linea;
   }

   public void setId(int id) {
       this.id = id;
   }

   public void setPropulsion(String propulsion) {
       this.propulsion = propulsion;
   }

   public void setVagonesPasajeros(int vagonesPasajeros) {
       this.vagonesPasajeros = vagonesPasajeros;
   }

   public void setVagonesCarga(int vagonesCarga) {
       this.vagonesCarga = vagonesCarga;
   }

   public void setLinea(String linea) {
       this.linea = linea;
   }

   @Override
   public String toString() {
    String cad;
    cad="Tren id:"+id+", propulsion: "+propulsion+", cantidad de vagones pasajeros: "+vagonesPasajeros+
    ", cantidad de vagones de carga:"+vagonesCarga+", linea:"+linea;
       return cad;
   }
}
