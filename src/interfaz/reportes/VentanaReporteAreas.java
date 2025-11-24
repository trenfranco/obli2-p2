package interfaz.reportes;

import dominio.*;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaReporteAreas extends JFrame {

    private Sistema sistema;

    private JList<Area> listaAreas;
    private DefaultListModel<Area> modeloAreas;
    private JPanel panelEmpleados;
    private JLabel labelTitulo;
    private JLabel labelSubtitulo;

    public VentanaReporteAreas(Sistema sistema) {
        this.sistema = sistema;

        configurarVentana();
        inicializarComponentes();
        cargarAreas();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("MARTRE - Reporte de Áreas");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        modeloAreas = new DefaultListModel<>();
        listaAreas = new JList<>(modeloAreas);
        listaAreas.setCellRenderer(new RenderizarListaConColor(sistema));
        JScrollPane scrollAreas = new JScrollPane(listaAreas);

        labelTitulo = new JLabel("Seleccione un área", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 20));

        labelSubtitulo = new JLabel(" ", SwingConstants.CENTER);

        panelEmpleados = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollEmpleados = new JScrollPane(panelEmpleados);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        panelDerecho.add(scrollEmpleados, BorderLayout.CENTER);
        panelDerecho.add(labelSubtitulo, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.add(scrollAreas, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void cargarAreas() {
        modeloAreas.clear();

        ArrayList<Area> ordenadas = sistema.getAreasOrdenadasPorPorcentajeAsignado();
        for (Area a : ordenadas) {
            modeloAreas.addElement(a);
        }
    }

    private void agregarEventos() {
        listaAreas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Area seleccionada = listaAreas.getSelectedValue();
                if (seleccionada != null) mostrarArea(seleccionada);
            }
        });
    }

    private void mostrarArea(Area area) {
        double porcentaje = sistema.calcularPorcentajeArea(area);

        labelTitulo.setText("Área: " + area.getNombre() + " – " + String.format("%.1f", porcentaje) + "%");
        labelSubtitulo.setText("Presupuesto asignado: " + String.format("%.1f", porcentaje) + "%");
        panelEmpleados.removeAll();

        ArrayList<Empleado> empleados = sistema.getEmpleadosOrdenados(area);

        if (empleados.isEmpty()) {

            JPanel contenedor = new JPanel(new BorderLayout());
            JLabel mensaje = new JLabel("No hay empleados en esta área");
            mensaje.setHorizontalAlignment(SwingConstants.LEFT);
            mensaje.setVerticalAlignment(SwingConstants.TOP);
            
            // Settear margen
            mensaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            contenedor.add(mensaje, BorderLayout.NORTH);
            panelEmpleados.add(contenedor);
        } else {
            for (Empleado emp : empleados) {

                JButton nuevo = new JButton(emp.getNombre());
                nuevo.setMargin(new Insets(-5, -5, -5, -5));

                nuevo.setBackground(sistema.getColorParaEmpleado(emp, area));
                nuevo.setForeground(Color.WHITE);

                nuevo.addActionListener(new VentanaMostrarInfoEmpleado(emp, VentanaReporteAreas.this));

                panelEmpleados.add(nuevo);
            }
        }
        panelEmpleados.revalidate();
        panelEmpleados.repaint();
    }
}