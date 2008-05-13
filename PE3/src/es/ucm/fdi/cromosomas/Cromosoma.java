package es.ucm.fdi.cromosomas;

import es.ucm.fdi.genes.Gen;

public abstract class Cromosoma {
	
	//los genes se deben generar aleatoriamente por la funcion
	protected Gen[] genes;
	//una vez generados los genes, se generan sus fenotipos
	protected double[] fenotipo;
	//longitud total del cromosoma, que se calculara sumando las longitudes de sus genes.
	protected int longitudCromosoma;
	protected int[] longitudGenes;
	//numero de genes en el cromosoma
	protected int numeroGenes;
	
	//la aptitud se genera en la inicializacion del algoritmo genetico
	protected double aptitud;
	//la puntuacion y la puntuacion Acumulada de generan al evaluar la aptitud.
	protected double puntuacion;
	protected double puntuacionAcumulada;
	
	/**************************************METODOS ABSTRACTOS*************************************/
	public abstract void fenotipo();
	public abstract Cromosoma copiarCromosoma();
	public abstract int calcularLongitudCromosoma();
	public abstract String toString();
	/************************************FIN METODOS ABSTRACTOS***********************************/
	
	
	public Cromosoma(int numeroGenes) {
		this.numeroGenes = numeroGenes;
		this.aptitud = 0;
		this.puntuacion = 0;
		this.puntuacionAcumulada = 0;
	}
	
	//funcion a la que se llamara una vez generados aleatoriamente los genes del cromosoma por las funciones.
	//seteara los atributos que dependen de los genes en cada cromosoma en particular
	//como precondicion, se deben haber generado y seteado los genes!!!
	private void inicializarCromosoma() {
		
		//comprobacion de la precondicion
		if (this.genes != null && this.genes.length != 0){
			
			this.longitudGenes = new int[this.numeroGenes];
			
			for (int i = 0; i < numeroGenes; i++){
				Gen genI = this.genes[i];
				longitudGenes[i] = genI.getLongitud();
			}
			this.longitudCromosoma = calcularLongitudCromosoma();
			fenotipo();
			//la aptitud se generara en el metodo que evalua la poblacion, para despues poder evaluarla
			this.aptitud = 0.0;
			
		}else{
			System.out.println("[ERROR - Cromosoma]El cromosoma no se inicializo correctamente, debido a que no se configuraron los genes");
		}
	}
	//por lo tanto, la creacion de los cromosomas seguira el siguiente flujo:
	//crear cromosoma, crear genes, setear genes(inicializa el cromosoma)

	
	/******************************GETTERS Y SETTERS***********************************/
	
	public double getAptitud() {
		return aptitud;
	}
	public void setAptitud(double aptitud) {
		this.aptitud = aptitud;
	}
	public double[] getFenotipo() {
		return fenotipo;
	}
	public void setFenotipo(double[] fenotipo) {
		this.fenotipo = fenotipo;
	}
	public Gen[] getGenes() {
		return genes;
	}
	
	/* NO SE PUEDE SETEAR LOS GENES SIN INICIALIZAR DESPUES*/
	public void setGenes(Gen[] genes) {
		this.genes = genes;
		inicializarCromosoma();
	}
	
	public double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}
	public double getPuntuacionAcumulada() {
		return puntuacionAcumulada;
	}
	public void setPuntuacionAcumulada(double puntuacionAcumulada) {
		this.puntuacionAcumulada = puntuacionAcumulada;
	}

	public int getLongitudCromosoma() {
		return longitudCromosoma;
	}

	public void setLongitudCromosoma(int longitudCromosoma) {
		this.longitudCromosoma = longitudCromosoma;
	}

	public int getNumeroGenes() {
		return numeroGenes;
	}

	public void setNumeroGenes(int numeroGenes) {
		this.numeroGenes = numeroGenes;
	}

	public int[] getLongitudGenes() {
		return longitudGenes;
	}

	public void setLongitudGenes(int[] longitudGenes) {
		this.longitudGenes = longitudGenes;
	}
	
}
