package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;

/*IMPLEMENTACION DEL CRUCE POR CICLOS*/
public class CruzadorCX implements Cruzador {

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2) {
		
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
			//obtenemos el genEntero de los padres 1 y 2
			GenEntero genPadre1I = (GenEntero) padre1.getGenes()[i];
			GenEntero genPadre2I = (GenEntero) padre2.getGenes()[i];
			
			//creamos el genEntero hijo1 e hijo2
			GenEntero genHijo1I = new GenEntero(genPadre1I.getLongitud());
			GenEntero genHijo2I = new GenEntero(genPadre1I.getLongitud());
			
			//obtenemos la codificacion del gen i de cada padre
			int[] codificacionPadre1I = genPadre1I.getGen();
			int[] codificacionPadre2I = genPadre2I.getGen();

			//creamos la codificacion del gen i de cada hijo
			/****************Cruce por Ciclos****************/
			int[] codificacionHijo1I = cruceCiclos(codificacionPadre1I, codificacionPadre2I);
			int[] codificacionHijo2I = cruceCiclos(codificacionPadre2I, codificacionPadre1I);
			/****************FIN Cruce por Ciclos****************/
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;
		}
		//se compactan los hijos con la misma configuracion de genes que los padres.
		//los cromosomas se evaluan en la funcion que setea los genes
		hijo1.setGenes(genesHijo1);
		hijo2.setGenes(genesHijo2);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}
	
	private int[] cruceCiclos(int[] codificacionPadreOrigen, int[]codificacionPadreAuxiliar){
		int longGenI = codificacionPadreOrigen.length;
		int[] codificacionHijo = new int[longGenI];
		
		//array de booleanos que indica si un elemento ya ha sido visitado
		boolean[] ciclos = new boolean[longGenI];
		for (int j = 0; j< ciclos.length; j++)ciclos[j]=false;
		
		//marcamos el primer elemento (MADRID) como ya visitado, y empezamos el cruce por la ciudad en posicion 1 (segunda ciudad)
		//ciclos[Ciudades.MADRID] = true;
		//codificacionHijo[0] = Ciudades.MADRID;
		
		//inicialmente el salto estara en el segundo elemento
		int salto = 0;
		
		//repetimos tantas veces como posiciones en el gen o un ciclo completo
		int j = 0;
		//si el elemento del padre 1 en el salto no fue visitado
		while (!ciclos[codificacionPadreOrigen[salto]] && j< longGenI){
			
			//el hijo 1 en la posicion del salto se configura con el padre1 en el salto
			codificacionHijo[salto] = codificacionPadreOrigen[salto];
			//marcamos el elemento como ya visitado
			ciclos[codificacionPadreOrigen[salto]] = true;
			
			//obtenemos el elemento analogo a la posicion del salto en el padre 2
			int elementoAnalogoPadre2 = codificacionPadreAuxiliar[salto];
			//obtenemos del padre2 la nueva direccion de salto
			salto = Busquedas.buscar(elementoAnalogoPadre2, codificacionPadreOrigen);
			j++;
		}
		//los numeros que no se hallan puesto, se cogen del padre 2 en el orden en el que este los tiene
		//para ello, recorremos el array "ciclos" buscando posiciones a false
		//despues buscamos el indice en el que se encuentra k en el padre 2, y metemos en ese indice
		//del hijo 1 el indice de "ciclos" en el que nos encontremos...
		for (int k = 0; k< ciclos.length; k++){
			if (!ciclos[k]){
				int indiceKEnPadre2 = Busquedas.buscar(k, codificacionPadreAuxiliar);
				codificacionHijo[indiceKEnPadre2] = k;
			}
		}
		return codificacionHijo;
	}
}