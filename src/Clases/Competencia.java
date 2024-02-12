package Clases;

import java.util.LinkedList;
import java.util.List;

public class Competencia {

    private String id;
    private String nombre;

    private LinkedList<Persona> misPersonas;
    private LinkedList<Equipo> misEquipos;
    private LinkedList<Jornada> misJornadas;
    private LinkedList<Estadio> misEstadios;

    public Competencia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.misPersonas = new LinkedList<>();
        this.misEquipos = new LinkedList<>();
        this.misJornadas = new LinkedList<>();
        this.misEstadios = new LinkedList<>();
    }

    public LinkedList<Persona> getMisPersonas() {
        return misPersonas;
    }

    public void setMisPersonas(LinkedList<Persona> misPersonas) {
        this.misPersonas = misPersonas;
    }

    public LinkedList<Equipo> getMisEquipos() {
        return misEquipos;
    }

    public void setMisEquipos(LinkedList<Equipo> misEquipos) {
        this.misEquipos = misEquipos;
    }

    public LinkedList<Jornada> getMisJornadas() {
        return misJornadas;
    }

    public void setMisJornadas(LinkedList<Jornada> misJornadas) {
        this.misJornadas = misJornadas;
    }

    public LinkedList<Estadio> getMisEstadios() {
        return misEstadios;
    }

    public void setMisEstadios(LinkedList<Estadio> misEstadios) {
        this.misEstadios = misEstadios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //METODOS 
    //Jugador más joven de la liga
    public Jugador jugadorMasJovendelaLiga() {

        Jugador respuesta = null;
        int menor = Integer.MIN_VALUE;

        for (Persona personaActual : this.misPersonas) {
            if (personaActual instanceof Jugador) {
                Jugador auxiliar = (Jugador) personaActual;

                if (auxiliar.getEdad() < menor) {

                    menor = auxiliar.getEdad();
                    respuesta = (Jugador) personaActual;
                }
            }
        }

        return respuesta;
    }

    //Promedio de edad por cada uno de los equipos
    public List<Double> promedioEdadEquipos() {
        List<Double> promedios = new LinkedList<>();

        for (Equipo equipoActual : this.misEquipos) {
            int totalEdad = 0;
            int cantidadJugadores = equipoActual.getMisJugadores().size();

            for (Jugador jugadorActual : equipoActual.getMisJugadores()) {
                totalEdad += jugadorActual.getEdad();
            }

            if (cantidadJugadores > 0) {
                double promedio = totalEdad / cantidadJugadores;
                promedios.add(promedio);
            } else {
                double promedio = 0;
                promedios.add(promedio);
            }

        }

        return promedios;
    }

    //Cantidad de partidos en el que un equipo ganó por goleada, quiere decir que el ganador sacó 4 goles de diferencia al contrincante.
    public int cantidadPartidosGoleada(Equipo equipo) {
        int cantidadGoleadas = 0;

        for (Jornada jornadaActual : this.misJornadas) {
            for (Partido partidoActual : jornadaActual.getMisPartidos()) {
                if (partidoActual.getEquipoLocal().equals(equipo) || partidoActual.getEquipoVisitante().equals(equipo)) {
                    int diferenciaGoles = partidoActual.getGolesLocal() - partidoActual.getGolesVisitante();

                    if (diferenciaGoles >= 4 || diferenciaGoles <= -4) {
                        cantidadGoleadas++;
                    }
                }
            }
        }

        return cantidadGoleadas;
    }

    //Jugador que ha marcado más goles en la liga.
    public Jugador jugadorMasGolesEnLaLiga() {

        int maximo = Integer.MIN_VALUE;
        Jugador respuesta = null;

        for (Persona personaActual : this.misPersonas) {
            if (personaActual instanceof Jugador) {

                Jugador auxiliar = (Jugador) personaActual;

                if (auxiliar.getNumeroGoles() > maximo) {
                    maximo = auxiliar.getNumeroGoles();
                    respuesta = (Jugador) personaActual;
                }
            }
        }

        return respuesta;
    }

    //Arquero que le han marcado menos goles, esta información se puede obtener de los goles en contra.
    public Jugador arqueroMenosGolesEnContra() {

        Jugador arqueroMenosGoles = null;
        int menor = Integer.MAX_VALUE;

        for (Equipo equipoActual : this.misEquipos) {
            int golesEnContraEquipo = equipoActual.getGolesContra();
            for (Persona personaActual : equipoActual.getMisJugadores()) {
                if (personaActual instanceof Jugador) {
                    Jugador auxiliar = (Jugador) personaActual;
                    String posicion = auxiliar.getPosicion();
                    if (posicion.equalsIgnoreCase("Arquero") || posicion.equalsIgnoreCase("Portero")) {
                        if (golesEnContraEquipo < menor) {
                            arqueroMenosGoles = (Jugador) personaActual;
                            menor = golesEnContraEquipo;
                        }
                    }

                }

            }
        }

        return arqueroMenosGoles;
    }

    //Equipo con nómina de jugadores y técnico más alta.
    public Equipo nominaJuadorTecnicoMasAlta() {

        Equipo respuesta = null;
        double maximo = Double.MIN_VALUE;

        for (Equipo equipoActual : this.misEquipos) {
            double nominaEquipo = equipoActual.getMiTecnico().getSalario();
            for (Jugador jugadorActual : equipoActual.getMisJugadores()) {
                nominaEquipo += jugadorActual.getSalario();
            }

            if (nominaEquipo > maximo) {
                maximo = nominaEquipo;
                respuesta = equipoActual;
            }

        }

        return respuesta;
    }

    //Equipo con mayor cantidad de aficionados
    public Equipo equipoMayorCantidadAficionados() {
        Equipo equipoMayorCantidadAficionados = null;
        int maxCantidadAficionados = 0;

        for (Equipo equipo : this.misEquipos) {
            int cantidadAficionados = equipo.getMisAficionados().size();

            if (cantidadAficionados > maxCantidadAficionados) {
                equipoMayorCantidadAficionados = equipo;
                maxCantidadAficionados = cantidadAficionados;
            }
        }

        return equipoMayorCantidadAficionados;
    }

    //Identificador del partido el cual tuvo más goles
    public String identificadorPartidoMasGoles() {
        String respuesta = "";
        int maximo = Integer.MIN_VALUE;

        for (Jornada jornadaActual : this.misJornadas) {
            for (Partido partidoActual : jornadaActual.getMisPartidos()) {
                int totalGoles = partidoActual.getGolesLocal() + partidoActual.getGolesVisitante();

                if (totalGoles > maximo) {
                    maximo = totalGoles;
                    respuesta = partidoActual.getId();
                }
            }
        }

        return respuesta;
    }

    //Nombre del estadio donde se marcaron más goles en toda la liga
    public String nombreEstadioMasGoles() {

        String respuesta = "";
        int maximo = Integer.MIN_VALUE;

        for (Estadio estadioActual : this.misEstadios) {
            for (Partido partidoActual : estadioActual.getMisPartidos()) {

                int totalGoles = partidoActual.getGolesLocal() + partidoActual.getGolesVisitante();

                if (totalGoles > maximo) {
                    maximo = totalGoles;
                    respuesta = estadioActual.getNombre();
                }
            }
        }

        return respuesta;
    }
}
