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
    private Departamento departamento;
    private Curriculum curriculum;
    private String ci;
    private int telefono;
    private int antiguedad;
    
    
    Empleado(String nombre, int salario, Departamento departamento, Curriculum curriculum, String ci, int telefono, int antiguedad) {
        this.id = contador_ids;
        contador_ids++;
        
        this.nombre = nombre;
        this.departamento = departamento;
        this.curriculum = curriculum;
        this.salario = salario;
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

    public int getSalario() {
        return salario;
    }

    public Departamento getDepartamento() {
        return departamento;
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

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
    
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
}
