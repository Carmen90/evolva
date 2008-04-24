package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

public class MutadorIntercambio implements Mutador {

	public Cromosoma[] mutacion(Cromosoma[] poblacion, double probabilidadMutacion) {
		
		//Numero de genes de un individuo
		int numGenes = poblacion[0].getNumeroGenes();
		
		//Para cada individuo
		for(int i=0;i<poblacion.length;i++){
			//Extraemos los genes de los padres
			Gen[] genesIndividuo = poblacion[i].getGenes();
			 
			//Para cada gen del individuo
			for(int j=0;j<numGenes;j++){
								
				int longitudGen = ((GenEntero)genesIndividuo[j]).getLongitud();
				int[] codificacionGenIndividuo = new int[longitudGen];
				
				//Guardamos la codificacion de los genes del padre en un array de enteros
				codificacionGenIndividuo = ((GenEntero)genesIndividuo[j]).getGen();
				
				//Calculamos una probabilidad para ver si se hace o no la mutacion
				double prob = Math.random();
				if(prob<probabilidadMutacion){  //Hay mutacion
					//Generamos 2 puntos al azar 
					int punto1 = MyRandom.aleatorioEntero(1,longitudGen);
					int punto2 = MyRandom.aleatorioEntero(1,longitudGen);
					
					//Hacemos que los dos puntos generados no sean iguales
					while(punto2==punto1)
						punto2 = MyRandom.aleatorioEntero(1,longitudGen);
					
					//Intercambiamos los valores de ambas posiciones en el gen
					int temp = codificacionGenIndividuo[punto1];
					codificacionGenIndividuo[punto1] = codificacionGenIndividuo[punto2];
					codificacionGenIndividuo[punto2] = temp;
				}
			}
		}
		
		return poblacion;
	}

}
