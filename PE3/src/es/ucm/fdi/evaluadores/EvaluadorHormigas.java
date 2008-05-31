package es.ucm.fdi.evaluadores;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.cromosomas.CromosomaHormigas;
import es.ucm.fdi.genes.Funcion;
import es.ucm.fdi.genes.Gen;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.genes.Terminal;
import es.ucm.fdi.genes.Terminal.terminales;
import es.ucm.fdi.genes.Funcion.funciones;
import es.ucm.fdi.genes.visitas.ResultadosVisitas;
import es.ucm.fdi.genes.visitas.VisitanteGenArboreo;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.utils.TableroComida;

public class EvaluadorHormigas implements Evaluador, VisitanteGenArboreo {

	public static final int MAX_PASOS = 400;
	public static final int MAX_PROFUNDIDAD = 4;

	private int pasosConsumidos;
	
	private boolean mostrarPasos;
	private boolean[][] pasos;
	
	public EvaluadorHormigas(){
		this.mostrarPasos = false;
	}
	
	public EvaluadorHormigas(boolean mostrarPasos){
		this.mostrarPasos = mostrarPasos; 
	}

	public boolean esMejorAptitud(double aptitud, double aptitudMejor) {
		//Es un problema de maximizacion, luego una aptitud mayor es mejor
		return aptitud > aptitudMejor;
	}

	public double evaluaAptitud(Cromosoma individuo) {
		this.pasosConsumidos = 0;
		if (mostrarPasos){
			this.pasos = new boolean[TableroComida.DIMENSION_X][TableroComida.DIMENSION_Y];
			this.pasos[0][0] = true;
			
		}
		ResultadoEvaluacion.getInstance().inicializar();
		GenArboreo gen = (GenArboreo) individuo.getGenes()[0];
		
		do{
			gen.aceptarVisitanteEvaluacion(this);
		//con esta condicion comprobamos no evaluar otro arbol, si ya hemos llegado al
		//maximo de pasos consumidos o a la solucion.
		}while( this.pasosConsumidos < MAX_PASOS && 
				ResultadoEvaluacion.getInstance().getNumBocadosComidos() < TableroComida.NUM_BOCADITOS );

		return ResultadoEvaluacion.getInstance().getNumBocadosComidos();
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
		//No hace falta, ya que en la generacion ya se va seteando en la construccion la profundidad...
		//gen.setProfundidad(profundidadInicial);
		return gen;
	}
	
	public static GenArboreo genArboreoAleatorio(int profundidad){
		
		GenArboreo genArboreo = null;
		//si todavia no hemos llegado a la profundidad m�xima, generamos una funcion aleatoriamente
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

	public Cromosoma generarCromosomaFijo() {
		//crear cromosoma
		CromosomaHormigas cromosoma = new CromosomaHormigas(1);
		
		//crear genes
		GenArboreo genRaiz = new Funcion(funciones.PROGN2,0);
		
		GenArboreo nivel1_1 = new Funcion(funciones.SIC,1);
		GenArboreo nivel1_2 = new Funcion(funciones.PROGN3,1);
		
		GenArboreo nivel2_1 = new Funcion(funciones.SIC,2);
		GenArboreo nivel2_2 = new Funcion(funciones.PROGN2,2);
		GenArboreo nivel2_3 = new Funcion(funciones.SIC,2);
		GenArboreo nivel2_4 = new Funcion(funciones.PROGN2,2);
		GenArboreo nivel2_5 = new Funcion(funciones.PROGN3,2);
		
		GenArboreo nivel3_1 = new Terminal(terminales.AVANZA,3);
		GenArboreo nivel3_2 = new Terminal(terminales.IZQUIERDA,3);
		GenArboreo nivel3_3 = new Terminal(terminales.AVANZA,3);
		GenArboreo nivel3_4 = new Terminal(terminales.DERECHA,3);
		GenArboreo nivel3_5 = new Terminal(terminales.AVANZA,3);
		GenArboreo nivel3_6 = new Terminal(terminales.IZQUIERDA,3);
		GenArboreo nivel3_7 = new Terminal(terminales.DERECHA,3);
		GenArboreo nivel3_8 = new Terminal(terminales.DERECHA,3);
		GenArboreo nivel3_9 = new Terminal(terminales.IZQUIERDA,3);
		GenArboreo nivel3_10 = new Terminal(terminales.AVANZA,3);
		GenArboreo nivel3_11 = new Terminal(terminales.IZQUIERDA,3);
		
		genRaiz.Agregar(nivel1_1);
		genRaiz.Agregar(nivel1_2);
		
		nivel1_1.Agregar(nivel2_1);
		nivel1_1.Agregar(nivel2_2);
		
		nivel1_2.Agregar(nivel2_3);
		nivel1_2.Agregar(nivel2_4);
		nivel1_2.Agregar(nivel2_5);
		
		nivel2_1.Agregar(nivel3_1);
		nivel2_1.Agregar(nivel3_2);
		
		nivel2_2.Agregar(nivel3_3);
		nivel2_2.Agregar(nivel3_4);
		
		nivel2_3.Agregar(nivel3_5);
		nivel2_3.Agregar(nivel3_6);
		
		nivel2_4.Agregar(nivel3_7);
		nivel2_4.Agregar(nivel3_8);
		
		nivel2_5.Agregar(nivel3_9);
		nivel2_5.Agregar(nivel3_10);
		nivel2_5.Agregar(nivel3_11);
		
		GenArboreo[] genes = new GenArboreo[cromosoma.getNumeroGenes()];
		genes[0] = genRaiz;
		
		//setear genes (al setear los genes automaticamente se inicializa el cromosoma)
		cromosoma.setGenes(genes);
		
		return cromosoma;
	}

	public double[] transformarAptitudesAMaximizacion(double[] aptitudesPositivas) {
		//el problema ya es de maximizacion!
		return aptitudesPositivas;
	}

	public ResultadosVisitas visitarFuncion(Funcion f) {
		ResultadoEvaluacion resultado = ResultadoEvaluacion.getInstance();

		//con esta condicion controlamos que nos quedemos a mitad de la evaluacion de un arbol
		//si hemos llegado a la solucion o al numero maximo de pasos posibles
		if (this.pasosConsumidos < MAX_PASOS && resultado.getNumBocadosComidos() < TableroComida.NUM_BOCADITOS){
			
			if (f.getValor() == funciones.SIC){
				if (resultado.hayComidaDelante()) 
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

		terminales valor = t.getValor();
		if (valor == terminales.AVANZA){
			resultado.avanza();
			//si en la nueva casilla hay comida entonces la comemos
			resultado.comer();
			if (this.mostrarPasos){
				this.pasos[resultado.getFilaActual()][resultado.getColumnaActual()] = true;
			}
		}
		//si giramos, permanecemos en la misma casilla, con lo cual ya comimos el bocado si lo habia.
		else if (valor == terminales.DERECHA) resultado.giraDerecha();
		else if (valor == terminales.IZQUIERDA) resultado.giraIzquierda();

		//consumimos un paso
		this.pasosConsumidos++;

		return resultado;

	}

	public boolean[][] getPasos() {
		return pasos;
	}
}
