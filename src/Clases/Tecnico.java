package Clases;

public class Tecnico extends Persona {

    private int annosExperiencia;
    private double salario;

    private Equipo miEquipo;

    public Tecnico(String cedula, String nombre, String apellido, int edad, int annosExperiencia, double salario) {
        super(cedula, nombre, apellido, edad);
        this.annosExperiencia = annosExperiencia;
        this.salario = salario;
    }

    public Equipo getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public int getAnnosExperiencia() {
        return annosExperiencia;
    }

    public void setAnnosExperiencia(int annosExperiencia) {
        this.annosExperiencia = annosExperiencia;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

}
