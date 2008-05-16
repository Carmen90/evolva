package es.ucm.fdi.genes.visitas;

import java.util.ArrayList;

import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;

public interface VisitanteGenArboreo {
	 //visita un elemento de tipo funcion y obtiene su fenotipo
	  public ResultadosVisitas visitarFuncion(Funcion f);
	  //visita un elemento de tipo terminal y obtiene su fenotipo
	  public ResultadosVisitas visitarTerminal(Terminal t);
}
