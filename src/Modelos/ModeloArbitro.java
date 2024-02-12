/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Clases.Arbitro;
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
public class ModeloArbitro implements Interfaces.Repositorio, Interfaces.Persistencia {
    
    List<Arbitro> arbitros;

    public ModeloArbitro() {
        
        this.arbitros = new LinkedList<>();
        cargar();
    }

    @Override
    public boolean create(Object elObjeto) {

        return this.arbitros.add((Arbitro) elObjeto);
    }

    @Override
    public Object get(String id) {

        Arbitro encontrado = null;
        int i = 0;

        while (i < arbitros.size() && encontrado == null) {

            if (this.arbitros.get(i).getCedula().equals(id)) {
                encontrado = this.arbitros.get(i);
            }

            i++;
        }

        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> respuesta = new LinkedList<>();
        for (Arbitro arbitroActual : this.arbitros) {
            respuesta.add((Object) arbitroActual);
        }
        return respuesta;
    }

    @Override
    public Object update(Object elObjeto) {

        Arbitro arbitroNuevo = (Arbitro) elObjeto;
        Arbitro arbitroActual = (Arbitro) get(arbitroNuevo.getCedula());
        int indiceActual = this.arbitros.indexOf(arbitroActual);

        if (arbitroActual != null) {
            arbitroActual = arbitroNuevo;
            this.arbitros.set(indiceActual, arbitroNuevo);

        }

        return arbitroActual;
    }

    @Override
    public boolean delete(String id) {

        boolean exito = false;
        Arbitro buscado = (Arbitro) get(id);

        if (buscado != null) {
            exito = this.arbitros.remove(buscado);
        }
        return exito;
    }

    @Override
    public void guardar() {

        Gson gson = new Gson();
        try (Writer writer = new FileWriter("Arbitros.json")) {
            gson.toJson(this.arbitros, writer);
        } catch (Exception e) {
            System.out.println("Error en el Json");
        }
    }

    @Override
    public void cargar() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("Arbitros.json")) {
            Type type = new TypeToken<List<Arbitro>>() {
            }.getType();
            this.arbitros = gson.fromJson(reader, type);

        } catch (Exception e) {
            System.out.println("Error en la Carga");
        }
    }

}
  

