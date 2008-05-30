package es.ucm.fdi.algoritmos;

import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;

public class AGeneticoHormigas extends AGenetico {

	public AGeneticoHormigas() {
		// TODO Auto-generated constructor stub
	}

	public AGeneticoHormigas(int tamañoPoblacion, int numMaxGeneraciones,
			double probabilidadCruce, double probabilidadMutacion,
			double tolerancia) {
		super(tamañoPoblacion, numMaxGeneraciones, probabilidadCruce,
				probabilidadMutacion, tolerancia);
		// TODO Auto-generated constructor stub
	}

	//GENERADO AUTOMACICAMENTE PARA CONTENTAR AL CONTROLADOR (PROVISIONAL)
	public AGeneticoHormigas(int tamPoblacion, int numGeneraciones,
			double probCruce, double probMutacion, int tipoMutacion,
			int tipoCruce, int tipoSeleccion) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void almacenarElite(double porcentajeElite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evaluarPoblacion(boolean mejora) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inicializar(Evaluador funcionEvaluacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public double mediaPoblacionInstantanea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cromosoma mejorPoblacionInstantanea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutacion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void recuperarElite() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reproduccion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seleccion() {
		// TODO Auto-generated method stub

	}

}
