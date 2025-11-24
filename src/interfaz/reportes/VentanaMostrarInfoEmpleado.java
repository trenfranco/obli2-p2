package interfaz.reportes;

import dominio.Empleado;
import dominio.Area;
import javax.swing.*;
import java.awt.event.*;

public class VentanaMostrarInfoEmpleado implements ActionListener {

    private Empleado empleado;
    private JFrame ventanaPadre;

    public VentanaMostrarInfoEmpleado(Empleado empleado, JFrame ventanaPadre) {
        this.empleado = empleado;
        this.ventanaPadre = ventanaPadre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
                ventanaPadre,
                "Nombre: " + empleado.getNombre() +
                        "\nCI: " + empleado.getCedula() +
                        "\nCelular: " + empleado.getCelular() +
                        "\nSalario: " + empleado.getSalario() +
                        "\nÁrea: " + empleado.getArea().getNombre() +
                        "\nCV:\n" + empleado.getTextoCurriculum(),
                "Información del empleado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}