package modelo.seleccion;

import modelo.cromosoma.Cromosoma;

public class SeleccionEstocUniv implements Seleccion{

	@Override
	public void selecciona(Cromosoma[] poblacion, double prob_cruce) {
		/*  Similar al muestreo proporcional(ruleta) pero ahora se genera un
			único número aleatorio simple r y a partir de él se calculan
			los restantes.
			
			Los individuos se mapean en segmentos continuos cuyo
			tamaño es el de su aptitud.
			
			Se colocan tantas marcas espaciadas por igual como
			individuos queremos seleccionar (N)
			
			La distancia entre las marcas es 1/N
			
			La posición de la primera marca se obtiene a partir de un
			número aleatorio entre 0 y 1/N.
		*/
		
		int individuos = poblacion.length;
		
		Cromosoma[] nuevaPoblacion = new Cromosoma[individuos];
		int[] seleccionados = new int[individuos]; //individuals
				
		double trial;
		trial = Math.random()*(1/individuos);
		
		int superviviente;
		
		for (int i = 0; i < individuos; i++) {
			
			/************** Modificado para revisión ********************/
			superviviente = 0;
			while (superviviente < individuos && trial > poblacion[superviviente].getPunt_acum()){
				superviviente++;
			}
			/************************************************************/
							
			trial += (1/individuos);
			seleccionados[i] = superviviente;
		}
				
		// Crear la poblacion auxiliar de supervivientes
		for (int i = 0; i < individuos; i++){
			nuevaPoblacion[i] = poblacion[seleccionados[i]].clone();
		}
			
				
		for (int i = 0; i < individuos; i++)
			poblacion[i] = nuevaPoblacion[i];	// REEMPLAZO INMEDIATO	
	}

}
