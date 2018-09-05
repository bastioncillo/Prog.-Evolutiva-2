package modelo.cruce;

import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class CruceOXordPrior implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		Random r = new Random();
			
		// Crear posiciones de cruce aletorio			
		int npc = 4;
		int [] pos_cruce = new int[npc];
		for(int j = 0; j < npc; j++){
			int pca = r.nextInt(padre1.getNumEdificios());
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
		
		cruceOX(npc, pos_cruce, padre1, padre2, hijo1);
		cruceOX(npc, pos_cruce, padre2, padre1, hijo2);
		
 		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
	
	private void cruceOX(int npc, int [] pos_cruce, Cromosoma padreX, Cromosoma padreY, Cromosoma hijo){
		int [] numsAbuscar = new int[npc];
		
		for(int j = 0; j < npc; j++){
			numsAbuscar[j] = padreX.getEdificio(pos_cruce[j]);
		}
		
		int [] posAsalvar = new int[npc];
		int p  = 0;
		
		for(int j = 0; j < padreY.getNumEdificios(); j++){
			if(padreY.getEdificio(j) == numsAbuscar[npc - npc] || 
					padreY.getEdificio(j) == numsAbuscar[npc - (npc-1)] ||
					padreY.getEdificio(j) == numsAbuscar[npc - (npc-2)] ||
					padreY.getEdificio(j) == numsAbuscar[npc - (npc-3)]){
				posAsalvar[p] = j;
				p++;
			}
		}
		
		p = 0;
		for(int j = 0; j < padreX.getNumEdificios(); j++){
			if(j != posAsalvar[npc-npc] && j != posAsalvar[npc-(npc-1)] &&
					j != posAsalvar[npc-(npc-2)] && j != posAsalvar[npc-(npc-3)])
				hijo.setEdificio(j, padreY.getEdificio(j));
			else{
				hijo.setEdificio(j, numsAbuscar[p]);
				p++;
			}	
		}
	}
}
