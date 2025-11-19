package logica;

import dominio.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author matim
 */
public class Sistema implements Serializable {
    private ArrayList<Area> areas;
    private ArrayList<Manager> managers;
    private ArrayList<Empleado> empleados;
    private ArrayList<Movimiento> movimientos;
    
    public Sistema() {
        areas = new ArrayList<>();
        managers = new ArrayList<>();
        empleados = new ArrayList<>();
        movimientos = new ArrayList<>();
    }
    
    /** Crear metodo agregarArea. Tiene que chequear la existencia comparando area.nombre en el ArrayList areas.
     * Si no existe se agrega, sino devuelve el error.
     */
    public boolean agregarArea(Area a) {
        return true;
    }
    
    // metodo que muestre todas las areas
    public ArrayList<Area> getAreas() {
        return areas;
    }
    
    // implementar metodo que cree un nuevo arrayList con las areas guardadas ordenadas por nombre creciente
    public ArrayList<Area> getAreasOrdenadas() {
        ArrayList<Area> ordenadas = new ArrayList<>();
        return ordenadas;
    }
}
