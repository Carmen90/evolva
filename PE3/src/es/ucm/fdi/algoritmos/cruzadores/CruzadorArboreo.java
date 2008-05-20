package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.genes.Funcion;

public class CruzadorArboreo implements Cruzador{

	public Cromosoma[] cruce(Cromosoma padre1, Cromosoma padre2) {
		//crear cromosoma, crear genes, setear genes, inicializar cromosoma

		int numeroGenes = padre1.getNumeroGenes();
		//creamos los cromosomas hijos
		Cromosoma hijo1 = new CromosomaHormigas(numeroGenes); 
		Cromosoma hijo2 = new CromosomaHormigas(numeroGenes);
		
		//creamos el array de genes de los hijos
		GenArboreo[] genesHijo1 = new GenArboreo[numeroGenes];
		GenArboreo[] genesHijo2 = new GenArboreo[numeroGenes];
		
		//para cada gen de cada cromosoma padre, calculamos un punto de cruce y los cruzamos por separado.
		for (int i = 0; i < padre1.getNumeroGenes(); i++){
			//obtenemos el genEntero de los padres 1 y 2
			GenArboreo genPadre1I = (GenArboreo) padre1.getGenes()[i];
			GenArboreo genPadre2I = (GenArboreo) padre2.getGenes()[i];
			
			//calculamos los puntos de cruce en los padres
			PuntoDeCruce puntoCrucePadre1 = this.calcularPuntoDeCruce(genPadre1I);
			PuntoDeCruce puntoCrucePadre2 = this.calcularPuntoDeCruce(genPadre2I);	
			
			//creamos el genEntero hijo1 e hijo2
			GenArboreo genHijo1I = null;
			GenArboreo genHijo2I = null;
						
			//creamos la codificacion del gen i de cada hijo
			/****************Cruce Arboreo****************/
			
			/****************FIN Cruce Arboreo****************/
			
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
	
	//calcula un punto de cruce correspondiente a una funcion.
	//para ello, generamos un numero aleatorio entre 0 y el numero de nodos - 1
	//y recorremos el arbol para ver si es funcion o terminal.
	//	si es terminal repetimos el proceso.
	//	si no es terminal 
	//		seleccionamos uno de los hijos. (el que se va a sustituir)
	//			si el hijo no es terminal ya tenemos el punto de cruce
	//			sino repetimos
	//	si se agotan los hijos repetimos el proceso entero.
	private PuntoDeCruce calcularPuntoDeCruce(GenArboreo g){
		
		PuntoDeCruce punto = new PuntoDeCruce();
		boolean esFuncionElPadre = false;
		boolean esFuncionElHijo = false;
		
		do{
			
			int posicionAleatoria = MyRandom.aleatorioEntero(0,g.calcularNumeroNodos()+1);	
			GenArboreo genEnPosicion = obtenerNodo(posicionAleatoria, g);
			if (genEnPosicion.getLongitud() != 0){
				
				esFuncionElPadre = true;
				
				boolean[] hijosVisitados = new boolean[genEnPosicion.getLongitud()];
				boolean todosLosHijosVisitados = false;
				GenArboreo hijoI = null;
				int posicionHijoAleatorio = -1;
				
				do{
					//calculamos un hijo aleatorio
					posicionHijoAleatorio = MyRandom.aleatorioEntero(0, genEnPosicion.getLongitud());
					//si no hemos visitado este hijo le visitamos
					if (!hijosVisitados[posicionHijoAleatorio]){
						hijoI = ((Funcion)genEnPosicion).getArgumentos()[posicionHijoAleatorio];
						hijosVisitados[posicionHijoAleatorio] = true;
					}
					//contamos cuantos hijos hemos visitado ya.
					int numHijosVisitados = 0;
					for (int i = 0; i<hijosVisitados.length;i++){
						if (hijosVisitados[i]) numHijosVisitados++; 
					}
					//si el numero de hijos visitados es igual al numero de hijos, ya hemos visitado tordos
					todosLosHijosVisitados = numHijosVisitados == genEnPosicion.getLongitud();
				
				//repetimos el calculo del hijo mientras no sea una funcion y no hayamos visitado todos
				}while(hijoI.getLongitud() == 0 && !todosLosHijosVisitados);
				
				if (hijoI.getLongitud()!= 0){
					esFuncionElHijo = true;
					punto.setNumeroHijo(posicionHijoAleatorio);
					punto.setNumeroNodo(posicionAleatoria);
				}
			}
			
		}while(!esFuncionElPadre && !esFuncionElHijo);
		return punto;
	}
	
	//funcion que devuelve el Nodo en la posicion indicada por el indice (preorden).
	private GenArboreo obtenerNodo(int indice, GenArboreo g){
		//si hemos llegado al indice 0, devolvemos el GenArboreo.
		if (indice == 0) return g;
		else{
			GenArboreo descendiente = null;
			
			//si la longitud es 0 estamos en un terminal, enotonces devolvemos null.
			for (int i = 0; i< g.getLongitud(); i++){
				indice++;
				if (descendiente == null)
					descendiente = obtenerNodo(indice, ((Funcion)g).getArgumentos()[i]);
				
			}
			return descendiente;
		}
		
	}
	
	class PuntoDeCruce{
		private int numeroNodo;
		private int numeroHijo;
		public int getNumeroNodo() {
			return numeroNodo;
		}
		public void setNumeroNodo(int numeroNodo) {
			this.numeroNodo = numeroNodo;
		}
		public int getNumeroHijo() {
			return numeroHijo;
		}
		public void setNumeroHijo(int numeroHijo) {
			this.numeroHijo = numeroHijo;
		}
		
	}
}
