package dominio;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Curriculum implements Serializable {
    private String texto;

    public Curriculum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}

