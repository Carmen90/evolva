package es.ucm.fdi.genes;

import java.util.ArrayList;

import es.ucm.fdi.cromosomas.VisitanteGenArboreo;

public abstract class GenArboreo extends Gen{
	
	//heredado de Gen
	//protected int longitud. Se utilizara para definir la aridad de las funciones.
	protected int profundidad;
	public abstract void setProfundidad(int profundidad);
	public abstract int calcularNumeroNodos();
	
	public abstract void Agregar(GenArboreo c);
	public abstract void Agregar(GenArboreo c, int indice);
    public abstract GenArboreo Remover(int indice);
        
    public abstract ArrayList aceptarVisitanteFenotipo (VisitanteGenArboreo v);
    
    //public abstract void Mostrar();
    	
	public GenArboreo (){
		super();
		this.profundidad = 0;
	}	
	public GenArboreo(int profundidad){
		//la longitud sera 0
		super();
		this.profundidad = profundidad;
	}
	public GenArboreo (int profundidad, int longitud){
		super(longitud);
		this.profundidad = profundidad;
	}

	public int getProfundidad() {
		return profundidad;
	}
	
}
