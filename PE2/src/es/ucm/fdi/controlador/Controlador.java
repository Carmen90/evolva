package es.ucm.fdi.controlador;

import es.ucm.fdi.algoritmos.AGeneticoViajante;
import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.*;
import es.ucm.fdi.gui.*;

public class Controlador {

	//Valores para ejecucion basica
	public static int POBLACION_DEFECTO = 5;//100;
	public static int GENERACIONES_DEFECTO = 5;//100;
	public static double CRUCE_DEFECTO = 0.6;
	public static double MUTACION_DEFECTO = 0.1;
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

	public void recogerDatosGUI(){

		
	}


	public void ejecucionSencilla(int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion){
		
		Cromosoma[] resultados = algoritmoGenetico(numGeneraciones, tamPoblacion, probCruce, 
				probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion);
		

		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGrafica(resultados[0], mejores, mejoresParciales, medias, numGeneraciones);
	}
	
	public void ejecucionMultiple(int parametro, double inicio, double fin, double incremento, 
								  int numGeneraciones, int tamPoblacion, double probCruce, 
								  double probMutacion, boolean eli, double porcentajeElite,
								  int tipoMutacion, int tipoCruce, int tipoSeleccion){
		
		String parametroS = "";
		switch (parametro){
		case Gui.tamañoPoblacion:{
			parametroS = "tamaño de la población";
			break;
		}
		case Gui.numGeneraciones:{
			parametroS = "número de generaciones";
			break;
		}
		case Gui.probCruce:{
			parametroS = "probabilidad de cruce";
			break;
		}
		case Gui.probMutacion:{
			parametroS = "probabilidad de mutación";
			break;
		}
		case Gui.elitismo:{
			parametroS = "elitismo";
			break;
		}
		default:
		}
				
		Cromosoma[] resultados = algoritmoGenetico(numGeneraciones, tamPoblacion, probCruce, 
				probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion);
	
		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGraficaMultiple(parametroS,null, null,null ,null );
	}
	
	/*
	 * return: Cromosoma[2].
	 * 		Cromosoma[0] -> El Mejor Global
	 * 		Cromosoma[1] -> El mejor en la ultima iteracion (Aptitud)
	 */
	private Cromosoma[] algoritmoGenetico(int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion){
		
		Cromosoma[] resultados = new Cromosoma[2];
		this.elitismo = eli;

		mejores = new double[numGeneraciones];
		medias = new double[numGeneraciones];
		mejoresParciales = new double[numGeneraciones];

		AGeneticoViajante AG = new AGeneticoViajante(tamPoblacion,numGeneraciones,
				probCruce,probMutacion,tipoMutacion,tipoCruce,tipoSeleccion);
		Evaluador f = new EvaluadorViajante();

		AG.inicializar(f); //crea población inicial de cromosomas
		AG.evaluarPoblacion();//evalúa los individuos y coge el mejor

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

			AG.evaluarPoblacion();

			//despues de evaluar la nueva generacion, recopilamos los datos de dicha generacion.
			medias[AG.getGeneracionActual()] = AG.mediaPoblacionInstantanea();
			mejores[AG.getGeneracionActual()] = AG.getElMejor().getAptitud();
			mejoresParciales[AG.getGeneracionActual()] = AG.mejorPoblacionInstantanea().getAptitud();

			AG.setGeneracionActual(AG.getGeneracionActual()+1);
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