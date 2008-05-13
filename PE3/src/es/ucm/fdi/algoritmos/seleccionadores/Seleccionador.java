package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public interface Seleccionador {
	
	//el parametro de entrada "mejora" quiere decir que se aplica el escalado de aptitud en la ruleta, o la presion selectiva en el ranking
	public Cromosoma[] generarSegmentos(Cromosoma[] poblacion, Evaluador e, boolean mejora);
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e);
}
