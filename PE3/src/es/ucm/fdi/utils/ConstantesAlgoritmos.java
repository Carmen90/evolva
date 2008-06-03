package es.ucm.fdi.utils;

public class ConstantesAlgoritmos {
	
	//Seleccion por Torneo
	public static final int NUMERO_CONTRINCANTES = 3;
	
	//Evaluador de las Hormigas
	public static final int MAX_PASOS = 400;
	public static final int MAX_PROFUNDIDAD = 3;
	
	private int numContrincantes;
	private int maxPasos;
	private int maxProfundidad;
	
	private static ConstantesAlgoritmos INSTANCE;
	
	private static void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new ConstantesAlgoritmos();
	}
	
	public static ConstantesAlgoritmos getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}
	
	private ConstantesAlgoritmos(){
		numContrincantes = NUMERO_CONTRINCANTES;
		maxPasos = MAX_PASOS;
		maxProfundidad = MAX_PROFUNDIDAD;
	}
	
	public int getNumContrincantes() {
		return numContrincantes;
	}
	public void setNumContrincantes(int numContrincantes) {
		this.numContrincantes = numContrincantes;
	}

	public int getMaxPasos() {
		return maxPasos;
	}

	public void setMaxPasos(int maxPasos) {
		this.maxPasos = maxPasos;
	}

	public int getMaxProfundidad() {
		return maxProfundidad;
	}

	public void setMaxProfundidad(int maxProfundidad) {
		this.maxProfundidad = maxProfundidad;
	}
	
	
}
