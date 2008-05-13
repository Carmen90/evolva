package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.utils.Ordenacion;
import es.ucm.fdi.utils.Singleton;

public class CruzadorOXOrdenPrioritario implements Cruzador {

	private static final int NUMERO_POSICIONES_INTERCAMBIO = Singleton.getInstance().getNumIntercambios();
	
	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2) {
		
		
		int numeroGenes = padre1.getNumeroGenes();
		//creamos los cromosomas hijos
		Cromosoma hijo1 = new CromosomaEnteroViajante(numeroGenes); 
		Cromosoma hijo2 = new CromosomaEnteroViajante(numeroGenes);
		
		//creamos el array de genes de los hijos
		GenEntero[] genesHijo1 = new GenEntero[padre1.getNumeroGenes()];
		GenEntero[] genesHijo2 = new GenEntero[padre1.getNumeroGenes()];

		//para cada gen del cromosoma:
		for(int i = 0; i<padre1.getNumeroGenes(); i++){
			
			//Extraemos los genes de los padres
			Gen[] genesPadre1 = padre1.getGenes();
			Gen[] genesPadre2 = padre2.getGenes();
			
			int longitudGen = ((GenEntero)genesPadre1[i]).getLongitud();
			int[] codificacionGenPadre1I = new int[longitudGen];
			int[] codificacionGenPadre2I = new int[longitudGen];
			
			//Guardamos las codificaciones de los genes de los padres en un array de enteros
			codificacionGenPadre1I = ((GenEntero)genesPadre1[i]).getGen();
			codificacionGenPadre2I = ((GenEntero)genesPadre2[i]).getGen();
		
			int[] codificacionHijo1I = new int[longitudGen];
			int[] codificacionHijo2I = new int[longitudGen];
			
			int[] ciudadesEnPosicionesElegidasP1 = new int[NUMERO_POSICIONES_INTERCAMBIO]; //Guardamos las ciudades del padre1 que estan en las posiciones escogidas
			int[] ciudadesEnPosicionesElegidasP2 = new int[NUMERO_POSICIONES_INTERCAMBIO]; //Guardamos las ciudades del padre2 que estan en las posiciones escogidas
			boolean[] posicionesEscogidas = new boolean[longitudGen];//De esta forma sabemos que posiciones han salido y cuales no
			
			//Guardamos las posiciones elegidas
			int[] posicionesElegidas = new int[NUMERO_POSICIONES_INTERCAMBIO];

			//posicionesEscogidas[0] = true; //Madrid no se puede modificar. Es la primera ciudad
			
			//Generamos numPosicionesIntercambio posiciones aleatorias
			int posicion = 0;
			for(int j=0;j<NUMERO_POSICIONES_INTERCAMBIO;j++){
				posicion = MyRandom.aleatorioEntero(0, longitudGen);
				while(posicionesEscogidas[posicion])
					posicion = MyRandom.aleatorioEntero(0, longitudGen);
				posicionesElegidas[j] = posicion;
				posicionesEscogidas[posicion] = true;
			}
			
			//Ordenamos el array con las posiciones elegidas, ya que luego habra que luego habra que colocar en las posiciones
			//indeterminadas las ciudades seleccionadas al principioconservando su orden relativo
			Ordenacion.ordenacionInsercion(posicionesElegidas);
			
			for(int j=0; j<NUMERO_POSICIONES_INTERCAMBIO;j++){
				int pos = posicionesElegidas[j];
				ciudadesEnPosicionesElegidasP1[j] = codificacionGenPadre1I[pos];
				ciudadesEnPosicionesElegidasP2[j] = codificacionGenPadre2I[pos];
			}
			
			//Tenemos que ver las ciudades del padre1 seleccionadas en que posicion del padre2 aparecen y viceversa
			int[] posicionCiudadesPadre1EnPadre2 = new int[NUMERO_POSICIONES_INTERCAMBIO];
			int[] posicionCiudadesPadre2EnPadre1 = new int[NUMERO_POSICIONES_INTERCAMBIO];
			for(int j=0;j<NUMERO_POSICIONES_INTERCAMBIO;j++){
				int ciudadP1 = ciudadesEnPosicionesElegidasP1[j];
				posicionCiudadesPadre1EnPadre2[j] = Busquedas.buscar(ciudadP1, codificacionGenPadre2I);
				int ciudadP2 = ciudadesEnPosicionesElegidasP2[j];
				posicionCiudadesPadre2EnPadre1[j] = Busquedas.buscar(ciudadP2, codificacionGenPadre1I);
			}
			
			//Madrid debe estar siempre en la posicion 0
			//codificacionHijo1I[0] = codificacionGenPadre1I[0];
			//codificacionHijo2I[0] = codificacionGenPadre2I[0];
			
			//Rellenamos el hijo1 copiando el padre2 excepto las posiciones que aparecen en posicionCiudadesPadre1EnPadre2 y viceversa
			int pos1 = 0;
			int pos2 = 0;
			for(int j=0;j<longitudGen;j++){
				int indice1 = Busquedas.buscar(j, posicionCiudadesPadre1EnPadre2);
				if(indice1 == -1) //No lo ha encontrado -> lo añadimos al hijo1
					codificacionHijo1I[j] = codificacionGenPadre2I[j];
				else {
					codificacionHijo1I[j] = ciudadesEnPosicionesElegidasP1[pos1];
					pos1++;
				}
				int indice2 = Busquedas.buscar(j, posicionCiudadesPadre2EnPadre1);
				if(indice2 == -1) //No lo ha encontrado -> lo añadimos al hijo2
					codificacionHijo2I[j] = codificacionGenPadre1I[j];
				else {
					codificacionHijo2I[j] = ciudadesEnPosicionesElegidasP2[pos2];
					pos2++;
				}
					
			}
			
			GenEntero genHijo1I = new GenEntero(longitudGen);
			GenEntero genHijo2I = new GenEntero(longitudGen);
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;		
					
		}
		
		hijo1.setGenes(genesHijo1);
		hijo2.setGenes(genesHijo2);
		
		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}
	
	

}
