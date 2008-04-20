package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

public class CruzadorPMX implements Cruzador {

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2, Evaluador evaluador) {
		
		int numeroGenes = padre1.getNumeroGenes();
		//creamos los cromosomas hijos
		Cromosoma hijo1 = new CromosomaEnteroViajante(numeroGenes); 
		Cromosoma hijo2 = new CromosomaEnteroViajante(numeroGenes);
		
		//creamos el array de genes de los hijos
		GenEntero[] genesHijo1 = new GenEntero[padre1.getNumeroGenes()];
		GenEntero[] genesHijo2 = new GenEntero[padre1.getNumeroGenes()];
		
		//para cada gen del cromosoma:
		for(int i = 0; i<padre1.getNumeroGenes(); i++){
			
			//Hallamos 2 puntos de corte
			int corte1 = MyRandom.aleatorioEntero(0, padre1.getGenes()[i].getLongitud());
			int corte2 = MyRandom.aleatorioEntero(0, padre2.getGenes()[i].getLongitud());
			int menor;
			int mayor;
			if(corte1<corte2){
				menor = corte1;
				mayor = corte2;
			}
			else{
				menor = corte2;
				mayor = corte1;
			}
			
			//Hallamos la longitud de los segmentos
			int tamañoSegmentos = mayor - menor + 1;
			int[] segmentoPadre1 = new int[tamañoSegmentos];
			int[] segmentoPadre2  = new int[tamañoSegmentos];
			
			//Extraemos los genes de los padres
			Gen[] genesPadre1 = padre1.getGenes();
			Gen[] genesPadre2 = padre2.getGenes();
			
			//TODO
			int[] codificacionGenI = new int[((GenEntero)genesPadre1[i]).getGen()[i]];
			//Nos quedamos con los segmentos situados entre los puntos de corte
			for (int j = menor; j<=mayor; j++){
				segmentoPadre1[j] = ((GenEntero)genesPadre1[i]).getGen()[j];
				segmentoPadre2[j] = ((GenEntero)genesPadre2[i]).getGen()[j];
			}
			
			//Intercambiamos los segmentos de los dos padres
			for(int j = menor; j<mayor; j++){
				
			}
			
		}
		
		 
		return null;
	}

}
