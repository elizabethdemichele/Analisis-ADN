package modelo;

/**
 *
 * @author elidemichele
 * 
 */

import java.util.List;
import java.util.ArrayList;

/**
 * Implementación de un árbol binario de búsqueda para ordenar patrones por frecuencia
 */
public class ArbolBinarioBusqueda {
    private Nodo raiz;

    private class Nodo {
        InformacionPatron dato;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(InformacionPatron dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    /**
     * Inserta un nuevo patrón en el árbol
     * @param info Información del patrón a insertar
     */
    public void insertar(InformacionPatron info) {
        raiz = insertarRec(raiz, info);
    }

    private Nodo insertarRec(Nodo nodo, InformacionPatron info) {
        if (nodo == null) {
            return new Nodo(info);
        }

        // Comparar por frecuencia (orden descendente)
        if (info.getFrecuencia() < nodo.dato.getFrecuencia()) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, info);
        } else {
            nodo.derecho = insertarRec(nodo.derecho, info);
        }

        return nodo;
    }

    /**
     * Obtiene los patrones ordenados por frecuencia (de mayor a menor)
     * @return Lista de patrones ordenados
     */
    public List<InformacionPatron> obtenerPatronesOrdenados() {
        List<InformacionPatron> resultado = new ArrayList<>();
        inordenInverso(raiz, resultado);
        return resultado;
    }

    private void inordenInverso(Nodo nodo, List<InformacionPatron> resultado) {
        if (nodo != null) {
            inordenInverso(nodo.derecho, resultado);
            resultado.add(nodo.dato);
            inordenInverso(nodo.izquierdo, resultado);
        }
    }

    /**
     * Obtiene el patrón con mayor frecuencia (CORRECCIÓN: ahora busca correctamente el más frecuente)
     * @return Patrón más frecuente
     */
    public InformacionPatron obtenerPatronMasFrecuente() {
        if (raiz == null) return null;
        Nodo actual = raiz;
        // El más frecuente está más a la derecha
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.dato;
    }

    /**
     * Obtiene el patrón con menor frecuencia (CORRECCIÓN: ahora busca correctamente el menos frecuente)
     * @return Patrón menos frecuente
     */
    public InformacionPatron obtenerPatronMenosFrecuente() {
        if (raiz == null) return null;
        Nodo actual = raiz;
        // El menos frecuente está más a la izquierda
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.dato;
    }
}