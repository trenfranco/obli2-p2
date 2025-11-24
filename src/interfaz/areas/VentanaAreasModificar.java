package interfaz.areas;

import logica.Sistema;
import dominio.Area;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class VentanaAreasModificar extends JFrame {
    private Sistema sistema;
    
    private JList<String> listaAreas;
    private DefaultListModel<String> modeloLista;
    
    private JLabel labelTitulo;
    private JLabel labelNombre;
    private JTextArea textoDescripcion;
    private JLabel labelPresupuesto;
    
    private JButton botonModificar;
    private JButton botonCancelar;
    
    private Area areaSeleccionada;
    
    public VentanaAreasModificar(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        inicializarComponentes();
        inicializarEventos();
        actualizarListaAreas();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Modificar área");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Parte izquierda (mostrar lista de areas sin empleados)
        modeloLista = new DefaultListModel<>();
        listaAreas = new JList<>(modeloLista);
        listaAreas.setBorder(BorderFactory.createTitledBorder("Áreas existentes"));
        listaAreas.setFont(new Font("", Font.BOLD, 14));
        JScrollPane scrollLista = new JScrollPane(listaAreas);
        
        // Parte derecha (mostrar informacion del area seleccionada y permitir modificar la descripcion)
        labelTitulo = new JLabel("MODIFICAR ÁREA", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));
        
        labelNombre = new JLabel("<html></html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        labelNombre.setForeground(Color.GRAY);
        textoDescripcion = new JTextArea(3, 30);
        textoDescripcion.setLineWrap(true);
        textoDescripcion.setWrapStyleWord(true);
        textoDescripcion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        labelPresupuesto = new JLabel("<html></html>");
        labelPresupuesto.setForeground(Color.GRAY);

        
        JPanel panelDatos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(labelNombre);
        panelDatos.add(new JLabel("Descripción:"));
        panelDatos.add(new JScrollPane(textoDescripcion));
        panelDatos.add(new JLabel("Presupuesto:"));
        panelDatos.add(labelPresupuesto);
        
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
        
        botonModificar.addActionListener(e -> modificarArea());
        botonCancelar.addActionListener(e -> dispose());
    }
    
    private void actualizarListaAreas() {
        modeloLista.clear();
        
        ArrayList<Area> lista = sistema.getAreas();
        for (Area a : lista) {
            modeloLista.addElement(a.getNombre());
        }
    }
    
    private void mostrarDatosArea(Area area) {
        labelNombre.setText("<html>" + area.getNombre() + "</html>"); // Uso de HTML para evitar desbordes por contenidos muy largos.
        textoDescripcion.setText(area.getDescripcion());
        labelPresupuesto.setText("<html>" + area.getPresupuestoAnual() + "</html>");
    }
    
    private void modificarArea() {
        if (areaSeleccionada == null) {
            return;
        }
        
        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Modificar la descripción del área \"" + areaSeleccionada.getNombre() + "\"?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirmar == JOptionPane.YES_OPTION) {
            String nuevaDescripcion = textoDescripcion.getText().trim();
            
            if (nuevaDescripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.");
                return;
            }
            
            sistema.modificarDescripcionArea(areaSeleccionada, nuevaDescripcion);
            
            JOptionPane.showMessageDialog(this,
                    "Área modificada con éxito.",
                    "Modificación",
                    JOptionPane.INFORMATION_MESSAGE
            );
            actualizarListaAreas();
            actualizarDatosMostrados();
        }
    }
    
    private void actualizarDatosMostrados() {
        if (areaSeleccionada != null) {
            labelNombre.setText("<html>" + areaSeleccionada.getNombre() + "</html>");
            labelPresupuesto.setText("<html>" + String.valueOf(areaSeleccionada.getPresupuestoAnual()) + "</html>");
            textoDescripcion.setText(areaSeleccionada.getDescripcion());
        }
    }
}