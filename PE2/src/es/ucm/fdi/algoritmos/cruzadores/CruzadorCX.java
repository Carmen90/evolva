package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;

/*IMPLEMENTACION DEL CRUCE POR CICLOS*/
public class CruzadorCX implements Cruzador {

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2, Evaluador evaluador) {
		
		//crear cromosoma, crear genes, setear genes, inicializar cromosoma

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
			int puntoCruceI = MyRandom.aleatorioEntero(0,longGenI);
			
			//creamos la codificacion del gen i de cada hijo
			int[] codificacionHijo1I = new int[longGenI];
			int[] codificacionHijo2I = new int[longGenI];

			// primera parte del intercambio: 1 a 1 y 2 a 2

			for (int j = 0; j < puntoCruceI; j++){
				codificacionHijo1I[j] = codificacionPadre1I[j];
				codificacionHijo2I[j] = codificacionPadre2I[j];
			}

			// segunda parte: 1 a 2 y 2 a 1
			for (int j = puntoCruceI; j < longGenI; j++){
				codificacionHijo1I[j] = codificacionPadre2I[j];
				codificacionHijo2I[j] = codificacionPadre1I[j];
			}
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;
		}
		//se compactan los hijos con la misma configuracion de genes que los padres.
		hijo1.setGenes(genesHijo1);
		hijo2.setGenes(genesHijo2);

		// se evalúan
		hijo1.inicializarCromosoma(evaluador);
		hijo2.inicializarCromosoma(evaluador);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}
}