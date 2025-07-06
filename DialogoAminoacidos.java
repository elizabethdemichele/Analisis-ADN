package vista;

import modelo.Aminoacido;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DialogoAminoacidos extends JDialog {
    public DialogoAminoacidos(JFrame parent, Map<Aminoacido, Integer> frecuencias, 
                            Map<Aminoacido, List<String>> tripletasPorAminoacido) {
        super(parent, "Frecuencias de Aminoácidos", true);
        configurarInterfaz(frecuencias, tripletasPorAminoacido);
    }

    private void configurarInterfaz(Map<Aminoacido, Integer> frecuencias, Map<Aminoacido, List<String>> tripletasPorAminoacido) {
        setSize(800, 600);
        setLocationRelativeTo(getParent());

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JTabbedPane pestanas = new JTabbedPane();

        // Pestaña de frecuencias
        JPanel panelFrecuencias = new JPanel(new BorderLayout());
        JTextArea areaFrecuencias = new JTextArea();
        areaFrecuencias.setEditable(false);

        // Solución: Usar un StringBuilder final para la primera parte
        final StringBuilder sbFrecuencias = new StringBuilder();
        frecuencias.entrySet().stream()
            .sorted(Map.Entry.<Aminoacido, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                Aminoacido aa = entry.getKey();
                sbFrecuencias.append(String.format("%-15s (%s/%s): %d ocurrencias%n",
                    aa.getNombre(),
                    aa.getAbreviatura3Letras(),
                    aa.getAbreviatura1Letra(),
                    entry.getValue()));

                List<String> tripletas = tripletasPorAminoacido.get(aa);
                if (tripletas != null && !tripletas.isEmpty()) {
                    sbFrecuencias.append("  Tripletas: ").append(String.join(", ", tripletas)).append("\n");
                }
                sbFrecuencias.append("\n");
            });

        areaFrecuencias.setText(sbFrecuencias.toString());
        panelFrecuencias.add(new JScrollPane(areaFrecuencias), BorderLayout.CENTER);
        pestanas.addTab("Frecuencias", panelFrecuencias);

        // Pestaña de tripletas
        JPanel panelTripletas = new JPanel(new BorderLayout());
        JTextArea areaTripletas = new JTextArea();
        areaTripletas.setEditable(false);

        // Solución: Usar un StringBuilder separado para la segunda parte
        StringBuilder sbTripletas = new StringBuilder();
        tripletasPorAminoacido.forEach((aa, tripletas) -> {
            sbTripletas.append(String.format("%-15s (%s/%s):%n",
                aa.getNombre(),
                aa.getAbreviatura3Letras(),
                aa.getAbreviatura1Letra()));

            sbTripletas.append("  Tripletas: ").append(String.join(", ", tripletas)).append("\n");
            sbTripletas.append("  Frecuencia total: ").append(frecuencias.getOrDefault(aa, 0)).append("\n\n");
        });

        areaTripletas.setText(sbTripletas.toString());
        panelTripletas.add(new JScrollPane(areaTripletas), BorderLayout.CENTER);
        pestanas.addTab("Tripletas", panelTripletas);

        panelPrincipal.add(pestanas, BorderLayout.CENTER);
        add(panelPrincipal);
    }
}
