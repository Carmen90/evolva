package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public class SeleccionRuleta implements Seleccionador{

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

}
