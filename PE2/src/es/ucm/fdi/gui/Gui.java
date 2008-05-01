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
import es.ucm.fdi.utils.ConstantesAlgoritmos;
import es.ucm.fdi.utils.Singleton;

public class Gui extends JFrame{

	private static final long serialVersionUID = 5251944077014401211L;
	public static final int tamañoPoblacion = 0;
	public static final int numGeneraciones = 1;
	public static final int probCruce = 2;
	public static final int probMutacion = 3;
	public static final int elitismo = 4;

	private Controlador controlador;
	private int parametroActivo;
	
	//FRAMES
	JFrame ventanaModificacionConstantes;
	
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
	private JPanel panelPresionEscalado;
	private JPanel panelConstantes;
	private JPanel panelContenedorConstantes;
	private JPanel panelIntercambios;
	private JPanel panelInserciones;
	private JPanel panelContrincantes;
	
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
	private JLabel labelIntercambios;
	private JLabel labelInserciones;
	private JLabel labelContrincantes;
	
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
	private JCheckBox checkEscalado;
	
	//CAMPOS DE TEXTO
	private JTextField textoNumGeneraciones;
	private JTextField textoTamPoblacion;
	private JTextField textoProbCruce;
	private JTextField textoProbMutacion;
	private JTextField textoElitismo;
	private JTextField textoInicial;
	private JTextField textoFinal;
	private JTextField textoIncremento;
	private JTextField textoIntercambios;
	private JTextField textoInserciones;
	private JTextField textoContrincantes;
	
	//BOTONES
	private JButton botonEmpezar;
	private JButton botonEjecucionMultiple;
	private JButton botonConstantes;
	
	//BARRA DE MENU
	private JMenuBar barraMenu;
	private JMenu menu;
	private JMenuItem cambiarConstantes;
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
		panelPresionEscalado = new JPanel(new GridLayout(1,2));
		panelElitismo = new JPanel(new GridLayout(1,2));
		panelConstantes = new JPanel(new BorderLayout());
		panelContenedorConstantes = new JPanel(new GridLayout(3,1));
		panelIntercambios = new JPanel(new GridLayout(1,2));
		panelInserciones = new JPanel(new GridLayout(1,2));
		panelContrincantes = new JPanel(new GridLayout(1,2));
		
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
		labelIntercambios = new JLabel("Número intercambios");
		labelInserciones = new JLabel("Número inserciones");
		labelContrincantes = new JLabel("Número contrincantes");
		
		//Creamos los combobox
		comboSeleccion = new JComboBox();
		comboCruce = new JComboBox();
		comboMutacion = new JComboBox();
		comboVariacionParametro = new JComboBox();
		comboVariacionParametro.setEnabled(false);
		
		//Añadimos las opciones a cada combobox		
		comboSeleccion.addItem("Ruleta");
		comboSeleccion.addItem("Ranking");
		comboSeleccion.addItem("Torneo");
		
		comboCruce.addItem("PMX");
		comboCruce.addItem("Ciclos (CX)");
		comboCruce.addItem("Recombinación de rutas (ERX)");
		comboCruce.addItem("Codificación ordinal");
		comboCruce.addItem("OX");
		comboCruce.addItem("OX orden prioritario");
		comboCruce.addItem("OX posiciones prioritarias");
		
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
		botonConstantes = new JButton("Aceptar");
		OyenteBotonModificarConstantes oyenteBotonAceptarCambios = new OyenteBotonModificarConstantes();
		botonConstantes.addActionListener(oyenteBotonAceptarCambios);
		
		
		//Para ejecutar el algoritmo correspondiente en cuanto se presione intro
		OyenteTeclaEjecucionBasica tecladoBasico = new OyenteTeclaEjecucionBasica();
		OyenteTeclaEjecucionMultiple tecladoMultiple = new OyenteTeclaEjecucionMultiple();
		
		//Creamos los checkbox
		checkElitismo = new JCheckBox("Elitismo");
		checkElitismo.setSelected(false);
		
		checkPresionSelectiva = new JCheckBox("Presión selectiva");
		checkPresionSelectiva.setEnabled(false);
		checkContractividad = new JCheckBox("Contractividad");
		checkEjecucionMultiple = new JCheckBox("Habilitar Ejecución múltiple");
		checkEscalado = new JCheckBox("Escalado de aptitud");
		
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
		textoIntercambios = new JTextField(String.valueOf(Singleton.getInstance().getNumIntercambios()));
		textoInserciones = new JTextField(String.valueOf(Singleton.getInstance().getNumInserciones()));
		textoContrincantes = new JTextField(String.valueOf(Singleton.getInstance().getNumContrincantes()));
		
