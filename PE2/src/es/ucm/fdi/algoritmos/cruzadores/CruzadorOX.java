package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.MyRandom;

public class CruzadorOX implements Cruzador {

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
			
			//Intercambiamos los segmentos de los dos padres
			for(int j = menor; j<=mayor; j++){
				codificacionHijo1I[j] = codificacionGenPadre2I[j];
				codificacionHijo2I[j] = codificacionGenPadre1I[j];
			}
			//codificacionHijo1I[0] = codificacionGenPadre1I[0];
			//codificacionHijo2I[0] = codificacionGenPadre2I[0];
//INICIO CRUCE OX****************************************************************************
			//Inicialmente partimos del segundo punto de corte de los padres
			//Recorremos el padre1 desde el segundo punto de corte hasta el final
			int posicion1 = mayor+1;
			int posicion2 = mayor+1;
			for(int k = mayor+1; k<longitudGen; k++){
				int elem1 = codificacionGenPadre1I[k];
				int elem2 = codificacionGenPadre2I[k];
				int indice1 = Busquedas.buscar(elem1, codificacionHijo1I, menor, mayor);
				int indice2 = Busquedas.buscar(elem2, codificacionHijo2I, menor, mayor);
				//El elemento no esta en el segmento -> podemos añadirlo
				if(indice1 == -1){
					codificacionHijo1I[posicion1] = elem1;
					posicion1++;
					if(posicion1 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion1 = 0; 
					}
					
				}
				if(indice2 == -1){
					codificacionHijo2I[posicion2] = elem2;
					posicion2++;
					if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion2 = 0;
					}
					
				}
			}
			//En caso de que el corte mayor sea 26 hacemos esto para que no se salgan de los indices del array
			if(posicion1==longitudGen){ 
				posicion1 = 0;
				posicion2 = 0;
			}

			for(int k=0;k<=mayor;k++){ //Recorremos los padres hasta el punto de partida
				int elem1 = codificacionGenPadre1I[k];
				int elem2 = codificacionGenPadre2I[k];
				int indice1 = Busquedas.buscar(elem1, codificacionHijo1I, menor, mayor);
				int indice2 = Busquedas.buscar(elem2, codificacionHijo2I, menor, mayor);
				//El elemento no esta en el segmento -> podemos añadirlo
				if(indice1 == -1){
					codificacionHijo1I[posicion1] = elem1;
					posicion1++;
					if(posicion1 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion1 = 0;
					}
				}
				if(indice2 == -1){
					codificacionHijo2I[posicion2] = elem2;
					posicion2++;	
					if(posicion2 == longitudGen){ //Hemos llegado al final del padre y volvemos al principio
						posicion2 = 0;
					}
				}
			}
//FIN CRUCE OX*******************************************************************************************
			
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
