package elementos;

public class Estacion {
    private String nombre;
    private String calle;
    private int numero;
    private String ciudad;
    private int codPostal;
    private int cantVias;
    private int cantPlataformas;    

    public Estacion (String nombre, String calle, int num,String ciudad, int codigo, int vias, int plataformas){
        this.nombre=nombre;
        this.calle=calle;
        this.numero=num;
        this.ciudad=ciudad;
        this.codPostal=codigo;
        this.cantVias=vias;
        this.cantPlataformas=plataformas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public int getCantVias() {
        return cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }

    public String toString(){
        String cadena=nombre+" Domicilio: "+calle+" "+numero+" ciudad: "+ciudad+" codigoPosal: "+codPostal+
        " vias: "+cantVias+" plataformas: "+cantPlataformas;
        return cadena;
    }



}
