package es.ucm.fdi.cromosomas;

import java.util.ArrayList;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Funcion;

public interface VisitanteGenArboreo {
	 //visita un elemento de tipo funcion y obtiene su fenotipo
	  public ArrayList<Terminal.terminales> visitarFuncion(Funcion gen);
	  //visita un elemento de tipo terminal y obtiene su fenotipo
	  public ArrayList<Terminal.terminales> visitarTerminal(Terminal c);
}
