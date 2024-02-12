/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Tecnico;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author daner
 */
public class ModeloTecnico implements Interfaces.Repositorio, Interfaces.Persistencia{

List <Tecnico> tecnicos;

    public ModeloTecnico() {
        this.tecnicos = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.tecnicos.add((Tecnico) elObjeto);
    }

    @Override
    public Object get(String id) {

        Tecnico encontrado = null;
        int i = 0;

        while (i < tecnicos.size() && encontrado == null) {

            if (this.tecnicos.get(i).getCedula().equals(id)) {
                encontrado = this.tecnicos.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Tecnico tecnicoActual : this.tecnicos) {
            respuesta.add((Object) tecnicoActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Tecnico tecnicoNuevo = (Tecnico) elObjeto;
        Tecnico tecnicoActual = (Tecnico) get(tecnicoNuevo.getCedula());
        int indiceActual = this.tecnicos.indexOf(tecnicoActual);

        if (tecnicoActual != null) {
            tecnicoActual = tecnicoNuevo;
            this.tecnicos.set(indiceActual, tecnicoNuevo);

        }

        return tecnicoActual;
    }

    @Override
    public boolean delete(String id) {
        boolean exito = false;
        Tecnico buscado = (Tecnico) get(id);

        if (buscado != null) {
            exito = this.tecnicos.remove(buscado);
        }
        return exito;    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Tecnicos.json")) {
            gson.toJson(this.tecnicos, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Tecnicos.json")) {
            Type type = new TypeToken<List<Tecnico>>() {
            }.getType();
            this.tecnicos = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }
    
}

    
    

