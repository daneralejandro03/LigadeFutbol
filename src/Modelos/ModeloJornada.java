/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Jornada;
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
public class ModeloJornada implements Interfaces.Repositorio, Interfaces.Persistencia{
    
    List<Jornada> jornadas;

    public ModeloJornada() {
        
        this.jornadas = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {
        return this.jornadas.add((Jornada) elObjeto);
    }

    @Override
    public Object get(String id) {

        Jornada encontrado = null;
        int i = 0;

        while (i < jornadas.size() && encontrado == null) {
            if (this.jornadas.get(i).getId().equals(id)) {
                encontrado = this.jornadas.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Jornada jornadaActual : this.jornadas) {
            respuesta.add((Object) jornadaActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Jornada jornadaNueva = (Jornada) elObjeto;
        Jornada jornadaActual = (Jornada) get(jornadaNueva.getId());
        int indiceActual = this.jornadas.indexOf(jornadaActual);

        if (jornadaActual != null) {
            jornadaActual = jornadaNueva;
            this.jornadas.set(indiceActual, jornadaNueva);

        }

        return jornadaActual;  
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Jornada buscado = (Jornada) get(id);

        if (buscado != null) {
            exito = this.jornadas.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Jornadas.json")) {
            gson.toJson(this.jornadas, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
        
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Jornadas.json")) {
            Type type = new TypeToken<List<Jornada>>() {
            }.getType();
            this.jornadas = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }
    
    
    
    
}
