package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public interface Seleccionador {
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e);
}
