package es.ucm.fdi.utils;

public class Singleton {
	private static ConstantesAlgoritmos INSTANCE;
	
	private static void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new ConstantesAlgoritmos();
	}
	
	public static ConstantesAlgoritmos getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}
}
