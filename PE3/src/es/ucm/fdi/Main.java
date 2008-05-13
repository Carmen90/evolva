package es.ucm.fdi;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.gui.Gui;

public class Main {

	public static void main(String[] args) {
		Controlador controlador = new Controlador();
		Gui gui = new Gui();
		gui.setControlador(controlador);
	}

}
