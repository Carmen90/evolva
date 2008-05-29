package es.ucm.fdi.cromosomas;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;

public class CromosomaHormigas extends Cromosoma{

	public CromosomaHormigas(int numeroGenes) {
		super(numeroGenes);
	}

	protected int calcularLongitudCromosoma() {
		GenArboreo gen = (GenArboreo) this.genes[0];
		int numeroNodos = gen.calcularNumeroNodos() + 1;
		return numeroNodos;
	}

	
	@Override
	public Cromosoma copiarCromosoma() {
		// TODO Auto-generated method stub
		return null;
	}

	//en nuestro caso el fenotipo nos e utilizara para el algoritmo.
	//simplemente lo utilizaremos para mostrarlo en la interfaz, con lo cual sera una cadena
	//con la secuencia del arbol.
	public void fenotipo() {
		fenotipo = this.genes[0].toString();
	}

	public String toString() {
		return fenotipo.toString();
	}

}
