package Clases;

import java.util.LinkedList;

public class Manager extends Persona {

    private int annosAfiliacion;

    private Equipo miEquipo;
    private LinkedList<Jugador> misJugadores;
    
    public Manager(String cedula, String nombre, String apellido, int edad, int annosAfiliacion) {
        super(cedula, nombre, apellido, edad);
        this.annosAfiliacion = annosAfiliacion;
        this.misJugadores = new LinkedList<>();
    }
    
    public Equipo getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public LinkedList<Jugador> getMisJugadores() {
        return misJugadores;
    }

    public void setMisJugadores(LinkedList<Jugador> misJugadores) {
        this.misJugadores = misJugadores;
    }

    public int getAnnosAfiliacion() {
        return annosAfiliacion;
    }

    public void setAnnosAfiliacion(int annosAfiliacion) {
        this.annosAfiliacion = annosAfiliacion;
    }

}
