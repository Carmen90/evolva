package es.ucm.fdi.cromosomas;

import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Ciudades;

public class CromosomaEnteroViajante extends Cromosoma{

	
	/*
	 * atributos heredados de cromosoma
	 * 
	 * --> se setea despues de la construccion <--
	 * protected Gen[] genes;
	 *
	 * --> se setea en la inicializacion del cromosoma despues de la construccion <--
	 * protected double[] fenotipo;
	 * protected int[] longitudGenes;
	 * protected int longitudCromosoma;
	 * 
	 * --> se crea en la construccion <--
	 * protected int numeroGenes;
	 * 
	 * --> se setean en la inicializacion del cromosoma  <--
	 * protected double aptitud;
	 * 
	 * --> se setean en el paso evaluarAptitud del algoritmo <--
	 * protected double puntuacion;
	 * protected double puntuacionAcumulada;
	 */
	
	public CromosomaEnteroViajante(int numeroGenes) {
		super(numeroGenes);
		
	}
	
	//constructora privada de copia
	private CromosomaEnteroViajante(CromosomaEnteroViajante original) {
		//crear cromosoma
		super(original.numeroGenes);
		this.numeroGenes = original.getNumeroGenes();
		
		//crear genes, longitudes de los genes y fenotipos
		GenEntero[] copia  = new GenEntero[original.getNumeroGenes()];
		this.longitudGenes = new int[this.numeroGenes];
		this.fenotipo = new double[this.numeroGenes];
		
		for (int i = 0; i< original.getNumeroGenes(); i++){
			copia[i] = (GenEntero)original.getGenes()[i].copiaGen();
			this.longitudGenes[i]  = original.getLongitudGenes()[i];
			this.fenotipo[i] = original.getFenotipo()[i];
		}
		//setear genes		
		this.genes = copia;

		this.longitudCromosoma = original.getLongitudCromosoma();
		this.aptitud = original.getAptitud();
		this.puntuacion = original.getPuntuacion();
		this.puntuacionAcumulada = original.getPuntuacionAcumulada();
	}

	public Cromosoma copiarCromosoma() {
		return new CromosomaEnteroViajante(this);
	}

	public void fenotipo() {
		GenEntero gen = (GenEntero) this.genes[0];
		double[] valor = new double[gen.getGen().length];
		for(int i = 0; i<gen.getGen().length;i++){
			valor[i] = gen.getGen()[i];
		}
		this.fenotipo = valor;
		
	}

	public int calcularLongitudCromosoma() {
		return 28;
	}
	public int calcularLongitudGen() {
		//como solo tenemos un gen en cada cromosoma:
		return calcularLongitudCromosoma();
	}

	public String toString() {
		String cadenaCromosoma = "[ ";
		for (int i = 0; i< this.numeroGenes; i++){
			GenEntero genI = (GenEntero) this.genes[i];
			cadenaCromosoma += "[ ";
			for (int j = 0; j<genI.getLongitud(); j++){
				cadenaCromosoma+= genI.getGen()[j]+" ";
			}
			cadenaCromosoma += "] ";
		}
		cadenaCromosoma += "] ";
		return cadenaCromosoma;
	}
}
