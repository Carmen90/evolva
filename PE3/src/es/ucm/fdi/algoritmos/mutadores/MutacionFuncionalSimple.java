package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;
import es.ucm.fdi.utils.MyRandom;

public class MutacionFuncionalSimple implements Mutador {

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
				//Generamos una profundidad aleatoria
				int profundidad = MyRandom.aleatorioEntero(0, EvaluadorHormigas.MAX_PROFUNDIDAD);
				gen = mutarGen(gen, profundidad);
				//como hemos mutado el individuo, entonces recalculamos el fenotipo.
				//la evaluacion se hara en el ultimo paso de cada generacion.
				individuo.fenotipo();
			}
		}
		return poblacion;
		
	}

	private GenArboreo mutarGen(GenArboreo gen, int profundidad) {
		
		//Comprobamos si hemos llegado a una funcion, que tendra longitud 2 ó 3
		if(gen.getLongitud() >=2){
			//Calculamos la longitud de la funcion
			int longitud = gen.getLongitud();
			//Generamos una parametro al azar por el que bajar por el arbol
			int indiceParametro = MyRandom.aleatorioEntero(0, longitud);
		}else{//Mutamos al padre
			
		}
		
		return gen;
	}

}
