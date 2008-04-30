package es.ucm.fdi.utils;

import java.util.Vector;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public class Ordenacion {
	
	public static void ordenacionInsercion(int[] array){
		int tmp;
		int i,j;
		int N = array.length;
		
		for(i = 1; i< N ; i++){
			tmp = array[i];
			for(j = i; (j>0) && tmp<(array[j-1]); j--)
				array[j] = array[j-1];
			array[j] = tmp;		
		}
	}
	
	//ordena la poblacion de individuos, de peor a mejor, para asignar los segmentos de seleccion
	//OJO se genera una copia ordenada de la poblacion
	public static Cromosoma[] sortSelectionIndividuals(Cromosoma[] poblacion, Evaluador evaluador){
		//para ordenar la poblacion, la recorremos entera, y vamos insertando ordenadamente 
		//en funcion del criterio de la funcion, los individuos en un array auxiliar.
		//despues, seteamos la poblacion ordenada...
		Vector <Cromosoma> ordenado = new Vector <Cromosoma>();

		//metemos el primer cromosoma directamente en el vector
		ordenado.add(poblacion[0].copiarCromosoma());
		//para los demas...
		for (int i = 1; i< poblacion.length; i++){
			//individuo que vamos a insertar
			Cromosoma individuoI = poblacion[i].copiarCromosoma();

			//buscamos donde insertar el nuevo elemento, teniendo en cuenta que se trata de una ordenacion
			//de peor a mejor aptitud, luego buscamos el indice en orden inverso
			int j = ordenado.size() - 1;
			boolean indiceEncontrado = false;
			while (!indiceEncontrado && j >= 0){
				Cromosoma individuoOrdenadoJ = ordenado.get(j);
				indiceEncontrado = evaluador.esMejorAptitud(individuoI.getAptitud(), individuoOrdenadoJ.getAptitud());
				j--;
			}
			//si hemos encontrado un indice apto para la insercion
			if (indiceEncontrado){
				ordenado.insertElementAt(individuoI, j+2);
			}else ordenado.insertElementAt(individuoI,0);
		}
		
		//una vez ordenados en el vector, seteamos la poblacion.
		Cromosoma[] poblacionOrdenada = new Cromosoma[poblacion.length];
		for (int i = 0; i < poblacion.length; i++){
			poblacionOrdenada[i] = ordenado.get(i);
		}
		return poblacionOrdenada;

	}
	
}
