/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Partido;
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
public class ModeloPartido implements Interfaces.Repositorio, Interfaces.Persistencia{
    
    List<Partido> partidos;

    public ModeloPartido() {
        this.partidos = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.partidos.add((Partido) elObjeto);
    }

    @Override
    public Object get(String id) {

        Partido encontrado = null;
        int i = 0;

        while (i < partidos.size() && encontrado == null) {
            if (this.partidos.get(i).getId().equals(id)) {
                encontrado = this.partidos.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Partido partidoActual : this.partidos) {
            respuesta.add((Object) partidoActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {
        Partido partidoNuevo = (Partido) elObjeto;
        Partido partidoActual = (Partido) get(partidoNuevo.getId());
        int indiceActual = this.partidos.indexOf(partidoActual);

        if (partidoActual != null) {
            partidoActual = partidoNuevo;
            this.partidos.set(indiceActual, partidoNuevo);

        }

        return partidoActual;     
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Partido buscado = (Partido) get(id);

        if (buscado != null) {
            exito = this.partidos.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Partidos.json")) {
            gson.toJson(this.partidos, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Partidos.json")) {
            Type type = new TypeToken<List<Partido>>() {
            }.getType();
            this.partidos = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }
    
    
    
    
}
