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
    public boolean agregarArea(Area area) {
       for (Area a: this.areas) {
           if (area.getNombre().equalsIgnoreCase(a.getNombre()))
               return false;
       }
       this.areas.add(area);
       return true;
    }
    
    // Metodo que elimine un Area.
    public boolean eliminarArea(Area area) {
        for (Area a: this.areas) {
            if (area.getNombre().equalsIgnoreCase(a.getNombre())) {
                this.areas.remove(this.areas.indexOf(a));
                return true;
            }
        }
        return false;
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
        ArrayList<Area> copia = new ArrayList<Area>(areas);
        int largo = copia.size();
        
        for (int j = 0; j < largo; j++) {
            for (int i = 0; i < largo - 1; i++) {
                Area aux;
                
                String nombre_area = copia.get(i).getNombre();
                String nombre_area_siguiente = copia.get(i + 1).getNombre();

                if (nombre_area.compareToIgnoreCase(nombre_area_siguiente) >  0) {
                    aux = copia.get(i);
                    copia.set(i, copia.get(i + 1));
                    copia.set(i + 1, aux);
                }

            }
        }
        return copia;
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
    // crear metodo getManagersOrdenados() -> Devolver nueva lista de managers, ordenados por antiguedad decr.
    // Crear metodo getManagerPorNombre(nombre) -> devolver obj Manager de la lista managers filtrando por nombre.
    // crear metodo public ArrayList<Manager> getManagersSinEmpleados() -> Crear lista nueva y filtrar solo managers que no tienen empleados a cargo.
    // crear metodo public boolean eliminarManager(Manager m) -> elimina el manager a traves de su obj solo si no tiene empleados, sino return false.
}