		//Creamos la barra de menus
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
		

		
		//Añadimos el oyente de la tecla a los elementos para la ejcucion del agoritmo basico
		textoNumGeneraciones.addKeyListener(tecladoBasico);
		textoTamPoblacion.addKeyListener(tecladoBasico);
		textoProbCruce.addKeyListener(tecladoBasico);
		textoProbMutacion.addKeyListener(tecladoBasico);
		textoElitismo.addKeyListener(tecladoBasico);
		
		//Añdimos el oyente de la tecla a los elementos para cambiar las constantes de los algoritmos
		OyenteTeclaModificarConstantes oyenteTeclaConstantes = new OyenteTeclaModificarConstantes();
		textoIntercambios.addKeyListener(oyenteTeclaConstantes);
		textoInserciones.addKeyListener(oyenteTeclaConstantes);
		textoContrincantes.addKeyListener(oyenteTeclaConstantes);		
		
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
		
		panelPresionEscalado.add(checkPresionSelectiva);
		panelPresionEscalado.add(checkEscalado);
				
		//bordes
		Border lineBorder, titleBorder, emptyBorder, compoundBorder;
		emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Control de diversidad");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelControlDiversidad.setBorder(compoundBorder);
		panelControlDiversidad.add(panelElitismo);
		panelControlDiversidad.add(checkContractividad);
		panelControlDiversidad.add(panelPresionEscalado);
		
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
		
		//Datos del panel de modificacion de constantes
		//bordes
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Cruce OX orden prioritario");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelIntercambios.setBorder(compoundBorder);
		panelIntercambios.add(labelIntercambios);
		panelIntercambios.add(textoIntercambios);
		
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Mutación por inserción");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelInserciones.setBorder(compoundBorder);
		panelInserciones.add(labelInserciones);
		panelInserciones.add(textoInserciones);
		
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Seleccion por Torneo");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelContrincantes.setBorder(compoundBorder);
		panelContrincantes.add(labelContrincantes);
		panelContrincantes.add(textoContrincantes);
		
		panelContenedorConstantes.add(panelIntercambios);
		panelContenedorConstantes.add(panelInserciones);
		panelContenedorConstantes.add(panelContrincantes);
		panelConstantes.add(panelContenedorConstantes,BorderLayout.CENTER);
		panelConstantes.add(botonConstantes,BorderLayout.SOUTH);
		
		menu.setText("Archivo");
		menu.add(cambiarConstantes);
		menu.add(itemValoresDefecto);
		menu.add(itemSalir);
		barraMenu.add(menu);
		
		OyenteEjecutar oyenteBoton = new OyenteEjecutar();
		botonEmpezar.addActionListener(oyenteBoton);
		
		OyenteBotonEjecucionMultiple oyenteBotonMultiple = new OyenteBotonEjecucionMultiple();
		botonEjecucionMultiple.addActionListener(oyenteBotonMultiple);
		
		OyenteElitismo oyenteCheckElitismo = new OyenteElitismo();
		checkElitismo.addActionListener(oyenteCheckElitismo);
		
		OyenteEjecucionMultiple oyenteCheckEjecucionMultiple = new OyenteEjecucionMultiple();
		checkEjecucionMultiple.addActionListener(oyenteCheckEjecucionMultiple);
		
		OyenteVariacionParametros oyenteComboVariacionParametros = new OyenteVariacionParametros();
		comboVariacionParametro.addActionListener(oyenteComboVariacionParametros);
		
		OyenteComboSeleccion oyenteComboSelec = new OyenteComboSeleccion();
		comboSeleccion.addActionListener(oyenteComboSelec);
		
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
	
