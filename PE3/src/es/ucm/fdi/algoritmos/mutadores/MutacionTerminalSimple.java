package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Terminal.terminales;
import es.ucm.fdi.utils.MyRandom;

public class MutacionTerminalSimple implements Mutador {

	public Cromosoma[] mutacion(Cromosoma[] poblacion, double probabilidadMutacion) {

		int tamañoPoblacion = poblacion.length;
		//Recorremos todos los individuos de la poblacion calculando para cada uno si hay mutacion o no
		for(int i=0; i<tamañoPoblacion; i++){
			double mutar = Math.random();
			if(mutar < probabilidadMutacion){  
				//Hay mutacion
				Cromosoma individuo = poblacion[i];
				//Extraemos el gen del individuo
				GenArboreo gen = (GenArboreo) individuo.getGenes()[0];
				gen = mutarGen(gen);
				//como hemos mutado el individuo, entonces recalculamos el fenotipo.
				//la evaluacion se hara en el ultimo paso de cada generacion.
				individuo.fenotipo();
			}
		}
		return poblacion;
		
	}

	private GenArboreo mutarGen(GenArboreo gen) {
		
		//Comprobamos si hemos llegado a un terminal
		if(gen.getLongitud() == 0){
			int indiceTerminal = MyRandom.aleatorioEntero(0, Terminal.NUM_TERMINALES);
			terminales valorTerminal = terminales.values()[indiceTerminal];
			
			((Terminal)gen).setValor(valorTerminal);
		}else{
			int longitud = gen.getLongitud();
			int indiceParametro = MyRandom.aleatorioEntero(0, longitud);
			((Funcion)gen).getArgumentos()[indiceParametro] = mutarGen(((Funcion)gen).getArgumentos()[indiceParametro]);
		}
		
		return gen;
	}

}
