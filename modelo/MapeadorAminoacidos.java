package modelo;

import java.util.*;

/**
 * Clase para manejar el mapeo completo entre tripletas de ADN y aminoácidos
 */
public class MapeadorAminoacidos {
    private final Map<String, Aminoacido> tripletaAAminoacido;
    private final Map<Aminoacido, Integer> frecuenciasAminoacidos;

    public MapeadorAminoacidos() {
        this.tripletaAAminoacido = new HashMap<>();
        this.frecuenciasAminoacidos = new HashMap<>();
        inicializarMapeoCompleto();
    }

    private void inicializarMapeoCompleto() {
        // Aminoácidos con sus tripletas correspondientes
        
        // Fenilalanina (Phe/F)
        agregarMapeo("TTT", "Fenilalanina", "Phe", "F");
        agregarMapeo("TTC", "Fenilalanina", "Phe", "F");
        
        // Leucina (Leu/L)
        agregarMapeo("TTA", "Leucina", "Leu", "L");
        agregarMapeo("TTG", "Leucina", "Leu", "L");
        agregarMapeo("CTT", "Leucina", "Leu", "L");
        agregarMapeo("CTC", "Leucina", "Leu", "L");
        agregarMapeo("CTA", "Leucina", "Leu", "L");
        agregarMapeo("CTG", "Leucina", "Leu", "L");
        
        // Isoleucina (Ile/I)
        agregarMapeo("ATT", "Isoleucina", "Ile", "I");
        agregarMapeo("ATC", "Isoleucina", "Ile", "I");
        agregarMapeo("ATA", "Isoleucina", "Ile", "I");
        
        // Metionina (Met/M) - También codón de inicio
        agregarMapeo("ATG", "Metionina", "Met", "M");
        
        // Valina (Val/V)
        agregarMapeo("GTT", "Valina", "Val", "V");
        agregarMapeo("GTC", "Valina", "Val", "V");
        agregarMapeo("GTA", "Valina", "Val", "V");
        agregarMapeo("GTG", "Valina", "Val", "V");
        
        // Serina (Ser/S)
        agregarMapeo("TCT", "Serina", "Ser", "S");
        agregarMapeo("TCC", "Serina", "Ser", "S");
        agregarMapeo("TCA", "Serina", "Ser", "S");
        agregarMapeo("TCG", "Serina", "Ser", "S");
        agregarMapeo("AGT", "Serina", "Ser", "S");
        agregarMapeo("AGC", "Serina", "Ser", "S");
        
        // Prolina (Pro/P)
        agregarMapeo("CCT", "Prolina", "Pro", "P");
        agregarMapeo("CCC", "Prolina", "Pro", "P");
        agregarMapeo("CCA", "Prolina", "Pro", "P");
        agregarMapeo("CCG", "Prolina", "Pro", "P");
        
        // Treonina (Thr/T)
        agregarMapeo("ACT", "Treonina", "Thr", "T");
        agregarMapeo("ACC", "Treonina", "Thr", "T");
        agregarMapeo("ACA", "Treonina", "Thr", "T");
        agregarMapeo("ACG", "Treonina", "Thr", "T");
        
        // Alanina (Ala/A)
        agregarMapeo("GCT", "Alanina", "Ala", "A");
        agregarMapeo("GCC", "Alanina", "Ala", "A");
        agregarMapeo("GCA", "Alanina", "Ala", "A");
        agregarMapeo("GCG", "Alanina", "Ala", "A");
        
        // Tirosina (Tyr/Y)
        agregarMapeo("TAT", "Tirosina", "Tyr", "Y");
        agregarMapeo("TAC", "Tirosina", "Tyr", "Y");
        
        // Histidina (His/H)
        agregarMapeo("CAT", "Histidina", "His", "H");
        agregarMapeo("CAC", "Histidina", "His", "H");
        
        // Glutamina (Gln/Q)
        agregarMapeo("CAA", "Glutamina", "Gln", "Q");
        agregarMapeo("CAG", "Glutamina", "Gln", "Q");
        
        // Asparagina (Asn/N)
        agregarMapeo("AAT", "Asparagina", "Asn", "N");
        agregarMapeo("AAC", "Asparagina", "Asn", "N");
        
        // Lisina (Lys/K)
        agregarMapeo("AAA", "Lisina", "Lys", "K");
        agregarMapeo("AAG", "Lisina", "Lys", "K");
        
        // Ácido Aspártico (Asp/D)
        agregarMapeo("GAT", "Ácido Aspártico", "Asp", "D");
        agregarMapeo("GAC", "Ácido Aspártico", "Asp", "D");
        
        // Ácido Glutámico (Glu/E)
        agregarMapeo("GAA", "Ácido Glutámico", "Glu", "E");
        agregarMapeo("GAG", "Ácido Glutámico", "Glu", "E");
        
        // Cisteína (Cys/C)
        agregarMapeo("TGT", "Cisteína", "Cys", "C");
        agregarMapeo("TGC", "Cisteína", "Cys", "C");
        
        // Triptófano (Trp/W)
        agregarMapeo("TGG", "Triptófano", "Trp", "W");
        
        // Arginina (Arg/R)
        agregarMapeo("CGT", "Arginina", "Arg", "R");
        agregarMapeo("CGC", "Arginina", "Arg", "R");
        agregarMapeo("CGA", "Arginina", "Arg", "R");
        agregarMapeo("CGG", "Arginina", "Arg", "R");
        agregarMapeo("AGA", "Arginina", "Arg", "R");
        agregarMapeo("AGG", "Arginina", "Arg", "R");
        
        // Glicina (Gly/G)
        agregarMapeo("GGT", "Glicina", "Gly", "G");
        agregarMapeo("GGC", "Glicina", "Gly", "G");
        agregarMapeo("GGA", "Glicina", "Gly", "G");
        agregarMapeo("GGG", "Glicina", "Gly", "G");
        
        // Codones de parada
        agregarMapeo("TAA", "STOP", "STOP", "-");
        agregarMapeo("TAG", "STOP", "STOP", "-");
        agregarMapeo("TGA", "STOP", "STOP", "-");
    }

    private void agregarMapeo(String tripletaADN, String nombre, String abrev3, String abrev1) {
        Aminoacido aminoacido = new Aminoacido(nombre, abrev3, abrev1);
        tripletaAAminoacido.put(tripletaADN, aminoacido);
    }

    public Aminoacido obtenerAminoacido(String tripletaADN) {
        return tripletaAAminoacido.getOrDefault(tripletaADN, 
               new Aminoacido("Desconocido", "???", "?"));
    }

    public void procesarSecuencia(List<InformacionPatron> patrones) {
        frecuenciasAminoacidos.clear();
        for (InformacionPatron patron : patrones) {
            Aminoacido aminoacido = obtenerAminoacido(patron.getPatron());
            frecuenciasAminoacidos.put(
                aminoacido, 
                frecuenciasAminoacidos.getOrDefault(aminoacido, 0) + patron.getFrecuencia()
            );
        }
    }

    public Map<Aminoacido, Integer> getFrecuenciasAminoacidos() {
        return new HashMap<>(frecuenciasAminoacidos);
    }

    public Map<Aminoacido, List<String>> getTripletasPorAminoacido() {
        Map<Aminoacido, List<String>> resultado = new HashMap<>();
        for (Map.Entry<String, Aminoacido> entry : tripletaAAminoacido.entrySet()) {
            resultado.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }
        return resultado;
    }
}
