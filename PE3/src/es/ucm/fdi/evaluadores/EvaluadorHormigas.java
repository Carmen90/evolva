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
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.MyRandom;
import es.ucm.fdi.utils.TableroComida;

public class EvaluadorHormigas implements Evaluador, VisitanteGenArboreo {

	//public static final int MAX_PASOS = 400;
	//public static final int MAX_PROFUNDIDAD = 4;

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
		}while( this.pasosConsumidos < ConstantesAlgoritmos.getInstance().getMaxPasos() && 
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
	
	//la profundidad pasada por parametro se refiere a la profundidad inicial
	public static GenArboreo genArboreoAleatorio(int profundidad){
		
		GenArboreo genArboreo = null;
		//si todavia no hemos llegado a la profundidad máxima, generamos una funcion aleatoriamente
		//y para cada parametro que se le tiene que aplicar, generamos un gen aleatoriamente. 
		if (profundidad < ConstantesAlgoritmos.getInstance().getMaxProfundidad()){
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
		if (this.pasosConsumidos < ConstantesAlgoritmos.getInstance().getMaxPasos() && resultado.getNumBocadosComidos() < TableroComida.NUM_BOCADITOS){
			
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
	
	public Cromosoma generarCromosomaSolucion() {
		//crear cromosoma
		CromosomaHormigas cromosoma = new CromosomaHormigas(1);
		
		//crear genes
		GenArboreo paso1 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso2 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso3 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso4 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso5 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso6 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso7 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso8 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso9 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso10 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso11 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso12 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso13 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso14 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso15 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso16 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso17 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso18 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso19 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso20 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso21 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso22 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso23 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso24 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso25 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso26 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso27 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso28 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso29 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso30 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso31 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso32 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso33 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso34 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso35 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso36 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso37 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso38 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso39 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso40 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso41 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso42 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso43 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso44 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso45 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso46 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso47 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso48 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso49 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso50 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso51 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso52 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso53 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso54 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso55 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso56 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso57 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso58 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso59 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso60 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso61 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso62 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso63 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso64 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso65 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso66 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso67 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso68 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso69 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso70 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso71 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso72 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso73 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso74 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso75 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso76 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso77 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso78 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso79 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso80 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso81 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso82 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso83 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso84 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso85 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso86 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso87 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso88 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso89 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso90 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso91 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso92 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso93 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso94 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso95 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso96 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso97 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso98 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso99 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso100 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso101 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso102 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso103 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso104 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso105 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso106 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso107 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso108 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso109 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso110 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso111 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso112 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso113 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso114 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso115 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso116 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso117 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso118 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso119 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso120 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso121 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso122 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso123 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso124 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso125 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso126 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso127 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso128 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso129 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso130 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso131 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso132 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso133 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso134 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso135 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso136 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso137 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso138 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso139 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso140 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso141 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso142 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso143 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso144 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso145 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso146 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso147 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso148 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso149 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso150 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso151 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso152 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso153 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso154 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso155 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso156 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso157 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso158 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso159 = new Terminal(terminales.DERECHA,5);
		GenArboreo paso160 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso161 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso162 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso163 = new Terminal(terminales.AVANZA,5);
		GenArboreo paso164 = new Terminal(terminales.IZQUIERDA,5);
		GenArboreo paso165 = new Terminal(terminales.AVANZA,5);
		
		GenArboreo nivel4_1 = new Funcion(funciones.PROGN3,4);
		nivel4_1.Agregar(paso1);
		nivel4_1.Agregar(paso2);
		nivel4_1.Agregar(paso3);
		GenArboreo nivel4_2 = new Funcion(funciones.PROGN3,4);
		nivel4_2.Agregar(paso4);
		nivel4_2.Agregar(paso5);
		nivel4_2.Agregar(paso6);
		GenArboreo nivel4_3 = new Funcion(funciones.PROGN3,4);
		nivel4_3.Agregar(paso7);
		nivel4_3.Agregar(paso8);
		nivel4_3.Agregar(paso9);
		GenArboreo nivel4_4 = new Funcion(funciones.PROGN3,4);
		nivel4_4.Agregar(paso10);
		nivel4_4.Agregar(paso11);
		nivel4_4.Agregar(paso12);
		GenArboreo nivel4_5 = new Funcion(funciones.PROGN3,4);
		nivel4_5.Agregar(paso13);
		nivel4_5.Agregar(paso14);
		nivel4_5.Agregar(paso15);
		GenArboreo nivel4_6 = new Funcion(funciones.PROGN3,4);
		nivel4_6.Agregar(paso16);
		nivel4_6.Agregar(paso17);
		nivel4_6.Agregar(paso18);
		GenArboreo nivel4_7 = new Funcion(funciones.PROGN3,4);
		nivel4_7.Agregar(paso19);
		nivel4_7.Agregar(paso20);
		nivel4_7.Agregar(paso21);
		GenArboreo nivel4_8 = new Funcion(funciones.PROGN3,4);
		nivel4_8.Agregar(paso22);
		nivel4_8.Agregar(paso23);
		nivel4_8.Agregar(paso24);
		GenArboreo nivel4_9 = new Funcion(funciones.PROGN3,4);
		nivel4_9.Agregar(paso25);
		nivel4_9.Agregar(paso26);
		nivel4_9.Agregar(paso27);
		GenArboreo nivel4_10= new Funcion(funciones.PROGN3,4);
		nivel4_10.Agregar(paso28);
		nivel4_10.Agregar(paso29);
		nivel4_10.Agregar(paso30);
		GenArboreo nivel4_11 = new Funcion(funciones.PROGN3,4);
		nivel4_11.Agregar(paso31);
		nivel4_11.Agregar(paso32);
		nivel4_11.Agregar(paso33);
		GenArboreo nivel4_12 = new Funcion(funciones.PROGN3,4);
		nivel4_12.Agregar(paso34);
		nivel4_12.Agregar(paso35);
		nivel4_12.Agregar(paso36);
		GenArboreo nivel4_13 = new Funcion(funciones.PROGN3,4);
		nivel4_13.Agregar(paso37);
		nivel4_13.Agregar(paso38);
		nivel4_13.Agregar(paso39);
		GenArboreo nivel4_14 = new Funcion(funciones.PROGN3,4);
		nivel4_14.Agregar(paso40);
		nivel4_14.Agregar(paso41);
		nivel4_14.Agregar(paso42);
		GenArboreo nivel4_15 = new Funcion(funciones.PROGN3,4);
		nivel4_15.Agregar(paso43);
		nivel4_15.Agregar(paso44);
		nivel4_15.Agregar(paso45);
		GenArboreo nivel4_16 = new Funcion(funciones.PROGN3,4);
		nivel4_16.Agregar(paso46);
		nivel4_16.Agregar(paso47);
		nivel4_16.Agregar(paso48);
		GenArboreo nivel4_17 = new Funcion(funciones.PROGN3,4);
		nivel4_17.Agregar(paso49);
		nivel4_17.Agregar(paso50);
		nivel4_17.Agregar(paso51);
		GenArboreo nivel4_18 = new Funcion(funciones.PROGN2,4);
		nivel4_18.Agregar(paso52);
		nivel4_18.Agregar(paso53);
		GenArboreo nivel4_19 = new Funcion(funciones.PROGN2,4);
		nivel4_19.Agregar(paso54);
		nivel4_19.Agregar(paso55);
		
		GenArboreo nivel4_20 = new Funcion(funciones.PROGN3,4);
		nivel4_20.Agregar(paso56);
		nivel4_20.Agregar(paso57);
		nivel4_20.Agregar(paso58);
		GenArboreo nivel4_21 = new Funcion(funciones.PROGN3,4);
		nivel4_21.Agregar(paso59);
		nivel4_21.Agregar(paso60);
		nivel4_21.Agregar(paso61);
		GenArboreo nivel4_22 = new Funcion(funciones.PROGN3,4);
		nivel4_22.Agregar(paso62);
		nivel4_22.Agregar(paso63);
		nivel4_22.Agregar(paso64);
		GenArboreo nivel4_23 = new Funcion(funciones.PROGN3,4);
		nivel4_23.Agregar(paso65);
		nivel4_23.Agregar(paso66);
		nivel4_23.Agregar(paso67);
		GenArboreo nivel4_24 = new Funcion(funciones.PROGN3,4);
		nivel4_24.Agregar(paso68);
		nivel4_24.Agregar(paso69);
		nivel4_24.Agregar(paso70);
		GenArboreo nivel4_25 = new Funcion(funciones.PROGN3,4);
		nivel4_25.Agregar(paso71);
		nivel4_25.Agregar(paso72);
		nivel4_25.Agregar(paso73);
		GenArboreo nivel4_26 = new Funcion(funciones.PROGN3,4);
		nivel4_26.Agregar(paso74);
		nivel4_26.Agregar(paso75);
		nivel4_26.Agregar(paso76);
		GenArboreo nivel4_27 = new Funcion(funciones.PROGN3,4);
		nivel4_27.Agregar(paso77);
		nivel4_27.Agregar(paso78);
		nivel4_27.Agregar(paso79);
		GenArboreo nivel4_28 = new Funcion(funciones.PROGN3,4);
		nivel4_28.Agregar(paso80);
		nivel4_28.Agregar(paso81);
		nivel4_28.Agregar(paso82);
		GenArboreo nivel4_29 = new Funcion(funciones.PROGN3,4);
		nivel4_29.Agregar(paso83);
		nivel4_29.Agregar(paso84);
		nivel4_29.Agregar(paso85);
		GenArboreo nivel4_30 = new Funcion(funciones.PROGN3,4);
		nivel4_30.Agregar(paso86);
		nivel4_30.Agregar(paso87);
		nivel4_30.Agregar(paso88);
		GenArboreo nivel4_31 = new Funcion(funciones.PROGN3,4);
		nivel4_31.Agregar(paso89);
		nivel4_31.Agregar(paso90);
		nivel4_31.Agregar(paso91);
		GenArboreo nivel4_32 = new Funcion(funciones.PROGN3,4);
		nivel4_32.Agregar(paso92);
		nivel4_32.Agregar(paso93);
		nivel4_32.Agregar(paso94);
		GenArboreo nivel4_33 = new Funcion(funciones.PROGN3,4);
		nivel4_33.Agregar(paso95);
		nivel4_33.Agregar(paso96);
		nivel4_33.Agregar(paso97);
		GenArboreo nivel4_34 = new Funcion(funciones.PROGN3,4);
		nivel4_34.Agregar(paso98);
		nivel4_34.Agregar(paso99);
		nivel4_34.Agregar(paso100);
		GenArboreo nivel4_35 = new Funcion(funciones.PROGN3,4);
		nivel4_35.Agregar(paso101);
		nivel4_35.Agregar(paso102);
		nivel4_35.Agregar(paso103);
		GenArboreo nivel4_36 = new Funcion(funciones.PROGN3,4);
		nivel4_36.Agregar(paso104);
		nivel4_36.Agregar(paso105);
		nivel4_36.Agregar(paso106);
		GenArboreo nivel4_37 = new Funcion(funciones.PROGN2,4);
		nivel4_37.Agregar(paso107);
		nivel4_37.Agregar(paso108);
		GenArboreo nivel4_38 = new Funcion(funciones.PROGN2,4);
		nivel4_38.Agregar(paso109);
		nivel4_38.Agregar(paso110);
		
		GenArboreo nivel4_39 = new Funcion(funciones.PROGN3,4);
		nivel4_39.Agregar(paso111);
		nivel4_39.Agregar(paso112);
		nivel4_39.Agregar(paso113);
		GenArboreo nivel4_40 = new Funcion(funciones.PROGN3,4);
		nivel4_40.Agregar(paso114);
		nivel4_40.Agregar(paso115);
		nivel4_40.Agregar(paso116);
		GenArboreo nivel4_41 = new Funcion(funciones.PROGN3,4);
		nivel4_41.Agregar(paso117);
		nivel4_41.Agregar(paso118);
		nivel4_41.Agregar(paso119);
		GenArboreo nivel4_42 = new Funcion(funciones.PROGN3,4);
		nivel4_42.Agregar(paso120);
		nivel4_42.Agregar(paso121);
		nivel4_42.Agregar(paso122);
		GenArboreo nivel4_43 = new Funcion(funciones.PROGN3,4);
		nivel4_43.Agregar(paso123);
		nivel4_43.Agregar(paso124);
		nivel4_43.Agregar(paso125);
		GenArboreo nivel4_44 = new Funcion(funciones.PROGN3,4);
		nivel4_44.Agregar(paso126);
		nivel4_44.Agregar(paso127);
		nivel4_44.Agregar(paso128);
		GenArboreo nivel4_45 = new Funcion(funciones.PROGN3,4);
		nivel4_45.Agregar(paso129);
		nivel4_45.Agregar(paso130);
		nivel4_45.Agregar(paso131);
		GenArboreo nivel4_46 = new Funcion(funciones.PROGN3,4);
		nivel4_46.Agregar(paso132);
		nivel4_46.Agregar(paso133);
		nivel4_46.Agregar(paso134);
		GenArboreo nivel4_47 = new Funcion(funciones.PROGN3,4);
		nivel4_47.Agregar(paso135);
		nivel4_47.Agregar(paso136);
		nivel4_47.Agregar(paso137);
		GenArboreo nivel4_48 = new Funcion(funciones.PROGN3,4);
		nivel4_48.Agregar(paso138);
		nivel4_48.Agregar(paso139);
		nivel4_48.Agregar(paso140);
		GenArboreo nivel4_49 = new Funcion(funciones.PROGN3,4);
		nivel4_49.Agregar(paso141);
		nivel4_49.Agregar(paso142);
		nivel4_49.Agregar(paso143);
		GenArboreo nivel4_50 = new Funcion(funciones.PROGN3,4);
		nivel4_50.Agregar(paso144);
		nivel4_50.Agregar(paso145);
		nivel4_50.Agregar(paso146);
		GenArboreo nivel4_51 = new Funcion(funciones.PROGN3,4);
		nivel4_51.Agregar(paso147);
		nivel4_51.Agregar(paso148);
		nivel4_51.Agregar(paso149);
		GenArboreo nivel4_52 = new Funcion(funciones.PROGN3,4);
		nivel4_52.Agregar(paso150);
		nivel4_52.Agregar(paso151);
		nivel4_52.Agregar(paso152);
		GenArboreo nivel4_53 = new Funcion(funciones.PROGN3,4);
		nivel4_53.Agregar(paso153);
		nivel4_53.Agregar(paso154);
		nivel4_53.Agregar(paso155);
		GenArboreo nivel4_54 = new Funcion(funciones.PROGN3,4);
		nivel4_54.Agregar(paso156);
		nivel4_54.Agregar(paso157);
		nivel4_54.Agregar(paso158);
		GenArboreo nivel4_55 = new Funcion(funciones.PROGN3,4);
		nivel4_55.Agregar(paso159);
		nivel4_55.Agregar(paso160);
		nivel4_55.Agregar(paso161);
		GenArboreo nivel4_56 = new Funcion(funciones.PROGN2,4);
		nivel4_56.Agregar(paso162);
		nivel4_56.Agregar(paso163);
		GenArboreo nivel4_57 = new Funcion(funciones.PROGN2,4);
		nivel4_57.Agregar(paso164);
		nivel4_57.Agregar(paso165);

		GenArboreo nivel3_1 = new Funcion(funciones.PROGN3,3);
		nivel3_1.Agregar(nivel4_1);
		nivel3_1.Agregar(nivel4_2);
		nivel3_1.Agregar(nivel4_3);
		GenArboreo nivel3_2 = new Funcion(funciones.PROGN3,3);
		nivel3_2.Agregar(nivel4_4);
		nivel3_2.Agregar(nivel4_5);
		nivel3_2.Agregar(nivel4_6);
		GenArboreo nivel3_3 = new Funcion(funciones.PROGN3,3);
		nivel3_3.Agregar(nivel4_7);
		nivel3_3.Agregar(nivel4_8);
		nivel3_3.Agregar(nivel4_9);
		GenArboreo nivel3_4 = new Funcion(funciones.PROGN3,3);
		nivel3_4.Agregar(nivel4_10);
		nivel3_4.Agregar(nivel4_11);
		nivel3_4.Agregar(nivel4_12);
		GenArboreo nivel3_5 = new Funcion(funciones.PROGN3,3);
		nivel3_5.Agregar(nivel4_13);
		nivel3_5.Agregar(nivel4_14);
		nivel3_5.Agregar(nivel4_15);
		GenArboreo nivel3_6 = new Funcion(funciones.PROGN2,3);
		nivel3_6.Agregar(nivel4_16);
		nivel3_6.Agregar(nivel4_17);
		GenArboreo nivel3_7 = new Funcion(funciones.PROGN2,3);
		nivel3_7.Agregar(nivel4_18);
		nivel3_7.Agregar(nivel4_19);
		
		GenArboreo nivel3_8 = new Funcion(funciones.PROGN3,3);
		nivel3_8.Agregar(nivel4_20);
		nivel3_8.Agregar(nivel4_21);
		nivel3_8.Agregar(nivel4_22);
		GenArboreo nivel3_9 = new Funcion(funciones.PROGN3,3);
		nivel3_9.Agregar(nivel4_23);
		nivel3_9.Agregar(nivel4_24);
		nivel3_9.Agregar(nivel4_25);
		GenArboreo nivel3_10 = new Funcion(funciones.PROGN3,3);
		nivel3_10.Agregar(nivel4_26);
		nivel3_10.Agregar(nivel4_27);
		nivel3_10.Agregar(nivel4_28);
		GenArboreo nivel3_11 = new Funcion(funciones.PROGN3,3);
		nivel3_11.Agregar(nivel4_29);
		nivel3_11.Agregar(nivel4_30);
		nivel3_11.Agregar(nivel4_31);
		GenArboreo nivel3_12 = new Funcion(funciones.PROGN3,3);
		nivel3_12.Agregar(nivel4_32);
		nivel3_12.Agregar(nivel4_33);
		nivel3_12.Agregar(nivel4_34);
		GenArboreo nivel3_13 = new Funcion(funciones.PROGN2,3);
		nivel3_13.Agregar(nivel4_35);
		nivel3_13.Agregar(nivel4_36);
		GenArboreo nivel3_14 = new Funcion(funciones.PROGN2,3);
		nivel3_14.Agregar(nivel4_37);
		nivel3_14.Agregar(nivel4_38);
		
		GenArboreo nivel3_15 = new Funcion(funciones.PROGN3,3);
		nivel3_15.Agregar(nivel4_39);
		nivel3_15.Agregar(nivel4_40);
		nivel3_15.Agregar(nivel4_41);
		GenArboreo nivel3_16 = new Funcion(funciones.PROGN3,3);
		nivel3_16.Agregar(nivel4_42);
		nivel3_16.Agregar(nivel4_43);
		nivel3_16.Agregar(nivel4_44);
		GenArboreo nivel3_17 = new Funcion(funciones.PROGN3,3);
		nivel3_17.Agregar(nivel4_45);
		nivel3_17.Agregar(nivel4_46);
		nivel3_17.Agregar(nivel4_47);
		GenArboreo nivel3_18 = new Funcion(funciones.PROGN3,3);
		nivel3_18.Agregar(nivel4_48);
		nivel3_18.Agregar(nivel4_49);
		nivel3_18.Agregar(nivel4_50);
		GenArboreo nivel3_19 = new Funcion(funciones.PROGN3,3);
		nivel3_19.Agregar(nivel4_51);
		nivel3_19.Agregar(nivel4_52);
		nivel3_19.Agregar(nivel4_53);
		GenArboreo nivel3_20 = new Funcion(funciones.PROGN2,3);
		nivel3_20.Agregar(nivel4_54);
		nivel3_20.Agregar(nivel4_55);
		GenArboreo nivel3_21 = new Funcion(funciones.PROGN2,3);
		nivel3_21.Agregar(nivel4_56);
		nivel3_21.Agregar(nivel4_57);
		
		GenArboreo nivel2_1 = new Funcion(funciones.PROGN3,2);
		nivel2_1.Agregar(nivel3_1);
		nivel2_1.Agregar(nivel3_2);
		nivel2_1.Agregar(nivel3_3);
		GenArboreo nivel2_2 = new Funcion(funciones.PROGN2,2);
		nivel2_2.Agregar(nivel3_4);
		nivel2_2.Agregar(nivel3_5);
		GenArboreo nivel2_3 = new Funcion(funciones.PROGN2,2);
		nivel2_3.Agregar(nivel3_6);
		nivel2_3.Agregar(nivel3_7);

		GenArboreo nivel2_4 = new Funcion(funciones.PROGN3,2);
		nivel2_4.Agregar(nivel3_8);
		nivel2_4.Agregar(nivel3_9);
		nivel2_4.Agregar(nivel3_10);
		GenArboreo nivel2_5 = new Funcion(funciones.PROGN2,2);
		nivel2_5.Agregar(nivel3_11);
		nivel2_5.Agregar(nivel3_12);
		GenArboreo nivel2_6 = new Funcion(funciones.PROGN2,2);
		nivel2_6.Agregar(nivel3_13);
		nivel2_6.Agregar(nivel3_14);
		
		GenArboreo nivel2_7 = new Funcion(funciones.PROGN3,2);
		nivel2_7.Agregar(nivel3_15);
		nivel2_7.Agregar(nivel3_16);
		nivel2_7.Agregar(nivel3_17);
		GenArboreo nivel2_8 = new Funcion(funciones.PROGN2,2);
		nivel2_8.Agregar(nivel3_18);
		nivel2_8.Agregar(nivel3_19);
		GenArboreo nivel2_9 = new Funcion(funciones.PROGN2,2);
		nivel2_9.Agregar(nivel3_20);
		nivel2_9.Agregar(nivel3_21);

		GenArboreo nivel1_1 = new Funcion(funciones.PROGN3,1);
		nivel1_1.Agregar(nivel2_1);
		nivel1_1.Agregar(nivel2_2);
		nivel1_1.Agregar(nivel2_3);
		GenArboreo nivel1_2 = new Funcion(funciones.PROGN3,1);
		nivel1_2.Agregar(nivel2_4);
		nivel1_2.Agregar(nivel2_5);
		nivel1_2.Agregar(nivel2_6);
		GenArboreo nivel1_3 = new Funcion(funciones.PROGN3,1);
		nivel1_3.Agregar(nivel2_7);
		nivel1_3.Agregar(nivel2_8);
		nivel1_3.Agregar(nivel2_9);
		
		GenArboreo genRaiz = new Funcion(funciones.PROGN3,0);
		genRaiz.Agregar(nivel1_1);
		genRaiz.Agregar(nivel1_2);
		genRaiz.Agregar(nivel1_3);
		
		GenArboreo[] genes = new GenArboreo[cromosoma.getNumeroGenes()];
		genes[0] = genRaiz;
		
		//setear genes (al setear los genes automaticamente se inicializa el cromosoma)
		cromosoma.setGenes(genes);
		
		return cromosoma;
	}
}
