package es.ucm.fdi.algoritmos.cruzadores;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.Busquedas;
import es.ucm.fdi.utils.Ciudades;
import es.ucm.fdi.utils.MyRandom;

public class CruzadorERX implements Cruzador {

	public static final int MAX_ESTANCAMIENTOS = 5;
	
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
			//obtenemos el genBinario de los padres 1 y 2
			GenEntero genPadre1I = (GenEntero) padre1.getGenes()[i];
			GenEntero genPadre2I = (GenEntero) padre2.getGenes()[i];

			//creamos el genBinario hijo1 e hijo2
			GenEntero genHijo1I = new GenEntero(genPadre1I.getLongitud());
			GenEntero genHijo2I = new GenEntero(genPadre1I.getLongitud());

			//obtenemos la codificacion del gen i de cada padre
			int[] codificacionPadre1I = genPadre1I.getGen();
			int[] codificacionPadre2I = genPadre2I.getGen();

			/****************Cruce por Recombinacion de Rutas****************/
			//ArrayList<ArrayList<Integer>> adyacencias1 = generarTablaAdyacencias(codificacionPadre1I, codificacionPadre2I);
			//creamos la codificacion del gen i de cada hijo
			int[] codificacionHijo1I = this.cruceRecombinacion(codificacionPadre1I, codificacionPadre2I);
			int[] codificacionHijo2I = this.cruceRecombinacion(codificacionPadre2I, codificacionPadre1I);
			/****************FIN Cruce por Recombinacion de Rutas****************/

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

	private ArrayList<ArrayList<Integer>> generarTablaAdyacencias (int[] genesPadre1, int[] genesPadre2){
		ArrayList<ArrayList<Integer>> adyacencias  = new ArrayList<ArrayList<Integer>>(Ciudades.NUM_CIUDADES);
		for (int i = 0; i< Ciudades.NUM_CIUDADES; i++){
			adyacencias.add(new ArrayList<Integer>());
		}

		//para cada ciudad:
		for (int ciudad = 0; ciudad < Ciudades.NUM_CIUDADES; ciudad++){
			ArrayList<Integer> adyacentesCiudadI = adyacencias.get(ciudad);

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

			//añadimos las ciudades vecinas de la "ciudad" en el padre 2
			if (indiceEnPadre2 != Ciudades.NUM_CIUDADES - 1){
				int ciudadVecinaDer = genesPadre2[indiceEnPadre2 +1];
				if (Busquedas.buscar(ciudadVecinaDer, adyacentesCiudadI) == -1)
					adyacentesCiudadI.add(ciudadVecinaDer);
			}else{
				int ciudadVecinaDer = genesPadre2[0];
				if (Busquedas.buscar(ciudadVecinaDer, adyacentesCiudadI) == -1)
					adyacentesCiudadI.add(genesPadre2[0]);
			}

			if (indiceEnPadre2 != 0){
				int ciudadVecinaIzq = genesPadre2[indiceEnPadre2 - 1];
				if (Busquedas.buscar(ciudadVecinaIzq, adyacentesCiudadI) == -1)
					adyacentesCiudadI.add(genesPadre2[indiceEnPadre2-1]);
			}
			else{
				int ciudadVecinaIzq = genesPadre2[Ciudades.NUM_CIUDADES - 1];
				if (Busquedas.buscar(ciudadVecinaIzq, adyacentesCiudadI) == -1)
					adyacentesCiudadI.add(genesPadre2[Ciudades.NUM_CIUDADES - 1]);
			}

		}
		return adyacencias;
	}

