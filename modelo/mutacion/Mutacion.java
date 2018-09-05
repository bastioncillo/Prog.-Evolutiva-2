package modelo.mutacion;

import modelo.cromosoma.Cromosoma;

public interface Mutacion {
	public int mutacion(int i, Cromosoma[] pob, double prob_mut);
}
