package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import sun.net.www.content.image.jpeg;

import es.ucm.fdi.algoritmos.AGeneticoHormigas;
import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.evaluadores.EvaluadorHormigas;
import es.ucm.fdi.genes.GenArboreo;
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.TableroComida;

public class Gui extends JFrame {
	
	//FRAMES
	private JFrame frameModificacionConstantes;
	
	//PANELES
	private JPanel panelPrincipal;
	private JPanel panelMatriz;
	private JPanel panelDatos;
	private JPanel panelParametrosAG;
	private JPanel panelMejoras;
	//private JPanel panelParametrosHormiga;
	private JPanel panelPoblacion;
	private JPanel panelGeneraciones;
	private JPanel panelProbCruce;
	private JPanel panelProbMutacion;
	private JPanel panelElitismo;
	private JPanel panelProfundidad;
	private JPanel panelMaxPasos;
	private JPanel panelGenotipo_Grafica;
	private JPanel panelDatos_Matriz;
	private JScrollPane panelScroll;
	private JPanel panelDatosAux;
	private JPanel panelTipoMutacion;
	private JPanel panelSeleccion;
	private JPanel panelConstantes;
	private JPanel panelContenedorConstantes;
	private JPanel panelContrincantes;
	private JPanel panelArbol;
	private JPanel panelEjecutar_Aptitud;
	
	//ETIQUETAS
	private JLabel labelNumGeneraciones;
	private JLabel labelTamPoblacion;
	private JLabel labelProbCruce;
	private JLabel labelTipoMutacion;
	private JLabel labelProbMutacion;
	private JLabel labelProfundidad;
	private JLabel labelMaxPasos;
	private JLabel labelSeleccion;
	private JLabel labelNumContrincantes;
	private JLabel labelAptitud;
	
	//CAMPOS DE TEXTO
	private JTextField textoNumGeneraciones;
	private JTextField textoTamPoblacion;
	private JTextField textoProbCruce;
	private JTextField textoProbMutacion;
	private JTextField textoElitismo;
	private JTextField textoProfundidad;
	private JTextField textoMaxPasos;
	private JTextArea textoGenotipo;
	private JTextField textoNumContrincantes;
	
	//CHECKBOX
	private JCheckBox checkElitismo;
	private JCheckBox checkContractividad;
	private JCheckBox checkEscalado;
	
	//COMBOS
	private JComboBox comboMutacion;
	private JComboBox comboSeleccion;
	
	//BOTONES
	private JButton botonEjecutar;
	private JButton botonMostrarGrafica;
	private JButton botonConstantes;
	
	//BARRA DE MENU
	private JMenuBar barraMenu;
	private JMenu menu;
	private JMenuItem cambiarConstantes;
	private JMenuItem itemValoresDefecto;
	private JMenuItem itemSalir;
	
	//CONTROLADOR
	private Controlador controlador;
	
	private Celda[][] tablero;
		
