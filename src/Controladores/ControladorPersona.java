/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.Aficionado;
import Clases.Arbitro;
import Clases.Competencia;
import Clases.Equipo;
import Clases.Jugador;
import Clases.Manager;
import Clases.Partido;
import Clases.Persona;
import Clases.Tecnico;
import Modelos.ModeloAficionado;
import Modelos.ModeloArbitro;
import Modelos.ModeloJugador;
import Modelos.ModeloManager;
import Modelos.ModeloTecnico;
import java.util.LinkedList;

/**
 *
 * @author daner
 */
public class ControladorPersona implements Interfaces.Repositorio {

    ModeloManager miModeloManager;
    ModeloJugador miModeloJugador;
    ModeloAficionado miModeloAficionado;
    ModeloTecnico miModeloTecnico;
    ModeloArbitro miModeloArbitro;

    public ControladorPersona() {

        this.miModeloManager = new ModeloManager();
        this.miModeloJugador = new ModeloJugador();
        this.miModeloAficionado = new ModeloAficionado();
        this.miModeloTecnico = new ModeloTecnico();
        this.miModeloArbitro = new ModeloArbitro();

    }

    public ModeloManager getMiModeloManager() {
        return miModeloManager;
    }

    public void setMiModeloManager(ModeloManager miModeloManager) {
        this.miModeloManager = miModeloManager;
    }

    public ModeloJugador getMiModeloJugador() {
        return miModeloJugador;
    }

    public void setMiModeloJugador(ModeloJugador miModeloJugador) {
        this.miModeloJugador = miModeloJugador;
    }

    public ModeloAficionado getMiModeloAficionado() {
        return miModeloAficionado;
    }

    public void setMiModeloAficionado(ModeloAficionado miModeloAficionado) {
        this.miModeloAficionado = miModeloAficionado;
    }

    public ModeloTecnico getMiModeloTecnico() {
        return miModeloTecnico;
    }

    public void setMiModeloTecnico(ModeloTecnico miModeloTecnico) {
        this.miModeloTecnico = miModeloTecnico;
    }

    public ModeloArbitro getMiModeloArbitro() {
        return miModeloArbitro;
    }

    public void setMiModeloArbitro(ModeloArbitro miModeloArbitro) {
        this.miModeloArbitro = miModeloArbitro;
    }

    @Override
    public boolean create(Object elObjeto) {

        boolean exito = false;

        if (elObjeto instanceof Manager) {
            exito = this.miModeloManager.create(elObjeto);
        } else if (elObjeto instanceof Jugador) {
            exito = this.miModeloJugador.create(elObjeto);
        } else if (elObjeto instanceof Aficionado) {
            exito = this.miModeloAficionado.create(elObjeto);
        } else if (elObjeto instanceof Tecnico) {
            exito = this.miModeloTecnico.create(elObjeto);
        } else if (elObjeto instanceof Arbitro) {
            exito = this.miModeloArbitro.create(elObjeto);
        }

        return exito;
    }

    @Override
    public Object get(String id) {

        Persona encontrado = null;

        Manager managerEncontrado = (Manager) this.miModeloManager.get(id);
        Jugador jugadorEncontrado = (Jugador) this.miModeloJugador.get(id);
        Aficionado aficionadoEncontrado = (Aficionado) this.miModeloAficionado.get(id);
        Tecnico tecnicoEncontrado = (Tecnico) this.miModeloTecnico.get(id);
        Arbitro arbitroEncontrado = (Arbitro) this.miModeloArbitro.get(id);

        if (managerEncontrado != null) {
            encontrado = managerEncontrado;
        } else if (jugadorEncontrado != null) {
            encontrado = jugadorEncontrado;
        } else if (aficionadoEncontrado != null) {
            encontrado = aficionadoEncontrado;
        } else if (tecnicoEncontrado != null) {
            encontrado = tecnicoEncontrado;
        } else if (arbitroEncontrado != null) {
            encontrado = arbitroEncontrado;
        }

        return encontrado;
    }

    public LinkedList<Object> index(int i) {

        LinkedList<Object> personas = new LinkedList<>();

        switch (i) {
            case 1:

                personas.addAll(this.miModeloManager.index());
                break;

            case 2:

                personas.addAll(this.miModeloJugador.index());
                break;

            case 3:

                personas.addAll(this.miModeloAficionado.index());
                break;

            case 4:

                personas.addAll(this.miModeloTecnico.index());
                break;

            case 5:

                personas.addAll(this.miModeloArbitro.index());
                break;
            default:
                throw new AssertionError();
        }

        return personas;
    }

