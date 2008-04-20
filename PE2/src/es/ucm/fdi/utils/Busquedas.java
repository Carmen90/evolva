package es.ucm.fdi.utils;

public class Busquedas {

	//devuelve el indice de la primera ocurrencia, o -1 si no lo ha encontrado
	public static int buscar (int elemento, int[] array){
		int indice = -1;
		int i = 0;
		boolean encontrado = false;
		while (!encontrado && i< array.length){
			if (elemento == array[i]){
				encontrado = true;
				indice = i;
			}
			i++;
		}
		return indice;
	}
}