	public Gui(){
		
		//Creamos las etiquetas
		labelTamPoblacion = new JLabel("Tamaño población");
		labelNumGeneraciones = new JLabel("Número generaciones");
		labelProbCruce = new JLabel("Probabilidad cruce");
		labelTipoMutacion = new JLabel("Tipo mutación");
		labelProbMutacion = new JLabel("Probabilidad mutación");
		labelProfundidad = new JLabel("Profundidad");
		labelMaxPasos = new JLabel("Max. pasos");
		labelSeleccion = new JLabel("Tipo selección");
		labelNumContrincantes = new JLabel("Contrincantes");
		labelAptitud = new JLabel();
		
		//Creamos los campos de texto
		textoNumGeneraciones = new JTextField();
		textoNumGeneraciones.setText(String.valueOf(Controlador.GENERACIONES_DEFECTO));
		OyenteTeclaIntro oyenteGeneraciones = new OyenteTeclaIntro();
		textoNumGeneraciones.addKeyListener(oyenteGeneraciones);
		
		textoTamPoblacion = new JTextField();
		textoTamPoblacion.setText(String.valueOf(Controlador.POBLACION_DEFECTO));
		OyenteTeclaIntro oyenteTamPoblacion = new OyenteTeclaIntro();
		textoTamPoblacion.addKeyListener(oyenteTamPoblacion);
		
		textoProbCruce = new JTextField();
		textoProbCruce.setText(String.valueOf(Controlador.CRUCE_DEFECTO));
		OyenteTeclaIntro oyenteProbCruce = new OyenteTeclaIntro();
		textoProbCruce.addKeyListener(oyenteProbCruce);
		
		textoProbMutacion = new JTextField();
		textoProbMutacion.setText(String.valueOf(Controlador.MUTACION_DEFECTO));
		OyenteTeclaIntro oyenteProbMutacion = new OyenteTeclaIntro();
		textoProbMutacion.addKeyListener(oyenteProbMutacion);
		
		textoElitismo = new JTextField();
		textoElitismo.setText(String.valueOf(Controlador.ELITISMO_DEFECTO));
		textoElitismo.setEnabled(false);   //Desactivamos el campo de texto de elitismo hasta que se marque en el checkbox
		OyenteTeclaIntro oyenteElitisimo = new OyenteTeclaIntro();
		textoElitismo.addKeyListener(oyenteElitisimo);
		
		textoProfundidad = new JTextField();
		textoProfundidad.setText(String.valueOf(ConstantesAlgoritmos.getInstance().getMaxProfundidad()));
		textoMaxPasos = new JTextField();
		textoMaxPasos.setText(String.valueOf(ConstantesAlgoritmos.getInstance().getMaxPasos()));
		textoGenotipo = new JTextArea(6,0);
		textoGenotipo.setEditable(false);
		textoGenotipo.setLineWrap(true);
		textoNumContrincantes = new JTextField();
		textoNumContrincantes.setText(String.valueOf(ConstantesAlgoritmos.getInstance().getNumContrincantes()));
		
		//Creamos los checkbox
		checkElitismo = new JCheckBox("Elitismo");
		OyenteCheckElitismo oyenteElitismo = new OyenteCheckElitismo();
		checkElitismo.addActionListener(oyenteElitismo);
		
		checkContractividad = new JCheckBox("Contractividad");
		checkEscalado = new JCheckBox("Escalado de aptitud");
		
		//Creamos los combos y añadimos los elementos
		comboMutacion = new JComboBox();
		comboMutacion.addItem("Terminal simple");
		comboMutacion.addItem("Funcional simple");
		comboMutacion.addItem("Árbol");
		
		comboSeleccion = new JComboBox();
		comboSeleccion.addItem("Ruleta");
		comboSeleccion.addItem("Torneo");
		OyenteComboSeleccion oyenteComboSelec = new OyenteComboSeleccion();
		comboSeleccion.addActionListener(oyenteComboSelec);
		
		
		//Creamos los botones
		botonEjecutar = new JButton("Ejecutar");
		OyenteBotonEjecutar ejecucion = new OyenteBotonEjecutar();
		botonEjecutar.addActionListener(ejecucion);
		
		botonMostrarGrafica = new JButton();
		botonMostrarGrafica.setIcon(new ImageIcon("./images/graficaIcon.JPG"));
		botonMostrarGrafica.setEnabled(false);
		OyenteBotonGrafica oyenteGrafica = new OyenteBotonGrafica();
		botonMostrarGrafica.addActionListener(oyenteGrafica);
		
		botonConstantes = new JButton("Aceptar");
		OyenteAceptarConstantes oyenteAceptar = new OyenteAceptarConstantes();
		botonConstantes.addActionListener(oyenteAceptar);
		

		//Creamos la barra de menus y añadimos los elementos y oyentes
		barraMenu = new JMenuBar();
		menu = new JMenu();
		cambiarConstantes = new JMenuItem("Cambiar constantes");
		OyenteModificarConstantes modificarCons = new OyenteModificarConstantes();
		cambiarConstantes.addActionListener(modificarCons);
		itemValoresDefecto = new JMenuItem("Valores por defecto");
		OyenteValoresPorDefecto valoresDefecto = new OyenteValoresPorDefecto();
		itemValoresDefecto.addActionListener(valoresDefecto);
		itemSalir = new JMenuItem("Salir");
		OyenteSalir salida = new OyenteSalir();
		itemSalir.addActionListener(salida);

		menu.setText("Archivo");
		menu.add(cambiarConstantes);
		menu.add(itemValoresDefecto);
		menu.add(itemSalir);
		barraMenu.add(menu);
		
		//Creamos el panel principal de la aplicacion
		panelPrincipal = new JPanel(new BorderLayout());
		
		//Creamos la matriz de casillas con los lugares donde hay comida
		panelMatriz = new JPanel(new GridLayout(32,32));
		this.tablero = new Celda[TableroComida.DIMENSION_X][TableroComida.DIMENSION_Y];
		for(int i=0; i<TableroComida.DIMENSION_Y; i++){
			for(int j=0; j<TableroComida.DIMENSION_X; j++){
				Celda casilla = new Celda(i,j);
				casilla.setEnabled(false);
				this.tablero[casilla.getFila()][casilla.getColumna()] = casilla;
				int contenidoCelda = TableroComida.COMIDA[i][j];
				if(contenidoCelda == 1)
					tablero[i][j].setBackground(Color.GREEN);
				else{
					//ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource("es/ucm/fdi/images/hormiga.gif"));
					//casilla.setIcon(icono);
					Color color = new Color(238,238,238);
					tablero[i][j].setBackground(color);
				}
				//Añadimos cada boton creado
				panelMatriz.add(casilla);
			}
		}

		//Creamos los paneles de las celdas
		panelPoblacion = new JPanel(new GridLayout(1,2));
		panelGeneraciones = new JPanel(new GridLayout(1,2));
		panelSeleccion = new JPanel(new GridLayout(1,2));
		panelProbCruce = new JPanel(new GridLayout(1,2));
		panelTipoMutacion = new JPanel(new GridLayout(1,2));
		panelProbMutacion = new JPanel(new GridLayout(1,2));
		panelElitismo = new JPanel(new GridLayout(1,2));
		panelProfundidad = new JPanel(new GridLayout(1,2));
		panelMaxPasos = new JPanel(new GridLayout(1,2));
		panelConstantes = new JPanel(new BorderLayout());
		panelContenedorConstantes = new JPanel(new BorderLayout());
		panelContrincantes = new JPanel(new GridLayout(1,2));
		panelArbol = new JPanel(new GridLayout(2,1));
		panelEjecutar_Aptitud = new JPanel(new GridLayout(1,2));
		
		//Añadimos los componentes de las celdas de los paneles
		panelPoblacion.add(labelTamPoblacion);
		panelPoblacion.add(textoTamPoblacion);
		
		panelGeneraciones.add(labelNumGeneraciones);
		panelGeneraciones.add(textoNumGeneraciones);
		
		panelSeleccion.add(labelSeleccion);
		panelSeleccion.add(comboSeleccion);
		
		panelProbCruce.add(labelProbCruce);
		panelProbCruce.add(textoProbCruce);
		
		panelTipoMutacion.add(labelTipoMutacion);
		panelTipoMutacion.add(comboMutacion);
		
		panelProbMutacion.add(labelProbMutacion);
		panelProbMutacion.add(textoProbMutacion);
		
		panelElitismo.add(checkElitismo);
		panelElitismo.add(textoElitismo);
		
		panelProfundidad.add(labelProfundidad);
		panelProfundidad.add(textoProfundidad);
		
		panelMaxPasos.add(labelMaxPasos);
		panelMaxPasos.add(textoMaxPasos);
		
		panelContrincantes.add(labelNumContrincantes);
		panelContrincantes.add(textoNumContrincantes);
		
		panelArbol.add(panelMaxPasos);
		panelArbol.add(panelProfundidad);
		
		//Creamos el panel de parametros basicos del AG y añadimos sus componentes
		panelParametrosAG = new JPanel(new GridLayout(6,1));
		Border lineBorder, titleBorder, emptyBorder, compoundBorder;
		emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Parámetros AG");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelParametrosAG.setBorder(compoundBorder);
		panelParametrosAG.add(panelPoblacion);
		panelParametrosAG.add(panelGeneraciones);
		panelParametrosAG.add(panelSeleccion);
		panelParametrosAG.add(panelProbCruce);
		panelParametrosAG.add(panelTipoMutacion);
		panelParametrosAG.add(panelProbMutacion);
		
		//Creamos el panel de mejoras y añadimos sus componentes
		panelMejoras = new JPanel(new GridLayout(3,1));
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Mejoras");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelMejoras.setBorder(compoundBorder);
		panelMejoras.add(panelElitismo);
		panelMejoras.add(checkContractividad);
		panelMejoras.add(checkEscalado);
		
		//Añadimos los componentes al contenedor de la ventana que cambia el valor de las constantes en los algoritmos
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Selección por Torneo");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelContrincantes.setBorder(compoundBorder);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Árbol");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelArbol.setBorder(compoundBorder);
		panelContenedorConstantes.add(panelContrincantes,BorderLayout.NORTH);
		panelContenedorConstantes.add(panelArbol,BorderLayout.CENTER);
		panelConstantes.add(panelContenedorConstantes,BorderLayout.CENTER);
		panelConstantes.add(botonConstantes, BorderLayout.SOUTH);
		
