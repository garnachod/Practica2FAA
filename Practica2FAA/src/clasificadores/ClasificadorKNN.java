package clasificadores;

import datos.Datos;
import datos.Elemento;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */
public class ClasificadorKNN extends Clasificador {
    
    Elemento datosTrain[][];
    
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
    }

    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
