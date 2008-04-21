package es.ucm.fdi.algoritmos.cruzadores;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.Ciudades;

public class CruzadorERX implements Cruzador {
	
	//tabla de adyacencias entre ciudades correspondientes a dos progenitores
	private ArrayList<ArrayList<Integer>> adyacencias;

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2, Evaluador evaluador) {
		//crear cromosoma, crear genes, setear genes

		int numeroGenes = padre1.getNumeroGenes();
		//creamos los cromosomas hijos
		Cromosoma hijo1 = new CromosomaEnteroViajante(numeroGenes); 
		Cromosoma hijo2 = new CromosomaEnteroViajante(numeroGenes);
		
		//creamos el array de genes de los hijos
		GenEntero[] genesHijo1 = new GenEntero[padre1.getNumeroGenes()];
		GenEntero[] genesHijo2 = new GenEntero[padre1.getNumeroGenes()];
		
		//para cada gen de cada cromosoma padre, calculamos un punto de cruce y los cruzamos por separado.
		for (int i = 0; i < padre1.getNumeroGenes(); i++){
			//obtenemos el genBinario de los padres 1 y 2
			GenEntero genPadre1I = (GenEntero) padre1.getGenes()[i];
			GenEntero genPadre2I = (GenEntero) padre2.getGenes()[i];
			
			//creamos el genBinario hijo1 e hijo2
			GenEntero genHijo1I = new GenEntero(genPadre1I.getLongitud());
			GenEntero genHijo2I = new GenEntero(genPadre1I.getLongitud());
			
			//obtenemos la codificacion del gen i de cada padre
			int[] codificacionPadre1I = genPadre1I.getGen();
			int[] codificacionPadre2I = genPadre2I.getGen();

			int longGenI = padre1.getLongitudGenes()[i];
					
			//creamos la codificacion del gen i de cada hijo
			int[] codificacionHijo1I = new int[longGenI];
			int[] codificacionHijo2I = new int[longGenI];
			
			/****************Cruce por Recombinacion de Rutas****************/
			
			/****************FIN Cruce por Recombinacion de Rutas****************/
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;
		}
		//se compactan los hijos con la misma configuracion de genes que los padres.
		hijo1.setGenes(genesHijo1, evaluador);
		hijo2.setGenes(genesHijo2, evaluador);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}
	
	private void generarTablaAdyacencias (Cromosoma padre1, Cromosoma padre2){
		int longitudCromosoma = padre1.calcularLongitudCromosoma();
		this.adyacencias = new ArrayList<ArrayList<Integer>>(longitudCromosoma);
		for (int i = 0; i< longitudCromosoma; i++){
			this.adyacencias.add(new ArrayList<Integer>());
		}
		
		//para cada ciudad:
		for (int ciudad = 0; ciudad < Ciudades.NUM_CIUDADES; ciudad++){
			ArrayList<Integer> adyacentesCiudadI = this.adyacencias.get(ciudad);
			int[] genesPadre1 = ((GenEntero)padre1.getGenes()[0]).getGen();
			int[] genesPadre2 = ((GenEntero)padre2.getGenes()[0]).getGen();
			
			//obtenemos los indices en los que se encuentra la ciudad, tanto en el padre1 como en el padre 2
			int indiceEnPadre1 = Busquedas.buscar(ciudad, genesPadre1);
			int indiceEnPadre2 = Busquedas.buscar(ciudad, genesPadre2);
			
			//añadimos las ciudades vecinas de la "ciudad" en el padre 1
			if (indiceEnPadre1 != Ciudades.NUM_CIUDADES - 1)
				adyacentesCiudadI.add(genesPadre1[indiceEnPadre1+1]);
			else adyacentesCiudadI.add(genesPadre1[0]);
			
			if (indiceEnPadre1 != 0)
				adyacentesCiudadI.add(genesPadre1[indiceEnPadre1-1]);
			else adyacentesCiudadI.add(genesPadre1[Ciudades.NUM_CIUDADES - 1]);
			//TODO
			
			
		}
	}
	
	private int buscar (int elemento,ArrayList<Integer> array){
		int indice = -1;
		int i = 0;
		boolean encontrado = false;
		while (!encontrado && i< array.size()){
			if (elemento == array.get(i).intValue()){
				encontrado = true;
				indice = i;
			}
			i++;
		}
		return indice;
	}

}
