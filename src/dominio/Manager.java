package dominio;

import java.util.ArrayList;

public class Manager {

    private static int contador_ids = 1;

    private int id;
    private String nombre;
    private String cedula;
    private String celular;
    private int antiguedad;
    private ArrayList<Empleado> empleadosACargo = new ArrayList<>();

    public Manager(String nombre, String ci, int antiguedad, String celular) {
        this.id = contador_ids;
        contador_ids++;

        this.nombre = nombre;
        this.cedula = ci;
        this.celular = celular;
        this.antiguedad = antiguedad;
    }
    
    public void agregarEmpleado(Empleado e){
        this.empleadosACargo.add(e);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCelular() {
        return celular;
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

    public void setCedula(String ci) {
        this.cedula = ci;
    }

    public void setCelular(String telefono) {
        this.celular = celular;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setEmpleadosACargo(ArrayList<Empleado> empleadosACargo) {
        this.empleadosACargo = empleadosACargo;
    }
}
