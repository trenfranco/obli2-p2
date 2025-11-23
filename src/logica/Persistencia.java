package logica;

import java.io.*;
/**
 *
 * @author matim
 */
public class Persistencia {
    private static final String ARCHIVO = "datos/sistema.dat";
    
    public static void guardarSistema(Sistema sistema) {
        try {
            File carpeta = new File("datos");
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdir();
                System.out.println("Carpeta datos creada: " + " en " + carpeta.getAbsolutePath());
            }
            
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO));
            oos.writeObject(sistema);
            oos.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Sistema cargarSistema() {
        try {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) {
                return null;
            }
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO));
            Sistema sistema = (Sistema) ois.readObject();
            ois.close();
            return sistema;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null; // Si se llega a una expecion devolver null porque el archivo esta corrupto o no existe.
        }
    }
}
