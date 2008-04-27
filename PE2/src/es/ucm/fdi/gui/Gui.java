package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
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
import javax.swing.JTextField;
import javax.swing.border.Border;

import es.ucm.fdi.controlador.Controlador;

public class Gui extends JFrame{
	
	private static final long serialVersionUID = 5251944077014401211L;
	private static final int tamañoPoblacion = 0;
	private static final int numGeneraciones = 1;
	private static final int probCruce = 2;
	private static final int probMutacion = 3;
	private static final int elitismo = 4;

	private Controlador controlador;
	private int parametroActivo;
	
	//PANELES
	private JPanel panelPrincipal;
	private JPanel panelBasico;
	private JPanel panelElitismo;
	private JPanel panelMejoras;
	private JPanel panelControlDiversidad;
	private JPanel panelVariacionParametros;
	private JPanel panelInicial;
	private JPanel panelFinal;
	private JPanel panelInicialFinal;
	private JPanel panelIncremento;
	private JPanel panelEjecucionMultiple;
	private JPanel panelDiversidadEjecucionMultiple;
	private JPanel panelContenedorBasico;
	
	//ETIQUETAS
	private JLabel labelNumGeneraciones;
	private JLabel labelTamPoblacion;
	private JLabel labelProbCruce;
	private JLabel labelProbMutacion;
	private JLabel labelSeleccion;
	private JLabel labelCruce;
	private JLabel labelMutacion;
	private JLabel labelVariacionParametro;
	private JLabel labelInicial;
	private JLabel labelFinal;
	private JLabel labelIncremento;
	
	//COMBOS
	private JComboBox comboSeleccion;
	private JComboBox comboCruce;
	private JComboBox comboMutacion;
	private JComboBox comboVariacionParametro;
	
	//CHECKBOX
	private JCheckBox checkElitismo;
	private JCheckBox checkPresionSelectiva;
	private JCheckBox checkContractividad;
	private JCheckBox checkEjecucionMultiple;
	
	//CAMPOS DE TEXTO
	private JTextField textoNumGeneraciones;
	private JTextField textoTamPoblacion;
	private JTextField textoProbCruce;
	private JTextField textoProbMutacion;
	private JTextField textoElitismo;
	private JTextField textoInicial;
	private JTextField textoFinal;
	private JTextField textoIncremento;
	
	//BOTONES
	private JButton botonEmpezar;
	private JButton botonEjecucionMultiple;
	
	//BARRA DE MENU
	private JMenuBar barraMenu;
	private JMenu menu;
	private JMenuItem itemValoresDefecto;
	private JMenuItem itemSalir;
	