    @Override
    public Object update(Object elObjeto) {

        Persona actualizada = null;

        if (elObjeto instanceof Manager) {
            actualizada = (Manager) this.miModeloManager.update(elObjeto);
        } else if (elObjeto instanceof Jugador) {
            actualizada = (Jugador) this.miModeloJugador.update(elObjeto);
        } else if (elObjeto instanceof Aficionado) {
            actualizada = (Aficionado) this.miModeloAficionado.update(elObjeto);
        } else if (elObjeto instanceof Tecnico) {
            actualizada = (Tecnico) this.miModeloTecnico.update(elObjeto);
        } else if (elObjeto instanceof Arbitro) {
            actualizada = (Arbitro) this.miModeloArbitro.update(elObjeto);
        }

        return actualizada;
    }

    @Override
    public boolean delete(String id) {

        boolean exitoManager = false;
        boolean exitoJugador = false;
        boolean exitoAficionado = false;
        boolean exitoTecnico = false;
        boolean exitoArbitro = false;

        exitoManager = this.miModeloManager.delete(id);
        exitoJugador = this.miModeloJugador.delete(id);
        exitoAficionado = this.miModeloAficionado.delete(id);
        exitoTecnico = this.miModeloTecnico.delete(id);
        exitoArbitro = this.miModeloArbitro.delete(id);

        return exitoManager || exitoJugador || exitoAficionado || exitoTecnico || exitoArbitro;
    }

