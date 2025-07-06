package vista;

/**
 *
 * @author elidemichele
 * 
 */

import controlador.ControladorADN;
import modelo.InformacionPatron;
import modelo.Aminoacido;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

/**
 * Ventana principal de la aplicación
 */
public class VentanaPrincipal extends JFrame {
    private final ControladorADN controlador;
    private JTextArea areaTexto;
    private JComboBox<String> comboPatrones;

    public VentanaPrincipal() {
        super("Análisis de Secuencias de ADN");
        this.controlador = new ControladorADN(this);
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Botón para cargar archivo
        JButton btnCargar = new JButton("Cargar Archivo ADN");
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });
        panelBotones.add(btnCargar);
        
        // Botón para listar patrones
        JButton btnListar = new JButton("Listar Patrones");
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPatrones();
            }
        });
        panelBotones.add(btnListar);
        
        // Combo para buscar patrones
        comboPatrones = new JComboBox<>();
        comboPatrones.setEditable(true);
        comboPatrones.setPreferredSize(new Dimension(150, 25));
        JButton btnBuscar = new JButton("Buscar Patrón");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPatron();
            }
        });
        panelBotones.add(new JLabel("Buscar:"));
        panelBotones.add(comboPatrones);
        panelBotones.add(btnBuscar);
        
        // Botón para patrón más frecuente
        JButton btnMasFrecuente = new JButton("Patrón Más Frecuente");
        btnMasFrecuente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPatronMasFrecuente();
            }
        });
        panelBotones.add(btnMasFrecuente);
        
        // Botón para patrón menos frecuente
        JButton btnMenosFrecuente = new JButton("Patrón Menos Frecuente");
        btnMenosFrecuente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPatronMenosFrecuente();
            }
        });
        panelBotones.add(btnMenosFrecuente);
        
        // Botón para reporte de colisiones
        JButton btnColisiones = new JButton("Reporte de Colisiones");
        btnColisiones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarReporteColisiones();
            }
        });
        panelBotones.add(btnColisiones);
        
        // Botón para frecuencias de aminoácidos
        JButton btnAminoacidos = new JButton("Frecuencias Aminoácidos");
        btnAminoacidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFrecuenciasAminoacidos();
            }
        });
        panelBotones.add(btnAminoacidos);
        
        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        
        // Área de texto para resultados
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }

    private void cargarArchivo() {
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int resultado = selectorArchivo.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorArchivo.getSelectedFile();
            controlador.cargarArchivoADN(archivo);
            
            // Actualizar combo box con los patrones
            comboPatrones.removeAllItems();
            for (InformacionPatron info : controlador.obtenerPatronesOrdenados()) {
                comboPatrones.addItem(info.getPatron());
            }
        }
    }

    private void listarPatrones() {
        // Revisar que se haya cargado un archivo
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Error: Primero debe cargar un archivo con datos de ADN");
            return;
        }
        
        areaTexto.setText("");
        List<InformacionPatron> patrones = controlador.obtenerPatronesOrdenados();
        
        if (patrones.isEmpty()) {
            areaTexto.append("No hay patrones cargados. Por favor, cargue un archivo primero.");
            return;
        }
        
        areaTexto.append("Patrones ordenados por frecuencia:\n\n");
        for (InformacionPatron info : patrones) {
            areaTexto.append(info.toString() + "\n");
        }
    }

    private void buscarPatron() {
        // Revisar que se haya cargado un archivo
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Error: Primero debe cargar un archivo con datos de ADN");
            return;
        }

        String patron = (String) comboPatrones.getSelectedItem();
        if (patron == null || patron.trim().isEmpty()) {
            mostrarMensaje("Por favor, ingrese un patrón válido de 3 nucleótidos (A, T, C, G)");
            return;
        }
        
        if (patron.length() != 3) {
            mostrarMensaje("El patrón debe tener exactamente 3 nucleótidos");
            return;
        }
        
        InformacionPatron info = controlador.buscarPatron(patron);
        if (info == null) {
            mostrarMensaje("El patrón '" + patron + "' no fue encontrado en la secuencia");
        } else {
            new DialogoPatron(this, info).setVisible(true);
        }
    }

    private void mostrarPatronMasFrecuente() {
        // Revisar que se haya cargado un archivo
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Error: Primero debe cargar un archivo con datos de ADN");
            return;
        }

        InformacionPatron info = controlador.obtenerPatronMasFrecuente();
        if (info == null) {
            mostrarMensaje("No hay patrones cargados. Por favor, cargue un archivo primero.");
        } else {
            areaTexto.setText("Patrón más frecuente:\n\n" + info.toString());
        }
    }

    private void mostrarPatronMenosFrecuente() {
        // Revisar que se haya cargado un archivo
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Error: Primero debe cargar un archivo con datos de ADN");
            return;
        }

        InformacionPatron info = controlador.obtenerPatronMenosFrecuente();
        if (info == null) {
            mostrarMensaje("No hay patrones cargados. Por favor, cargue un archivo primero.");
        } else {
            areaTexto.setText("Patrón menos frecuente:\n\n" + info.toString());
        }
    }

    private void mostrarReporteColisiones() {
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Debe cargar un archivo con datos de ADN primero");
            return;
        }

        try {
            Map<Integer, List<String>> reporte = controlador.generarReporteColisionesDetallado();
            if (reporte.isEmpty()) {
                areaTexto.setText("No se encontraron colisiones en la tabla hash.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("REPORTE DETALLADO DE COLISIONES\n\n");
                sb.append("Total de colisiones: ").append(reporte.size()).append("\n\n");

                reporte.forEach((hash, patrones) -> {
                    sb.append("Hash: ").append(hash).append("\n");
                    sb.append("Patrones colisionados (").append(patrones.size()).append("):\n");
                    patrones.forEach(p -> sb.append("  - ").append(p).append("\n"));
                    sb.append("\n");
                });

                areaTexto.setText(sb.toString());
            }
        } catch (Exception e) {
            mostrarMensaje("Error al generar reporte de colisiones: " + e.getMessage());
        }
    }

    private void mostrarFrecuenciasAminoacidos() {
        if (!controlador.hayDatosCargados()) {
            mostrarMensaje("Debe cargar un archivo con datos de ADN primero");
            return;
        }

        Map<Aminoacido, List<InformacionPatron>> frecuencias = controlador.obtenerFrecuenciasAminoacidosCompleto();

        if (frecuencias.isEmpty()) {
            mostrarMensaje("No se encontraron datos de aminoácidos");
            return;
        }

        // Crear modelo para la tabla
        String[] columnNames = {"Aminoácido", "Abrev. 3 letras", "Abrev. 1 letra", "Tripletas", "Frecuencia Total"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Ordenar por frecuencia descendente
        frecuencias.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(
                e2.getValue().stream().mapToInt(InformacionPatron::getFrecuencia).sum(),
                e1.getValue().stream().mapToInt(InformacionPatron::getFrecuencia).sum()))
            .forEach(entry -> {
                Aminoacido aa = entry.getKey();
                List<InformacionPatron> patrones = entry.getValue();

                int frecuenciaTotal = patrones.stream().mapToInt(InformacionPatron::getFrecuencia).sum();
                String tripletas = patrones.stream()
                    .map(InformacionPatron::getPatron)
                    .collect(Collectors.joining(", "));

                model.addRow(new Object[]{
                    aa.getNombre(),
                    aa.getAbreviatura3Letras(),
                    aa.getAbreviatura1Letra(),
                    tripletas,
                    frecuenciaTotal
                });
            });

        // Mostrar en diálogo con tabla
        JDialog dialog = new JDialog(this, "Frecuencias de Aminoácidos", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
