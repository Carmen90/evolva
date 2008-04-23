package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorViajante;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

public class PruebasCruce {

	public static final int[] PadreFijo1 = {25, 4, 22, 13, 1, 8, 9, 19, 26, 14, 11, 12, 21, 3, 2, 6, 18, 7, 24, 0, 27, 5, 15, 17, 10, 23, 16, 20};
	public static final int[] PadreFijo2 = {25, 0, 22, 7, 8, 17, 9, 5, 12, 24, 15, 26, 21, 10, 6, 3, 18, 11, 20, 1, 14, 2, 16, 13, 27, 19, 4, 23};
	
	public static void main (String[] args){
		Evaluador e = new EvaluadorViajante();
		
		/****************************CROMOSOMA 1*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma = (CromosomaEnteroViajante) e.generarCromosomaAleatorio();
		//CromosomaEnteroViajante cromosoma = (CromosomaEnteroViajante) e.generarCromosomaFijo(PadreFijo1);
		
		/****************************CROMOSOMA 2*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma2 =(CromosomaEnteroViajante) e.generarCromosomaAleatorio();
		//CromosomaEnteroViajante cromosoma2 =(CromosomaEnteroViajante) e.generarCromosomaFijo(PadreFijo2);
		
		/**************************** CRUCE *********************************/
		Cruzador cruzador = new CruzadorERX();
		Cromosoma[] hijos = cruzador.cruce(cromosoma, cromosoma2,e);
		
		
		/**************************** DEPURACION *********************************/
		System.out.println("Cromosomas Progenitores: ");
		System.out.println(cromosoma.toString());
		System.out.println(cromosoma2.toString());
		System.out.println("Cromosomas Hijos: ");
		System.out.println(hijos[0].toString());
		System.out.println(hijos[1].toString());
	}
}
