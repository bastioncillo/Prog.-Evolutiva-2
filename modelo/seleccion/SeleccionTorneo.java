package modelo.seleccion;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class SeleccionTorneo implements Seleccion{

	public void selecciona(Cromosoma[] poblacion, double prob_cruce) {
		
		/*
		 * Cada elemento de la muestra se toma eligiendo el mejor
		 * de los individuos de un conjunto de z elementos(2 ó 3)
		 * tomados al azar de la población base.
		 * 
		 * El proceso se repite k veces hasta completar la muestra.
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
			if (poblacion[individuo1].getAptitud() >= poblacion[individuo2].getAptitud())
				seleccionados[i] = individuo1;
			else
				seleccionados[i] = individuo2;
			/*******************************************************/
			
		}
		
		// Crear la poblacion auxiliar de supervivientes
		for (int i = 0; i < individuos; i++){
			nuevaPoblacion[i] = poblacion[seleccionados[i]].clone();
		}
						
		for (int i = 0; i < individuos; i++)
			poblacion[i] = nuevaPoblacion[i];	// REEMPLAZO INMEDIATO
	}
}
