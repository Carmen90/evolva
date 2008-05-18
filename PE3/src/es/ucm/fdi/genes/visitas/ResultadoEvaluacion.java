package es.ucm.fdi.genes.visitas;

import es.ucm.fdi.utils.TableroComida;

public class ResultadoEvaluacion implements ResultadosVisitas {

	public static final int ESTE = 0;
	public static final int SUR = 1;
	public static final int OESTE = 2;
	public static final int NORTE = 3;
	
	//datos de la posicion actual de la hormiga en el tablero.
	private int filaActual;
	private int columnaActual;
	private int orientacion;
	
	//aptitud acumulada hasta esta posicion actual
	private int numBocadosComidos;
	private boolean[][] bocadosComidos;  
	
	private static ResultadoEvaluacion INSTANCE = null;
	
	public static ResultadoEvaluacion getInstance(){
		if (INSTANCE == null){
			INSTANCE = new ResultadoEvaluacion();
		}
		return INSTANCE;
	}
	
	private ResultadoEvaluacion (){
		inicializar();
	}
	
	public void inicializar(){
		this.filaActual = 0;
		this.columnaActual = 0;
		this.orientacion = ESTE;
		this.numBocadosComidos = 0;
		//por defecto se inicializan todos los bocados como no comidos (false)
		this.bocadosComidos = new boolean[TableroComida.DIMENSION_X][TableroComida.DIMENSION_Y];
		comer();
	}
	
	public void giraDerecha(){
		this.orientacion = (this.orientacion + 1) % 4;
	}
	
	public void giraIzquierda(){
		this.orientacion = (this.orientacion - 1 + 4) % 4;
	}
	
	public void avanza(){
		switch(orientacion){
		case NORTE: this.filaActual = (this.filaActual - 1 + TableroComida.DIMENSION_Y) % TableroComida.DIMENSION_Y;
					break;
		case SUR: this.filaActual = (this.filaActual + 1) % TableroComida.DIMENSION_Y;
					break;
		case ESTE: this.columnaActual = (this.columnaActual + 1) % TableroComida.DIMENSION_X;
					break;
		case OESTE: this.columnaActual = (this.columnaActual - 1 + TableroComida.DIMENSION_X) % TableroComida.DIMENSION_X;
					break;
		}
	}
	
	public boolean hayComidaDelante(){
		//miramos si hay comida delante
		int columnaSiguiente = this.columnaActual;
		int filaSiguiente = this.filaActual;
		
		switch(orientacion){
		case NORTE: filaSiguiente = (this.filaActual - 1 + TableroComida.DIMENSION_Y) % TableroComida.DIMENSION_Y;
					break;
		case SUR: filaSiguiente = (this.filaActual + 1) % TableroComida.DIMENSION_Y;
					break;
		case ESTE: columnaSiguiente = (this.columnaActual + 1) % TableroComida.DIMENSION_X;
					break;
		case OESTE: columnaSiguiente = (this.columnaActual - 1 + TableroComida.DIMENSION_X) % TableroComida.DIMENSION_X;
					break;
		}
		
		if (TableroComida.COMIDA[filaSiguiente][columnaSiguiente] == 1) return true;
		return false;
	}
	
	public void comer(){
		//si hay comida en esta casilla y no nos la habiamos comido ya, nos la comemos
		if (TableroComida.COMIDA[this.filaActual][this.columnaActual] == 1 && 
			!this.bocadosComidos[this.filaActual][this.columnaActual]){
			
			this.numBocadosComidos++;
			this.bocadosComidos[this.filaActual][this.columnaActual] = true;
		}
			
	}

	public int getFilaActual() {
		return filaActual;
	}

	public int getColumnaActual() {
		return columnaActual;
	}

	public int getOrientacion() {
		return orientacion;
	}

	public int getNumBocadosComidos() {
		return numBocadosComidos;
	}
	
}
