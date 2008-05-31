package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;

public class PruebasEvaluador {

	public static void main(String[] args) {
		Evaluador evaluador = new EvaluadorHormigas();
		
		//PRUEBA DE GENERACION ALEATORIA DE CROMOSOMAS:
		Cromosoma c = evaluador.generarCromosomaAleatorio();
		//Cromosoma c = evaluador.generarCromosomaFijo();
		System.out.println("--->PRUEBA DE GENERACION ALEATORIA DE CROMOSOMAS");
		System.out.println("El cromosoma aleatorio generado es el siguiente: ");
		System.out.println(c.getFenotipo());
		System.out.println("Numero de nodos = "+c.getLongitudCromosoma());
		
		//PRUEBA DE EVALUACION DE CROMOSOMAS:
		System.out.println("--->PRUEBA DE EVALUACION DE CROMOSOMAS");
		
		c.setAptitud(evaluador.evaluaAptitud(c));
		System.out.println("La aptitud del cromosoma aleatorio es: ");
		System.out.println(c.getAptitud());

	}

}
