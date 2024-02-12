/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Competencia;
import Clases.Equipo;
import Clases.Jornada;
import Clases.Partido;
import Modelos.ModeloJornada;
import java.util.LinkedList;

/**
 *
 * @author daner
 */
public class ControladorJornada implements Interfaces.Repositorio {

    ModeloJornada miModeloJornada;

    public ControladorJornada() {

        this.miModeloJornada = new ModeloJornada();
    }

    public ModeloJornada getMiModeloJornada() {
        return miModeloJornada;
    }

    public void setMiModeloJornada(ModeloJornada miModeloJornada) {
        this.miModeloJornada = miModeloJornada;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;
        exito = this.miModeloJornada.create(elObjeto);

        return exito;
    }

    @Override
    public Object get(String id) {

        Jornada encontrado = null;

        Jornada jornadaEncontrada = (Jornada) this.miModeloJornada.get(id);

        if (jornadaEncontrada != null) {
            encontrado = jornadaEncontrada;
        }
        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> jornadas = new LinkedList<>();
        jornadas.addAll(this.miModeloJornada.index());
        return jornadas;
    }

    @Override
    public Object update(Object elObjeto) {

        Jornada actualizada = null;
        actualizada = (Jornada) this.miModeloJornada.update(elObjeto);

        return actualizada;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        exito = this.miModeloJornada.delete(id);
        return exito;
    }

    //Relaciones
    public void setCompetencia(String idJornada, String idCompetencia) {

        Jornada jornadaActual = (Jornada) this.get(idJornada);
        jornadaActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetencia(String idJornada, String idCompetencia) {

        Competencia eliminar = null;
        Jornada jornadaActual = (Jornada) this.get(idJornada);
        jornadaActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Jornada> getJornadaCompetencia(String id) {
        LinkedList<Object> jornadas = this.index();
        LinkedList<Jornada> respuesta = new LinkedList<>();
        LinkedList<Jornada> respuesta2 = new LinkedList<>();

        for (Object jornada : jornadas) {
            respuesta2.add((Jornada) jornada);
        }

        for (Jornada jornadaActual : respuesta2) {
            if (jornadaActual.getMiCompetencia() != null && jornadaActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add(jornadaActual);
            }
        }

        return respuesta;
    }

    //Identificador del partido el cual tuvo más goles
    public String identificadorPartidoMasGoles() {

        String respuesta = "";
        int maximo = Integer.MIN_VALUE;

        LinkedList<Jornada> jornadas = new LinkedList<>();

        ControladorPartido auxiliar = new ControladorPartido();

        for (Object jornada : index()) {
            jornadas.add((Jornada) jornada);
        }

        for (Jornada jornadaActual : jornadas) {
            for (Partido partidoActual : auxiliar.getPartidosJornada(jornadaActual.getId())) {

                int totalGoles = partidoActual.getGolesLocal() + partidoActual.getGolesVisitante();

                if (totalGoles > maximo) {
                    maximo = totalGoles;
                    respuesta = partidoActual.getId();
                }
            }
        }

        return respuesta;
    }

}