	private int[] cruceRecombinacion (int[] padreOrigen, int[] padreAuxiliar){
		int[] codificacionHijo = new int[Ciudades.NUM_CIUDADES];
		
		int numeroCiudadesIntegradas = 0;
		int numeroEstancamientos = 0;
		
		do{
			//regeneramos la tabla de adyacencias.
			ArrayList<ArrayList<Integer>> adyacencias = generarTablaAdyacencias(padreOrigen, padreAuxiliar);
			//inicializamos la codificacion del Hijo (NO HARIA FALTA Y AUMENTA COMPLEJIDAD AL ALGORITMO)
			/*for (int i = 0; i<Ciudades.NUM_CIUDADES;i++){
				codificacionHijo[i] = 0;
			}*/
			//la ciudad 0 siempre tiene que ser madrid
			//codificacionHijo[0] = padreOrigen[0];
			//como hemos añadido la ciudad en la posicion 0, esta se elimina de los adyacentes de todas las ciudades
			//eliminarDeListaVecinos(codificacionHijo[0], adyacencias);
			
			//para aumentar la variabilidad del cruce, empezamos por la ciudad en la posicion 1
			//X <- el primer elemento del padre aleatorio
			//codificacionHijo[1] = padreOrigen[1];
			//numeroCiudadesIntegradas = 2;
			
			codificacionHijo[0] = padreOrigen[0];
			numeroCiudadesIntegradas = 1;
	
			boolean estancamiento = false;
			int i = 1;
			//Mientras no este lleno el hijo:
			while (!estancamiento && i< Ciudades.NUM_CIUDADES){
	
				int ciudadOrigen = codificacionHijo[i-1];
				
				//quitar X de la lista de vecinos
				eliminarDeListaVecinos(ciudadOrigen,adyacencias);
				
				ArrayList<Integer> candidatos = adyacencias.get(ciudadOrigen);
				if (candidatos.size() == 0){ //si no hay candidatos entonces nos hemos bloqueado...
					estancamiento = true;
					numeroEstancamientos++;
					System.out.println("ERX se ha estancado en la ciudad "+ciudadOrigen);
					System.out.println("El padre 1 era: ");
					String cadena = "[ ";
					for (int n = 0; n<padreOrigen.length; n++){
						cadena += (padreOrigen[n]+" ");
					}
					cadena += "]";
					System.out.println(cadena);
					System.out.println("El padre 2 era: ");
					cadena = "[ ";
					for (int n = 0; n<padreAuxiliar.length; n++){
						cadena +=  (padreAuxiliar[n]+" ");
					}
					cadena += "]";
					System.out.println(cadena);
				//sino podemos proseguir
				}else{
					//miramos que candidato contiene menos ciudades adyacentes
					//iniciamos como menor al primer candidato
					int ciudadConMenosVecinos = candidatos.get(0);
					//para candidato a partir del segundo...
					for (int j = 1; j< candidatos.size();j++){
						//cogemos el numero de vecinos del candidato i
						ArrayList<Integer> vecinosDelCandidatoI = adyacencias.get(candidatos.get(j));
						int numeroVecinosCandidato = vecinosDelCandidatoI.size();
						//cogemos el numero de vecinos del menor
						ArrayList<Integer> vecinosDelMenor = adyacencias.get(ciudadConMenosVecinos);
						int numeroVecinosMenor = vecinosDelMenor.size();
						//si el candidato i tiene menos vecinos lo actualizamos y decimos que no todos 
						//los candidatos tienen el mismo numero de vecinos
						if (numeroVecinosCandidato < numeroVecinosMenor){
							ciudadConMenosVecinos = candidatos.get(j);
						}
					}
					//en este punto ya sabemos cual es el minimo,
					//pero no sabemos si hay varios minimos, con lo cual lo buscamos:
					ArrayList<Integer> minimos = new ArrayList<Integer>();
					//cogemos el numero de vecinos del menor
					ArrayList<Integer> vecinosDelMenor = adyacencias.get(ciudadConMenosVecinos);
					int numeroVecinosMenor = vecinosDelMenor.size();
					
					for (int j = 0; j< candidatos.size();j++){
						
						//cogemos el numero de vecinos del candidato i
						ArrayList<Integer> vecinosDelCandidatoI = adyacencias.get(candidatos.get(j));
						int numeroVecinosCandidato = vecinosDelCandidatoI.size();
						
						if (numeroVecinosMenor == numeroVecinosCandidato)
							minimos.add(candidatos.get(j));
					}
					//si el tamaño de los minimos es distinto de 1, quiere decir que hay mas de un minimo, 
					//con lo cual el random lo hacemos solo entre los minimos.
					if (minimos.size() != 1){
						int indiceCandidato = MyRandom.aleatorioEntero(0, minimos.size());
						ciudadConMenosVecinos = minimos.get(indiceCandidato);
					}
					codificacionHijo[i] = ciudadConMenosVecinos;
					System.out.println("Ciudad elegida en la iteracion "+i+" = "+ ciudadConMenosVecinos);
					numeroCiudadesIntegradas++;
					i++;
				}
			}
		}while(numeroCiudadesIntegradas != Ciudades.NUM_CIUDADES && numeroEstancamientos != MAX_ESTANCAMIENTOS);

		if (numeroEstancamientos == MAX_ESTANCAMIENTOS){
			System.out.println("No se ha podido generar un hijo por superar el maximo de estancamientos...");
			return padreOrigen;
		}else return codificacionHijo;
	}

	private void eliminarDeListaVecinos (int vecino, ArrayList<ArrayList<Integer>> listaVecinos){

		for (int ciudad = 0; ciudad < Ciudades.NUM_CIUDADES; ciudad++){
			ArrayList<Integer> adyacentesCiudad = listaVecinos.get(ciudad);
			boolean eliminado = false;
			int i = 0;
			while (!eliminado && i< adyacentesCiudad.size()){
				if (vecino == adyacentesCiudad.get(i).intValue()){
					adyacentesCiudad.remove(i);
					eliminado = true;
				}
				i++;
			}
		}
	}



}
