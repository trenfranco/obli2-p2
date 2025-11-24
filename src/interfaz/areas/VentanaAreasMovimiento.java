package interfaz.areas;

import dominio.Area;
import dominio.Empleado;
import dominio.Movimiento;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class VentanaAreasMovimiento extends JFrame {
    private Sistema sistema;
    
    private JComboBox<String> comboMes;
    private JComboBox<Area> comboAreaOrigen;
    private JComboBox<String> comboEmpleado;
    private JComboBox<Area> comboAreaDestino;
    
    private JButton botonEjecutar;
    private JButton botonCancelar;
    
    public VentanaAreasMovimiento(Sistema sistema) {
        this.sistema = sistema;
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        cargarCombosIniciales();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Movimiento de empleados");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Título
        JLabel labelTitulo = new JLabel("MOVIMIENTO DE ÁREA", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));

        // Labels
        JLabel lblMes = new JLabel("Mes:");
        JLabel lblOrigen = new JLabel("Área origen:");
        JLabel lblEmpleado = new JLabel("Empleado (origen):");
        JLabel lblDestino = new JLabel("Área destino:");

        // Combos
        comboMes = new JComboBox<>();
        comboAreaOrigen = new JComboBox<>();
        comboEmpleado = new JComboBox<>();
        comboAreaDestino = new JComboBox<>();

        // Formulario con GridLayout (4 filas x 2 columnas)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        panelFormulario.add(lblMes);
        panelFormulario.add(comboMes);

        panelFormulario.add(lblOrigen);
        panelFormulario.add(comboAreaOrigen);

        panelFormulario.add(lblEmpleado);
        panelFormulario.add(comboEmpleado);

        panelFormulario.add(lblDestino);
        panelFormulario.add(comboAreaDestino);

        // Botones
        botonEjecutar = new JButton("Ejecutar movimiento");
        botonCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelBotones.add(botonEjecutar);
        panelBotones.add(botonCancelar);

        // Panel derecho conteniendo formulario + botones
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        // Panel contenedor principal (no hay lista a la izquierda, se mantiene estilo simple)
        JPanel contenedor = new JPanel(new BorderLayout(12, 12));
        contenedor.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contenedor.add(panelDerecho, BorderLayout.CENTER);

        add(contenedor);
    }
    
    private void agregarEventos() {
        // Cuando cambia el area origen, recargar empleados y limpiar destino
        comboAreaOrigen.addActionListener(e -> {
            Area origen = (Area) comboAreaOrigen.getSelectedItem();
            cargarEmpleadosDeArea(origen);
            // limpiar destino hasta que se seleccione empleado
            comboAreaDestino.removeAllItems();
        });

        // Cuando se selecciona empleado, cargar áreas destino (todas menos origen)
        comboEmpleado.addActionListener(e -> {
            String seleccionado = (String) comboEmpleado.getSelectedItem();
            if (seleccionado == null) {
                comboAreaDestino.removeAllItems();
                return;
            }
            Area origen = (Area) comboAreaOrigen.getSelectedItem();
            cargarAreasDestino(origen);
        });

        botonCancelar.addActionListener(e -> dispose());

        botonEjecutar.addActionListener(e -> {
            if (comboMes.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Seleccioná un mes.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboAreaOrigen.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccioná un área de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboEmpleado.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccioná un empleado de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboAreaDestino.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccioná un área de destino.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // comboMes 0 -> Enero -> mes = 1
            int mes = comboMes.getSelectedIndex() + 1; 
            Area origen = (Area) comboAreaOrigen.getSelectedItem();
            Area destino = (Area) comboAreaDestino.getSelectedItem();

            String empleadoLine = (String) comboEmpleado.getSelectedItem(); // "Nombre - CI"
            String[] partes = empleadoLine.split(" - ");
            if (partes.length < 2) {
                JOptionPane.showMessageDialog(this, "Formato de empleado inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String cedula = partes[1];

            Empleado empleado = sistema.getEmpleadoPorCedula(cedula);
            if (empleado == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el empleado seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // No permitir mover a la misma área
            if (destino.getNombre().equalsIgnoreCase(origen.getNombre())) {
                JOptionPane.showMessageDialog(this, "El área destino no puede ser la misma que la de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Movimiento mov = new Movimiento(mes, origen, destino, empleado);

            boolean completado = sistema.ejecutarMovimiento(mov);
            if (completado) {
                sistema.agregarMovimiento(mov); // almacenar para reportes
                JOptionPane.showMessageDialog(this, "Movimiento ejecutado con éxito.", "Info", JOptionPane.INFORMATION_MESSAGE);
                cargarCombosIniciales();
            } else {
                JOptionPane.showMessageDialog(this, "No fue posible ejecutar el movimiento. Verifique presupuesto del área destino.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void cargarCombosIniciales() {
        cargarMeses();
        cargarAreasOrigen();
        comboEmpleado.removeAllItems();
        comboAreaDestino.removeAllItems();
    }
    
    private void cargarMeses() {
        comboMes.removeAllItems();
        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        for (String m : meses) {
            comboMes.addItem(m);
        }
        comboMes.setSelectedIndex(0);
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
        for (Empleado emp : integrantes) {
            // agregar como "Nombre - CI" para luego poder recuperar por cédula
            comboEmpleado.addItem(emp.getNombre() + " - " + emp.getCedula());
        }
        if (comboEmpleado.getItemCount() > 0) {
            comboEmpleado.setSelectedIndex(0);
        }
    }
    
    private void cargarAreasDestino(Area origen) {
        comboAreaDestino.removeAllItems();
        for (Area a : sistema.getAreas()) {
            if (!a.getNombre().equalsIgnoreCase(origen.getNombre())) {
                comboAreaDestino.addItem(a);
            }
        }
        if (comboAreaDestino.getItemCount() > 0) {
            comboAreaDestino.setSelectedIndex(0);
        }
    }
}
