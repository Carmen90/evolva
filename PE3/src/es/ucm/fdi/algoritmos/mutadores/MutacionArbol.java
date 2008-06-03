package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.MyRandom;

public class MutacionArbol implements Mutador {

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
				//Generamos una profundidad aleatoria, que puede llegar incluso al nivel maximo de profundidad para mutar las hojas
				int profundidad = MyRandom.aleatorioEntero(0, ConstantesAlgoritmos.getInstance().getMaxProfundidad() + 1);
				//TODO cambiar el 1 por la profundidad.
				ResultadoMutacion resultado = mutarGen(gen, profundidad);
				gen = resultado.gen;
				//como hemos mutado el individuo, entonces recalculamos el fenotipo.
				//la evaluacion se hara en el ultimo paso de cada generacion.
				individuo.fenotipo();
			}
		}
		return poblacion;

	}
	
	//Tenemos que bajar hasta la profundidad indicada y mutar todo el gen
	private ResultadoMutacion mutarGen(GenArboreo gen, int profundidad) {
		
		ResultadoMutacion resultado = new ResultadoMutacion();
		
		//Calculamos la longitud de la funcion (numero de parametros)
		int longitud = gen.getLongitud();
		
		//CASO BASE: Comprobamos si hemos llegado a la profundidad deseada
		if(profundidad==0){
			//Generamos una nueva rama desde la profundidad que tenga el gen seleccionado hasta el maximo de profundidad
			GenArboreo nuevaRama = EvaluadorHormigas.genArboreoAleatorio(gen.getProfundidad());
			switch(longitud){
			case 0: ((Terminal)gen).setValor(((Terminal)nuevaRama).getValor());break;
			default: {
				GenArboreo[] nuevosArgumentos = ((Funcion)nuevaRama).getArgumentos();
				int numParametros = ((Funcion)nuevaRama).getLongitud();
				((Funcion)gen).setValor(((Funcion)nuevaRama).getValor());
				((Funcion)gen).setArgumentos(nuevosArgumentos);
				((Funcion)gen).setLongitud(numParametros);
				((Funcion)gen).setNumAgregados(numParametros);
			}
			}
			//System.out.println("Nueva rama: "+nuevaRama.toString());
			resultado.exito = true;
			resultado.gen = gen;

		}else if(profundidad != 0 && longitud == 0){
			//Al ir descendiendo llegamos a un terminal. Tenemos que seguir por otra rama
			resultado.exito = false;
			resultado.gen = gen;
		}else{ //CASO RECURSIVO -> Longitud != 0 y seguimos descendiendo
			profundidad--;
			boolean[] ramasVisitadas = new boolean[longitud];
			boolean todasRamasVisitadas = false;
			int contador = 0;
			do{
				int ramaAleatoria;
				do{
					ramaAleatoria = MyRandom.aleatorioEntero(0, longitud);
				}while(ramasVisitadas[ramaAleatoria]);
				ramasVisitadas[ramaAleatoria] = true;
				contador++;
				if(contador == longitud) //Hemos visitado todas las ramas
					todasRamasVisitadas = true;
				resultado = mutarGen(((Funcion)gen).getArgumentos()[ramaAleatoria], profundidad);
			}while(!resultado.exito && !todasRamasVisitadas);
		}
		return resultado;
	}
	
	private class ResultadoMutacion{
		private boolean exito;
		private GenArboreo gen;
		
		private ResultadoMutacion(){}
		private ResultadoMutacion(boolean exito, GenArboreo gen){
			this.exito = exito;
			this.gen = gen;
		}
		
	}

}
