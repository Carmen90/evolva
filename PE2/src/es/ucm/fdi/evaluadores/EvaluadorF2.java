package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class EvaluadorF2 implements Evaluador {

	private final double xMax = 12.1;
	private final double xMin = -3.0;
	private final double yMax = 5.8;
	private final double yMin = 4.1;
	
	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;
		
		individuo.fenotipo();
		
		double fenotipoX = individuo.getFenotipo()[0];
		
		double fenotipoY = individuo.getFenotipo()[1];
		
		aptitud = 21.5 + fenotipoX*Math.sin(4*Math.PI*fenotipoX) + 
						 fenotipoY*Math.sin(20*Math.PI*fenotipoY);
			
		return aptitud;
	}

	public Cromosoma generarCromosomaAleatorio(double tolerancia) {
		//crear cromosoma
		CromosomaBinario cromosoma = new CromosomaBinario(2);
		
		//crear genes
		
		//GEN X
		int longitudGen1 = cromosoma.calcularLongitudGen(tolerancia, xMax, xMin);
		GenBinario gen1 = new GenBinario(longitudGen1,xMax,xMin);
		
		boolean[] datosGen1 = new boolean[longitudGen1];
		for (int i = 0; i< longitudGen1; i++){
			datosGen1[i] = MyRandom.aleatorioBool();
		}
		gen1.setGen(datosGen1);
		
		//GEN Y
		int longitudGen2 = cromosoma.calcularLongitudGen(tolerancia, yMax, yMin);
		GenBinario gen2 = new GenBinario(longitudGen2,yMax,yMin);
		
		boolean[] datosGen2 = new boolean[longitudGen2];
		for (int i = 0; i< longitudGen2; i++){
			datosGen2[i] = MyRandom.aleatorioBool();
		}
		gen2.setGen(datosGen2);
		
		GenBinario[] genes = new GenBinario[cromosoma.getNumeroGenes()];
		genes[0] = gen1;
		genes[1] = gen2;
		
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
