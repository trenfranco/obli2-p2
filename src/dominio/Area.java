/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;
import java.util.ArrayList;
import java.io.Serializable;


/**
 *
 * @author Usuario
 */
public class Area implements Serializable {
    private static int contador_ids = 1;
    
    int id;
    String nombre;
    int presupuesto_anual;
    ArrayList<Empleado> integrantes = new ArrayList<>();
    String descripcion;
    
    public Area(String nombre, int presupuesto_anual, String descripcion) {
        this.id = contador_ids;
        contador_ids++;
        
        this.nombre = nombre;
        this.presupuesto_anual = presupuesto_anual;
        this.descripcion = descripcion;
    }
    
    public void AgregarEmpleado(Empleado e) {
        this.integrantes.add(e);
    }
    
    public void EliminarEmpleado(Empleado e) {
        this.integrantes.remove(e);
    }
    
    public void agregarPresupuesto(int monto) {
        this.presupuesto_anual += monto;
    }
    
    public void restarPresupuesto(int monto) {
        this.presupuesto_anual -= monto;
    }
    
    public void agregarEmpleado(Empleado e) {
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

    public int getPresupuestoAnual() {
        return presupuesto_anual;
    }

    public void setPresupuesto_anual(int presupuesto_anual) {
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
}
