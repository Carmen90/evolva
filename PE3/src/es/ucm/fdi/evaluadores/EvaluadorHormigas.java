package es.ucm.fdi.evaluadores;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.GenEntero;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;
import es.ucm.fdi.genes.Funcion.funciones;
import es.ucm.fdi.genes.visitas.ResultadoEvaluacion;
import es.ucm.fdi.genes.visitas.ResultadosVisitas;
import es.ucm.fdi.genes.visitas.VisitanteGenArboreo;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.utils.TableroComida;

public class EvaluadorHormigas implements Evaluador, VisitanteGenArboreo {

	public static final int MAX_PASOS = 400;
	public static final int MAX_PROFUNDIDAD = 4;

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

	public Cromosoma generarCromosomaAleatorio() {
		//crear cromosoma
		CromosomaHormigas cromosoma = new CromosomaHormigas(1);
		
		//crear genes
		GenArboreo gen = (GenArboreo) this.generarGenAleatorio();
		
		GenArboreo[] genes = new GenArboreo[cromosoma.getNumeroGenes()];
		genes[0] = gen;
		
		//setear genes (al setear los genes automaticamente se inicializa el cromosoma)
		cromosoma.setGenes(genes);
		
		return cromosoma;
	}
	
	public Gen generarGenAleatorio() {
		int profundidadInicial = 0;
		GenArboreo gen = genArboreoAleatorio(profundidadInicial);
		//antes de devolver el genAleatorio, generamos la profundidad de todos los hijos.
		//gen.setProfundidad(profundidadInicial);
		return gen;
	}
	
	private GenArboreo genArboreoAleatorio(int profundidad){
		
		GenArboreo genArboreo = null;
		//si todavia no hemos llegado a la profundidad máxima, generamos una funcion aleatoriamente
		//y para cada parametro que se le tiene que aplicar, generamos un gen aleatoriamente. 
		if (profundidad < MAX_PROFUNDIDAD){
			int indiceFuncion = MyRandom.aleatorioEntero(0, Funcion.NUM_FUNCIONES);
			funciones valorFuncion = funciones.values()[indiceFuncion];
			int numParametros = 0;
			if (indiceFuncion < Funcion.LIMITE_2_PARAMETROS){
				numParametros = 2;
			}else if (indiceFuncion < Funcion.LIMITE_3_PARAMETROS){
				numParametros = 3;
			}
			
			genArboreo = new Funcion(valorFuncion,profundidad);
			genArboreo.setLongitud(numParametros);
			
			for (int i = 0; i< numParametros; i++){
				//llamamos recursivamente para generar los genes 
				GenArboreo c = genArboreoAleatorio(profundidad + 1);
				//agregamos el gen generado.
				genArboreo.Agregar(c);
			}
		
		//cuando ya hemos superado el limite de profundidad, generamos uno de los posibles valores de
		//terminales y lo devolvemos.
		}else{
			int indiceTerminal = MyRandom.aleatorioEntero(0, Terminal.NUM_TERMINALES);
			terminales valorTerminal = terminales.values()[indiceTerminal];
			
			genArboreo = new Terminal(valorTerminal, profundidad);
		}
		
		return genArboreo;
		
	}

	public Cromosoma generarCromosomaFijo(int[] padreFijo) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] transformarAptitudesAMaximizacion(double[] aptitudesPositivas) {
		//el problema ya es de maximizacion!
		return aptitudesPositivas;
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
