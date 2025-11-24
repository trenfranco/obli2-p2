package interfaz.reportes;

import dominio.Area;
import dominio.Empleado;
import dominio.Reporte;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class ReporteInteligente extends JFrame {
    private Sistema sistema;

    private JComboBox<Area> comboAreaOrigen;
    private JComboBox<String> comboEmpleado;
    private JComboBox<Area> comboAreaDestino;

    private JTextArea textAreaReporte;
    private JScrollPane scrollReporte;

    private JButton botonGenerar;
    private JButton botonCerrar;

    public ReporteInteligente(Sistema sistema) {
        this.sistema = sistema;
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        cargarCombosIniciales();
    }

    private void configurarVentana() {
        setTitle("MARTRE - Reporte Inteligente");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel labelTitulo = new JLabel("REPORTE INTELIGENTE DE MOVIMIENTO", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Panel superior
        JPanel panelFormulario = crearPanelFormulario();

        // Panel cenytro
        JPanel panelReporte = crearPanelReporte();

        // Panel inferior
        JPanel panelBotones = crearPanelBotones();

        // Agregar componentes al panel principal
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        // panel central con reporte
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelFormulario, BorderLayout.NORTH);
        panelCentro.add(panelReporte, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelFormulario() {
        JLabel lblOrigen = new JLabel("Área origen:");
        JLabel lblEmpleado = new JLabel("Empleado:");
        JLabel lblDestino = new JLabel("Área destino:");

        comboAreaOrigen = new JComboBox<>();
        comboEmpleado = new JComboBox<>();
        comboAreaDestino = new JComboBox<>();

        //GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Selección de movimiento"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        panel.add(lblOrigen);
        panel.add(comboAreaOrigen);

        panel.add(lblEmpleado);
        panel.add(comboEmpleado);

        panel.add(lblDestino);
        panel.add(comboAreaDestino);

        return panel;
    }

    private JPanel crearPanelReporte() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Reporte generado"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        textAreaReporte = new JTextArea();
        textAreaReporte.setEditable(false);
        textAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textAreaReporte.setLineWrap(true);
        textAreaReporte.setWrapStyleWord(true);
        textAreaReporte.setText("Seleccione un área origen, empleado y área destino,\nluego presione 'Generar Reporte' para obtener el análisis.");

        scrollReporte = new JScrollPane(textAreaReporte);
        scrollReporte.setPreferredSize(new Dimension(850, 300));

        panel.add(scrollReporte, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        botonGenerar = new JButton("Generar Reporte");
        botonCerrar = new JButton("Cerrar");

        // Estilo para el btn principal
        botonGenerar.setFont(new Font("", Font.BOLD, 12));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.add(botonGenerar);
        panel.add(botonCerrar);

        return panel;
    }

    private void agregarEventos() {
        comboAreaOrigen.addActionListener(e -> {
            Area origen = (Area) comboAreaOrigen.getSelectedItem();
            cargarEmpleadosDeArea(origen);
            // Limpiar area destino
            comboAreaDestino.removeAllItems();
        });

        comboEmpleado.addActionListener(e -> {
            String seleccionado = (String) comboEmpleado.getSelectedItem();
            if (seleccionado == null) {
                comboAreaDestino.removeAllItems();
                return;
            }
            Area origen = (Area) comboAreaOrigen.getSelectedItem();
            cargarAreasDestino(origen);
        });

        botonCerrar.addActionListener(e -> dispose());

        botonGenerar.addActionListener(e -> generarReporte());
    }

    private void generarReporte() {
        if (comboAreaOrigen.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un área de origen.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (comboEmpleado.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un empleado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (comboAreaDestino.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un área de destino.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Area areaOrigen = (Area) comboAreaOrigen.getSelectedItem();
        Area areaDestino = (Area) comboAreaDestino.getSelectedItem();

        String empleadoLine = (String) comboEmpleado.getSelectedItem();
        String[] partes = empleadoLine.split(" - ");
        if (partes.length < 2) {
            JOptionPane.showMessageDialog(this,
                "Formato de empleado inválido.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        String cedula = partes[1];

        Empleado empleado = sistema.getEmpleadoPorCedula(cedula);
        if (empleado == null) {
            JOptionPane.showMessageDialog(this,
                "No se encontró el empleado seleccionado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (areaDestino.getNombre().equalsIgnoreCase(areaOrigen.getNombre())) {
            JOptionPane.showMessageDialog(this,
                "El área destino debe ser diferente del área origen.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        textAreaReporte.setText("Generando reporte...\n\nPor favor espere...");
        textAreaReporte.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        botonGenerar.setEnabled(false);

        try {
            Reporte reporte = sistema.generarReporteInteligente(areaOrigen, areaDestino, empleado);
            String textoReporte = reporte.getTextoReporte();

            // Mostrar el reporte
            textAreaReporte.setText(textoReporte);
            textAreaReporte.setCaretPosition(0); // Scroll al inicio

        } catch (Exception ex) {
            textAreaReporte.setText("Error al generar el reporte:\n\n" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            textAreaReporte.setCursor(Cursor.getDefaultCursor());
            botonGenerar.setEnabled(true);
        }
    }

    private void cargarCombosIniciales() {
        cargarAreasOrigen();
        comboEmpleado.removeAllItems();
        comboAreaDestino.removeAllItems();
    }

    private void cargarAreasOrigen() {
        comboAreaOrigen.removeAllItems();
        ArrayList<Area> areas = sistema.getAreas();
        for (Area a : areas) {
            comboAreaOrigen.addItem(a);
        }
        if (comboAreaOrigen.getItemCount() > 0) {
            comboAreaOrigen.setSelectedIndex(0);
        }
    }

    private void cargarEmpleadosDeArea(Area area) {
        comboEmpleado.removeAllItems();
        if (area == null) {
            return;
        }
        ArrayList<Empleado> integrantes = area.getIntegrantes();
        if (integrantes.isEmpty()) {
            return;
        }
        for (Empleado emp : integrantes) {
            comboEmpleado.addItem(emp.getNombre() + " - " + emp.getCedula());
        }
        if (comboEmpleado.getItemCount() > 0) {
            comboEmpleado.setSelectedIndex(0);
        }
    }

    private void cargarAreasDestino(Area origen) {
        comboAreaDestino.removeAllItems();
        if (origen == null) {
            return;
        }
        for (Area a : sistema.getAreas()) {
            // Excluir el origen
            if (!a.getNombre().equalsIgnoreCase(origen.getNombre())) {
                comboAreaDestino.addItem(a);
            }
        }
        if (comboAreaDestino.getItemCount() > 0) {
            comboAreaDestino.setSelectedIndex(0);
        }
    }
}
