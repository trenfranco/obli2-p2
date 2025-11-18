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
        labelObligatorio.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Insertar imagen (Escalando el tamaño)
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/interfaz/images/logoV1.png"));
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
            "Sistema con datos precargados"
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
                cargarDatosFicticios(sistema);
                break;

            default:
                System.exit(0);
        }

        new VentanaMenuPrincipal(sistema).setVisible(true);
        dispose();
    }

    // Datos precargados (ficticios)
    private void cargarDatosFicticios(Sistema sistema) {
        // Desarrollar
    }
}