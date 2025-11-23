package interfaz.managers;

import logica.Sistema;
import dominio.Manager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Falta agregar verificacion que el input en el campo celular no sea otra cosa que numeros
 * @author matim
 */
public class VentanaManagersModificar extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaManagers;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JLabel labelCedula;
    private JLabel labelAntiguedad;
    private JTextArea textoCelular;
    
    private JButton botonModificar;
    private JButton botonCancelar;
    
    private Manager managerSeleccionado;
    
    public VentanaManagersModificar(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        inicializarEventos();
        actualizarListaManagers();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Modificar manager");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Parte izquierda (mostrar lista de areas sin empleados)
        modeloLista = new DefaultListModel<>();
        listaManagers = new JList<>(modeloLista);
        listaManagers.setBorder(BorderFactory.createTitledBorder("Managers existentes"));
        listaManagers.setFont(new Font("", Font.BOLD, 14));
        JScrollPane scrollLista = new JScrollPane(listaManagers);
        
        // Parte derecha (mostrar informacion del area seleccionada y permitir Eliminar)
        labelTitulo = new JLabel("MODIFICAR MANAGER", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));
        
        labelNombre = new JLabel("<html></html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelNombre.setForeground(Color.GRAY);
        labelCedula = new JLabel("<html></html>");
        labelCedula.setForeground(Color.GRAY);
        labelAntiguedad = new JLabel("<html></html>");
        labelAntiguedad.setForeground(Color.GRAY);
        textoCelular = new JTextArea(3, 30);
        textoCelular.setLineWrap(true);
        textoCelular.setWrapStyleWord(true);
        textoCelular.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(labelNombre);
        panelDatos.add(new JLabel("Cédula:"));
        panelDatos.add(labelCedula);
        panelDatos.add(new JLabel("Antigüedad:"));
        panelDatos.add(labelAntiguedad);
        panelDatos.add(new JLabel("Celular:"));
        panelDatos.add(new JScrollPane(textoCelular));
        
        botonModificar = new JButton("Modificar");
        botonCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(botonModificar);
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
        // Eventos a inicilizar: Mostrar Area seleccionada, boton eliminar area, boton cancelar.
        listaManagers.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nombre = listaManagers.getSelectedValue();
                if (nombre != null) {
                    managerSeleccionado = sistema.getManagerPorNombre(nombre);
                    mostrarDatosManager(managerSeleccionado);
                }
            }
        });
        
        botonModificar.addActionListener(e -> modificarManager());
        botonCancelar.addActionListener(e -> dispose());
    }
    
    private void actualizarListaManagers() {
        modeloLista.clear();
        
        ArrayList<Manager> lista = sistema.getManagers();
        for (Manager m : lista) {
            modeloLista.addElement(m.getNombre());
        }
    }
    
    private void mostrarDatosManager(Manager manager) {
        labelNombre.setText("<html>" + manager.getNombre() + "</html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelCedula.setText("<html>" + manager.getCedula() + "</html>");
        labelAntiguedad.setText("<html>" + manager.getAntiguedad() + "</html>");
        textoCelular.setText(manager.getCelular());
    }
    
    private void modificarManager() {
        if (managerSeleccionado == null) {
            return;
        }
        
        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Modificar el celular del manager \"" + managerSeleccionado.getNombre() + "\"?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirmar == JOptionPane.YES_OPTION) {
            String nuevoCelular = textoCelular.getText().trim();
            sistema.modificarCelularManager(managerSeleccionado, nuevoCelular);
            
            JOptionPane.showMessageDialog(this,
                    "Manager modificado con éxito.",
                    "Modificación",
                    JOptionPane.INFORMATION_MESSAGE
            );
            actualizarListaManagers();
            actualizarDatosMostrados();
        }
    }
    
    private void actualizarDatosMostrados() {
        if (managerSeleccionado != null) {
            labelNombre.setText("<html>" + managerSeleccionado.getNombre() + "</html>");
            labelCedula.setText("<html>" + managerSeleccionado.getCedula() + "</html>");
            labelAntiguedad.setText("<html>" + String.valueOf(managerSeleccionado.getAntiguedad()) + "</html>");
            textoCelular.setText(managerSeleccionado.getCelular());
        }
    }
}
