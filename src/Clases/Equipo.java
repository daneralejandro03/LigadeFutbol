package Clases;

import java.util.LinkedList;

public class Equipo {

    private String id;
    private String nombre;
    private int annoFundacion;
    private int titulosNacionales;
    private int titulosInternaciones;
    private int puntos;
    private int golesFavor;
    private int golesContra;
    private int partidosJugados;

    private LinkedList<Jugador> misJugadores;
    private LinkedList<Aficionado> misAficionados;
    private LinkedList<Partido> misPartidos;
    private Tecnico miTecnico;
    private Manager miManager;

    private Competencia miCompetencia;

    public Equipo(String id, String nombre, int annoFundacion, int titulosNacionales, int titulosInternaciones, int puntos, int golesFavor, int golesContra, int partidosJugados) {
        this.id = id;
        this.nombre = nombre;
        this.annoFundacion = annoFundacion;
        this.titulosNacionales = titulosNacionales;
        this.titulosInternaciones = titulosInternaciones;
        this.puntos = puntos;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.partidosJugados = partidosJugados;
        this.misJugadores = new LinkedList<>();
        this.misAficionados = new LinkedList<>();
        this.misPartidos = new LinkedList<>();
    }

    public Competencia getMiCompetencia() {
        return miCompetencia;
    }

    public void setMiCompetencia(Competencia miCompetencia) {
        this.miCompetencia = miCompetencia;
    }

    public Manager getMiManager() {
        return miManager;
    }

    public void setMiManager(Manager miManager) {
        this.miManager = miManager;
    }

    public LinkedList<Jugador> getMisJugadores() {
        return misJugadores;
    }

    public void setMisJugadores(LinkedList<Jugador> misJugadores) {
        this.misJugadores = misJugadores;
    }

    public LinkedList<Aficionado> getMisAficionados() {
        return misAficionados;
    }

    public void setMisAficionados(LinkedList<Aficionado> misAficionados) {
        this.misAficionados = misAficionados;
    }

    public LinkedList<Partido> getMisPartidos() {
        return misPartidos;
    }

    public void setMisPartidos(LinkedList<Partido> misPartidos) {
        this.misPartidos = misPartidos;
    }

    public Tecnico getMiTecnico() {
        return miTecnico;
    }

    public void setMiTecnico(Tecnico miTecnico) {
        this.miTecnico = miTecnico;
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

    public int getAnnoFundacion() {
        return annoFundacion;
    }

    public void setAnnoFundacion(int annoFundacion) {
        this.annoFundacion = annoFundacion;
    }

    public int getTitulosNacionales() {
        return titulosNacionales;
    }

    public void setTitulosNacionales(int titulosNacionales) {
        this.titulosNacionales = titulosNacionales;
    }

    public int getTitulosInternaciones() {
        return titulosInternaciones;
    }

    public void setTitulosInternaciones(int titulosInternaciones) {
        this.titulosInternaciones = titulosInternaciones;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }
    
    
}
