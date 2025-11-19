package interfaz.areas;

import logica.Sistema;
import dominio.Area;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class VentanaAreasAlta extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaAreas;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JLabel labelDescripcion;
    private JLabel labelPresupuesto;
    
    private JTextField textoNombre;
    private JTextField textoDescripcion;
    private JTextField textoPresupuesto;
    
    private JButton botonGuardar;
    private JButton botonCancelar;
    
    public VentanaAreasAlta(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        actualizarLista();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Alta de área");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Declaracion de elementos
        // Parte izquierda (Mostrar todas las areas por nombre en orden creciente)
        modeloLista = new DefaultListModel<>();
        listaAreas = new JList<>(modeloLista);
        listaAreas.setBorder(BorderFactory.createTitledBorder("Áreas existentes"));
        listaAreas.setFont(new Font("", Font.BOLD, 16));
        JScrollPane scrollLista = new JScrollPane(listaAreas);
        
        // Parte derecha (Formulario para crear area) 
        labelTitulo = new JLabel("ALTA DE ÁREA");
        labelTitulo.setFont(new Font("", Font.BOLD, 16));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelNombre = new JLabel("Nombre del Área:");
        labelDescripcion = new JLabel("Descripción:");
        labelPresupuesto = new JLabel("<html>Presupuesto anual:<br>(en USD)</html>"); // JLabel con HTML para poder mostrar texto en otra linea
        
        textoNombre = new JTextField(20);
        textoDescripcion = new JTextField(40);
        textoPresupuesto = new JTextField(20);
        
        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");
        
        // Distribucion en Layout
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10)); // Grid Layout params: filas, col, distancia en px.
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelFormulario.add(labelNombre);
        panelFormulario.add(textoNombre);
        panelFormulario.add(labelDescripcion);
        panelFormulario.add(textoDescripcion);
        panelFormulario.add(labelPresupuesto);
        panelFormulario.add(textoPresupuesto);
        
        // Crear panel de los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        
        // Distribuir elementos en el Panel Derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        
        // Contenedor de la lista y el form para aplicar margenes
        JPanel panelContenedor = new JPanel(new BorderLayout(10,10));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25)); // Border para agregar margen
        panelContenedor.add(scrollLista, BorderLayout.WEST);
        panelContenedor.add(panelDerecho, BorderLayout.CENTER);
        
        // Agregar lista y Panel Derecho al Layout Principal
        add(panelContenedor);
    }
    
    // Botones para Guardar y Cancelar
    public void agregarEventos() {
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArea();
            }
        });
        
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    public void guardarArea() {
        String nombre = textoNombre.getText().trim();
        String descripcion = textoDescripcion.getText().trim();
        String presupuestoStr = textoPresupuesto.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del área no puede estar vacío.");
            return;
        }
        
        double presupuesto;
        try {
            presupuesto = Double.parseDouble(presupuestoStr);
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Presupuesto inválido, debe ser un número.");
            return;
        }
        
        // Crear area nueva y guardarla en el Sistema si el nombre no existe.
        Area nueva = new Area(nombre, descripcion, presupuesto);
        boolean guardada = sistema.agregarArea(nueva);
        if (!guardada) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un área con ese nombre.\nIngrese un nombre distinto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        JOptionPane.showMessageDialog(this, "Área registrada correctamente.");
        actualizarLista();
        dispose();
    }
    
    public void actualizarLista() {
        modeloLista.clear();
        ArrayList<Area> ordenadas = new ArrayList<>(sistema.getAreasOrdenadas());
        for (Area a : ordenadas) {
            modeloLista.addElement(a.getNombre());
        }
    }
}
