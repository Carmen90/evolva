package es.ucm.fdi.utils;

public class ConstantesAlgoritmos {
	
	public static final int NUMERO_CONTRINCANTES = 3;
	
	private int numContrincantes;
	
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
	}
	
	public int getNumContrincantes() {
		return numContrincantes;
	}
	public void setNumContrincantes(int numContrincantes) {
		this.numContrincantes = numContrincantes;
	}
	
	
}
