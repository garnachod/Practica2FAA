package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoContinuo;
import datos.TiposDeAtributos;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */
public class ClasificadorKNN extends Clasificador {
    
    Elemento datosTrain[][];
    double maximos[];
    double minimos[];
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        // Si aún no se ha entrenado
        if (this.datosTrain == null) {
            this.datosTrain = datosTrain.getDatos();
        
        // Si ya se ha entrenado, añadir nuevos datos al array
        } else {
            ArrayList <Elemento[]> ambos = new ArrayList<>();
            Collections.addAll(ambos, this.datosTrain);
            Collections.addAll(ambos, datosTrain.getDatos());
            this.datosTrain = ambos.toArray(new Elemento[ambos.size()][]);
        }
        
        // Recalcular máximos y mínimos
        this.maximos = new double[this.datosTrain[0].length];
        this.minimos = new double[this.datosTrain[0].length];
        this.calcularMaxMin();
        
        // Normalizar
        this.normalizarDatosTrain();
    }
    
    private double maximo (int columna) {
        double max = -Double.MAX_VALUE;
        for (Elemento[] dato : this.datosTrain) {
            if (dato[columna].getValorContinuo() > max) {
                max = dato[columna].getValorContinuo();
            }
        }
        return max;
    }
    
    private double minimo (int columna) {
        double min = Double.MAX_VALUE;
        for (Elemento[] dato : this.datosTrain) {
            if (dato[columna].getValorContinuo() < min) {
                min = dato[columna].getValorContinuo();
            }
        }
        return min;
    }
    
    private void calcularMaxMin() {
        
        if (this.datosTrain == null)
            return;
        
        // Para cada columna
        for (int i = 0; i < this.datosTrain[0].length; i++) {
            
            // Si es de tipo continuo
            if (datosTrain[0][i].getTipo() == TiposDeAtributos.Continuo) {
                
                // Encontrar máximo y mínimo
                this.maximos[i] = maximo(i);
                this.minimos[i] = minimo(i);
            }
            
        }
    }
    
    private Elemento[] normalizarFila (Elemento[] fila) {
        for (int i = 0; i < fila.length; i++) {
            if (fila[i].getTipo() == TiposDeAtributos.Continuo) {
                double valor = fila[i].getValorContinuo();
                double min = minimos[i];
                double max = maximos[i];
                if ((max - min) != 0) {
                    double valorNorm = (valor - min) / (max - min);
                    ((ElementoContinuo)fila[i]).setValor(valorNorm);
                }
            }
        }
        return fila;
    }
    
    private void normalizarDatosTrain () {
        for (int i = 0; i < datosTrain.length; i++) {
            this.normalizarFila(datosTrain[i]);
        }
    }
    
   /*
    * Calcula distancia euleriana al cuadrado
    * @param filaA Fila normalizada
    * @param filaB Fila normalizada
    */
    public double distancia (Elemento[] filaA, Elemento[] filaB) {
        double distancia = 0;
        
        for (int i = 0; i < filaA.length; i++) {
            distancia += filaA[i].diferencia(filaB[i]);
        }
        return distancia;
    }
    
    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        // Ir normalizando filas !!
        return null;
    }
    
}
