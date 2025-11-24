package logica;

import dominio.*;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

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
    
    // Empleados
    public ArrayList<Empleado> getEmpleados() {
            return this.empleados;
    }
    
    public void cargarDatosPrecargados() {
        // Áreas
        Area a1 = new Area(
                "Personal",
                "Reclutamiento de personal, promociones, gestión de cargos",
                100000
        );
        Area a2 = new Area(
                "RRHH",
                "Relacionamiento en la empresa, organigrama, gestión de equipos",
                80000
        );
        Area a3 = new Area(
                "Seguridad",
                "Seguridad física, vigilancia, seguridad informática, protocolos y políticas de seguridad",
                25000
        );
        Area a4 = new Area(
                "Comunicaciones",
                "Comunicaciones internas, reglas y protocolos, comunicaciones con proveedores y clientes",
                20000
        );
        Area a5 = new Area(
                "Marketing",
                "Acciones planificadas, publicidad en medios masivos, publicidad en redes, gestión de redes",
                10000
        );

        agregarArea(a1);
        agregarArea(a2);
        agregarArea(a3);
        agregarArea(a4);
        agregarArea(a5);

        // Managers
        Manager m1 = new Manager("Ana Martínez", "4.568.369-1", 10, "099 123456");
        Manager m2 = new Manager("Ricardo Morales", "3.214.589-3", 4, "094 121212");
        Manager m3 = new Manager("Laura Torales", "3.589.257-5", 1, "099 654321");
        Manager m4 = new Manager("Juan Pablo Zapata", "4.555.197-7", 5, "099 202020");

        agregarManager(m1);
        agregarManager(m2);
        agregarManager(m3);
        agregarManager(m4);
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
        ArrayList<Area> copia = new ArrayList<>(areas);
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
    
    public ArrayList<Area> getAreasSinEmpleados() {
        ArrayList<Area> areasSinEmpleados = new ArrayList<>();
        
        for (Area a: this.areas) {
            if (a.getIntegrantes().isEmpty())
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
            if (m.getCedula().equalsIgnoreCase(manager.getCedula()))
                return false;
        }
        
        for (Empleado e: this.empleados) {
            if (e.getCedula().equalsIgnoreCase(manager.getCedula()))
                return false;
        }
        
        this.managers.add(manager);
        return true;
    }
    
    public ArrayList<Manager> getManagersOrdenadosAntiguedadDecreciente() {
        ArrayList<Manager> copia = new ArrayList<Manager>(this.managers);
        
        int largo = copia.size();
        
        for (int i=0; i < largo; i++) {
            for (int j=0; j < largo -1; j++){
                int antiguedad1 = copia.get(j).getAntiguedad();
                int antiguedad2 = copia.get(j+1).getAntiguedad();
                
                if (antiguedad1 < antiguedad2) {
                    Manager aux = copia.get(j);
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
            if (m.getCedula().equalsIgnoreCase(manager.getCedula()) && m.getEmpleadosACargo().isEmpty())
                return this.managers.remove(m);
        }
        return false;
    }
    
    // Crear metodo modificarCelularManager(managerSeleccionado, nuevoCelular) -> modificar el cel de un Manager de la lista
    public void modificarCelularManager(Manager managerSeleccionado, String nuevoCelular) {
        for (Manager m: this.managers) {
            if (m == managerSeleccionado) {
                m.setCelular(nuevoCelular);
            }
        }
    }
    
    // Manejo Empleados
    
    public boolean agregarEmpleado(Empleado empleado) {
        // constructor anterior
        // agregarEmpleado(Empleado empleado, Manager manager, Area area)
        for (Manager m: this.managers) {
            if (m.getCedula().equalsIgnoreCase(empleado.getCedula()))
                return false;
        }
        
        for (Empleado e: this.empleados) {
            if (e.getCedula().equalsIgnoreCase(empleado.getCedula()))
                return false;
        }
        
        double costoAnualEmp = empleado.getSalario() * 12;
        
        Area a = empleado.getArea();
        if (a.getPresupuestoAnual() < costoAnualEmp) {
            return false;
        }
        
        a.agregarEmpleado(empleado);
        empleado.getManager().agregarEmpleado(empleado);
        this.empleados.add(empleado);
        
        return true;
    }
    
    public Empleado getEmpleadoPorCedula(String cedula) {
        for (Empleado e : empleados) {
            if (e.getCedula().equalsIgnoreCase(cedula)) {
                return e;
            }
        }
        return null;
    }
    
    public Empleado getEmpleadoPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        for (Empleado e : empleados) {
            if (e.getNombre().equalsIgnoreCase(nombre.trim())) {
                return e;
            }
        }
        return null; // no encontrado
    }
    
    public ArrayList<Empleado> getEmpleadosOrdenadosSalarioCreciente() {
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
    
    public boolean agregarMovimiento(Movimiento m) {
       this.movimientos.add(m);
       return true;
    }
    
    public ArrayList<Movimiento> getMovimientos() {
            return this.movimientos;
    }
    
    public boolean ejecutarMovimiento(Movimiento movimiento) {
        Empleado empleado = movimiento.getEmpleado();
        Area area_origen = empleado.getArea();
        Area area_destino = movimiento.getAreaDestino();
        int mes = movimiento.getMes();

        double presupuesto_necesario = Movimiento.CalcularPresupuestoNecesario(empleado.getSalario(), mes);
        double presupuesto_area_destino = movimiento.getAreaDestino().getPresupuestoAnual();

        if (presupuesto_area_destino >= presupuesto_necesario) {
            area_origen.EliminarEmpleado(empleado);
            area_origen.agregarPresupuesto(presupuesto_necesario);

            area_destino.agregarEmpleado(empleado, true);
            area_destino.restarPresupuesto(presupuesto_necesario);

            empleado.setArea(area_destino);

            return true;
        }
        return false;
    }

    //Manejo Reportes
    //Reporte con analisis de ventajas y desventajas
    public Reporte generarReporteInteligente(Area areaOrigen, Area areaDestino, Empleado empleado) {
        Reporte reporte = new Reporte(areaOrigen, areaDestino, empleado);
        reporte.generarReporteConLLM();
        return reporte;
    }
    
    // Reporte de estado de Area:    
    public ArrayList<Area> getAreasOrdenadasPorPorcentajeAsignado() {
        ArrayList<Area> copia = new ArrayList<>(areas);
        copia.sort((a1, a2) -> Double.compare(calcularPorcentajeArea(a2), calcularPorcentajeArea(a1)));
        return copia;
    }
    
    // Calcular el porcentaje de presupuesto gastado de un area
    public double calcularPorcentajeArea(Area area) {
        double gastado = 0;

        for (Empleado e : area.getIntegrantes()) {
            gastado += e.getSalario() * 12;
        }

        double total = area.getPresupuestoAnual() + gastado;
        if (total == 0) {
            return 0;
        }

        return (gastado / total) * 100.0;
    }
    
    // Color de un area segun el porcentaje de presupuesto gastado.
    public Color getColorParaArea(Area area) {
        double p = calcularPorcentajeArea(area);
        if (p > 90) {
            return Color.RED;
        }
        if (p >= 70) {
            return Color.YELLOW;
        }
        return Color.LIGHT_GRAY;
    }
    
    // Obtener empleados por nombre
    public ArrayList<Empleado> getEmpleadosOrdenados(Area area) {
        ArrayList<Empleado> lista = new ArrayList<>(area.getIntegrantes());
        // Selection sort
        for (int i = 0; i < lista.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < lista.size(); j++) {
                Empleado e1 = lista.get(j);
                Empleado e2 = lista.get(minIndex);

                // Comparación de letras 
                if (e1.getNombre().compareToIgnoreCase(e2.getNombre()) < 0) {
                    minIndex = j;
                }
            }

            // Intercambiar posiciones
            if (minIndex != i) {
                Empleado temp = lista.get(i);
                lista.set(i, lista.get(minIndex));
                lista.set(minIndex, temp);
            }
        }
        return lista;
    }
    
    // Color del Empleado segun su salario
    public Color getColorParaEmpleado(Empleado emp, Area area) {

        ArrayList<Empleado> empleados = new ArrayList<>(area.getIntegrantes());

        double min = empleados.stream().mapToDouble(Empleado::getSalario).min().orElse(0);
        double max = empleados.stream().mapToDouble(Empleado::getSalario).max().orElse(0);

        double factor = (emp.getSalario() - min) / (max - min + 0.0001);

        int azul = (int) (factor * 255);
        return new Color(0, 0, azul);
    }
    
    // Reporte de Movimientos:
    // Obtener movimientos ordenados por mes ascendente
    public ArrayList<Movimiento> getMovimientosOrdenados() {
        ArrayList<Movimiento> copia = new ArrayList<>(movimientos);

        int n = copia.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (copia.get(j).getMes() > copia.get(j + 1).getMes()) {
                    Movimiento aux = copia.get(j);
                    copia.set(j, copia.get(j + 1));
                    copia.set(j + 1, aux);
                }
            }
        }
        return copia;
    }
    
    // Filtro por criterios
    public ArrayList<Movimiento> filtrarMovimientos(Integer mes, Area origen, Area destino, Empleado empleado) {
        ArrayList<Movimiento> filtrados = new ArrayList<>();

        for (Movimiento m : movimientos) {
            boolean okMes = (mes == null || m.getMes() == mes);
            boolean okOrigen = (origen == null || m.getAreaOrigen().equals(origen));
            boolean okDestino = (destino == null || m.getAreaDestino().equals(destino));
            boolean okEmpleado = (empleado == null || m.getEmpleado().equals(empleado));

            if (okMes && okOrigen && okDestino && okEmpleado) {
                filtrados.add(m);
            }
        }
        return filtrados;
    }
    
    // Exportar a CSV
    public void exportarMovimientosCSV(ArrayList<Movimiento> movs, File archivo) throws Exception {

        FileWriter fw = new FileWriter(archivo);

        fw.write("Mes,Area Origen,Area Destino,Empleado\n");

        for (Movimiento m : movs) {
            fw.write(
                    m.getMes() + ","
                    + m.getAreaOrigen().getNombre() + ","
                    + m.getAreaDestino().getNombre() + ","
                    + m.getEmpleado().getNombre() + "\n"
            );
        }
        fw.close();
    }
}
