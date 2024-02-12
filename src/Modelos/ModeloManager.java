/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Manager;
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
public class ModeloManager implements Interfaces.Repositorio, Interfaces.Persistencia{

    List<Manager> managers;

    public ModeloManager() {
        this.managers = new LinkedList<>();
        cargar();
    }
    
    @Override
    public boolean create(Object elObjeto) {

        return this.managers.add((Manager) elObjeto);
    }

    @Override
    public Object get(String id) {

        Manager encontrado = null;
        int i = 0;

        while (i < managers.size() && encontrado == null) {
            
            
            if (this.managers.get(i).getCedula().equals(id)) {
                encontrado = this.managers.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Manager managerActual : this.managers) {
            respuesta.add((Object) managerActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Manager managerNuevo = (Manager) elObjeto;
        Manager managerActual = (Manager) get(managerNuevo.getCedula());
        int indiceActual = this.managers.indexOf(managerActual);

        if (managerActual != null) {
            managerActual = managerNuevo;
            this.managers.set(indiceActual, managerNuevo);

        }

        return managerActual;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Manager buscado = (Manager) get(id);

        if (buscado != null) {
            exito = this.managers.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Managers.json")) {
            gson.toJson(this.managers, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Managers.json")) {
            Type type = new TypeToken<List<Manager>>() {
            }.getType();
            this.managers = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }
    
}
    
