package tests;

import dominio.Area;
import dominio.Manager;
import dominio.Empleado;
import logica.Sistema;

public class TestSistema {
    public static void main(String[] args) {

        Sistema sistema = new Sistema();

        System.out.println("===== PRUEBA DE AREAS =====");

        Area a1 = new Area("Marketing", 90000, "Publicidad, redes sociales");
        Area a2 = new Area("RRHH", 80000, "Recursos humanos");
        Area a3 = new Area("Seguridad", 120000, "Seguridad física y digital");
        Area a4 = new Area("Marketing", 50000, "Duplicado para probar");

        // Agregar áreas
        System.out.println("Agregar Marketing: " + sistema.agregarArea(a1));  // true
        System.out.println("Agregar RRHH: " + sistema.agregarArea(a2));       // true
        System.out.println("Agregar Seguridad: " + sistema.agregarArea(a3));  // true
        System.out.println("Agregar Marketing duplicado: " + sistema.agregarArea(a4)); // false

        System.out.println("\n===== AREAS REGISTRADAS =====");
        for (Area area : sistema.getAreas()) {
            System.out.println(area.getNombre());
        }

        // Probar ordenar
        System.out.println("\n===== AREAS ORDENADAS POR NOMBRE =====");
        for (Area area : sistema.getAreasOrdenadas()) {
            System.out.println(area.getNombre());
        }

        // Probar obtener por nombre
        System.out.println("\n===== BUSCAR AREA POR NOMBRE =====");
        Area buscada = sistema.getAreaPorNombre("rrhh");
        if (buscada != null) {
            System.out.println("Encontrada: " + buscada.getNombre());
        } else {
            System.out.println("No se encontró el área.");
        }

        // Probar eliminar área
        System.out.println("\n===== ELIMINAR AREA =====");
        boolean eliminada = sistema.eliminarArea(a2);
        System.out.println("RRHH eliminada: " + eliminada);

        System.out.println("\n===== AREAS DESPUES DE ELIMINAR =====");
        for (Area area : sistema.getAreas()) {
            System.out.println(area.getNombre());
        }

        System.out.println("\n===== PRUEBAS COMPLETADAS =====");
    }
}
