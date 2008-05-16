package es.ucm.fdi.genes;

public abstract class Gen {

	protected int longitud;

	public abstract Gen copiaGen();
	
	public Gen(){}
	
	public Gen(int longitud){
		this.longitud = longitud;
	}
	
	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
}
