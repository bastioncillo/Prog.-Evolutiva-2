package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CruceCodOrdinal implements Cruce{
	
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		//Creo la lista dinámica
		int [] listaDin = new int[padre1.getNumEdificios()];
				
		int [] auxH1 = new int[padre1.getNumEdificios()];
		int [] auxH2 = new int[padre2.getNumEdificios()];
		
		cruceCodOrdF1(listaDin, auxH1, padre1);	
		cruceCodOrdF1(listaDin, auxH2, padre2);
		
		Random r = new Random();
		// Buscar punto de cruce aletorio
		int puntoCruce = r.nextInt(padre1.getNumEdificios());
	
		//Para los valores del 0 al punto de cruce,
		//guardamos en cada hijo esa parte de la variable auxH correspondiente
		for (int i = 0; i < puntoCruce; i++) {
			hijo1.setEdificio(i, auxH1[i]);
			hijo2.setEdificio(i, auxH2[i]);
		}
		
		//Ahora guardamos en cada hijo la parte desde el punto de cruce al final, 
		//de la variable auxH inversa a la del primer bucle
		for (int i = puntoCruce; i < padre1.getNumEdificios(); i++) {
			hijo1.setEdificio(i, auxH2[i]);
			hijo2.setEdificio(i, auxH1[i]);
		}

		//Ahora hemos de tomar los valores obtenidos para los hijos en la fase
		//anterior y sustituirlos por el valor correspondiente de la lista dinámica		
		cruceCodOrdF2(listaDin, hijo1, padre1);
		cruceCodOrdF2(listaDin, hijo2, padre2);
	
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
	
	private void cruceCodOrdF1(int [] list, int [] a1, Cromosoma padre){
		//Límite de la lista para cuando vayamos reduciéndola
		int t = padre.getNumEdificios();
		//Lista dinámica
		for(int i = 0; i < t; i++)
			list[i] = i+1;
		
		int j;
		boolean encontrado;
		for(int i = 0; i < padre.getNumEdificios(); i++){
			j = 0;
			encontrado = false;
			while(j < t && !encontrado){
				if(padre.getEdificio(i) == list[j]){
					encontrado = true;
					a1[i] = j+1;
					unoALaIzq(list, t, j);
					t--;
				}else
					j++;
			}
		}
	}

	private void cruceCodOrdF2(int [] list, Cromosoma hijo, Cromosoma padre){
		//Recargamos la lista
		int t = padre.getNumEdificios();
		for(int i = 0; i < t; i++)
			list[i] = i+1;
		
		int p;
		for(int i = 0; i < hijo.getNumEdificios(); i++){
			p = hijo.getEdificio(i); 
			hijo.setEdificio(i, list[p-1]);
			unoALaIzq(list, t, p-1);
			t--;
		}
	}
	
	private void unoALaIzq(int[] l, int tope, int p){
		for(int i = p; i < tope-1; i++)
			l[i] = l[i+1];
	}
}
