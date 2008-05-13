package es.ucm.fdi.algoritmos;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public abstract class AGenetico {
	
	protected Cromosoma[] poblacion, poblacionSeleccionada, elite;
	protected int numeroElite;
	
	protected Cromosoma elMejor;
	protected int posicionMejorCromosoma;
	protected int generacionActual;
	
	//valores que aporta el usuario
	protected int numMaxGeneraciones;
	protected int tamañoPoblacion;
	protected double probabilidadCruce;
	protected double probabilidadMutacion;
	protected double tolerancia;
	
	protected Evaluador evaluador;

	public AGenetico(){}
	
	public AGenetico(int tamañoPoblacion, int numMaxGeneraciones, double probabilidadCruce, double probabilidadMutacion, double tolerancia) {
		this.tamañoPoblacion = tamañoPoblacion;
		this.numMaxGeneraciones = numMaxGeneraciones;
		this.probabilidadCruce = probabilidadCruce;
		this.probabilidadMutacion = probabilidadMutacion;
		this.tolerancia = tolerancia;
		
		this.poblacion = new Cromosoma[tamañoPoblacion];
		this.generacionActual = 0;
		/*no inicializados en construccion:
		   //protected Cromosoma elMejor;
		   //protected int posicionMejorCromosoma;
		*/
	}

	//métodos que deben sobreescribir los algoritmos geneticos concretos 
	protected abstract void inicializar(Evaluador funcionEvaluacion);
	protected abstract void seleccion();
	protected abstract void reproduccion();
	protected abstract void mutacion();
	protected abstract void evaluarPoblacion(boolean mejora);
	
	protected abstract double mediaPoblacionInstantanea();
	protected abstract Cromosoma mejorPoblacionInstantanea();
	protected abstract void almacenarElite(double porcentajeElite);
	public abstract void recuperarElite();
	
	public boolean terminado(){
		return this.generacionActual == numMaxGeneraciones;
	}
	
	//getters y setters
	public Cromosoma getElMejor() {
		return elMejor;
	}
	public void setElMejor(Cromosoma elMejor) {
		this.elMejor = elMejor;
	}
	public int getNumMaxGeneraciones() {
		return numMaxGeneraciones;
	}
	public void setNumMaxGeneraciones(int numMaxGeneraciones) {
		this.numMaxGeneraciones = numMaxGeneraciones;
	}
	public Cromosoma[] getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(Cromosoma[] poblacion) {
		this.poblacion = poblacion;
	}
	public int getPosicionMejorCromosoma() {
		return posicionMejorCromosoma;
	}
	public void setPosicionMejorCromosoma(int posicionMejorCromosoma) {
		this.posicionMejorCromosoma = posicionMejorCromosoma;
	}
	public double getProbabilidadCruce() {
		return probabilidadCruce;
	}
	public void setProbabilidadCruce(double probabilidadCruce) {
		this.probabilidadCruce = probabilidadCruce;
	}
	public double getProbabilidadMutacion() {
		return probabilidadMutacion;
	}
	public void setProbabilidadMutacion(double probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
	}
	public int getTamañoPoblacion() {
		return tamañoPoblacion;
	}
	public void setTamañoPoblacion(int tamañoPoblacion) {
		this.tamañoPoblacion = tamañoPoblacion;
	}
	public double getTolerancia() {
		return tolerancia;
	}
	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public Cromosoma[] getPoblacionSeleccionada() {
		return poblacionSeleccionada;
	}

	public void setPoblacionSeleccionada(Cromosoma[] poblacionSeleccionada) {
		this.poblacionSeleccionada = poblacionSeleccionada;
	}

	public int getGeneracionActual() {
		return generacionActual;
	}

	public void setGeneracionActual(int generacionActual) {
		this.generacionActual = generacionActual;
	}
		
	
}
