package es.ucm.fdi.genes;

public class GenBinario extends Gen{
	
	//array de bases del gen, en este caso, cada base es un booleano
	private boolean[] gen;
		
	public GenBinario (int longitud, double xMax, double xMin){
		super(longitud,xMax,xMin);
		this.gen = new boolean[longitud];
	}
	
	public boolean[] getGen() {
		return this.gen;
	}
	public void setGen(boolean[] gen) {
		this.gen = gen;
		this.longitud = this.gen.length;
	}
	
	public Gen copiaGen() {
		boolean[] copiaGenes = new boolean[longitud];
		
		for (int i = 0; i< longitud; i++){
			copiaGenes[i] = this.gen[i];
		}
		
		GenBinario copia = new GenBinario(this.longitud,this.xMax,this.xMin);
		copia.setGen(copiaGenes);
		
		return copia;
	}
		
}
