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
        
        for (Area a: this.areas) {
            if (a.getIntegrantes().size() == 0)
                areasSinEmpleados.add(a);
        }
        return areasSinEmpleados;
    }
    
    // Implementar metodo que modifique la descripcion de un area que este dentro de areas. (metodo setDescripcion(desc) de Area)
    public boolean modificarDescripcionArea(Area area, String nuevaDescripcion) {
        for (Area a: this.areas) {
            if (area.getNombre().equalsIgnoreCase(a.getNombre())) {
                a.setDescripcion(nuevaDescripcion);
                return true;
            }
        }
        return false;
    }
    
    // Metodos de manejo de Managers
    
    public ArrayList<Manager> getManagers(){
        return this.managers;
    }
    
    public boolean agregarManager(Manager manager) {
        for (Manager m: this.managers) {
            if (m.getCi() == manager.getCi())
                return false;
        }
        
        for (Empleado e: this.empleados) {
            if (e.getCi() == manager.getCi())
                return false;
        }
        
        this.managers.add(manager);
        return true;
    }
    
    public ArrayList<Manager> getManagersOrdenados() {
        ArrayList<Manager> copia = new ArrayList<Manager>(this.managers);
        
        int largo = copia.size();
        int antiguedad1, antiguedad2;
        Manager aux;
        
        for (int i=0; i < largo; i++) {
            for (int j=0; j < largo -1; j++){
                antiguedad1 = copia.get(j).getAntiguedad();
                antiguedad2 = copia.get(j+1).getAntiguedad();
                
                if (antiguedad1 > antiguedad2) {
                    aux = copia.get(j);
                    copia.set(j, copia.get(j+1));
                    copia.set(j+1, aux);
                }
            }
        }
        return copia;
    }
    
    public Manager getManagerPorNombre(String nombre) {
        for (Manager m : this.managers) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }
    
    public ArrayList<Manager> getManagersSinEmpleados() {
        ArrayList<Manager> managers_sin_empleados = new ArrayList<Manager>();
        
        for (Manager m: this.managers){
            if (m.getEmpleadosACargo().isEmpty())
                managers_sin_empleados.add(m);
        }
        return managers_sin_empleados;
    }
    
    public boolean eliminarManager(Manager manager) {
        // repetimos validacion x las dudas, quitar luego
        for (Manager m: this.managers) {
            if (m.getCi().equalsIgnoreCase(manager.getCi()) && m.getEmpleadosACargo().isEmpty())
                return this.managers.remove(m);
        }
        return false;
    }
    
    // Manejo Empleados
    
    public boolean agregarEmpleado(Empleado empleado, Manager manager, Area area) {
        for (Manager m: this.managers) {
            if (m.getCi() == empleado.getCi())
                return false;
        }
        
        for (Empleado e: this.empleados) {
            if (e.getCi() == empleado.getCi())
                return false;
        }
        
        this.empleados.add(empleado);
        manager.agregarEmpleado(empleado);
        area.agregarEmpleado(empleado);
        return true;
    }
    
    public ArrayList<Empleado> getEmpleadosOrdenados() {
        ArrayList<Empleado> copia = new ArrayList<>(this.empleados);
        int n = copia.size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                Empleado e1 = copia.get(j);
                Empleado e2 = copia.get(j + 1);

                if (e1.getSalario() > e2.getSalario()) {
                    copia.set(j, e2);
                    copia.set(j + 1, e1);
                }
            }
        }
        return copia;
    }
    
    // Logica para movimientos
    
    public boolean ejecutarMovimiento(Movimiento movimiento) {
        Empleado empleado = movimiento.getEmpleado();
        
        int presupuesto_necesario = Movimiento.CalcularPresupuestoNecesario(empleado.getSalario(), movimiento.getMes());
        int presupuesto_area_destino = movimiento.getAreaDestino().getPresupuestoAnual();
        
        Area area_origen = empleado.getArea();
        Area area_destino = movimiento.getAreaDestino();
        
        if (presupuesto_area_destino >= presupuesto_necesario) {
            area_origen.agregarPresupuesto(presupuesto_necesario);
            area_destino.restarPresupuesto(presupuesto_necesario);
            
            area_origen.EliminarEmpleado(empleado);
            area_destino.AgregarEmpleado(empleado);
            
            empleado.setArea(area_destino);
            
            return true;
        }
        return false;
    }
}