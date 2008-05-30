package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.utils.Ordenacion;
import es.ucm.fdi.utils.Poblacion;

public class SeleccionRuleta implements Seleccionador{
	
	public static final double C = 1.5; 
	//precondicion!! la poblacion inicial debe estar ordenada
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e) {
		int tamañoPoblacion = poblacionInicial.length;
		Cromosoma[] poblacionSeleccionada = new Cromosoma[tamañoPoblacion];

		double probSeleccion;
		int posSuperviviente;

		for(int i =0; i<tamañoPoblacion; i++){
			probSeleccion = Math.random();
			posSuperviviente = 0;

			while(probSeleccion > poblacionInicial[posSuperviviente].getPuntuacionAcumulada() && 
					posSuperviviente < tamañoPoblacion){
				posSuperviviente++;
			}
			//copiamos el individuo seleccionado a la poblacion seleccionada auxiliar
			poblacionSeleccionada[i] = poblacionInicial[posSuperviviente].copiarCromosoma();
		}
		return poblacionSeleccionada;
	}

	public Cromosoma[] generarSegmentos(Cromosoma[] poblacion, Evaluador evaluador, boolean mejora) {
		double puntAcumulada = 0.0; //puntuacion acumulada
		double sumaAptitud = 0.0; //suma de la aptitud
		int tamañoPoblacion = poblacion.length;
		
		double[] aptitudesEscaladas = new double[poblacion.length];
		double a = (C-1)*Poblacion.mediaPoblacionInstantanea(poblacion)/(Poblacion.mejorPoblacionInstantanea(poblacion, evaluador).getAptitud()-Poblacion.mediaPoblacionInstantanea(poblacion));
		double b = Poblacion.mediaPoblacionInstantanea(poblacion)*(1-a);
		
		for(int i = 0; i<tamañoPoblacion; i++){
			aptitudesEscaladas[i] = a*poblacion[i].getAptitud()+b;
		}
		
		
		//ordenamos la poblacion de peor a mejor aptitud.
		poblacion = Ordenacion.sortSelectionIndividuals(poblacion, evaluador);
				
		double[] aptitudesPositivas = new double[tamañoPoblacion];
		for (int i = 0; i< tamañoPoblacion; i++){
				double aptitudPositivaI = poblacion[i].getAptitud();
				aptitudesPositivas[i] = aptitudPositivaI;
		}
		
		//si el problema es de minimizacion, debemos asignar mejores puntuaciones a los individuos con
		//menor aptitud luego transformamos el problema de minimizacion en un problema de maximizacion
		double[] aptitudesMaximizadas = new double[aptitudesPositivas.length];
		if(mejora)
			aptitudesMaximizadas = evaluador.transformarAptitudesAMaximizacion(aptitudesEscaladas);
		else 
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
		return poblacion;
	}

}
