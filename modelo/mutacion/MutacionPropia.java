package modelo.mutacion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class MutacionPropia implements Mutacion {

	@Override
	public int mutacion(int individuos, Cromosoma[] poblacion, double prob_mutacion) {
		// TODO Auto-generated method stub
		int tM = 0;
		//Por cada individuo de la poblaci�n...
		for (int i = 0; i < individuos; i++) {
			//...vemos si mutamos su cromosoma
			tM++;
			mutacionCromosoma(poblacion[i], prob_mutacion);
			poblacion[i].setAptitud(0);
			poblacion[i].setAptitud(poblacion[i].calcularAptitud());
		}
		
		return tM;
	}
	
	private void mutacionCromosoma(Cromosoma indv, double prob_mut){
		
		Random r = new Random();
		boolean out = false;
		int q = 0;
		int j = 0;
		while (!out){
			q = r.nextInt(indv.getNumEdificios());
			j = r.nextInt(indv.getNumEdificios());
			if( j > q){
				int aux = j;
				j = q;
				q = aux;
			}
			if(q-j > 1){
				out = true;
			}
		}
		
		int num = q-j;
		
		while( j <= q - 1){
			int aux = indv.getEdificio(j);
			indv.setEdificio(j, indv.getEdificio(q));
			indv.setEdificio(q, aux);
			j++;
			q--;
		}
		
		if( num % 2 == 0) {
			
			q = r.nextInt(indv.getNumEdificios());
			int aux = indv.getEdificio(j);
			indv.setEdificio(j, indv.getEdificio(q));
			indv.setEdificio(q, aux);
			
		}
		
	}
}
