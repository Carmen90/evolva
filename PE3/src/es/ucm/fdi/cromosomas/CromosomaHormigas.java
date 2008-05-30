package es.ucm.fdi.cromosomas;

import es.ucm.fdi.genes.GenArboreo;

public class CromosomaHormigas extends Cromosoma{

	public CromosomaHormigas(int numeroGenes) {
		super(numeroGenes);
	}
	
	private CromosomaHormigas(CromosomaHormigas original){
		//crear cromosoma
		super(original.numeroGenes);
		
		//crear genes, longitudes de los genes y fenotipos
		GenArboreo[] copia  = new GenArboreo[original.getNumeroGenes()];
				
		for (int i = 0; i< original.getNumeroGenes(); i++){
			copia[i] = (GenArboreo)original.getGenes()[i].copiaGen();
		}
		//setear genes		
		this.genes = copia;

		this.longitudCromosoma = original.getLongitudCromosoma();
		this.aptitud = original.getAptitud();
		this.puntuacion = original.getPuntuacion();
		this.puntuacionAcumulada = original.getPuntuacionAcumulada();
		fenotipo();
	}

	protected int calcularLongitudCromosoma() {
		GenArboreo gen = (GenArboreo) this.genes[0];
		int numeroNodos = gen.calcularNumeroNodos() + 1;
		return numeroNodos;
	}

	public Cromosoma copiarCromosoma() {
		return new CromosomaHormigas(this);
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
