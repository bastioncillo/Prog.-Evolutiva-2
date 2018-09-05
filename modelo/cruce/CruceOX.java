package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CruceOX implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		Random r = new Random();
	
		// Buscar puntos de cruce aletorio
		int puntoCruce1 = r.nextInt(padre1.getNumEdificios()-2);
		int puntoCruce2;
		do{
			puntoCruce2 = r.nextInt(padre1.getNumEdificios()-1);
		}while(puntoCruce1 >= puntoCruce2);
		
		//Para los valores del primer punto de cruce al segundo punto de cruce,
		//guardamos en cada hijo esa parte del padre opuesto
		for (int j = puntoCruce1; j < puntoCruce2; j++) {
			hijo1.setEdificio(j, padre2.getEdificio(j));
			hijo2.setEdificio(j, padre1.getEdificio(j));
		}
		
		//Ahora cogemos los elementos de un padre que se han quedado fuera del
		//intervalo de los puntos de corte, y los vamos colocando seguidos en el
		//gen mientras no haya conflicto con los valores dentro del intervalo de
		//los puntos de cruce
		
		//Variables auxiliares:
		//j: necesaria para recorrer los arrays
		int j = puntoCruce2;
		//pos: necesaria para saber donde colocar en la vbleHijo
		int pos = puntoCruce2; 
		//p_con: necesaria para guardar las posiciones con valores conflictivos
		//entre padre e hijo
		int [] pos_conflic = new int[puntoCruce2-puntoCruce1];
		int p = 0; //<- para moverse por p_con[]
		//k: necesaria para moverse por el intervalo de los puntos de cruce
		int k = puntoCruce1;
		//conflicto: necesaria para saber si una de los datos que vas a colocar fuera del
				//intervalo choca con uno de los datos del mismo
		boolean conflicto = false;
		//aux: necesaria para la fase 2
		int aux;
		
		/******************************* HIJO 1 ********************************/
		//-------------------------Fase 1:---------------------------
		while(j != puntoCruce1){
			k = puntoCruce1;
			conflicto = false;				
			while(k < puntoCruce2 && !conflicto){
				if(padre1.getEdificio(j) == hijo1.getEdificio(k)){
					conflicto = true;
					//posición del intervalo de los puntos de cruce
					//donde hay conflicto:
					pos_conflic[p] = k; 
					p++;
				}else
					k++;
			}
			if(!conflicto){
				hijo1.setEdificio(pos, padre1.getEdificio(j));
				pos = (pos+1)%padre1.getNumEdificios();
			}
			j = (j+1)%padre1.getNumEdificios();
		}
		//------------------------Fase 2:----------------------------
		//Ordeno de menor a mayor los datos del array pos_conflic:
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
		//vblePadre indicados por pos_conflic[]
		p = 0;
		while(pos != puntoCruce1){
			hijo1.setEdificio(pos, padre1.getEdificio(pos_conflic[p]));
			k = puntoCruce1;
			while(k < puntoCruce2){
				if(hijo1.getEdificio(pos) == hijo1.getEdificio(k)){
					hijo1.setEdificio(pos, padre1.getEdificio(k));
					k = puntoCruce1;
				}else
					k++;
			}
			pos = (pos+1)%padre1.getNumEdificios();
			p++;
		}
		
		/****************************** HIJO 2 ********************************/
		pos = puntoCruce2; //<- reseteo el valor de pos
		p = 0; //<- reseteo el valor de p
		j = puntoCruce2; //<- reseteo el valor de j
		//-------------------------Fase 1:-------------------------------
		while(j != puntoCruce1){
			k = puntoCruce1;
			conflicto = false;
			while(k < puntoCruce2 && !conflicto){
				if(padre2.getEdificio(j) == hijo2.getEdificio(k)){
					conflicto = true;
					//posición del intervalo de los puntos de cruce
					//donde hay conflicto:
					pos_conflic[p] = k; 
					p++;
				}else
					k++;
			}
			if(!conflicto){
				hijo2.setEdificio(pos, padre2.getEdificio(j));
				pos = (pos+1)%padre2.getNumEdificios();
			}
			j = (j+1)%padre2.getNumEdificios();
		}
		//------------------------Fase 2:--------------------------------
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
		while(pos != puntoCruce1){
			hijo2.setEdificio(pos, padre2.getEdificio(pos_conflic[p]));
			k = puntoCruce1;
			while(k < puntoCruce2){
				if(hijo2.getEdificio(pos) == hijo2.getEdificio(k)){
					hijo2.setEdificio(pos, padre2.getEdificio(k));
					k = puntoCruce1;
				}else
					k++;
			}
			pos = (pos+1)%padre2.getNumEdificios();
			p++;
		}
		
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
}
