package es.ucm.fdi.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.ucm.fdi.controlador.Controlador;

public class Gui extends JFrame{
	
	private static final long serialVersionUID = 5251944077014401211L;

	private Controlador controlador;
	
	private JPanel panelPrincipal;
	private JPanel panelElitismo;
	
	private JLabel labelFuncion;
	private JLabel labelNumGenes;
	private JLabel labelNumGeneraciones;
	private JLabel labelTamPoblacion;
	private JLabel labelProbCruce;
	private JLabel labelProbMutacion;
	private JLabel labelTolerancia;
	
	private JComboBox comboFunciones;
	private JComboBox comboNumGenes;
	private JCheckBox checkElitismo;
	
	private JTextField textoNumGeneraciones;
	private JTextField textoTamPoblacion;
	private JTextField textoProbCruce;
	private JTextField textoProbMutacion;
	private JTextField textoTolerancia;
	private JTextField textoElitismo;
	
	private JButton botonEmpezar;
	
	public Gui(){
		
		panelPrincipal = new JPanel(new GridLayout(8,2));
		panelElitismo = new JPanel(new GridLayout(1,2));
		
		labelFuncion = new JLabel("Función");
		labelNumGenes = new JLabel("Número genes");
		labelNumGeneraciones = new JLabel("Número generaciones");
		labelTamPoblacion = new JLabel("Tamaño población");
		labelProbCruce = new JLabel("Probabilidad cruce");
		labelProbMutacion = new JLabel("Probabilidad mutación");
		labelTolerancia = new JLabel("Tolerancia/Precisión");
		botonEmpezar = new JButton("Ejecutar algoritmo");
		
		OyenteTecla teclado = new OyenteTecla();
		
		comboFunciones = new JComboBox();
		comboFunciones.addKeyListener(teclado);
		
		comboNumGenes = new JComboBox();
		comboNumGenes.addKeyListener(teclado);
		
		checkElitismo = new JCheckBox("Elitismo");
		checkElitismo.setSelected(false);
		
		comboFunciones.addItem("Función 1");
		comboFunciones.addItem("Función 2");
		comboFunciones.addItem("Función 3");
		comboFunciones.addItem("Función 4");
		comboFunciones.addItem("Función 5");
		
		comboNumGenes.addItem(1);
		comboNumGenes.addItem(2);
		comboNumGenes.addItem(3);
		comboNumGenes.addItem(4);
		comboNumGenes.addItem(5);
		comboNumGenes.addItem(6);
		comboNumGenes.addItem(7);
		comboNumGenes.addItem(8);
		comboNumGenes.addItem(9);
		comboNumGenes.addItem(10);
		
		comboNumGenes.setEnabled(false);
		
		textoNumGeneraciones = new JTextField(String.valueOf(Controlador.GENERACIONES_DEFECTO));
		textoTamPoblacion = new JTextField(String.valueOf(Controlador.POBLACION_DEFECTO));
		textoProbCruce = new JTextField(String.valueOf(Controlador.CRUCE_DEFECTO));
		textoProbMutacion = new JTextField(String.valueOf(Controlador.MUTACION_DEFECTO));
		textoTolerancia = new JTextField(String.valueOf(Controlador.TOLERANCIA_DEFECTO));
		textoElitismo = new JTextField(String.valueOf(Controlador.ELITISMO_DEFECTO));
		textoElitismo.setEnabled(false);
		
		textoNumGeneraciones.addKeyListener(teclado);
		textoTamPoblacion.addKeyListener(teclado);
		textoProbCruce.addKeyListener(teclado);
		textoProbMutacion.addKeyListener(teclado);
		textoTolerancia.addKeyListener(teclado);
		textoElitismo.addKeyListener(teclado);
		
		panelPrincipal.add(labelFuncion);
		panelPrincipal.add(comboFunciones);
		
		panelPrincipal.add(labelNumGenes);
		panelPrincipal.add(comboNumGenes);
		
		panelPrincipal.add(labelNumGeneraciones);
		panelPrincipal.add(textoNumGeneraciones);
		
		panelPrincipal.add(labelTamPoblacion);
		panelPrincipal.add(textoTamPoblacion);
		
		panelPrincipal.add(labelProbCruce);
		panelPrincipal.add(textoProbCruce);
		
		panelPrincipal.add(labelProbMutacion);
		panelPrincipal.add(textoProbMutacion);
		
		panelPrincipal.add(labelTolerancia);
		panelPrincipal.add(textoTolerancia);
		
		
		panelElitismo.add(checkElitismo);
		panelElitismo.add(textoElitismo);
		panelPrincipal.add(panelElitismo);
		panelPrincipal.add(botonEmpezar);
		
		OyenteEjecutar oyenteBoton = new OyenteEjecutar();
		botonEmpezar.addActionListener(oyenteBoton);
		
		OyenteComboFuncion oyenteFuncion = new OyenteComboFuncion();
		comboFunciones.addActionListener(oyenteFuncion);
		
		OyenteElitismo oyenteCheckElitismo = new OyenteElitismo();
		checkElitismo.addActionListener(oyenteCheckElitismo);
		
		this.setContentPane(panelPrincipal);
		this.setSize(400, 250);
		this.setVisible(true);
		this.setTitle("Algoritmo genético simple");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class OyenteEjecutar implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ejecutarAlgoritmo();
		}	
	}
	
	class OyenteComboFuncion implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			comboNumGenes.setEnabled(false);
			
			switch (comboFunciones.getSelectedIndex()){
			case 0: {comboNumGenes.setSelectedIndex(0);
					break;}
			case 1: {comboNumGenes.setSelectedIndex(1);
					break;}
			case 2: {comboNumGenes.setSelectedIndex(0);
					break;}
			case 3: {comboNumGenes.setSelectedIndex(1);
					break;}
			default:
				comboNumGenes.setEnabled(true);
			}
		}
	}
	
	class OyenteElitismo implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			JCheckBox elitismo = (JCheckBox)arg0.getSource();
			if(elitismo.isSelected()) textoElitismo.setEnabled(true);
			else textoElitismo.setEnabled(false);
		}
		
	}
	
	class OyenteTecla implements KeyListener{

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10){
				ejecutarAlgoritmo();
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	private void ejecutarAlgoritmo(){
		try{
			int funcion = comboFunciones.getSelectedIndex();
			int numGenes = (Integer)comboNumGenes.getSelectedItem();
			int numGeneraciones = Integer.parseInt(textoNumGeneraciones.getText());
			int tamPoblacion = Integer.parseInt(textoTamPoblacion.getText());
			double probCruce = Double.parseDouble(textoProbCruce.getText());
			double probMutacion = Double.parseDouble(textoProbMutacion.getText());
			double tolerancia = Double.parseDouble(textoTolerancia.getText());
			boolean elitismo = checkElitismo.isSelected();
			controlador.recogerDatosGUI(funcion, numGenes, numGeneraciones, tamPoblacion, probCruce, probMutacion, tolerancia, elitismo);
		}catch(NumberFormatException exception){
			JOptionPane.showMessageDialog(null, "Se han introducido mal los datos");
		}
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

}
