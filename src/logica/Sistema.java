/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    /** Crear metodo agregarArea. Tiene que chequear la existencia por area.nombre en el ArrayList areas.
     * Si no existe se agrega, sino devuelve el error.
     */
    public boolean agregarArea(Area a) {
        return true;
    }
    
    public ArrayList<Area> getAreas() {
        return areas;
    }
}
