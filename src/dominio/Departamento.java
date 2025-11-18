/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;
import java.util.ArrayList;


/**
 *
 * @author Usuario
 */
public class Departamento {
    private static int contador_ids = 1;
    
    int id;
    String nombre;
    int presupuesto_anual;
    ArrayList<Empleado> integrantes = new ArrayList<>();
    String descripcion;
    
    public Departamento(int id, String nombre, int presupuesto_anual, String descripcion) {
        this.id = contador_ids;
        contador_ids++;
        
        this.nombre = nombre;
        this.presupuesto_anual = presupuesto_anual;
        this.descripcion = descripcion;
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

    public int getPresupuesto_anual() {
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
