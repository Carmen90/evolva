package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Funcion.funciones;
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
		
		//Calculamos la longitud de la funcion (numero de parametros)
		int longitud = gen.getLongitud();
		//Comprobamos si hemos llegado a una funcion, que tendra longitud 2 ó 3
		if(profundidad==0 && longitud!=0){
			//generamos una funcion aleatoria y la cambiamos
			int indiceFuncion = MyRandom.aleatorioEntero(0, Funcion.NUM_FUNCIONES);
			funciones funcion = funciones.values()[indiceFuncion];
			
			int numParametros = 0;
			if (indiceFuncion < Funcion.LIMITE_2_PARAMETROS){
				numParametros = 2;
			}else if (indiceFuncion < Funcion.LIMITE_3_PARAMETROS){
				numParametros = 3;
			}
			
			while((numParametros <= longitud){
				//TODO cambiar el valor de la funcion que hay en el arbol
			}
			
		
		return gen;
	}

}
