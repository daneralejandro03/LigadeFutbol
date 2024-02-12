package Vistas;

import Clases.Aficionado;
import Clases.Arbitro;
import Clases.Competencia;
import Clases.Equipo;
import Clases.Estadio;
import Clases.Jornada;
import Clases.Jugador;
import Clases.Manager;
import Clases.Partido;
import Clases.Persona;
import Clases.Tecnico;
import Controladores.ControladorCompetencia;
import Controladores.ControladorEquipo;
import Controladores.ControladorEstadio;
import Controladores.ControladorJornada;
import Controladores.ControladorPartido;
import Controladores.ControladorPersona;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Formulario extends javax.swing.JFrame {

    ControladorCompetencia miControladorCompetencia;
    ControladorEquipo miControladorEquipo;
    ControladorEstadio miControladorEstadio;
    ControladorJornada miControladorJornada;
    ControladorPartido miControladorPartido;
    ControladorPersona miControladorPersona;

    FondoPanel fondo= new FondoPanel();
    public Formulario() {

        this.setContentPane(fondo);
        
        initComponents();

        this.txtNacionalidadJugador.setEnabled(false);
        this.txtPosicionJugador.setEnabled(false);
        this.txtNumeroGolesJugador.setEnabled(false);
        this.txtSalarioJugador.setEnabled(false);
        this.txtAnnosFidelidadAficionado.setEnabled(false);
        this.CheckBoxAbonadoAficionado.setEnabled(false);
        this.txtAnnosExperienciaTecnico.setEnabled(false);
        this.txtSalarioTecnico.setEnabled(false);
        this.CheckBoxFifaArbitro.setEnabled(false);

        this.miControladorCompetencia = new ControladorCompetencia();
        this.miControladorEquipo = new ControladorEquipo();
        this.miControladorEstadio = new ControladorEstadio();
        this.miControladorJornada = new ControladorJornada();
        this.miControladorPartido = new ControladorPartido();
        this.miControladorPersona = new ControladorPersona();
        this.setLocationRelativeTo(null);

        //Actualizar Tablas
        actualizarTablaCompetencia();
        actualizarTablaEquipo();
        actualizarTablaEstadio();
        actualizarTablaJornada();
        actualizarTablaPartido();
        actualizarTablaManager();
        actualizarTablaJugador();
        actualizarTablaAficionado();
        actualizarTablaTecnico();
        actualizarTablaArbitro();

        //ComboBox de la Competencia
        actualizarComboPersonasManagers();
        actualizarComboPersonasJugadores();
        actualizarComboPersonasAficionados();
        actualizarComboPersonasTecnicos();
        actualizarComboPersonasArbitros();
        actualizarComboEquiposCompetencia();
        actualizarComboJornadaCompetencia();
        actualizarComboEstadioCompetencia();

        //ComboBox del Equipo
        actualizarComboPersonasManagersEquipo();
        actualizarComboPersonasJugadoresEquipo();
        actualizarComboPersonasAficionadosEquipo();
        actualizarComboPersonasTecnicosEquipo();

        //ComboBox de Estadio
        actualizarComboEstadioPartido();

        //ComboBox de Partido
        actualizarComboPartidocomboEquipoLocal();
        actualizarComboPartidocomboEquipoVisitante();
        actualizarPartidoCombocomboArbitro();

        //ComboBox de Jornada
        actualizarComboPartidosJornada();

        //ComoboBox de Persona
        actualizarComboJugadoresPersonasManagers();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                eventoCierre();
            }
        });
    }

    //TABLA DE POSICIONES DE LOS EQUIPOS
    private void tablaDePosiciones() {

        ControladorEquipo tabla = new ControladorEquipo();

        String Matriz[][] = new String[tabla.tablaPosiciones().size()][3];

        Equipo auxiliar;

        for (int i = 0; i < tabla.tablaPosiciones().size(); i++) {
            auxiliar = tabla.tablaPosiciones().get(i);
            Matriz[i][0] = auxiliar.getNombre();
            Matriz[i][1] = "" + auxiliar.getPuntos();
            Matriz[i][2] = "" + (auxiliar.getGolesFavor() - auxiliar.getGolesContra());

            tablaDePosicionesEquipos.setModel(new javax.swing.table.DefaultTableModel(
                    Matriz,
                    new String[]{
                        "Equipo", "Puntos", "Diferencia de goles"
                    }
            ));
        }
    }

    //TABAL DE EDAD DEL PROMEDIO JUGADORES
    private void actualizarTablaPromedioEdadEquipoJugadores() {

        ControladorEquipo tabla = new ControladorEquipo();

        String Matriz[][] = new String[tabla.nombreEquipos().size()][2];

        Equipo auxiliar;
        double auxiliar2;

        for (int i = 0; i < tabla.promedioEdadEquipos().size(); i++) {

            auxiliar = tabla.nombreEquipos().get(i);
            auxiliar2 = tabla.promedioEdadEquipos().get(i);

            Matriz[i][0] = auxiliar.getNombre();
            Matriz[i][1] = "" + auxiliar2;

            tablaPromedioEdadEquipos.setModel(new javax.swing.table.DefaultTableModel(
                    Matriz,
                    new String[]{
                        "Equipo", "Promedio"
                    }
            ));
        }
    }

    //FUNCIÓN PARA ACTUALIZAR LOS GOLES EN EL PARTIDO
    public void actualizarGolesEquipo(String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante) {

        Equipo equipoLoca = (Equipo) miControladorEquipo.get(equipoLocal);
        Equipo equipoVisi = (Equipo) miControladorEquipo.get(equipoVisitante);

        // Actualizar goles a favor y en contra del equipo local
        equipoLoca.setGolesFavor(equipoLoca.getGolesFavor() + golesLocal);
        equipoLoca.setGolesContra(equipoLoca.getGolesContra() + golesVisitante);

        // Actualizar goles a favor y en contra del equipo visitante
        equipoVisi.setGolesFavor(equipoVisi.getGolesFavor() + golesVisitante);
        equipoVisi.setGolesContra(equipoVisi.getGolesContra() + golesLocal);

        // Verificar el resultado del partido y asignar puntos a los equipos
        if (golesLocal > golesVisitante) {
            equipoLoca.setPuntos(equipoLoca.getPuntos() + 3);
        } else if (golesLocal == golesVisitante) {
            equipoLoca.setPuntos(equipoLoca.getPuntos() + 1);
            equipoVisi.setPuntos(equipoVisi.getPuntos() + 1);
        } else {
            equipoVisi.setPuntos(equipoVisi.getPuntos() + 3);
        }

        equipoLoca.setPartidosJugados(equipoLoca.getPartidosJugados() + 1);
        equipoVisi.setPartidosJugados(equipoVisi.getPartidosJugados() + 1);
        actualizarTablaEquipo();
    }

    // EN ESTE ESPACIO SE ENCUENTRAN TODOS LOS COMBOBOX PARA PODER REALIZAR LA ACTUALIZACIÓN DE ELLOS
    //Persona Competencia
    public void actualizarComboPersonasManagers() {

        this.comboPersonasManagers.removeAllItems();
        LinkedList<Object> losManagers = this.miControladorPersona.index(1);

        for (Object actual : losManagers) {
            String nombre = ((Manager) actual).getCedula() + " - " + ((Manager) actual).getNombre();
            this.comboPersonasManagers.addItem(nombre);
        }
    }

    public void actualizarComboPersonasJugadores() {

        this.comboPersonasJugadores.removeAllItems();
        LinkedList<Object> losJugadores = this.miControladorPersona.index(2);

        for (Object actual : losJugadores) {
            String nombre = ((Jugador) actual).getCedula() + " - " + ((Jugador) actual).getNombre();
            this.comboPersonasJugadores.addItem(nombre);
        }
    }

    public void actualizarComboPersonasAficionados() {

        this.comboPersonasAficionados.removeAllItems();
        LinkedList<Object> losAficionados = this.miControladorPersona.index(3);

        for (Object actual : losAficionados) {
            String nombre = ((Aficionado) actual).getCedula() + " - " + ((Aficionado) actual).getNombre();
            this.comboPersonasAficionados.addItem(nombre);
        }
    }

    public void actualizarComboPersonasTecnicos() {

        this.comboPersonasTecnicos.removeAllItems();
        LinkedList<Object> losTecnicos = this.miControladorPersona.index(4);

        for (Object actual : losTecnicos) {
            String nombre = ((Tecnico) actual).getCedula() + " - " + ((Tecnico) actual).getNombre();
            this.comboPersonasTecnicos.addItem(nombre);
        }
    }

    public void actualizarComboPersonasArbitros() {

        this.comboPersonasArbitros.removeAllItems();
        LinkedList<Object> losArbitros = this.miControladorPersona.index(5);

        for (Object actual : losArbitros) {
            String nombre = ((Arbitro) actual).getCedula() + " - " + ((Arbitro) actual).getNombre();
            this.comboPersonasArbitros.addItem(nombre);
        }
    }

    //Equipos Competencia
    public void actualizarComboEquiposCompetencia() {

        this.comboEquiposCompetencia.removeAllItems();
        LinkedList<Object> losEquipos = this.miControladorEquipo.index();

        for (Object actual : losEquipos) {
            String nombre = ((Equipo) actual).getId() + " - " + ((Equipo) actual).getNombre();
            this.comboEquiposCompetencia.addItem(nombre);
        }
    }

    //Jornada Competencia
    public void actualizarComboJornadaCompetencia() {

        this.comboJornadasCompetencia.removeAllItems();
        LinkedList<Object> lasJornadas = this.miControladorJornada.index();

        for (Object actual : lasJornadas) {
            String nombre = ((Jornada) actual).getId() + " - " + ((Jornada) actual).getNumero();
            this.comboJornadasCompetencia.addItem(nombre);
        }
    }

    //Estadio Competencia
    public void actualizarComboEstadioCompetencia() {

        this.comboEstadiosCompetencia.removeAllItems();
        LinkedList<Object> losEstadios = this.miControladorEstadio.index();

        for (Object actual : losEstadios) {
            String nombre = ((Estadio) actual).getId() + " - " + ((Estadio) actual).getNombre();
            this.comboEstadiosCompetencia.addItem(nombre);
        }
    }

    //Persona Equipo
    public void actualizarComboPersonasManagersEquipo() {

        this.comboManagersEquipo.removeAllItems();
        LinkedList<Object> losManagers = this.miControladorPersona.index(1);

        for (Object actual : losManagers) {
            String nombre = ((Manager) actual).getCedula() + " - " + ((Manager) actual).getNombre();
            this.comboManagersEquipo.addItem(nombre);
        }
    }

    public void actualizarComboPersonasJugadoresEquipo() {

        this.comboJugadoresEquipo.removeAllItems();
        LinkedList<Object> losJugadores = this.miControladorPersona.index(2);

        for (Object actual : losJugadores) {
            String nombre = ((Jugador) actual).getCedula() + " - " + ((Jugador) actual).getNombre();
            this.comboJugadoresEquipo.addItem(nombre);
        }
    }

    //Persona Jugador
    public void actualizarComboJugadoresPersonasManagers() {

        this.comboJugadorPersona.removeAllItems();
        LinkedList<Object> losJugadores = this.miControladorPersona.index(2);

        for (Object actual : losJugadores) {
            String nombre = ((Jugador) actual).getCedula() + " - " + ((Jugador) actual).getNombre();
            this.comboJugadorPersona.addItem(nombre);
        }
    }

    public void actualizarComboPersonasAficionadosEquipo() {

        this.comboAficionadosEquipo.removeAllItems();
        LinkedList<Object> losAficionados = this.miControladorPersona.index(3);

        for (Object actual : losAficionados) {
            String nombre = ((Aficionado) actual).getCedula() + " - " + ((Aficionado) actual).getNombre();
            this.comboAficionadosEquipo.addItem(nombre);
        }
    }

    public void actualizarComboPersonasTecnicosEquipo() {

        this.comboTecnicosEquipo.removeAllItems();
        LinkedList<Object> losTecnicos = this.miControladorPersona.index(4);

        for (Object actual : losTecnicos) {
            String nombre = ((Tecnico) actual).getCedula() + " - " + ((Tecnico) actual).getNombre();
            this.comboTecnicosEquipo.addItem(nombre);
        }
    }

    // Estadio Partidos
    public void actualizarComboEstadioPartido() {

        this.comboPartidosEstadio.removeAllItems();
        LinkedList<Object> losPartidos = this.miControladorPartido.index();

        for (Object actual : losPartidos) {
            String nombre = ((Partido) actual).getId() + " - " + ((Partido) actual).getFecha();
            this.comboPartidosEstadio.addItem(nombre);
        }
    }

    //Jornada Partidos
    public void actualizarComboJornadaPartido() {

        this.comboPartidosJornada.removeAllItems();
        LinkedList<Object> losPartidos = this.miControladorPartido.index();

        for (Object actual : losPartidos) {
            String nombre = ((Partido) actual).getId() + " - " + ((Partido) actual).getFecha();
            this.comboPartidosJornada.addItem(nombre);
        }
    }

    // Partido
    //Equipo Local
    public void actualizarComboPartidocomboEquipoLocal() {

        this.comboEquipoLocalPartido.removeAllItems();
        LinkedList<Object> losEquipos = this.miControladorEquipo.index();

        for (Object actual : losEquipos) {
            String nombre = ((Equipo) actual).getId() + " - " + ((Equipo) actual).getNombre();
            this.comboEquipoLocalPartido.addItem(nombre);
        }
    }

    //Equipo Visitante
    public void actualizarComboPartidocomboEquipoVisitante() {

        this.comboEquipoVisitantePartido.removeAllItems();
        LinkedList<Object> losEquipos = this.miControladorEquipo.index();

        for (Object actual : losEquipos) {
            String nombre = ((Equipo) actual).getId() + " - " + ((Equipo) actual).getNombre();
            this.comboEquipoVisitantePartido.addItem(nombre);
        }
    }

    public void actualizarPartidoCombocomboArbitro() {

        this.comboArbitroPartido.removeAllItems();
        LinkedList<Object> losArbitros = this.miControladorPersona.index(5);

        for (Object actual : losArbitros) {
            String nombre = ((Arbitro) actual).getCedula() + " - " + ((Arbitro) actual).getNombre();
            this.comboArbitroPartido.addItem(nombre);
        }
    }

    //Jornada partidos
    public void actualizarComboPartidosJornada() {

        this.comboPartidosJornada.removeAllItems();
        LinkedList<Object> losPartidos = this.miControladorPartido.index();

        for (Object actual : losPartidos) {
            String nombre = ((Partido) actual).getId() + " - " + ((Partido) actual).getFecha();
            this.comboPartidosJornada.addItem(nombre);
        }
    }

    //El evento que al cerrar el formulario va guardar los datos
    public void eventoCierre() {
        this.miControladorCompetencia.getMiModeloCompetencia().guardar();
        this.miControladorEquipo.getMiModeloEquipo().guardar();
        this.miControladorEstadio.getMiModeloEstadio().guardar();
        this.miControladorJornada.getMiModeloJornada().guardar();
        this.miControladorPartido.getMiModeloPartido().guardar();
        this.miControladorPersona.getMiModeloManager().guardar();
        this.miControladorPersona.getMiModeloJugador().guardar();
        this.miControladorPersona.getMiModeloAficionado().guardar();
        this.miControladorPersona.getMiModeloTecnico().guardar();
        this.miControladorPersona.getMiModeloArbitro().guardar();
        System.out.println("Cerrando");
        System.exit(0);
    }

    // FUNCIONES DE LIMPIAR LAS CAJAS DE LOS FORMULARIOS
    public void LimpiarCajasCompetencia() {
        this.txtIdCompetencia.setText("");
        this.txtNombreCompetencia.setText("");
    }

    public void limpiarCajasEquipo() {
        this.txtIdEquipo.setText("");
        this.txtNombreEquipo.setText("");
        this.txtAnnofuncacionEquipo.setText("");
        this.txtTitulosnacionalesEquipo.setText("");
        this.txtTitulosinternacionalesEquipo.setText("");
        this.txtPuntosEquipo.setText("");
        this.txtGolesfavorEquipo.setText("");
        this.txtGolescontraEquipo.setText("");
        this.txtPartidosjugadosEquipo.setText("");
    }

    public void limpiarCajasEstadio() {
        this.txtIdEstadio.setText("");
        this.txtNombreEstadio.setText("");
        this.txtCiudadEstadio.setText("");
        this.txtCapacidadEstadio.setText("");
    }

    public void limpiarCajasJornada() {
        this.txtIdJornada.setText("");
        this.txtNumeroJornada.setText("");
        this.txtEsloganJornada.setText("");
    }

    public void limpiarCajasPartido() {
        this.txtIdPartido.setText("");
        this.txtFechaPartido.setText("");
        this.txtGolesLocalPartido.setText("");
        this.txtGolesVisitantePartido.setText("");
        this.txtPuntosGanadorPartido.setText("");
        this.comboEquipoLocalPartido.setSelectedIndex(0);
        this.comboEquipoVisitantePartido.setSelectedIndex(0);
    }

    public void limpiarCajasPersona() {
        this.txtCedulaPersona.setText("");
        this.txtNombrePersona.setText("");
        this.txtApellidoPersona.setText("");
        this.txtEdadPersona.setText("");
        this.txtAnnosAfiliacionManager.setText("");
        this.txtNacionalidadJugador.setText("");
        this.txtPosicionJugador.setText("");
        this.txtNumeroGolesJugador.setText("");
        this.txtSalarioJugador.setText("");
        this.txtAnnosFidelidadAficionado.setText("");
        this.CheckBoxAbonadoAficionado.setSelected(false);
        this.txtAnnosExperienciaTecnico.setText("");
        this.txtSalarioTecnico.setText("");
        this.CheckBoxFifaArbitro.setSelected(false);

    }

    public void limpiarCajasPersonaHerencia() {
        this.txtAnnosAfiliacionManager.setText("");
        this.comboJugadorPersona.setSelectedIndex(0);
        this.txtNacionalidadJugador.setText("");
        this.txtPosicionJugador.setText("");
        this.txtNumeroGolesJugador.setText("");
        this.txtSalarioJugador.setText("");
        this.txtAnnosFidelidadAficionado.setText("");
        this.CheckBoxAbonadoAficionado.setSelected(false);
        this.txtAnnosExperienciaTecnico.setText("");
        this.txtSalarioTecnico.setText("");
        this.CheckBoxFifaArbitro.setSelected(false);
    }

    // FUNCIONES DE ACTUALIZAR TODAS LAS TABLAS DE LOS FORMULARIOS
    public void actualizarTablaCompetencia() {

        String nombreColumnas[] = {"ID", "Nombre"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaCompetencia.setModel(miModelo);
        LinkedList<Object> lasCompetencias = this.miControladorCompetencia.index();

        for (Object competenciaActual : lasCompetencias) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Competencia) competenciaActual).getId();
            fila[1] = ((Competencia) competenciaActual).getNombre();
            miModelo.addRow(fila);
        }

    }

    public void actualizarTablaEquipo() {

        String nombreColumnas[] = {"ID", "Nombre", "Año Fundación", "Titulos Nacionales", "Titulos Internacionales", "Puntos", "Goles a Favor", "Goles en Contra", "Partidos Jugados"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaEquipo.setModel(miModelo);
        LinkedList<Object> losEquipos = this.miControladorEquipo.index();

        for (Object competenciaActual : losEquipos) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Equipo) competenciaActual).getId();
            fila[1] = ((Equipo) competenciaActual).getNombre();
            fila[2] = "" + ((Equipo) competenciaActual).getAnnoFundacion();
            fila[3] = "" + ((Equipo) competenciaActual).getTitulosNacionales();
            fila[4] = "" + ((Equipo) competenciaActual).getTitulosInternaciones();
            fila[5] = "" + ((Equipo) competenciaActual).getPuntos();
            fila[6] = "" + ((Equipo) competenciaActual).getGolesFavor();
            fila[7] = "" + ((Equipo) competenciaActual).getGolesContra();
            fila[8] = "" + ((Equipo) competenciaActual).getPartidosJugados();
            miModelo.addRow(fila);
        }

    }

    public void actualizarTablaEstadio() {

        String nombreColumnas[] = {"ID", "Nombre", "Ciudad", "Capacidad"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaEstadio.setModel(miModelo);
        LinkedList<Object> losEstadios = this.miControladorEstadio.index();

        for (Object estadioActual : losEstadios) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Estadio) estadioActual).getId();
            fila[1] = ((Estadio) estadioActual).getNombre();
            fila[2] = ((Estadio) estadioActual).getCiudad();
            fila[3] = "" + ((Estadio) estadioActual).getCapacidad();
            miModelo.addRow(fila);
        }

    }

    public void actualizarTablaJornada() {

        String nombreColumnas[] = {"ID", "Numero", "Eslogan"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaJornada.setModel(miModelo);
        LinkedList<Object> lasJornadas = this.miControladorJornada.index();

        for (Object jornadaActual : lasJornadas) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Jornada) jornadaActual).getId();
            fila[1] = "" + ((Jornada) jornadaActual).getNumero();
            fila[2] = ((Jornada) jornadaActual).getEslogan();
            miModelo.addRow(fila);
        }

    }

    public void actualizarTablaPartido() {

        String nombreColumnas[] = {"ID", "Fecha", "Puntos Ganador", "Equipo Local", "Goles Local", "Equipo Visitante", "Goles Visitante"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaPartido.setModel(miModelo);
        LinkedList<Object> losPartidos = this.miControladorPartido.index();

        for (Object partidoActual : losPartidos) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Partido) partidoActual).getId();
            fila[1] = ((Partido) partidoActual).getFecha();
            fila[2] = "" + ((Partido) partidoActual).getPuntosGanador();
            fila[3] = ((Partido) partidoActual).getEquipoLocal();
            fila[4] = "" + ((Partido) partidoActual).getGolesLocal();
            fila[5] = ((Partido) partidoActual).getEquipoVisitante();
            fila[6] = "" + ((Partido) partidoActual).getGolesVisitante();
            miModelo.addRow(fila);
        }

    }

    //Desde este punto se inician las tablas de la clase persona
    public void actualizarTablaManager() {

        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad", "Años de Afiliación"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaManager.setModel(miModelo);
        LinkedList<Object> losManagers = this.miControladorPersona.index(1);

        for (Object managerActual : losManagers) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Persona) managerActual).getCedula();
            fila[1] = ((Persona) managerActual).getNombre();
            fila[2] = ((Persona) managerActual).getApellido();
            fila[3] = "" + ((Persona) managerActual).getEdad();
            fila[4] = "" + ((Manager) managerActual).getAnnosAfiliacion();
            miModelo.addRow(fila);

        }
    }

    public void actualizarTablaJugador() {

        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad", "Nacionalidad", "Posición", "Numero de Goles", "Salario Jugador"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaJugador.setModel(miModelo);
        LinkedList<Object> losJugadores = this.miControladorPersona.index(2);

        for (Object jugadorActual : losJugadores) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Jugador) jugadorActual).getCedula();
            fila[1] = ((Jugador) jugadorActual).getNombre();
            fila[2] = ((Jugador) jugadorActual).getApellido();
            fila[3] = "" + ((Jugador) jugadorActual).getEdad();
            fila[4] = ((Jugador) jugadorActual).getNacionalidad();
            fila[5] = ((Jugador) jugadorActual).getPosicion();
            fila[6] = "" + ((Jugador) jugadorActual).getNumeroGoles();
            fila[7] = "" + ((Jugador) jugadorActual).getSalario();
            miModelo.addRow(fila);

        }
    }

    public void actualizarTablaAficionado() {

        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad", "Años de Fidelidad", "Abonado"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaAficionado.setModel(miModelo);
        LinkedList<Object> losAficionados = this.miControladorPersona.index(3);

        for (Object aficionadoActual : losAficionados) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Aficionado) aficionadoActual).getCedula();
            fila[1] = ((Aficionado) aficionadoActual).getNombre();
            fila[2] = ((Aficionado) aficionadoActual).getApellido();
            fila[3] = "" + ((Aficionado) aficionadoActual).getEdad();
            fila[4] = "" + ((Aficionado) aficionadoActual).getAnnosFidelidad();
            fila[5] = "" + ((Aficionado) aficionadoActual).isAbonado();
            miModelo.addRow(fila);
        }
    }

    public void actualizarTablaTecnico() {

        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad", "Años de experiencia", "Salario Tecnicos"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.tablaTecnico.setModel(miModelo);
        LinkedList<Object> losTecnicos = this.miControladorPersona.index(4);

        for (Object tecnicoActual : losTecnicos) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Tecnico) tecnicoActual).getCedula();
            fila[1] = ((Tecnico) tecnicoActual).getNombre();
            fila[2] = ((Tecnico) tecnicoActual).getApellido();
            fila[3] = "" + ((Tecnico) tecnicoActual).getEdad();
            fila[4] = "" + ((Tecnico) tecnicoActual).getAnnosExperiencia();
            fila[5] = "" + ((Tecnico) tecnicoActual).getSalario();
            miModelo.addRow(fila);
        }
    }

    public void actualizarTablaArbitro() {

        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad", "Arbitro Fifa"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombreColumnas);

        this.TablaArbitro.setModel(miModelo);
        LinkedList<Object> losArbitros = this.miControladorPersona.index(5);

        for (Object arbitroActual : losArbitros) {
            String fila[] = new String[nombreColumnas.length];
            fila[0] = ((Arbitro) arbitroActual).getCedula();
            fila[1] = ((Arbitro) arbitroActual).getNombre();
            fila[2] = ((Arbitro) arbitroActual).getApellido();
            fila[3] = "" + ((Arbitro) arbitroActual).getEdad();
            fila[4] = "" + ((Arbitro) arbitroActual).isArbitroFifa();
            miModelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new FondoPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdCompetencia = new javax.swing.JTextField();
        txtNombreCompetencia = new javax.swing.JTextField();
        btnAgregarCompetencia = new javax.swing.JButton();
        btnBuscarCompetencia = new javax.swing.JButton();
        btnEliminarCompetencia = new javax.swing.JButton();
        btnActualizarCompetencia = new javax.swing.JButton();
        btnSalirComptencia = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCompetencia = new javax.swing.JTable();
        btnLimpiarCompetencia = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        comboPersonasManagers = new javax.swing.JComboBox<>();
        comboEquiposCompetencia = new javax.swing.JComboBox<>();
        comboJornadasCompetencia = new javax.swing.JComboBox<>();
        comboEstadiosCompetencia = new javax.swing.JComboBox<>();
        btnAgregarManagersCompetencia = new javax.swing.JButton();
        btnEliminarManagersCompetencia = new javax.swing.JButton();
        btnAgregarEquiposCompetencia = new javax.swing.JButton();
        btnEliminarEquiposCompetencia = new javax.swing.JButton();
        btnAgregarJornadasCompetencia = new javax.swing.JButton();
        btnEliminarJornadasCompetencia = new javax.swing.JButton();
        btnAgregarEstadiosCompetencia = new javax.swing.JButton();
        btnEliminarEstadiosCompetencia = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        comboPersonasJugadores = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        comboPersonasAficionados = new javax.swing.JComboBox<>();
        comboPersonasTecnicos = new javax.swing.JComboBox<>();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        comboPersonasArbitros = new javax.swing.JComboBox<>();
        btnAgregarJugadoresCompetencia = new javax.swing.JButton();
        btnEliminarJugadoresCompetencia = new javax.swing.JButton();
        btnAgregarAficionadosCompetencia = new javax.swing.JButton();
        btnEliminarAficionadosCompetencia = new javax.swing.JButton();
        btnAgregarTecnicosCompetencia = new javax.swing.JButton();
        btnEliminarTecnicosCompetencia = new javax.swing.JButton();
        btnAgregarArbitrosCompetencia4 = new javax.swing.JButton();
        btnEliminarArbitrosCompetencia = new javax.swing.JButton();
        jPanel2 = new FondoPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEquipo = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtIdEquipo = new javax.swing.JTextField();
        txtNombreEquipo = new javax.swing.JTextField();
        txtAnnofuncacionEquipo = new javax.swing.JTextField();
        txtTitulosnacionalesEquipo = new javax.swing.JTextField();
        txtTitulosinternacionalesEquipo = new javax.swing.JTextField();
        txtPuntosEquipo = new javax.swing.JTextField();
        txtGolesfavorEquipo = new javax.swing.JTextField();
        txtGolescontraEquipo = new javax.swing.JTextField();
        txtPartidosjugadosEquipo = new javax.swing.JTextField();
        btnAgregarEquipo = new javax.swing.JButton();
        btnBuscarEquipo = new javax.swing.JButton();
        btnEliminarEquipo = new javax.swing.JButton();
        btnActualizarEquipo = new javax.swing.JButton();
        btnSalirEquipo = new javax.swing.JButton();
        btnLimpiarEquipo = new javax.swing.JButton();
        comboManagersEquipo = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        comboTecnicosEquipo = new javax.swing.JComboBox<>();
        comboAficionadosEquipo = new javax.swing.JComboBox<>();
        comboJugadoresEquipo = new javax.swing.JComboBox<>();
        btnAgregarJugadoresEquipo = new javax.swing.JButton();
        btnAgregarManagersEquipo = new javax.swing.JButton();
        btnEliminarManagersEquipo = new javax.swing.JButton();
        btnEliminarJugadoresEquipo = new javax.swing.JButton();
        btnAgregarAficionadosEquipo = new javax.swing.JButton();
        btnEliminarAficionadosEquipo = new javax.swing.JButton();
        btnEliminarTecnicosEquipo = new javax.swing.JButton();
        btnAgregarTecnicosEquipo = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        jPanel3 = new FondoPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtIdEstadio = new javax.swing.JTextField();
        txtNombreEstadio = new javax.swing.JTextField();
        txtCiudadEstadio = new javax.swing.JTextField();
        txtCapacidadEstadio = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaEstadio = new javax.swing.JTable();
        btnAgregarEstadio = new javax.swing.JButton();
        btnBuscarEstadio = new javax.swing.JButton();
        btnEliminarEstadio = new javax.swing.JButton();
        btnActualizarEstadio = new javax.swing.JButton();
        btnSalirEstadio = new javax.swing.JButton();
        btnLimpiarEstadio = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        comboPartidosEstadio = new javax.swing.JComboBox<>();
        btnAgregarPartidosEstadio = new javax.swing.JButton();
        btnEliminarPartidosEstadio = new javax.swing.JButton();
        jLabel84 = new javax.swing.JLabel();
        jPanel4 = new FondoPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtIdJornada = new javax.swing.JTextField();
        txtNumeroJornada = new javax.swing.JTextField();
        txtEsloganJornada = new javax.swing.JTextField();
        btnAgregarJornada = new javax.swing.JButton();
        btnBuscarJornada = new javax.swing.JButton();
        btnEliminarJornada = new javax.swing.JButton();
        btnActualizarJornada = new javax.swing.JButton();
        btnSalirJornada = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaJornada = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        btnLimpiarJornada = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        comboPartidosJornada = new javax.swing.JComboBox<>();
        btnAgregarPartidosJornada = new javax.swing.JButton();
        btnEliminarPartidosJornada = new javax.swing.JButton();
        jPanel5 = new FondoPanel();
        txtIdPartido = new javax.swing.JTextField();
        txtFechaPartido = new javax.swing.JTextField();
        txtGolesLocalPartido = new javax.swing.JTextField();
        txtGolesVisitantePartido = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaPartido = new javax.swing.JTable();
        btnAgregarPartido = new javax.swing.JButton();
        btnBuscarPartido = new javax.swing.JButton();
        btnEliminarPartido = new javax.swing.JButton();
        btnActualizarPartido = new javax.swing.JButton();
        btnSalirPartido = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtPuntosGanadorPartido = new javax.swing.JTextField();
        btnLimpiarPartido = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        comboEquipoLocalPartido = new javax.swing.JComboBox<>();
        comboEquipoVisitantePartido = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        comboArbitroPartido = new javax.swing.JComboBox<>();
        btnAgregarArbitroPartido = new javax.swing.JButton();
        btnEliminarArbitroPartido = new javax.swing.JButton();
        jPanel6 = new FondoPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaManager = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaJugador = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaAficionado = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablaTecnico = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        TablaArbitro = new javax.swing.JTable();
        txtCedulaPersona = new javax.swing.JTextField();
        txtNombrePersona = new javax.swing.JTextField();
        txtApellidoPersona = new javax.swing.JTextField();
        txtEdadPersona = new javax.swing.JTextField();
        comboTipoPersona = new javax.swing.JComboBox<>();
        txtNacionalidadJugador = new javax.swing.JTextField();
        txtPosicionJugador = new javax.swing.JTextField();
        txtNumeroGolesJugador = new javax.swing.JTextField();
        txtSalarioJugador = new javax.swing.JTextField();
        txtAnnosFidelidadAficionado = new javax.swing.JTextField();
        CheckBoxAbonadoAficionado = new javax.swing.JCheckBox();
        txtAnnosExperienciaTecnico = new javax.swing.JTextField();
        txtSalarioTecnico = new javax.swing.JTextField();
        txtAnnosAfiliacionManager = new javax.swing.JTextField();
        CheckBoxFifaArbitro = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnAgregarPersona = new javax.swing.JButton();
        btnBuscarPersona = new javax.swing.JButton();
        btnEliminarPersona = new javax.swing.JButton();
        btnActualizarPersona = new javax.swing.JButton();
        btnSalirJornada2 = new javax.swing.JButton();
        btnLimpiarPersona = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        comboJugadorPersona = new javax.swing.JComboBox<>();
        btnAgregarJugadorPersona = new javax.swing.JButton();
        btnEliminarJugadorPersona = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        jPanel7 = new FondoPanel();
        jLabel70 = new javax.swing.JLabel();
        txtInformesPartidosGoleada = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        btnGenerarInformes = new javax.swing.JButton();
        btnSalirJornada3 = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        txtInformesJugadormasJoven1 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        txtJugadorMasGolesLiga = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        txtArqueroMenosGoles = new javax.swing.JTextField();
        txtNominaJuadorTecnicoMasAlta = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        txtEquipoMayorAficionados = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        txtIdPartidoMasGoles = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        txtNombreEstadioMasGoles = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaPromedioEdadEquipos = new javax.swing.JTable();
        jLabel81 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDePosicionesEquipos = new javax.swing.JTable();
        jLabel82 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        txtCantidadGolesEquipoMasPuntos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INGRESAR COMPETENCIA");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre:");

        txtIdCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarCompetencia.setText("AGREGAR");
        btnAgregarCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompetenciaActionPerformed(evt);
            }
        });

        btnBuscarCompetencia.setText("BUSCAR");
        btnBuscarCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarCompetencia.setText("ELIMINAR");
        btnEliminarCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCompetenciaActionPerformed(evt);
            }
        });

        btnActualizarCompetencia.setText("ACTUALIZAR");
        btnActualizarCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCompetenciaActionPerformed(evt);
            }
        });

        btnSalirComptencia.setText("SALIR");
        btnSalirComptencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirComptenciaActionPerformed(evt);
            }
        });

        tablaCompetencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nombre"
            }
        ));
        jScrollPane1.setViewportView(tablaCompetencia);

        btnLimpiarCompetencia.setText("LIMPIAR");
        btnLimpiarCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCompetenciaActionPerformed(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("RELACIONES");

        jLabel52.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Managers:");

        jLabel53.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Equipos:");

        jLabel54.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Jornadas:");

        jLabel55.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Estadios:");

        comboPersonasManagers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPersonasManagersActionPerformed(evt);
            }
        });

        btnAgregarManagersCompetencia.setText("Agregar");
        btnAgregarManagersCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarManagersCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarManagersCompetencia.setText("Eliminar");
        btnEliminarManagersCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarManagersCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarEquiposCompetencia.setText("Agregar");
        btnAgregarEquiposCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEquiposCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarEquiposCompetencia.setText("Eliminar");
        btnEliminarEquiposCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEquiposCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarJornadasCompetencia.setText("Agregar");
        btnAgregarJornadasCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarJornadasCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarJornadasCompetencia.setText("Eliminar");
        btnEliminarJornadasCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarJornadasCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarEstadiosCompetencia.setText("Agregar");
        btnAgregarEstadiosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEstadiosCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarEstadiosCompetencia.setText("Eliminar");
        btnEliminarEstadiosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEstadiosCompetenciaActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Jugadores:");

        jLabel57.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Aficionados:");

        jLabel58.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Tecnicos:");

        jLabel59.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Arbitros: ");

        btnAgregarJugadoresCompetencia.setText("Agregar");
        btnAgregarJugadoresCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarJugadoresCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarJugadoresCompetencia.setText("Eliminar");
        btnEliminarJugadoresCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarJugadoresCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarAficionadosCompetencia.setText("Agregar");
        btnAgregarAficionadosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAficionadosCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarAficionadosCompetencia.setText("Eliminar");
        btnEliminarAficionadosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAficionadosCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarTecnicosCompetencia.setText("Agregar");
        btnAgregarTecnicosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTecnicosCompetenciaActionPerformed(evt);
            }
        });

        btnEliminarTecnicosCompetencia.setText("Eliminar");
        btnEliminarTecnicosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTecnicosCompetenciaActionPerformed(evt);
            }
        });

        btnAgregarArbitrosCompetencia4.setText("Agregar");
        btnAgregarArbitrosCompetencia4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarArbitrosCompetencia4ActionPerformed(evt);
            }
        });

        btnEliminarArbitrosCompetencia.setText("Eliminar");
        btnEliminarArbitrosCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArbitrosCompetenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(btnAgregarCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalirComptencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscarCompetencia, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEliminarCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizarCompetencia))
                            .addComponent(btnLimpiarCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel51))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdCompetencia)
                            .addComponent(txtNombreCompetencia, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPersonasArbitros, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPersonasTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPersonasAficionados, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPersonasJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPersonasManagers, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarManagersCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarManagersCompetencia))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarJugadoresCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarJugadoresCompetencia))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarAficionadosCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarAficionadosCompetencia)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel54)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboEquiposCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboJornadasCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboEstadiosCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarEquiposCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEquiposCompetencia))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarJornadasCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarJornadasCompetencia))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarEstadiosCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEstadiosCompetencia)))
                        .addGap(0, 342, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarArbitrosCompetencia4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarArbitrosCompetencia))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarTecnicosCompetencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarTecnicosCompetencia)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtIdCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombreCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCompetencia)
                    .addComponent(btnBuscarCompetencia)
                    .addComponent(btnEliminarCompetencia)
                    .addComponent(btnActualizarCompetencia))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalirComptencia)
                    .addComponent(btnLimpiarCompetencia))
                .addGap(29, 29, 29)
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(comboPersonasManagers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarManagersCompetencia)
                            .addComponent(btnEliminarManagersCompetencia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(comboPersonasJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(comboPersonasAficionados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarEquiposCompetencia)
                                .addComponent(btnEliminarEquiposCompetencia))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel53)
                                .addComponent(comboEquiposCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarJornadasCompetencia)
                                .addComponent(btnEliminarJornadasCompetencia))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarJugadoresCompetencia)
                                .addComponent(btnEliminarJugadoresCompetencia))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel54)
                                .addComponent(comboJornadasCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarEstadiosCompetencia)
                                .addComponent(btnEliminarEstadiosCompetencia))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarAficionadosCompetencia)
                                .addComponent(btnEliminarAficionadosCompetencia))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel55)
                                .addComponent(comboEstadiosCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarTecnicosCompetencia)
                        .addComponent(btnEliminarTecnicosCompetencia))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel58)
                        .addComponent(comboPersonasTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarArbitrosCompetencia4)
                        .addComponent(btnEliminarArbitrosCompetencia))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel59)
                        .addComponent(comboPersonasArbitros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Competencia", jPanel1);

        tablaEquipo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Año Fundación", "Titulos Nacionales", "Titulos Internacionales", "Puntos", "Goles a Favor", "Goles en Contra", "Partidos Jugados"
            }
        ));
        jScrollPane2.setViewportView(tablaEquipo);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID:");

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("INGRESAR EQUIPO");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Año fundación: ");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Titulos Nacionales:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Titulos Internacionales:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Puntos:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Goles a Favor:");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Goles en contra: ");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Partidos Jugados: ");

        txtIdEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdEquipoActionPerformed(evt);
            }
        });

        txtAnnofuncacionEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnnofuncacionEquipoActionPerformed(evt);
            }
        });

        txtGolesfavorEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGolesfavorEquipoActionPerformed(evt);
            }
        });

        btnAgregarEquipo.setText("AGREGAR");
        btnAgregarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEquipoActionPerformed(evt);
            }
        });

        btnBuscarEquipo.setText("BUSCAR");
        btnBuscarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEquipoActionPerformed(evt);
            }
        });

        btnEliminarEquipo.setText("ELIMINAR");
        btnEliminarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEquipoActionPerformed(evt);
            }
        });

        btnActualizarEquipo.setText("ACTUALIZAR");
        btnActualizarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEquipoActionPerformed(evt);
            }
        });

        btnSalirEquipo.setText("SALIR");
        btnSalirEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirEquipoActionPerformed(evt);
            }
        });

        btnLimpiarEquipo.setText("LIMPIAR");
        btnLimpiarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarEquipoActionPerformed(evt);
            }
        });

        comboManagersEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboManagersEquipoActionPerformed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Managers:");

        jLabel64.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Jugadores:");

        jLabel65.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Aficionados:");

        jLabel66.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Tecnicos:");

        btnAgregarJugadoresEquipo.setText("Agregar");
        btnAgregarJugadoresEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarJugadoresEquipoActionPerformed(evt);
            }
        });

        btnAgregarManagersEquipo.setText("Agregar");
        btnAgregarManagersEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarManagersEquipoActionPerformed(evt);
            }
        });

        btnEliminarManagersEquipo.setText("Eliminar");
        btnEliminarManagersEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarManagersEquipoActionPerformed(evt);
            }
        });

        btnEliminarJugadoresEquipo.setText("Eliminar");
        btnEliminarJugadoresEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarJugadoresEquipoActionPerformed(evt);
            }
        });

        btnAgregarAficionadosEquipo.setText("Agregar");
        btnAgregarAficionadosEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAficionadosEquipoActionPerformed(evt);
            }
        });

        btnEliminarAficionadosEquipo.setText("Eliminar");
        btnEliminarAficionadosEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAficionadosEquipoActionPerformed(evt);
            }
        });

        btnEliminarTecnicosEquipo.setText("Eliminar");
        btnEliminarTecnicosEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTecnicosEquipoActionPerformed(evt);
            }
        });

        btnAgregarTecnicosEquipo.setText("Agregar");
        btnAgregarTecnicosEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTecnicosEquipoActionPerformed(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("RELACIONES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(102, 102, 102)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNombreEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPartidosjugadosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGolescontraEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGolesfavorEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(63, 63, 63))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTitulosinternacionalesEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAnnofuncacionEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTitulosnacionalesEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPuntosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel64)
                                    .addComponent(jLabel65)
                                    .addComponent(jLabel66))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboTecnicosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboAficionadosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboJugadoresEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboManagersEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnAgregarManagersEquipo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEliminarManagersEquipo))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnAgregarJugadoresEquipo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEliminarJugadoresEquipo))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnAgregarAficionadosEquipo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEliminarAficionadosEquipo)))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAgregarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnSalirEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnBuscarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnEliminarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnActualizarEquipo))
                                            .addComponent(btnLimpiarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAgregarTecnicosEquipo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarTecnicosEquipo))))
                            .addComponent(jLabel83))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(513, 513, 513))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNombreEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtAnnofuncacionEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTitulosnacionalesEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTitulosinternacionalesEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPuntosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel83)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel63)
                                    .addComponent(comboManagersEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregarManagersEquipo)
                                    .addComponent(btnEliminarManagersEquipo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel64)
                                    .addComponent(comboJugadoresEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel65)
                                    .addComponent(comboAficionadosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregarJugadoresEquipo)
                                    .addComponent(btnEliminarJugadoresEquipo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregarAficionadosEquipo)
                                    .addComponent(btnEliminarAficionadosEquipo)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregarEquipo)
                                    .addComponent(btnBuscarEquipo)
                                    .addComponent(btnEliminarEquipo)
                                    .addComponent(btnActualizarEquipo))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSalirEquipo)
                                    .addComponent(btnLimpiarEquipo))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarTecnicosEquipo)
                                .addComponent(btnEliminarTecnicosEquipo))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel66)
                                .addComponent(comboTecnicosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtGolesfavorEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtGolescontraEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtPartidosjugadosEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Equipo", jPanel2);

        jLabel16.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("INGRESAR ESTADIO");
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ID:");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Ciudad:");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Nombre:");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Capacidad:");

        txtIdEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdEstadioActionPerformed(evt);
            }
        });

        txtNombreEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEstadioActionPerformed(evt);
            }
        });

        tablaEstadio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Ciudad", "Capacidad"
            }
        ));
        jScrollPane3.setViewportView(tablaEstadio);

        btnAgregarEstadio.setText("AGREGAR");
        btnAgregarEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEstadioActionPerformed(evt);
            }
        });

        btnBuscarEstadio.setText("BUSCAR");
        btnBuscarEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEstadioActionPerformed(evt);
            }
        });

        btnEliminarEstadio.setText("ELIMINAR");
        btnEliminarEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEstadioActionPerformed(evt);
            }
        });

        btnActualizarEstadio.setText("ACTUALIZAR");
        btnActualizarEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEstadioActionPerformed(evt);
            }
        });

        btnSalirEstadio.setText("SALIR");
        btnSalirEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirEstadioActionPerformed(evt);
            }
        });

        btnLimpiarEstadio.setText("LIMPIAR");
        btnLimpiarEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarEstadioActionPerformed(evt);
            }
        });

        jLabel67.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Partidos:");

        comboPartidosEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPartidosEstadioActionPerformed(evt);
            }
        });

        btnAgregarPartidosEstadio.setText("Agregar");
        btnAgregarPartidosEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPartidosEstadioActionPerformed(evt);
            }
        });

        btnEliminarPartidosEstadio.setText("Eliminar");
        btnEliminarPartidosEstadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPartidosEstadioActionPerformed(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("RELACIONES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(btnAgregarEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalirEstadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscarEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEliminarEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizarEstadio))
                            .addComponent(btnLimpiarEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel67))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtCiudadEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtIdEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombreEstadio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCapacidadEstadio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboPartidosEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel84))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnAgregarPartidosEstadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarPartidosEstadio))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(562, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtIdEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtCiudadEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCapacidadEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel84))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(comboPartidosEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPartidosEstadio)
                    .addComponent(btnEliminarPartidosEstadio))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarEstadio)
                    .addComponent(btnBuscarEstadio)
                    .addComponent(btnEliminarEstadio)
                    .addComponent(btnActualizarEstadio))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalirEstadio)
                    .addComponent(btnLimpiarEstadio))
                .addContainerGap(232, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Estadio", jPanel3);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Numero:");

        jLabel21.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("INGRESAR JORNADA");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtIdJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdJornadaActionPerformed(evt);
            }
        });

        btnAgregarJornada.setText("AGREGAR");
        btnAgregarJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarJornadaActionPerformed(evt);
            }
        });

        btnBuscarJornada.setText("BUSCAR");
        btnBuscarJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarJornadaActionPerformed(evt);
            }
        });

        btnEliminarJornada.setText("ELIMINAR");
        btnEliminarJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarJornadaActionPerformed(evt);
            }
        });

        btnActualizarJornada.setText("ACTUALIZAR");
        btnActualizarJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarJornadaActionPerformed(evt);
            }
        });

        btnSalirJornada.setText("SALIR");
        btnSalirJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirJornadaActionPerformed(evt);
            }
        });

        tablaJornada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Numero", "Eslogan"
            }
        ));
        jScrollPane4.setViewportView(tablaJornada);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Eslogan:");

        btnLimpiarJornada.setText("LIMPIAR");
        btnLimpiarJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarJornadaActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Partidos:");

        comboPartidosJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPartidosJornadaActionPerformed(evt);
            }
        });

        btnAgregarPartidosJornada.setText("Agregar");
        btnAgregarPartidosJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPartidosJornadaActionPerformed(evt);
            }
        });

        btnEliminarPartidosJornada.setText("Eliminar");
        btnEliminarPartidosJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPartidosJornadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(txtEsloganJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNumeroJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(jLabel21))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel69)
                        .addGap(18, 18, 18)
                        .addComponent(comboPartidosJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarPartidosJornada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarPartidosJornada))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(btnAgregarJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalirJornada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscarJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnEliminarJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizarJornada))
                            .addComponent(btnLimpiarJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(577, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNumeroJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtEsloganJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(comboPartidosJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPartidosJornada)
                    .addComponent(btnEliminarPartidosJornada))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarJornada)
                    .addComponent(btnBuscarJornada)
                    .addComponent(btnEliminarJornada)
                    .addComponent(btnActualizarJornada))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalirJornada)
                    .addComponent(btnLimpiarJornada))
                .addContainerGap(237, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Jornada", jPanel4);

        txtIdPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPartidoActionPerformed(evt);
            }
        });

        txtGolesLocalPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGolesLocalPartidoActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Goles Visitante");

        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Goles Local");

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Fecha:");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("ID:");

        jLabel27.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("INGRESAR PARTIDO");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tablaPartido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Fecha", "Puntos Ganador", "Equipo Local", "Goles Local", "Equipo Visitante", "Goles Visitante"
            }
        ));
        jScrollPane5.setViewportView(tablaPartido);

        btnAgregarPartido.setText("AGREGAR");
        btnAgregarPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPartidoActionPerformed(evt);
            }
        });

        btnBuscarPartido.setText("BUSCAR");
        btnBuscarPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPartidoActionPerformed(evt);
            }
        });

        btnEliminarPartido.setText("ELIMINAR");
        btnEliminarPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPartidoActionPerformed(evt);
            }
        });

        btnActualizarPartido.setText("ACTUALIZAR");
        btnActualizarPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPartidoActionPerformed(evt);
            }
        });

        btnSalirPartido.setText("SALIR");
        btnSalirPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPartidoActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Puntos Ganador");

        txtPuntosGanadorPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntosGanadorPartidoActionPerformed(evt);
            }
        });

        btnLimpiarPartido.setText("LIMPIAR");
        btnLimpiarPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarPartidoActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Equipo Local");

        jLabel61.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Equipo Visitante");

        jLabel62.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("VS");

        jLabel68.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Arbitro:");

        comboArbitroPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboArbitroPartidoActionPerformed(evt);
            }
        });

        btnAgregarArbitroPartido.setText("Agregar");
        btnAgregarArbitroPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarArbitroPartidoActionPerformed(evt);
            }
        });

        btnEliminarArbitroPartido.setText("Eliminar");
        btnEliminarArbitroPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArbitroPartidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAgregarPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalirPartido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscarPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnEliminarPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizarPartido))
                    .addComponent(btnLimpiarPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(348, 348, 348))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGolesLocalPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel60)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboEquipoLocalPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(24, 24, 24)
                                        .addComponent(txtGolesVisitantePartido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel62)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel61)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboEquipoVisitantePartido, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addComponent(comboArbitroPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel68))
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregarArbitroPartido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarArbitroPartido))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26))
                                .addGap(91, 91, 91)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFechaPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPuntosGanadorPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jLabel27)))
                .addContainerGap(227, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtIdPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtFechaPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPuntosGanadorPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(comboEquipoLocalPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62)
                            .addComponent(jLabel61)
                            .addComponent(comboEquipoVisitantePartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(txtGolesLocalPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtGolesVisitantePartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(comboArbitroPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarArbitroPartido)
                    .addComponent(btnEliminarArbitroPartido))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarPartido)
                    .addComponent(btnBuscarPartido)
                    .addComponent(btnEliminarPartido)
                    .addComponent(btnActualizarPartido))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalirPartido)
                    .addComponent(btnLimpiarPartido))
                .addContainerGap(156, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Partido", jPanel5);

        tablaManager.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Años de Afiliación"
            }
        ));
        jScrollPane11.setViewportView(tablaManager);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado Managers", jPanel13);

        tablaJugador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Nacionalidad", "Posición", "Numero de Goles", "Salario Jugador"
            }
        ));
        jScrollPane12.setViewportView(tablaJugador);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado Jugadores", jPanel14);

        tablaAficionado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Años de Fidelidad", "Abonado"
            }
        ));
        jScrollPane13.setViewportView(tablaAficionado);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado Aficionados", jPanel15);

        tablaTecnico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Años de experiencia", "Salario Tecnicos"
            }
        ));
        jScrollPane14.setViewportView(tablaTecnico);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado Tecnicos", jPanel16);

        TablaArbitro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Arbitro Fifa"
            }
        ));
        jScrollPane15.setViewportView(TablaArbitro);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado Arbitro", jPanel17);

        txtCedulaPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaPersonaActionPerformed(evt);
            }
        });

        txtApellidoPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoPersonaActionPerformed(evt);
            }
        });

        comboTipoPersona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Jugador", "Aficionado", "Tecnico", "Arbitro" }));
        comboTipoPersona.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTipoPersonaItemStateChanged(evt);
            }
        });
        comboTipoPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoPersonaActionPerformed(evt);
            }
        });

        txtNacionalidadJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNacionalidadJugadorActionPerformed(evt);
            }
        });

        txtNumeroGolesJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroGolesJugadorActionPerformed(evt);
            }
        });

        txtAnnosFidelidadAficionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnnosFidelidadAficionadoActionPerformed(evt);
            }
        });

        CheckBoxAbonadoAficionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxAbonadoAficionadoActionPerformed(evt);
            }
        });

        txtAnnosExperienciaTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnnosExperienciaTecnicoActionPerformed(evt);
            }
        });

        txtSalarioTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalarioTecnicoActionPerformed(evt);
            }
        });

        txtAnnosAfiliacionManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnnosAfiliacionManagerActionPerformed(evt);
            }
        });

        CheckBoxFifaArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxFifaArbitroActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Tipo:");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Edad:");

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Apellido:");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Nombre:");

        jLabel33.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Cedula:");

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("PERSONA");

        jLabel36.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("MANAGER");

        jLabel37.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Años de Afiliación:");

        jLabel38.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Salario Jugador:");

        jLabel39.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Numero de Goles:");

        jLabel40.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Posición:");

        jLabel41.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Nacionalidad:");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("JUGADOR");

        jLabel43.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("ARBITRO");

        jLabel44.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Arbitro FIFA:");

        jLabel45.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("AFICIONADO");

        jLabel46.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Años de Fidelidad:");

        jLabel47.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Abonado:");

        jLabel34.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("INGRESAR PERSONA");
        jLabel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel48.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("TECNICO");

        jLabel49.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Años de Experiencia:");

        jLabel50.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Salario Tecnico:");

        btnAgregarPersona.setText("AGREGAR");
        btnAgregarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPersonaActionPerformed(evt);
            }
        });

        btnBuscarPersona.setText("BUSCAR");
        btnBuscarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPersonaActionPerformed(evt);
            }
        });

        btnEliminarPersona.setText("ELIMINAR");
        btnEliminarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPersonaActionPerformed(evt);
            }
        });

        btnActualizarPersona.setText("ACTUALIZAR");
        btnActualizarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPersonaActionPerformed(evt);
            }
        });

        btnSalirJornada2.setText("SALIR");
        btnSalirJornada2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirJornada2ActionPerformed(evt);
            }
        });

        btnLimpiarPersona.setText("LIMPIAR");
        btnLimpiarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarPersonaActionPerformed(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Jugadores:");

        comboJugadorPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJugadorPersonaActionPerformed(evt);
            }
        });

        btnAgregarJugadorPersona.setText("Agregar");
        btnAgregarJugadorPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarJugadorPersonaActionPerformed(evt);
            }
        });

        btnEliminarJugadorPersona.setText("Eliminar");
        btnEliminarJugadorPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarJugadorPersonaActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("ESTE ESPACIO SOLO ES PARA LOS MANAGERS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addGap(41, 41, 41)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckBoxFifaArbitro)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtAnnosAfiliacionManager, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboJugadorPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarJugadorPersona)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarJugadorPersona))))
                    .addComponent(jLabel43)
                    .addComponent(jLabel35)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombrePersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtCedulaPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEdadPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellidoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel39)
                                            .addComponent(jLabel38))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSalarioJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNumeroGolesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel72)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel40)
                                                    .addComponent(jLabel41))
                                                .addGap(29, 29, 29)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtPosicionJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtNacionalidadJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(jLabel42))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel45)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel46)
                                                    .addComponent(jLabel47))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtAnnosFidelidadAficionado, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(CheckBoxAbonadoAficionado)))
                                            .addComponent(jLabel34))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(btnAgregarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addComponent(btnSalirJornada2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnLimpiarPersona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addComponent(btnBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnEliminarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(btnActualizarPersona))
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                    .addComponent(jLabel50)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txtSalarioTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel48)
                                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addComponent(jLabel49)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtAnnosExperienciaTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))))))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtCedulaPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(txtNombrePersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtApellidoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEdadPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jLabel48))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtNacionalidadJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(txtAnnosFidelidadAficionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)
                            .addComponent(txtAnnosExperienciaTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel40)
                                .addComponent(txtPosicionJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel47))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel50)
                                    .addComponent(txtSalarioTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(CheckBoxAbonadoAficionado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(txtNumeroGolesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSalarioJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(comboTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel71)
                        .addComponent(comboJugadorPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarJugadorPersona)
                        .addComponent(btnEliminarJugadorPersona))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(txtAnnosAfiliacionManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarPersona)
                        .addComponent(btnBuscarPersona)
                        .addComponent(btnEliminarPersona)
                        .addComponent(btnActualizarPersona)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalirJornada2)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43)
                        .addComponent(btnLimpiarPersona)))
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44)
                    .addComponent(CheckBoxFifaArbitro))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Persona", jPanel6);

        jLabel70.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Jugador más joven de la liga:");

        txtInformesPartidosGoleada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInformesPartidosGoleadaActionPerformed(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("INFORMES ");
        jLabel73.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnGenerarInformes.setText("GENERAR INFORME");
        btnGenerarInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarInformesActionPerformed(evt);
            }
        });

        btnSalirJornada3.setText("SALIR");
        btnSalirJornada3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirJornada3ActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Cantidad de partidos en el que un equipo ganó por goleada, quiere decir que el ganador sacó 4 goles de diferencia al contrincante");

        txtInformesJugadormasJoven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInformesJugadormasJoven1ActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Jugador que ha marcado más goles en la liga:");

        txtJugadorMasGolesLiga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJugadorMasGolesLigaActionPerformed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Arquero que le han marcado menos goles, esta información se puede obtener de los goles en contra");

        txtArqueroMenosGoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArqueroMenosGolesActionPerformed(evt);
            }
        });

        txtNominaJuadorTecnicoMasAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNominaJuadorTecnicoMasAltaActionPerformed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Equipo con nómina de jugadores y técnico más alta");

        txtEquipoMayorAficionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEquipoMayorAficionadosActionPerformed(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Equipo con mayor cantidad de aficionados");

        jLabel79.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("Identificador del partido el cual tuvo más goles");

        txtIdPartidoMasGoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPartidoMasGolesActionPerformed(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Nombre del estadio donde se marcaron más goles en toda la liga");

        txtNombreEstadioMasGoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEstadioMasGolesActionPerformed(evt);
            }
        });

        tablaPromedioEdadEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Equipo", "Promedio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablaPromedioEdadEquipos);

        jLabel81.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Promedio de edad Equipos");

        tablaDePosicionesEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Equipo", "Puntos", "Diferencia de goles"
            }
        ));
        jScrollPane7.setViewportView(tablaDePosicionesEquipos);

        jLabel82.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Tabla de Posiciones");

        jLabel85.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("La cantidad de goles que marco el equipo con mas puntos");

        txtCantidadGolesEquipoMasPuntos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadGolesEquipoMasPuntosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(535, 535, 535)
                        .addComponent(jLabel73))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70)
                            .addComponent(txtInformesJugadormasJoven1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInformesPartidosGoleada, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtArqueroMenosGoles, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76)
                            .addComponent(jLabel79)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txtIdPartidoMasGoles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(108, 108, 108)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGenerarInformes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSalirJornada3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel85)
                            .addComponent(txtCantidadGolesEquipoMasPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel81))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel82)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreEstadioMasGoles, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel80)
                                    .addComponent(txtNominaJuadorTecnicoMasAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel77)
                                    .addComponent(txtEquipoMayorAficionados, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel78)
                                    .addComponent(txtJugadorMasGolesLiga, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel75))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInformesJugadormasJoven1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInformesPartidosGoleada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNominaJuadorTecnicoMasAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEquipoMayorAficionados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJugadorMasGolesLiga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel76)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArqueroMenosGoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdPartidoMasGoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreEstadioMasGoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel85)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadGolesEquipoMasPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(jLabel82))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(58, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGenerarInformes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirJornada3)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Informe", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPuntosGanadorPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntosGanadorPartidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPuntosGanadorPartidoActionPerformed

    private void btnSalirPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPartidoActionPerformed
        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirPartidoActionPerformed

    private void btnActualizarPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPartidoActionPerformed

        String id = this.txtIdPartido.getText();
        String fecha = this.txtFechaPartido.getText();
        int puntosGanandor = Integer.parseInt(this.txtPuntosGanadorPartido.getText());
        String equipoLocal = (String) this.comboEquipoLocalPartido.getSelectedItem();
        int golesLocal = Integer.parseInt(this.txtGolesLocalPartido.getText());
        String equipoVisitante = (String) this.comboEquipoVisitantePartido.getSelectedItem();
        int golesVisitante = Integer.parseInt(this.txtGolesVisitantePartido.getText());

        //Relaciones de Equipo Loca y Equipo Visitante
        String partesEquipoLocal[] = equipoLocal.split(" - ");
        String partesEquipoVisitsnte[] = equipoVisitante.split(" - ");

        String idEquipoLocal = partesEquipoLocal[0];
        String idEquipoVisitante = partesEquipoVisitsnte[0];

        Partido elPartido = new Partido(id, fecha, puntosGanandor, idEquipoLocal, golesLocal, idEquipoVisitante, golesVisitante);

        Partido exito = (Partido) this.miControladorPartido.update(elPartido);
        if (exito != null) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            actualizarTablaPartido();
            actualizarGolesEquipo(idEquipoLocal, idEquipoVisitante, golesLocal, golesVisitante);

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al Actualizar");
        }

        limpiarCajasPartido();
        actualizarTablaPartido();
    }//GEN-LAST:event_btnActualizarPartidoActionPerformed

    private void btnEliminarPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPartidoActionPerformed

        String idPartido = this.txtIdPartido.getText();
        boolean exito = this.miControladorPartido.delete(idPartido);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Partido eliminado Correctamente");
            limpiarCajasPartido();
            actualizarTablaPartido();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar el partido");
        }
    }//GEN-LAST:event_btnEliminarPartidoActionPerformed

    private void btnBuscarPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPartidoActionPerformed

        String idBuscado = this.txtIdPartido.getText();

        Object respuesta = this.miControladorPartido.get(idBuscado);

        if (respuesta == null) {
            JOptionPane.showMessageDialog(this, "Partido Buscado no existe");
        } else {
            Partido encontrado = (Partido) respuesta;
            
            this.txtIdPartido.setText(encontrado.getId());
            this.txtFechaPartido.setText(encontrado.getFecha());
            this.txtGolesLocalPartido.setText("" + encontrado.getGolesLocal());
            this.txtGolesVisitantePartido.setText("" + encontrado.getGolesVisitante());
            this.txtPuntosGanadorPartido.setText("" + encontrado.getPuntosGanador());
            this.comboEquipoLocalPartido.setSelectedItem(encontrado.getEquipoLocal());
            this.comboEquipoVisitantePartido.setSelectedItem(encontrado.getEquipoVisitante());
        }
    }//GEN-LAST:event_btnBuscarPartidoActionPerformed

    private void btnAgregarPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPartidoActionPerformed

        String id = this.txtIdPartido.getText();
        String fecha = this.txtFechaPartido.getText();
        int puntosGanandor = Integer.parseInt(this.txtPuntosGanadorPartido.getText());
        String equipoLocal = (String) this.comboEquipoLocalPartido.getSelectedItem();
        int golesLocal = Integer.parseInt(this.txtGolesLocalPartido.getText());
        String equipoVisitante = (String) this.comboEquipoVisitantePartido.getSelectedItem();
        int golesVisitante = Integer.parseInt(this.txtGolesVisitantePartido.getText());

        //Relaciones de Equipo Loca y Equipo Visitante
        String partesEquipoLocal[] = equipoLocal.split(" - ");
        String partesEquipoVisitsnte[] = equipoVisitante.split(" - ");

        String idEquipoLocal = partesEquipoLocal[0];
        String idEquipoVisitante = partesEquipoVisitsnte[0];

        Partido nuevoPartido = new Partido(id, fecha, puntosGanandor, idEquipoLocal, golesLocal, idEquipoVisitante, golesVisitante);

        boolean exito = this.miControladorPartido.create(nuevoPartido);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Creado exitosamente");
            actualizarTablaPartido();
            actualizarGolesEquipo(idEquipoLocal, idEquipoVisitante, golesLocal, golesVisitante);

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al crear");
        }

        limpiarCajasPartido();
        actualizarTablaPartido();
    }//GEN-LAST:event_btnAgregarPartidoActionPerformed

    //ESTA FUNCIÓN ES PARA GENERAR UNA RELACIÓN ¡NO SIRVE PARA NADA!
    public void generarRelacionEquipoVisitante() {

        String idPartido = this.txtIdPartido.getText();
        String id = (String) this.comboEquipoLocalPartido.getSelectedItem();
        String partes[] = id.split(" - ");
        String idEquipo = partes[0];
        System.out.println(idEquipo);
        //this.miControladorEquipo.setPartido(idEquipo, idPartido);

        JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
    }
    private void txtGolesLocalPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGolesLocalPartidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGolesLocalPartidoActionPerformed

    private void txtIdPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPartidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPartidoActionPerformed

    private void btnSalirJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirJornadaActionPerformed
        eventoCierre();
        this.dispose();    }//GEN-LAST:event_btnSalirJornadaActionPerformed

    private void btnActualizarJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarJornadaActionPerformed

        String id = this.txtIdJornada.getText();
        int numero = Integer.parseInt(this.txtNumeroJornada.getText());
        String eslogan = this.txtEsloganJornada.getText();

        Jornada laJornada = new Jornada(id,
                numero,
                eslogan);

        Jornada exito = (Jornada) this.miControladorJornada.update(laJornada);
        if (exito != null) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al Actualizar");
        }

        limpiarCajasJornada();
        actualizarTablaJornada();
    }//GEN-LAST:event_btnActualizarJornadaActionPerformed

    private void btnEliminarJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarJornadaActionPerformed

        String idJornada = this.txtIdJornada.getText();
        boolean exito = this.miControladorJornada.delete(idJornada);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Competencia eliminada Correctamente");
            limpiarCajasJornada();
            actualizarTablaJornada();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar a la Competencia");
        }
    }//GEN-LAST:event_btnEliminarJornadaActionPerformed

    private void btnBuscarJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarJornadaActionPerformed

        String idBuscado = this.txtIdJornada.getText();

        Object respuesta = this.miControladorJornada.get(idBuscado);

        if (respuesta == null) {
            JOptionPane.showMessageDialog(this, "Jornada Buscada no existe");
        } else {
            Jornada encontrado = (Jornada) respuesta;
            this.txtIdCompetencia.setText(encontrado.getId());
            this.txtNumeroJornada.setText("" + encontrado.getNumero());
            this.txtEsloganJornada.setText(encontrado.getEslogan());
        }
    }//GEN-LAST:event_btnBuscarJornadaActionPerformed

    private void btnAgregarJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarJornadaActionPerformed

        String id = this.txtIdJornada.getText();
        int numero = Integer.parseInt(this.txtNumeroJornada.getText());
        String eslogan = this.txtEsloganJornada.getText();

        Jornada nuevaJornada = new Jornada(id, numero, eslogan);

        boolean exito = this.miControladorJornada.create(nuevaJornada);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Creado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al crear");
        }

        limpiarCajasJornada();
        actualizarTablaJornada();

    }//GEN-LAST:event_btnAgregarJornadaActionPerformed

    private void txtIdJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdJornadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdJornadaActionPerformed

    private void btnSalirEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirEstadioActionPerformed
        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirEstadioActionPerformed

    private void btnActualizarEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEstadioActionPerformed

        String id = this.txtIdEstadio.getText();
        String nombre = this.txtNombreEstadio.getText();
        String ciudad = this.txtCiudadEstadio.getText();
        int capacidad = Integer.parseInt(this.txtCapacidadEstadio.getText());

        Estadio elEstadio = new Estadio(id,
                nombre,
                ciudad,
                capacidad);

        Estadio exito = (Estadio) this.miControladorEstadio.update(elEstadio);
        if (exito != null) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al Actualizar");
        }

        limpiarCajasEstadio();
        actualizarTablaEstadio();
    }//GEN-LAST:event_btnActualizarEstadioActionPerformed

    private void btnEliminarEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEstadioActionPerformed

        String idEstadio = this.txtIdEstadio.getText();
        boolean exito = this.miControladorEstadio.delete(idEstadio);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Competencia eliminada Correctamente");
            limpiarCajasEstadio();
            actualizarTablaEstadio();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar a la Competencia");
        }
    }//GEN-LAST:event_btnEliminarEstadioActionPerformed

    private void btnBuscarEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEstadioActionPerformed

        String idBuscado = this.txtIdEstadio.getText();

        Object respuesta = this.miControladorEstadio.get(idBuscado);

        if (respuesta == null) {
            JOptionPane.showMessageDialog(this, "Estadio buscado no existe");
        } else {
            Estadio encontrado = (Estadio) respuesta;
            this.txtIdEstadio.setText(encontrado.getId());
            this.txtNombreEstadio.setText(encontrado.getNombre());
            this.txtCiudadEstadio.setText(encontrado.getCiudad());
            this.txtCapacidadEstadio.setText("" + encontrado.getCapacidad());
        }
    }//GEN-LAST:event_btnBuscarEstadioActionPerformed

    private void btnAgregarEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEstadioActionPerformed

        String id = this.txtIdEstadio.getText();
        String nombre = this.txtNombreEstadio.getText();
        String ciudad = this.txtCiudadEstadio.getText();
        int capacidad = Integer.parseInt(this.txtCapacidadEstadio.getText());

        Estadio estadioNuevo = new Estadio(id, nombre, ciudad, capacidad);

        boolean exito = this.miControladorEstadio.create(estadioNuevo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Creado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al crear");
        }

        limpiarCajasEstadio();
        actualizarTablaEstadio();
    }//GEN-LAST:event_btnAgregarEstadioActionPerformed

    private void txtNombreEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEstadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreEstadioActionPerformed

    private void txtIdEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdEstadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdEstadioActionPerformed

    private void btnSalirEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirEquipoActionPerformed
        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirEquipoActionPerformed

    private void btnActualizarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEquipoActionPerformed

        String id = this.txtIdEquipo.getText();
        String nombre = this.txtNombreEquipo.getText();
        int annoFundacion = Integer.parseInt(this.txtAnnofuncacionEquipo.getText());
        int titulosNacionales = Integer.parseInt(this.txtTitulosnacionalesEquipo.getText());
        int titulosInternacionales = Integer.parseInt(this.txtTitulosinternacionalesEquipo.getText());
        int puntos = Integer.parseInt(this.txtPuntosEquipo.getText());
        int golesFavor = Integer.parseInt(this.txtGolesfavorEquipo.getText());
        int golesContra = Integer.parseInt(this.txtGolescontraEquipo.getText());
        int partidosJugados = Integer.parseInt(this.txtPartidosjugadosEquipo.getText());

        Equipo elEquipo = new Equipo(id,
                nombre,
                annoFundacion,
                titulosNacionales,
                titulosInternacionales,
                puntos,
                golesFavor,
                golesContra,
                partidosJugados);

        Equipo exito = (Equipo) this.miControladorEquipo.update(elEquipo);
        if (exito != null) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al Actualizar");
        }

        limpiarCajasEquipo();
        actualizarTablaEquipo();

    }//GEN-LAST:event_btnActualizarEquipoActionPerformed

    private void btnEliminarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEquipoActionPerformed
        String idEquipo = this.txtIdEquipo.getText();
        boolean exito = this.miControladorEquipo.delete(idEquipo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Competencia eliminada Correctamente");
            limpiarCajasEquipo();
            actualizarTablaEquipo();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar a la Competencia");
        }
    }//GEN-LAST:event_btnEliminarEquipoActionPerformed

    private void btnBuscarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEquipoActionPerformed

        String idBuscado = this.txtIdEquipo.getText();

        Object respuesta = this.miControladorEquipo.get(idBuscado);

        if (respuesta == null) {
            JOptionPane.showMessageDialog(this, "Equipo buscado no existe");
        } else {
            Equipo encontrado = (Equipo) respuesta;
            this.txtIdEquipo.setText(encontrado.getId());
            this.txtNombreEquipo.setText(encontrado.getNombre());
            this.txtAnnofuncacionEquipo.setText("" + encontrado.getAnnoFundacion());
            this.txtTitulosnacionalesEquipo.setText("" + encontrado.getTitulosNacionales());
            this.txtTitulosinternacionalesEquipo.setText("" + encontrado.getTitulosInternaciones());
            this.txtPuntosEquipo.setText("" + encontrado.getPuntos());
            this.txtGolesfavorEquipo.setText("" + encontrado.getGolesFavor());
            this.txtGolescontraEquipo.setText("" + encontrado.getGolesContra());
            this.txtPartidosjugadosEquipo.setText("" + encontrado.getPartidosJugados());
        }

    }//GEN-LAST:event_btnBuscarEquipoActionPerformed

    private void btnAgregarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEquipoActionPerformed

        String id = this.txtIdEquipo.getText();
        String nombre = this.txtNombreEquipo.getText();
        int annoFundacion = Integer.parseInt(this.txtAnnofuncacionEquipo.getText());
        int titulosNacionales = Integer.parseInt(this.txtTitulosnacionalesEquipo.getText());
        int titulosInternacionales = Integer.parseInt(this.txtTitulosinternacionalesEquipo.getText());
        int puntos = Integer.parseInt(this.txtPuntosEquipo.getText());
        int golesFavor = Integer.parseInt(this.txtGolesfavorEquipo.getText());
        int golesContra = Integer.parseInt(this.txtGolescontraEquipo.getText());
        int partidosJugados = Integer.parseInt(this.txtPartidosjugadosEquipo.getText());

        Equipo nuevoEquipo = new Equipo(id, nombre, annoFundacion, titulosNacionales, titulosInternacionales, puntos, golesFavor, golesContra, partidosJugados);

        boolean exito = this.miControladorEquipo.create(nuevoEquipo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Creado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al crear");
        }

        limpiarCajasEquipo();
        actualizarTablaEquipo();
    }//GEN-LAST:event_btnAgregarEquipoActionPerformed

    private void txtGolesfavorEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGolesfavorEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGolesfavorEquipoActionPerformed

    private void txtAnnofuncacionEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnnofuncacionEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnnofuncacionEquipoActionPerformed

    private void txtIdEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdEquipoActionPerformed

    private void btnSalirComptenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirComptenciaActionPerformed

        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirComptenciaActionPerformed

    private void btnActualizarCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCompetenciaActionPerformed

        String id = this.txtIdCompetencia.getText();
        String nombre = this.txtNombreCompetencia.getText();
        Competencia laCompetencia = new Competencia(id,
                nombre);

        Competencia exito = (Competencia) this.miControladorCompetencia.update(laCompetencia);
        if (exito != null) {
            JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
        } else {
            JOptionPane.showMessageDialog(this, "Problemas al Actualizar");
        }

        LimpiarCajasCompetencia();
        actualizarTablaCompetencia();

        //Actualizar Tablas
        actualizarTablaCompetencia();
        actualizarTablaEquipo();
        actualizarTablaEstadio();
        actualizarTablaJornada();
        actualizarTablaPartido();
        actualizarTablaManager();
        actualizarTablaJugador();
        actualizarTablaAficionado();
        actualizarTablaTecnico();
        actualizarTablaArbitro();

        //ComboBox de la Competencia
        actualizarComboPersonasManagers();
        actualizarComboPersonasJugadores();
        actualizarComboPersonasAficionados();
        actualizarComboPersonasTecnicos();
        actualizarComboPersonasArbitros();
        actualizarComboEquiposCompetencia();
        actualizarComboJornadaCompetencia();
        actualizarComboEstadioCompetencia();

        //ComboBox del Equipo
        actualizarComboPersonasManagersEquipo();
        actualizarComboPersonasJugadoresEquipo();
        actualizarComboPersonasAficionadosEquipo();
        actualizarComboPersonasTecnicosEquipo();

        //ComboBox de Estadio
        actualizarComboEstadioPartido();

        //ComboBox de Partido
        actualizarComboPartidocomboEquipoLocal();
        actualizarComboPartidocomboEquipoVisitante();
        actualizarPartidoCombocomboArbitro();

        //ComboBox de Jornada
        actualizarComboPartidosJornada();

        //ComoboBox de Persona
        actualizarComboJugadoresPersonasManagers();
    }//GEN-LAST:event_btnActualizarCompetenciaActionPerformed

    private void btnEliminarCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCompetenciaActionPerformed

        String idCompetencia = this.txtIdCompetencia.getText();
        boolean exito = this.miControladorCompetencia.delete(idCompetencia);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Competencia eliminada Correctamente");
            LimpiarCajasCompetencia();
            actualizarTablaCompetencia();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar a la Competencia");
        }
    }//GEN-LAST:event_btnEliminarCompetenciaActionPerformed

    private void btnBuscarCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCompetenciaActionPerformed

        String idBuscado = this.txtIdCompetencia.getText();

        Object respuesta = this.miControladorCompetencia.get(idBuscado);

        if (respuesta == null) {
            JOptionPane.showMessageDialog(this, "Competencia  buscada no existe");
        } else {
            Competencia encontrado = (Competencia) respuesta;
            this.txtIdCompetencia.setText(encontrado.getId());
            this.txtNombreCompetencia.setText(encontrado.getNombre());
        }
    }//GEN-LAST:event_btnBuscarCompetenciaActionPerformed

    private void btnAgregarCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompetenciaActionPerformed

        String id = this.txtIdCompetencia.getText();
        String nombre = this.txtNombreCompetencia.getText();
        Competencia nuevaCompetencia = new Competencia(id, nombre);

        boolean exito = this.miControladorCompetencia.create(nuevaCompetencia);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Creado exitosamente");

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "Problemas al crear");
        }

        LimpiarCajasCompetencia();
        actualizarTablaCompetencia();
    }//GEN-LAST:event_btnAgregarCompetenciaActionPerformed

    private void txtIdCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCompetenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompetenciaActionPerformed

    private void txtApellidoPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoPersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoPersonaActionPerformed

    private void txtCedulaPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaPersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaPersonaActionPerformed

    private void comboTipoPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoPersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoPersonaActionPerformed

    private void txtAnnosAfiliacionManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnnosAfiliacionManagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnnosAfiliacionManagerActionPerformed

    private void txtNacionalidadJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNacionalidadJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNacionalidadJugadorActionPerformed

    private void txtNumeroGolesJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroGolesJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroGolesJugadorActionPerformed

    private void CheckBoxFifaArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxFifaArbitroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxFifaArbitroActionPerformed

    private void txtAnnosFidelidadAficionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnnosFidelidadAficionadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnnosFidelidadAficionadoActionPerformed

    private void CheckBoxAbonadoAficionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxAbonadoAficionadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxAbonadoAficionadoActionPerformed

    private void txtAnnosExperienciaTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnnosExperienciaTecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnnosExperienciaTecnicoActionPerformed

    private void txtSalarioTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalarioTecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalarioTecnicoActionPerformed

    private void btnAgregarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPersonaActionPerformed

        if (this.comboTipoPersona.getSelectedIndex() == 0) {

            String cedula = this.txtCedulaPersona.getText();
            String nombre = this.txtNombrePersona.getText();
            String apellido = this.txtApellidoPersona.getText();
            int edad = Integer.parseInt(this.txtEdadPersona.getText());
            int annosAfiliacion = Integer.parseInt(this.txtAnnosAfiliacionManager.getText());

            Manager nuevoManager = new Manager(cedula, nombre, apellido, edad, annosAfiliacion);

            boolean exito = this.miControladorPersona.create(nuevoManager);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al crear");
            }

            actualizarTablaManager();

        } else if (this.comboTipoPersona.getSelectedIndex() == 1) {

            String cedula = this.txtCedulaPersona.getText();
            String nombre = this.txtNombrePersona.getText();
            String apellido = this.txtApellidoPersona.getText();
            int edad = Integer.parseInt(this.txtEdadPersona.getText());
            String nacionalidad = this.txtNacionalidadJugador.getText();
            String posicion = this.txtPosicionJugador.getText();
            int numeroGoles = Integer.parseInt(this.txtNumeroGolesJugador.getText());
            double salarioJugador = Double.parseDouble(this.txtSalarioJugador.getText());

            Jugador nuevoJugador = new Jugador(cedula, nombre, apellido, edad, nacionalidad, posicion, numeroGoles, salarioJugador);

            boolean exito = this.miControladorPersona.create(nuevoJugador);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al crear");
            }

            actualizarTablaJugador();

        } else if (this.comboTipoPersona.getSelectedIndex() == 2) {

            String cedula = this.txtCedulaPersona.getText();
            String nombre = this.txtNombrePersona.getText();
            String apellido = this.txtApellidoPersona.getText();
            int edad = Integer.parseInt(this.txtEdadPersona.getText());
            int annosFideliada = Integer.parseInt(this.txtAnnosFidelidadAficionado.getText());
            boolean abonado = this.CheckBoxAbonadoAficionado.isSelected();

            Aficionado nuevoAficionado = new Aficionado(cedula, nombre, apellido, edad, annosFideliada, abonado);

            boolean exito = this.miControladorPersona.create(nuevoAficionado);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al crear");
            }

            actualizarTablaAficionado();

        } else if (this.comboTipoPersona.getSelectedIndex() == 3) {

            String cedula = this.txtCedulaPersona.getText();
            String nombre = this.txtNombrePersona.getText();
            String apellido = this.txtApellidoPersona.getText();
            int edad = Integer.parseInt(this.txtEdadPersona.getText());
            int annosExperiencia = Integer.parseInt(this.txtAnnosExperienciaTecnico.getText());
            double salarioTecnico = Double.parseDouble(this.txtSalarioTecnico.getText());

            Tecnico nuevoTecnico = new Tecnico(cedula, nombre, apellido, edad, annosExperiencia, salarioTecnico);

            boolean exito = this.miControladorPersona.create(nuevoTecnico);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al crear");
            }

            actualizarTablaTecnico();

        } else if (this.comboTipoPersona.getSelectedIndex() == 4) {

            String cedula = this.txtCedulaPersona.getText();
            String nombre = this.txtNombrePersona.getText();
            String apellido = this.txtApellidoPersona.getText();
            int edad = Integer.parseInt(this.txtEdadPersona.getText());
            boolean arbitroFifa = this.CheckBoxFifaArbitro.isSelected();

            Arbitro nuevoArbitro = new Arbitro(cedula, nombre, apellido, edad, arbitroFifa);

            boolean exito = this.miControladorPersona.create(nuevoArbitro);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al crear");
            }

            actualizarTablaArbitro();
        }

        limpiarCajasPersona();

        //Actualizar Tablas
        actualizarTablaCompetencia();
        actualizarTablaEquipo();
        actualizarTablaEstadio();
        actualizarTablaJornada();
        actualizarTablaPartido();
        actualizarTablaManager();
        actualizarTablaJugador();
        actualizarTablaAficionado();
        actualizarTablaTecnico();
        actualizarTablaArbitro();

        //ComboBox de la Competencia
        actualizarComboPersonasManagers();
        actualizarComboPersonasJugadores();
        actualizarComboPersonasAficionados();
        actualizarComboPersonasTecnicos();
        actualizarComboPersonasArbitros();
        actualizarComboEquiposCompetencia();
        actualizarComboJornadaCompetencia();
        actualizarComboEstadioCompetencia();

        //ComboBox del Equipo
        actualizarComboPersonasManagersEquipo();
        actualizarComboPersonasJugadoresEquipo();
        actualizarComboPersonasAficionadosEquipo();
        actualizarComboPersonasTecnicosEquipo();

        //ComboBox de Estadio
        actualizarComboEstadioPartido();

        //ComboBox de Partido
        actualizarComboPartidocomboEquipoLocal();
        actualizarComboPartidocomboEquipoVisitante();
        actualizarPartidoCombocomboArbitro();

        //ComboBox de Jornada
        actualizarComboPartidosJornada();

        //ComoboBox de Persona
        actualizarComboJugadoresPersonasManagers();


    }//GEN-LAST:event_btnAgregarPersonaActionPerformed

    private void btnBuscarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPersonaActionPerformed

        String idBuscado = this.txtCedulaPersona.getText();

        Object respuesta = this.miControladorPersona.get(idBuscado);

        if (respuesta == null) {

            JOptionPane.showMessageDialog(this, "La persona buscada no existe");

        } else if (respuesta instanceof Manager) {

            Manager encontrado = (Manager) respuesta;
            this.txtCedulaPersona.setText(encontrado.getCedula());
            this.txtNombrePersona.setText(encontrado.getNombre());
            this.txtApellidoPersona.setText(encontrado.getApellido());
            this.txtEdadPersona.setText("" + encontrado.getEdad());
            this.txtAnnosAfiliacionManager.setText("" + encontrado.getAnnosAfiliacion());
            this.comboTipoPersona.setSelectedIndex(0);

        } else if (respuesta instanceof Jugador) {

            Jugador encontrado = (Jugador) respuesta;
            this.txtCedulaPersona.setText(encontrado.getCedula());
            this.txtNombrePersona.setText(encontrado.getNombre());
            this.txtApellidoPersona.setText(encontrado.getApellido());
            this.txtEdadPersona.setText("" + encontrado.getEdad());
            this.txtNacionalidadJugador.setText(encontrado.getNacionalidad());
            this.txtPosicionJugador.setText(encontrado.getPosicion());
            this.txtNumeroGolesJugador.setText("" + encontrado.getNumeroGoles());
            this.txtSalarioJugador.setText("" + encontrado.getSalario());
            this.comboTipoPersona.setSelectedIndex(1);

        } else if (respuesta instanceof Aficionado) {

            Aficionado encontrado = (Aficionado) respuesta;

            this.txtCedulaPersona.setText(encontrado.getCedula());
            this.txtNombrePersona.setText(encontrado.getNombre());
            this.txtApellidoPersona.setText(encontrado.getApellido());
            this.txtEdadPersona.setText("" + encontrado.getEdad());
            this.txtAnnosFidelidadAficionado.setText("" + encontrado.getAnnosFidelidad());
            this.CheckBoxAbonadoAficionado.setSelected(encontrado.isAbonado());
            this.comboTipoPersona.setSelectedIndex(2);

        } else if (respuesta instanceof Tecnico) {

            Tecnico encontrado = (Tecnico) respuesta;

            this.txtCedulaPersona.setText(encontrado.getCedula());
            this.txtNombrePersona.setText(encontrado.getNombre());
            this.txtApellidoPersona.setText(encontrado.getApellido());
            this.txtEdadPersona.setText("" + encontrado.getEdad());
            this.txtAnnosExperienciaTecnico.setText("" + encontrado.getAnnosExperiencia());
            this.txtSalarioTecnico.setText("" + encontrado.getSalario());
            this.comboTipoPersona.setSelectedIndex(3);

        } else if (respuesta instanceof Arbitro) {

            Arbitro encontrado = (Arbitro) respuesta;

            this.txtCedulaPersona.setText(encontrado.getCedula());
            this.txtNombrePersona.setText(encontrado.getNombre());
            this.txtApellidoPersona.setText(encontrado.getApellido());
            this.txtEdadPersona.setText("" + encontrado.getEdad());
            this.CheckBoxFifaArbitro.setSelected(encontrado.isArbitroFifa());
            this.comboTipoPersona.setSelectedIndex(4);
        }

    }//GEN-LAST:event_btnBuscarPersonaActionPerformed

    private void btnEliminarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPersonaActionPerformed

        String idPartido = this.txtCedulaPersona.getText();
        boolean exito = this.miControladorPersona.delete(idPartido);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Persona eliminada Correctamente");
            limpiarCajasPersona();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //Actualizar Tablas
            actualizarTablaCompetencia();
            actualizarTablaEquipo();
            actualizarTablaEstadio();
            actualizarTablaJornada();
            actualizarTablaPartido();
            actualizarTablaManager();
            actualizarTablaJugador();
            actualizarTablaAficionado();
            actualizarTablaTecnico();
            actualizarTablaArbitro();

            //ComboBox de la Competencia
            actualizarComboPersonasManagers();
            actualizarComboPersonasJugadores();
            actualizarComboPersonasAficionados();
            actualizarComboPersonasTecnicos();
            actualizarComboPersonasArbitros();
            actualizarComboEquiposCompetencia();
            actualizarComboJornadaCompetencia();
            actualizarComboEstadioCompetencia();

            //ComboBox del Equipo
            actualizarComboPersonasManagersEquipo();
            actualizarComboPersonasJugadoresEquipo();
            actualizarComboPersonasAficionadosEquipo();
            actualizarComboPersonasTecnicosEquipo();

            //ComboBox de Estadio
            actualizarComboEstadioPartido();

            //ComboBox de Partido
            actualizarComboPartidocomboEquipoLocal();
            actualizarComboPartidocomboEquipoVisitante();
            actualizarPartidoCombocomboArbitro();

            //ComboBox de Jornada
            actualizarComboPartidosJornada();

            //ComoboBox de Persona
            actualizarComboJugadoresPersonasManagers();

        } else {
            JOptionPane.showMessageDialog(this, "No se puede eliminar la persona");
        }
    }//GEN-LAST:event_btnEliminarPersonaActionPerformed

    private void btnActualizarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPersonaActionPerformed

        String cedula = this.txtCedulaPersona.getText();
        String nombre = this.txtNombrePersona.getText();
        String apellido = this.txtApellidoPersona.getText();
        int edad = Integer.parseInt(this.txtEdadPersona.getText());

        if (this.comboTipoPersona.getSelectedIndex() == 0) {

            int annosAfiliacion = Integer.parseInt(this.txtAnnosAfiliacionManager.getText());

            Manager elManager = new Manager(cedula, nombre, apellido, edad, annosAfiliacion);

            Manager exito = (Manager) this.miControladorPersona.update(elManager);

            if (exito != null) {
                JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al actualizar");
            }

            actualizarTablaManager();

        } else if (this.comboTipoPersona.getSelectedIndex() == 1) {

            String nacionalidad = this.txtNacionalidadJugador.getText();
            String posicion = this.txtPosicionJugador.getText();
            int numeroGoles = Integer.parseInt(this.txtNumeroGolesJugador.getText());
            double salarioJugador = Double.parseDouble(this.txtSalarioJugador.getText());

            Jugador elJugador = new Jugador(cedula, nombre, apellido, edad, nacionalidad, posicion, numeroGoles, salarioJugador);

            Jugador exito = (Jugador) this.miControladorPersona.update(elJugador);

            if (exito != null) {
                JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al actualizar");
            }

            actualizarTablaJugador();

        } else if (this.comboTipoPersona.getSelectedIndex() == 2) {

            int annosFideliada = Integer.parseInt(this.txtAnnosFidelidadAficionado.getText());
            boolean abonado = this.CheckBoxAbonadoAficionado.isSelected();

            Aficionado nuevoAficionado = new Aficionado(cedula, nombre, apellido, edad, annosFideliada, abonado);

            boolean exito = this.miControladorPersona.create(nuevoAficionado);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al actualizar");

                actualizarTablaAficionado();
            }

        } else if (this.comboTipoPersona.getSelectedIndex() == 3) {

            int annosExperiencia = Integer.parseInt(this.txtAnnosExperienciaTecnico.getText());
            double salarioTecnico = Double.parseDouble(this.txtSalarioTecnico.getText());

            Tecnico elTecnico = new Tecnico(cedula, nombre, apellido, edad, annosExperiencia, salarioTecnico);

            Tecnico exito = (Tecnico) this.miControladorPersona.update(elTecnico);

            if (exito != null) {
                JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al actualizar");
            }

            actualizarTablaTecnico();

        } else if (this.comboTipoPersona.getSelectedIndex() == 4) {

            boolean arbitroFifa = this.CheckBoxFifaArbitro.isSelected();

            Arbitro elArbitro = new Arbitro(cedula, nombre, apellido, edad, arbitroFifa);

            Arbitro exito = (Arbitro) this.miControladorPersona.update(elArbitro);

            if (exito != null) {
                JOptionPane.showMessageDialog(this, "Actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Problemas al actualizar");
            }

            actualizarTablaArbitro();

        }

        limpiarCajasPersona();

        //Actualizar Tablas
        actualizarTablaCompetencia();
        actualizarTablaEquipo();
        actualizarTablaEstadio();
        actualizarTablaJornada();
        actualizarTablaPartido();
        actualizarTablaManager();
        actualizarTablaJugador();
        actualizarTablaAficionado();
        actualizarTablaTecnico();
        actualizarTablaArbitro();

        //ComboBox de la Competencia
        actualizarComboPersonasManagers();
        actualizarComboPersonasJugadores();
        actualizarComboPersonasAficionados();
        actualizarComboPersonasTecnicos();
        actualizarComboPersonasArbitros();
        actualizarComboEquiposCompetencia();
        actualizarComboJornadaCompetencia();
        actualizarComboEstadioCompetencia();

        //ComboBox del Equipo
        actualizarComboPersonasManagersEquipo();
        actualizarComboPersonasJugadoresEquipo();
        actualizarComboPersonasAficionadosEquipo();
        actualizarComboPersonasTecnicosEquipo();

        //ComboBox de Estadio
        actualizarComboEstadioPartido();

        //ComboBox de Partido
        actualizarComboPartidocomboEquipoLocal();
        actualizarComboPartidocomboEquipoVisitante();
        actualizarPartidoCombocomboArbitro();

        //ComboBox de Jornada
        actualizarComboPartidosJornada();

        //ComoboBox de Persona
        actualizarComboJugadoresPersonasManagers();


    }//GEN-LAST:event_btnActualizarPersonaActionPerformed

    private void btnSalirJornada2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirJornada2ActionPerformed
        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirJornada2ActionPerformed

    private void comboTipoPersonaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoPersonaItemStateChanged

        if (comboTipoPersona.getSelectedIndex() == 0) {

            this.txtAnnosAfiliacionManager.setEnabled(true);
            this.comboJugadorPersona.setEnabled(true);
            this.btnAgregarJugadorPersona.setEnabled(true);
            this.btnEliminarJugadorPersona.setEnabled(true);

            this.txtNacionalidadJugador.setEnabled(false);
            this.txtPosicionJugador.setEnabled(false);
            this.txtNumeroGolesJugador.setEnabled(false);
            this.txtSalarioJugador.setEnabled(false);

            this.txtAnnosFidelidadAficionado.setEnabled(false);
            this.CheckBoxAbonadoAficionado.setEnabled(false);

            this.txtAnnosExperienciaTecnico.setEnabled(false);
            this.txtSalarioTecnico.setEnabled(false);

            this.CheckBoxFifaArbitro.setEnabled(false);

            limpiarCajasPersonaHerencia();

        } else if (comboTipoPersona.getSelectedIndex() == 1) {

            this.txtAnnosAfiliacionManager.setEnabled(false);
            this.comboJugadorPersona.setEnabled(false);
            this.btnAgregarJugadorPersona.setEnabled(false);
            this.btnEliminarJugadorPersona.setEnabled(false);

            this.txtNacionalidadJugador.setEnabled(true);
            this.txtPosicionJugador.setEnabled(true);
            this.txtNumeroGolesJugador.setEnabled(true);
            this.txtSalarioJugador.setEnabled(true);

            this.txtAnnosFidelidadAficionado.setEnabled(false);
            this.CheckBoxAbonadoAficionado.setEnabled(false);

            this.txtAnnosExperienciaTecnico.setEnabled(false);
            this.txtSalarioTecnico.setEnabled(false);

            this.CheckBoxFifaArbitro.setEnabled(false);

            limpiarCajasPersonaHerencia();

        } else if (comboTipoPersona.getSelectedIndex() == 2) {

            this.txtAnnosAfiliacionManager.setEnabled(false);
            this.comboJugadorPersona.setEnabled(false);
            this.btnAgregarJugadorPersona.setEnabled(false);
            this.btnEliminarJugadorPersona.setEnabled(false);

            this.txtNacionalidadJugador.setEnabled(false);
            this.txtPosicionJugador.setEnabled(false);
            this.txtNumeroGolesJugador.setEnabled(false);
            this.txtSalarioJugador.setEnabled(false);

            this.txtAnnosFidelidadAficionado.setEnabled(true);
            this.CheckBoxAbonadoAficionado.setEnabled(true);

            this.txtAnnosExperienciaTecnico.setEnabled(false);
            this.txtSalarioTecnico.setEnabled(false);

            this.CheckBoxFifaArbitro.setEnabled(false);

            limpiarCajasPersonaHerencia();

        } else if (comboTipoPersona.getSelectedIndex() == 3) {

            this.txtAnnosAfiliacionManager.setEnabled(false);
            this.comboJugadorPersona.setEnabled(false);
            this.btnAgregarJugadorPersona.setEnabled(false);
            this.btnEliminarJugadorPersona.setEnabled(false);

            this.txtNacionalidadJugador.setEnabled(false);
            this.txtPosicionJugador.setEnabled(false);
            this.txtNumeroGolesJugador.setEnabled(false);
            this.txtSalarioJugador.setEnabled(false);

            this.txtAnnosFidelidadAficionado.setEnabled(false);
            this.CheckBoxAbonadoAficionado.setEnabled(false);

            this.txtAnnosExperienciaTecnico.setEnabled(true);
            this.txtSalarioTecnico.setEnabled(true);

            limpiarCajasPersonaHerencia();

            this.CheckBoxFifaArbitro.setEnabled(false);
        } else if (comboTipoPersona.getSelectedIndex() == 4) {

            this.txtAnnosAfiliacionManager.setEnabled(false);
            this.comboJugadorPersona.setEnabled(false);
            this.btnAgregarJugadorPersona.setEnabled(false);
            this.btnEliminarJugadorPersona.setEnabled(false);

            this.txtNacionalidadJugador.setEnabled(false);
            this.txtPosicionJugador.setEnabled(false);
            this.txtNumeroGolesJugador.setEnabled(false);
            this.txtSalarioJugador.setEnabled(false);

            this.txtAnnosFidelidadAficionado.setEnabled(false);
            this.CheckBoxAbonadoAficionado.setEnabled(false);

            this.txtAnnosExperienciaTecnico.setEnabled(false);
            this.txtSalarioTecnico.setEnabled(false);

            this.CheckBoxFifaArbitro.setEnabled(true);
        }
    }//GEN-LAST:event_comboTipoPersonaItemStateChanged

    private void btnLimpiarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarPersonaActionPerformed

        limpiarCajasPersona();
    }//GEN-LAST:event_btnLimpiarPersonaActionPerformed

    private void btnLimpiarPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarPartidoActionPerformed

        limpiarCajasPartido();
    }//GEN-LAST:event_btnLimpiarPartidoActionPerformed

    private void btnLimpiarJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarJornadaActionPerformed

        limpiarCajasJornada();
    }//GEN-LAST:event_btnLimpiarJornadaActionPerformed

    private void btnLimpiarEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarEstadioActionPerformed

        limpiarCajasEstadio();
    }//GEN-LAST:event_btnLimpiarEstadioActionPerformed

    private void btnLimpiarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarEquipoActionPerformed

        limpiarCajasEquipo();
    }//GEN-LAST:event_btnLimpiarEquipoActionPerformed

    private void btnLimpiarCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCompetenciaActionPerformed

        LimpiarCajasCompetencia();
    }//GEN-LAST:event_btnLimpiarCompetenciaActionPerformed

    private void btnAgregarManagersCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarManagersCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasManagers.getSelectedItem();
            String partes[] = id.split(" - ");
            String idManager = partes[0];
            System.out.println(idManager);
            this.miControladorPersona.setCompetenciaManager(idManager, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarManagersCompetenciaActionPerformed

    private void btnAgregarEquiposCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEquiposCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboEquiposCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idEquipo = partes[0];
            System.out.println(idEquipo);
            this.miControladorEquipo.setCompetencia(idEquipo, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }

    }//GEN-LAST:event_btnAgregarEquiposCompetenciaActionPerformed

    private void btnAgregarJornadasCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarJornadasCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboJornadasCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJornada = partes[0];
            System.out.println(idJornada);
            this.miControladorJornada.setCompetencia(idJornada, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarJornadasCompetenciaActionPerformed

    private void btnAgregarEstadiosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEstadiosCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboEstadiosCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idEstadio = partes[0];
            System.out.println(idEstadio);
            this.miControladorEstadio.setCompetencia(idEstadio, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarEstadiosCompetenciaActionPerformed

    private void btnAgregarJugadoresCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarJugadoresCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasJugadores.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setCompetenciaJugador(idJugador, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarJugadoresCompetenciaActionPerformed

    private void btnAgregarAficionadosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAficionadosCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasAficionados.getSelectedItem();
            String partes[] = id.split(" - ");
            String idAficionado = partes[0];
            System.out.println(idAficionado);
            this.miControladorPersona.setCompetenciaAficionado(idAficionado, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarAficionadosCompetenciaActionPerformed

    private void btnAgregarTecnicosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTecnicosCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasTecnicos.getSelectedItem();
            String partes[] = id.split(" - ");
            String idTecnico = partes[0];
            System.out.println(idTecnico);
            this.miControladorPersona.setCompetenciaTecnico(idTecnico, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarTecnicosCompetenciaActionPerformed

    private void btnAgregarArbitrosCompetencia4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarArbitrosCompetencia4ActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasArbitros.getSelectedItem();
            String partes[] = id.split(" - ");
            String idArbitro = partes[0];
            System.out.println(idArbitro);
            this.miControladorPersona.setCompetenciaArbitro(idArbitro, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarArbitrosCompetencia4ActionPerformed

    private void comboPersonasManagersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPersonasManagersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPersonasManagersActionPerformed

    private void comboManagersEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboManagersEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboManagersEquipoActionPerformed

    private void btnAgregarJugadoresEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarJugadoresEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboJugadoresEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setEquipoJugador(idJugador, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarJugadoresEquipoActionPerformed

    private void btnAgregarManagersEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarManagersEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboManagersEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idManager = partes[0];
            System.out.println(idManager);
            this.miControladorPersona.setEliminarEquipoManager(idManager, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }

    }//GEN-LAST:event_btnAgregarManagersEquipoActionPerformed

    private void btnAgregarAficionadosEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAficionadosEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboAficionadosEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idAficionado = partes[0];
            System.out.println(idAficionado);
            this.miControladorPersona.setEquipoAficionado(idAficionado, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarAficionadosEquipoActionPerformed

    private void btnAgregarTecnicosEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTecnicosEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboTecnicosEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idTecnico = partes[0];
            System.out.println(idTecnico);
            this.miControladorPersona.setEquipoTecnico(idTecnico, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarTecnicosEquipoActionPerformed

    private void comboPartidosEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPartidosEstadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPartidosEstadioActionPerformed

    private void btnAgregarPartidosEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPartidosEstadioActionPerformed

        if (this.txtIdEstadio.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idEstadio = this.txtIdEstadio.getText();
            String id = (String) this.comboPartidosEstadio.getSelectedItem();
            String partes[] = id.split(" - ");
            String idPartido = partes[0];
            System.out.println(idPartido);
            this.miControladorPartido.setEstadio(idPartido, idEstadio);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarPartidosEstadioActionPerformed

    private void comboArbitroPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboArbitroPartidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboArbitroPartidoActionPerformed

    private void btnAgregarArbitroPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarArbitroPartidoActionPerformed
        if (this.txtIdPartido.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idPartido = this.txtIdPartido.getText();
            String id = (String) this.comboArbitroPartido.getSelectedItem();
            String partes[] = id.split(" - ");
            String idArbitro = partes[0];
            System.out.println(idArbitro);
            this.miControladorPersona.setPartidoArbitro(idArbitro, idPartido);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarArbitroPartidoActionPerformed

    private void comboPartidosJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPartidosJornadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPartidosJornadaActionPerformed

    private void btnAgregarPartidosJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPartidosJornadaActionPerformed

        if (this.txtIdJornada.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idJornada = this.txtIdJornada.getText();
            String id = (String) this.comboPartidosJornada.getSelectedItem();
            String partes[] = id.split(" - ");
            String idPartido = partes[0];
            System.out.println(idPartido);
            this.miControladorPartido.setJornada(idPartido, idJornada);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }

    }//GEN-LAST:event_btnAgregarPartidosJornadaActionPerformed

    private void btnEliminarPartidosJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPartidosJornadaActionPerformed
        if (this.txtIdJornada.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idJornada = this.txtIdJornada.getText();
            String id = (String) this.comboPartidosJornada.getSelectedItem();
            String partes[] = id.split(" - ");
            String idPartido = partes[0];
            System.out.println(idPartido);
            this.miControladorPartido.setEliminarJornada(idPartido, idJornada);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }

     }//GEN-LAST:event_btnEliminarPartidosJornadaActionPerformed

    private void btnEliminarManagersCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarManagersCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasManagers.getSelectedItem();
            String partes[] = id.split(" - ");
            String idManager = partes[0];
            System.out.println(idManager);
            this.miControladorPersona.setEliminarCompetenciaManager(idManager, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnEliminarManagersCompetenciaActionPerformed

    private void btnEliminarJugadoresCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarJugadoresCompetenciaActionPerformed
        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasJugadores.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setEliminarCompetenciaJugador(idJugador, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarJugadoresCompetenciaActionPerformed

    private void btnEliminarAficionadosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAficionadosCompetenciaActionPerformed
        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasAficionados.getSelectedItem();
            String partes[] = id.split(" - ");
            String idAficionado = partes[0];
            System.out.println(idAficionado);
            this.miControladorPersona.setEliminarCompetenciaAficionado(idAficionado, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarAficionadosCompetenciaActionPerformed

    private void btnEliminarTecnicosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTecnicosCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasTecnicos.getSelectedItem();
            String partes[] = id.split(" - ");
            String idTecnico = partes[0];
            System.out.println(idTecnico);
            this.miControladorPersona.setEliminarCompetenciaTecnico(idTecnico, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarTecnicosCompetenciaActionPerformed

    private void btnEliminarArbitrosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArbitrosCompetenciaActionPerformed
        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboPersonasArbitros.getSelectedItem();
            String partes[] = id.split(" - ");
            String idArbitro = partes[0];
            System.out.println(idArbitro);
            this.miControladorPersona.setEliminarCompetenciaArbitro(idArbitro, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarArbitrosCompetenciaActionPerformed

    private void btnEliminarEquiposCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEquiposCompetenciaActionPerformed
        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboEquiposCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idEquipo = partes[0];
            System.out.println(idEquipo);
            this.miControladorEquipo.setEliminarCompetencia(idEquipo, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarEquiposCompetenciaActionPerformed

    private void btnEliminarJornadasCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarJornadasCompetenciaActionPerformed
        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboJornadasCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJornada = partes[0];
            System.out.println(idJornada);
            this.miControladorJornada.setEliminarCompetencia(idJornada, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarJornadasCompetenciaActionPerformed

    private void btnEliminarEstadiosCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEstadiosCompetenciaActionPerformed

        if (this.txtIdCompetencia.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idCompetencia = this.txtIdCompetencia.getText();
            String id = (String) this.comboEstadiosCompetencia.getSelectedItem();
            String partes[] = id.split(" - ");
            String idEstadio = partes[0];
            System.out.println(idEstadio);
            this.miControladorEstadio.setEliminarCompetencia(idEstadio, idCompetencia);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarEstadiosCompetenciaActionPerformed

    private void txtInformesPartidosGoleadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInformesPartidosGoleadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInformesPartidosGoleadaActionPerformed

    private void btnEliminarManagersEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarManagersEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboManagersEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idManager = partes[0];
            System.out.println(idManager);
            this.miControladorPersona.setEquipoManager(idManager, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarManagersEquipoActionPerformed

    private void btnEliminarJugadoresEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarJugadoresEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboJugadoresEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setEliminarEquipoJugador(idJugador, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarJugadoresEquipoActionPerformed

    private void btnEliminarAficionadosEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAficionadosEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboAficionadosEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idAficionado = partes[0];
            System.out.println(idAficionado);
            this.miControladorPersona.setEliminarEquipoAficionado(idAficionado, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarAficionadosEquipoActionPerformed

    private void btnEliminarTecnicosEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTecnicosEquipoActionPerformed

        if (this.txtIdEquipo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Equipo");

        } else {
            String idEquipo = this.txtIdEquipo.getText();
            String id = (String) this.comboTecnicosEquipo.getSelectedItem();
            String partes[] = id.split(" - ");
            String idTecnico = partes[0];
            System.out.println(idTecnico);
            this.miControladorPersona.setEliminarEquipoTecnico(idTecnico, idEquipo);

            JOptionPane.showMessageDialog(this, "La relación se elimino Correctamente");
        }
    }//GEN-LAST:event_btnEliminarTecnicosEquipoActionPerformed

    private void comboJugadorPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJugadorPersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboJugadorPersonaActionPerformed

    private void btnAgregarJugadorPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarJugadorPersonaActionPerformed
        if (this.txtCedulaPersona.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Manager");

        } else {
            String idManager = this.txtCedulaPersona.getText();
            String id = (String) this.comboJugadorPersona.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setPersonaJugador(idJugador, idManager);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnAgregarJugadorPersonaActionPerformed

    private void btnEliminarJugadorPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarJugadorPersonaActionPerformed
        if (this.txtCedulaPersona.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque un Manager");

        } else {
            String idManager = this.txtCedulaPersona.getText();
            String id = (String) this.comboJugadorPersona.getSelectedItem();
            String partes[] = id.split(" - ");
            String idJugador = partes[0];
            System.out.println(idJugador);
            this.miControladorPersona.setEliminarPersonaJugador(idJugador, idManager);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnEliminarJugadorPersonaActionPerformed

    private void btnGenerarInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarInformesActionPerformed

        Jugador jugadorMasJoven = this.miControladorPersona.jugadorMasJoven();
        txtInformesJugadormasJoven1.setText("El jugador más joven de la liga es : " + jugadorMasJoven.getNombre());

        int partidosPorGoleada = this.miControladorEquipo.cantidadDePartidosGoleada();
        txtInformesPartidosGoleada.setText("Partidos por goleada son: " + partidosPorGoleada);

        Jugador jugadorMasGolesLiga = this.miControladorPersona.jugadorMasgolesenLiga();
        txtJugadorMasGolesLiga.setText("Jugador que ha marcado más goles en la liga: " + jugadorMasGolesLiga.getNombre());

        Jugador arqueroMenosGoles = this.miControladorEquipo.arqueroMenosGolesEnContra();
        if (arqueroMenosGoles != null) {
            txtArqueroMenosGoles.setText("Arquero que le han marcado menos goles: " + arqueroMenosGoles.getNombre());
        } else {
            txtArqueroMenosGoles.setText("No existe Arquero con menos goles marcados");
        }

        Equipo equipoMayorAficionados = this.miControladorEquipo.equipoMayorCantidadAficionados();
        txtEquipoMayorAficionados.setText("Equipo con mayor cantidad de aficionados: " + equipoMayorAficionados.getNombre());

        String IdPartidoMasGoles = this.miControladorJornada.identificadorPartidoMasGoles();
        txtIdPartidoMasGoles.setText("Identificador del partido el cual tuvo más goles: " + IdPartidoMasGoles);

        String nombreEstadioMasGoles = this.miControladorEstadio.nombreEstadioMasGoles();
        txtNombreEstadioMasGoles.setText("Nombre del estadio donde se marcaron más goles en toda la liga: " + nombreEstadioMasGoles);

        actualizarTablaPromedioEdadEquipoJugadores();

        tablaDePosiciones();

        int CantidadGolesEquipoMasPuntos = this.miControladorEquipo.cantidadGolesMarcoEquipoMasPuntos();
        txtCantidadGolesEquipoMasPuntos.setText("La cantidad de goles que marco el equipo con mas puntos:"+CantidadGolesEquipoMasPuntos);
        
        Equipo nominaJuadorTecnicoMasAlta = this.miControladorEquipo.nominaJuadorTecnicoMasAlta();
        txtNominaJuadorTecnicoMasAlta.setText("Equipo con nómina de jugadores y técnico más alta:" + nominaJuadorTecnicoMasAlta.getNombre());

        
    }//GEN-LAST:event_btnGenerarInformesActionPerformed

    private void btnSalirJornada3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirJornada3ActionPerformed
        eventoCierre();
        this.dispose();
    }//GEN-LAST:event_btnSalirJornada3ActionPerformed

    private void btnEliminarArbitroPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArbitroPartidoActionPerformed
        if (this.txtIdPartido.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idPartido = this.txtIdPartido.getText();
            String id = (String) this.comboArbitroPartido.getSelectedItem();
            String partes[] = id.split(" - ");
            String idArbitro = partes[0];
            System.out.println(idArbitro);
            this.miControladorPersona.setEliminarPartidoArbitro(idArbitro, idPartido);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnEliminarArbitroPartidoActionPerformed

    private void btnEliminarPartidosEstadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPartidosEstadioActionPerformed
        if (this.txtIdEstadio.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor busque una Competencia");

        } else {
            String idEstadio = this.txtIdEstadio.getText();
            String id = (String) this.comboPartidosEstadio.getSelectedItem();
            String partes[] = id.split(" - ");
            String idPartido = partes[0];
            System.out.println(idPartido);
            this.miControladorPartido.setEliminarEstadio(idPartido, idEstadio);

            JOptionPane.showMessageDialog(this, "La relación se genero Correctamente");
        }
    }//GEN-LAST:event_btnEliminarPartidosEstadioActionPerformed

    private void txtInformesJugadormasJoven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInformesJugadormasJoven1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInformesJugadormasJoven1ActionPerformed

    private void txtJugadorMasGolesLigaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJugadorMasGolesLigaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJugadorMasGolesLigaActionPerformed

    private void txtArqueroMenosGolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArqueroMenosGolesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArqueroMenosGolesActionPerformed

    private void txtNominaJuadorTecnicoMasAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNominaJuadorTecnicoMasAltaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNominaJuadorTecnicoMasAltaActionPerformed

    private void txtEquipoMayorAficionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEquipoMayorAficionadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEquipoMayorAficionadosActionPerformed

    private void txtIdPartidoMasGolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPartidoMasGolesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPartidoMasGolesActionPerformed

    private void txtNombreEstadioMasGolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEstadioMasGolesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreEstadioMasGolesActionPerformed

    private void txtCantidadGolesEquipoMasPuntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadGolesEquipoMasPuntosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadGolesEquipoMasPuntosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxAbonadoAficionado;
    private javax.swing.JCheckBox CheckBoxFifaArbitro;
    private javax.swing.JTable TablaArbitro;
    private javax.swing.JButton btnActualizarCompetencia;
    private javax.swing.JButton btnActualizarEquipo;
    private javax.swing.JButton btnActualizarEstadio;
    private javax.swing.JButton btnActualizarJornada;
    private javax.swing.JButton btnActualizarPartido;
    private javax.swing.JButton btnActualizarPersona;
    private javax.swing.JButton btnAgregarAficionadosCompetencia;
    private javax.swing.JButton btnAgregarAficionadosEquipo;
    private javax.swing.JButton btnAgregarArbitroPartido;
    private javax.swing.JButton btnAgregarArbitrosCompetencia4;
    private javax.swing.JButton btnAgregarCompetencia;
    private javax.swing.JButton btnAgregarEquipo;
    private javax.swing.JButton btnAgregarEquiposCompetencia;
    private javax.swing.JButton btnAgregarEstadio;
    private javax.swing.JButton btnAgregarEstadiosCompetencia;
    private javax.swing.JButton btnAgregarJornada;
    private javax.swing.JButton btnAgregarJornadasCompetencia;
    private javax.swing.JButton btnAgregarJugadorPersona;
    private javax.swing.JButton btnAgregarJugadoresCompetencia;
    private javax.swing.JButton btnAgregarJugadoresEquipo;
    private javax.swing.JButton btnAgregarManagersCompetencia;
    private javax.swing.JButton btnAgregarManagersEquipo;
    private javax.swing.JButton btnAgregarPartido;
    private javax.swing.JButton btnAgregarPartidosEstadio;
    private javax.swing.JButton btnAgregarPartidosJornada;
    private javax.swing.JButton btnAgregarPersona;
    private javax.swing.JButton btnAgregarTecnicosCompetencia;
    private javax.swing.JButton btnAgregarTecnicosEquipo;
    private javax.swing.JButton btnBuscarCompetencia;
    private javax.swing.JButton btnBuscarEquipo;
    private javax.swing.JButton btnBuscarEstadio;
    private javax.swing.JButton btnBuscarJornada;
    private javax.swing.JButton btnBuscarPartido;
    private javax.swing.JButton btnBuscarPersona;
    private javax.swing.JButton btnEliminarAficionadosCompetencia;
    private javax.swing.JButton btnEliminarAficionadosEquipo;
    private javax.swing.JButton btnEliminarArbitroPartido;
    private javax.swing.JButton btnEliminarArbitrosCompetencia;
    private javax.swing.JButton btnEliminarCompetencia;
    private javax.swing.JButton btnEliminarEquipo;
    private javax.swing.JButton btnEliminarEquiposCompetencia;
    private javax.swing.JButton btnEliminarEstadio;
    private javax.swing.JButton btnEliminarEstadiosCompetencia;
    private javax.swing.JButton btnEliminarJornada;
    private javax.swing.JButton btnEliminarJornadasCompetencia;
    private javax.swing.JButton btnEliminarJugadorPersona;
    private javax.swing.JButton btnEliminarJugadoresCompetencia;
    private javax.swing.JButton btnEliminarJugadoresEquipo;
    private javax.swing.JButton btnEliminarManagersCompetencia;
    private javax.swing.JButton btnEliminarManagersEquipo;
    private javax.swing.JButton btnEliminarPartido;
    private javax.swing.JButton btnEliminarPartidosEstadio;
    private javax.swing.JButton btnEliminarPartidosJornada;
    private javax.swing.JButton btnEliminarPersona;
    private javax.swing.JButton btnEliminarTecnicosCompetencia;
    private javax.swing.JButton btnEliminarTecnicosEquipo;
    private javax.swing.JButton btnGenerarInformes;
    private javax.swing.JButton btnLimpiarCompetencia;
    private javax.swing.JButton btnLimpiarEquipo;
    private javax.swing.JButton btnLimpiarEstadio;
    private javax.swing.JButton btnLimpiarJornada;
    private javax.swing.JButton btnLimpiarPartido;
    private javax.swing.JButton btnLimpiarPersona;
    private javax.swing.JButton btnSalirComptencia;
    private javax.swing.JButton btnSalirEquipo;
    private javax.swing.JButton btnSalirEstadio;
    private javax.swing.JButton btnSalirJornada;
    private javax.swing.JButton btnSalirJornada2;
    private javax.swing.JButton btnSalirJornada3;
    private javax.swing.JButton btnSalirPartido;
    private javax.swing.JComboBox<String> comboAficionadosEquipo;
    private javax.swing.JComboBox<String> comboArbitroPartido;
    private javax.swing.JComboBox<String> comboEquipoLocalPartido;
    private javax.swing.JComboBox<String> comboEquipoVisitantePartido;
    private javax.swing.JComboBox<String> comboEquiposCompetencia;
    private javax.swing.JComboBox<String> comboEstadiosCompetencia;
    private javax.swing.JComboBox<String> comboJornadasCompetencia;
    private javax.swing.JComboBox<String> comboJugadorPersona;
    private javax.swing.JComboBox<String> comboJugadoresEquipo;
    private javax.swing.JComboBox<String> comboManagersEquipo;
    private javax.swing.JComboBox<String> comboPartidosEstadio;
    private javax.swing.JComboBox<String> comboPartidosJornada;
    private javax.swing.JComboBox<String> comboPersonasAficionados;
    private javax.swing.JComboBox<String> comboPersonasArbitros;
    private javax.swing.JComboBox<String> comboPersonasJugadores;
    private javax.swing.JComboBox<String> comboPersonasManagers;
    private javax.swing.JComboBox<String> comboPersonasTecnicos;
    private javax.swing.JComboBox<String> comboTecnicosEquipo;
    private javax.swing.JComboBox<String> comboTipoPersona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tablaAficionado;
    private javax.swing.JTable tablaCompetencia;
    private javax.swing.JTable tablaDePosicionesEquipos;
    private javax.swing.JTable tablaEquipo;
    private javax.swing.JTable tablaEstadio;
    private javax.swing.JTable tablaJornada;
    private javax.swing.JTable tablaJugador;
    private javax.swing.JTable tablaManager;
    private javax.swing.JTable tablaPartido;
    private javax.swing.JTable tablaPromedioEdadEquipos;
    private javax.swing.JTable tablaTecnico;
    private javax.swing.JTextField txtAnnofuncacionEquipo;
    private javax.swing.JTextField txtAnnosAfiliacionManager;
    private javax.swing.JTextField txtAnnosExperienciaTecnico;
    private javax.swing.JTextField txtAnnosFidelidadAficionado;
    private javax.swing.JTextField txtApellidoPersona;
    private javax.swing.JTextField txtArqueroMenosGoles;
    private javax.swing.JTextField txtCantidadGolesEquipoMasPuntos;
    private javax.swing.JTextField txtCapacidadEstadio;
    private javax.swing.JTextField txtCedulaPersona;
    private javax.swing.JTextField txtCiudadEstadio;
    private javax.swing.JTextField txtEdadPersona;
    private javax.swing.JTextField txtEquipoMayorAficionados;
    private javax.swing.JTextField txtEsloganJornada;
    private javax.swing.JTextField txtFechaPartido;
    private javax.swing.JTextField txtGolesLocalPartido;
    private javax.swing.JTextField txtGolesVisitantePartido;
    private javax.swing.JTextField txtGolescontraEquipo;
    private javax.swing.JTextField txtGolesfavorEquipo;
    private javax.swing.JTextField txtIdCompetencia;
    private javax.swing.JTextField txtIdEquipo;
    private javax.swing.JTextField txtIdEstadio;
    private javax.swing.JTextField txtIdJornada;
    private javax.swing.JTextField txtIdPartido;
    private javax.swing.JTextField txtIdPartidoMasGoles;
    private javax.swing.JTextField txtInformesJugadormasJoven1;
    private javax.swing.JTextField txtInformesPartidosGoleada;
    private javax.swing.JTextField txtJugadorMasGolesLiga;
    private javax.swing.JTextField txtNacionalidadJugador;
    private javax.swing.JTextField txtNombreCompetencia;
    private javax.swing.JTextField txtNombreEquipo;
    private javax.swing.JTextField txtNombreEstadio;
    private javax.swing.JTextField txtNombreEstadioMasGoles;
    private javax.swing.JTextField txtNombrePersona;
    private javax.swing.JTextField txtNominaJuadorTecnicoMasAlta;
    private javax.swing.JTextField txtNumeroGolesJugador;
    private javax.swing.JTextField txtNumeroJornada;
    private javax.swing.JTextField txtPartidosjugadosEquipo;
    private javax.swing.JTextField txtPosicionJugador;
    private javax.swing.JTextField txtPuntosEquipo;
    private javax.swing.JTextField txtPuntosGanadorPartido;
    private javax.swing.JTextField txtSalarioJugador;
    private javax.swing.JTextField txtSalarioTecnico;
    private javax.swing.JTextField txtTitulosinternacionalesEquipo;
    private javax.swing.JTextField txtTitulosnacionalesEquipo;
    // End of variables declaration//GEN-END:variables

    class FondoPanel extends JPanel{
        
        private Image imagen;
        
        @Override
        public void paint(Graphics g){
            imagen = new ImageIcon(getClass().getResource("/Imagenes/Fondo.jpg")).getImage();
            
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            
            setOpaque(false);
            
            super.paint(g);
        }
    }
    
}
