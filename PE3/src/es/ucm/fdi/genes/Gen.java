package es.ucm.fdi.genes;

public abstract class Gen {
	protected double xMax;
	protected double xMin;
	protected int longitud;
	
	public abstract Gen copiaGen();
	
	public Gen (){}	
		
	public Gen (int longitud, double xMax, double xMin){
		this.longitud = longitud;
		this.xMax = xMax;
		this.xMin = xMin;
	}
	
	public double getXMax() {
		return xMax;
	}

	public void setXMax(double max) {
		xMax = max;
	}

	public double getXMin() {
		return xMin;
	}

	public void setXMin(double min) {
		xMin = min;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	
}
