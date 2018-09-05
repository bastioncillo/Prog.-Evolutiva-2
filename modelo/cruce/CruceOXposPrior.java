package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CruceOXposPrior implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		Random r = new Random();
		
		// Crear posiciones de cruce aletorio			
		int npc = 4;
		int [] pos_cruce = new int[npc];
		for(int j = 0; j < npc; j++){
			int pca = r.nextInt(padre1.getNumEdificios()-1);
			boolean repetido = false;
			int k = 0;
			while(k < j && !repetido){
				if(pos_cruce[k] == pca)
					repetido = true;
				else
					k++;
			}
			if(repetido)
				j--;
			else
				pos_cruce[j] = pca;
		}
		
		//Ordeno de menor a mayor las posiciones de cruce
		int aux;
		for(int l = 0; l < npc; l++){
			for(int m = 0; m < l; m++){
				if(pos_cruce[l] < pos_cruce[m]){
					aux = pos_cruce[m];
					pos_cruce[m] = pos_cruce[l];
					pos_cruce[l] = aux;
				}
			}
		}
		
		//Intercambio de los alelos para las posiciones de cruce señaladas
		//hijo 1:
		for(int j = 0; j < npc; j++){
			hijo1.setEdificio(pos_cruce[j], padre2.getEdificio(pos_cruce[j]));
		}
		//hijo 2:
		for(int j = 0; j < npc; j++){
			hijo2.setEdificio(pos_cruce[j], padre1.getEdificio(pos_cruce[j]));
		}
		
		//Variables auxiliares
		int pos = pos_cruce[3]+1, j = pos_cruce[3]+1, k = 0, p = 0;
		int[] pos_conflic = new int[npc];
		boolean repetido = false;
		
		/**************************** HIJO 1 ********************************/
		//--------------------------- Fase 1: ------------------------------//
		while(j != pos_cruce[3]){
			k = 0;
			repetido = false;
			//Si la posición que vayamos a mirar para colocar el alelo coincide con alguna
			//de las posiciones de cruce no hacemos nada, ya que ya están cruzadas
			if(j != pos_cruce[0] && j != pos_cruce[1] && 
					j != pos_cruce[2] && j != pos_cruce[3]){
				while(!repetido && k < npc){
					if(padre1.getEdificio(j) == hijo1.getEdificio(pos_cruce[k])){
						repetido = true;
						//guardamos en el array de posiciones conflictivas aquellas
						//para las que se repitan valores
						pos_conflic[p] = pos_cruce[k];
						p++;
					}
					else
						k++;
				}
				if(!repetido){
					hijo1.setEdificio(pos, padre1.getEdificio(j));
					//calculamos el nuevo valor de pos tantas veces como sean necesarias 
					//para no caer en una de las posiciones de cruce y acabar sobreescribiéndola
					do{
						pos = (pos+1)%padre1.getNumEdificios();
					}while(pos == pos_cruce[0] || pos == pos_cruce[1] || 
							pos == pos_cruce[2]);
				}
			}
			j = (j+1)%padre1.getNumEdificios();
		}
		//--------------------------- Fase 2: ------------------------------//
		//Ordeno de menor a mayor los datos del array p_con:
		for(int l = 0; l < p; l++){
			for(int m = 0; m < l; m++){
				if(pos_conflic[l] < pos_conflic[m]){
					aux = pos_conflic[m];
					pos_conflic[m] = pos_conflic[l];
					pos_conflic[l] = aux;
				}
			}
		}
		//Ahora relleno los huecos del array vbleHijo con los datos del intervalo de la
		//vblePadre indicados por p_con[]
		p = 0;
		while(pos != pos_cruce[3]){
			hijo1.setEdificio(pos, padre1.getEdificio(pos_conflic[p]));
			k = 0;
			while(k < npc){
				if(hijo1.getEdificio(pos) == hijo1.getEdificio(pos_cruce[k])){
					hijo1.setEdificio(pos, padre1.getEdificio(pos_cruce[k]));
					k = 0;
				}else
					k++;
			}
			do{
				pos = (pos+1)%padre1.getNumEdificios();
			}while(pos == pos_cruce[0] || pos == pos_cruce[1] || 
					pos == pos_cruce[2]);
			p++;
		}
		
		/**************************** HIJO 2 ********************************/
		j = pos_cruce[3]+1;
		p = 0;
		pos = pos_cruce[3]+1;
		//--------------------------- Fase 1: ------------------------------//
		while(j != pos_cruce[3]){
			k = 0;
			repetido = false;
			//Si la posición que vayamos a mirar para colocar el alelo coincide con alguna
			//de las posiciones de cruce no hacemos nada, ya que ya están cruzadas
			if(j != pos_cruce[0] && j != pos_cruce[1] && 
					j != pos_cruce[2] && j != pos_cruce[3]){
				while(!repetido && k < npc){
					if(padre2.getEdificio(j) == hijo2.getEdificio(pos_cruce[k])){
						repetido = true;
						//guardamos en el array de posiciones conflictivas aquellas
						//para las que se repitan valores
						pos_conflic[p] = pos_cruce[k];
						p++;
					}
					else
						k++;
				}
				if(!repetido){
					hijo2.setEdificio(pos, padre2.getEdificio(j));
					//calculamos el nuevo valor de pos tantas veces como sean necesarias 
					//para no caer en una de las posiciones de cruce y acabar sobreescribiéndola
					do{
						pos = (pos+1)%padre2.getNumEdificios();
					}while(pos == pos_cruce[0] || pos == pos_cruce[1] || 
							pos == pos_cruce[2]);
				}
			}
			j = (j+1)%padre2.getNumEdificios();
		}
		//--------------------------- Fase 2: ------------------------------//
		//Ordeno de menor a mayor los datos del array p_con:
		for(int l = 0; l < p; l++){
			for(int m = 0; m < l; m++){
				if(pos_conflic[l] < pos_conflic[m]){
					aux = pos_conflic[m];
					pos_conflic[m] = pos_conflic[l];
					pos_conflic[l] = aux;
				}
			}
		}
		//Ahora relleno los huecos del array vbleHijo con los datos del intervalo de la
		//vblePadre indicados por p_con[]
		p = 0;
		while(pos != pos_cruce[3]){
			hijo2.setEdificio(pos, padre2.getEdificio(pos_conflic[p]));
			k = 0;
			while(k < npc){
				if(hijo2.getEdificio(pos) == hijo2.getEdificio(pos_cruce[k])){
					hijo2.setEdificio(pos, padre2.getEdificio(pos_cruce[k]));
					k = 0;
				}else
					k++;
			}
			do{
				pos = (pos+1)%padre2.getNumEdificios();
			}while(pos == pos_cruce[0] || pos == pos_cruce[1] || 
					pos == pos_cruce[2]);
			p++;
		}
				
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
}
