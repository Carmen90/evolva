package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorViajante;

public class PruebasMutacion {

	public static void main(String[] args) {

		Evaluador e = new EvaluadorViajante();

		/****************************CROMOSOMA 1*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma = (CromosomaEnteroViajante) e.generarCromosomaAleatorio();

		/****************************CROMOSOMA 2*********************************/
		//crear cromosoma
		CromosomaEnteroViajante cromosoma2 =(CromosomaEnteroViajante) e.generarCromosomaAleatorio();
		
		System.out.println("Individuos iniciales: ");
		System.out.println(cromosoma.toString());
		System.out.println(cromosoma2.toString());

		/**************************** CRUCE *********************************/
		Mutador mutador = new MutadorInversion();
		Cromosoma[] individuos = new Cromosoma[2];
		individuos[0] = cromosoma;
		individuos[1] = cromosoma2;
		individuos = mutador.mutacion(individuos, 0.9);


		/**************************** DEPURACION *********************************/
		
		System.out.println("Individuos mutados: ");
		System.out.println(individuos[0].toString());
		System.out.println(individuos[1].toString());
	}

}
