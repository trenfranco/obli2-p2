package interfaz.managers;

import logica.Sistema;
import dominio.Manager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class VentanaManagersBaja extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaManagers;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JLabel labelCedula;
    private JLabel labelAntiguedad;
    private JLabel labelCelular;
    
    private JButton botonEliminar;
    private JButton botonCancelar;
    
    private Manager managerSeleccionado;
    
    /*
    * CASOS NO CONSIDERADOS:
    * Manejo de cuando no hay managers sin empleados
    * Manejo cuando sistema devuelva lista vacía
    */
    
    public VentanaManagersBaja(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        inicializarEventos();
        actualizarListaManagers();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Baja de manager");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Parte izquierda (mostrar lista de managers sin empleados)
        modeloLista = new DefaultListModel<>();
        listaManagers = new JList<>(modeloLista);
        listaManagers.setBorder(BorderFactory.createTitledBorder("Managers sin empleados a cargo"));
        listaManagers.setFont(new Font("", Font.BOLD, 14));
        JScrollPane scrollLista = new JScrollPane(listaManagers);
        
        // Parte derecha (mostrar informacion del Manager seleccionado y permitir Eliminar)
        labelTitulo = new JLabel("BAJA DE MANAGER", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));
        
        labelNombre = new JLabel("<html></html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelCedula = new JLabel("<html></html>");
        labelAntiguedad = new JLabel("<html></html>");
        labelCelular = new JLabel("<html></html>");
        
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(labelNombre);
        panelDatos.add(new JLabel("Cédula:"));
        panelDatos.add(labelCedula);
        panelDatos.add(new JLabel("Antigüedad:"));
        panelDatos.add(labelAntiguedad);
        panelDatos.add(new JLabel("Celular:"));
        panelDatos.add(labelCelular);
        
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
        actualizarListaManagers();
    }
    
    private void inicializarEventos() {
        // Eventos a inicilizar: Mostrar Manager seleccionada, boton eliminar Manager, boton cancelar.
        listaManagers.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nombre = listaManagers.getSelectedValue();
                if (nombre != null) {
                    managerSeleccionado = sistema.getManagerPorNombre(nombre);
                    mostrarDatosManager(managerSeleccionado);
                }
            }
        });
        
        botonEliminar.addActionListener(e -> eliminarManager());
        botonCancelar.addActionListener(e -> dispose());
    }
    
    private void actualizarListaManagers() {
        modeloLista.clear();
        
        ArrayList<Manager> managersSinEmpleados = sistema.getManagersSinEmpleados();
        for (Manager m : managersSinEmpleados) {
            modeloLista.addElement(m.getNombre());
        }
    }
    
    private void mostrarDatosManager(Manager manager) {
        labelNombre.setText("<html>" + manager.getNombre() + "</html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelCedula.setText("<html>" + manager.getCedula() + "</html>");
        labelAntiguedad.setText("<html>" + manager.getAntiguedad() + "</html>");
        labelCelular.setText("<html>" + manager.getCelular() + "</html>");
    }
    
    private void eliminarManager() {
        if (managerSeleccionado == null) {
            return;
        }
        
        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar el manager \"" + managerSeleccionado.getNombre() + "\"?",
                "Confirmar eliminar",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirmar == JOptionPane.YES_OPTION) {
            boolean eliminado = sistema.eliminarManager(managerSeleccionado);
            
            if (!eliminado) {
                // No se elimino porque tiene empleados a cargo
                JOptionPane.showMessageDialog(this,
                    "No es posible eliminar este manager.\n"
                    + "Tiene empleados a cargo.",
                    "Error al eliminar",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            JOptionPane.showMessageDialog(this,
                    "Manager eliminado con éxito.",
                    "Eliminar",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            managerSeleccionado = null;
            limpiarDatosMostrados();
            actualizarListaManagers();
        }
    }
    
    private void limpiarDatosMostrados() {
        labelNombre.setText("");
        labelCedula.setText("");
        labelAntiguedad.setText("");
        labelCelular.setText("");
    }
}
