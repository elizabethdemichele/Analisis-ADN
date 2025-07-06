package controlador;

/**
 *
 * @author elidemichele
 * 
 */

import modelo.*;
import vista.VentanaPrincipal;

import modelo.InformacionPatron;
import modelo.TablaHash;
import modelo.ArbolBinarioBusqueda;
import modelo.Aminoacido;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Controlador principal que maneja la lógica de la aplicación
 */
public class ControladorADN {
    private final VentanaPrincipal vista;
    private TablaHash tablaPatrones;
    private ArbolBinarioBusqueda arbolPatrones;
    private final Map<String, Aminoacido> mapaAminoacidos;
    private String secuenciaADN;
    private final MapeadorAminoacidos mapeadorAminoacidos;

    public ControladorADN(VentanaPrincipal vista) {
        this.vista = vista;
        this.mapaAminoacidos = new HashMap<>();
        this.mapeadorAminoacidos = new MapeadorAminoacidos();
    }

    /**
     * Carga un archivo con una secuencia de ADN y procesa los patrones
     * @param archivo Archivo a cargar
     */
    public void cargarArchivoADN(File archivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            StringBuilder constructor = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Eliminar espacios y caracteres no válidos
                linea = linea.replaceAll("[^ATCG]", "").toUpperCase();
                constructor.append(linea);
            }
            secuenciaADN = constructor.toString();
            procesarSecuenciaADN();
            vista.mostrarMensaje("Archivo cargado correctamente. Secuencia de " + secuenciaADN.length() + " nucleótidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al leer el archivo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Procesa la secuencia de ADN para extraer patrones
     */
    private void procesarSecuenciaADN() {
        // Tamaño de la tabla hash: próximo primo mayor que el número esperado de patrones
        int tamañoTabla = siguientePrimo(secuenciaADN.length() / 3);
        tablaPatrones = new TablaHash(tamañoTabla);
        arbolPatrones = new ArbolBinarioBusqueda();

        // Extraer todos los patrones de 3 nucleótidos
        for (int i = 0; i <= secuenciaADN.length() - 3; i++) {
            String patron = secuenciaADN.substring(i, i + 3);
            tablaPatrones.insertar(patron, i);
        }

        // Insertar todos los patrones en el árbol para ordenarlos por frecuencia
        for (InformacionPatron info : tablaPatrones.obtenerTodosPatrones()) {
            arbolPatrones.insertar(info);
        }
    }

    /**
     * Encuentra el siguiente número primo mayor o igual a n
     */
    private int siguientePrimo(int n) {
        if (n <= 1) return 2;
        int candidato = n;
        while (!esPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }

    private boolean esPrimo(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }

    /**
     * Obtiene la lista de patrones ordenados por frecuencia
     */
    public List<InformacionPatron> obtenerPatronesOrdenados() {
        return arbolPatrones.obtenerPatronesOrdenados();
    }

    /**
     * Busca un patrón específico
     */
    public InformacionPatron buscarPatron(String patron) {
        return tablaPatrones.buscar(patron);
    }

    /**
     * Obtiene el patrón más frecuente
     */
    public InformacionPatron obtenerPatronMasFrecuente() {
        return arbolPatrones.obtenerPatronMasFrecuente();
    }

    /**
     * Obtiene el patrón menos frecuente
     */
    public InformacionPatron obtenerPatronMenosFrecuente() {
        return arbolPatrones.obtenerPatronMenosFrecuente();
    }

    /**
     * Obtiene el reporte de colisiones
     */
    public List<String> obtenerReporteColisiones() {
        return tablaPatrones.obtenerPatronesConColisiones();
    }

    /**
     * Obtiene el mapeo de aminoácidos con sus tripletas y frecuencias
     */
    public Map<Aminoacido, Integer> obtenerFrecuenciasAminoacidos() {
        Map<Aminoacido, Integer> frecuencias = new HashMap<>();
        
        for (InformacionPatron info : tablaPatrones.obtenerTodosPatrones()) {
            String tripleta = info.getPatron();
            Aminoacido aminoacido = encontrarAminoacidoPorTripleta(tripleta);
            
            if (aminoacido != null) {
                frecuencias.put(aminoacido, frecuencias.getOrDefault(aminoacido, 0) + info.getFrecuencia());
            }
        }
        
        return frecuencias;
    }

    /**
     * Encuentra el aminoácido correspondiente a una tripleta de ADN
     */
    private Aminoacido encontrarAminoacidoPorTripleta(String tripletaADN) {
        // Convertir a ARN (cambiar T por U)
        String tripletaARN = tripletaADN.replace('T', 'U');
        
        for (Aminoacido aminoacido : mapaAminoacidos.values()) {
            for (String tripleta : aminoacido.getTripletasADN()) {
                if (tripleta.equals(tripletaADN)) {
                    return aminoacido;
                }
            }
        }
        
        return null; // No es una tripleta válida
    }
    
    public boolean hayDatosCargados() {
        return secuenciaADN != null && !secuenciaADN.isEmpty() && tablaPatrones != null;
    }
    
    /**
    * Genera un reporte detallado de colisiones
    * @return Mapa con las cadenas hash como clave y lista de patrones que colisionaron
    */
   public Map<Integer, List<String>> generarReporteColisionesDetallado() {
       Map<Integer, List<String>> reporte = new HashMap<>();

       if (!hayDatosCargados()) {
           return reporte;
       }

       for (int i = 0; i < tablaPatrones.obtenerTamañoTabla(); i++) {
           LinkedList<InformacionPatron> lista = tablaPatrones.obtenerLista(i);
           if (lista != null && lista.size() > 1) {
               List<String> patronesColisionados = new ArrayList<>();
               for (InformacionPatron info : lista) {
                   patronesColisionados.add(info.getPatron());
               }
               reporte.put(i, patronesColisionados);
           }
       }

       return reporte;
   }
   
   public Map<Aminoacido, List<String>> obtenerTripletasPorAminoacido() {
        return mapeadorAminoacidos.getTripletasPorAminoacido();
    }
   
   public Map<Aminoacido, List<InformacionPatron>> obtenerFrecuenciasAminoacidosCompleto() {
        Map<Aminoacido, List<InformacionPatron>> resultado = new HashMap<>();

        if (!hayDatosCargados()) {
            return resultado;
        }

        // Procesar todos los patrones
        for (InformacionPatron patron : tablaPatrones.obtenerTodosPatrones()) {
            Aminoacido aminoacido = mapeadorAminoacidos.obtenerAminoacido(patron.getPatron());

            if (!resultado.containsKey(aminoacido)) {
                resultado.put(aminoacido, new ArrayList<>());
            }
            resultado.get(aminoacido).add(patron);
        }

        return resultado;
    }
   
}
