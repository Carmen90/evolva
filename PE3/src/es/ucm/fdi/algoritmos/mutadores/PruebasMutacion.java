package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;


public class PruebasMutacion {

	public static void main(String[] args) {

		Evaluador e = new EvaluadorHormigas();

		/****************************CROMOSOMA 1*********************************/
		//crear cromosoma
		CromosomaHormigas cromosoma = (CromosomaHormigas) e.generarCromosomaAleatorio();

		
		System.out.println("Individuo inicial: ");
		System.out.println(cromosoma.getFenotipo());

		/**************************** MUTACION *********************************/
		Mutador mutador = new MutacionTerminalSimple();
		Cromosoma[] individuos = new Cromosoma[1];
		individuos[0] = cromosoma;
		individuos = mutador.mutacion(individuos, 0.9);


		/**************************** DEPURACION *********************************/
		
		System.out.println("Individuo mutado: ");
		System.out.println(individuos[0].getFenotipo());
		
	}

}
