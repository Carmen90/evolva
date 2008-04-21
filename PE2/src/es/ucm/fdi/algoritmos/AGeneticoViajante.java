package es.ucm.fdi.algoritmos;

import java.util.Vector;

import es.ucm.fdi.algoritmos.cruzadores.Cruzador;
import es.ucm.fdi.algoritmos.mutadores.Mutador;
import es.ucm.fdi.algoritmos.seleccionadores.Seleccionador;
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
	//TODO incluir variantes CRUCE_OX
	
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
		
	protected Cruzador cruzador;
	protected Mutador mutador;
	protected Seleccionador seleccionador;
	
	public AGeneticoViajante() {
		super();
	}

	public AGeneticoViajante(int tamañoPoblacion, int numMaxGeneraciones, double probabilidadCruce, double probabilidadMutacion, double tolerancia,
							int tipoMutacion, int tipoCruce, int tipoSeleccion) {
		super(tamañoPoblacion, numMaxGeneraciones, probabilidadCruce, probabilidadMutacion, tolerancia);
		setCruzador(tipoCruce);
		setMutador(tipoMutacion);
		setSeleccionador(tipoSeleccion);
	}

	public void evaluarPoblacion() {
		// TODO Auto-generated method stub
		
	}

	public void inicializar(Evaluador funcionEvaluacion) {
		// TODO Auto-generated method stub
		
	}

	public void seleccion() {
		if (this.seleccionador != null) this.poblacionSeleccionada = seleccionador.seleccion(poblacion);
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
		if (this.mutador!=null) this.poblacion = mutador.mutacion(poblacion);
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
	
	//TODO implementar los metodos de set con switch
	//generar aqui las instancias de los operadores concretos del patron Strategy
	public void setCruzador(int tipoCruce) {
		/*public static final int CRUCE_PMX = 0;
		public static final int CRUCE_CX = 1;
		public static final int CRUCE_ERX = 2;
		public static final int CRUCE_ORDINAL = 3;
		public static final int CRUCE_OX = 4;
		*/
		//TODO incluir variantes CRUCE_OX
	}

	public void setMutador(int tipoMutacion) {
		/*public static final int SELECCION_RULETA = 0;
		public static final int SELECCION_RANKING = 1;
		public static final int SELECCION_TORNEO = 2;
		*/
	}

	public void setSeleccionador(int tipoSeleccion) {
		/*public static final int MUTACION_INSERCION = 0;
		public static final int MUTACION_INTERCAMBIO = 1;
		public static final int MUTACION_INVERSION = 2;
		*/
	}
	
	public double mediaPoblacionInstantanea(){
		double totalAptitudes = 0.0;
		for (int i = 0; i < this.tamañoPoblacion; i++){
			totalAptitudes += poblacion[i].getAptitud();
		}
		return totalAptitudes / this.tamañoPoblacion;

	}

	public double mejorPoblacionInstantanea() {
		double mejorAptitud = 0.0;
		for (int i = 0; i < this.tamañoPoblacion; i++){
			if (evaluador.esMejorAptitud(poblacion[i].getAptitud(),mejorAptitud)){
				mejorAptitud = poblacion[i].getAptitud();
			}
		}
		return mejorAptitud;
	}

}
