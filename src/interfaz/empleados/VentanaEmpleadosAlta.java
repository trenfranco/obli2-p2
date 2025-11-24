package interfaz.empleados;

import logica.Sistema;
import dominio.Empleado;
import dominio.Manager;
import dominio.Area;
import javax.swing.*;
import java.awt.*;

// CONSIDERAR CASO DE QUE LOS COMBOS ESTEN VACIOS PERO AUN ASI SE INTENTE GUARDAR, esto genera:
// Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "dominio.Area.getPresupuestoAnual()" because "a" is null
// El CV se genera igual aunque el empleado no se cree.

public class VentanaEmpleadosAlta extends JFrame {
    private Sistema sistema;

    private JList<String> listaEmpleados;
    private DefaultListModel<String> modeloLista;

    private JTextField textoNombre;
    private JTextField textoCedula;
    private JTextField textoCelular;
    private JTextArea textoCurriculum;
    private JTextField textoSalario;

    private JComboBox<Manager> comboManagers;
    private JComboBox<Area> comboAreas;

    private JButton botonGuardar;
    private JButton botonCancelar;

    public VentanaEmpleadosAlta(Sistema sistema) {
        this.sistema = sistema;

        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        actualizarLista();
        cargarCombos();
    }

    private void configurarVentana() {
        setTitle("MARTRE - Alta de empleados");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/images/logo.png")).getImage());
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Lista izquierda
        modeloLista = new DefaultListModel<>();
        listaEmpleados = new JList<>(modeloLista);
        listaEmpleados.setBorder(BorderFactory.createTitledBorder("Empleados (salario creciente)"));
        listaEmpleados.setFont(new Font("", Font.BOLD, 14));
        JScrollPane scrollLista = new JScrollPane(listaEmpleados);
        scrollLista.setPreferredSize(new Dimension(280, 0));

        // Formulario derecha
        JLabel labelTitulo = new JLabel("ALTA DE EMPLEADO", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("", Font.BOLD, 18));

        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelCedula = new JLabel("Cédula:");
        JLabel labelCelular = new JLabel("Celular:");
        JLabel labelCurriculum = new JLabel("Texto del curriculum:");
        JLabel labelSalario = new JLabel("Salario mensual:");
        JLabel labelManager = new JLabel("Manager:");
        JLabel labelArea = new JLabel("Área:");

        textoNombre = new JTextField(20);
        textoCedula = new JTextField(20);
        textoCelular = new JTextField(20);
        textoCurriculum = new JTextArea(3, 20);
        textoCurriculum.setLineWrap(true);
        textoCurriculum.setWrapStyleWord(true);
        textoCurriculum.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollCv = new JScrollPane(textoCurriculum);

        textoSalario = new JTextField(20);

        comboManagers = new JComboBox<>();
        comboAreas = new JComboBox<>();

        // Distribucion del formulario (GridLayout 7x2)
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panelFormulario.add(labelNombre);
        panelFormulario.add(textoNombre);

        panelFormulario.add(labelCedula);
        panelFormulario.add(textoCedula);

        panelFormulario.add(labelCelular);
        panelFormulario.add(textoCelular);

        panelFormulario.add(labelCurriculum);
        panelFormulario.add(scrollCv);

        panelFormulario.add(labelSalario);
        panelFormulario.add(textoSalario);

        panelFormulario.add(labelManager);
        panelFormulario.add(comboManagers);

        panelFormulario.add(labelArea);
        panelFormulario.add(comboAreas);

        // Botones
        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        // Panel derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        // Panel contenedor
        JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelContenedor.add(scrollLista, BorderLayout.WEST);
        panelContenedor.add(panelDerecho, BorderLayout.CENTER);

        add(panelContenedor);
    }

    private void agregarEventos() {
        botonGuardar.addActionListener(e -> guardarEmpleado());
        botonCancelar.addActionListener(e -> dispose());

        listaEmpleados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                String linea = listaEmpleados.getSelectedValue();
                if (linea == null) return;

                // "Nombre - CI - $salario"
                String[] partes = linea.split(" - ");
                if (partes.length < 2) return;

                String cedula = partes[1];
                Empleado emp = sistema.getEmpleadoPorCedula(cedula);
                if (emp == null) return;

                textoNombre.setText(emp.getNombre());
                textoCedula.setText(emp.getCedula());
                textoCelular.setText(emp.getCelular());
                textoCurriculum.setText(emp.getTextoCurriculum());
                textoSalario.setText(String.valueOf(emp.getSalario()));

                comboManagers.setSelectedItem(emp.getManager());
                comboAreas.setSelectedItem(emp.getArea());
            }
        });
    }

    private void guardarEmpleado() {
        String nombre = textoNombre.getText().trim();
        String cedula = textoCedula.getText().trim();
        String celular = textoCelular.getText().trim();
        String curriculum = textoCurriculum.getText().trim();
        String salarioStr = textoSalario.getText().trim();

        if (nombre.isEmpty() || cedula.isEmpty() || celular.isEmpty() ||
                curriculum.isEmpty() || salarioStr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
            if (salario <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El salario debe ser un número positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Manager m = (Manager) comboManagers.getSelectedItem();
        Area a = (Area) comboAreas.getSelectedItem();

        Empleado nuevo = new Empleado(nombre, cedula, celular, curriculum, salario, m, a);

        boolean agregado = sistema.agregarEmpleado(nuevo);
        if (!agregado) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el empleado. Revise la cédula y presupuesto.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.");

        actualizarLista();
        dispose();
    }

    private void cargarCombos() {
        comboManagers.removeAllItems();
        for (Manager m : sistema.getManagers())
            comboManagers.addItem(m);

        comboAreas.removeAllItems();
        for (Area a : sistema.getAreas())
            comboAreas.addItem(a);
    }

    private void actualizarLista() {
        modeloLista.clear();
        for (Empleado e : sistema.getEmpleadosOrdenadosSalarioCreciente()) {
            modeloLista.addElement(
                    e.getNombre() + " - " + e.getCedula() + " - $" + e.getSalario()
            );
        }
    }
}
