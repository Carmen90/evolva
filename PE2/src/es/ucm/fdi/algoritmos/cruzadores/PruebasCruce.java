package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorViajante;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

public class PruebasCruce {

	public static void main (String[] args){
		Evaluador e = new EvaluadorViajante();
		
		/****************************CROMOSOMA 1*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma = (CromosomaEnteroViajante) e.generarCromosomaAleatorio(0);
		
		/****************************CROMOSOMA 2*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma2 =(CromosomaEnteroViajante) e.generarCromosomaAleatorio(0);
		
		/**************************** CRUCE *********************************/
		Cruzador cruzador = new CruzadorCX();
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