	class OyenteBotonEjecucionMultiple implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ejecutarAlgoritmoMultiple();
		}
		
	}
	
	private void ejecutarAlgoritmo(){
		try{
			int numGeneraciones = Integer.parseInt(textoNumGeneraciones.getText());
			int tamPoblacion = Integer.parseInt(textoTamPoblacion.getText());
			double probCruce = Double.parseDouble(textoProbCruce.getText());
			double probMutacion = Double.parseDouble(textoProbMutacion.getText());
			boolean elitismo = checkElitismo.isSelected();
			double porcentajeElitismo = Double.parseDouble(textoElitismo.getText());
			int tipoMutacion = comboMutacion.getSelectedIndex();
			int tipoCruce = comboCruce.getSelectedIndex();
			int tipoSeleccion = comboSeleccion.getSelectedIndex();
			boolean contractividad = checkContractividad.isSelected();
			boolean presionSelectiva = checkPresionSelectiva.isSelected();
			boolean escalado = checkEscalado.isSelected();
			controlador.ejecucionSencilla (numGeneraciones, tamPoblacion, probCruce, probMutacion ,elitismo,
										porcentajeElitismo, tipoMutacion, tipoCruce,tipoSeleccion,
										contractividad, presionSelectiva, escalado);
			
		}catch(NumberFormatException exception){
			JOptionPane.showMessageDialog(null, "Se han introducido mal los datos");
		}
	}
	
	private void ejecutarAlgoritmoMultiple(){
		try{
			guardarValores(parametroActivo);
			int parametro = comboVariacionParametro.getSelectedIndex();
			double inicio = Double.parseDouble(textoInicial.getText());
			double fin = Double.parseDouble(textoFinal.getText()); 
			double incremento = Double.parseDouble(textoIncremento.getText()); 
			int numGeneraciones = Integer.parseInt(textoNumGeneraciones.getText());
			int tamPoblacion = Integer.parseInt(textoTamPoblacion.getText());
			double probCruce = Double.parseDouble(textoProbCruce.getText());
			double probMutacion = Double.parseDouble(textoProbMutacion.getText());
			boolean eli = checkElitismo.isSelected();
			double porcentajeElite = Double.parseDouble(textoElitismo.getText());
			int tipoMutacion = comboMutacion.getSelectedIndex();
			int tipoCruce = comboCruce.getSelectedIndex();
			int tipoSeleccion = comboSeleccion.getSelectedIndex();
			boolean contractividad = checkContractividad.isSelected();
			boolean presionSelectiva = checkPresionSelectiva.isSelected();
			boolean escalado = checkEscalado.isSelected();
			controlador.ejecucionMultiple(parametro,inicio,fin,incremento, numGeneraciones, tamPoblacion, probCruce, 
					probMutacion, eli, porcentajeElite, tipoMutacion, tipoCruce, tipoSeleccion,
					contractividad, presionSelectiva, escalado);
		}catch(NumberFormatException exception){
			JOptionPane.showMessageDialog(null, "Se han introducido mal los datos");
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
			cargarValoresUsuario(parametroActivo);			
		}
		
	}
	
	private void cargarValoresUsuario(int parametroActivo){
		switch(parametroActivo){
		case tamañoPoblacion:{
			textoInicial.setText(String.valueOf(controlador.getCopiaPoblacionMultiple()[0]));
			textoFinal.setText(String.valueOf(controlador.getCopiaPoblacionMultiple()[1]));
			textoIncremento.setText(String.valueOf(controlador.getCopiaPoblacionMultiple()[2]));
		}break;
		case numGeneraciones:{
			textoInicial.setText(String.valueOf(controlador.getCopiaGeneracionesMultiple()[0]));
			textoFinal.setText(String.valueOf(controlador.getCopiaGeneracionesMultiple()[1]));
			textoIncremento.setText(String.valueOf(controlador.getCopiaGeneracionesMultiple()[2]));
		}break;
		case probCruce:{
			textoInicial.setText(String.valueOf(controlador.getCopiaCruceMultiple()[0]));
			textoFinal.setText(String.valueOf(controlador.getCopiaCruceMultiple()[1]));
			textoIncremento.setText(String.valueOf(controlador.getCopiaCruceMultiple()[2]));
		}break;
		case probMutacion:{
			textoInicial.setText(String.valueOf(controlador.getCopiaMutacionMultiple()[0]));
			textoFinal.setText(String.valueOf(controlador.getCopiaMutacionMultiple()[1]));
			textoIncremento.setText(String.valueOf(controlador.getCopiaMutacionMultiple()[2]));
		}break;
		case elitismo:{
			textoInicial.setText(String.valueOf(controlador.getCopiaElitismoMultiple()[0]));
			textoFinal.setText(String.valueOf(controlador.getCopiaElitismoMultiple()[1]));
			textoIncremento.setText(String.valueOf(controlador.getCopiaElitismoMultiple()[2]));
		}
		}
	}

	private void cargarValores(int parametroActivo) {
		controlador.setCopiaPoblacionMultiple(Controlador.POBLACION_MULTIPLE_DEFECTO);
		controlador.setCopiaGeneracionesMultiple(Controlador.GENERACIONES_MULTIPLE_DEFECTO);
		controlador.setCopiaCruceMultiple(Controlador.CRUCE_MULTIPLE_DEFECTO);
		controlador.setCopiaMutacionMultiple(Controlador.MUTACION_MULTIPLE_DEFECTO);
		controlador.setCopiaElitismoMultiple(Controlador.ELITISMO_MULTIPLE_DEFECTO);
		
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

	private void guardarValores(int parametroActivo){
		double[] copia = new double[3];
		
		copia[0]=Double.parseDouble(textoInicial.getText());
		copia[1]=Double.parseDouble(textoFinal.getText());
		copia[2]=Double.parseDouble(textoIncremento.getText());
		
		switch(parametroActivo){
		case tamañoPoblacion:controlador.setCopiaPoblacionMultiple(copia);break;
		case numGeneraciones:controlador.setCopiaGeneracionesMultiple(copia);break;
		case probCruce:controlador.setCopiaCruceMultiple(copia);break;
		case probMutacion:controlador.setCopiaMutacionMultiple(copia);break;
		case elitismo:controlador.setCopiaElitismoMultiple(copia);
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
					ejecutarAlgoritmo();
				}
				else{
					System.out.println("Ejecucion multiple");
					ejecutarAlgoritmoMultiple();
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
				ejecutarAlgoritmoMultiple();
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
			resetarConstantes();
			
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
	
	class OyenteComboSeleccion implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			if(comboSeleccion.getSelectedIndex() == 0){  //Ruleta = 0
				checkEscalado.setEnabled(true);
				checkPresionSelectiva.setSelected(false);
			}
			else checkEscalado.setEnabled(false);
			
			if(comboSeleccion.getSelectedIndex() == 1){  //Ranking = 1
				checkPresionSelectiva.setEnabled(true);
				checkEscalado.setSelected(false);
			}
			else checkPresionSelectiva.setEnabled(false);
		}	
		
	}
	
	class OyenteModificarConstantes implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ventanaModificacionConstantes = new JFrame();
			ventanaModificacionConstantes.setContentPane(panelConstantes);
			ventanaModificacionConstantes.setTitle("Modificar constantes");
			ventanaModificacionConstantes.setSize(300, 210);
			ventanaModificacionConstantes.setVisible(true);			
		}
		
	}
	
	class OyenteBotonModificarConstantes implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			modificarConstantesAlgoritmos();			
		}
		
	}
	
	class OyenteTeclaModificarConstantes implements KeyListener{

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 10){
				modificarConstantesAlgoritmos();
			}
		}

		public void keyReleased(KeyEvent e) {
		
		}

		public void keyTyped(KeyEvent e) {
			
		}
		
	}
	
	private void modificarConstantesAlgoritmos(){
		int numIntercambios = Integer.parseInt(textoIntercambios.getText());
		int numInserciones = Integer.parseInt(textoInserciones.getText());
		int numContrincantes = Integer.parseInt(textoContrincantes.getText());
		
		Singleton.getInstance().setNumIntercambios(numIntercambios);
		Singleton.getInstance().setNumInserciones(numInserciones);
		Singleton.getInstance().setNumContrincantes(numContrincantes);
		
		//Cerramos la ventana
		ventanaModificacionConstantes.dispose();
	}
	
	private void resetarConstantes() {
		int numIntercambios = ConstantesAlgoritmos.NUMERO_INTERCAMBIOS;
		int numInserciones = ConstantesAlgoritmos.NUMERO_INSERCIONES;
		int numContrincantes = ConstantesAlgoritmos.NUMERO_CONTRINCANTES;
		
		Singleton.getInstance().setNumIntercambios(numIntercambios);
		Singleton.getInstance().setNumInserciones(numInserciones);
		Singleton.getInstance().setNumContrincantes(numContrincantes);
				
		numIntercambios = Singleton.getInstance().getNumIntercambios();
		numInserciones = Singleton.getInstance().getNumInserciones();
		numContrincantes = Singleton.getInstance().getNumContrincantes();
		
		textoIntercambios.setText(String.valueOf(numIntercambios));
		textoInserciones.setText(String.valueOf(numInserciones));
		textoContrincantes.setText(String.valueOf(numContrincantes));
		
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

}