/**
 *
 * @author elidemichele
 * 
 */

import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación
 */
public class Aplicacion {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
