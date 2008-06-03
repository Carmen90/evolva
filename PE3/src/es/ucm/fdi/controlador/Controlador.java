package es.ucm.fdi.controlador;

import es.ucm.fdi.algoritmos.AGenetico;
import es.ucm.fdi.algoritmos.AGeneticoHormigas;
import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.*;
import es.ucm.fdi.gui.*;

public class Controlador {

	//Valores para ejecucion basica
	public static int POBLACION_DEFECTO = 200;
	public static int GENERACIONES_DEFECTO = 100;
	public static double CRUCE_DEFECTO = 0.7;
	public static double MUTACION_DEFECTO = 0.2;
	public static double ELITISMO_DEFECTO = 0.02;

	public double[] mejores;
	public double[] mejoresParciales;
	public double[] medias;

	private boolean elitismo;

	public Controlador(){}

	public Cromosoma[] ejecucionSencilla(int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion,
			 boolean contractividad, boolean escalado){
		
		Evaluador e = new EvaluadorHormigas();

		Cromosoma[] resultados = algoritmoGenetico(e, numGeneraciones, tamPoblacion, probCruce, 
				probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
				contractividad, escalado);
		
		return resultados;

		//tenemos varias graficas, todas deben conocer al controlador para solicitar datos del ultimo modelo 
		//generado. No obstante, el controlador no necesita detalles de las vistas graficas, por eso lo creamos aqui
		/*
		Grafica2D grafica = new Grafica2D();
		grafica.setControlador(this);
		grafica.generarGrafica(resultados[0], mejores, mejoresParciales, medias, numGeneraciones);
		*/
	}
	
	/*
	 * return: Cromosoma[2].
	 * 		Cromosoma[0] -> El Mejor Global
	 * 		Cromosoma[1] -> El mejor en la ultima iteracion (Aptitud)
	 */
	private Cromosoma[] algoritmoGenetico(Evaluador e, int numGeneraciones, int tamPoblacion, double probCruce, 
			double probMutacion, boolean eli, double porcentajeElite, int tipoMutacion, int tipoCruce, int tipoSeleccion,
			boolean contractividad, boolean escalado){
		
		Cromosoma[] resultados = new Cromosoma[2];
		this.elitismo = eli;
		
		double mediaPoblacionAnterior = 0.0;

		mejores = new double[numGeneraciones];
		medias = new double[numGeneraciones];
		mejoresParciales = new double[numGeneraciones];

		AGeneticoHormigas AG = new AGeneticoHormigas(tamPoblacion,numGeneraciones,
				probCruce,probMutacion,tipoMutacion,tipoCruce,tipoSeleccion);
		
		AG.inicializar(e); //crea población inicial de cromosomas
		AG.evaluarPoblacion(escalado);//evalúa los individuos y coge el mejor
		//-------------> POBLACION ORDENADA <------------------//
		mediaPoblacionAnterior = AG.mediaPoblacionInstantanea();

		while (!AG.terminado()) {

			//antes de la seleccion, separamos los mejores individuos, calculados a partir
			//del porcentaje introducido por el usuario.
			if (elitismo) AG.almacenarElite(porcentajeElite);
			//-------------> POBLACION ORDENADA <------------------//

			AG.seleccion();
			AG.reproduccion();
			AG.mutacion();
			//POBLACION DESORDENADA

			//antes de reevaluar la poblacion, incluimos de nuevo los  mejores individuos
			//sustituyendo los peores individuos generados en el proceso.
			if (elitismo) AG.recuperarElite();
			//-------------> POBLACION ORDENADA <------------------//

			AG.evaluarPoblacion(escalado);
			//-------------> POBLACION ORDENADA <------------------//

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

	public boolean getElitismo() {
		return elitismo;
	}

}