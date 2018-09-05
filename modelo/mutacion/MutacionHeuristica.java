package modelo.mutacion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import modelo.cromosoma.Cromosoma;

public class MutacionHeuristica implements Mutacion {
	int n = 3;
	int pos[] = new int [n];
	String[] tmp;
	Map<Integer, Integer> pAux;
	Cromosoma indv;
	Cromosoma mejor;
	
	public int mutacion(int individuos, Cromosoma[] poblacion, double prob_mutacion) {
		// TODO Auto-generated method stub
		int tM = 0;
		Cromosoma mutado;
		
		//Por cada individuo de la población...
		for (int i = 0; i < individuos; i++) {
			//...vemos si mutamos su cromosoma
			mutacionCromosoma(poblacion[i], prob_mutacion, poblacion.length);
			tM++;
			mutado = mejor.clone();	
			poblacion[i] = mutado;
		}
		return tM;
	}
	
	private void mutacionCromosoma(Cromosoma indvX, double prob_mut, int individuos){
		Random r = new Random();
		
		pAux = new HashMap<Integer, Integer>();
		indv = indvX;
		
		int azar[] = new int [n];
		int e = 0;
		
		for(int i=0; i < n; i++){
			e = r.nextInt(indv.getNumEdificios());
			if(!pAux.containsKey(e)){
				//posicion, valor
				pAux.put(e, indv.getEdificio(e));
				azar[i] = indv.getEdificio(e);
				pos[i] = e;
			}else{
				i--;
			}
		}
		
		mejor = new Cromosoma();
		mejor = indv.clone();
		
		String [] elem = new String [n];
		for(int i=0; i < n; i++){
			elem[i] = Integer.toString(azar[i]);
		}
		Perm2(elem, "", n, n);
		
	}
		
	private  void Perm2(String[] elem, String act, int n, int r) {
        if (n == 0) {
            //System.out.println(act);
            tmp = act.split(" ");
            selecInd();
        } else {
            for (int i = 0; i < r; i++) {
                if (!act.contains(elem[i])) { // Controla que no haya repeticiones
                    Perm2(elem, act + elem[i] + " ", n - 1, r);
                }
            }
        }
    }
	
	private void selecInd(){
		int azar[] = new int [n];
		
		for(int i=0; i < n; i++){
			azar[i] = Integer.parseInt(tmp[i]);
		}
		
		Cromosoma indv1 = new Cromosoma();
		
		indv1 = indv.clone();
		
		Iterator<Integer> it = pAux.keySet().iterator();
		int w = 0;
		while(it.hasNext()){
		  Integer key = it.next();
		  indv1.setEdificio(key, azar[w]);
		  w++;
		}
		indv1.setAptitud(0);
		indv1.setAptitud(indv1.calcularAptitud());

		if(indv1.getAptitud() < mejor.getAptitud()){
			mejor = indv1.clone();
		}
		
	}	
}
