/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Estadio;
import Clases.Jornada;
import Clases.Partido;
import Modelos.ModeloPartido;
import java.util.LinkedList;

/**
 *
 * @author daner
 */
public class ControladorPartido implements Interfaces.Repositorio {

    ModeloPartido miModeloPartido;

    public ControladorPartido() {
        miModeloPartido = new ModeloPartido();
    }

    public ModeloPartido getMiModeloPartido() {
        return miModeloPartido;
    }

    public void setMiModeloPartido(ModeloPartido miModeloPartido) {
        this.miModeloPartido = miModeloPartido;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;
        exito = this.miModeloPartido.create(elObjeto);

        return exito;
    }

    @Override
    public Object get(String id) {

        Partido encontrado = null;

        Partido partidoEncontrado = (Partido) this.miModeloPartido.get(id);

        if (partidoEncontrado != null) {
            encontrado = partidoEncontrado;
        }
        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> partidos = new LinkedList<>();
        partidos.addAll(this.miModeloPartido.index());
        return partidos;
    }

    @Override
    public Object update(Object elObjeto) {
        Partido actualizada = null;
        actualizada = (Partido) this.miModeloPartido.update(elObjeto);

        return actualizada;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        exito = this.miModeloPartido.delete(id);
        return exito;
    }

    //Relaciones
    public void setEstadio(String idPartido, String idEstadio) {

        Partido partidoActual = (Partido) this.get(idPartido);
        partidoActual.setMiEstadio(new Estadio(idEstadio, "", "", 0));

    }

    public void setEliminarEstadio(String idPartido, String idEstadio) {

        Estadio eliminar = null;
        Partido partidoActual = (Partido) this.get(idPartido);
        partidoActual.setMiEstadio(eliminar);

    }

    public LinkedList<Partido> getEstadiosCompetencia(String id) {
        LinkedList<Object> partidos = this.index();
        LinkedList<Partido> respuesta = new LinkedList<>();
        LinkedList<Partido> respuesta2 = new LinkedList<>();

        for (Object partido : partidos) {
            respuesta2.add((Partido) partido);
        }

        for (Partido partidoActual : respuesta2) {
            if (partidoActual.getMiEstadio() != null && partidoActual.getMiEstadio().getId().equals(id)) {
                respuesta.add(partidoActual);
            }
        }

        return respuesta;
    }

    public void setJornada(String idPartido, String idJornada) {

        Partido partidoActual = (Partido) this.get(idPartido);
        partidoActual.setMiJornada(new Jornada(idJornada, 0, ""));

    }

    public void setEliminarJornada(String idPartido, String idJornada) {

        Jornada eliminar = null;
        Partido partidoActual = (Partido) this.get(idPartido);
        partidoActual.setMiJornada(eliminar);

    }

    public LinkedList<Partido> getPartidosJornada(String id) {
        LinkedList<Object> partidos = this.index();
        LinkedList<Partido> respuesta = new LinkedList<>();
        LinkedList<Partido> respuesta2 = new LinkedList<>();

        for (Object partido : partidos) {
            respuesta2.add((Partido) partido);
        }

        for (Partido partidoActual : respuesta2) {
            if (partidoActual.getMiJornada() != null && partidoActual.getMiJornada().getId().equals(id)) {
                respuesta.add(partidoActual);
            }
        }

        return respuesta;
    }
    
    public LinkedList<Partido> getPartidosEquipo(String id) {
        LinkedList<Object> partidos = this.index();
        LinkedList<Partido> respuesta = new LinkedList<>();

        for (Object partidoActual : partidos) {
            if (((Partido) partidoActual).getEquipoLocal().equals(id) || ((Partido) partidoActual).getEquipoVisitante().equals(id)) {
                respuesta.add((Partido) partidoActual);
            }
        }

        return respuesta;
    }

}
