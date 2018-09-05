package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import modelo.cromosoma.Cromosoma;
import modelo.cruce.Cruce;
import modelo.mutacion.Mutacion;
import modelo.reproduccion.Reproduccion;
import modelo.seleccion.Seleccion;

public class AlgoritmoGenetico {
	Map<Integer, Integer> pobAux = new HashMap<Integer, Integer>();
	protected Cromosoma[] poblacion;
	protected Cromosoma mejor;
	
	// Parametros
	protected int individuos;
	private int generaciones;
	private double prob_cruce;
	private double prob_mutacion;
	private Seleccion metodoSeleccion;
	private Mutacion metodoMutacion;
	private Cruce metodoCruce;
	private Reproduccion metodoReproduccion;
	private boolean elitismo;
	private int tamElite;
	private String funcion;
	private int numEdificios;
	private int totalCruces;
	private int totalMutaciones;
	private int totalInversiones;
	
	/************ Modificado para revisión ***************/
	private Cromosoma[] elite;
	/*****************************************************/
	
	// Estadisticas
	private double[] mejorGeneracion;
	private double[] mejorGlobal;
	private double[] mediaAptitud;
	
	//Constructor
	public AlgoritmoGenetico (int t, int g, double pc, double pm, Seleccion s, boolean e, String fun, Mutacion m, Cruce c, Reproduccion r) {
		
		generaciones = g;
		prob_cruce = pc;
		prob_mutacion = pm;
		metodoSeleccion = s;
		metodoMutacion = m;
		metodoCruce = c;
		metodoReproduccion = r;
		totalCruces = 0;
		totalMutaciones = 0;
		totalInversiones = 0;
				
		funcion = fun;
		numeroEdi();
		
		if(t > factorial(numEdificios))
			individuos = factorial(numEdificios);
		else
			individuos = t;
		
		poblacion = new Cromosoma[individuos];
		
		elitismo = e;
		Double d = (individuos * 0.2) + 1;
		tamElite = d.intValue(); 
		
		/************ Modificado para revisión ***************/
		if(elitismo){
			elite = new Cromosoma[tamElite];
		}
		
		/*****************************************************/
		
		mejorGeneracion = new double[generaciones];
		mejorGlobal = new double[generaciones];
		mediaAptitud = new double[generaciones];
	}
	
	//Ejecucuión del algoritmo
	public Cromosoma ejecuta() {
		inicializaPoblacion();
		evaluarPoblacion(0);	// Evaluamos la población puntuandoa sus individuos
		
		int t=1;
		
		while (!terminacion(t)) {
			seleccion();	   // Modifica la poblacion (la sobreescribe)
			reproduccion();	   // Modifica a los progenitores	
			inversionEspecial();
			mutacion();		   // Modifica la poblacion a nivel de genes
			inversionEspecial();
			
			if(elitismo){
				reintegrar(); //Si ha habido elitismo, reintegramos la élite en la población
			}
			
			evaluarPoblacion(t); //Reevaluamos
			t++;
		}
		//Mostramos por consola el total de cruces, de mutaciones y de inversiones
		System.out.println("Total de cruces: " + totalCruces);
		System.out.println("Total de mutaciones: " + totalMutaciones);
		System.out.println("Total de inversiones: " + totalInversiones);
		//Devolvemos el mejor Cromosoma
		return mejor;
	}
	
	//Proceso de mutación de la población
	private void mutacion() {
		totalMutaciones += metodoMutacion.mutacion(individuos, poblacion, prob_mutacion);
	}
	
	//Proceso de reproducción de la población
	private void reproduccion() {
		totalCruces += metodoReproduccion.reproduccion(individuos, prob_cruce, metodoCruce, poblacion, funcion);
	}
	
	//Seleccionamos los indivíduos de esta generación que
	//participarán en el proceso de cruce y de mutación
	private void seleccion() {
		if(elitismo){
			seleccionarElite();
		}
		metodoSeleccion.selecciona(poblacion, prob_cruce);
	}
	
	/****************************** Modificado para revisión **********************/
	//Selección de los indivíduos de la población que formarán parte de la élite
	public void seleccionarElite() {
		ordenaMayorAMenor();
		for(int i = 0; i < tamElite; i++){
			this.elite[i] = poblacion[i];
        }
	}
	
