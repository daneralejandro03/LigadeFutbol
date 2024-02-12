package Clases;

public class Liga_Nacional_de_Futbol {

    private String id;
    private String nombre;

    public Liga_Nacional_de_Futbol(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public static void main(String[] args) {
        
    }

}
