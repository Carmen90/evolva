package es.ucm.fdi.algoritmos.cruzadores;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Ciudades;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.MyRandom;

public class CruzadorCodOrdinal implements Cruzador{

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
			//obtenemos el genEntero de los padres 1 y 2
			GenEntero genPadre1I = (GenEntero) padre1.getGenes()[i];
			GenEntero genPadre2I = (GenEntero) padre2.getGenes()[i];

			//creamos el genEntero hijo1 e hijo2
			GenEntero genHijo1I = new GenEntero(genPadre1I.getLongitud());
			GenEntero genHijo2I = new GenEntero(genPadre1I.getLongitud());

			//obtenemos la codificacion del gen i de cada padre
			int[] codificacionPadre1I = genPadre1I.getGen();
			int[] codificacionPadre2I = genPadre2I.getGen();
			
			//creamos la codificacion de los hijos
			int longitudGen = genPadre1I.getLongitud();
			int[] codificacionHijo1I = new int[longitudGen];
			int[] codificacionHijo2I = new int[longitudGen];

			/****************Cruce por Codificacion Ordinal****************/
			//generamos dos listas dinámicas, una para cada hijo:
			ArrayList<Integer> dinamicaH1 = new ArrayList<Integer>();
			ArrayList<Integer> dinamicaH2 = new ArrayList<Integer>();
			//inicializamos las listas dinamicas
			for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
				dinamicaH1.add(j);
				dinamicaH2.add(j);
			}
			//creamos las codificacions ordinales de los hijos
			ArrayList<Integer> codOrdinalH1 = new ArrayList<Integer>();
			ArrayList<Integer> codOrdinalH2 = new ArrayList<Integer>();
			//para cada ciudad, obtenemos su posicion en la lista dinamica, eliminandola
			//tras esa accion de la lista dinamica.
			for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
				//buscamos la ordenacion de la ciudad del padre1 en la posicion j
				int ordenH1 = Busquedas.buscar(codificacionPadre1I[j], dinamicaH1);
				//añadimos la ordenacion a la codificacion
				codOrdinalH1.add(ordenH1);
				//eliminamos el elemento en la posicion del orden de la lista dinamica
				dinamicaH1.remove(ordenH1);
				//lo mismo para el hijo 2
				int ordenH2 = Busquedas.buscar(codificacionPadre2I[j], dinamicaH2);
				codOrdinalH2.add(ordenH2);
				dinamicaH2.remove(ordenH2);
			}
			//ya tenemos la codificacion de las listas, ahora las cruzamos en un punto al azar
			int puntoCruceI = MyRandom.aleatorioEntero(0,Ciudades.NUM_CIUDADES);
			
			
			/****************FIN Cruce por Codificacion Ordinal****************/

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

}
