package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaEnteroViajante;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.utils.Ciudades;

public class EvaluadorViajante implements Evaluador{

	public boolean esMejorAptitud(double aptitud, double aptitudMejor) {
		//se trata de un problema de minimizacion, pues es mejor una distancia mas pequeña
		return aptitud < aptitudMejor;
	}

	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;
		
		GenEntero gen = (GenEntero) individuo.getGenes()[0];
		aptitud = calcularDistancias(gen);
		return aptitud;
		
	}

	private double calcularDistancias(GenEntero gen) {
		int[] genes = gen.getGen();
		//iniciamos la cuenta con la distancia entre la última ciudad y la primera
		double acumulada = Ciudades.DISTANCIAS[genes[genes.length-1]][genes[0]];
		for (int i = 0; i< genes.length-1; i++){
			int origen = genes[i];
			int destino = genes[i+1];
			acumulada += Ciudades.DISTANCIAS[origen][destino];
		}
		return acumulada;
	}

	public Cromosoma generarCromosomaAleatorio(double tolerancia) {
		//crear cromosoma
		CromosomaEnteroViajante cromosoma = new CromosomaEnteroViajante(1);
		
		//crear genes
		int longitudGen = cromosoma.calcularLongitudGen();
		GenEntero gen = new GenEntero(longitudGen);
		
		int[] datosGen = new int[longitudGen];
		
		//todavia no se ha generado ninguna ciudad
		boolean[] generados = new boolean[longitudGen];
		for (int i = 0; i< longitudGen;i++){
			generados[i] = false;
		}
		
		//inicializamos la primera ciudad, ya que siempre empezaremos por Madrid
		generados[Ciudades.MADRID] = true;
		datosGen[0] = Ciudades.MADRID;
		//para las restantes:
		int j = 1;
		//int j = 0;
		while (j < longitudGen){
			//hay 28 ciudades numeradas de 0 a 27
			int aleatorio = MyRandom.aleatorioEntero(0,28);
			if (!generados[aleatorio]){
				datosGen[j] = aleatorio;
				generados[aleatorio] = true;
				j++;
			}
		}
		gen.setGen(datosGen);
		
		GenEntero[] genes = new GenEntero[cromosoma.getNumeroGenes()];
		genes[0] = gen;
		
		//setear genes (al setear los genes automaticamente se inicializa el cromosoma)
		cromosoma.setGenes(genes, this);
		
		return cromosoma;
	}

	public double[] transformarAptitudesAMaximizacion(double[] aptitudesPositivas) {
		double aptitudMayor = aptitudesPositivas[0];
		
		for (int i = 1; i< aptitudesPositivas.length; i++){
			if (aptitudesPositivas[i] > aptitudMayor) aptitudMayor = aptitudesPositivas[i];
		}
		
		for (int i = 0; i< aptitudesPositivas.length; i++){
			aptitudesPositivas[i] = aptitudMayor - aptitudesPositivas[i];
		}
		
		return aptitudesPositivas;
	}

}
