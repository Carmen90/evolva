package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;

public class PruebasSeleccion {

	public static void main(String[] args) {
		Cromosoma[] poblacion = new Cromosoma[5];
		Evaluador evaluador = new EvaluadorHormigas();
		for (int j = 0; j< 5;j++){
			//generamos e inicializamos cada individuo de la poblacion
			poblacion[j] = evaluador.generarCromosomaAleatorio();		
			poblacion[j].setAptitud(evaluador.evaluaAptitud(poblacion[j]));
		}
		
		Seleccionador torneo = new SeleccionTorneo();
		@SuppressWarnings("unused")
		Cromosoma[] pobAux = torneo.seleccion(poblacion, evaluador);

	}

}
