package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.cromosomas.Cromosoma;

public class Grafica2D extends JFrame{

	private static final long serialVersionUID = -3885066388990981332L;
	private JPanel panelContenido;
	private JLabel aptitudLabel;
	
	private Controlador controlador;
	
	public Grafica2D(){}
	
	public void generarGrafica(Cromosoma elMejor, double[] mejores, double[] mejoresParciales, double[] medias, int numeroGeneraciones){
		panelContenido = new JPanel(new BorderLayout());
		// define your data
		double[] generaciones = new double[numeroGeneraciones];
		for (int i = 0; i< numeroGeneraciones; i++){
			generaciones[i] = i+1;
		}
		
		
			// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		plot.setAxisLabel(0, "Generacion");
		
		plot.getAxis(0).setLabelPosition(0.5, -0.10);
		// define the legend position
		plot.addLegend("SOUTH");

		// add a line plot to the PlotPanel
		plot.addLinePlot("Mejor Aptitud en cada generacion", generaciones, mejoresParciales);
		plot.addLinePlot("Media de aptitudes", generaciones, medias);
		plot.addLinePlot("Mejor Aptitud hasta el momento", generaciones, mejores);
		
	
		
		String textoAptitud = "Aptitud mejor individuo: "+elMejor.getAptitud();
		
		aptitudLabel = new JLabel(textoAptitud, JLabel.CENTER);
		
		JPanel panelilloLabels = new JPanel (new GridLayout());
		panelilloLabels.add(aptitudLabel);
		//panelilloLabels.add(botonGenotipo);		
		
		panelContenido.add(plot, BorderLayout.CENTER);
		panelContenido.add(panelilloLabels,BorderLayout.SOUTH);
		
		// put the PlotPanel in a JFrame like a JPanel
		String titulo = "Grafico de resultados ";
		if (controlador.getElitismo()) titulo += "con "; else titulo+= "sin ";
		titulo+="elitismo";
		this.setTitle(titulo);
		this.setSize(600, 600);
		this.setContentPane(panelContenido);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void setControlador (Controlador controlador){
		this.controlador = controlador;
	}
}
