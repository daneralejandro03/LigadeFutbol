/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Equipo;
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
public class ModeloEquipo implements Interfaces.Repositorio, Interfaces.Persistencia {

    List<Equipo> equipos;

    public ModeloEquipo() {
        this.equipos = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.equipos.add((Equipo) elObjeto);

    }

    @Override
    public Object get(String id) {
        Equipo encontrado = null;
        int i = 0;

        while (i < equipos.size() && encontrado == null) {
            if (this.equipos.get(i).getId().equals(id)) {
                encontrado = this.equipos.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {
        LinkedList<Object> respuesta = new LinkedList<>();
        for (Equipo equipoActual : this.equipos) {
            respuesta.add((Object) equipoActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Equipo equipoNuevo = (Equipo) elObjeto;
        Equipo equipoActual = (Equipo) get(equipoNuevo.getId());
        int indiceActual = this.equipos.indexOf(equipoActual);

        if (equipoActual != null) {
            equipoActual = equipoNuevo;
            this.equipos.set(indiceActual, equipoNuevo);

        }

        return equipoActual;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Equipo buscado = (Equipo) get(id);

        if (buscado != null) {
            exito = this.equipos.remove(buscado);
        }
        return exito;

    }

    @Override
    public void guardar() {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Equipos.json")) {
            gson.toJson(this.equipos, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Equipos.json")) {
            Type type = new TypeToken<List<Equipo>>() {
            }.getType();
            this.equipos = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }
}
