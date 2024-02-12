/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Aficionado;
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
public class ModeloAficionado implements Interfaces.Repositorio, Interfaces.Persistencia{
    
    List <Aficionado> aficionados;

    public ModeloAficionado() {
        
        this.aficionados = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.aficionados.add((Aficionado) elObjeto);
    }

    @Override
    public Object get(String id) {

        Aficionado encontrado = null;
        int i = 0;

        while (i < aficionados.size() && encontrado == null) {

            if (this.aficionados.get(i).getCedula().equals(id)) {
                encontrado = this.aficionados.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Aficionado aficionadoActual : this.aficionados) {
            respuesta.add((Object) aficionadoActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Aficionado aficionadoNuevo = (Aficionado) elObjeto;
        Aficionado aficionadoActual = (Aficionado) get(aficionadoNuevo.getCedula());
        int indiceActual = this.aficionados.indexOf(aficionadoActual);

        if (aficionadoActual != null) {
            aficionadoActual = aficionadoNuevo;
            this.aficionados.set(indiceActual, aficionadoNuevo);

        }

        return aficionadoActual;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Aficionado buscado = (Aficionado) get(id);

        if (buscado != null) {
            exito = this.aficionados.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Aficionados.json")) {
            gson.toJson(this.aficionados, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Aficionados.json")) {
            Type type = new TypeToken<List<Aficionado>>() {
            }.getType();
            this.aficionados = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }

}
    
    
