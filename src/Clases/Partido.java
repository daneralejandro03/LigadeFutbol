package Clases;

public class Partido {

    private String id;
    private String fecha;
    private int puntosGanador;
    private String equipoLocal;
    private int golesLocal;
    private String equipoVisitante;
    private int golesVisitante;

    private Arbitro miArbitro;
    private Equipo miEquipo;
    private Jornada miJornada;

    private Estadio miEstadio;

    public Partido(String id, String fecha, int puntosGanador, String equipoLocal, int golesLocal, String equipoVisitante, int golesVisitante) {
        this.id = id;
        this.fecha = fecha;
        this.puntosGanador = puntosGanador;
        this.equipoLocal = equipoLocal;
        this.golesLocal = golesLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesVisitante = golesVisitante;
    }


    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Estadio getMiEstadio() {
        return miEstadio;
    }

    public void setMiEstadio(Estadio miEstadio) {
        this.miEstadio = miEstadio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public int getPuntosGanador() {
        return puntosGanador;
    }

    public void setPuntosGanador(int puntosGanador) {
        this.puntosGanador = puntosGanador;
    }

    public Arbitro getMiArbitro() {
        return miArbitro;
    }

    public void setMiArbitro(Arbitro miArbitro) {
        this.miArbitro = miArbitro;
    }

    public Equipo getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public Jornada getMiJornada() {
        return miJornada;
    }

    public void setMiJornada(Jornada miJornada) {
        this.miJornada = miJornada;
    }

}
