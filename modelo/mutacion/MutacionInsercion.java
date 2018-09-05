package modelo.mutacion;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class MutacionInsercion implements Mutacion{
	@Override
	public int mutacion(int individuos, Cromosoma[] poblacion, double prob_mutacion) {
		// TODO Auto-generated method stub
		int tM = 0;
		//Por cada individuo de la población...
		for (int i = 0; i < individuos; i++) {
			//...vemos si mutamos su cromosoma
			mutacionCromosoma(poblacion[i], prob_mutacion);
			tM++;
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
		
		int muv = indv.getEdificio(q);
		
		while( j < q ){
			
			indv.setEdificio(q, indv.getEdificio(q-1));
	
			q --;
			
		}
		
		indv.setEdificio(q, muv);
	}
}
