package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CrucePMX implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		Random r = new Random();
		
		// Buscar puntos de cruce aletorio
		int puntoCruce1 = r.nextInt(padre1.getNumEdificios()-1);
		int puntoCruce2;
		do{
			puntoCruce2 = r.nextInt(padre1.getNumEdificios());
		}while(puntoCruce1 >= puntoCruce2);
					
		//Para los valores del primer punto de cruce al segundo punto de cruce,
		//guardamos en cada hijo esa parte del padre opuesto
		for (int j = puntoCruce1; j < puntoCruce2; j++) {
			hijo1.setEdificio(j, padre2.getEdificio(j));
			hijo2.setEdificio(j, padre1.getEdificio(j));
		}
		
		//Ahora recorremos los hijos desde el comienzo hasta el primer punto
		//de cruce, y desde el segundo punto de cruce hasta el final, manteniendo
		//las x que no sean iguales que las de la sección intercambiada en el bucle 
		//anterior, y modificando aquellas que sí lo sean
		
		crucePMX(puntoCruce1, puntoCruce2, hijo1, padre1);
		crucePMX(puntoCruce1, puntoCruce2, hijo2, padre2);
		
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos		
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
	
	private void crucePMX(int ptoC1, int ptoC2, Cromosoma hijo, Cromosoma padre){
		boolean conflicto = false;
		int k = ptoC1;
		/*************************** HIJO 1 ********************************/
		//Parte anterior al primer punto de cruce
		for(int j = 0; j < ptoC1; j++){				
			conflicto = false;
			k = ptoC1;
			while(!conflicto && k < ptoC2){
				if(padre.getEdificio(j) == hijo.getEdificio(k))
					conflicto = true;
				else
					k++;
			}
			if(!conflicto)
				hijo.setEdificio(j, padre.getEdificio(j));
			else{
				hijo.setEdificio(j, padre.getEdificio(k));
				k = ptoC1;
				while(k < ptoC2){
					if(hijo.getEdificio(j) == hijo.getEdificio(k)){
						hijo.setEdificio(j, padre.getEdificio(k));
						k = ptoC1;
					}else
						k++;
				}
			}
		}
					
		//Parte posterior al segundo punto de cruce
		for(int j = ptoC2; j < padre.getNumEdificios(); j++){
			conflicto = false;
			k = ptoC1;
			while(!conflicto && k < ptoC2){
				if(padre.getEdificio(j) == hijo.getEdificio(k))
					conflicto = true;
				else
					k++;
			}
			if(!conflicto)
				hijo.setEdificio(j, padre.getEdificio(j));
			else{
				hijo.setEdificio(j, padre.getEdificio(k));
				k = ptoC1;
				while(k < ptoC2){
					if(hijo.getEdificio(j) == hijo.getEdificio(k)){
						hijo.setEdificio(j, padre.getEdificio(k));
						k = ptoC1;
					}
					else
						k++;
				}
			}
		}
	}
}
