package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.genes.Gen;

public interface Evaluador {
	
	//cada funcion debe definir el numero de genes que tendran
	//los individuos, asi como los valores maximos y minimos para
	//cada gen (xMax y xMin)
	
	//funcion que evalua la aptitud de un individuo en particular
	public double evaluaAptitud(Cromosoma individuo);
	
	//funcion que genera cromosomas aleatorios, en funcion del
	//numero de genes que necesite cada individuo dependiendo 
	//de cada funcion, y los valores xMax y xMin que necesite 
	//cada gen en particular del cromosoma creado
	public Cromosoma generarCromosomaAleatorio();
	
	//funcion que genera genes aleatorios
	public Gen generarGenAleatorio();
	
	
	//para depuracion
	public Cromosoma generarCromosomaFijo();
	
	
	
	//funcion que especifica la mejora entre dos aptitudes
	public boolean esMejorAptitud (double aptitud, double aptitudMejor);
	
	public double[] transformarAptitudesAMaximizacion(double[] aptitudesPositivas);

}
