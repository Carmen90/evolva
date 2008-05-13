package es.ucm.fdi.utils;

public class MyRandom {
	//funcion que calcula un entero aleatorio entre menor y mayor-1
	public static int aleatorioEntero(int menor, int mayor){
		
		//calculamos el multiplicador para el numero aleatorio, 
		//en funcion del numero de digitos del limite superior
		int multiplicador = 10;
		double division = mayor/10;
		while (Math.floor(division) != 0){
			multiplicador *= 10;
			division = division/10;
		}
		
		//mientras que el numero aleatorio generado sea menor que "menor" lo seguimos generando
		int aleatorio = (int)(Math.random()*multiplicador) % mayor;
		while (aleatorio < menor) aleatorio = (int)(Math.random()*multiplicador) % mayor;
		
		return aleatorio;
	}
	
	//funcion que calcula un booleano entero
	public static boolean aleatorioBool(){
		return (Math.random() >= 0.5);
	}
}
