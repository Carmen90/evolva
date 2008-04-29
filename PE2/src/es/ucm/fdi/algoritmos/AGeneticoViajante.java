package es.ucm.fdi.algoritmos;

import java.util.Vector;

import es.ucm.fdi.algoritmos.cruzadores.*;
import es.ucm.fdi.algoritmos.mutadores.*;
import es.ucm.fdi.algoritmos.seleccionadores.*;
import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;

public class AGeneticoViajante extends AGenetico{
	
	/*
	 * Tipos de Cruce 
	 */
	public static final int CRUCE_PMX = 0;
	public static final int CRUCE_CX = 1;
	public static final int CRUCE_ERX = 2;
	public static final int CRUCE_ORDINAL = 3;
	public static final int CRUCE_OX = 4;
	public static final int CRUCE_OXOrdenPrioritario = 5;
	public static final int CRUCE_OXPosicionesPrioritarias = 6;
	
	/*
	 * Tipos de Selección
	 */
	public static final int SELECCION_RULETA = 0;
	public static final int SELECCION_RANKING = 1;
	public static final int SELECCION_TORNEO = 2;
	
	/*
	 * Tipos de Mutación
	 */
	public static final int MUTACION_INSERCION = 0;
	public static final int MUTACION_INTERCAMBIO = 1;
	public static final int MUTACION_INVERSION = 2;
		
	private Cruzador cruzador;
	private Mutador mutador;
	private Seleccionador seleccionador;
	private Evaluador evaluador;
	
	private double probabilidadMutacion;
	
	public AGeneticoViajante() {
		super();
	}

	public AGeneticoViajante(int tamañoPoblacion, int numMaxGeneraciones, double probabilidadCruce, double probabilidadMutacion,
							int tipoMutacion, int tipoCruce, int tipoSeleccion) {
		super(tamañoPoblacion, numMaxGeneraciones, probabilidadCruce, probabilidadMutacion, 0);
		setCruzador(tipoCruce);
		setMutador(tipoMutacion);
		setSeleccionador(tipoSeleccion);
	}

	//TODO INCLUIR MEJORAS DE DESPLAZAMIENTO Y ESCALADO DE APTITUD
	public void evaluarPoblacion() {
		double puntAcumulada = 0.0; //puntuacion acumulada
		double sumaAptitud = 0.0; //suma de la aptitud
		
		double aptitudMejor = poblacion[0].getAptitud(); //mejor aptitud
		this.posicionMejorCromosoma = 0;

		//revisamos contadores de aptitud relativa y puntuacion acumulada de los 
		//individuos de la poblacion. Calculamos la posicion del mejor individuo
		for (int i = 1; i< tamañoPoblacion; i++){

			double aptitudI = poblacion[i].getAptitud();
			
			if ( evaluador.esMejorAptitud(aptitudI, aptitudMejor) ){
				this.posicionMejorCromosoma = i;
				aptitudMejor = aptitudI;
			}

		}

		double aptitudMenor = poblacion[0].getAptitud();
		double[] aptitudesPositivas = new double[this.tamañoPoblacion];
		//calculamos primero la aptitud menor:
		for (int i = 1; i< this.tamañoPoblacion; i++){
			if (poblacion[i].getAptitud() < aptitudMenor) aptitudMenor = poblacion[i].getAptitud();
		}
		//si la aptitud menor es negativa, eliminamos todos los valores negativos de aptitud y 
		//recalculamos la suma de aptitudes.
		if (aptitudMenor < 0){
			for (int i = 0; i< this.tamañoPoblacion; i++){
				double aptitudPositivaI = poblacion[i].getAptitud() + Math.abs(aptitudMenor);
				aptitudesPositivas[i] = aptitudPositivaI;
			}
		//sino, simplemente copiamos los valores de aptitud al array de aptitudes positivas
		}else{
			for (int i = 0; i< this.tamañoPoblacion; i++){
				double aptitudPositivaI = poblacion[i].getAptitud();
				aptitudesPositivas[i] = aptitudPositivaI;
			}
		}
		
		//si el problema es de minimizacion, debemos asignar mejores puntuaciones a los individuos con
		//menor aptitud luego transformamos el problema de minimizacion en un problema de maximizacion
		double[] aptitudesMaximizadas = new double[aptitudesPositivas.length];
		aptitudesMaximizadas = evaluador.transformarAptitudesAMaximizacion(aptitudesPositivas);
		aptitudesPositivas = aptitudesMaximizadas;
		
		//calculamos la suma de las aptitudes
		for (int i = 0; i< aptitudesPositivas.length; i++){
			sumaAptitud += aptitudesPositivas[i];
		}
		//calculo de puntuaciones y puntuaciones acumuladas
		for (int i = 0; i< tamañoPoblacion; i++){
			
			double aptitudI = aptitudesPositivas[i];
			
			poblacion[i].setPuntuacion(aptitudI/sumaAptitud);
			double acumulacion = poblacion[i].getPuntuacion() + puntAcumulada;
			poblacion[i].setPuntuacionAcumulada(acumulacion);
			puntAcumulada = acumulacion;
		
		
		}

		//Si el mejor de esta generacion es mejor que el mejor que tenia de antes
		//pues lo actualizamos
		if ( evaluador.esMejorAptitud(aptitudMejor,elMejor.getAptitud())) {
			elMejor = poblacion[posicionMejorCromosoma];
		}
		
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
		if (this.cruzador != null) return cruzador.cruce(padre1, padre2, evaluador);
		else return null;
	}

