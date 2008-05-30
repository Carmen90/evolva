package es.ucm.fdi.algoritmos.seleccionadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.MyRandom;

public class SeleccionTorneo implements Seleccionador {
	
	private static final int NUMERO_CONTRINCANTES = ConstantesAlgoritmos.getInstance().getNumContrincantes();
	
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e) {
		Cromosoma[] poblacionAux = new Cromosoma[poblacionInicial.length];
		Cromosoma[] elegidos = new Cromosoma[NUMERO_CONTRINCANTES];
		Cromosoma campeon = null;
		int indice = 0;
		
		while(indice < poblacionInicial.length){
			
			//Generamos 3 numeros aleatorios y extraemos 3 cromosomas que competiran en el torneo
			for(int i = 0; i<NUMERO_CONTRINCANTES; i++){
				int numGenerado = MyRandom.aleatorioEntero(0, poblacionInicial.length);
				elegidos[i] = poblacionInicial[numGenerado];
			}
			
			//El cromosoma campeon sera el mejor de los 3
			campeon = elegidos[0];
			for(int i = 1; i < NUMERO_CONTRINCANTES; i++){
				if(e.esMejorAptitud(elegidos[i].getAptitud(), campeon.getAptitud()))
					campeon = elegidos[i];
			}
			//Añadimos el campeon a la nueva poblacion
			poblacionAux[indice] = campeon.copiarCromosoma();
			indice++;
		}
		return poblacionAux;
	}

	public Cromosoma[] generarSegmentos(Cromosoma[] poblacion, Evaluador evaluador, boolean mejora) {
		return poblacion;
	}
	
}
