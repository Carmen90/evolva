package es.ucm.fdi.algoritmos.cruzadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Terminal.terminales;

public class CruzadorArboreoMejorado implements Cruzador{

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

			//creamos el genEntero hijo1 e hijo2
			GenArboreo genHijo1I = (GenArboreo) genPadre1I.copiaGen();
			GenArboreo genHijo2I = (GenArboreo) genPadre2I.copiaGen();

			//calculamos los puntos de cruce en los padres
			PuntoDeCruce puntoCrucePadre1 = this.calcularPuntoDeCruce(genHijo1I);
			PuntoDeCruce puntoCrucePadre2 = this.calcularPuntoDeCruce(genHijo2I);

			/******************************DEPURACION*******************************/
			/*System.out.println("punto de cruce padre 1: " + puntoCrucePadre1.toString());
			System.out.println("punto de cruce padre 2: " + puntoCrucePadre2.toString());*/
			
			GenArboreo rama1 = puntoCrucePadre1.getPadre();
			GenArboreo rama2 = puntoCrucePadre2.getPadre();
			
			GenArboreo ramaAux;
			//si a la rama 1 no se le pudieron seleccionar hijos, tendremos que asignarle directamente
			//al hijo seleccionado de la rama 2 o la rama 2 si a esta no se le pudieron seleccionar hijos
			//con lo cual, a la rama 1 entera le asignamos la rama 2
			if (puntoCrucePadre1.getNumeroHijo() == -1){
				ramaAux = rama1;
				if (puntoCrucePadre2.getNumeroHijo() == -1){
					rama1 = rama2;
				}else{
					rama1 = ((Funcion)rama2).Remover(puntoCrucePadre2.getNumeroHijo());
				}
			//si a la rama 1 se le pudieron seleccionar hijos, le asignamos el hijo seleccionado de la rama2
			//si se pudo seleccionar hijo, o la rama 2 entera si no se pudo
			}else{
				ramaAux = rama1.Remover(puntoCrucePadre1.getNumeroHijo());
				if (puntoCrucePadre2.getNumeroHijo() == -1){
					rama1.Agregar(rama2, puntoCrucePadre1.getNumeroHijo());
				}else{
					rama1.Agregar(((Funcion)rama2).Remover(puntoCrucePadre2.getNumeroHijo()), puntoCrucePadre1.getNumeroHijo());
				}
			}
			
			//tenemos por completo en ramaAux, lo que tenemos que setear en la rama 2.
			if (puntoCrucePadre2.getNumeroHijo() == -1){
				rama2 = ramaAux;
			}else{
				//si tuvimos que eliminar el hijo de la rama 2, ya lo eliminamos.
				rama2.Agregar(ramaAux, puntoCrucePadre2.getNumeroHijo());
			}


			//creamos la codificacion del gen i de cada hijo
			/****************Cruce Arboreo****************/
			
			//control de profundidad
			genHijo1I = controlarProfundidad(genHijo1I);
			genHijo2I = controlarProfundidad(genHijo2I);
			
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

	//para calcular el punto de cruce,
	//
	private PuntoDeCruce calcularPuntoDeCruce(GenArboreo g){

		PuntoDeCruce punto;

		int profundidadMax = ConstantesAlgoritmos.getInstance().getMaxProfundidad();
		//generamos los segmentos de probabilidad para los diferentes nodos del arbol, segun los niveles.
		//para el primer nivel, asignamos el 50%
		//para el siguiente nivel, el 50% del 50%, y asi sucesivamente.
		double probabilidad = 1;
		double[] segmentosProbabilidad = new double[profundidadMax + 1];
		//No podemos seleccionar probabilidad para la raiz
		segmentosProbabilidad[0] = -1;
		for (int i = 1; i <= profundidadMax; i++){
			probabilidad = probabilidad / 4;
			segmentosProbabilidad[i] = probabilidad;
			//System.out.println("Segmento en "+i+"= "+probabilidad);
		}

		//una vez generadas las probabilidades para los niveles, calculamos una probabilidad aleatoria, y comenzamos por la raiz.
		double probabilidadNivel = Math.random();

		//comenzamos por la raiz
		punto = obtenerPadreConProbabilidad(probabilidadNivel,segmentosProbabilidad, g);

		return punto;
	}

