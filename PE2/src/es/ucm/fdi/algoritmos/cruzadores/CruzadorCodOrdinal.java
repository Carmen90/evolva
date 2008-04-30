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

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2) {
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
			int[] codificacionHijo1I;
			int[] codificacionHijo2I;

			/****************Cruce por Codificacion Ordinal****************/
			//creamos las codificacions ordinales de los padres
			ArrayList<Integer> codOrdinalP1 = this.codificar(codificacionPadre1I);
			ArrayList<Integer> codOrdinalP2 = this.codificar(codificacionPadre2I);
				
			//ya tenemos la codificacion de las listas de los padres, ahora las cruzamos en un punto al azar
			//creamos las codificacions ordinales de los hijos
			ArrayList<Integer> codOrdinalH1 = new ArrayList<Integer>();
			ArrayList<Integer> codOrdinalH2 = new ArrayList<Integer>();
		
			int puntoCruceI = MyRandom.aleatorioEntero(0,Ciudades.NUM_CIUDADES);
			for (int j = 0; j < puntoCruceI; j++){
				codOrdinalH1.add(codOrdinalP1.get(j));
				codOrdinalH2.add(codOrdinalP2.get(j));
			}
			// segunda parte: 1 a 2 y 2 a 1
			for (int j = puntoCruceI; j < Ciudades.NUM_CIUDADES; j++){
				codOrdinalH1.add(codOrdinalP2.get(j));
				codOrdinalH2.add(codOrdinalP1.get(j));
			}
			//ahora decodificamos las codificaciones ordinales para generar los hijos finalmente...
			codificacionHijo1I = deCodificar(codOrdinalH1);
			codificacionHijo2I = deCodificar(codOrdinalH2);
			/****************FIN Cruce por Codificacion Ordinal****************/

			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);

			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;
		}
		//se compactan los hijos con la misma configuracion de genes que los padres.
		hijo1.setGenes(genesHijo1);
		hijo2.setGenes(genesHijo2);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}
	
	private ArrayList<Integer> codificar (int[] array){
		//Creamos la lista dinamica
		ArrayList<Integer> dinamica = new ArrayList<Integer>();
		//inicializamos las listas dinamicas
		for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
			dinamica.add(j);
		}
		//creamos el arrayList que contendra la codificacion ordinal
		ArrayList<Integer> codOrdinal = new ArrayList<Integer>();
		for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
			//buscamos la ordenacion de la ciudad del array en la posicion j
			int ordenH1 = Busquedas.buscar(array[j], dinamica);
			//añadimos la ordenacion a la codificacion
			codOrdinal.add(ordenH1);
			//eliminamos el elemento en la posicion del orden de la lista dinamica
			dinamica.remove(ordenH1);
		}
		return codOrdinal;
	}
	
	private int[] deCodificar (ArrayList<Integer> codificacion){
		//Creamos la lista dinamica
		ArrayList<Integer> dinamica = new ArrayList<Integer>();
		//inicializamos las listas dinamicas
		for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
			dinamica.add(j);
		}
		//creamos el array que contendra la decodificacion
		int[] decodificacion = new int[codificacion.size()];
		for (int j = 0; j < Ciudades.NUM_CIUDADES; j++){
			//miramos que hay en la posicion indicada por codificacion[j] de la lista dinamica
			int queHay = dinamica.get(codificacion.get(j));
			decodificacion[j] = queHay;
			dinamica.remove(codificacion.get(j).intValue());
		}
		return decodificacion;
	}

}
