package es.ucm.fdi.cromosomas;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;

public class CromosomaHormigas extends Cromosoma implements VisitanteGenArboreo {

	public CromosomaHormigas(int numeroGenes) {
		super(numeroGenes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calcularLongitudCromosoma() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cromosoma copiarCromosoma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fenotipo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<terminales> visitarFuncion(Funcion gen) {
		ArrayList<terminales> secuenciaTerminales = new ArrayList<terminales>();

	    for(int i = 0; i<gen.getLongitud(); i++){
	    	ArrayList<terminales> secuenciaTerminalesHijoI = gen.getArgumentos()[i].aceptarVisitanteFenotipo(this);
	    	Iterator<terminales> it = secuenciaTerminalesHijoI.iterator();
	    	while(it.hasNext()){
	    		terminales terminalI = it.next();
	    		secuenciaTerminales.add(terminalI);
	    	}
	    }
	     return secuenciaTerminales;
	}

	public ArrayList<terminales> visitarTerminal(Terminal c) {
		ArrayList<terminales> secuenciaTerminales = new ArrayList<terminales>();
		
		secuenciaTerminales.add(c.getValor());
		return secuenciaTerminales;
		
	}

}
