package es.ucm.fdi.genes;

import java.util.ArrayList;

import es.ucm.fdi.genes.visitas.ResultadosVisitas;
import es.ucm.fdi.genes.visitas.VisitanteGenArboreo;


public class Funcion extends GenArboreo {

	public static enum funciones {SIC, PROGN2, PROGN3};
	
	private funciones valor;
	private GenArboreo[] argumentos;
	private int numAgregados;
	
	public Funcion (funciones valor, int profundidad){
		super(profundidad);
		this.valor = valor;
		this.numAgregados = 0;
		
		int longitud = 0;
		switch (valor) {
		case SIC:
		case PROGN2: longitud = 2; break;
		case PROGN3: longitud = 3; break;
		default:
		}
		
		this.setLongitud(longitud);
		this.argumentos = new GenArboreo[longitud];
	}
	
	private Funcion (Funcion funcion) {
		this.numAgregados = funcion.numAgregados;
		this.valor = funcion.valor;
		this.longitud = funcion.longitud;
		this.profundidad = funcion.profundidad;
		
		this.argumentos = new GenArboreo[this.longitud];
		for (int i = 0; i< this.longitud; i++){
			this.argumentos[i] = (GenArboreo) funcion.getArgumentos()[i].copiaGen();
		}
	}

	public void Agregar(GenArboreo c) {
		//lo añadimos si podemos añadir mas elementos
		if (this.numAgregados != this.longitud){
			this.argumentos[numAgregados] = c;
			//seteamos la profundidad
			c.setProfundidad(this.profundidad + 1);
			numAgregados++;
		}
		
	}
	
	//forzamos un GenArboreo en una posicion "indice", se utilizara para realizar los cruces.
	//no hace falta tocar el numero de agregados, ya que eso solo es para la inicializacion
	public void Agregar(GenArboreo c, int indice){
		this.argumentos[indice] = c;
		c.setProfundidad(this.profundidad + 1);
	}

	public String toString() {
		String cadena = this.valor.toString()+ "( ";
				
		for (int i = 0; i< this.longitud; i++){
			if (i == longitud -1) cadena += argumentos[i].toString();
			else cadena += argumentos[i].toString() + ", ";
		}
		cadena += " )" ;
		
		return cadena;

	}

	public GenArboreo Remover(int indice) {
		//devolveremos null en caso de que no se haya podido elminar el hijo.
		GenArboreo aux = null;
		//solo actuamos si el indice es factible dentro de la longitud de la funcion
		if (indice < this.longitud){
			aux = (GenArboreo) this.argumentos[indice].copiaGen();
			this.argumentos[indice] = null;
		}
		return aux;
	}

	public Gen copiaGen() {
		Gen copia = new Funcion (this);
		return copia;
	}
	
	//GETTERS Y SETTERS
	
	public funciones getValor() {
		return valor;
	}

	public void setValor(funciones valor) {
		this.valor = valor;
	}

	public GenArboreo[] getArgumentos() {
		return argumentos;
	}

	public boolean setArgumentos(GenArboreo[] argumentos){
		if (argumentos.length == this.longitud){
			this.argumentos = argumentos;
			return true;
		}
		return false;
	}

	public void setProfundidad(int profundidad) {
		//seteamos la profundidad a la que se encuentra la funcion en el arbol
		this.profundidad = profundidad;
		int profundidadHijos = profundidad++;
		//y propagamos a sus argumentos, ya sean terminales u otras funciones, la profundidad + 1
		for (int i = 0; i< this.longitud; i++){
			if (this.argumentos[i] != null)
				this.argumentos[i].setProfundidad(profundidadHijos);
		}
		
	}

	public int calcularNumeroNodos() {
		int numeroNodos = 0;
		for (int i = 0; i< this.longitud; i++){
			numeroNodos += this.argumentos[i].calcularNumeroNodos();
		}
		return numeroNodos + this.longitud;
	}

	public ResultadosVisitas aceptarVisitanteEvaluacion(VisitanteGenArboreo v) {
		return v.visitarFuncion(this);
	}
	
}
