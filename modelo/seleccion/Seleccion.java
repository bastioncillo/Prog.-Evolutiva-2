package modelo.seleccion;

import modelo.cromosoma.Cromosoma;

public interface Seleccion {
	public void selecciona(Cromosoma[] poblacion, double prob_cruce);
}
