package es.ucm.fdi.algoritmos;

import es.ucm.fdi.algoritmos.cruzadores.*;
import es.ucm.fdi.algoritmos.mutadores.*;
import es.ucm.fdi.algoritmos.seleccionadores.*;
import es.ucm.fdi.cromosomas.*;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.utils.Ordenacion;
import es.ucm.fdi.utils.Poblacion;

public class AGeneticoHormigas extends AGenetico {
	
	/*
	 * Tipos de Cruce 
	 * Solo tenemos un tipo de cruce para las estrategias de cruce
	 */
	public static final int CRUCE_ARBOREO = 0;
	
	/*
	 * Tipos de Selección
	 */
	public static final int SELECCION_RULETA = 0;
	public static final int SELECCION_TORNEO = 1;
	
	/*
	 * Tipos de Mutación
	 */
	public static final int MUTACION_TERMINAL = 0;
	public static final int MUTACION_FUNCIONAL = 1;
	public static final int MUTACION_ARBOL = 2;
		
	private Cruzador cruzador;
	private Mutador mutador;
	private Seleccionador seleccionador;
	private Evaluador evaluador;

	public AGeneticoHormigas() {
		super();
	}

	public AGeneticoHormigas(int tamPoblacion, int numGeneraciones, double probCruce, double probMutacion,
			int tipoMutacion, int tipoCruce, int tipoSeleccion) {
		super(tamPoblacion, numGeneraciones, probCruce, probMutacion, 0);
		setCruzador(tipoCruce);
		setMutador(tipoMutacion);
		setSeleccionador(tipoSeleccion);
	}

	public void inicializar(Evaluador funcionEvaluacion) {
		this.evaluador = funcionEvaluacion;

		//creamos la poblacion aleatoriamente, para cada cromosoma, lo creamos aleatoriamente y 
		//ellos mismos diran a su genes que se creen aleatoriamente.

		for (int j = 0; j< tamañoPoblacion;j++){
			//generamos e inicializamos cada individuo de la poblacion
			poblacion[j] = evaluador.generarCromosomaAleatorio();		
		}

		//inicializamos el mejor al primer individuo de la poblacion.		
		elMejor = poblacion[0];
		posicionMejorCromosoma = 0;

	}
	public void evaluarPoblacion(boolean mejora) {
		//evaluamos la aptitud de cada uno de los individuos de la poblacion.
		for (int i = 0; i< this.tamañoPoblacion; i++){
			double aptitud = this.evaluador.evaluaAptitud(poblacion[i]);
			poblacion[i].setAptitud(aptitud);
		}
			
		double aptitudMejor = poblacion[0].getAptitud(); //mejor aptitud
		this.posicionMejorCromosoma = 0;

		//Calculamos la posicion del mejor individuo
		for (int i = 1; i< tamañoPoblacion; i++){

			double aptitudI = poblacion[i].getAptitud();
			
			if ( evaluador.esMejorAptitud(aptitudI, aptitudMejor) ){
				this.posicionMejorCromosoma = i;
				aptitudMejor = aptitudI;
			}

		}
		
		//Si el mejor de esta generacion es mejor que el mejor que tenia de antes
		//pues lo actualizamos
		if ( evaluador.esMejorAptitud(aptitudMejor,elMejor.getAptitud())) {
			elMejor = poblacion[posicionMejorCromosoma];
		}

		poblacion = this.seleccionador.generarSegmentos(poblacion, evaluador, mejora);
	}

	public void seleccion() {
		if (this.seleccionador != null) this.poblacionSeleccionada = seleccionador.seleccion(poblacion, this.evaluador);
	}
	
