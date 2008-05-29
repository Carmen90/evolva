package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.genes.Funcion;

public class CruzadorArboreo implements Cruzador{
	
	private GenArboreo nodoI = null;

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
			
			/******************************DEPURACION*******************************/
			System.out.println("punto de cruce padre 1: " + puntoCrucePadre1.toString());
			System.out.println("punto de cruce padre 2: " + puntoCrucePadre2.toString());
			
			//creamos el genEntero hijo1 e hijo2
			GenArboreo genHijo1I = (GenArboreo) genPadre1I.copiaGen();
			GenArboreo genHijo2I = (GenArboreo) genPadre2I.copiaGen();
						
			//creamos la codificacion del gen i de cada hijo
			/****************Cruce Arboreo****************/
			obtenerNodo(puntoCrucePadre1.numeroNodo, genHijo1I);
			GenArboreo ramaHijo1 = this.nodoI.Remover(puntoCrucePadre1.numeroHijo);
			
			obtenerNodo(puntoCrucePadre2.numeroNodo, genHijo2I);
			GenArboreo ramaHijo2 = this.nodoI.Remover(puntoCrucePadre2.numeroHijo);
			
			obtenerNodo(puntoCrucePadre1.numeroNodo, genHijo1I);
			this.nodoI.Agregar(ramaHijo2, puntoCrucePadre1.numeroHijo);
			
			obtenerNodo(puntoCrucePadre2.numeroNodo, genHijo2I);
			this.nodoI.Agregar(ramaHijo1, puntoCrucePadre2.numeroHijo);
							
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
	//			---->> [COMENTADO] si el hijo no es terminal ya tenemos el punto de cruce
	//			---->> [COMENTADO] sino repetimos
	//	    ---->> [COMENTADO] si se agotan los hijos repetimos el proceso entero.
	// El punto de cruce puede ser tambien un terminal ahora.
	private PuntoDeCruce calcularPuntoDeCruce(GenArboreo g){
		
		PuntoDeCruce punto = new PuntoDeCruce();
		boolean esFuncionElPadre = false;
		//boolean esFuncionElHijo = false;
		
		do{
			
			int posicionAleatoria = MyRandom.aleatorioEntero(0,g.calcularNumeroNodos()+1);	
			obtenerNodo(posicionAleatoria, g);
			//obtenerNodo(posicionAleatoria, g);
			GenArboreo genEnPosicion = this.nodoI;
			if (genEnPosicion.getLongitud() != 0){
				
				esFuncionElPadre = true;
				
				/*boolean[] hijosVisitados = new boolean[genEnPosicion.getLongitud()];
				boolean todosLosHijosVisitados = false;
				GenArboreo hijoI = null;
				int posicionHijoAleatorio = -1;
				
				do{*/
					//calculamos un hijo aleatorio
					int posicionHijoAleatorio = MyRandom.aleatorioEntero(0, genEnPosicion.getLongitud());
					//si no hemos visitado este hijo le visitamos
				/*	if (!hijosVisitados[posicionHijoAleatorio]){
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
				*/
				/*if (hijoI.getLongitud()!= 0){
					esFuncionElHijo = true; */
					punto.setNumeroHijo(posicionHijoAleatorio);
					punto.setNumeroNodo(posicionAleatoria);
				//}
			}
			
		}while(! (esFuncionElPadre /*&& esFuncionElHijo*/) );
		return punto;
	}
	
	//funcion que setea el nodoI en el indice indicado.
	//devuelve hasta que nodo hemos bajado en el arbol (para controlar la recursion)
	//cuando hemos llegado al nodo, encontes devolvemos -1.
	private int obtenerNodo(int indice, GenArboreo g){
		//si hemos llegado al indice 0, devolvemos el GenArboreo.
		if (indice == 0){
			this.nodoI = g;
			indice = -1;
		}else{
			indice--;
			
			int i = 0;
			while (i < g.getLongitud() && indice != -1){
				indice = obtenerNodo(indice, ((Funcion)g).getArgumentos()[i]);
				i++;
			}
		}
		return indice;
		
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
		public String toString(){
			return "numeroNodo: "+numeroNodo+"; numeroHijo: "+numeroHijo;
		}
		
	}
}
