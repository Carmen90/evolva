package es.ucm.fdi.evaluadores;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;
import es.ucm.fdi.genes.Funcion.funciones;
import es.ucm.fdi.genes.visitas.ResultadoEvaluacion;
import es.ucm.fdi.genes.visitas.ResultadosVisitas;
import es.ucm.fdi.genes.visitas.VisitanteGenArboreo;
import es.ucm.fdi.utils.TableroComida;

public class EvaluadorHormigas implements Evaluador, VisitanteGenArboreo {

	public static int MAX_PASOS = 400;

	private int pasosConsumidos;

	public boolean esMejorAptitud(double aptitud, double aptitudMejor) {
		//Es un problema de maximizacion, luego una aptitud mayor es mejor
		return aptitud > aptitudMejor;
	}

	public double evaluaAptitud(Cromosoma individuo) {
		this.pasosConsumidos = 0;
		
		ResultadoEvaluacion.getInstance().inicializar();
		GenArboreo gen = (GenArboreo) individuo.getGenes()[0];
		
		do{
			gen.aceptarVisitanteEvaluacion(this);
		//con esta condicion comprobamos no evaluar otro arbol, si ya hemos llegado al
		//maximo de pasos consumidos o a la solucion.
		}while( this.pasosConsumidos < MAX_PASOS && 
				ResultadoEvaluacion.getInstance().getBocadosComidos() < TableroComida.NUM_BOCADITOS );

		return ResultadoEvaluacion.getInstance().getBocadosComidos();
	}

	@Override
	public Cromosoma generarCromosomaAleatorio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cromosoma generarCromosomaFijo(int[] padreFijo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] transformarAptitudesAMaximizacion(
			double[] aptitudesPositivas) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultadosVisitas visitarFuncion(Funcion f) {
		ResultadoEvaluacion resultado = ResultadoEvaluacion.getInstance();

		//con esta condicion controlamos que nos quedemos a mitad de la evaluacion de un arbol
		//si hemos llegado a la solucion o al numero maximo de pasos posibles
		if (this.pasosConsumidos < MAX_PASOS && resultado.getBocadosComidos() < TableroComida.NUM_BOCADITOS){
			
			if (f.getValor() == funciones.SIC){
				if (resultado.hayComida()) 
					resultado = (ResultadoEvaluacion) f.getArgumentos()[0].aceptarVisitanteEvaluacion(this);
				else resultado = (ResultadoEvaluacion) f.getArgumentos()[1].aceptarVisitanteEvaluacion(this);

			}else{

				for(int i = 0; i< f.getLongitud(); i++){
					resultado = (ResultadoEvaluacion) f.getArgumentos()[i].aceptarVisitanteEvaluacion(this);
				}
			}
		}
		return resultado;

	}

	public ResultadosVisitas visitarTerminal(Terminal t) {
		ResultadoEvaluacion resultado = ResultadoEvaluacion.getInstance();

		if (t.getValor() == terminales.AVANZA) resultado.avanza();
		else if (t.getValor() == terminales.DERECHA) resultado.giraDerecha();
		else if (t.getValor() == terminales.IZQUIERDA) resultado.giraIzquierda();

		if (resultado.hayComida()) resultado.comer();

		//consumimos un paso
		this.pasosConsumidos++;

		return resultado;

	}

}
