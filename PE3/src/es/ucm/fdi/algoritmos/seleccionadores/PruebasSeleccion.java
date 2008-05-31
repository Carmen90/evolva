package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;

public class PruebasSeleccion {

	public static int numeroIndividuos = 20;
	public static void main(String[] args) {
		Cromosoma[] poblacion = new Cromosoma[numeroIndividuos];
		Evaluador evaluador = new EvaluadorHormigas();
		
		for (int j = 0; j<numeroIndividuos ;j++){
			//generamos e inicializamos cada individuo de la poblacion
			poblacion[j] = evaluador.generarCromosomaAleatorio();		
			poblacion[j].setAptitud(evaluador.evaluaAptitud(poblacion[j]));
		}
		
		Seleccionador seleccionador = new SeleccionTorneo();
		poblacion = seleccionador.generarSegmentos(poblacion, evaluador, false);
		
		System.out.println("poblacion generada, con segmentos de seleccion generados:");
		for (int i = 0; i< poblacion.length; i++){
			System.out.println("cromsoma "+i+": aptitud = "+poblacion[i].getAptitud() + "; puntuacionAcumulada = "+poblacion[i].getPuntuacionAcumulada());
		}
				
		Cromosoma[] pobAux = seleccionador.seleccion(poblacion, evaluador);

		System.out.println("poblacion generada, con segmentos de seleccion generados:");
		for (int i = 0; i< poblacion.length; i++){
			System.out.println("cromsoma "+i+": aptitud = "+pobAux[i].getAptitud());
		}
	}

}