	//Ordenar la población de mayor a menor aptitud
	private void ordenaMayorAMenor(){
		
		Cromosoma aux;
		for(int i = 0; i < individuos; i++){
			for(int j = 0; j < i; j++){
				if(poblacion[i].getAptitud() < poblacion[j].getAptitud()){
					aux = poblacion[i];
					poblacion[i] = poblacion[j];
					poblacion[j] = aux;
				}
			}
		}
	}
	
	//Reintegrar la élite en la población
	private void reintegrar(){
		int cont = 0;
		//Sustituímos los peores individuos de la población por la élite 
		for(int i = individuos-1; i > individuos - tamElite; i--){
			poblacion[i] = elite[cont];
			cont++;
		}
	}
	
	/*******************************************************************************/
	
	//Comprobar si el algoritmo ha terminado
	private boolean terminacion(int t) {
		return (t >= generaciones);
	}
	
	//Evaluar población
	private void evaluarPoblacion(int t) {
		double puntAcumulada = 0;
		double sumaAptitudes = 0;
		
		int mejorAptitud = 4000000;
		int posicionMejor = 0;
		
		for (int i = 0; i < individuos; i++) {
			int a = poblacion[i].getAptitud();
			sumaAptitudes += a;
			
			if (a < mejorAptitud) {	
				posicionMejor = i;
				mejorAptitud = a;
			}
			
		}
		
		for (int i = 0; i < individuos; i++) {
			poblacion[i].puntua(sumaAptitudes, puntAcumulada);
			puntAcumulada += poblacion[i].getPuntuacion();
		}
		
		if (mejor == null) {
			mejor = new Cromosoma();
			mejor.setAptitud(0);
			mejor = poblacion[posicionMejor];
		}else{
			if (mejorAptitud < mejor.getAptitud()) {		
				mejor = poblacion[posicionMejor];
			}
		}
		
		mejorGeneracion[t] = poblacion[posicionMejor].getAptitud();
							
		mejorGlobal[t] = mejor.getAptitud();

		mediaAptitud[t] = sumaAptitudes / individuos;
	}

	//Inicializar la población
	private void inicializaPoblacion() {
		for (int i = 0; i < individuos; i++) {
			Cromosoma c = new Cromosoma(numEdificios);

			if(!pobAux.containsKey(c.getId())){
				pobAux.put(c.getId(), 0);
				poblacion[i] = c;
				poblacion[i].setFichero(funcion);
				poblacion[i].setAptitud(poblacion[i].calcularAptitud());
			}else{
				i--;
			}
		}
	}
		
	private int factorial(int n){
		int res;
		if(n == 1 || n == 0)
			return 1;
		res = factorial(n-1) * n;
		return res;
	}
	
	private void numeroEdi(){
		FileReader file;
		try {
			
			file = new FileReader(funcion);
	
			BufferedReader b = new BufferedReader(file);
					
			String[] tempa;
			String delimiter = " ";
			String linea = b.readLine();
							
			tempa = linea.split(delimiter);
					
			//dimension de la matriz
			numEdificios = Integer.parseInt(tempa[0]);
			
			b.close();
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void inversionEspecial(){
		for (int i = 0; i < individuos; i++) {
			//...vemos si mutamos su cromosoma
			invers(poblacion[i], prob_mutacion);
			totalInversiones++;
			poblacion[i].setAptitud(0);
			poblacion[i].setAptitud(poblacion[i].calcularAptitud());
		}
	}
	
	private void invers(Cromosoma indv, double prob_mut){
		
		Random r = new Random();
		boolean out = false;
		int q = 0;
		int j = 0;
		while (!out){
			q = r.nextInt(indv.getNumEdificios());
			j = r.nextInt(indv.getNumEdificios());
			if( j > q){
				int aux = j;
				j = q;
				q = aux;
			}
			if(q-j > 1){
				out = true;
			}
		}
		
		while( j <= q - 1){
			int aux = indv.getEdificio(j);
			indv.setEdificio(j, indv.getEdificio(q));
			indv.setEdificio(q, aux);
			j++;
			q--;
		}
	}
	
	/*************** Getters y Setters *****************/
	public double[] getMejorPorGeneracion() {
		return this.mejorGeneracion;
	}
	
	public double[] getMejorGlobal() {
		return this.mejorGlobal;
	}
	
	public double[] getMediaAptitud() {
		return this.mediaAptitud;
	}
}