		//Creamos el panel de parametros de la hormiga y añadimos sus componentes
		/*panelParametrosHormiga = new JPanel(new GridLayout(2,1));
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Parámetros Hormiga");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelParametrosHormiga.setBorder(compoundBorder);
		panelParametrosHormiga.add(panelProfundidad);
		panelParametrosHormiga.add(panelMaxPasos);*/
		
		//Creamos el panel de los datos y añadimos sus subpaneles
		panelDatos = new JPanel(new GridLayout(2,1));
		
		JPanel panelParamAGAux = new JPanel(new BorderLayout());
		panelParamAGAux.add(panelParametrosAG,BorderLayout.NORTH);
		
		JPanel panelMejorasAux = new JPanel(new BorderLayout());
		panelMejorasAux.add(panelMejoras,BorderLayout.NORTH);
		
		
		panelDatos.add(panelParamAGAux);
		panelDatos.add(panelMejorasAux);
		
		
		//Creamos el panelDatosAux que contiene al PanelDatos y al boton de ejecutar
		panelDatosAux = new JPanel(new BorderLayout());
		panelDatosAux.add(panelDatos,BorderLayout.CENTER);
		panelEjecutar_Aptitud.add(botonEjecutar);
		panelEjecutar_Aptitud.add(labelAptitud);
		panelDatosAux.add(panelEjecutar_Aptitud,BorderLayout.SOUTH);
		
