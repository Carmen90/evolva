package es.ucm.fdi.utils;

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
}
