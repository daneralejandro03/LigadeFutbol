package Clases;

import java.util.LinkedList;

public class Jornada {

    private String id;
    private int numero;
    private String Eslogan;

    private LinkedList<Partido> misPartidos;

    private Competencia miCompetencia;

    public Jornada(String id, int numero, String Eslogan) {
        this.id = id;
        this.numero = numero;
        this.Eslogan = Eslogan;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEslogan() {
        return Eslogan;
    }

    public void setEslogan(String Eslogan) {
        this.Eslogan = Eslogan;
    }


}
