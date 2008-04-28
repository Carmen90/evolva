package es.ucm.fdi.controlador;

import es.ucm.fdi.algoritmos.AGeneticoViajante;
import es.ucm.fdi.evaluadores.*;
import es.ucm.fdi.gui.Grafica2D;

public class Controlador {

	//Valores para ejecucion basica
	public static int POBLACION_DEFECTO = 100;
	public static int GENERACIONES_DEFECTO = 100;
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

	public void recogerDatosGUI(int numGenes, int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite){

		this.elitismo = eli;

		mejores = new double[numGeneraciones];
		medias = new double[numGeneraciones];
		mejoresParciales = new double[numGeneraciones];

		AGeneticoViajante AG = new AGeneticoViajante(tamPoblacion,numGeneraciones,
				probCruce,probMutacion,0,0,0);
		Evaluador f = new EvaluadorViajante();

		AG.inicializar(f); //crea poblaci�n inicial de cromosomas
		AG.evaluarPoblacion();//eval�a los individuos y coge el mejor

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
			mejoresParciales[AG.getGeneracionActual()] = AG.mejorPoblacionInstantanea();

			AG.setGeneracionActual(AG.getGeneracionActual()+1);
		}

		/*System.out.println("Este ha sido el mejor valor hallado para la funcion: "+ AG.getElMejor().getAptitud());
		System.out.println("Algoritmo finalizado!");*/

		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGrafica(AG.getElMejor(), mejores, mejoresParciales, medias, numGeneraciones);
	}


	/*private void ejecucionSencilla(){
		
	}
	
	private void ejecucionMultiple(){
		
	}*/

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