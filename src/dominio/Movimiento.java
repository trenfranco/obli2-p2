/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;
import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Movimiento implements Serializable{
   
    private int mes;
    private Area areaOrigen;
    private Area areaDestino;
    private Empleado empleado;

    public Movimiento(int mes, Area areaOrigen, Area areaDestino, Empleado empleado) {
        this.mes = mes;
        this.areaOrigen = areaOrigen;
        this.areaDestino = areaDestino;
        this.empleado = empleado;
    }
    
    public static int CalcularPresupuestoNecesario(int sueldo_empleado, int mes) {
        return sueldo_empleado * (12 - mes + 1);
    }

    public int getMes() {
        return mes;
    }

    public Area getAreaOrigen() {
        return areaOrigen;
    }

    public Area getAreaDestino() {
        return areaDestino;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAreaOrigen(Area areaOrigen) {
        this.areaOrigen = areaOrigen;
    }

    public void setAreaDestino(Area areaDestino) {
        this.areaDestino = areaDestino;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Mes: " + mes +
                ", Origen: " + areaOrigen.getNombre() +
                ", Destino: " + areaDestino.getNombre() +
                ", Empleado: " + empleado.getNombre();
    }
}
    
