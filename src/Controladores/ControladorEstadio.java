/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Competencia;
import Clases.Estadio;
import Clases.Partido;
import Modelos.ModeloEstadio;
import java.util.LinkedList;

/**
 *
 * @author daner
 */
public class ControladorEstadio implements Interfaces.Repositorio {

    ModeloEstadio miModeloEstadio;

    public ControladorEstadio() {

        this.miModeloEstadio = new ModeloEstadio();
    }

    public ModeloEstadio getMiModeloEstadio() {
        return miModeloEstadio;
    }

    public void setMiModeloEstadio(ModeloEstadio miModeloEstadio) {
        this.miModeloEstadio = miModeloEstadio;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;
        exito = this.miModeloEstadio.create(elObjeto);

        return exito;
    }

    @Override
    public Object get(String id) {
        Estadio encontrado = null;

        Estadio estadioEncontrado = (Estadio) this.miModeloEstadio.get(id);

        if (estadioEncontrado != null) {
            encontrado = estadioEncontrado;
        }
        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> estadios = new LinkedList<>();
        estadios.addAll(this.miModeloEstadio.index());
        return estadios;

    }

    @Override
    public Object update(Object elObjeto) {

        Estadio actualizada = null;
        actualizada = (Estadio) this.miModeloEstadio.update(elObjeto);

        return actualizada;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        exito = this.miModeloEstadio.delete(id);
        return exito;
    }

    //Relaciones
    public void setCompetencia(String idEstadio, String idCompetencia) {

        Estadio estadioActual = (Estadio) this.get(idEstadio);
        estadioActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetencia(String idEstadio, String idCompetencia) {

        Competencia eliminar = null;
        Estadio estadioActual = (Estadio) this.get(idEstadio);
        estadioActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Estadio> getJornadaCompetencia(String id) {
        LinkedList<Object> estadios = this.index();
        LinkedList<Estadio> respuesta = new LinkedList<>();
        LinkedList<Estadio> respuesta2 = new LinkedList<>();

        for (Object estadio : estadios) {
            respuesta2.add((Estadio) estadio);
        }

        for (Estadio estadioActual : respuesta2) {
            if (estadioActual.getMiCompetencia() != null && estadioActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add(estadioActual);
            }
        }

        return respuesta;
    }

    public String nombreEstadioMasGoles() {

        String respuesta = "";
        int maximo = Integer.MIN_VALUE;
        
        ControladorPartido auxiliar = new ControladorPartido();
        LinkedList<Estadio> estadios = new LinkedList<>();
        
        for (Object estadio : index()){
            estadios.add((Estadio) estadio);
        }

        for (Estadio estadioActual : estadios) {
            for (Partido partidoActual : auxiliar.getPartidosEquipo(estadioActual.getId())) {

                int totalGoles = partidoActual.getGolesLocal() + partidoActual.getGolesVisitante();

                if (totalGoles > maximo) {
                    maximo = totalGoles;
                    respuesta = estadioActual.getNombre();
                }
            }
        }

        return respuesta;
    }
    
}
