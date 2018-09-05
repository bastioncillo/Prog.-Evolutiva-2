package modelo.seleccion;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class SeleccionTorneoProb implements Seleccion{

	@Override
	public void selecciona(Cromosoma[] poblacion, double prob_cruce) {
		/*
		 * Se diferencia en el paso de selección del ganador del
		 * torneo. En vez de escoger siempre el mejor se genera un 
		 * número aleatorio del intervalo [0..1], si es mayor que un
		 * parámetro p (fijado para todo el proceso evolutivo) se
		 * escoge el individuo más alto y en caso contrario el menos
		 * apto. Generalmente p toma valores en el rango (0.5,1)
		 * 
		 */
		int individuos = poblacion.length;
		
		Cromosoma[] nuevaPoblacion = new Cromosoma[individuos];
		int[] seleccionados = new int[individuos]; 
				
		Random r = new Random();
		
		for (int i = 0; i < individuos; i++) {
			
			/************* Modificado para revisión *****************/
			int individuo1 = r.nextInt(individuos);
			int individuo2 = r.nextInt(individuos);
			
			if(r.nextDouble() > prob_cruce){
				if (poblacion[individuo1].getAptitud() >= poblacion[individuo2].getAptitud())
					seleccionados[i] = individuo1;
				else
					seleccionados[i] = individuo2;
			}else{
				if (poblacion[individuo1].getAptitud() < poblacion[individuo2].getAptitud())
					seleccionados[i] = individuo1;
				else
					seleccionados[i] = individuo2;
			}
			/******************************************************/
			
		}
		
		// Crear la poblacion auxiliar de supervivientes
		for (int i = 0; i < individuos; i++){
			nuevaPoblacion[i] = poblacion[seleccionados[i]].clone();					
		}
			
						
		for (int i = 0; i < individuos; i++)
			poblacion[i] = nuevaPoblacion[i];	// REEMPLAZO INMEDIATO
	}
}
