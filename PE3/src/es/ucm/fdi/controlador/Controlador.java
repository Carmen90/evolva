package es.ucm.fdi.controlador;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.*;
import es.ucm.fdi.gui.*;

public class Controlador {

	//Valores para ejecucion basica
	public static int POBLACION_DEFECTO = 300;
	public static int GENERACIONES_DEFECTO = 100;
	public static double CRUCE_DEFECTO = 0.7;
	public static double MUTACION_DEFECTO = 0.12;
	public static double TOLERANCIA_DEFECTO = 0.0001;
	public static double ELITISMO_DEFECTO = 0.02;

	//Valores para la ejecucion multiple
	public static double[] POBLACION_MULTIPLE_DEFECTO = {100,1000,100};
	public static double[] GENERACIONES_MULTIPLE_DEFECTO = {100,500,50};
	public static double[] CRUCE_MULTIPLE_DEFECTO = {0.1,0.9,0.05};
	public static double[] MUTACION_MULTIPLE_DEFECTO = {0.01,0.5,0.01};
	public static double[] ELITISMO_MULTIPLE_DEFECTO = {0.01,0.3,0.01};

	//Valores para guardar los datos que mete el usuario y recordarlos
	private double[] copiaPoblacionMultiple;
	private double[] copiaGeneracionesMultiple;
	private double[] copiaCruceMultiple;
	private double[] copiaMutacionMultiple;
	private double[] copiaElitismoMultiple;

	public double[] mejores;
	public double[] mejoresParciales;
	public double[] medias;

	private int numeroFuncion;
	private boolean elitismo;

	public Controlador(){
		copiaPoblacionMultiple = POBLACION_MULTIPLE_DEFECTO;
		copiaGeneracionesMultiple = GENERACIONES_MULTIPLE_DEFECTO;
		copiaCruceMultiple = CRUCE_MULTIPLE_DEFECTO;
		copiaMutacionMultiple = MUTACION_MULTIPLE_DEFECTO;
		copiaElitismoMultiple = ELITISMO_MULTIPLE_DEFECTO;
	}

