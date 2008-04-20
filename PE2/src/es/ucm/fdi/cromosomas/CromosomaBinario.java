package es.ucm.fdi.cromosomas;

import es.ucm.fdi.genes.GenBinario;


public class CromosomaBinario extends Cromosoma{
	
	
	/*
	 * atributos heredados de cromosoma
	 * 
	 * --> se setea despues de la construccion <--
	 * protected Gen[] genes;
	 *
	 * --> se setea en la inicializacion del cromosoma despues de la construccion <--
	 * protected double[] fenotipo;
	 * protected int[] longitudGenes;
	 * protected int longitudCromosoma;
	 * 
	 * --> se crea en la construccion <--
	 * protected int numeroGenes;
	 * 
	 * --> se setean en la inicializacion del cromosoma  <--
	 * protected double aptitud;
	 * 
	 * --> se setean en el paso evaluarAptitud del algoritmo <--
	 * protected double puntuacion;
	 * protected double puntuacionAcumulada;
	 */
	
	public CromosomaBinario(int numeroGenes) {
		super(numeroGenes);
	}
	
	//constructora privada de copia
	private CromosomaBinario(CromosomaBinario original){
		//crear cromosoma
		super(original.numeroGenes);
		this.numeroGenes = original.getNumeroGenes();
		
		//crear genes, longitudes de los genes y fenotipos
		GenBinario[] copia  = new GenBinario[original.getNumeroGenes()];
		this.longitudGenes = new int[this.numeroGenes];
		this.fenotipo = new double[this.numeroGenes];
		
		for (int i = 0; i< original.getNumeroGenes(); i++){
			copia[i] = (GenBinario) original.getGenes()[i].copiaGen();
			this.longitudGenes[i]  = original.getLongitudGenes()[i];
			this.fenotipo[i] = original.getFenotipo()[i];
		}
		//setear genes		
		this.genes = copia;

		this.longitudCromosoma = original.getLongitudCromosoma();
		this.aptitud = original.getAptitud();
		this.puntuacion = original.getPuntuacion();
		this.puntuacionAcumulada = original.getPuntuacionAcumulada();
		
	}
	
	public int calcularLongitudGen(double tolerancia, double xMax, double xMin){
		return (int)Math.ceil(Math.log10( ( (xMax - xMin)/ tolerancia)+ 1 ) / Math.log10(2.0));
	}
	
	public int calcularLongitudCromosoma(){
		int longitud = 0;
		for (int i = 0; i< numeroGenes; i++){
			longitud += longitudGenes[i];
		}
		return longitud;
	}

	public void fenotipo() {
		double[] valor = new double[numeroGenes];
		
		for(int i = 0; i<this.numeroGenes;i++){
			GenBinario gen = (GenBinario) this.genes[i];
			
			double xMin = gen.getXMin();
			double xMax = gen.getXMax();
			
			double p1 = xMax - xMin;
			double p2 = binDec( (GenBinario)this.genes[i] , this.longitudGenes[i] );
			double d1 = Math.pow(2,this.longitudGenes[i]) - 1;
			
			valor[i] = xMin + ( p1 * p2 / d1);
		}
		this.fenotipo = valor;
	}

	private double binDec(GenBinario gen, int longitud) {
		double resultado = 0.0;
		
		for(int i=0;i<gen.getLongitud();i++){
			if (gen.getGen()[i]) resultado += Math.pow(2, gen.getLongitud()-i-1);
		}
		return resultado;
	}

	public Cromosoma copiarCromosoma() {
		return new CromosomaBinario(this);
	}

	public int getNumeroGenes() {
		return numeroGenes;
	}

	public void setNumeroGenes(int numeroGenes) {
		this.numeroGenes = numeroGenes;
	}

	public boolean[] desglosarCromosoma() {
		boolean[] desglose = new boolean[this.longitudCromosoma];
		int contador = 0;
		for (int i = 0; i< this.numeroGenes;i++){
			GenBinario genI = (GenBinario) this.genes[i];
			for (int j = 0; j< genI.getLongitud(); j++){
				desglose[contador] = genI.getGen()[j];
				contador++;
			}
		}
		return desglose;
	}
	
	public GenBinario[] compactarCromosoma(boolean[] desglose){
		
		GenBinario[] compactado = new GenBinario[this.numeroGenes];
		int contador = 0;
		
		for (int i = 0; i< this.numeroGenes;i++){
			//gel del cromosoma padre, del que se copiara la configuracion.
			GenBinario genPadreI = (GenBinario) this.getGenes()[i];
			GenBinario genHijoI = new GenBinario(genPadreI.getLongitud(),genPadreI.getXMax(),genPadreI.getXMin());
			
			int limiteInferior = contador;
			contador += genHijoI.getLongitud();
			boolean[] datosGen = new boolean[genHijoI.getLongitud()];
			
			int contadorCompactado = 0;
			for (int j = limiteInferior; j< contador; j++){
				datosGen[contadorCompactado] = desglose[j];
				contadorCompactado++;
			}
			
			genHijoI.setGen(datosGen);
			compactado[i] = genHijoI;
		}
		return compactado;
	}

	public String toString() {
		String cadenaCromosoma = "[ ";
		for (int i = 0; i< this.numeroGenes; i++){
			GenBinario genI = (GenBinario) this.genes[i];
			cadenaCromosoma += "[ ";
			for (int j = 0; j<genI.getLongitud(); j++){
				cadenaCromosoma+= genI.getGen()[j]+" ";
			}
			cadenaCromosoma += "] ";
		}
		cadenaCromosoma += "] ";
		return cadenaCromosoma;
	}

}