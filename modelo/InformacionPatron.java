package modelo;

/**
 *
 * @author elidemichele
 * 
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que almacena la información de un patrón de ADN
 */
public class InformacionPatron implements Comparable<InformacionPatron> {
    private final String patron;
    private final List<Integer> posiciones;
    private int frecuencia;

    /**
     * Constructor
     * @param patron Secuencia de nucleótidos
     */
    public InformacionPatron(String patron) {
        this.patron = patron;
        this.posiciones = new ArrayList<>();
        this.frecuencia = 0;
    }

    /**
     * Agrega una posición donde se encontró el patrón
     * @param posicion Posición en la secuencia principal
     */
    public void agregarPosicion(int posicion) {
        posiciones.add(posicion);
        frecuencia++;
    }

    // Getters
    public String getPatron() { return patron; }
    public List<Integer> getPosiciones() { return posiciones; }
    public int getFrecuencia() { return frecuencia; }

    /**
     * Compara patrones por frecuencia (para ordenamiento)
     */
    @Override
    public int compareTo(InformacionPatron otro) {
        return Integer.compare(otro.frecuencia, this.frecuencia);
    }

    @Override
    public String toString() {
        return "Patrón: " + patron + ", Frecuencia: " + frecuencia + 
               ", Posiciones: " + posiciones.toString();
    }
}
