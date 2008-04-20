package es.ucm.fdi.algoritmos;

import java.util.Vector;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class AGeneticoSimpleImpl extends AGenetico{

	public AGeneticoSimpleImpl() {
		super();
	}

	public AGeneticoSimpleImpl(int tamañoPoblacion, int numMaxGeneraciones, double probabilidadCruce, double probabilidadMutacion, double tolerancia) {
		super(tamañoPoblacion, numMaxGeneraciones, probabilidadCruce, probabilidadMutacion, tolerancia);
	}

	public void inicializar(Evaluador funcionEvaluacion) {

		this.evaluador = funcionEvaluacion;

		//creamos la poblacion aleatoriamente, para cada cromosoma, lo creamos aleatoriamente y 
		//ellos mismos diran a su genes que se creen aleatoriamente.

		for (int j = 0; j< tamañoPoblacion;j++){
			//generamos e inicializamos cada individuo de la poblacion
			poblacion[j] = evaluador.generarCromosomaAleatorio(this.tolerancia);		
		}

		//inicializamos el mejor al primer individuo de la poblacion.		
		elMejor = poblacion[0];
		posicionMejorCromosoma = 0;

	}

	public void evaluarPoblacion() {
		double puntAcumulada = 0.0; //puntuacion acumulada
		double aptitudMejor = 0.0; //mejor aptitud
		double sumaAptitud = 0.0; //suma de la aptitud

		//...

		//revisamos contadores de aptitud relativa y puntuacion acumulada de los 
		//individuos de la poblacion. Calculamos la posicion del mejor individuo
		for (int i = 0; i< tamañoPoblacion; i++){

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

	//seleccion por ruleta
	public void seleccion() {

		this.poblacionSeleccionada = new Cromosoma[tamañoPoblacion];

		double probSeleccion;
		int posSuperviviente;

		for(int i =0; i<tamañoPoblacion; i++){
			probSeleccion = Math.random();
			posSuperviviente = 0;

			while(probSeleccion > poblacion[posSuperviviente].getPuntuacionAcumulada() && 
					posSuperviviente < tamañoPoblacion){
				posSuperviviente++;
			}
			//copiamos el individuo seleccionado a la poblacion seleccionada auxiliar
			poblacionSeleccionada[i] = poblacion[posSuperviviente].copiarCromosoma();
		}
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

		//Se cruzan los individuos elegidos en un punto al azar
		//cogemos la longitud del cromosoma del mejor cromosoma por ejemplo


		/*if (elMejor.getNumeroGenes() == 1){
			int longCromosoma = this.elMejor.getLongitudCromosoma();
			puntoCruce = MyRandom.aleatorioEntero(0,longCromosoma);
		}else{
			int puntoCruceGen = MyRandom.aleatorioEntero(0, elMejor.getNumeroGenes()); 
			puntoCruce = 0;
			for (int i = 0; i< puntoCruceGen; i++){
				puntoCruce += elMejor.getGenes()[i].getLongitud();
			}
		}*/

		for(int i = 0; i< numSelecCruce; i+=2){

			Cromosoma padre1 = this.poblacionSeleccionada[selCruce[i]];
			Cromosoma padre2 = this.poblacionSeleccionada[selCruce[i+1]];
			//Cromosoma[] hijos = cruce(padre1, padre2, puntoCruce);
			Cromosoma[] hijos = cruceVariosGenes(padre1, padre2);
			
			//REMPLAZO los nuevos individuos sustituyen a sus progenitores
			//tenemos con lo cual un remplazo inmediato basado en aptitud.
			this.poblacionSeleccionada[selCruce[i]] = hijos[0];
			this.poblacionSeleccionada[selCruce[i+1]] = hijos[1];
		}

		//una vez efectuado el remplazo de la descendencia en la poblacion seleccionada
		//actualizamos la poblacion.
		this.poblacion = poblacionSeleccionada;
	}

	@SuppressWarnings("unused")
	private Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2, int puntoCruce) {

		//crear cromosoma, crear genes, setear genes, inicializar cromosoma

		int numeroGenes = padre1.getNumeroGenes();
		Cromosoma hijo1 = new CromosomaBinario(numeroGenes); 
		Cromosoma hijo2 = new CromosomaBinario(numeroGenes);

		boolean[] desglosePadre1 = ((CromosomaBinario)padre1).desglosarCromosoma();
		boolean[] desglosePadre2 = ((CromosomaBinario)padre2).desglosarCromosoma();

		boolean[] desgloseHijo1 = new boolean[padre1.getLongitudCromosoma()];
		boolean[] desgloseHijo2 = new boolean[padre2.getLongitudCromosoma()];

		// primera parte del intercambio: 1 a 1 y 2 a 2

		for (int i = 0; i < puntoCruce; i++){
			desgloseHijo1[i] = desglosePadre1[i];
			desgloseHijo2[i] = desglosePadre2[i];
		}

		// segunda parte: 1 a 2 y 2 a 1
		for (int i = puntoCruce; i < padre1.getLongitudCromosoma(); i++){
			desgloseHijo1[i] = desglosePadre2[i];
			desgloseHijo2[i] = desglosePadre1[i];
		}

		//se compactan los hijos con la misma configuracion de genes que los padres.
		hijo1.setGenes(((CromosomaBinario)padre1).compactarCromosoma(desgloseHijo1));
		hijo2.setGenes(((CromosomaBinario)padre2).compactarCromosoma(desgloseHijo2));

		// se evalúan
		hijo1.inicializarCromosoma(evaluador);
		hijo2.inicializarCromosoma(evaluador);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;

	}

	private Cromosoma[] cruceVariosGenes (Cromosoma padre1, Cromosoma padre2){
		//crear cromosoma, crear genes, setear genes, inicializar cromosoma

		int numeroGenes = padre1.getNumeroGenes();
		//creamos los cromosomas hijos
		Cromosoma hijo1 = new CromosomaBinario(numeroGenes); 
		Cromosoma hijo2 = new CromosomaBinario(numeroGenes);
		
		//creamos el array de genes de los hijos
		GenBinario[] genesHijo1 = new GenBinario[padre1.getNumeroGenes()];
		GenBinario[] genesHijo2 = new GenBinario[padre1.getNumeroGenes()];
		
		//para cada gen de cada cromosoma padre, calculamos un punto de cruce y los cruzamos por separado.
		for (int i = 0; i < padre1.getNumeroGenes(); i++){
			//obtenemos el genBinario de los padres 1 y 2
			GenBinario genPadre1I = (GenBinario) padre1.getGenes()[i];
			GenBinario genPadre2I = (GenBinario) padre2.getGenes()[i];
			
			//creamos el genBinario hijo1 e hijo2
			GenBinario genHijo1I = new GenBinario(genPadre1I.getLongitud(),genPadre1I.getXMax(),genPadre1I.getXMin());
			GenBinario genHijo2I = new GenBinario(genPadre1I.getLongitud(),genPadre1I.getXMax(),genPadre1I.getXMin());
			
			//obtenemos la codificacion del gen i de cada padre
			boolean[] codificacionPadre1I = genPadre1I.getGen();
			boolean[] codificacionPadre2I = genPadre2I.getGen();

			int longGenI = padre1.getLongitudGenes()[i];
			int puntoCruceI = MyRandom.aleatorioEntero(0,longGenI);
			
			//creamos la codificacion del gen i de cada hijo
			boolean[] codificacionHijo1I = new boolean[longGenI];
			boolean[] codificacionHijo2I = new boolean[longGenI];

			// primera parte del intercambio: 1 a 1 y 2 a 2

			for (int j = 0; j < puntoCruceI; j++){
				codificacionHijo1I[j] = codificacionPadre1I[j];
				codificacionHijo2I[j] = codificacionPadre2I[j];
			}

			// segunda parte: 1 a 2 y 2 a 1
			for (int j = puntoCruceI; j < longGenI; j++){
				codificacionHijo1I[j] = codificacionPadre2I[j];
				codificacionHijo2I[j] = codificacionPadre1I[j];
			}
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;
		}
		//se compactan los hijos con la misma configuracion de genes que los padres.
		hijo1.setGenes(genesHijo1);
		hijo2.setGenes(genesHijo2);

		// se evalúan
		hijo1.inicializarCromosoma(evaluador);
		hijo2.inicializarCromosoma(evaluador);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;


	}


	public void mutacion() {
		boolean mutado;
		double probabilidad;

		for (int i = 0; i< tamañoPoblacion; i++){
			mutado = false;

			CromosomaBinario cromosomaI = (CromosomaBinario)this.poblacion[i];
			boolean[] desglosePadreI = cromosomaI.desglosarCromosoma();

			for (int j = 0; j< cromosomaI.getLongitudCromosoma(); j++){
				// se genera un numero aleatorio en [0 1)
				probabilidad = Math.random();
				// mutan los genes con prob<prob_mut
				if (probabilidad < this.probabilidadMutacion){
					desglosePadreI[j] = !desglosePadreI[j];
					mutado = true;
				}
			}

			if (mutado){
				cromosomaI.setGenes( cromosomaI.compactarCromosoma(desglosePadreI));
				cromosomaI.setAptitud(evaluador.evaluaAptitud(cromosomaI));
			}
		}
	}


	public void almacenarElite(double porcentajeElite) {
		this.numeroElite = (int)(this.tamañoPoblacion * porcentajeElite);
		this.elite = new CromosomaBinario[numeroElite];

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

