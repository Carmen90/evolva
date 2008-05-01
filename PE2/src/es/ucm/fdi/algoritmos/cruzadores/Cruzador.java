package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;

public interface Cruzador {
	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2);

}
