/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Competencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author daner
 */
public class ModeloCompetencia implements Interfaces.Repositorio, Interfaces.Persistencia {

    List<Competencia> competencias;

    public ModeloCompetencia() {
        this.competencias = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.competencias.add((Competencia) elObjeto);

    }

      @Override
    public Object get(String id) {

        Competencia encontrado = null;
        int i = 0;

        while (i < competencias.size() && encontrado == null) {
            if (this.competencias.get(i).getId().equals(id)) {
                encontrado = this.competencias.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Competencia competenciaActual : this.competencias){
            respuesta.add((Object) competenciaActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Competencia competenciaNueva = (Competencia) elObjeto;
        Competencia competenciaActual = (Competencia) get(competenciaNueva.getId());
        int indiceActual = this.competencias.indexOf(competenciaActual);

        if (competenciaActual != null) {
            competenciaActual = competenciaNueva;
            this.competencias.set(indiceActual, competenciaNueva);

        }

        return competenciaActual;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Competencia buscado = (Competencia) get(id);

        if (buscado != null){
               exito = this.competencias.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Competencias.json")) {
            gson.toJson(this.competencias, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }

    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Competencias.json")) {
            Type type = new TypeToken<List<Competencia>>() {
            }.getType();
            this.competencias = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }

}