	public Gui(){
		
		parametroActivo = tamañoPoblacion;
		
		panelPrincipal = new JPanel(new GridLayout(1,2));
		panelBasico = new JPanel(new GridLayout(7,2));
		panelMejoras = new JPanel(new BorderLayout());
		panelControlDiversidad = new JPanel(new GridLayout(3,1));
		panelVariacionParametros = new JPanel(new GridLayout(1,2));
		panelInicial = new JPanel(new GridLayout(1,2));
		panelFinal = new JPanel(new GridLayout(1,2));
		panelInicialFinal = new JPanel(new GridLayout(1,2));
		panelIncremento = new JPanel(new GridLayout(1,2));
		panelEjecucionMultiple = new JPanel(new GridLayout(4,1));
		panelDiversidadEjecucionMultiple = new JPanel(new GridLayout(2,1));
		panelContenedorBasico = new JPanel(new BorderLayout());
		
		panelElitismo = new JPanel(new GridLayout(1,2));
		
		//Creamos las etiquetas
		labelNumGeneraciones = new JLabel("Número generaciones");
		labelTamPoblacion = new JLabel("Tamaño población");
		labelProbCruce = new JLabel("Probabilidad cruce");
		labelProbMutacion = new JLabel("Probabilidad mutación");
		labelSeleccion = new JLabel("Método de selección");
		labelCruce = new JLabel("Método de cruce");
		labelMutacion = new JLabel("Método de mutación");
		labelVariacionParametro = new JLabel("Parámetro a variar");
		labelInicial = new JLabel("Inicial");
		labelFinal = new JLabel("Final  ",JLabel.RIGHT);
		labelIncremento = new JLabel("Incremento");
		
		//Creamos los combobox
		comboSeleccion = new JComboBox();
		comboCruce = new JComboBox();
		comboMutacion = new JComboBox();
		comboVariacionParametro = new JComboBox();
		comboVariacionParametro.setEnabled(false);
		
		//Añadimos las opciones a cada combobox		
		comboSeleccion.addItem("Ruleta");
		comboSeleccion.addItem("Torneo");
		comboSeleccion.addItem("Ranking");
		
		comboCruce.addItem("PMX");
		comboCruce.addItem("OX");
		comboCruce.addItem("OX posiciones prioritarias");
		comboCruce.addItem("OX orden prioritario");
		comboCruce.addItem("Ciclos (CX)");
		comboCruce.addItem("Recombinación de rutas (ERX)");
		comboCruce.addItem("Codificación ordinal");
		
		comboMutacion.addItem("Inserción");
		comboMutacion.addItem("Intercambio");
		comboMutacion.addItem("Inversión");
		
		comboVariacionParametro.addItem("Tamaño población");
		comboVariacionParametro.addItem("Número generaciones");
		comboVariacionParametro.addItem("Probabilidad cruce");
		comboVariacionParametro.addItem("Probabilidad mutación");
		comboVariacionParametro.addItem("Elitismo");
				
		//Creamos los botones
		botonEmpezar = new JButton("Ejecutar");
		botonEjecucionMultiple = new JButton("Ejecución múltiple");
		botonEjecucionMultiple.setEnabled(false);
		
		//Para ejecutar el algoritmo correspondiente en cuanto se presione intro
		OyenteTeclaEjecucionBasica tecladoBasico = new OyenteTeclaEjecucionBasica();
		OyenteTeclaEjecucionMultiple tecladoMultiple = new OyenteTeclaEjecucionMultiple();
		
		//Creamos los checkbox
		checkElitismo = new JCheckBox("Elitismo");
		checkElitismo.setSelected(false);
		
		checkPresionSelectiva = new JCheckBox("Presión selectiva Ranking");
		checkContractividad = new JCheckBox("Contractividad");
		checkEjecucionMultiple = new JCheckBox("Habilitar Ejecución múltiple");
		
		//Creamos las cajas de texto
		textoNumGeneraciones = new JTextField(String.valueOf(Controlador.GENERACIONES_DEFECTO));
		textoTamPoblacion = new JTextField(String.valueOf(Controlador.POBLACION_DEFECTO));
		textoProbCruce = new JTextField(String.valueOf(Controlador.CRUCE_DEFECTO));
		textoProbMutacion = new JTextField(String.valueOf(Controlador.MUTACION_DEFECTO));
		textoElitismo = new JTextField(String.valueOf(Controlador.ELITISMO_DEFECTO));
		textoElitismo.setEnabled(false);
		textoInicial = new JTextField(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[0]));
		textoInicial.setEnabled(false);
		textoFinal = new JTextField(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[1]));
		textoFinal.setEnabled(false);
		textoIncremento = new JTextField(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[2]));
		textoIncremento.setEnabled(false);
		
		//Creamos la barra de menus
		barraMenu = new JMenuBar();
		menu = new JMenu();
		itemValoresDefecto = new JMenuItem("Valores por defecto");
		OyenteValoresPorDefecto valoresDefecto = new OyenteValoresPorDefecto();
		itemValoresDefecto.addActionListener(valoresDefecto);
		itemSalir = new JMenuItem("Salir");
		OyenteSalir salida = new OyenteSalir();
		itemSalir.addActionListener(salida);
		

		
		//Añadimos el oyente de la tecla a los elementos para la ejcucion del agoritmo basico
		textoNumGeneraciones.addKeyListener(tecladoBasico);
		textoTamPoblacion.addKeyListener(tecladoBasico);
		textoProbCruce.addKeyListener(tecladoBasico);
		textoProbMutacion.addKeyListener(tecladoBasico);
		textoElitismo.addKeyListener(tecladoBasico);
		
		//Añadimos el oyente de la tecla a los elementos para la ejcucion multiple
		textoInicial.addKeyListener(tecladoMultiple);
		textoFinal.addKeyListener(tecladoMultiple);
		textoIncremento.addKeyListener(tecladoMultiple);

		panelBasico.add(labelTamPoblacion);
		panelBasico.add(textoTamPoblacion);
		
		panelBasico.add(labelNumGeneraciones);
		panelBasico.add(textoNumGeneraciones);
		
		panelBasico.add(labelSeleccion);
		panelBasico.add(comboSeleccion);
		
		panelBasico.add(labelCruce);
		panelBasico.add(comboCruce);
		
		panelBasico.add(labelProbCruce);
		panelBasico.add(textoProbCruce);
		
		panelBasico.add(labelMutacion);
		panelBasico.add(comboMutacion);
		
		panelBasico.add(labelProbMutacion);
		panelBasico.add(textoProbMutacion);

		panelElitismo.add(checkElitismo);
		panelElitismo.add(textoElitismo);
				
		//bordes
		Border lineBorder, titleBorder, emptyBorder, compoundBorder;
		emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Control de diversidad");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelControlDiversidad.setBorder(compoundBorder);
		panelControlDiversidad.add(panelElitismo);
		panelControlDiversidad.add(checkPresionSelectiva);
		panelControlDiversidad.add(checkContractividad);
		
		panelVariacionParametros.add(labelVariacionParametro);
		panelVariacionParametros.add(comboVariacionParametro);
		
		panelInicial.add(labelInicial);
		panelInicial.add(textoInicial);
		
		panelFinal.add(labelFinal);
		panelFinal.add(textoFinal);
		
		panelInicialFinal.add(panelInicial);
		panelInicialFinal.add(panelFinal);
		
		panelIncremento.add(labelIncremento);
		panelIncremento.add(textoIncremento);
		
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Ejecución múltiple");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelEjecucionMultiple.setBorder(compoundBorder);
		panelEjecucionMultiple.add(checkEjecucionMultiple);
		panelEjecucionMultiple.add(panelVariacionParametros);
		panelEjecucionMultiple.add(panelInicialFinal);
		panelEjecucionMultiple.add(panelIncremento);
		
		panelDiversidadEjecucionMultiple.add(panelControlDiversidad);
		panelDiversidadEjecucionMultiple.add(panelEjecucionMultiple);
		
		panelMejoras.add(panelDiversidadEjecucionMultiple,BorderLayout.CENTER);
		panelMejoras.add(botonEjecucionMultiple,BorderLayout.SOUTH);
		
		panelContenedorBasico.add(panelBasico,BorderLayout.CENTER);
		panelContenedorBasico.add(botonEmpezar,BorderLayout.SOUTH);
		
		panelPrincipal.add(panelContenedorBasico);
		panelPrincipal.add(panelMejoras);
		
		menu.setText("Archivo");
		menu.add(itemValoresDefecto);
		menu.add(itemSalir);
		barraMenu.add(menu);
		
		OyenteEjecutar oyenteBoton = new OyenteEjecutar();
		botonEmpezar.addActionListener(oyenteBoton);
		
		OyenteElitismo oyenteCheckElitismo = new OyenteElitismo();
		checkElitismo.addActionListener(oyenteCheckElitismo);
		
		OyenteEjecucionMultiple oyenteCheckEjecucionMultiple = new OyenteEjecucionMultiple();
		checkEjecucionMultiple.addActionListener(oyenteCheckEjecucionMultiple);
		
		OyenteVariacionParametros oyenteComboVariacionParametros = new OyenteVariacionParametros();
		comboVariacionParametro.addActionListener(oyenteComboVariacionParametros);
		
		this.setContentPane(panelPrincipal);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText("Archivo");
		menu.add(new JMenuItem("Valores por defecto"));
		menu.add(new JMenuItem("Salir"));
		menuBar.add(menu);
		this.setJMenuBar(barraMenu);
		this.setSize(730, 300);
		this.setVisible(true);
		this.setTitle("Problema del viajante");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class OyenteEjecutar implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ejecutarAlgoritmo();
		}	
	}
	
		
	class OyenteElitismo implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			JCheckBox elitismo = (JCheckBox)arg0.getSource();
			if(elitismo.isSelected()) textoElitismo.setEnabled(true);
			else textoElitismo.setEnabled(false);
		}
		
	}
	
	class OyenteEjecucionMultiple implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JCheckBox ejecucionMultiple = (JCheckBox)e.getSource();
			if(ejecucionMultiple.isSelected()){   //Activamos los controles de ejecucion multiple
				comboVariacionParametro.setEnabled(true);
				textoInicial.setEnabled(true);
				textoFinal.setEnabled(true);
				textoIncremento.setEnabled(true);
				desactivarCajaTexto(parametroActivo);
				botonEjecucionMultiple.setEnabled(true);
			}
			else{
				//Los controles de ejecucion multiple quedan descativados
				comboVariacionParametro.setEnabled(false);
				textoInicial.setEnabled(false);
				textoFinal.setEnabled(false);
				textoIncremento.setEnabled(false);
				
				//Perimitimos modificar los parametros basicos del algoritmo
				textoTamPoblacion.setEnabled(true);
				textoNumGeneraciones.setEnabled(true);
				textoProbCruce.setEnabled(true);
				textoProbMutacion.setEnabled(true);
				if(checkElitismo.isSelected())
					textoElitismo.setEnabled(true);
				checkElitismo.setEnabled(true);
				botonEjecucionMultiple.setEnabled(false);
			}
			
		}
		
	}
	
	class OyenteVariacionParametros implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			parametroActivo = comboVariacionParametro.getSelectedIndex();
			desactivarCajaTexto(parametroActivo);
			
			//Cargamos los valores por defecto correspondientes
			cargarValores(parametroActivo);			
		}
		
	}

	public void cargarValores(int parametroActivo) {
		switch(parametroActivo){
		case tamañoPoblacion:{
			textoInicial.setText(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[0]));
			textoFinal.setText(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[1]));
			textoIncremento.setText(String.valueOf(Controlador.POBLACION_MULTIPLE_DEFECTO[2]));
		}break;
		case numGeneraciones:{
			textoInicial.setText(String.valueOf(Controlador.GENERACIONES_MULTIPLE_DEFECTO[0]));
			textoFinal.setText(String.valueOf(Controlador.GENERACIONES_MULTIPLE_DEFECTO[1]));
			textoIncremento.setText(String.valueOf(Controlador.GENERACIONES_MULTIPLE_DEFECTO[2]));
		}break;
		case probCruce:{
			textoInicial.setText(String.valueOf(Controlador.CRUCE_MULTIPLE_DEFECTO[0]));
			textoFinal.setText(String.valueOf(Controlador.CRUCE_MULTIPLE_DEFECTO[1]));
			textoIncremento.setText(String.valueOf(Controlador.CRUCE_MULTIPLE_DEFECTO[2]));
		}break;
		case probMutacion:{
			textoInicial.setText(String.valueOf(Controlador.MUTACION_MULTIPLE_DEFECTO[0]));
			textoFinal.setText(String.valueOf(Controlador.MUTACION_MULTIPLE_DEFECTO[1]));
			textoIncremento.setText(String.valueOf(Controlador.MUTACION_MULTIPLE_DEFECTO[2]));
		}break;
		case elitismo:{
			textoInicial.setText(String.valueOf(Controlador.ELITISMO_MULTIPLE_DEFECTO[0]));
			textoFinal.setText(String.valueOf(Controlador.ELITISMO_MULTIPLE_DEFECTO[1]));
			textoIncremento.setText(String.valueOf(Controlador.ELITISMO_MULTIPLE_DEFECTO[2]));
		}
		}
		
	}

	
	//Se encarga de desactivar la caja de texto pasada por parametro y de activar las demas
	private void desactivarCajaTexto(int paramActivo){
		switch(paramActivo){
		case tamañoPoblacion: {
			textoTamPoblacion.setEnabled(false);
			textoNumGeneraciones.setEnabled(true);
			textoProbCruce.setEnabled(true);
			textoProbMutacion.setEnabled(true);
			if(checkElitismo.isSelected())
				textoElitismo.setEnabled(true);
			else 
				textoElitismo.setEnabled(false);
			checkElitismo.setEnabled(true);
		}break;
		case numGeneraciones: {
			textoTamPoblacion.setEnabled(true);
			textoNumGeneraciones.setEnabled(false);
			textoProbCruce.setEnabled(true);
			textoProbMutacion.setEnabled(true);
			if(checkElitismo.isSelected())
				textoElitismo.setEnabled(true);
			else 
				textoElitismo.setEnabled(false);
			checkElitismo.setEnabled(true);
		}break;
		case probCruce: {
			textoTamPoblacion.setEnabled(true);
			textoNumGeneraciones.setEnabled(true);
			textoProbCruce.setEnabled(false);
			textoProbMutacion.setEnabled(true);
			if(checkElitismo.isSelected())
				textoElitismo.setEnabled(true);
			else 
				textoElitismo.setEnabled(false);
			checkElitismo.setEnabled(true);
		}break;
		case probMutacion: {
			textoTamPoblacion.setEnabled(true);
			textoNumGeneraciones.setEnabled(true);
			textoProbCruce.setEnabled(true);
			textoProbMutacion.setEnabled(false);
			if(checkElitismo.isSelected())
				textoElitismo.setEnabled(true);
			else 
				textoElitismo.setEnabled(false);
			checkElitismo.setEnabled(true);
		}break;
		case elitismo: {
			textoTamPoblacion.setEnabled(true);
			textoNumGeneraciones.setEnabled(true);
			textoProbCruce.setEnabled(true);
			textoProbMutacion.setEnabled(true);
			textoElitismo.setEnabled(false);
			if(!checkElitismo.isSelected())
				checkElitismo.setSelected(true);
			checkElitismo.setEnabled(false);
		}break;
		}
	}
	
	
	class OyenteTeclaEjecucionBasica implements KeyListener{

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10){
				
				if(!checkEjecucionMultiple.isSelected()){
					System.out.println("Ejecucion basica");
					//TODO ejecutarAlgoritmo();
				}
				else{
					System.out.println("Ejecucion multiple");
					//TODO Ejecucion multiple
				}
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	class OyenteTeclaEjecucionMultiple implements KeyListener{

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 10){
				//TODO Algoritmo con ejecucion multiple
				System.out.println("Ejecucion multiple");
			}
		}

		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {}
		
	}
	
	class OyenteValoresPorDefecto implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			textoTamPoblacion.setText(String.valueOf(Controlador.POBLACION_DEFECTO));
			textoNumGeneraciones.setText(String.valueOf(Controlador.GENERACIONES_DEFECTO));
			textoProbCruce.setText(String.valueOf(Controlador.CRUCE_DEFECTO));
			textoProbMutacion.setText(String.valueOf(Controlador.MUTACION_DEFECTO));
			textoElitismo.setText(String.valueOf(Controlador.ELITISMO_DEFECTO));
			cargarValores(parametroActivo);
		}
		
	}
	
	class OyenteSalir implements ActionListener{

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
	
	private void ejecutarAlgoritmo(){
		try{
			int numGeneraciones = Integer.parseInt(textoNumGeneraciones.getText());
			int tamPoblacion = Integer.parseInt(textoTamPoblacion.getText());
			double probCruce = Double.parseDouble(textoProbCruce.getText());
			double probMutacion = Double.parseDouble(textoProbMutacion.getText());
			boolean elitismo = checkElitismo.isSelected();
			controlador.recogerDatosGUI(0, 0, numGeneraciones, tamPoblacion, probCruce, probMutacion, 0, elitismo);
		}catch(NumberFormatException exception){
			JOptionPane.showMessageDialog(null, "Se han introducido mal los datos");
		}
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

}
