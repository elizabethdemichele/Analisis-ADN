package modelo;

/**
 *
 * @author elidemichele
 * 
 */


import java.util.List;
import java.util.ArrayList;


/**
 * Clase que representa un aminoácido y sus tripletas asociadas
 */
public class Aminoacido {
    private final String nombre;
    private final String abreviatura3Letras;
    private final String abreviatura1Letra;
    private final List<String> tripletasADN;

    public Aminoacido(String nombre, String abreviatura3Letras, String abreviatura1Letra) {
        this.nombre = nombre;
        this.abreviatura3Letras = abreviatura3Letras;
        this.abreviatura1Letra = abreviatura1Letra;
        this.tripletasADN = new ArrayList<>();
    }

    public void agregarTripleta(String tripleta) {
        tripletasADN.add(tripleta);
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getAbreviatura3Letras() { return abreviatura3Letras; }
    public String getAbreviatura1Letra() { return abreviatura1Letra; }
    public List<String> getTripletasADN() { return tripletasADN; }

    @Override
    public String toString() {
        return nombre + " (" + abreviatura3Letras + "/" + abreviatura1Letra + ") - Tripletas: " + tripletasADN;
    }
}