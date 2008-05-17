package es.ucm.fdi.gui;

import javax.swing.JButton;

public class Celda extends JButton {
	private int fila;
	private int columna;
	
	public Celda(int fila, int columna){
		super();
		this.fila = fila;
		this.columna = columna;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}
}
