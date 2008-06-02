package es.ucm.fdi;

import javax.swing.JProgressBar;

import es.ucm.fdi.algoritmos.AGeneticoHormigas;
import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.gui.Gui;

public class Main {

	public static void main(String[] args) {
		Controlador controlador = new Controlador();
		//Gui gui = new Gui();
		//gui.setControlador(controlador);
		
		
		/****************DEPURACION DEL ALGORITMO GENÉTICO CON CONSOLA******************************/
		int numGeneraciones = Controlador.GENERACIONES_DEFECTO;
		int tamPoblacion = Controlador.POBLACION_DEFECTO;
		double probCruce = Controlador.CRUCE_DEFECTO; 
		double probMutacion = Controlador.MUTACION_DEFECTO; 
		boolean eli = true; 
		double porcentajeElite = Controlador.ELITISMO_DEFECTO; 
		int tipoMutacion = AGeneticoHormigas.MUTACION_TERMINAL; 
		int tipoCruce = AGeneticoHormigas.CRUCE_ARBOREO; 
		int tipoSeleccion = AGeneticoHormigas.SELECCION_RULETA; 
		boolean contractividad = false; 
		boolean escalado = false; 
		
		controlador.ejecucionSencilla(numGeneraciones, tamPoblacion, probCruce, probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion, contractividad, escalado);
		
		System.out.println("Mejores individuos hallados: ");
		for (int i = 0; i < controlador.mejores.length; i++){
			System.out.println(controlador.mejores[i]);	
		}
		
	}

}
