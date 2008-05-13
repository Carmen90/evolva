package es.ucm.fdi.utils;

import java.util.ArrayList;

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
	
	public static int buscar (int elemento,ArrayList<Integer> array){
		int indice = -1;
		int i = 0;
		boolean encontrado = false;
		while (!encontrado && i< array.size()){
			if (elemento == array.get(i).intValue()){
				encontrado = true;
				indice = i;
			}
			i++;
		}
		return indice;
	}
}
