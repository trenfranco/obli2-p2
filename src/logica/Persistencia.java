package logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author matim
 */
public class Persistencia {
    private static String ARCHIVO = "sistema.dat";
    
    public static void guardarSistema(Sistema sistema) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(sistema);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Sistema cargarSistema() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (Sistema) ois.readObject();
        } catch (Exception e) {
            return null; // Si se llega a una expecion devolver null porque el archivo esta corrupto o no existe.
        }
    }
}
