package Clases;

public abstract class Persona {

    private String cedula;
    private String nombre;
    private String apellido;
    private int edad;

    private Competencia miCompetencia;

    public Persona(String cedula, String nombre, String apellido, int edad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public Competencia getMiCompetencia() {
        return miCompetencia;
    }

    public void setMiCompetencia(Competencia miCompetencia) {
        this.miCompetencia = miCompetencia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

}
