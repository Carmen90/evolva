package es.ucm.fdi.algoritmos.mutadores;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

public class MutadorInsercion implements Mutador {

	public static int NUMERO_INSERCIONES = 1;

	public Cromosoma[] mutacion(Cromosoma[] poblacion, double probabilidadMutacion) {

		//Numero de genes de un individuo
		int numGenes = poblacion[0].getNumeroGenes();

		//Para cada individuo
		for(int i=0;i<poblacion.length;i++){
			System.out.println("individuo " + i);
			//Extraemos los genes de los padres
			Gen[] genesIndividuo = poblacion[i].getGenes();

			//Para cada gen del individuo
			for(int j=0;j<numGenes;j++){

				//Calculamos una probabilidad para ver si se hace o no la mutacion
				double prob = Math.random();
				if(prob<probabilidadMutacion){  //Hay mutacion

					int longitudGen = ((GenEntero)genesIndividuo[j]).getLongitud();
					//Guardamos la codificacion de los genes del padre en un array de enteros
					int[] codificacionGenIndividuo = ((GenEntero)genesIndividuo[j]).getGen();

					//hacemos tantas inserciones como especifica el parametro 				
					for (int k = 0; k<NUMERO_INSERCIONES; k++){
						//generamos las posiciones aleatorias...
						int posicionElementoAIntercambiar = MyRandom.aleatorioEntero(1,longitudGen);
						int posicionInsercion = posicionElementoAIntercambiar;
						//nos aseguramos de que no volvamos a insertar el elemento en la misma posicion
						while (posicionInsercion == posicionElementoAIntercambiar)
							posicionInsercion = MyRandom.aleatorioEntero(1, longitudGen);
						
						System.out.println("Se cambiara el elemento en "+posicionElementoAIntercambiar +
								" ("+codificacionGenIndividuo[posicionElementoAIntercambiar]+")");
						System.out.println("A la posicion "+ posicionInsercion);

						//copiamos el elemento en su nueva posicion, desplazando previamente
						//los restantes
						int elemento = codificacionGenIndividuo[posicionElementoAIntercambiar];
						if (posicionElementoAIntercambiar < posicionInsercion){
							for (int n = posicionElementoAIntercambiar; n <  posicionInsercion; n++){
								codificacionGenIndividuo[n] = codificacionGenIndividuo[n+1];
							}
						}else{
							for (int n = posicionElementoAIntercambiar; n >  posicionInsercion; n--){
								codificacionGenIndividuo[n] = codificacionGenIndividuo[n-1];
							}
						}
						codificacionGenIndividuo[posicionInsercion] = elemento;
					}
				}
			}
		}
		return poblacion;
	}
}
