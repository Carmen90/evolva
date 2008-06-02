package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.utils.TableroComida;

public class PruebasEvaluador {

	public static void main(String[] args) {
		Evaluador evaluador = new EvaluadorHormigas(true);
		
		//PRUEBA DE GENERACION ALEATORIA DE CROMOSOMAS:
		Cromosoma c = evaluador.generarCromosomaAleatorio();
		//Cromosoma c = evaluador.generarCromosomaFijo();
		//Cromosoma c = ((EvaluadorHormigas)evaluador).generarCromosomaSolucion();
		System.out.println("--->PRUEBA DE GENERACION ALEATORIA DE CROMOSOMAS");
		System.out.println("El cromosoma aleatorio generado es el siguiente: ");
		System.out.println(c.getFenotipo());
		System.out.println("Numero de nodos = "+c.getLongitudCromosoma());
		
		//PRUEBA DE EVALUACION DE CROMOSOMAS:
		System.out.println("--->PRUEBA DE EVALUACION DE CROMOSOMAS");
		
		c.setAptitud(evaluador.evaluaAptitud(c));
		System.out.println("La aptitud del cromosoma aleatorio es: ");
		System.out.println(c.getAptitud());
		
		boolean[][] pasosDados = ((EvaluadorHormigas)evaluador).getPasos();
		for (int i = 0; i<pasosDados.length; i++){
			for (int j = 0; j < pasosDados[i].length; j++){
				String casilla = " · ";
				if (TableroComida.COMIDA[i][j]==1)casilla =" O ";
				if (pasosDados[i][j]) casilla =" # ";
				System.out.print(casilla);
			}
			System.out.println();
		}
	}

}