    @Override
    public LinkedList<Object> index() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //RELACIONES DE PERSONA
    //Manager
    public void setCompetenciaManager(String idManager, String idCompetencia) {

        Manager managerActual = (Manager) this.get(idManager);
        managerActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetenciaManager(String idManager, String idCompetencia) {

        Competencia eliminar = null;
        Manager managerActual = (Manager) this.get(idManager);
        managerActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Manager> getManagersCompetencia(String id) {
        LinkedList<Object> managers = this.index();
        LinkedList<Manager> respuesta = new LinkedList<>();
        LinkedList<Manager> respuesta2 = new LinkedList<>();

        for (Object manager : managers) {
            respuesta2.add((Manager) manager);
        }

        for (Manager managerActual : respuesta2) {
            if (managerActual.getMiCompetencia() != null && managerActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add((Manager) managerActual);
            }
        }

        return respuesta;
    }

    public void setEquipoManager(String idManager, String idEquipo) {

        Manager managerActual = (Manager) this.get(idManager);
        managerActual.setMiEquipo(new Equipo(idEquipo, "", 0, 0, 0, 0, 0, 0, 0));

    }

    public void setEliminarEquipoManager(String idManager, String idEquipo) {

        Equipo eliminar = null;
        Manager managerActual = (Manager) this.get(idManager);
        managerActual.setMiEquipo(eliminar);

    }

    public LinkedList<Manager> getManagersEquipo(String id) {
        LinkedList<Object> managers = this.index();
        LinkedList<Manager> respuesta = new LinkedList<>();
        LinkedList<Manager> respuesta2 = new LinkedList<>();

        for (Object manager : managers) {
            respuesta2.add((Manager) manager);
        }

        for (Manager managerActual : respuesta2) {
            if (managerActual.getMiEquipo() != null && managerActual.getMiEquipo().getId().equals(id)) {
                respuesta.add((Manager) managerActual);
            }
        }

        return respuesta;
    }

    //Jugador
    public void setCompetenciaJugador(String idJugador, String idCompetencia) {

        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetenciaJugador(String idJugador, String idCompetencia) {

        Competencia eliminar = null;
        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Jugador> getJugadorCompetencia(String id) {
        LinkedList<Object> jugadores = this.index();
        LinkedList<Jugador> respuesta = new LinkedList<>();
        LinkedList<Jugador> respuesta2 = new LinkedList<>();

        for (Object jugador : jugadores) {
            respuesta2.add((Jugador) jugador);
        }

        for (Jugador jugadorActual : respuesta2) {
            if (jugadorActual.getMiEquipo() != null && jugadorActual.getMiEquipo().getId().equals(id)) {
                respuesta.add((Jugador) jugadorActual);
            }
        }

        return respuesta;
    }

    //---------------------------------------------------------------------------------
    public void setEquipoJugador(String idJugador, String idEquipo) {
        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiEquipo(new Equipo(idEquipo, "", 0, 0, 0, 0, 0, 0, 0));

    }

    public void setEliminarEquipoJugador(String idJugador, String idEquipo) {

        Equipo eliminar = null;
        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiEquipo(eliminar);

    }

    public LinkedList<Jugador> getJugadoresEquipoJugador(String id) {
        LinkedList<Object> jugadores = this.index(2);
        LinkedList<Jugador> respuesta = new LinkedList<>();
        LinkedList<Jugador> respuesta2 = new LinkedList<>();

        for (Object jugador : jugadores) {
            respuesta2.add((Jugador) jugador);
        }

        for (Jugador jugadorActual : respuesta2) {
            if (jugadorActual.getMiEquipo() != null && jugadorActual.getMiEquipo().getId().equals(id)) {
                respuesta.add((Jugador) jugadorActual);
            }
        }

        return respuesta;
    }

    //-------------------------------------------------------------------------------------------------
    public void setPersonaJugador(String idJugador, String idManager) {

        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiManager(new Manager(idManager, "", "", 0, 0));

    }

    public void setEliminarPersonaJugador(String idJugador, String idManager) {

        Manager eliminar = null;
        Jugador jugadorActual = (Jugador) this.get(idJugador);
        jugadorActual.setMiManager(eliminar);

    }

    public LinkedList<Jugador> getJugadorPersona(String id) {
        LinkedList<Object> jugadores = this.index();
        LinkedList<Jugador> respuesta = new LinkedList<>();
        LinkedList<Jugador> respuesta2 = new LinkedList<>();

        for (Object jugador : jugadores) {
            respuesta2.add((Jugador) jugador);
        }

        for (Jugador jugadorActual : respuesta2) {
            if (jugadorActual.getMiManager() != null && jugadorActual.getMiManager().getCedula().equals(id)) {
                respuesta.add((Jugador) jugadorActual);
            }
        }

        return respuesta;
    }

    //Aficionado
    public void setCompetenciaAficionado(String idAficionado, String idCompetencia) {

        Aficionado aficionadoActual = (Aficionado) this.get(idAficionado);
        aficionadoActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetenciaAficionado(String idAficionado, String idCompetencia) {

        Competencia eliminar = null;
        Aficionado aficionadoActual = (Aficionado) this.get(idAficionado);
        aficionadoActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Aficionado> getAficionadoCompetencia(String id) {
        LinkedList<Object> Aficionados = this.index();
        LinkedList<Aficionado> respuesta = new LinkedList<>();
        LinkedList<Aficionado> respuesta2 = new LinkedList<>();

        for (Object aficionado : Aficionados) {
            respuesta2.add((Aficionado) aficionado);
        }

        for (Aficionado aficonadoActual : respuesta2) {
            if (aficonadoActual.getMiCompetencia() != null && aficonadoActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add((Aficionado) aficonadoActual);
            }
        }

        return respuesta;
    }

    public void setEquipoAficionado(String idAficionado, String idEquipo) {

        Aficionado aficionadoActual = (Aficionado) this.get(idAficionado);
        aficionadoActual.setMiEquipo(new Equipo(idEquipo, "", 0, 0, 0, 0, 0, 0, 0));

    }

    public void setEliminarEquipoAficionado(String idAficionado, String idEquipo) {

        Equipo eliminar = null;
        Aficionado aficionadoActual = (Aficionado) this.get(idAficionado);
        aficionadoActual.setMiEquipo(eliminar);

    }

    public LinkedList<Aficionado> getAficionadoEquipo(String id) {
        LinkedList<Object> Aficionados = this.index(3);
        LinkedList<Aficionado> respuesta = new LinkedList<>();
        LinkedList<Aficionado> respuesta2 = new LinkedList<>();

        for (Object aficionado : Aficionados) {
            respuesta2.add((Aficionado) aficionado);
        }

        for (Aficionado aficonadoActual : respuesta2) {
            if (aficonadoActual.getMiEquipo() != null && aficonadoActual.getMiEquipo().getId().equals(id)) {
                respuesta.add((Aficionado) aficonadoActual);
            }
        }

        return respuesta;
    }

    //Tecnicos
    public void setCompetenciaTecnico(String idTecnico, String idCompetencia) {

        Tecnico tecnicoActual = (Tecnico) this.get(idTecnico);
        tecnicoActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetenciaTecnico(String idTecnico, String idCompetencia) {

        Competencia eliminar = null;
        Tecnico tecnicoActual = (Tecnico) this.get(idTecnico);
        tecnicoActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Jugador> getTecnicoCompetencia(String id) {
        LinkedList<Object> jugadores = this.index();
        LinkedList<Jugador> respuesta = new LinkedList<>();
        LinkedList<Jugador> respuesta2 = new LinkedList<>();

        for (Object jugador : jugadores) {
            respuesta2.add((Jugador) jugador);
        }

        for (Jugador jugadorActual : respuesta2) {
            if (jugadorActual.getMiCompetencia() != null && jugadorActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add((Jugador) jugadorActual);
            }
        }

        return respuesta;
    }

    //Tecnico 
    public void setEquipoTecnico(String idTecnico, String idEquipo) {

        Tecnico tecnicoActual = (Tecnico) this.get(idTecnico);
        tecnicoActual.setMiEquipo(new Equipo(idEquipo, "", 0, 0, 0, 0, 0, 0, 0));

    }

    public void setEliminarEquipoTecnico(String idTecnico, String idEquipo) {

        Equipo eliminar = null;
        Tecnico tecnicoActual = (Tecnico) this.get(idTecnico);
        tecnicoActual.setMiEquipo(eliminar);

    }

    public Tecnico getTecnicoEquipo(String id) {
        Tecnico respuesta = null;
        LinkedList<Object> tecnicos = this.index(4);

        for (Object tecnicoActual : tecnicos) {
            if (((Tecnico) tecnicoActual).getMiEquipo().getId().equals(id)) {
                respuesta = (Tecnico) tecnicoActual;
            }
        }

        return respuesta;
    }

    //Arbitro
    public void setCompetenciaArbitro(String idArbitro, String idCompetencia) {

        Arbitro arbitroActual = (Arbitro) this.get(idArbitro);
        arbitroActual.setMiCompetencia(new Competencia(idCompetencia, ""));

    }

    public void setEliminarCompetenciaArbitro(String idArbitro, String idCompetencia) {

        Competencia eliminar = null;
        Arbitro arbitroActual = (Arbitro) this.get(idArbitro);
        arbitroActual.setMiCompetencia(eliminar);

    }

    public LinkedList<Arbitro> getArbitroCompetencia(String id) {
        LinkedList<Object> arbitros = this.index();
        LinkedList<Arbitro> respuesta = new LinkedList<>();
        LinkedList<Arbitro> respuesta2 = new LinkedList<>();

        for (Object arbitro : arbitros) {
            respuesta2.add((Arbitro) arbitro);
        }

        for (Arbitro arbitroActual : respuesta2) {
            if (arbitroActual.getMiCompetencia() != null && arbitroActual.getMiCompetencia().getId().equals(id)) {
                respuesta.add((Arbitro) arbitroActual);
            }
        }

        return respuesta;
    }

    //Relaciones con Partido Arbitro
    public void setPartidoArbitro(String idArbitro, String idPartido) {

        Arbitro arbitroActual = (Arbitro) this.get(idArbitro);
        arbitroActual.setMiPartido(new Partido(idPartido, "", 0, "", 0, "", 0));

    }

    public void setEliminarPartidoArbitro(String idArbitro, String idPartido) {

        Partido eliminar = null;
        Arbitro arbitroActual = (Arbitro) this.get(idArbitro);
        arbitroActual.setMiPartido(eliminar);

    }

    public LinkedList<Arbitro> getArbitroPartido(String id) {
        LinkedList<Object> arbitros = this.index();
        LinkedList<Arbitro> respuesta = new LinkedList<>();
        LinkedList<Arbitro> respuesta2 = new LinkedList<>();

        for (Object arbitro : arbitros) {
            respuesta2.add((Arbitro) arbitro);
        }

        for (Arbitro arbitroActual : respuesta2) {
            if (arbitroActual.getMiPartido() != null && arbitroActual.getMiPartido().getId().equals(id)) {
                respuesta.add((Arbitro) arbitroActual);
            }
        }

        return respuesta;
    }

    // Jugador más joven de la liga
    public Jugador jugadorMasJoven() {
        Jugador respuesta = null;

        int edad = Integer.MAX_VALUE;

        LinkedList<Object> auxiliar = this.index(2);
        LinkedList<Jugador> jugadores = new LinkedList<>();

        for (Object jugadorActual : auxiliar) {
            jugadores.add((Jugador) jugadorActual);
        }

        for (Jugador jugActual : jugadores) {
            if (jugActual.getEdad() < edad) {
                edad = jugActual.getEdad();
                respuesta = jugActual;
            }
        }

        return respuesta;
    }

    //Jugador que ha marcado más goles en la liga.
    public Jugador jugadorMasgolesenLiga() {

        Jugador respuesta = null;
        int maximo = Integer.MIN_VALUE;

        LinkedList<Object> auxiliar = this.index(2);
        LinkedList<Jugador> jugadores = new LinkedList<>();

        for (Object jugadorActual : auxiliar) {
            jugadores.add((Jugador) jugadorActual);
        }

        for (Jugador jugadorActal : jugadores) {
            if (jugadorActal.getNumeroGoles() > maximo) {
                maximo = jugadorActal.getNumeroGoles();
                respuesta = jugadorActal;
            }
        }
        return respuesta;
    }

}
