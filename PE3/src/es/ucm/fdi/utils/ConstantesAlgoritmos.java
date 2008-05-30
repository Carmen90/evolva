package es.ucm.fdi.utils;

public class ConstantesAlgoritmos {
	public static final int NUMERO_INTERCAMBIOS = 10;
	public static final int NUMERO_INSERCIONES = 1;
	public static final int NUMERO_CONTRINCANTES = 3;
	
	private int numIntercambios;
	private int numInserciones;
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
		numIntercambios = NUMERO_INTERCAMBIOS;
		numInserciones = NUMERO_INSERCIONES;
		numContrincantes = NUMERO_CONTRINCANTES;
	}
	
	public int getNumIntercambios() {
		return numIntercambios;
	}
	public void setNumIntercambios(int numIntercambios) {
		this.numIntercambios = numIntercambios;
	}
	public int getNumInserciones() {
		return numInserciones;
	}
	public void setNumInserciones(int numInserciones) {
		this.numInserciones = numInserciones;
	}
	public int getNumContrincantes() {
		return numContrincantes;
	}
	public void setNumContrincantes(int numContrincantes) {
		this.numContrincantes = numContrincantes;
	}
	
	
}
