package es.ucm.fdi.algoritmos.mutadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Funcion.funciones;
import es.ucm.fdi.utils.ConstantesAlgoritmos;
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
				int profundidad = MyRandom.aleatorioEntero(0, ConstantesAlgoritmos.getInstance().getMaxProfundidad());
				//TODO cambiar el 1 por la profundidad.
				ResultadoMutacion resultado = mutarGen(gen, profundidad);
				//como hemos mutado el individuo, entonces recalculamos el fenotipo.
				//la evaluacion se hara en el ultimo paso de cada generacion.
				individuo.fenotipo();
			}
		}
		return poblacion;

	}

	private ResultadoMutacion mutarGen(GenArboreo gen, int profundidad) {

		ResultadoMutacion resultado = new ResultadoMutacion();
		//Calculamos la longitud de la funcion (numero de parametros)
		int longitud = gen.getLongitud();
		//CASO BASE: Comprobamos si hemos llegado a una funcion, que tendra longitud 2 ó 3
		if(profundidad==0 && longitud!=0){
			int numParametros;
			int indiceFuncion;
			funciones funcion;
			do{
				//generamos una funcion aleatoria y la cambiamos. 
				//TODO si sale una funcion igual que la que tenemos, ¿la dejamos o generamos otra distinta?
				//De momento generamos otra distinta, de modo que siempre camiara de valor
				do{
					indiceFuncion = MyRandom.aleatorioEntero(0, Funcion.NUM_FUNCIONES);
					funcion = funciones.values()[indiceFuncion];//Aqui tenemos la nueva funcion a setear
				}while(funcion == ((Funcion)gen).getValor());

				numParametros = 0;
				if (indiceFuncion < Funcion.LIMITE_2_PARAMETROS){
					numParametros = 2;
				}else if (indiceFuncion < Funcion.LIMITE_3_PARAMETROS){
					numParametros = 3;
				}

			}while(numParametros > longitud);  //Repetimos el proceso mientras obtengamos funciones de mayor longitud 
												//que la que queremos mutar
			if(numParametros < longitud){
				//Tenemos una funcion de 3 argumentos y la vamos a cambiar por una de 2. 
				//Habra que podar la rama que cuelga de un parametro elegido al azar.

				//Generamos un entero aleatorio entre 0 y 2 para descartar la rama que cuelga de ese parametro
				int paramEliminado = MyRandom.aleatorioEntero(0, Funcion.LIMITE_3_PARAMETROS);
				GenArboreo[] nuevosArgumentos = new GenArboreo[numParametros];
				int indice = 0;
				for(int i=0; i<longitud; i++){ //Copiamos los parametros al nuevo array de parametros solo si
					//no coinciden con el parametro eliminado
					if(i != paramEliminado){
						nuevosArgumentos[indice] = ((Funcion)gen).getArgumentos()[i];
						indice++;
					}
				}
				//Seteamos los nuevos argumentos
				((Funcion)gen).setArgumentos(nuevosArgumentos);
				((Funcion)gen).setLongitud(numParametros);
				((Funcion)gen).setNumAgregados(numParametros);

			}
			((Funcion)gen).setValor(funcion); //Seteamos el nuevo valor de funcion
			resultado.exito=true;
			resultado.gen = gen;

		}else if(profundidad == 0 && longitud == 0){
			/*TODO Hemos llegado a la profundidad deseada, pero no estamos es una 
			funcion => NO PODEMOS MUTAR. Este caso de momento no se va a dar, 
			porque solo se baja como mucho hasta el penultimo nivel y los arboles son equilibrados*/
			resultado.exito=false;
			resultado.gen = gen;
			
		}else if(profundidad != 0 && longitud == 0){ 
			/*TODO No estamos en la profundidad deseada, pero hemos llegado a un TERMINAL
			Esto de momento no ocurre ya que los arboles son equilibrados. Con el cruce 
			la cosa cambiara...*/
			resultado.exito=false;
			resultado.gen = gen;
			
		}else{ //Longitud != 0 y estamos descendiendo
			profundidad--;
			boolean[] ramasVisitadas = new boolean[longitud];
			boolean todasRamasVisitadas = false;
			int contador = 0;
			do{
				//ramasVisitadas = new boolean[longitud];
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