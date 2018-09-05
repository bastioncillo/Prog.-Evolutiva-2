package modelo.cromosoma;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import modelo.edificio.Edificio;
import modelo.utiles.Utiles;

public class Cromosoma {
	private Map<Integer, Integer> ediAux = new HashMap<Integer, Integer>();
	protected Edificio []edificios; //el gen se conoce como ciudad
	protected int aptitud; //función de evaluación fitness adaptación
	protected double puntuacion; //puntuacion relativa(aptitud/suma)
	protected double punt_acum; //puntuacion acumulada para selección
	protected int numEdificios; //número de ciudades del cromosoma
	protected int idCromosoma;
	protected int flujo[][];
	protected int dist[][];
	protected int dim;
	protected String fichero;

	public int calcularAptitud() {
		cargarMatricez();
		
		for(int i=0; i < edificios.length; i++){
			for(int j=0; j < edificios.length; j++){
				if (i != j) {
					int edi1 = edificios[i].getEdificio();
					int edi2 = edificios[j].getEdificio();
					aptitud += (dist[i+1][j+1] * flujo[edi1][edi2]);
				}
			}
		}
		return aptitud;
	}
	
	//Constructor vacio necesario para la mutación heurística
	public Cromosoma(){}
	
	//Contructor
	public Cromosoma(int l){
		numEdificios = l;
		edificios = new Edificio[numEdificios];
		
		for(int i = 0; i < numEdificios; i++){
			Edificio e = new Edificio(numEdificios);
			
			if(!ediAux.containsKey(e.getEdificio())){
				ediAux.put(e.getEdificio(), 0);
				edificios[i] = e;
			}else{
				i--;
			}
			
		}
		idCromosoma = Utiles.juntarEnteros(edificios, l);
	}
	
	//Clonador
	public Cromosoma(Edificio []c, int aptitud, double puntuacion, double punt_acum, int numEdificios, int idCromosoma , String fichero){
		numEdificios = c.length;
		edificios = new Edificio[numEdificios];
		for(int i = 0; i < numEdificios; i++){
			edificios[i] = c[i];
		}
		
		this.aptitud = aptitud;
		this.puntuacion = puntuacion;
		this.punt_acum =  punt_acum;
		this.numEdificios = numEdificios;
		this.idCromosoma = idCromosoma;
		this.fichero =  fichero;
		
	}
	
	public Cromosoma clone() {
		Edificio []ciu = new Edificio[numEdificios];
		for(int i = 0; i < numEdificios; i++){
			ciu[i] = edificios[i].clone();
		}
		return new Cromosoma(ciu, aptitud, puntuacion, punt_acum, numEdificios, idCromosoma , fichero);
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Distribucion: ");
		for(int i = 0; i < numEdificios; i++){
			s.append("|" + edificios[i].getEdificio());
		}
		s.append("| Aptitud = " + aptitud);
		return new String(s);
	}
	
	public void cargarMatricez(){
		//FileInputStream fileInput;
		FileReader file;
		try {
			//fileInput = new FileInputStream("C:/Users/Pedro/workspace/Evolutiva2_1/datos12.dat");
			//BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
					
			file = new FileReader(fichero);
	
			BufferedReader b = new BufferedReader(file);
					
			String[] tempa;
			String delimiter = " ";
			String linea = b.readLine();
							
			tempa = linea.split(delimiter);
					
			//dimension de la matriz
			dim = Integer.parseInt(tempa[0]);
			dist = new int [dim+1][dim+1];
			flujo = new int [dim+1][dim+1];
					
			boolean sig =false;
			linea = b.readLine();
		
			int f=1;
					
			while((linea=b.readLine())!=null){
						
				tempa = linea.split(delimiter);
				if(tempa.length > 1){
					int t = 0;
					for(int c=1; c < tempa.length+1; c++){

						if(!sig){
							dist[f][c] = Integer.parseInt(tempa[t]);
						}else{
							flujo[f][c] = Integer.parseInt(tempa[t]);
						}
						t++;
					}
					f++;
				}else{
					sig=true;
					f=1;
				}
			}

			b.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void puntua(double suma, double acumulada) {
		puntuacion = aptitud/suma;
		punt_acum = acumulada + puntuacion;
		
	}	
	
	/***************Getters y Setters************/
	public void setAptitud(int a) {
		this.aptitud = a;
	}

	public double getPunt_acum() {
		return this.punt_acum;
	}

	public int getAptitud() {
		return this.aptitud;
	}

	public void setPuntuacion(double p) {
		this.puntuacion = p;		
	}

	public double getPuntuacion() {
		return this.puntuacion;
	}

	public void setPunt_acum(double pa) {
		this.punt_acum = pa;
	}

	public int getNumEdificios(){
		return this.numEdificios;
	}
	
	public int getEdificio(int i) {
		return this.edificios[i].getEdificio();
	}
	
	public Edificio[] getEdificioAll() {
		return this.edificios;
	}
	
	public void setEdificio(int i, int c){
		this.edificios[i].setEdificio(c);
	}
	
	public int getId(){
		return this.idCromosoma;
	}
	
	public String getFichero(){
		return fichero;
	}
	
	public void setFichero(String f){
		this.fichero = f;
	}
}