	public void mutacion() {
		if (this.mutador!=null) this.poblacion = mutador.mutacion(poblacion, probabilidadMutacion);
	}

	public void almacenarElite(double porcentajeElite) {
		this.numeroElite = (int)(this.tamañoPoblacion * porcentajeElite);
		this.elite = new CromosomaEnteroViajante[numeroElite];

		//ordenamos la poblacion:
		ordenarPoblacion();

		//guardamos los "numeroElite" mejores en el array de la elite
		for (int i = 0; i<numeroElite; i++){
			this.elite[i] = poblacion[i].copiarCromosoma();
		}
	}

	public void recuperarElite() {
		//ordenamos la poblacion:
		ordenarPoblacion();

		//recuperamos la elite, sustituyendo los peores individuos:
		int contadorElite = 0;
		for (int i = this.tamañoPoblacion-this.numeroElite; i<this.tamañoPoblacion;i++){
			this.poblacion[i] = this.elite[contadorElite];
			contadorElite++;
		}

	}

	private void ordenarPoblacion(){
		//para ordenar la poblacion, la recorremos entera, y vamos insertando ordenadamente 
		//en funcion del criterio de la funcion, los individuos en un array auxiliar.
		//despues, seteamos la poblacion ordenada...
		Vector <Cromosoma> ordenado = new Vector <Cromosoma>();

		//metemos el primer cromosoma directamente en el vector
		ordenado.add(this.poblacion[0]);
		//para los demas...
		for (int i = 1; i< this.tamañoPoblacion; i++){
			//individuo que vamos a insertar
			Cromosoma individuoI = this.poblacion[i];

			//buscamos donde insertar el nuevo elemento
			int j = 0;
			boolean indiceEncontrado = false;
			while (!indiceEncontrado && j < ordenado.size()){

				Cromosoma individuoOrdenadoJ = ordenado.get(j);
				indiceEncontrado = evaluador.esMejorAptitud(individuoI.getAptitud(), individuoOrdenadoJ.getAptitud());
				j++;
			}
			//si hemos encontrado un indice apto para la insercion
			if (indiceEncontrado){
				ordenado.insertElementAt(individuoI, --j);
			}else ordenado.add(individuoI);
		}
		//una vez ordenados en el vector, seteamos la poblacion.
		for (int i = 0; i < this.tamañoPoblacion; i++){
			this.poblacion[i] = ordenado.get(i);
		}

	}
	
	//generamos aqui las instancias de los operadores concretos del patron Strategy
	public void setCruzador(int tipoCruce) {
		switch(tipoCruce){
		case CRUCE_PMX: this.cruzador = new CruzadorPMX(); break;
		case CRUCE_CX: this.cruzador = new CruzadorCX(); break;
		case CRUCE_ERX: this.cruzador = new CruzadorERX(); break;
		case CRUCE_ORDINAL: this.cruzador = new CruzadorCodOrdinal(); break;
		case CRUCE_OX: this.cruzador = new CruzadorOX(); break;
		case CRUCE_OXOrdenPrioritario: this.cruzador = new CruzadorOXOrdenPrioritario(); break;
		case CRUCE_OXPosicionesPrioritarias: this.cruzador = new CruzadorOXPosicionesPrioritarias(); break;
		default: this.cruzador = new CruzadorPMX();
		}
	}

	public void setMutador(int tipoMutacion) {
		switch(tipoMutacion){
		case MUTACION_INSERCION: this.mutador = new MutadorInsercion(); break;
		case MUTACION_INTERCAMBIO: this.mutador = new MutadorIntercambio(); break;
		case MUTACION_INVERSION: this.mutador = new MutadorInversion(); break;
		default: this.mutador = new MutadorInsercion();
		}
	}

	public void setSeleccionador(int tipoSeleccion) {
		switch(tipoSeleccion){
		case SELECCION_RULETA: this.seleccionador = new SeleccionRuleta(); break;
		case SELECCION_RANKING: this.seleccionador = new SeleccionRanking(); break;
		case SELECCION_TORNEO: this.seleccionador = new SeleccionTorneo(); break;
		default:  this.seleccionador = new SeleccionRuleta();
		}
	}
	
	public double mediaPoblacionInstantanea(){
		double totalAptitudes = 0.0;
		for (int i = 0; i < this.tamañoPoblacion; i++){
			totalAptitudes += poblacion[i].getAptitud();
		}
		return totalAptitudes / this.tamañoPoblacion;

	}

	public Cromosoma mejorPoblacionInstantanea() {
		double mejorAptitud = poblacion[0].getAptitud();
		int posicionMejorAptitud = 0;
		for (int i = 1; i < this.tamañoPoblacion; i++){
			if (evaluador.esMejorAptitud(poblacion[i].getAptitud(),mejorAptitud)){
				mejorAptitud = poblacion[i].getAptitud();
				posicionMejorAptitud = i;
			}
		}
		return poblacion[posicionMejorAptitud];
	}

}
