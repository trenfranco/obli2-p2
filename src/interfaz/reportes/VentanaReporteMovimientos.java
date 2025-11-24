package interfaz.reportes;

import dominio.*;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class VentanaReporteMovimientos extends JFrame {
    private Sistema sistema;
    private JTable tabla;
    private DefaultComboBoxModel<Integer> modeloMes;
    private DefaultComboBoxModel<Area> modeloOrigen;
    private DefaultComboBoxModel<Area> modeloDestino;
    private DefaultComboBoxModel<Empleado> modeloEmpleados;
    private JButton botonFiltrar;
    private JButton botonCSV;

    public VentanaReporteMovimientos(Sistema sistema) {
        this.sistema = sistema;
        configurarVentana();
        inicializarComponentes();
        cargarFiltros();
        cargarTabla(sistema.getMovimientosOrdenados());
    }

    private void configurarVentana() {
        setTitle("Reporte de Movimientos");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelPrincipal);

        JPanel panelFiltros = new JPanel(new GridLayout(2, 4, 10, 10));

        modeloMes = new DefaultComboBoxModel<>();
        modeloOrigen = new DefaultComboBoxModel<>();
        modeloDestino = new DefaultComboBoxModel<>();
        modeloEmpleados = new DefaultComboBoxModel<>();

        JComboBox<Integer> comboMes = new JComboBox<>(modeloMes);
        JComboBox<Area> comboOrigen = new JComboBox<>(modeloOrigen);
        JComboBox<Area> comboDestino = new JComboBox<>(modeloDestino);
        JComboBox<Empleado> comboEmpleado = new JComboBox<>(modeloEmpleados);

        panelFiltros.add(new JLabel("Mes:"));
        panelFiltros.add(comboMes);
        panelFiltros.add(new JLabel("Área Origen:"));
        panelFiltros.add(comboOrigen);
        panelFiltros.add(new JLabel("Área Destino:"));
        panelFiltros.add(comboDestino);
        panelFiltros.add(new JLabel("Empleado:"));
        panelFiltros.add(comboEmpleado);

        tabla = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tabla);

        botonFiltrar = new JButton("Aplicar Filtros");
        botonCSV = new JButton("Exportar CSV");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonFiltrar);
        panelBotones.add(botonCSV);

        panelPrincipal.add(panelFiltros, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        botonFiltrar.addActionListener(e -> {
            Integer mes = (Integer) comboMes.getSelectedItem();
            Area origen = (Area) comboOrigen.getSelectedItem();
            Area destino = (Area) comboDestino.getSelectedItem();
            Empleado emp = (Empleado) comboEmpleado.getSelectedItem();
            ArrayList<Movimiento> filtrados = sistema.filtrarMovimientos(mes, origen, destino, emp);
            cargarTabla(filtrados);
        });

        botonCSV.addActionListener(e -> exportarCSV());
    }

    private void cargarFiltros() {
        modeloMes.addElement(null);
        for (int i = 1; i <= 12; i++) modeloMes.addElement(i);

        modeloOrigen.addElement(null);
        modeloDestino.addElement(null);
        for (Area a : sistema.getAreas()) {
            modeloOrigen.addElement(a);
            modeloDestino.addElement(a);
        }

        modeloEmpleados.addElement(null);
        for (Empleado e : sistema.getEmpleados()) {
            modeloEmpleados.addElement(e);
        }
    }

    private void cargarTabla(ArrayList<Movimiento> lista) {
        String[] columnas = {"Mes", "Origen", "Destino", "Empleado"};
        String[][] datos = new String[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Movimiento m = lista.get(i);
            datos[i][0] = "" + m.getMes();
            datos[i][1] = m.getAreaOrigen().getNombre();
            datos[i][2] = m.getAreaDestino().getNombre();
            datos[i][3] = m.getEmpleado().getNombre();
        }

        tabla.setModel(new DefaultTableModel(datos, columnas));
    }

    private void exportarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar CSV");

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            try {
                ArrayList<Movimiento> tablaActual = new ArrayList<>();

                for (int i = 0; i < tabla.getRowCount(); i++) {
                    int mes = Integer.parseInt(tabla.getValueAt(i, 0).toString());
                    Area origen = sistema.getAreaPorNombre(tabla.getValueAt(i, 1).toString());
                    Area destino = sistema.getAreaPorNombre(tabla.getValueAt(i, 2).toString());
                    Empleado emp = sistema.getEmpleadoPorNombre(tabla.getValueAt(i, 3).toString());
                    tablaActual.add(new Movimiento(mes, origen, destino, emp));
                }

                sistema.exportarMovimientosCSV(tablaActual, archivo);
                JOptionPane.showMessageDialog(this, "CSV exportado correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar CSV: " + ex.getMessage());
            }
        }
    }
}
