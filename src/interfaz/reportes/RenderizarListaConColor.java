package interfaz.reportes;

import dominio.Area;
import logica.Sistema;

import javax.swing.*;
import java.awt.*;

public class RenderizarListaConColor extends DefaultListCellRenderer {

    private Sistema sistema;

    public RenderizarListaConColor(Sistema sistema) {
        this.sistema = sistema;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        Area area = (Area) value;

        // Texto del item
        double porcentaje = sistema.calcularPorcentajeArea(area);
        label.setText(area.getNombre() + " (" + String.format("%.1f", porcentaje) + "%)");

        // Color según porcentaje
        if (!isSelected) {
            label.setBackground(sistema.getColorParaArea(area));
        }

        label.setOpaque(true);
        return label;
    }
}
