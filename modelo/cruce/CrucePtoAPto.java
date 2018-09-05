package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CrucePtoAPto implements Cruce{
	
	// Cruce de este cromosoma con otro para producir dos hijos
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		Random r = new Random();
		
		// Buscar punto de cruce aletorio
		int puntoCruce = r.nextInt(padre1.getNumEdificios());
		
		//Para los valores del 0 al punto de cruce,
		//guardamos en cada hijo esa parte del padre correspondiente
		for (int j = 0; j < puntoCruce; j++) {
			hijo1.setEdificio(j, padre1.getEdificio(j));
			hijo2.setEdificio(j, padre2.getEdificio(j));
		}
		
		//Ahora guardamos en cada hijo la parte desde el punto de cruce al final, 
		//del padre inverso al del primer bucle
		for (int j = puntoCruce; j < padre1.getNumEdificios(); j++) {
			hijo1.setEdificio(j, padre2.getEdificio(j));
			hijo2.setEdificio(j, padre1.getEdificio(j));
		}
		
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
}