	public void reproduccion() {
		//seleccionados para reproducir
		int[] selCruce = new int[this.tamañoPoblacion];

		//contador seleccionados
		int numSelecCruce = 0;
		double prob;

		//se eligen los individuos a cruzar
		for(int i = 0; i<this.tamañoPoblacion; i++){
			//se generan tamPob numeros aleatorios entre [0 1)
			prob = Math.random();
			//Se eligen los individuos de las posiciones i si prob < pobCruce
			if(prob < this.probabilidadCruce){
				selCruce[numSelecCruce] = i;
				numSelecCruce++;
			}
		}
		//el numero de seleccionados se hace par
		if(numSelecCruce % 2 == 1)
			numSelecCruce--;

		for(int i = 0; i< numSelecCruce; i+=2){

			Cromosoma padre1 = this.poblacionSeleccionada[selCruce[i]];
			Cromosoma padre2 = this.poblacionSeleccionada[selCruce[i+1]];
			//Cromosoma[] hijos = cruce(padre1, padre2, puntoCruce);
			Cromosoma[] hijos = cruce(padre1, padre2);
			
			//REMPLAZO los nuevos individuos sustituyen a sus progenitores
			//tenemos con lo cual un remplazo inmediato basado en aptitud.
			this.poblacionSeleccionada[selCruce[i]] = hijos[0];
			this.poblacionSeleccionada[selCruce[i+1]] = hijos[1];
		}

		//una vez efectuado el remplazo de la descendencia en la poblacion seleccionada
		//actualizamos la poblacion.
		this.poblacion = poblacionSeleccionada;

	}
	
	private Cromosoma[] cruce (Cromosoma padre1, Cromosoma padre2){
		if (this.cruzador != null) return cruzador.cruce(padre1, padre2);
		else return null;
	}
	
	public void mutacion() {
		if (this.mutador!=null) this.poblacion = mutador.mutacion(poblacion, probabilidadMutacion);
	}
	
	public void almacenarElite(double porcentajeElite) {
		this.numeroElite = (int)(this.tamañoPoblacion * porcentajeElite);
		this.elite = new CromosomaHormigas[numeroElite];

		//en la poblacion "sortedPop", los individuos estan ordenados de peor a mejor
		Cromosoma[] sortedPop = Ordenacion.sortSelectionIndividuals(poblacion, evaluador);

		//guardamos los "numeroElite" mejores en el array de la elite
		for (int i = 0; i<numeroElite; i++){
			this.elite[i] = sortedPop[sortedPop.length-i-1].copiarCromosoma();
		}
	}

	public void recuperarElite() {
		//en la poblacion "sortedPop", los individuos estan ordenados de peor a mejor
		Cromosoma[] sortedPop = Ordenacion.sortSelectionIndividuals(poblacion, evaluador);
		this.poblacion = sortedPop;
		
		//recuperamos la elite, sustituyendo los peores individuos (estan colocados al principio):
		for (int i = 0; i<this.numeroElite;i++){
			this.poblacion[i] = this.elite[i];
		}
	}
	
	public double mediaPoblacionInstantanea() {
		return Poblacion.mediaPoblacionInstantanea(poblacion);
	}

	public Cromosoma mejorPoblacionInstantanea() {
		return Poblacion.mejorPoblacionInstantanea(poblacion, evaluador);
	}
	
	/***************************** GETTERS Y SETTERS *******************************/

	//generamos aqui las instancias de los operadores concretos del patron Strategy
	public void setCruzador(int tipoCruce) {
		switch(tipoCruce){
		case CRUCE_ARBOREO: this.cruzador = new CruzadorArboreo(); break;
		default: this.cruzador = new CruzadorArboreo();
		}
	}

	public void setMutador(int tipoMutacion) {
		switch(tipoMutacion){
		case MUTACION_TERMINAL: this.mutador = new MutacionTerminalSimple(); break;
		case MUTACION_FUNCIONAL: this.mutador = new MutacionFuncionalSimple(); break;
		case MUTACION_ARBOL: this.mutador = new MutacionArbol(); break;
		default: this.mutador = new MutacionArbol();
		}
	}

	public void setSeleccionador(int tipoSeleccion) {
		switch(tipoSeleccion){
		case SELECCION_RULETA: this.seleccionador = new SeleccionRuleta(); break;
		case SELECCION_TORNEO: this.seleccionador = new SeleccionTorneo(); break;
		default:  this.seleccionador = new SeleccionRuleta();
		}
	}

}
