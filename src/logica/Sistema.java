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
    
    // Métodos de manejo de áreas
    /** Crear metodo agregarArea. Tiene que chequear la existencia comparando area.nombre en el ArrayList areas.
     * Si no existe se agrega, sino devuelve el error.
     */
    public boolean agregarArea(Area a) {
        return true || false;
    }
    
    // Metodo que elimine un Area.
    public boolean eliminarArea(Area a) {
        return true || false;
    }
    
    // metodo que muestre todas las areas
    public ArrayList<Area> getAreas() {
        return areas;
    }
    
    // Metodo para devolver un area por su nombre 
    public Area getAreaPorNombre(String nombre) {
        for (Area a : areas) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                return a;
            }
        }
        return null;
    }
    
    // implementar metodo que cree un nuevo arrayList con las areas guardadas ordenadas por nombre creciente
    public ArrayList<Area> getAreasOrdenadas() {
        ArrayList<Area> ordenadas = new ArrayList<>();
        return ordenadas;
    }
    
    // Implementar metodo que cree un nuevo arrayList que contenga las areas sin Empleados para poder ser eliminadas
    public ArrayList<Area> getAreasSinEmpleados() {
        ArrayList<Area> areasSinEmpleados = new ArrayList<>();
        return areasSinEmpleados;
    }
    
    // Implementar metodo que modifique la descripcion de un area que este dentro de areas. (metodo setDescripcion(desc) de Area)
    public boolean modificarDescripcionArea(Area area, String nuevaDescripcion) {
        return true || false;
    }
    
    // Metodos de manejo de Managers
    // Crear metodo public boolean agregarManager(Manager m) -> Devolver true || false si se crea (antes de agregar verificar que ningun empleado/manager tiene esa cédula)
    // Crear metodo getManagers() -> devuelve una nueva lista con todos los managers.
    // crear metodo getManagersOrdenados() -> Devolver nueva lista de managers, ordenados por antiguedad decr.
    // Crear metodo getManagerPorNombre(String nombre) -> devolver obj Manager de la lista managers filtrando por nombre.
    // crear metodo public ArrayList<Manager> getManagersSinEmpleados() -> Crear lista nueva y filtrar solo managers que no tienen empleados a cargo.
    // crear metodo public boolean eliminarManager(Manager m) -> elimina el manager a traves de su obj solo si no tiene empleados, sino return false.
    // crear metodo modificarCelularManager(Manager managerSeleccionado, String nuevoCelular) -> Cambia el celular del manager usando Manager.setCelular(String nuevoCelular)
}
