package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;

public interface Mutador {
	public Cromosoma[] mutacion(Cromosoma[] poblacion, double probabilidadMutacion);
}
