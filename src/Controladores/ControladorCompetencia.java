/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Competencia;
import Modelos.ModeloCompetencia;
import java.util.LinkedList;

/**
 *
 * @author daner
 */
public class ControladorCompetencia implements Interfaces.Repositorio {

    ModeloCompetencia miModeloCompetencia;

    public ControladorCompetencia() {

        this.miModeloCompetencia = new ModeloCompetencia();
    }

    public ModeloCompetencia getMiModeloCompetencia() {
        return miModeloCompetencia;
    }

    public void setMiModeloCompetencia(ModeloCompetencia miModeloCompetencia) {
        this.miModeloCompetencia = miModeloCompetencia;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;
        exito = this.miModeloCompetencia.create(elObjeto);

        return exito;
    }

    @Override
    public Object get(String id) {
        Competencia encontrado = null;

        Competencia competenciaEncontrada = (Competencia) this.miModeloCompetencia.get(id);

        if (competenciaEncontrada != null) {
            encontrado = competenciaEncontrada;
        }
        return encontrado;

    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> competencias = new LinkedList<>();
        competencias.addAll(this.miModeloCompetencia.index());
        return competencias;

    }

    @Override
    public Object update(Object elObjeto) {

        Competencia actualizada = null;
        actualizada = (Competencia) this.miModeloCompetencia.update(elObjeto);

        return actualizada;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;

        exito = this.miModeloCompetencia.delete(id);

        return exito;

    }

}
