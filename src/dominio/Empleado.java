/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Usuario
 */
public class Empleado {
    private static int contador_ids = 1;
    
    private int id;
    private String nombre;
    private int salario;
    private Area area;
    private Manager manager;
    private Curriculum curriculum;
    private String cedula;
    private String celular;
    private int antiguedad;
    private int mes_entrada;
    
    
    Empleado(String nombre, int salario, Area area, Curriculum curriculum, String ci, String cel, int antiguedad, Manager manager) {
        this.id = contador_ids;
        contador_ids++;
        
        this.nombre = nombre;
        this.area = area;
        this.curriculum = curriculum;
        this.salario = salario;
        this.cedula = ci;
        this.celular = cel;
        this.antiguedad = antiguedad;
        this.manager = manager;
        this.mes_entrada = 1;
        
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

    public int getSalario() {
        return salario;
    }

    public Area getArea() {
        return area;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    // -------- Setters --------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
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

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
    
    public Manager getManager(){
        return manager;
    }
    
    public void setManager(Manager m) {
        this.manager = m;
    }
}
