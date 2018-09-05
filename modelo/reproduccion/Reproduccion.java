package modelo.reproduccion;

import modelo.cromosoma.Cromosoma;
import modelo.cruce.Cruce;

public interface Reproduccion {
	public int reproduccion(int individuos, double prob_cruce, Cruce metodoCruce, Cromosoma[] poblacion, String fichero);
}
