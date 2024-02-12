package Clases;

import java.util.LinkedList;

public class Estadio {

    private String id;
    private String nombre;
    private String ciudad;
    private int capacidad;

    private LinkedList<Partido> misPartidos;

    private Competencia miCompetencia;

    public Estadio(String id, String nombre, String ciudad, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.misPartidos = new LinkedList<>();
    }

    public Competencia getMiCompetencia() {
        return miCompetencia;
    }

    public void setMiCompetencia(Competencia miCompetencia) {
        this.miCompetencia = miCompetencia;
    }

    public LinkedList<Partido> getMisPartidos() {
        return misPartidos;
    }

    public void setMisPartidos(LinkedList<Partido> misPartidos) {
        this.misPartidos = misPartidos;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}
