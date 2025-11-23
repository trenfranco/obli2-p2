package tests;

import dominio.Area;
import dominio.Manager;
import dominio.Empleado;
import dominio.Movimiento;
import dominio.Curriculum;
import logica.Sistema;

public class TestSistema {
    public static void main(String[] args) {

        Sistema sistema = new Sistema();

        System.out.println("===== PRUEBA DE AREAS =====");

        Area a1 = new Area("Marketing", "Publicidad, redes sociales", 10000);
        Area a2 = new Area("RRHH", "Recursos humanos", 80000);
        Area a3 = new Area("Seguridad", "Seguridad física y digital", 2000);
        Area a4 = new Area("Marketing", "Duplicado para probar", 50000);

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

                System.out.println("\n===== PRUEBA: AREAS SIN EMPLEADOS =====");

        // Todas las áreas están sin empleados porque nunca agregamos empleados
        for (Area area : sistema.getAreasSinEmpleados()) {
            System.out.println("Sin empleados: " + area.getNombre());
        }



        System.out.println("\n===== PRUEBA: MODIFICAR DESCRIPCION DE AREA =====");

        Area areaMarketing = sistema.getAreaPorNombre("Marketing");
        boolean modificado = sistema.modificarDescripcionArea(areaMarketing, "Nueva desc de Marketing");

        System.out.println("¿Modificado?: " + modificado);
        System.out.println("Nueva descripción: " + areaMarketing.getDescripcion());

        
                System.out.println("\n\n===== PRUEBA DE MANAGERS =====");

        Manager m1 = new Manager("Ana", "111", 5, "1234415");
        Manager m2 = new Manager("Luis", "222", 10, "4564711");
        Manager m3 = new Manager("Maria", "333", 2, "45056561");

        // Agregar managers
        System.out.println("Agregar Ana: " + sistema.agregarManager(m1)); // true
        System.out.println("Agregar Luis: " + sistema.agregarManager(m2)); // true
        System.out.println("Agregar Maria: " + sistema.agregarManager(m3)); // true

        System.out.println("\n===== MANAGERS ORDENADOS POR ANTIGÜEDAD =====");
        for (Manager m : sistema.getManagersOrdenados()) {
            System.out.println(m.getNombre() + " - Antiguedad: " + m.getAntiguedad());
        }

        System.out.println("\n===== MANAGERS SIN EMPLEADOS =====");
        //m1.agregarEmpleado()
        for (Manager m : sistema.getManagersSinEmpleados()) {
            System.out.println("Sin empleados: " + m.getNombre());
        }

        System.out.println("\n===== ELIMINAR MANAGER =====");

        // Como todos los managers tienen 0 empleados, la eliminación debería funcionar
        boolean eliminadoManager = sistema.eliminarManager(m2);
        System.out.println("¿Luis eliminado?: " + eliminadoManager);

        System.out.println("Managers restantes:");
        for (Manager m : sistema.getManagers()) {
            System.out.println(m.getNombre());
        }
        
        
        System.out.println("\n===== TEST EMPLEADOS =====");

        Empleado e1 = new Empleado("Ana", 110, a1, "Hola coom estas", "52424002", "9088282", 3, m1);
        Empleado e2 = new Empleado("Luis", 105, a1, "Chhau cajajhcs", "59943", "99282", 1, m2);
        Empleado e3 = new Empleado("Mati", 50, a3, "asdas asd asad s", "455242", "123456", 10, m3);
        
        System.out.println("Agregar Juan: " + sistema.agregarEmpleado(e1));
        System.out.println("Agregar Euge: " + sistema.agregarEmpleado(e2));
        System.out.println("Agregar Mati: " + sistema.agregarEmpleado(e3));

        a1.agregarEmpleado(e1);
        a1.agregarEmpleado(e2);
        a3.agregarEmpleado(e3);

        System.out.println("\nEmpleados cargados en el sistema:");
        for (Empleado e : sistema.getEmpleados()) {
            System.out.println(e.getNombre() + " - $" + e.getSalario());
        }

        System.out.println("\n===== EMPLEADOS ORDENADOS POR SALARIO =====");
        for (Empleado e : sistema.getEmpleadosOrdenados()) {
            System.out.println(e.getNombre() + " - $" + e.getSalario());
        }
        
        System.out.println("\n===== TEST MOVIMIENTO =====");
        
        Movimiento mov1 = new Movimiento(9, a1, a3, e2);
        System.out.println("Presupuesto de Marketing: " + a1.getPresupuestoAnual());
        System.out.println("Presupuesto de Seguridad: " + a3.getPresupuestoAnual());
        
        sistema.ejecutarMovimiento(mov1);
        
        System.out.println("Presupuesto de Marketing luego del movimiento: " + a1.getPresupuestoAnual());
        System.out.println("Presupuesto de Seguridad luego del movimiento: " + a3.getPresupuestoAnual());
        
        System.out.println("\n===== INTEGRANTES DESPUÉS DEL MOVIMIENTO =====");

        System.out.println("\nIntegrantes de " + a1.getNombre() + ":");
        for (Empleado emp : a1.getIntegrantes()) {
            System.out.println(" - " + emp.getNombre());
        }

        System.out.println("\nIntegrantes de " + a3.getNombre() + ":");
        for (Empleado emp : a3.getIntegrantes()) {
            System.out.println(" - " + emp.getNombre());
        }
        
        System.out.println("\n===== PRUEBAS COMPLETADAS =====");
    }
}
