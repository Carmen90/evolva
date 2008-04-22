package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
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
			//Especificamos las posiciones sin valor de los progenitores orignales que no planteen conflicto
			//Recorremos todo el gen hijo en busca de algun valor repetido
			
			//Parte del gen por debajo del corte menor
			for(int j=0;j<menor;j++){
				int k=menor;
				boolean encontrado = false;
				//Hijo1
				while(!encontrado && k<=mayor){
					if(codificacionGenPadre1I[j]==codificacionHijo1I[k]){ //Conflicto
						//Tenemos que comprobar que el numero de la pareja no este ya en el segmento del hijo
						//Buscamos la pareja en el segmento
						int elem = codificacionHijo2I[k];
						int indice = Busquedas.buscar(elem, codificacionHijo1I, menor, mayor);
						while(indice != -1){
							elem = codificacionHijo2I[indice];
							indice = Busquedas.buscar(elem, codificacionHijo1I, menor, mayor);
								
						}
						codificacionHijo1I[j] = elem;
						encontrado = true;
					}
					k++;
				}
				if(!encontrado)
					codificacionHijo1I[j] = codificacionGenPadre1I[j];
				
				k=menor;
				encontrado = false;
				//Hijo2
				while(!encontrado && k<=mayor){
					if(codificacionGenPadre2I[j]==codificacionHijo2I[k]){ //Conflicto
						//Tenemos que comprobar que el numero de la pareja no este ya en el segmento del hijo
						//Buscamos la pareja en el segmento
						int elem = codificacionHijo1I[k];
						int indice = Busquedas.buscar(elem, codificacionHijo2I, menor, mayor);
						while(indice!=-1){
							elem = codificacionHijo1I[indice];
							indice = Busquedas.buscar(elem, codificacionHijo2I, menor, mayor);
						}
						codificacionHijo2I[j] = elem;
						encontrado = true;
					}
					k++;
				}
				if(!encontrado)
					codificacionHijo2I[j] = codificacionGenPadre2I[j];
			}
			
			//Parte del gen por encima del corte mayor
			for(int j=mayor+1;j<longitudGen;j++){
				int k=menor;
				boolean encontrado = false;
				//Hijo1
				while(!encontrado && k<=mayor){
					if(codificacionGenPadre1I[j]==codificacionHijo1I[k]){ //Conflicto
						//Tenemos que comprobar que el numero de la pareja no este ya en el segmento del hijo
						//Buscamos la pareja en el segmento
						int elem = codificacionHijo2I[k];
						int indice = Busquedas.buscar(elem, codificacionHijo1I, menor, mayor);
						while(indice != -1){
							elem = codificacionHijo2I[indice];
							indice = Busquedas.buscar(elem, codificacionHijo1I, menor, mayor);
								
						}
						codificacionHijo1I[j] = elem;
						encontrado = true;
					}
					k++;
				}
				if(!encontrado)
					codificacionHijo1I[j] = codificacionGenPadre1I[j];
				
				k=menor;
				encontrado = false;
				//Hijo2
				while(!encontrado && k<=mayor){
					if(codificacionGenPadre2I[j]==codificacionHijo2I[k]){ //Conflicto
						//Tenemos que comprobar que el numero de la pareja no este ya en el segmento del hijo
						//Buscamos la pareja en el segmento
						int elem = codificacionHijo1I[k];
						int indice = Busquedas.buscar(elem, codificacionHijo2I, menor, mayor);
						while(indice!=-1){
							elem = codificacionHijo1I[indice];
							indice = Busquedas.buscar(elem, codificacionHijo2I, menor, mayor);
						}
						codificacionHijo2I[j] = elem;
						encontrado = true;
					}
					k++;
				}
				if(!encontrado)
					codificacionHijo2I[j] = codificacionGenPadre2I[j];
			}
			
			GenEntero genHijo1I = new GenEntero(longitudGen);
			GenEntero genHijo2I = new GenEntero(longitudGen);
			
			genHijo1I.setGen(codificacionHijo1I);
			genHijo2I.setGen(codificacionHijo2I);
			
			genesHijo1[i] = genHijo1I;
			genesHijo2[i] = genHijo2I;			
				
		}
		hijo1.setGenes(genesHijo1, evaluador);
		hijo2.setGenes(genesHijo2, evaluador);
		
		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = hijo1;
		hijos[1] = hijo2;
		return hijos;
	}

}
