package dominio;

import java.io.Serializable;

/**
 *
 * @author Matías Martínez y Franco Trenche
 */
public class Area implements Serializable {
    private String nombre;
    private String descripcion;
    private double presupuesto;
    
    public Area(String nombre, String descripcion, double presupuesto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuesto = presupuesto;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public double getPresupuesto() {
        return this.presupuesto;
    }
    // getters/setters
    // toString
}