		//Creamos el panel que contiene el area de texto del genotipo con una barra de desplazamiento
		panelScroll = new JScrollPane();
		panelScroll.setViewportView(textoGenotipo);
		panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//Creamos el panel del genotipo y boton ver grafica
		panelGenotipo_Grafica = new JPanel(new BorderLayout());
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Genotipo");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelGenotipo_Grafica.setBorder(compoundBorder);
		panelGenotipo_Grafica.add(panelScroll,BorderLayout.CENTER);
		
		panelGenotipo_Grafica.add(botonMostrarGrafica,BorderLayout.EAST);
		
		//Creamos el panel de datos-Matriz
		panelDatos_Matriz = new JPanel(new BorderLayout());
		panelDatos_Matriz.add(panelDatosAux,BorderLayout.WEST);
		panelDatos_Matriz.add(panelMatriz,BorderLayout.CENTER);
	
		panelPrincipal.add(panelDatos_Matriz,BorderLayout.CENTER);
		panelPrincipal.add(panelGenotipo_Grafica,BorderLayout.SOUTH);
		
		this.setContentPane(panelPrincipal);
		this.setJMenuBar(barraMenu);
		this.setSize(765, 595);
		this.setVisible(true);
		this.setTitle("Hormiga automática");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./images/hormiga.JPG"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public class OyenteBotonEjecutar implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ejecutarAlgoritmo();
		}
		
	}
	
	public class OyenteTeclaIntro implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
			//Solo si hemos pulsado la tecla intro
			if(e.getKeyCode() == 10)
				ejecutarAlgoritmo();
		}
		
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		
	}
	
	public class OyenteCheckElitismo implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(checkElitismo.isSelected())
				textoElitismo.setEnabled(true);
			else textoElitismo.setEnabled(false);
		}
		
	}
	
	public class OyenteModificarConstantes implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			frameModificacionConstantes = new JFrame();
			frameModificacionConstantes.setContentPane(panelConstantes);
			frameModificacionConstantes.setTitle("Modificar constantes");
			frameModificacionConstantes.setSize(240, 175);
			frameModificacionConstantes.setVisible(true);	
			
		}
		
	}
	
	public class OyenteAceptarConstantes implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ConstantesAlgoritmos.getInstance().setNumContrincantes(Integer.parseInt(textoNumContrincantes.getText()));
			ConstantesAlgoritmos.getInstance().setMaxPasos(Integer.parseInt(textoMaxPasos.getText()));
			ConstantesAlgoritmos.getInstance().setMaxProfundidad(Integer.parseInt(textoProfundidad.getText()));
			frameModificacionConstantes.dispose();
		}
		
	}
	
	public class OyenteValoresPorDefecto implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			textoTamPoblacion.setText(String.valueOf(Controlador.POBLACION_DEFECTO));
			textoNumGeneraciones.setText(String.valueOf(Controlador.GENERACIONES_DEFECTO));
			comboSeleccion.setSelectedIndex(0);
			textoProbCruce.setText(String.valueOf(Controlador.CRUCE_DEFECTO));
			comboMutacion.setSelectedIndex(0);
			textoProbMutacion.setText(String.valueOf(Controlador.MUTACION_DEFECTO));
			checkElitismo.setSelected(false);
			textoElitismo.setText(String.valueOf(Controlador.ELITISMO_DEFECTO));
			checkContractividad.setSelected(false);
			checkEscalado.setSelected(false);			
			
		}
		
	}
	
	public class OyenteSalir implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if( JOptionPane.showConfirmDialog(null,
					"¿Seguro que desea salir?",
					"Salir de la aplicación",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE
			) == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
		
	}
	
	public class OyenteComboSeleccion implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(comboSeleccion.getSelectedIndex() == 0)
				checkEscalado.setEnabled(true);
			else checkEscalado.setEnabled(false);
		}
		
	}
	
	public class OyenteBotonGrafica implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			//TODO
			controlador.dibujarGrafica();
		}
		
	}
	
	private void ejecutarAlgoritmo(){

		//Limpio la matriz de casillas, el texto del genotipo y el boton de las graficas en cada nueva ejecucion
		reiniciarMatrizCasillas();
		this.textoGenotipo.setText("");
		botonMostrarGrafica.setEnabled(false);
		
		//Extraemos los datos para pasarselos al controlador
		int tamPoblacion = Integer.parseInt(textoTamPoblacion.getText());
		int numGeneraciones = Integer.parseInt(textoNumGeneraciones.getText());
		double probCruce = Double.parseDouble(textoProbCruce.getText());
		double probMutacion = Double.parseDouble(textoProbMutacion.getText());
		boolean elitismo = checkElitismo.isSelected();
		double porcentajeElite = Double.parseDouble(textoElitismo.getText());
		int tipoMutacion = comboMutacion.getSelectedIndex();
		int tipoSeleccion = comboSeleccion.getSelectedIndex();
		boolean contractividad = checkContractividad.isSelected();
		boolean escalado = checkEscalado.isSelected();
		int tipoCruce = AGeneticoHormigas.CRUCE_ARBOREO_MEJORADO;
		
		Cromosoma[] resultados = controlador.ejecucionSencilla(numGeneraciones, tamPoblacion, probCruce, probMutacion, elitismo, 
										porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion, 
										contractividad, escalado);
		
		//Resultados[0] contiene el mejor cromosoma GLOBAL
		Cromosoma c = resultados[0];
		Evaluador evaluador = new EvaluadorHormigas(true);
		c.setAptitud(evaluador.evaluaAptitud(c));
		labelAptitud.setText("      Mejor aptitud: " + c.getAptitud());
		
		/*
		 * Mostramos el recorrido graficamente: NARANJA->hormiga
												VERDE -> Comida
		 */
		boolean[][] pasosDados = ((EvaluadorHormigas)evaluador).getPasos();
		for (int i = 0; i<pasosDados.length; i++){
			for (int j = 0; j < pasosDados[i].length; j++){
				if (pasosDados[i][j]) tablero[i][j].setBackground(Color.ORANGE);
			}
		}
		
		//Incrustamos en el area de texto el genotipo del cromosoma:
		textoGenotipo.setText(((GenArboreo)c.getGenes()[0]).toString());
		if(textoGenotipo.getText() != "") botonMostrarGrafica.setEnabled(true);
		
		
		
	}
	
	private void reiniciarMatrizCasillas(){
		for(int i=0; i<TableroComida.DIMENSION_Y; i++){
			for(int j=0; j<TableroComida.DIMENSION_X; j++){
				int contenidoCelda = TableroComida.COMIDA[i][j];
				if(contenidoCelda == 1)
					tablero[i][j].setBackground(Color.GREEN);
				else{
					Color color = new Color(238,238,238);  //Gris por defecto
					tablero[i][j].setBackground(color);
				}
			}
		}
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
}
