/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Estadio;
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
public class ModeloEstadio implements Interfaces.Repositorio, Interfaces.Persistencia{
    
    List <Estadio> estadios;

    public ModeloEstadio() {
        
        this.estadios = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.estadios.add((Estadio) elObjeto);
        
    }

    @Override
    public Object get(String id) {

        Estadio encontrado = null;
        int i = 0;

        while (i < estadios.size() && encontrado == null) {
            if (this.estadios.get(i).getId().equals(id)) {
                encontrado = this.estadios.get(i);
            }

            i++;
        }

        return encontrado;

    }

    @Override
    public LinkedList<Object> index() {
        LinkedList<Object> respuesta = new LinkedList<>();
        for (Estadio estadioActual : this.estadios){
            respuesta.add((Object) estadioActual);
        }
        return respuesta;        
    }

    @Override
    public Object update(Object elObjeto) {
        Estadio estadioNuevo = (Estadio) elObjeto;
        Estadio estadioActual = (Estadio) get(estadioNuevo.getId());
        int indiceActual = this.estadios.indexOf(estadioActual);

        if (estadioActual != null) {
            estadioActual = estadioNuevo;
            this.estadios.set(indiceActual, estadioNuevo);

        }

        return estadioActual;    
    }

    @Override
    public boolean delete(String id) {
      
        boolean exito = false;
        Estadio buscado = (Estadio) get(id);

        if (buscado != null) {
            exito = this.estadios.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Estadios.json")) {
            gson.toJson(this.estadios, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Estadios.json")) {
            Type type = new TypeToken<List<Estadio>>() {
            }.getType();
            this.estadios = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }

    }
    
    
    
    
}
