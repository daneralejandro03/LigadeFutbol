/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Aficionado;
import Clases.Competencia;
import Clases.Equipo;
import Clases.Jornada;
import Clases.Jugador;
import Clases.Partido;
import Clases.Persona;
import Clases.Tecnico;
import Modelos.ModeloEquipo;
import Vistas.Formulario;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daner
 */
public class ControladorEquipo implements Interfaces.Repositorio {

    ModeloEquipo miModeloEquipo;

    public ControladorEquipo() {

        this.miModeloEquipo = new ModeloEquipo();
    }

    public ModeloEquipo getMiModeloEquipo() {
        return miModeloEquipo;
    }

    public void setMiModeloEquipo(ModeloEquipo miModeloEquipo) {
        this.miModeloEquipo = miModeloEquipo;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;
        exito = this.miModeloEquipo.create(elObjeto);

        return exito;
    }

    @Override
    public Object get(String id) {
        Equipo encontrado = null;

        Equipo equipoEncontrado = (Equipo) this.miModeloEquipo.get(id);

        if (equipoEncontrado != null) {
            encontrado = equipoEncontrado;
        }
        return encontrado;
    }

    @Override
    public LinkedList<Object> index() {

        LinkedList<Object> equipos = new LinkedList<>();
        equipos.addAll(this.miModeloEquipo.index());
        return equipos;
    }

    @Override
    public Object update(Object elObjeto) {

        Equipo actualizada = null;
        actualizada = (Equipo) this.miModeloEquipo.update(elObjeto);

        return actualizada;
    }

    @Override
    public boolean delete(String id) {
        boolean exito = false;

        exito = this.miModeloEquipo.delete(id);

        return exito;
    }

