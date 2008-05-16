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
	private int bocadosComidos;
	
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
		this.bocadosComidos = 0;
		if (hayComida()) comer();
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
	
	public boolean hayComida(){
		if (TableroComida.COMIDA[this.filaActual][this.columnaActual] == 1) return true;
		return false;
	}
	
	public void comer(){
		this.bocadosComidos++;
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

	public int getBocadosComidos() {
		return bocadosComidos;
	}
	
}
