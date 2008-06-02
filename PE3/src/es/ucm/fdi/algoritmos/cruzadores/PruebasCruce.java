package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.GenArboreo;

public class PruebasCruce {

	public static void main (String[] args){
		Evaluador e = new EvaluadorHormigas();
		
		/****************************CROMOSOMA 1*********************************/
		//crear cromosoma
		CromosomaHormigas cromosoma = (CromosomaHormigas) e.generarCromosomaFijo();
				
		/****************************CROMOSOMA 2*********************************/
		//crear cromosoma
		CromosomaHormigas cromosoma2 =(CromosomaHormigas) e.generarCromosomaAleatorio();
		
		/**************************** DEPURACION *********************************/
		System.out.println("Cromosomas Progenitores: ");
		System.out.println(cromosoma.toString());
		System.out.println(cromosoma2.toString());
		
		/**************************** CRUCE *********************************/
		Cruzador cruzador = new CruzadorArboreoMejorado();		
		Cromosoma[] hijos = cruzador.cruce(cromosoma, cromosoma2);
		
		
		/**************************** DEPURACION *********************************/
		System.out.println("Cromosomas Hijos: ");
		System.out.println(hijos[0].toString());
		System.out.println(hijos[1].toString());
		
	}
}
