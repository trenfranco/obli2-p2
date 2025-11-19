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
public class VentanaAreasBaja extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaAreas;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JLabel labelDescripcion;
    private JLabel labelPresupuesto;
    
    private JButton botonEliminar;
    private JButton botonCancelar;
    
    private Area areaSeleccionada;
    
    public VentanaAreasBaja(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        inicializarEventos();
        actualizarListaAreas();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Baja de área");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Parte izquierda (mostrar lista de areas sin empleados)
        modeloLista = new DefaultListModel<>();
        listaAreas = new JList<>(modeloLista);
        listaAreas.setBorder(BorderFactory.createTitledBorder("Áreas sin empleados"));
        listaAreas.setFont(new Font("", Font.BOLD, 16));
        JScrollPane scrollLista = new JScrollPane(listaAreas);
        
        // Parte derecha (mostrar informacion del area seleccionada y permitir Eliminar)
        labelTitulo = new JLabel("BAJA DE ÁREA");
        labelTitulo.setFont(new Font("", Font.BOLD, 16));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelNombre = new JLabel("<html></html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelDescripcion = new JLabel("<html></html>");
        labelPresupuesto = new JLabel("<html></html>");
        
        JPanel panelDatos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(labelNombre);
        panelDatos.add(new JLabel("Descripción:"));
        panelDatos.add(labelDescripcion);
        panelDatos.add(new JLabel("Presupuesto:"));
        panelDatos.add(labelPresupuesto);
        
        botonEliminar = new JButton("Eliminar");
        botonCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(botonEliminar);
        panelBotones.add(botonCancelar);
        
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelDatos, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        
        // Contenedor de la lista y el panel con detalles para aplicar margenes
        JPanel panelContenedor = new JPanel(new BorderLayout(10, 10));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panelContenedor.add(scrollLista, BorderLayout.WEST);
        panelContenedor.add(panelDerecho, BorderLayout.CENTER);
        
        // Agregar lista y Panel Derecho al Layout Principal
        add(panelContenedor);
        
        actualizarListaAreas();
    }
    
    private void inicializarEventos() {
        // Eventos a inicilizar: Mostrar Area seleccionada, boton eliminar area, boton cancelar.
        listaAreas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nombre = listaAreas.getSelectedValue();
                if (nombre != null) {
                    areaSeleccionada = sistema.getAreaPorNombre(nombre);
                    mostrarDatosArea(areaSeleccionada);
                }
            }
        });
        
        botonEliminar.addActionListener(e -> eliminarArea());
        botonCancelar.addActionListener(e -> dispose());
    }
    
    private void actualizarListaAreas() {
        modeloLista.clear();
        
        ArrayList<Area> lista = sistema.getAreasSinEmpleados();
        for (Area a : lista) {
            modeloLista.addElement(a.getNombre());
        }
    }
    
    private void mostrarDatosArea(Area area) {
        labelNombre.setText("<html>" + area.getNombre() + "</html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelDescripcion.setText("<html>" + area.getDescripcion() + "</html>");
        labelPresupuesto.setText("<html>" + area.getPresupuesto() + "</html>");
    }
    
    private void eliminarArea() {
        if (areaSeleccionada == null) {
            return;
        }
        
        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar el área \"" + areaSeleccionada.getNombre() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirmar == JOptionPane.YES_OPTION) {
            sistema.eliminarArea(areaSeleccionada);
            
            JOptionPane.showMessageDialog(this,
                    "Área eliminada con éxito.",
                    "Eliminación",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            areaSeleccionada = null;
            limpiarDatosMostrados();
            actualizarListaAreas();
        }
    }
    
    private void limpiarDatosMostrados() {
        labelNombre.setText("");
        labelDescripcion.setText("");
        labelPresupuesto.setText("");
    }
}
