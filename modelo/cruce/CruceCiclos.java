package modelo.cruce;

import modelo.cromosoma.Cromosoma;

public class CruceCiclos implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
				
		//Inicializo todos los alelos de los hijos a 0:
			//Los que al final de la primera fase sigan valiendo 0 serán reemplazados 
			//por el alelo correspondiente del otro padre de manera automática en la
			//segunda fase
		for(int j = 0; j < padre1.getNumEdificios(); j++){
			hijo1.setEdificio(j, 0);
			hijo2.setEdificio(j, 0);
		}
		
		cruceCiclos(hijo1, padre1, padre2);
		cruceCiclos(hijo2, padre2, padre1);
				
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}	
	
	private void cruceCiclos(Cromosoma hijo, Cromosoma padreX, Cromosoma padreY){
		boolean cC = false; //ciclo cerrado: true | ciclo abierto: false
		hijo.setEdificio(0, padreX.getEdificio(0));
		int pos = 0;
				
		//1ªFase:
		int i;
		boolean encontrado;
		while(!cC){
			i = 0;
			encontrado = false;
			if(padreY.getEdificio(pos) != hijo.getEdificio(0)){
				while(!encontrado && i < padreX.getNumEdificios()){
					if(padreX.getEdificio(i) == padreY.getEdificio(pos)){
						hijo.setEdificio(i, padreX.getEdificio(i));
						encontrado = true;
						pos = i;
					}else
						i++;
				}
			}else
				cC = true;
		}
		//2ªFase:
		for(int j = 0; j < padreY.getNumEdificios(); j++){
			if(hijo.getEdificio(j) == 0)
				hijo.setEdificio(j, padreY.getEdificio(j));
		}
	}
}
