package interfaz;

import interfaz.areas.VentanaAreasAlta;
import interfaz.areas.VentanaAreasBaja;
import interfaz.areas.VentanaAreasModificar;
import logica.Sistema;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author matim
 */
public class VentanaMenuPrincipal extends JFrame {

    private Sistema sistema;
    private JMenuBar barraMenu;

    public VentanaMenuPrincipal(Sistema sistema) {
        this.sistema = sistema;
        
        configurarVentana();
        // Imagen de fondo del menu principal
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/interfaz/images/fondoMenu.jpg"));
        Image imagenOriginal = iconoOriginal.getImage();
        
        // Escalar imagen al tamaño de la ventana
        Image imagenEscalada = imagenOriginal.getScaledInstance(
                getWidth(),
                getHeight(),
                Image.SCALE_SMOOTH
        );
        
        // Crear JLabel con el fondo escalado
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        JLabel fondo = new JLabel(iconoEscalado);
        
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
        
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("MARTRE - Menú Principal");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logoV1.png")).getImage());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    // Crear y configurar elementos dentro de la ventana
    private void inicializarComponentes() {
        barraMenu = new JMenuBar();

        // Menú Áreas
        JMenu menuAreas = new JMenu("Áreas");
        
        JMenuItem itemAltaArea = new JMenuItem("Alta");
        JMenuItem itemBajaArea = new JMenuItem("Baja");
        JMenuItem itemModArea = new JMenuItem("Modificar");
        JMenuItem itemMovArea = new JMenuItem("Realizar Movimiento");
        
        // Mostrar ventanas al hacer click en la funcionalidad:
        itemAltaArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaAreasAlta(sistema).setVisible(true);
            }
        });
        itemBajaArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaAreasBaja(sistema).setVisible(true);
            }
        });
        itemModArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaAreasModificar(sistema).setVisible(true);
            }
        });
        
        
        menuAreas.add(itemAltaArea);
        menuAreas.add(itemBajaArea);
        menuAreas.add(itemModArea);
        menuAreas.add(itemMovArea);
        
        // Menú Managers
        JMenu menuManagers = new JMenu("Managers");
        
        JMenuItem itemAltaManager = new JMenuItem("Alta");
        JMenuItem itemBajaManager = new JMenuItem("Baja");
        JMenuItem itemModManager = new JMenuItem("Modificar");
        
        menuManagers.add(itemAltaManager);
        menuManagers.add(itemBajaManager);
        menuManagers.add(itemModManager);
        
        // Menú Empleados
        JMenu menuEmpleados = new JMenu("Empleados");
        
        JMenuItem itemAltaEmpleado = new JMenuItem("Alta");
        
        menuEmpleados.add(itemAltaEmpleado);
        
        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        
        JMenuItem itemRepInt = new JMenuItem("Reporte inteligente");
        JMenuItem itemRepEst = new JMenuItem("Reporte de estado de área");
        JMenuItem itemRepMov = new JMenuItem("Reporte de movimientos");
        
        menuReportes.add(itemRepInt);
        menuReportes.add(itemRepEst);
        menuReportes.add(itemRepMov);
        
        // Agregar secciones a la barra del menú
        barraMenu.add(menuAreas);
        barraMenu.add(menuManagers);
        barraMenu.add(menuEmpleados);
        barraMenu.add(menuReportes);
        
        setJMenuBar(barraMenu);
    }
}