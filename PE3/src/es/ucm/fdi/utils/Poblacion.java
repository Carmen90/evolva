package es.ucm.fdi.utils;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public class Poblacion {
	
	public static double mediaPoblacionInstantanea(Cromosoma[] poblacion){
		double totalAptitudes = 0.0;
		int tamaņoPoblacion = poblacion.length;
		
		for (int i = 0; i < tamaņoPoblacion; i++){
			totalAptitudes += poblacion[i].getAptitud();
		}
		return totalAptitudes / poblacion.length;

	}
	
	public static Cromosoma mejorPoblacionInstantanea(Cromosoma[] poblacion, Evaluador evaluador) {
		double mejorAptitud = poblacion[0].getAptitud();
		int tamaņoPoblacion = poblacion.length;
		
		int posicionMejorAptitud = 0;
		for (int i = 1; i < tamaņoPoblacion; i++){
			if (evaluador.esMejorAptitud(poblacion[i].getAptitud(),mejorAptitud)){
				mejorAptitud = poblacion[i].getAptitud();
				posicionMejorAptitud = i;
			}
		}
		return poblacion[posicionMejorAptitud];
	}
}
