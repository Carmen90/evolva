package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaBinario;
import es.ucm.fdi.genes.GenBinario;
import es.ucm.fdi.utils.MyRandom;

public class EvaluadorF4 implements Evaluador {

	private final double xMax = 10.0;
	private final double xMin = -10.0;
	
	public double evaluaAptitud(Cromosoma individuo) {
		double aptitud = 0.0;
		
		individuo.fenotipo();
		
		double fenotipoX1 = individuo.getFenotipo()[0];
		
		double fenotipoX2 = individuo.getFenotipo()[1];
		
		double producto1 = 0.0;
		for (int i = 1; i<= 5; i++){
			producto1 += i*Math.cos((i+1)*fenotipoX1 + i);
		}
		double producto2 = 0.0;
		for (int i = 1; i<= 5; i++){
			producto2 += i*Math.cos((i+1)*fenotipoX2 + i);
		}
		
		aptitud = producto1*producto2;
			
		return aptitud;
	}

	public Cromosoma generarCromosomaAleatorio(double tolerancia) {
		//crear cromosoma
		CromosomaBinario cromosoma = new CromosomaBinario(2);
		
		//crear genes
		
		//GEN X1
		int longitudGen1 = cromosoma.calcularLongitudGen(tolerancia, xMax, xMin);
		GenBinario gen1 = new GenBinario(longitudGen1,xMax,xMin);
		
		boolean[] datosGen1 = new boolean[longitudGen1];
		for (int i = 0; i< longitudGen1; i++){
			datosGen1[i] = MyRandom.aleatorioBool();
		}
		gen1.setGen(datosGen1);
		
		//GEN X2
		int longitudGen2 = cromosoma.calcularLongitudGen(tolerancia, xMax, xMin);
		GenBinario gen2 = new GenBinario(longitudGen2,xMax,xMin);
		
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
