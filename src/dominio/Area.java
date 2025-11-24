package dominio;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Clase Area
 * Representa un área dentro de la organización, con un presupuesto anual,
 * descripción, identificador automático y una lista de empleados asociados.
 * Permite agregar o quitar empleados, ajustar el presupuesto y obtener
 * información relevante para reportes.
 *
 * @author Matias Martinez 282558 - Franco Trenche 368637
 *
 * @param nombre Nombre del área.
 * @param descripcion Descripción general del área.
 * @param presupuesto_anual Presupuesto anual asignado al área.
 */
public class Area implements Serializable {
    private static int contador_ids = 1;
    
    int id;
    String nombre;
    double presupuesto_anual;
    ArrayList<Empleado> integrantes = new ArrayList<>();
    String descripcion;
    
    public Area(String nombre, String descripcion, double presupuesto_anual) {
        this.id = contador_ids;
        contador_ids++;
        
        this.nombre = nombre;
        this.presupuesto_anual = presupuesto_anual;
        this.descripcion = descripcion;
    }
    
    public double calcularPresupuestoAnual() {
        //unused
        double total = this.presupuesto_anual;
        
        for (Empleado e: this.integrantes) {
            total -= e.getSalario() * 12;
        }
        return total;
    }
    
    public void EliminarEmpleado(Empleado e) {
        this.integrantes.remove(e);
    }
    
    public void agregarPresupuesto(double monto) {
        this.presupuesto_anual += monto;
    }
    
    public void restarPresupuesto(double monto) {
        this.presupuesto_anual -= monto;
    }
    
    public void agregarEmpleado(Empleado e) {
        this.agregarEmpleado(e, false);
    }
    
    public void agregarEmpleado(Empleado e, boolean esMovimiento) {
        if (!esMovimiento)
            this.presupuesto_anual -= e.getSalario() * 12;
        
        this.integrantes.add(e);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPresupuestoAnual() {
        return presupuesto_anual;
    }

    public void setPresupuesto_anual(double presupuesto_anual) {
        this.presupuesto_anual = presupuesto_anual;
    }

    public ArrayList<Empleado> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(ArrayList<Empleado> integrantes) {
        this.integrantes = integrantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
