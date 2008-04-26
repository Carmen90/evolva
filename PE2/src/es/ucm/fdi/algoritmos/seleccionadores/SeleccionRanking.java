package es.ucm.fdi.algoritmos.seleccionadores;

import java.util.Vector;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public class SeleccionRanking implements Seleccionador {

	public static final double BETA = 1.5;
	
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e) {
		return performRankSelection(poblacionInicial, e);
	}


	public Cromosoma[] performRankSelection(Cromosoma[] initPop, Evaluador e) {
		int tamañoPoblacion = initPop.length;
		
		Cromosoma[] sortedPop = sortIndividual(initPop, e);
		Cromosoma[] futureParents = new Cromosoma[sortedPop.length];
		
		futureParents[0]=sortedPop[0].copiarCromosoma();
		futureParents[1]=sortedPop[1].copiarCromosoma();
		int numOfParents =2;
		
		double[] fitnessSegments = rankPopulation(tamañoPoblacion);
		double entireSegment = fitnessSegments[fitnessSegments.length-1];
		
		while(numOfParents<futureParents.length){
			double x = (double)(Math.random()*entireSegment);
			if(x<=fitnessSegments[0]) {
				//First Idividual was Selected 
				futureParents[numOfParents]=sortedPop[0].copiarCromosoma();
				numOfParents++;}
			else
				for(int i=1; i<futureParents.length; i++)
					if(x>fitnessSegments[i-1] && x<=fitnessSegments[i]){
						//i'th Idividual was Selected 
						futureParents[numOfParents]=sortedPop[i].copiarCromosoma();
						numOfParents++;}
		} 
		return futureParents;
	}

	private double[] rankPopulation(int tamañoPoblacion){
		double[] fitnessSegments = new double[tamañoPoblacion];
		
		for(int i=0 ; i<fitnessSegments.length ; i++){
			double probOfIth = (double)i/tamañoPoblacion;
			probOfIth = probOfIth*2*(BETA -1);
			probOfIth = BETA - probOfIth;
			probOfIth = (double)probOfIth*((double)1/tamañoPoblacion);
			if(i!=0)
				fitnessSegments[i] = fitnessSegments[i-1] + probOfIth;
			else
				fitnessSegments[i] = probOfIth;
		}
		
		return fitnessSegments;
	} 
	
	private Cromosoma[] sortIndividual(Cromosoma[] poblacion, Evaluador evaluador){
		//para ordenar la poblacion, la recorremos entera, y vamos insertando ordenadamente 
		//en funcion del criterio de la funcion, los individuos en un array auxiliar.
		//despues, seteamos la poblacion ordenada...
		Vector <Cromosoma> ordenado = new Vector <Cromosoma>();

		//metemos el primer cromosoma directamente en el vector
		ordenado.add(poblacion[0]);
		//para los demas...
		for (int i = 1; i< poblacion.length; i++){
			//individuo que vamos a insertar
			Cromosoma individuoI = poblacion[i];

			//buscamos donde insertar el nuevo elemento, teniendo en cuenta que se trata de una ordenacion
			//de menor a mayor, luego buscamos el indice en orden inverso
			int j = ordenado.size() - 1;
			boolean indiceEncontrado = false;
			while (!indiceEncontrado && j >= 0){
				Cromosoma individuoOrdenadoJ = ordenado.get(j);
				indiceEncontrado = evaluador.esMejorAptitud(individuoI.getAptitud(), individuoOrdenadoJ.getAptitud());
				j--;
			}
			//si hemos encontrado un indice apto para la insercion
			if (indiceEncontrado){
				ordenado.insertElementAt(individuoI, j+2);
			}else ordenado.insertElementAt(individuoI,0);
		}
		
		//una vez ordenados en el vector, seteamos la poblacion.
		Cromosoma[] poblacionOrdenada = new Cromosoma[poblacion.length];
		for (int i = 0; i < poblacion.length; i++){
			poblacionOrdenada[i] = ordenado.get(i);
		}
		return poblacionOrdenada;

	}

}