	private PuntoDeCruce obtenerPadreConProbabilidad(double probabilidad, double[] probabilidades, GenArboreo g){
		PuntoDeCruce punto;

		//si hemos llegado al final del arbol, devolvemos el terminal
		if (g.getProfundidad() == ConstantesAlgoritmos.getInstance().getMaxProfundidad()){
			punto = new PuntoDeCruce();
			punto.setPadre(g);
			punto.setNumeroHijo(-1);
			//si todavia estamos en una funcion
		}else{
			//si estamos en un terminal, entonces devolvemos el terminal, con el hijo a -1, ya que no tiene mas hijos
			if (g.getLongitud() == 0){
				punto = new PuntoDeCruce();
				punto.setPadre(g);
				punto.setNumeroHijo(-1);
			}else{
				
				//Si la probabilidad calculada es mayor que la probabilidad correspondiente al nivel Actual + 1, 
				//cogemos un hijo al azar y ya hemos terminado.
				if (probabilidad >= probabilidades[g.getProfundidad() + 1]){
					punto = new PuntoDeCruce();
					int hijoAleatorio = MyRandom.aleatorioEntero(0, g.getLongitud());
					punto.setPadre(g);
					punto.setNumeroHijo(hijoAleatorio);

				//Si la probabilidad calculada es menor que la probabilidad correspondiente al nivel Actual + 1,
				//calculamos un hijo al azar, y repetimos el proceso.	
				}else{
					int hijoAleatorio = MyRandom.aleatorioEntero(0, g.getLongitud());
					punto = obtenerPadreConProbabilidad(probabilidad, probabilidades, ((Funcion)g).getArgumentos()[hijoAleatorio]);
				}

			}
		}
		return punto;
	}

	//Funcion para el control de profundidad de los arboles cruzados
	//cuando llegamos al limite de profundidad, cambiamos los hijos 
	//funcion y los sustituimos por terminales aleatorios
	public GenArboreo controlarProfundidad(GenArboreo g){
		//si estamos a un nivel de la profundidad maxima, entonces miramos los hijos 
		//y sustituimos las funciones por terminales
		if (g.getProfundidad() == ConstantesAlgoritmos.getInstance().getMaxProfundidad() - 1){
			for (int i = 0; i< g.getLongitud(); i++){
				//si el hijo i es una funcion
				if (((Funcion)g).getArgumentos()[i].getLongitud() != 0){
					//calculamos un terminal aleatorio
					int indiceTerminal = MyRandom.aleatorioEntero(0, Terminal.NUM_TERMINALES);
					terminales valorTerminal = terminales.values()[indiceTerminal];
					GenArboreo terminal = new Terminal(valorTerminal,ConstantesAlgoritmos.getInstance().getMaxProfundidad());

					//removemos el hijo actual
					GenArboreo sustituido = ((Funcion)g).Remover(i);
					//seteamos el terminal nuevo
					((Funcion)g).Agregar(terminal, i);

					/*DEPURACION*/
					/*System.out.println("podada la rama: "+ sustituido.toString());
					System.out.println("sustituida por: "+terminal.toString());*/
				}
			}
			//si no hemos llegado al nivel de profundidad, entonces seguimos controlando 
			//la profundidad para cada uno de los hijos
		}else{
			for (int i = 0; i< g.getLongitud(); i++){
				((Funcion)g).getArgumentos()[i] = controlarProfundidad(((Funcion)g).getArgumentos()[i]);
			}
		}
		return g;

	}


	//Clase utilizada para representar los puntos de cruce en un arbol
	class PuntoDeCruce{
		private GenArboreo padre;
		private int numeroHijo;

		public GenArboreo getPadre() {
			return padre;
		}
		public void setPadre(GenArboreo padre) {
			this.padre = padre;
		}
		public int getNumeroHijo() {
			return numeroHijo;
		}
		public void setNumeroHijo(int numeroHijo) {
			this.numeroHijo = numeroHijo;
		}
		public String toString(){
			return "numeroNodo: "+padre.toString()+"; numeroHijo: "+numeroHijo;
		}

	}
}
