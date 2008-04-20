package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public interface Cruzador {
	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2, Evaluador evaluador);

}
