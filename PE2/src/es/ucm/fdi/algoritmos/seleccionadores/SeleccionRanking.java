package es.ucm.fdi.algoritmos.seleccionadores;

import java.util.Vector;

import es.ucm.fdi.algoritmos.AGeneticoViajante;
import es.ucm.fdi.cromosomas.Cromosoma;
import es.ucm.fdi.evaluadores.Evaluador;
import es.ucm.fdi.utils.Ordenacion;
import es.ucm.fdi.utils.Poblacion;

public class SeleccionRanking implements Seleccionador {

	public static final double BETA = 1.5;
	
	//precondicion, initPop ya debe estar ordenada!!!!
	public Cromosoma[] seleccion(Cromosoma[] poblacionInicial, Evaluador e) {
		return performRankSelection(poblacionInicial, e);
	}


	//precondicion, initPop ya debe estar ordenada!!!!
	public Cromosoma[] performRankSelection(Cromosoma[] sortedPop, Evaluador e) {
		int tama�oPoblacion = sortedPop.length;
		
		Cromosoma[] futureParents = new Cromosoma[sortedPop.length];
		
		futureParents[0]=sortedPop[0].copiarCromosoma();
		futureParents[1]=sortedPop[1].copiarCromosoma();
		int numOfParents =2;
		
		double entireSegment = sortedPop[sortedPop.length-1].getPuntuacionAcumulada();
		
		while(numOfParents<futureParents.length){
			double x = (double)(Math.random()*entireSegment);
			if(x<=sortedPop[0].getPuntuacionAcumulada()) {
				//First Idividual was Selected 
				futureParents[numOfParents]=sortedPop[0].copiarCromosoma();
				numOfParents++;}
			else
				for(int i=1; i<futureParents.length; i++)
					if(x>sortedPop[i-1].getPuntuacionAcumulada() && x<=sortedPop[i].getPuntuacionAcumulada()){
						//i'th Idividual was Selected 
						futureParents[numOfParents]=sortedPop[i].copiarCromosoma();
						numOfParents++;}
		} 
		return futureParents;
	}

	public Cromosoma[] generarSegmentos(Cromosoma[] poblacion, Evaluador evaluador, boolean mejora) {
		int tama�oPoblacion = poblacion.length;
		double presionSelectiva;
		
		//Si se le pasa true en mejora tenemos que hayar la presion selectiva (BETA) con la poblacion pasada por argumento
		if(mejora){
			double AptMax = Poblacion.mejorPoblacionInstantanea(poblacion, evaluador).getAptitud();
			double AptMed = Poblacion.mediaPoblacionInstantanea(poblacion);
			presionSelectiva = AptMax/AptMed;
			
			//La presion selectiva debe estar entre 1 y 2 por tanto, si supera 2 le asignamos directamente 2.
			if(presionSelectiva > 2)
				presionSelectiva = 2;
		}
		else presionSelectiva = BETA;
		
		poblacion = Ordenacion.sortSelectionIndividuals(poblacion, evaluador);
		
		double puntuacionAcumulada = 0.0;
		
		for(int i=0 ; i < tama�oPoblacion ; i++){
			
			double probOfIth = (double)i/tama�oPoblacion;
			probOfIth = probOfIth*2*(presionSelectiva -1);
			probOfIth = presionSelectiva - probOfIth;
			probOfIth = (double)probOfIth*((double)1/tama�oPoblacion);
			
			poblacion[i].setPuntuacion(probOfIth);
			puntuacionAcumulada += probOfIth;
			poblacion[i].setPuntuacionAcumulada(puntuacionAcumulada);
			
		}
		return poblacion;
	}

}
