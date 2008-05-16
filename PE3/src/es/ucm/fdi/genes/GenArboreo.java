package es.ucm.fdi.genes;

public abstract class GenArboreo extends Gen{
	
	//heredado de Gen
	//protected int longitud. Se utilizara para definir la aridad de las funciones.
	protected int profundidad;
	public abstract void setProfundidad(int profundidad);
	
	public abstract void Agregar(GenArboreo c);
    public abstract void Remover(GenArboreo c);
    public abstract void Mostrar(int profundidad);
    	
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
