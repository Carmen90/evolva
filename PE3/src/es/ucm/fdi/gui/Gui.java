package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.plaf.basic.BasicOptionPaneUI;

import sun.net.www.content.image.jpeg;

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
	private JPanel panelParametrosHormiga;
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
		
		//Creamos los campos de texto
		textoNumGeneraciones = new JTextField();
		textoTamPoblacion = new JTextField();
		textoProbCruce = new JTextField();
		textoProbMutacion = new JTextField();
		textoElitismo = new JTextField();
		textoElitismo.setEnabled(false);   //Desactivamos el campo de texto de elitismo hasta que se marque en el checkbox
		textoProfundidad = new JTextField();
		textoMaxPasos = new JTextField();
		textoGenotipo = new JTextArea();
		textoGenotipo.setEditable(false);
		textoGenotipo.setLineWrap(true);
		textoNumContrincantes = new JTextField();
		
		//Creamos los checkbox
		checkElitismo = new JCheckBox("Elitismo");
		OyenteCheckElitismo oyenteElitismo = new OyenteCheckElitismo();
		checkElitismo.addActionListener(oyenteElitismo);
		
		checkContractividad = new JCheckBox("Contractividad");
		
		//Creamos los combos y añadimos los elementos
		comboMutacion = new JComboBox();
		comboMutacion.addItem("Terminal simple");
		comboMutacion.addItem("Funcional simple");
		comboMutacion.addItem("Árbol");
		
		comboSeleccion = new JComboBox();
		comboSeleccion.addItem("Ruleta");
		comboSeleccion.addItem("Torneo");
		
		//Creamos los botones
		botonEjecutar = new JButton("Ejecutar");
		botonMostrarGrafica = new JButton("Gráfica");
		botonConstantes = new JButton("Aceptar");
		

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
		for(int i=0; i<TableroComida.DIMENSION_Y; i++){
			for(int j=0; j<TableroComida.DIMENSION_X; j++){
				Celda casilla = new Celda(i,j);
				casilla.setEnabled(false);
				int contenidoCelda = TableroComida.COMIDA[i][j];
				if(contenidoCelda == 1)
					casilla.setBackground(Color.GREEN);
				else{
					//ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource("es/ucm/fdi/images/hormiga.gif"));
					//casilla.setIcon(icono);
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
		panelContenedorConstantes = new JPanel(new GridLayout(1,1));
		panelContrincantes = new JPanel(new GridLayout(1,2));
		
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
		panelMejoras = new JPanel(new GridLayout(2,1));
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Mejoras");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelMejoras.setBorder(compoundBorder);
		panelMejoras.add(panelElitismo);
		panelMejoras.add(checkContractividad);
		
		//Añadimos los componentes al contenedor de la ventana que cambia el valor de las constantes en los algoritmos
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Selección por Torneo");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelContrincantes.setBorder(compoundBorder);
		panelContenedorConstantes.add(panelContrincantes);
		panelConstantes.add(panelContenedorConstantes,BorderLayout.CENTER);
		panelConstantes.add(botonConstantes, BorderLayout.SOUTH);
		
		//Creamos el panel de parametros de la hormiga y añadimos sus componentes
		panelParametrosHormiga = new JPanel(new GridLayout(2,1));
		titleBorder = BorderFactory.createTitledBorder(lineBorder, "Parámetros Hormiga");
		compoundBorder = BorderFactory.createCompoundBorder(titleBorder,emptyBorder);
		panelParametrosHormiga.setBorder(compoundBorder);
		panelParametrosHormiga.add(panelProfundidad);
		panelParametrosHormiga.add(panelMaxPasos);
		
		//Creamos el panel de los datos y añadimos sus subpaneles
		panelDatos = new JPanel(new BorderLayout());
		panelDatos.add(panelParametrosAG,BorderLayout.NORTH);
		panelDatos.add(panelMejoras,BorderLayout.CENTER);
		panelDatos.add(panelParametrosHormiga,BorderLayout.SOUTH);
		
		//Creamos el panelDatosAux que contiene al PanelDatos y al boton de ejecutar
		panelDatosAux = new JPanel(new BorderLayout());
		panelDatosAux.add(panelDatos,BorderLayout.CENTER);
		panelDatosAux.add(botonEjecutar,BorderLayout.SOUTH);
		
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
	
		panelPrincipal.add(panelDatos_Matriz,BorderLayout.NORTH);
		panelPrincipal.add(panelGenotipo_Grafica,BorderLayout.CENTER);
		
		this.setContentPane(panelPrincipal);
		this.setJMenuBar(barraMenu);
		this.setSize(950, 490);
		this.setVisible(true);
		this.setTitle("Hormiga automática");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			//TODO
			frameModificacionConstantes = new JFrame();
			frameModificacionConstantes.setContentPane(panelConstantes);
			frameModificacionConstantes.setTitle("Modificar constantes");
			frameModificacionConstantes.setSize(240, 210);
			frameModificacionConstantes.setVisible(true);	
			
		}
		
	}
	
	public class OyenteValoresPorDefecto implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			//TODO
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
}
