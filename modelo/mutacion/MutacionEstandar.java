package modelo.mutacion;

import java.util.Random;
import modelo.cromosoma.Cromosoma;

public class MutacionEstandar implements Mutacion{
	
	/*Mutaci�n a nivel de la poblaci�n*/
	public int mutacion(int individuos, Cromosoma[] poblacion, double prob_mutacion){
		boolean mutado;
		int tM = 0;
		
		//Por cada individuo de la poblaci�n...
		for (int i = 0; i < individuos; i++) {
			//...vemos si mutamos su cromosoma
			mutado = mutacionCromosoma(poblacion[i], prob_mutacion);
			if (mutado)		
				poblacion[i].setAptitud(poblacion[i].calcularAptitud());
				tM++;
		}
		return tM;
	}
	
	/*Mutaci�n a nivel del cromosma*/
	private boolean mutacionCromosoma(Cromosoma indv, double prob_mut){
		boolean mutado = false;
		Random r = new Random();
		double prob;
		
		//Para cada gen del cromsoma...
		for (int i = 0; i < indv.getNumEdificios(); i++) {
			
			prob = r.nextDouble();
			//...si la probabilidad de mutaci�n es mayor 
			//que un valor aleatorio mutamos ese alelo 
			//(invertimos su valor booleano)
			if (prob < prob_mut) {
				//indv.invertirAlelo(i);
				mutado = true;
			}
		}
		
		return mutado;
		
	}

}
