package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class EvaluadorF5 implements Evaluador {

	private final double xMax = Math.PI;
	private final double xMin = 0.0;

	private int NUM_GENES;
	
	public EvaluadorF5(int numGenes){
		this.NUM_GENES = numGenes;
	}

	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;

		individuo.fenotipo();

		int numGenes = individuo.getGenes().length;

		for (int i = 0; i< numGenes;i++){

			double fenotipoXi = individuo.getFenotipo()[i];

			aptitud += Math.sin(fenotipoXi) * 
			Math.pow( Math.sin( (i+1 + 1)*Math.pow(fenotipoXi,2) / Math.PI ),20);
		}

		return -1*aptitud;

	}

	public Cromosoma generarCromosomaAleatorio(double tolerancia) {

		//crear cromosoma
		CromosomaBinario cromosoma = new CromosomaBinario(NUM_GENES);

		GenBinario[] genes = new GenBinario[cromosoma.getNumeroGenes()];

		//crear genes
		for (int j = 0; j<NUM_GENES;j++){

			int longitudGen = cromosoma.calcularLongitudGen(tolerancia, xMax, xMin);
			GenBinario gen = new GenBinario(longitudGen,xMax,xMin);

			boolean[] datosGen = new boolean[longitudGen];
			for (int i = 0; i< longitudGen; i++){
				datosGen[i] = MyRandom.aleatorioBool();
			}
			gen.setGen(datosGen);

			genes[j] = gen;

		}
		//setear genes
		cromosoma.setGenes(genes);

		//inicializar cromosoma
		cromosoma.inicializarCromosoma(this);

		return cromosoma;
	}

	//funcion de minimizacion, la aptidut mejorara a la aptitudMejor si es menor
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
