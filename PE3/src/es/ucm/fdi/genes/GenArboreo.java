package es.ucm.fdi.genes;

import es.ucm.fdi.genes.visitas.ResultadosVisitas;
import es.ucm.fdi.genes.visitas.VisitanteGenArboreo;


public abstract class GenArboreo extends Gen{
	
	//heredado de Gen
	//protected int longitud. Se utilizara para definir la aridad de las funciones.
	protected int profundidad;
	public abstract void setProfundidad(int profundidad);
	public abstract int calcularNumeroNodos();
	
	public abstract void Agregar(GenArboreo c);
	public abstract void Agregar(GenArboreo c, int indice);
    public abstract GenArboreo Remover(int indice);
        
    public abstract ResultadosVisitas aceptarVisitanteEvaluacion (VisitanteGenArboreo v);
    
    public abstract String toString();
    
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
