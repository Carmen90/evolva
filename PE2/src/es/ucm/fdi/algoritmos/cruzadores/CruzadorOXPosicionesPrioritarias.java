package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.MyRandom;

public class CruzadorOXPosicionesPrioritarias implements Cruzador {

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
			
			//En estos arrays guardamos los elementos que han sido seleccionados de cada gen padre
			int[] elementosSeleccionadosPadre1 = new int[3];
			int[] elementosSeleccionadosPadre2 = new int[3];
			
			//Array de booleanos que indica las posiciones que han salido elegidas
			boolean[] escogidos = new boolean[longitudGen];
			
			//Usamos estos dos arrays para saber donde tenemos que insertar los valores del gen
			boolean[] posicionesLibresHijo1 = new boolean[longitudGen];
			boolean[] posicionesLibresHijo2 = new boolean[longitudGen];
			
			//Inicializamos todas las posiciones a false indicando que ninguna posicion ha sido seleccionada
			for(int j=0; j<longitudGen; j++){
				escogidos[j] = false;
				posicionesLibresHijo1[j] = true;
				posicionesLibresHijo2[j] = true;
			}
			
//COMIENZO CURCE OX CON POSICIONES PRIORITARIAS*********************************************************			
			//Escogemos 3 posiciones aleatorias
			int posicion = 0;
			for(int j=0; j<3; j++){
				posicion = MyRandom.aleatorioEntero(0, longitudGen);				
				
				//Si se generan posiciones ya elegidas, volvemos a generar
				while(escogidos[posicion]){
					posicion = MyRandom.aleatorioEntero(0, longitudGen);
				}
				escogidos[posicion] = true;
				elementosSeleccionadosPadre1[j] = codificacionGenPadre1I[posicion];
				elementosSeleccionadosPadre2[j] = codificacionGenPadre2I[posicion];
				codificacionHijo1I[posicion] = codificacionGenPadre2I[posicion];
				posicionesLibresHijo1[posicion] = false;
				codificacionHijo2I[posicion] = codificacionGenPadre1I[posicion];
				posicionesLibresHijo2[posicion] = false;
				
			}
		
			//Madrid debe estar siempre en la posicion 0
			codificacionHijo1I[0] = codificacionGenPadre1I[0];
			posicionesLibresHijo1[0] = false;
			codificacionHijo2I[0] = codificacionGenPadre2I[0];
			posicionesLibresHijo2[0] = false;
			
			//Partimos del ultimo punto de corte generado
			int posicion1 = posicion+1;
			int posicion2 = posicion+1;
			for(int k = posicion+1; k<longitudGen; k++){
				int elem1 = codificacionGenPadre1I[k];
				int elem2 = codificacionGenPadre2I[k];
				int indice1 = Busquedas.buscar(elem1, elementosSeleccionadosPadre2);
				int indice2 = Busquedas.buscar(elem2, elementosSeleccionadosPadre1);
				//El elemento no esta en el segmento -> podemos añadirlo
				if(indice1 == -1){
					while(!posicionesLibresHijo1[posicion1]){
						posicion1++;
						if(posicion1 == longitudGen){
							posicion1=1;
						}
					}
					codificacionHijo1I[posicion1] = elem1;
					posicionesLibresHijo1[posicion1] = false;
					posicion1++;
					if(posicion1 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion1 = 1; //Dejamos la posicion 0 (Madrid) siempre fija
					}
					
				}
				if(indice2 == -1){
					while(!posicionesLibresHijo2[posicion2]){
						posicion2++;
						if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
							posicion2 = 1;  //Dejamos la posicion 0 (Madrid) siempre fija
						}
					}
					codificacionHijo2I[posicion2] = elem2;
					posicionesLibresHijo2[posicion2] = false;
					posicion2++;
					if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion2 = 1;  //Dejamos la posicion 0 (Madrid) siempre fija
					}
					
				}
			}
			//En caso de que el corte mayor sea 27 hacemos esto para que no se salgan de los indices del array
			if(posicion1==longitudGen){ 
				posicion1 = 1;
				posicion2 = 1;
			}
			//Empezamos desde la siguiente ciudad a Madrid
			for(int k=1;k<=posicion;k++){ //Recorremos los padres hasta el punto de partida
				int elem1 = codificacionGenPadre1I[k];
				int elem2 = codificacionGenPadre2I[k];
				int indice1 = Busquedas.buscar(elem1, elementosSeleccionadosPadre2);
				int indice2 = Busquedas.buscar(elem2, elementosSeleccionadosPadre1);
				//El elemento no esta en el segmento -> podemos añadirlo
				if(indice1 == -1){
					while(!posicionesLibresHijo1[posicion1]){
						posicion1++;
						if(posicion1 == longitudGen){
							posicion1=1;
						}
					}
					codificacionHijo1I[posicion1] = elem1;
					posicionesLibresHijo1[posicion1] = false;
					posicion1++;
					if(posicion1 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion1 = 1;  //Dejamos la posicion 0 (Madrid) siempre fija
					}
				}
				if(indice2 == -1){
					while(!posicionesLibresHijo2[posicion2]){
						posicion2++;
						if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
							posicion2 = 1;  //Dejamos la posicion 0 (Madrid) siempre fija
						}
					}
					codificacionHijo2I[posicion2] = elem2;
					posicionesLibresHijo2[posicion2] = false;
					posicion2++;	
					if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion2 = 1;  //Dejamos la posicion 0 (Madrid) siempre fija
					}
				}
			}
//FIN CURCE OX CON POSICIONES PRIORITARIAS*********************************************************
			
			GenEntero genHijo1I = new GenEntero(longitudGen);
			GenEntero genHijo2I = new GenEntero(longitudGen);
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;		
			
		}
		
		hijo1.setGenes(genesHijo1,evaluador);
		hijo2.setGenes(genesHijo2,evaluador);
		
		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}

}
