package Clases;

public class Arbitro extends Persona {

    private boolean arbitroFifa;

    private Partido miPartido;

    public Arbitro(String cedula, String nombre, String apellido, int edad, boolean arbitroFifa) {
        super(cedula, nombre, apellido, edad);
        this.arbitroFifa = arbitroFifa;
    }

    public Partido getMiPartido() {
        return miPartido;
    }

    public void setMiPartido(Partido miPartido) {
        this.miPartido = miPartido;
    }

    public boolean isArbitroFifa() {
        return arbitroFifa;
    }

    public void setArbitroFifa(boolean arbitroFifa) {
        this.arbitroFifa = arbitroFifa;
    }

}
