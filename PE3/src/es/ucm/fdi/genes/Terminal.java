package es.ucm.fdi.genes;

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
	
	@Override
	public Gen copiaGen() {
		Gen copia = new Terminal(this);
		return copia;
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

	public terminales getValor() {
		return valor;
	}
	public void setValor(terminales valor) {
		this.valor = valor;
	}
	@Override
	public void setProfundidad(int profundidad) {
		// TODO Auto-generated method stub
		
	}

}