    //Relaciones
    public void setCompetencia(String idEquipo, String idCompetencia) {

        Equipo equipoActual = (Equipo) this.get(idEquipo);
        equipoActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetencia(String idEquipo, String idCompetencia) {

        Competencia eliminar = null;
        Equipo equipoActual = (Equipo) this.get(idEquipo);
        equipoActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Equipo> getEquiposCompetencia(String id) {
        LinkedList<Object> equipos = this.index();
        LinkedList<Equipo> respuesta = new LinkedList<>();
        LinkedList<Equipo> respuesta2 = new LinkedList<>();

        for (Object equipo : equipos) {
            respuesta2.add((Equipo) equipo);
        }

        for (Equipo equipoActual : respuesta2) {
            if (equipoActual.getMiCompetencia() != null && equipoActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add(equipoActual);
            }
        }

        return respuesta;
    }

    //Cantidad de partidos en el que un equipo ganó por goleada, quiere decir que el ganador sacó 4 goles de diferencia al contrincante.
    public int cantidadDePartidosGoleada() {
        int respuesta = 0;

        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        ControladorPartido auxiliar = new ControladorPartido();

        for (Equipo equipoActual : equipos) {
            for (Partido partidoActual : auxiliar.getPartidosEquipo(equipoActual.getId())) {
                int golesLocal = partidoActual.getGolesLocal();
                int golesVisitante = partidoActual.getGolesVisitante();
                int diferencia = golesLocal - golesVisitante;

                int abs = Math.abs(diferencia);
                if (abs >= 4) {
                    respuesta++;
                }
            }

        }

        respuesta = respuesta / 2;

        return respuesta;
    }

    //Arquero que le han marcado menos goles, esta información se puede obtener de los goles en contra.
    public Jugador arqueroMenosGolesEnContra() {

        Jugador respuesta = null;
        Equipo equipoMenosVencido = null;
        int goles = Integer.MAX_VALUE;

        ControladorPersona auxiliar = new ControladorPersona();

        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        for (Equipo equipoActual : equipos) {
            if (equipoActual.getGolesContra() < goles) {
                goles = equipoActual.getGolesContra();
                equipoMenosVencido = equipoActual;
            }
        }

        for (Jugador jugadorActual : auxiliar.getJugadoresEquipoJugador(equipoMenosVencido.getId())) {
            if (jugadorActual.getPosicion().equalsIgnoreCase("Arquero") || jugadorActual.getPosicion().equalsIgnoreCase("Portero")) {
                respuesta = jugadorActual;
            }
        }

        return respuesta;
    }

    //Equipo con mayor cantidad de aficionados
    public Equipo equipoMayorCantidadAficionados() {

        Equipo respuesta = null;
        int cantidad = 0;
        int mayor = Integer.MIN_VALUE;

        ControladorPersona auxiliar = new ControladorPersona();

        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        for (Equipo equipoActual : equipos) {
            for (Aficionado aficionadoActual : auxiliar.getAficionadoEquipo(equipoActual.getId())) {
                cantidad++;
            }

            if (cantidad > mayor) {
                mayor = cantidad;
                respuesta = equipoActual;
            }

            cantidad = 0;
        }
        return respuesta;
    }

    //Equipo con nómina de jugadores y técnico más alta.
    public Equipo nominaJuadorTecnicoMasAlta() {

        Equipo respuesta = null;
        double maximo = Double.MIN_VALUE;

        ControladorPersona auxiliar = new ControladorPersona();
        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        for (Equipo equipoActual : equipos) {
            Tecnico tecnico = auxiliar.getTecnicoEquipo(equipoActual.getId());
            
            double nominaEquipo = tecnico.getSalario();
            
            for (Jugador jugadorActual : auxiliar.getJugadoresEquipoJugador(equipoActual.getId())) {
                nominaEquipo += jugadorActual.getSalario();
            }

            if (nominaEquipo > maximo) {
                maximo = nominaEquipo;
                respuesta = equipoActual;
            }

        }

        return respuesta;
    }

    //Promedio de edad por cada uno de los equipos
    public List<Double> promedioEdadEquipos() {

        List<Double> promedios = new LinkedList<>();
        List<Jugador> jugadores = new LinkedList<>();

        ControladorPersona auxiliar = new ControladorPersona();
        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        for (Equipo equipoActual : equipos) {
            int totalEdad = 0;
            for (Jugador jugadorActual : auxiliar.getJugadoresEquipoJugador(equipoActual.getId())) {

                jugadores.add(jugadorActual);

                for (Jugador jugador : jugadores) {
                    totalEdad += jugador.getEdad();

                }

                if (!jugadores.isEmpty()) {
                    double promedio = totalEdad / jugadores.size();
                    promedios.add(promedio);
                } else {
                    double promedio = 0;
                    promedios.add(promedio);
                }
            }
        }

        return promedios;
    }

    //Nombre de los Equipos para los promedios
    public List<Equipo> nombreEquipos() {

        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        return equipos;
    }

    //Tabla de Psoiciones de los Equipos
    public LinkedList<Equipo> tablaPosiciones() {

        LinkedList<Equipo> equipos = new LinkedList<>();

        for (Object equipo : index()) {
            equipos.add((Equipo) equipo);
        }

        Collections.sort(equipos, new Comparator<Equipo>() {
            public int compare(Equipo equipo1, Equipo equipo2) {
                int puntos1 = equipo1.getPuntos();
                int puntos2 = equipo2.getPuntos();
                int diferenciaGoles1 = equipo1.getGolesFavor() - equipo1.getGolesContra();
                int diferenciaGoles2 = equipo2.getGolesFavor() - equipo2.getGolesContra();

                if (puntos1 != puntos2) {
                    return Integer.compare(puntos2, puntos1);
                } else {
                    return Integer.compare(diferenciaGoles2, diferenciaGoles1);
                }
            }
        });

        System.out.println("Tabla de Posiciones:");
        System.out.println("Equipo\t\tPuntos\tDiferencia de Goles");
        for (int i = 0; i < equipos.size(); i++) {
            Equipo equipo = equipos.get(i);
            System.out.println(equipo.getNombre() + "\t\t" + equipo.getPuntos() + "\t" + (equipo.getGolesFavor() - equipo.getGolesContra()));
        }

        return equipos;
    }
    
    //La cantidad de goles que marco el equipo con mas puntos
    
    public int cantidadGolesMarcoEquipoMasPuntos(){
        
        int cantidadGoles = 0;
        int maximoPuntos = Integer.MIN_VALUE;
        
        
        LinkedList <Equipo> equipos = new LinkedList<>();
        
        for (Object equipo:index()){
            equipos.add((Equipo) equipo);
        }
        
        for (Equipo equipoActual : equipos){
            
            if(equipoActual.getPuntos() > maximoPuntos){
                
                maximoPuntos = equipoActual.getPuntos();
                cantidadGoles = equipoActual.getGolesFavor();
                
                
            }
        }
        
        return cantidadGoles;
    }

}
