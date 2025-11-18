package dominio;

import java.util.ArrayList;

public class Manager {

    private static int contador_ids = 1;

    private int id;
    private String nombre;
    private String ci;
    private int telefono;
    private int antiguedad;
    private ArrayList<Empleado> empleadosACargo = new ArrayList<>();

    public Manager(String nombre, String ci, int telefono, int antiguedad) {
        this.id = contador_ids;
        contador_ids++;

        this.nombre = nombre;
        this.ci = ci;
        this.telefono = telefono;
        this.antiguedad = antiguedad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCi() {
        return ci;
    }

    public int getTelefono() {
        return telefono;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public ArrayList<Empleado> getEmpleadosACargo() {
        return empleadosACargo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setEmpleadosACargo(ArrayList<Empleado> empleadosACargo) {
        this.empleadosACargo = empleadosACargo;
    }
}