	public void ejecucionSencilla(int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion,
			 boolean contractividad, boolean presionSelectiva, boolean escalado){
		
		Evaluador e = new EvaluadorViajante();
		
		Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, tamPoblacion, probCruce, 
				probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
				contractividad, presionSelectiva, escalado);
		

		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGrafica(resultados[0], mejores, mejoresParciales, medias, numGeneraciones);
	}
	
	public void ejecucionMultiple(int parametro, double inicio, double fin, double incremento, 
								  int numGeneraciones, int tamPoblacion, double probCruce, 
								  double probMutacion, boolean eli, double porcentajeElite,
								  int tipoMutacion, int tipoCruce, int tipoSeleccion,
								  boolean contractividad, boolean presionSelectiva, boolean escalado){
		
		Evaluador e = new EvaluadorViajante();
		
		int numIteraciones = (int)Math.round((fin - inicio) / incremento)+1;
		double[] iteraciones = new double[numIteraciones];
		double[] mejoresGlobales = new double[numIteraciones];
		double[] ultimosMejores = new double[numIteraciones];
		
		Cromosoma elMejorDeTodasLasIteraciones = null;
		
		String parametroS = "";
		switch (parametro){
		case Gui.tamañoPoblacion:{
			parametroS = "tamaño de la población";
			int numIt = 0;
			for (int i = (int)inicio; i < (int)fin; i += incremento){
				Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, i, probCruce, 
						probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
						contractividad, presionSelectiva, escalado);
				if (i == (int)inicio) elMejorDeTodasLasIteraciones = resultados[0];
				else{
					if (e.esMejorAptitud(resultados[0].getAptitud(), elMejorDeTodasLasIteraciones.getAptitud()))
						elMejorDeTodasLasIteraciones = resultados[0];
				}
				iteraciones[numIt] = i;
				mejoresGlobales[numIt] = resultados[0].getAptitud();
				ultimosMejores[numIt] = resultados[1].getAptitud();
				numIt++;
			}
			break;
		}
		case Gui.numGeneraciones:{
			parametroS = "número de generaciones";
			int numIt = 0;
			for (int i = (int)inicio; i < (int)fin; i += incremento){
				Cromosoma[] resultados = algoritmoGenetico(e, i, tamPoblacion, probCruce, 
						probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
						contractividad, presionSelectiva, escalado);
				if (i == (int)inicio) elMejorDeTodasLasIteraciones = resultados[0];
				else{
					if (e.esMejorAptitud(resultados[0].getAptitud(), elMejorDeTodasLasIteraciones.getAptitud()))
						elMejorDeTodasLasIteraciones = resultados[0];
				}
				iteraciones[numIt] = i;
				mejoresGlobales[numIt] = resultados[0].getAptitud();
				ultimosMejores[numIt] = resultados[1].getAptitud();
				numIt++;
			}
			break;
		}
		case Gui.probCruce:{
			parametroS = "probabilidad de cruce";
			int numIt = 0;
			for (double d = inicio; d < fin; d += incremento){
				Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, tamPoblacion, d, 
						probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
						contractividad, presionSelectiva, escalado);
				if (d == inicio) elMejorDeTodasLasIteraciones = resultados[0];
				else{
					if (e.esMejorAptitud(resultados[0].getAptitud(), elMejorDeTodasLasIteraciones.getAptitud()))
						elMejorDeTodasLasIteraciones = resultados[0];
				}
				iteraciones[numIt] = d;
				mejoresGlobales[numIt] = resultados[0].getAptitud();
				ultimosMejores[numIt] = resultados[1].getAptitud();
				numIt++;
			}
			break;
		}
		case Gui.probMutacion:{
			parametroS = "probabilidad de mutación";
			int numIt = 0;
			for (double d = inicio; d < fin; d += incremento){
				Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, tamPoblacion, probCruce, 
						d, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
						contractividad, presionSelectiva, escalado);
				if (d == inicio) elMejorDeTodasLasIteraciones = resultados[0];
				else{
					if (e.esMejorAptitud(resultados[0].getAptitud(), elMejorDeTodasLasIteraciones.getAptitud()))
						elMejorDeTodasLasIteraciones = resultados[0];
				}
				iteraciones[numIt] = d;
				mejoresGlobales[numIt] = resultados[0].getAptitud();
				ultimosMejores[numIt] = resultados[1].getAptitud();
				numIt++;
			}
			break;
		}
		case Gui.elitismo:{
			parametroS = "elitismo";
			int numIt = 0;
			for (double d = inicio; d < fin; d += incremento){
				Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, tamPoblacion, probCruce, 
						probMutacion, eli, d, tipoMutacion, tipoCruce, tipoSeleccion,
						contractividad, presionSelectiva, escalado);
				if (d == inicio) elMejorDeTodasLasIteraciones = resultados[0];
				else{
					if (e.esMejorAptitud(resultados[0].getAptitud(), elMejorDeTodasLasIteraciones.getAptitud()))
						elMejorDeTodasLasIteraciones = resultados[0];
				}
				iteraciones[numIt] = d;
				mejoresGlobales[numIt] = resultados[0].getAptitud();
				ultimosMejores[numIt] = resultados[1].getAptitud();
				numIt++;
			}
			break;
		}
		default:
		}
	
		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGraficaMultiple(parametroS, elMejorDeTodasLasIteraciones , mejoresGlobales, ultimosMejores, iteraciones);
	}
	
	/*
	 * return: Cromosoma[2].
	 * 		Cromosoma[0] -> El Mejor Global
	 * 		Cromosoma[1] -> El mejor en la ultima iteracion (Aptitud)
	 */
	private Cromosoma[] algoritmoGenetico(Evaluador e, int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion,
			boolean contractividad, boolean presionSelectiva, boolean escalado){
		
		//Esta es la mejora que le pasamos si es seleccion por ranking o ruleta para que evalue la poblacion 
		boolean mejora = presionSelectiva || escalado;
		
		Cromosoma[] resultados = new Cromosoma[2];
		this.elitismo = eli;
		
		double mediaPoblacionAnterior = 0.0;

		mejores = new double[numGeneraciones];
		medias = new double[numGeneraciones];
		mejoresParciales = new double[numGeneraciones];

		AGeneticoViajante AG = new AGeneticoViajante(tamPoblacion,numGeneraciones,
				probCruce,probMutacion,tipoMutacion,tipoCruce,tipoSeleccion);
		
		AG.inicializar(e); //crea población inicial de cromosomas
		AG.evaluarPoblacion(mejora);//evalúa los individuos y coge el mejor
		mediaPoblacionAnterior = AG.mediaPoblacionInstantanea();

		while (!AG.terminado()) {

			//antes de la seleccion, separamos los mejores individuos, calculados a partir
			//del porcentaje introducido por el usuario.
			if (elitismo) AG.almacenarElite(porcentajeElite);

			AG.seleccion();
			AG.reproduccion();
			AG.mutacion();

			//antes de reevaluar la poblacion, incluimos de nuevo los  mejores individuos
			//sustituyendo los peores individuos generados en el proceso.
			if (elitismo) AG.recuperarElite();

			AG.evaluarPoblacion(mejora);

			double mediaPoblacionActual = AG.mediaPoblacionInstantanea();
			if (contractividad){
				//si la media de aptitudes supera la media de la poblacion anterior, entonces continuamos
				//== PARA PREVENIR NO TERMINACION EN CASO DE ESTANCAMIENTO
				if (e.esMejorAptitud(mediaPoblacionActual, mediaPoblacionAnterior) ||
						mediaPoblacionActual == mediaPoblacionAnterior){ 
					//despues de evaluar la nueva generacion, recopilamos los datos de dicha generacion.
					medias[AG.getGeneracionActual()] = mediaPoblacionActual;
					mejores[AG.getGeneracionActual()] = AG.getElMejor().getAptitud();
					mejoresParciales[AG.getGeneracionActual()] = AG.mejorPoblacionInstantanea().getAptitud();
					mediaPoblacionAnterior = mediaPoblacionActual;
					
					AG.setGeneracionActual(AG.getGeneracionActual()+1);
				}
			}else{
				medias[AG.getGeneracionActual()] = mediaPoblacionActual;
				mejores[AG.getGeneracionActual()] = AG.getElMejor().getAptitud();
				mejoresParciales[AG.getGeneracionActual()] = AG.mejorPoblacionInstantanea().getAptitud();
				mediaPoblacionAnterior = mediaPoblacionActual;
				AG.setGeneracionActual(AG.getGeneracionActual()+1);
			}
		}
		//el mejor global
		resultados[0] = AG.getElMejor();
		//el mejor de la poblacion en la ultima iteracion del algoritmo
		resultados[1] = AG.mejorPoblacionInstantanea();
		return resultados;
	}

	public int getNumeroFuncion() {
		return numeroFuncion;
	}

	public boolean getElitismo() {
		return elitismo;
	}

	public double[] getCopiaPoblacionMultiple() {
		return copiaPoblacionMultiple;
	}

	public void setCopiaPoblacionMultiple(double[] copia) {
		this.copiaPoblacionMultiple = copia;
	}

	public double[] getCopiaGeneracionesMultiple() {
		return copiaGeneracionesMultiple;
	}

	public void setCopiaGeneracionesMultiple(double[] copia) {
		this.copiaGeneracionesMultiple = copia;
	}

	public double[] getCopiaCruceMultiple() {
		return copiaCruceMultiple;
	}

	public void setCopiaCruceMultiple(double[] copiaCruceMultiple) {
		this.copiaCruceMultiple = copiaCruceMultiple;
	}

	public double[] getCopiaMutacionMultiple() {
		return copiaMutacionMultiple;
	}

	public void setCopiaMutacionMultiple(double[] copiaMutacionMultiple) {
		this.copiaMutacionMultiple = copiaMutacionMultiple;
	}

	public double[] getCopiaElitismoMultiple() {
		return copiaElitismoMultiple;
	}

	public void setCopiaElitismoMultiple(double[] copiaElitismoMultiple) {
		this.copiaElitismoMultiple = copiaElitismoMultiple;
	}	

}