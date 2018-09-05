package modelo.cruce;

import modelo.cromosoma.Cromosoma;

public class CrucePropio implements Cruce{
 
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		/*Este método consiste en intercambiar las posiciones impares de los padres, esto es:
		 * guardamos en las posiciones pares de un hijo el gen del padre correspondiente y las
		 * impares las del padre opuesto. Tras esto debemos comprobar que genes están repetidos
		 * y sustituirlos por aquellos que han sido reemplazados, en la posisción donde 
		 * encontremos el "clon" del número para el cual estamos buscando si hay repetidos*/
				
		int [] reemplazadosH1 = new int[padre1.getNumEdificios()];
		int [] reemplazadosH2 = new int[padre2.getNumEdificios()];
		int p = 0;
		
		for(int i = 0; i < padre1.getNumEdificios(); i++){
			if(i%2 == 1){
				hijo2.setEdificio(i, padre1.getEdificio(i));
				hijo1.setEdificio(i, padre2.getEdificio(i));
				reemplazadosH1[p] = padre1.getEdificio(i);
				reemplazadosH2[p] = padre2.getEdificio(i);
				p++;
			}else{
				hijo1.setEdificio(i, padre1.getEdificio(i));
				hijo2.setEdificio(i, padre2.getEdificio(i));
			}
		}
		
		cruceMio(hijo1, padre1, reemplazadosH1);
		cruceMio(hijo2, padre2, reemplazadosH2);
	
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos		
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
	
	private void cruceMio(Cromosoma hijo, Cromosoma padre, int [] reemplazados){
		int p = 0;
		for(int i = 0; i < padre.getNumEdificios(); i++){
			int j = i+1;
			boolean repetido = false;
			while(!repetido && j < padre.getNumEdificios()){
				if(hijo.getEdificio(i) == hijo.getEdificio(j)){
					repetido = true;
				}else
					j++;
			}
			
			if(repetido){
				hijo.setEdificio(j, reemplazados[p]);
				p++;
				for(int k = 0; k < padre.getNumEdificios(); k++){
					if(k != j && hijo.getEdificio(j) == hijo.getEdificio(k)){
						hijo.setEdificio(j, reemplazados[p]);
						p++;
						k = -1;
					}
				}
			}
		}
	}
}
