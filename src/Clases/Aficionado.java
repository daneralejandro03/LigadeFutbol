package Clases;

public class Aficionado extends Persona {

    private int annosFidelidad;
    private boolean abonado;

    private Equipo miEquipo;

    public Aficionado(String cedula, String nombre, String apellido, int edad, int annosFidelidad, boolean abonado) {
        super(cedula, nombre, apellido, edad);
        this.annosFidelidad = annosFidelidad;
        this.abonado = abonado;
    }

    public Equipo getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public int getAnnosFidelidad() {
        return annosFidelidad;
    }

    public void setAnnosFidelidad(int annosFidelidad) {
        this.annosFidelidad = annosFidelidad;
    }

    public boolean isAbonado() {
        return abonado;
    }

    public void setAbonado(boolean abonado) {
        this.abonado = abonado;
    }

}
