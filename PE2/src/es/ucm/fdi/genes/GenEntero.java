package es.ucm.fdi.genes;

public class GenEntero extends Gen{
	//array de bases del gen, en este caso, cada base es un entero
	private int[] gen;
	
	public GenEntero (int longitud){
		super(longitud, Double.MAX_VALUE, Double.MIN_VALUE);
		this.gen = new int[longitud];
	}
	
	public GenEntero (int longitud, double xMax, double xMin){
		super(longitud,xMax,xMin);
		this.gen = new int[longitud];
	}
		
	public int[] getGen() {
		return this.gen;
	}
	public void setGen(int[] gen) {
		this.gen = gen;
		this.longitud = this.gen.length;
	}
	
	public Gen copiaGen() {
		int[] copiaGenes = new int[longitud];
		
		for (int i = 0; i< longitud; i++){
			copiaGenes[i] = this.gen[i];
		}
		
		GenEntero copia = new GenEntero(this.longitud);
		copia.setGen(copiaGenes);
		
		return copia;
	}
}
