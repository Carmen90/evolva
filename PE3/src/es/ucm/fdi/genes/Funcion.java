package es.ucm.fdi.genes;

public class Funcion extends GenArboreo {

	public static enum funciones {SIC, PROGN2, PROGN3};
	
	private funciones valor;
	private GenArboreo[] argumentos;
	
	public Funcion (funciones valor, int profundidad){
		super(profundidad);
		this.valor = valor;
		
		int longitud = 0;
		switch (valor) {
		case SIC:
		case PROGN2: longitud = 2; break;
		case PROGN3: longitud = 3; break;
		default:
		}
		
		this.setLongitud(longitud);
		//this.argumentos = new GenArboreo[longitud];
	}
	
	@Override
	public void Agregar(GenArboreo c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Mostrar(int profundidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Remover(GenArboreo c) {
		// TODO Auto-generated method stub

	}

	@Override
	public GenArboreo copiaGen() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void setProfundidad(int profundidad) {
		// TODO Auto-generated method stub
		
	}
	
}
