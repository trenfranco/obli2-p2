package dominio;

import utilidades.ArchivosCV;
import java.io.Serializable;


public class Empleado implements Serializable {
    private static int contador_ids = 1;
    
    private int id;
    private String nombre;
    private double salario;
    private Area area;
    private Manager manager;
    private String texto_curriculum;
    private String cedula;
    private String celular;;
    private int mes_entrada;
    

    public Empleado(String nombre, double salario, Area area, String texto_curriculum, String ci, String cel, int antiguedad, Manager manager) {

        this.id = contador_ids;
        contador_ids++;

        this.nombre = nombre;
        this.area = area;
        this.texto_curriculum = texto_curriculum;
        this.salario = salario;
        this.cedula = ci;
        this.celular = cel;
        this.manager = manager;
        this.mes_entrada = 1;

        if (texto_curriculum != null && !texto_curriculum.isEmpty()) {
            ArchivosCV.guardarCV(ci, texto_curriculum);
        }
    }
    
    public int getMesEntrada() {
        return mes_entrada;
    }
    
    public void setMesEntrada(int i) {
        this.mes_entrada = i;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }

    public Area getArea() {
        return area;
    }

    public String getTextoCurriculum() {
        return texto_curriculum;
    }

    // -------- Setters --------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setTextoCurriculum(String txt) {
        this.texto_curriculum = txt;
    }
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String ci) {
        this.cedula = ci;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String cel) {
        this.celular = cel;
    }

    public Manager getManager(){
        return manager;
    }
    
    public void setManager(Manager m) {
        this.manager = m;
    }
    
    // toString para mostrar info en tabla de reporte.
    @Override
    public String toString() {
        return nombre + " (" + cedula + ")";
    }
}