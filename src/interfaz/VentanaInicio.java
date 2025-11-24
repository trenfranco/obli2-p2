package interfaz;

import dominio.*;
import logica.Persistencia;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaInicio extends JFrame {
    private JLabel labelLogo;
    private JLabel labelMatias;
    private JLabel labelFranco;
    private JLabel labelObligatorio;
    private JSeparator separador;

    public VentanaInicio() {
        inicializarComponentes();
        configurarVentana();
        iniciarCuentaRegresiva();
    }
    
    private void configurarVentana() {
        setTitle("ERP - MARTRE");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    // Crear y configurar elementos de la ventana
    private void inicializarComponentes() {
        labelLogo = new JLabel();
        labelMatias = new JLabel("Matías Martínez - 282558");
        labelFranco = new JLabel("Franco Trenche - 368637");
        labelObligatorio = new JLabel("Segundo Obligatorio Programación 2");
        separador = new JSeparator();
        separador.setMaximumSize(new Dimension(150, 2)); // Dimension(px ancho, px alto)

        labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
        labelFranco.setHorizontalAlignment(SwingConstants.CENTER);
        labelMatias.setHorizontalAlignment(SwingConstants.CENTER);
        labelObligatorio.setHorizontalAlignment(SwingConstants.CENTER);
        labelObligatorio.setFont(new Font("", Font.BOLD, 14));

        // Insertar imagen (Escalando el tamaño)
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/interfaz/images/logo.png"));
        Image imagenOriginal = iconoOriginal.getImage();
        // Escalar proporcionalmente (ancho = -1 para mantener la relación de aspecto)
        Image imagenEscalada = imagenOriginal.getScaledInstance(-1, 350, Image.SCALE_SMOOTH);
        labelLogo.setIcon(new ImageIcon(imagenEscalada));
        
        setLayout(new BorderLayout());
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        

        // Alineación
        labelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelFranco.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMatias.setAlignmentX(Component.CENTER_ALIGNMENT);
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelObligatorio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espacios
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(labelLogo);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(labelFranco);
        panelCentro.add(labelMatias);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(separador);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(labelObligatorio);
        
        // Panel contenedor para centrar verticalmente
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        // Agregar “glue” arriba
        contenedor.add(Box.createVerticalGlue());
        // Agregar el panel con el contenido
        contenedor.add(panelCentro);
        // Agregar “glue” abajo
        contenedor.add(Box.createVerticalGlue());

        add(contenedor, BorderLayout.CENTER);
    }

    // Timer de 3.5s para mostrar opciones.
    private void iniciarCuentaRegresiva() {
        Timer t = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarOpcionesInicio();
            }
        });

        t.setRepeats(false);
        t.start();
    }

    // Menu de selección de inicio
    private void mostrarOpcionesInicio() {
        String[] opciones = {
            "Sistema nuevo",
            "Sistema guardado",
            "Datos precargados"
        };

        int seleccion = JOptionPane.showOptionDialog(
                this, // Component parentComponent
                "Comenzar con ...", // Object message
                "Inicio del Sistema", // String title
                JOptionPane.DEFAULT_OPTION, // int optionType
                JOptionPane.PLAIN_MESSAGE, // int messageType
                null, // Icon icon
                opciones, // Object[] options
                opciones[0] // Object initialValue
        );

        Sistema sistema = null;

        switch (seleccion) {

            case 0:  // nuevo
                sistema = new Sistema();
                break;

            case 1:  // cargar
                sistema = Persistencia.cargarSistema();
                if (sistema == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró un sistema guardado. Se creará uno nuevo.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                    sistema = new Sistema();
                }
                break;

            case 2:  // datos ficticios
                sistema = new Sistema();
                sistema.cargarDatosPrecargados();
                break;

            default:
                System.exit(0);
        }

        new VentanaMenuPrincipal(sistema).setVisible(true);
        dispose();
    }

    // Datos precargados
    private void cargarDatosFicticios(Sistema sistema) {
        Area a1 = new Area("Personal", "Reclutamiento de personal, promociones, gestión de cargos", 100000);
        Area a2 = new Area("RRHH", "Relacionamiento en la empresa, organigrama, gestión de equipos", 80000);
        Area a3 = new Area("Seguridad", "Seguridad física, vigilancia, seguridad informática, protocolos y políticas de seguridad", 120000);
        Area a4 = new Area("Comunicaciones", "Comunicaciones internas, reglas y protocolos, comunicaciones con proveedores y clientes", 20000);
        Area a5 = new Area("Marketing", "Acciones planificadas, publicidad en medios masivos, publicidad en redes, gestión de redes", 95000);

        sistema.agregarArea(a1);
        sistema.agregarArea(a2);
        sistema.agregarArea(a3);
        sistema.agregarArea(a4);
        sistema.agregarArea(a5);

        Manager m1 = new Manager("Ana Martínez", "4.568.369-1", 10, "099 123456");
        Manager m2 = new Manager("Ricardo Morales", "3.214.589-3", 4, "094 121212");
        Manager m3 = new Manager("Laura Torales", "3.589.257-5", 1, "099 654321");
        Manager m4 = new Manager("Juan Pablo Zapata", "4.555.197-7", 5, "099 202020");

        sistema.agregarManager(m1);
        sistema.agregarManager(m2);
        sistema.agregarManager(m3);
        sistema.agregarManager(m4);
    }

}