/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author matim
 * @author franco
 */
public class ArchivosCV {

    private static final String CARPETA_CVS = "cvs";

    /**
     * Guarda el CV de un empleado en un archivo de texto
     * @param cedula La cédula del empleado
     * @param textoCV El texto del curriculum
     * @return true si se guardó correctamente, false si hubo un error
     */
    public static boolean guardarCV(String cedula, String textoCV) {
        try {
            File carpeta = new File(CARPETA_CVS);
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdir();
                System.out.println("Carpeta cvs creada: " + creada + " en " + carpeta.getAbsolutePath());
            }

            String nombreArchivo = CARPETA_CVS + File.separator + "CV" + cedula + ".txt";
            File archivo = new File(nombreArchivo);

            FileWriter writer = new FileWriter(archivo);
            writer.write(textoCV);
            writer.close();

            System.out.println("CV guardado exitosamente en: " + archivo.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el CV: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
