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
	private JButton botonGenotipo;
	private JPanel panelGenotipo;
	
	private Controlador controlador;
	
	public Grafica2D(){
		
	}
	
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
		
		//panelFenotipo = new JPanel( new GridLayout(elMejor.getNumeroGenes()+1,1));
		panelGenotipo = new JPanel( new GridLayout( elMejor.getLongitudGenes()[0]+1,1));
		
		panelGenotipo.add(new JLabel("Genotipo mejor individuo:"),JLabel.CENTER);
		
		/*for (int i = 0; i< elMejor.getNumeroGenes(); i++){
			String textoFenotipos = "";
			if (i == 0) textoFenotipos += "[ ";
			textoFenotipos += elMejor.getFenotipo()[i];
			if (i == elMejor.getNumeroGenes()-1)textoFenotipos += " ] ";
			else textoFenotipos += "; ";
			panelFenotipo.add(new JLabel(textoFenotipos));
			
		}*/
		for (int i = 0; i< elMejor.getLongitudGenes()[0]; i++){
			String textoGenotipos = "";
			if (i == 0) textoGenotipos += "[ ";
			textoGenotipos += elMejor.getFenotipo()[i];
			if (i == elMejor.getLongitudGenes()[0]-1)textoGenotipos += " ] ";
			else textoGenotipos += "; ";
			panelGenotipo.add(new JLabel(textoGenotipos));
		}
		
			
		String textoAptitud = "Aptitud mejor individuo: "+elMejor.getAptitud();
		
		aptitudLabel = new JLabel(textoAptitud, JLabel.CENTER);
		
		
		this.botonGenotipo = new JButton ("Ver genotipo");
		botonGenotipo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Object textoOpciones[] = {"Cerrar"};
				JOptionPane.showOptionDialog(
						null,
						panelGenotipo,
						"Genotipo del mejor individuo",
						JOptionPane.PLAIN_MESSAGE,
						-1,
						null,
						textoOpciones,
						textoOpciones[0]);

				
			}
			
		});
		
		JPanel panelilloLabels = new JPanel (new GridLayout(1,2));
		panelilloLabels.add(aptitudLabel);
		panelilloLabels.add(botonGenotipo);		
		
		panelContenido.add(plot, BorderLayout.CENTER);
		panelContenido.add(panelilloLabels,BorderLayout.SOUTH);
		
		// put the PlotPanel in a JFrame like a JPanel
		String titulo = "Grafico de resultados ejecución básica ";
		if (controlador.getElitismo()) titulo += "con "; else titulo+= "sin ";
		titulo+="elitismo";
		this.setTitle(titulo);
		this.setSize(600, 600);
		this.setContentPane(panelContenido);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void generarGraficaMultiple(String parametro, Cromosoma elMejor, double[] mejoresGlobales, double[] ultimosMejores, double[] iteraciones){
		panelContenido = new JPanel(new BorderLayout());
				
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		plot.setAxisLabel(0, "Iteración");
		
		plot.getAxis(0).setLabelPosition(0.5, -0.10);
		// define the legend position
		plot.addLegend("SOUTH");

		// add a line plot to the PlotPanel
		plot.addLinePlot("Mejor individuo global", iteraciones, mejoresGlobales);
		plot.addLinePlot("Mejor individuo final", iteraciones, ultimosMejores);
		
		//panelFenotipo = new JPanel( new GridLayout(elMejor.getNumeroGenes()+1,1));
		panelGenotipo = new JPanel( new GridLayout( elMejor.getLongitudGenes()[0]+1,1));
		
		panelGenotipo.add(new JLabel("Genotipo mejor individuo:"),JLabel.CENTER);
		
		/*for (int i = 0; i< elMejor.getNumeroGenes(); i++){
			String textoFenotipos = "";
			if (i == 0) textoFenotipos += "[ ";
			textoFenotipos += elMejor.getFenotipo()[i];
			if (i == elMejor.getNumeroGenes()-1)textoFenotipos += " ] ";
			else textoFenotipos += "; ";
			panelFenotipo.add(new JLabel(textoFenotipos));
			
		}*/
		for (int i = 0; i< elMejor.getLongitudGenes()[0]; i++){
			String textoGenotipos = "";
			if (i == 0) textoGenotipos += "[ ";
			textoGenotipos += elMejor.getFenotipo()[i];
			if (i == elMejor.getLongitudGenes()[0]-1)textoGenotipos += " ] ";
			else textoGenotipos += "; ";
			panelGenotipo.add(new JLabel(textoGenotipos));
		}
		
			
		String textoAptitud = "Aptitud mejor individuo: "+elMejor.getAptitud();
		
		aptitudLabel = new JLabel(textoAptitud, JLabel.CENTER);
		
		
		this.botonGenotipo = new JButton ("Ver genotipo");
		botonGenotipo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Object textoOpciones[] = {"Cerrar"};
				JOptionPane.showOptionDialog(
						null,
						panelGenotipo,
						"Genotipo del mejor individuo",
						JOptionPane.PLAIN_MESSAGE,
						-1,
						null,
						textoOpciones,
						textoOpciones[0]);
				}
			
		});
		
		JPanel panelilloLabels = new JPanel (new GridLayout(1,2));
		panelilloLabels.add(aptitudLabel);
		panelilloLabels.add(botonGenotipo);		
		
		panelContenido.add(plot, BorderLayout.CENTER);
		panelContenido.add(panelilloLabels,BorderLayout.SOUTH);
		
		// put the PlotPanel in a JFrame like a JPanel
		String titulo = "Grafico de resultados... parámetro variable: "+ parametro;
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
