package es.ucm.fdi.genes.visitas;

import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Terminal;

public interface VisitanteGenArboreo {
	 //visita un elemento de tipo funcion y obtiene su fenotipo
	  public ResultadosVisitas visitarFuncion(Funcion f);
	  //visita un elemento de tipo terminal y obtiene su fenotipo
	  public ResultadosVisitas visitarTerminal(Terminal t);
}
