package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;

public interface Seleccionador {
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial);
}
