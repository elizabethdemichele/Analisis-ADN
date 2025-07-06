package vista;

/**
 *
 * @author elidemichele
 * 
 */

import modelo.InformacionPatron;
import javax.swing.*;
import java.awt.*;

/**
 * Diálogo que muestra la información detallada de un patrón de ADN
 */
public class DialogoPatron extends JDialog {
    private final InformacionPatron informacion;

    /**
     * Constructor del diálogo
     * @param parent Ventana padre
     * @param informacion Información del patrón a mostrar
     */
    public DialogoPatron(JFrame parent, InformacionPatron informacion) {
        super(parent, "Información del Patrón", true);
        this.informacion = informacion;
        configurarInterfaz();
    }

    /**
     * Configura los componentes de la interfaz del diálogo
     */
    private void configurarInterfaz() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JLabel lblPatron = new JLabel("Patrón: " + informacion.getPatron());
        JLabel lblFrecuencia = new JLabel("Frecuencia: " + informacion.getFrecuencia());
        
        panelInfo.add(lblPatron);
        panelInfo.add(lblFrecuencia);
        panelInfo.add(new JLabel("Posiciones en la secuencia:"));
        
        // Área de texto para las posiciones
        JTextArea areaPosiciones = new JTextArea();
        areaPosiciones.setEditable(false);
        StringBuilder posicionesStr = new StringBuilder();
        
        for (int pos : informacion.getPosiciones()) {
            posicionesStr.append(pos).append(", ");
        }
        
        // Eliminar la última coma y espacio
        if (posicionesStr.length() > 0) {
            posicionesStr.setLength(posicionesStr.length() - 2);
        }
        
        areaPosiciones.setText(posicionesStr.toString());
        JScrollPane scrollPosiciones = new JScrollPane(areaPosiciones);
        panelInfo.add(scrollPosiciones);

        panelPrincipal.add(panelInfo, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Muestra el diálogo con la información del patrón
     */
    public void mostrarInformacion() {
        setVisible(true);
    }
}
