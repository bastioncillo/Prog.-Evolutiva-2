package modelo.cruce;

import modelo.cromosoma.Cromosoma;

public interface Cruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2);
}
