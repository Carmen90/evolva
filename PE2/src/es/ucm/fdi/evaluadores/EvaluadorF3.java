package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class EvaluadorF3 implements Evaluador {

	private final double xMax = 25.0;
	private final double xMin = 0.0;
	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;
		
		individuo.fenotipo();
		
		double fenotipoX = individuo.getFenotipo()[0];
		
		aptitud = Math.sin(fenotipoX) / ( 1+ Math.sqrt(fenotipoX) + (Math.cos(fenotipoX)/(1+fenotipoX)));
			
		return aptitud;
	}

	public Cromosoma generarCromosomaAleatorio(double tolerancia) {
		//crear cromosoma
		CromosomaBinario cromosoma = new CromosomaBinario(1);
		
		//crear genes
		int longitudGen = cromosoma.calcularLongitudGen(tolerancia, xMax, xMin);
		GenBinario gen = new GenBinario(longitudGen,xMax,xMin);
		
		boolean[] datosGen = new boolean[longitudGen];
		for (int i = 0; i< longitudGen; i++){
			datosGen[i] = MyRandom.aleatorioBool();
		}
		gen.setGen(datosGen);
		
		GenBinario[] genes = new GenBinario[cromosoma.getNumeroGenes()];
		genes[0] = gen;
		
		//setear genes
		cromosoma.setGenes(genes);
		
		//inicializar cromosoma
		cromosoma.inicializarCromosoma(this);
		
		return cromosoma;
	}

	//funcion de maximizacion, la aptidut mejorara a la aptitudMejor si es mayor
	public boolean esMejorAptitud(double aptitud, double aptitudMejor) {
		return aptitud < aptitudMejor;
	}
	
	//como el problema es de minimizacion, transformamos las aptitudes
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
