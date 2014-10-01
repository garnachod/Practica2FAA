/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoFactory;
import datos.TiposDeAtributos;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dani
 */
public class ClasificadorNaiveBayes extends Clasificador{
    //el primer Array nos dice la columna
    //el primer HashMap nos dice el valor de la columna
    //el segundo hashmap cuenta por cada valor de la columna cuantas incidencias 
    //tiene la clase, para hacer p(D|H)
    ArrayList<HashMap<Elemento, HashMap<Elemento, Integer>>> incidencias;
    
    //cuenta las incidencias totales de cada clase
    HashMap<Elemento, Integer> incidenciaClaseTotal;
    
    //filas totales de train
    int filasTrain = 0;
    

    @Override
    public void entrenamiento(Datos datosTrain) {
        this.incidencias = new ArrayList<>();
        this.incidenciaClaseTotal = new HashMap<>();
        
        //creacion de objetos
        for(Elemento e: datosTrain.getDatos()[0]){
            HashMap<Elemento, HashMap<Elemento, Integer>> incidenciaColumna = new HashMap<>();
            this.incidencias.add(incidenciaColumna);
        }
        
        
        for(Elemento fila[] : datosTrain.getDatos()){
            this.filasTrain++;
            Elemento ultimoElemFila = fila[fila.length-1];
            for(int i = 0; i < (fila.length - 1); i++){
                HashMap<Elemento, HashMap<Elemento, Integer>> incidenciaColumna  = this.incidencias.get(i);
                  
                if(incidenciaColumna.containsKey(fila[i])){
                    //se ha encontrado el elemento
                    HashMap<Elemento, Integer> incidenciaClase = incidenciaColumna.get(fila[i]);
                    if(incidenciaClase.containsKey(ultimoElemFila)){
                        Integer contador = incidenciaClase.get(ultimoElemFila);
                        contador++;
                        incidenciaClase.put(ultimoElemFila, contador);
                    }else{
                        Integer contador = 1;
                        incidenciaClase.put(ultimoElemFila, contador);
                    }
                    
                }else{
                    //no se encontraba el elemento en la base de datos
                    HashMap<Elemento, Integer> incidenciaClase = new HashMap<>();
                    Integer contador = 1;
                    incidenciaClase.put(ultimoElemFila, contador);
                    incidenciaColumna.put(fila[i], incidenciaClase);
                }
            }
            //sumar las incidencias de cada clase
            if(this.incidenciaClaseTotal.containsKey(ultimoElemFila)){
                Integer nIncidencias = this.incidenciaClaseTotal.get(ultimoElemFila);
                nIncidencias++;
                this.incidenciaClaseTotal.put(ultimoElemFila, nIncidencias);
            }else{
                Integer nIncidencias = 1;
                this.incidenciaClaseTotal.put(ultimoElemFila, nIncidencias);
            }
        }
        
    }

    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        ArrayList<Elemento> clasificado = new ArrayList<>();
        
        
        for(Elemento fila[] : datosTest.getDatos()){
            Elemento mejorClase = null;
            //Elemento ultimoElemFila = fila[fila.length-1];
            double mejorProb = 0;
            for(Elemento claseTest : datosTest.getClases()){
                
                //simulacion de la clase, decimos, si fuera esta clase, que prob da
               
                double prob = 0;
                for(int i = 0; i < (fila.length - 1); i++){
                    /*
                        prob de el dato dada la hipotesis
                        MULi (p(Di|H))
                    */
                    double probAux = 0;
                    try{
                        probAux = this.incidencias.get(i).get(fila[i]).get(claseTest);
                    }catch(Exception e){
                        
                    }
                    probAux = probAux/this.incidenciaClaseTotal.get(claseTest);
                    if(i == 0){
                        prob = probAux;
                    }else{
                        prob = prob*probAux;
                    }
                }
                /*
                    prob de la hipotesis
                */
                double probAux = this.incidenciaClaseTotal.get(claseTest);
                probAux = probAux/this.filasTrain;
                prob = prob*probAux;
                if(prob > mejorProb){
                    mejorProb = prob;
                    mejorClase = claseTest;
                }
            }
            /*fin del iterador de las clases*/
            clasificado.add(mejorClase);
        }
        /*fin del iterador de las filas*/
        return clasificado;
    }
    
}
