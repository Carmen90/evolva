package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class EvaluadorF1 implements Evaluador {

	private final double xMax = 1.0;
	private final double xMin = 0.0;
	
	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;
		
		//el fenotipo se debe haber calculado antes de la llamada a evaluaAptitud
		
		double fenotipoX = individuo.getFenotipo()[0];
		
		aptitud = fenotipoX + Math.abs(Math.sin(32*Math.PI*fenotipoX));
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
		return aptitud > aptitudMejor;
	}

	//como el problema es de maximizacion, no hacemos nada
	public double[] transformarAptitudesAMaximizacion(double[] aptitudesPositivas) {
		return aptitudesPositivas;
	}
}
