package es.ucm.fdi.utils;

public class Busquedas {

	//devuelve el indice de la primera ocurrencia entre menor y mayor, o -1 si no lo ha encontrado
	public static int buscar (int elemento, int[] array, int indiceMenor, int indiceMayor){
		int indice = -1;
		int i = indiceMenor;
		boolean encontrado = false;
		if (indiceMayor <= array.length){
			while (!encontrado && i<= indiceMayor){
				if (elemento == array[i]){
					encontrado = true;
					indice = i;
				}
				i++;
			}
		}
		return indice;
	}
	
	//devuelve el indice de la primera ocurrencia entre 0 y n-1, o -1 si no lo ha encontrado
	public static int buscar (int elemento, int[] array){
		return buscar (elemento, array, 0, array.length - 1);
	}
}
