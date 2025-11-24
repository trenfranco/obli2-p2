package interfaz.managers;

import logica.Sistema;
import dominio.Manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class VentanaManagersAlta extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaManagers;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JLabel labelCedula;
    private JLabel labelAntiguedad;
    private JLabel labelCelular;
    
    private JTextField textoNombre;
    private JTextField textoCedula;
    private JTextField textoAntiguedad;
    private JTextField textoCelular;
    
    private JButton botonGuardar;
    private JButton botonCancelar;
    
    public VentanaManagersAlta(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        actualizarLista();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Alta de manager");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Declaracion de elementos
        // Parte izquierda (Mostrar todas los managers ordenados por años de antiguedad decreciente)
        modeloLista = new DefaultListModel<>();
        listaManagers = new JList<>(modeloLista);
        listaManagers.setBorder(BorderFactory.createTitledBorder("Managers existentes"));
        listaManagers.setFont(new Font("", Font.BOLD, 14));
        JScrollPane scrollLista = new JScrollPane(listaManagers);
        
        // Parte derecha (Formulario para crear Manager) 
        labelTitulo = new JLabel("ALTA DE MANAGER", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));
        
        labelNombre = new JLabel("Nombre:");
        labelCedula = new JLabel("Cédula:");
        labelAntiguedad = new JLabel("<html>Antigüedad:<br>(en años)</html>"); // JLabel con HTML para poder mostrar texto en otra linea
        labelCelular = new JLabel("Celular:");
        
        textoNombre = new JTextField(20);
        textoCedula = new JTextField(40);
        textoAntiguedad = new JTextField(20);
        textoCelular = new JTextField(20);
        
        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");
        
        // Distribucion en Layout
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10)); // Grid Layout params: filas, col, distancia en px.
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelFormulario.add(labelNombre);
        panelFormulario.add(textoNombre);
        panelFormulario.add(labelCedula);
        panelFormulario.add(textoCedula);
        panelFormulario.add(labelAntiguedad);
        panelFormulario.add(textoAntiguedad);
        panelFormulario.add(labelCelular);
        panelFormulario.add(textoCelular);
        
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
                guardarManager();
            }
        });
        
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    public void guardarManager() {
        String nombre = textoNombre.getText().trim();
        String cedula = textoCedula.getText().trim();
        String antiguedadStr = textoAntiguedad.getText().trim();
        String celular = textoCelular.getText().trim();
        
        if (nombre.isEmpty() || cedula.isEmpty() || antiguedadStr.isEmpty() || celular.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // validar que la antiguedad sea un entero
        int antiguedad;
        try {
            antiguedad = Integer.parseInt(antiguedadStr);
            if (antiguedad < 0) {
                JOptionPane.showMessageDialog(this, "La antigüedad no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "La antigüedad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear Manager nuevo y guardarlo en el Sistema si la cedula no esta en el sistema (clases Empleado/Manager).
        Manager nuevo = new Manager(nombre, cedula, antiguedad, celular);
        boolean guardado = sistema.agregarManager(nuevo);
        if (!guardado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un manager registrado con esta cédula.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        JOptionPane.showMessageDialog(this, "Manager registrado correctamente.");
        actualizarLista();
        dispose();
    }
    
    public void actualizarLista() {
        modeloLista.clear();
        ArrayList<Manager> ordenados = new ArrayList<>(sistema.getManagersOrdenadosAntiguedadDecreciente());
        for (Manager m : ordenados) {
            modeloLista.addElement(m.getNombre());
        }
    }
}

