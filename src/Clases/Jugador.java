package Clases;

public class Jugador extends Persona {

    private String nacionalidad;
    private String posicion;
    private int numeroGoles;
    private double salario;

    private Equipo miEquipo;
    private Manager miManager;

    public Jugador(String cedula, String nombre, String apellido, int edad, String nacionalidad, String posicion, int numeroGoles, double salario) {
        super(cedula, nombre, apellido, edad);
        this.nacionalidad = nacionalidad;
        this.posicion = posicion;
        this.numeroGoles = numeroGoles;
        this.salario = salario;
    }

    public Manager getMiManager() {
        return miManager;
    }

    public void setMiManager(Manager miManager) {
        this.miManager = miManager;
    }

    public Equipo getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getNumeroGoles() {
        return numeroGoles;
    }

    public void setNumeroGoles(int numeroGoles) {
        this.numeroGoles = numeroGoles;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

}
