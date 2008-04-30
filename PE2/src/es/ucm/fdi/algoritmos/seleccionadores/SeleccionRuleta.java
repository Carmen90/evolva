package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.utils.Ordenacion;

public class SeleccionRuleta implements Seleccionador{

	//precondicion!! la poblacion inicial debe estar ordenada
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e) {
		int tama�oPoblacion = poblacionInicial.length;
		Cromosoma[] poblacionSeleccionada = new Cromosoma[tama�oPoblacion];

		double probSeleccion;
		int posSuperviviente;

		for(int i =0; i<tama�oPoblacion; i++){
			probSeleccion = Math.random();
			posSuperviviente = 0;

			while(probSeleccion > poblacionInicial[posSuperviviente].getPuntuacionAcumulada() && 
					posSuperviviente < tama�oPoblacion){
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
		
		int tama�oPoblacion = poblacion.length;
		
		//ordenamos la poblacion de peor a mejor aptitud.
		poblacion = Ordenacion.sortSelectionIndividuals(poblacion, evaluador);
				
		double[] aptitudesPositivas = new double[tama�oPoblacion];
		for (int i = 0; i< tama�oPoblacion; i++){
				double aptitudPositivaI = poblacion[i].getAptitud();
				aptitudesPositivas[i] = aptitudPositivaI;
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
		for (int i = 0; i< tama�oPoblacion; i++){
			
			double aptitudI = aptitudesPositivas[i];
			
			poblacion[i].setPuntuacion(aptitudI/sumaAptitud);
			double acumulacion = poblacion[i].getPuntuacion() + puntAcumulada;
			poblacion[i].setPuntuacionAcumulada(acumulacion);
			puntAcumulada = acumulacion;
		}
		return poblacion;
	}

}
