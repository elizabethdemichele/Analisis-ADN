package modelo;

/**
 *
 * @author elidemichele
 * 
 */

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implementación de una tabla hash con manejo de colisiones por encadenamiento
 */
public class TablaHash {
    private final LinkedList<InformacionPatron>[] tabla;
    private final int tamaño;
    private int contadorColisiones;

    /**
     * Constructor para inicializar la tabla hash
     * @param tamaño Tamaño de la tabla hash
     */
    @SuppressWarnings("unchecked")
    public TablaHash(int tamaño) {
        this.tamaño = tamaño;
        this.tabla = new LinkedList[tamaño];
        this.contadorColisiones = 0;
        
        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    /**
     * Función hash personalizada para cadenas de ADN
     * @param clave Cadena de ADN a hashear
     * @return Valor hash calculado
     */
    private int funcionHash(String clave) {
        int hash = 0;
        // Usamos un polinomio para reducir colisiones
        for (int i = 0; i < clave.length(); i++) {
            char nucleotido = clave.charAt(i);
            int valorNucleotido = 0;
            switch (nucleotido) {
                case 'A': valorNucleotido = 1; break;
                case 'T': valorNucleotido = 2; break;
                case 'C': valorNucleotido = 3; break;
                case 'G': valorNucleotido = 4; break;
            }
            hash = (hash * 31 + valorNucleotido) % tamaño;
        }
        return Math.abs(hash);
    }

    /**
     * Inserta un patrón en la tabla hash
     * @param patron Patrón de ADN a insertar
     * @param posicion Posición en la secuencia principal
     */
    public void insertar(String patron, int posicion) {
        int indice = funcionHash(patron);
        LinkedList<InformacionPatron> lista = tabla[indice];
        
        // Verificar si ya existe el patrón
        boolean existe = false;
        for (InformacionPatron info : lista) {
            if (info.getPatron().equals(patron)) {
                info.agregarPosicion(posicion);
                existe = true;
                break;
            }
        }
        
        // Si no existe, crear nueva entrada
        if (!existe) {
            if (!lista.isEmpty()) {
                contadorColisiones++;
            }
            InformacionPatron nuevaInfo = new InformacionPatron(patron);
            nuevaInfo.agregarPosicion(posicion);
            lista.add(nuevaInfo);
        }
    }

    /**
     * Busca un patrón en la tabla hash
     * @param patron Patrón a buscar
     * @return Información del patrón o null si no se encuentra
     */
    public InformacionPatron buscar(String patron) {
        int indice = funcionHash(patron);
        for (InformacionPatron info : tabla[indice]) {
            if (info.getPatron().equals(patron)) {
                return info;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los patrones almacenados en la tabla
     * @return Lista de todos los patrones con su información
     */
    public ArrayList<InformacionPatron> obtenerTodosPatrones() {
        ArrayList<InformacionPatron> todosPatrones = new ArrayList<>();
        for (LinkedList<InformacionPatron> lista : tabla) {
            todosPatrones.addAll(lista);
        }
        return todosPatrones;
    }

    /**
     * Obtiene el número de colisiones registradas
     * @return Contador de colisiones
     */
    public int obtenerContadorColisiones() {
        return contadorColisiones;
    }

    /**
     * Obtiene los patrones que han causado colisiones
     * @return Lista de patrones con colisiones
     */
    public ArrayList<String> obtenerPatronesConColisiones() {
        ArrayList<String> patronesConColisiones = new ArrayList<>();
        for (LinkedList<InformacionPatron> lista : tabla) {
            if (lista.size() > 1) {
                for (InformacionPatron info : lista) {
                    patronesConColisiones.add(info.getPatron());
                }
            }
        }
        return patronesConColisiones;
    }
}
