package es.ucm.fdi.genes;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.VisitanteGenArboreo;

public class Terminal extends GenArboreo {

	public static enum terminales{AVANZA,DERECHA,IZQUIERDA};
	
	private terminales valor;
	
	public Terminal (terminales valor){
		super();
		this.valor = valor;
	}
	public Terminal(terminales valor, int profundidad){
		super(profundidad);
		this.valor = valor;
	}
	
	private Terminal (Terminal terminal){
		this.valor = terminal.valor;
		this.longitud = terminal.longitud;
		this.profundidad = terminal.profundidad;
	}

	public Gen copiaGen() {
		Gen copia = new Terminal(this);
		return copia;
	}
	
	//como estamos ante una hoja del arbol, no tenemos que implementar "Agregar" ni "Remover"
	public void Agregar(GenArboreo c) {}
	public void Agregar(GenArboreo c, int indice) {}
	public GenArboreo Remover( int indice) { return null;}
	
	public  String toString() {
		return valor.toString();
	}

	public terminales getValor() {
		return valor;
	}
	public void setValor(terminales valor) {
		this.valor = valor;
	}
	
	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	public int calcularNumeroNodos() {
		return 0;
	}
	
	public ArrayList aceptarVisitanteFenotipo(VisitanteGenArboreo v) {
		return v.visitarTerminal(this);
	}

}
