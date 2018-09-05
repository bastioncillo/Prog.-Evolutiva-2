package modelo.cruce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import modelo.cromosoma.Cromosoma;

public class CruceRecRutas implements Cruce{

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		//Inicializo todos los alelos de los hijos a 0:
		for(int j = 0; j < padre1.getNumEdificios(); j++){
			hijo1.setEdificio(j, 0);
			hijo2.setEdificio(j, 0);
		}
		
		//Cargo la matriz de distancias, también valdría la de flujo, para obatener
		//la tabla de conectividades a travás de ella.
		int[][] dist = new int[padre1.getNumEdificios()][padre1.getNumEdificios()];
		String fichero = padre1.getFichero(); 
		cargarMatriz(fichero, dist);
		
		//En la fila superior de la matriz están todos edificios (genes) del cromosoma
		int[][] tablaConectividad = new int[padre1.getNumEdificios()][padre1.getNumEdificios()];
		for(int i = 0; i< padre1.getNumEdificios(); i++){
			tablaConectividad[i][0] = i+1;
		}
		//En la columna correspondiente de cada posición de la primera fila, están todas
		//las ciudades a las que está conectada la ciudad de la primera fila
		int p;
		for(int i = 0; i < padre1.getNumEdificios(); i++){
			p = 1;
			for(int j = 0; j < padre1.getNumEdificios(); j++){
				if(dist[i][j] != 0){
					tablaConectividad[i][p] = j+1;
					p++;
				}
			}
			if(p < padre1.getNumEdificios()){
				tablaConectividad[i][p] = 0;
			}
		}
		
		cruceRutas(hijo1, padre1, padre2, tablaConectividad);
		cruceRutas(hijo2, padre2, padre1, tablaConectividad);
		
		//Una vez tenemos en los hijos el resultado de cruzar a los padres
		//los evaluamos
		hijo1.setAptitud(hijo1.calcularAptitud());
		hijo2.setAptitud(hijo2.calcularAptitud());
	}
	
	private void cruceRutas(Cromosoma hijo, Cromosoma padreX, Cromosoma padreY, int [][] tablaConectividad){
		//Se construye el primer descendiente tomando la ciudad
		//inicial de uno de los progenitores
		hijo.setEdificio(0, padreY.getEdificio(0));
		boolean encontrado;
		int pos = 0;
		
		while(pos < hijo.getNumEdificios()-1){
			int i = 0;
			encontrado = false;
			//Buscamos la ciudad que toque ahora en la tabla
			while(!encontrado && i < padreX.getNumEdificios()){
				if(tablaConectividad[i][0] == hijo.getEdificio(pos))
					encontrado = true;
				else i++;
			}
			
			//Una vez hemos encontrado la ciudad en la tabla aumentamos el valor de "pos"
			//para poder colocar la siguiente ciudad en el hijo
			pos++;
			
			int j = 1, c = 0, n, f, k;
			int [][] ciuConectadas = new int[padreX.getNumEdificios()][2];
			
			//Para las ciudades conectadas a la ciudad buscada en el bucle anterior:
			while(j < padreX.getNumEdificios() && tablaConectividad[i][j] != 0){
				f = 0;
				n = 0;
				encontrado = false;
				//Buscamos dichas ciudades en la tabla de conectividades
				while(!encontrado && f < padreX.getNumEdificios()){
					if(tablaConectividad[f][0] == tablaConectividad[i][j])
						encontrado = true;
					else
						f++;
				}
				//Una vez laa hemos encontrado, hacemos un recuento de sus propias ciudades 
				//a las que están unidas
				k = 1;
				while(k < padreX.getNumEdificios() && tablaConectividad[f][k] != 0){
					n+=1;
					k++;
				}
				//Y lo guardamos en la tabla de ciudades conectadas
				ciuConectadas[c][0] = tablaConectividad[i][j];
				ciuConectadas[c][1] = n;
				c++;
				j++;
			}
			
			//Selecciono como candidato para ser el próximo gen a la primera ciudad de la 
			//tabla ciuConectadas que no esté ya en el hijo
			int g = 0, ciuCandidato, ciusDeCandidato;
			k = 0;
			ciuCandidato = ciuConectadas[g][0];
			while(g < c && repetido(pos, hijo, ciuCandidato)){
				g++;
				ciuCandidato = ciuConectadas[g][0];
			}
			ciusDeCandidato = ciuConectadas[g][1];
			
			boolean iguales = false;
			//En el caso de que haya vayas ciudades con el mismo número de ciudades conectadas
			//guardo dicha ciudad de la tabla de ciuConectadas
			int[] ciusIguales = new int [c];
			ciusIguales[0] = ciuCandidato;
			
			//Para cada ciudad a partir de candidato miro si alguna de la siguente de 
			//ciuConectadas está conectada a menos ciudades que el propio candidato.
			//Niguna de estas ciudades puede ser seleccionada si ya se encunatra en el hijo
			for(int h = g+1; h < c; h++){
				//Sustituyo al candidato y desmiento que haya más
				//de una ciudad conectada al mismo número de ciudades.
				if(ciuConectadas[h][1] < ciusDeCandidato && 
						!repetido(pos, hijo, ciuConectadas[h][0])){
					ciuCandidato = ciuConectadas[h][0];
					ciusDeCandidato = ciuConectadas[h][1];
					iguales = false;
				//Afirmo que hay varias ciudades conectadas al mismo número de ciudades y
				//añado dicha ciudad al array de ciusIguales
				}else if(ciuConectadas[h][1] == ciusDeCandidato &&
						!repetido(pos, hijo, ciuConectadas[h][0])){
					iguales = true;
					ciusIguales[k] = ciuCandidato;
					k++;
					ciusIguales[k] = ciuConectadas[h][0];
					ciuCandidato = ciuConectadas[h][0];
					ciusDeCandidato = ciuConectadas[h][1];
				}
			}
						
			//Si resulta que hay varias ciudades conectadas al menor número de ciudades
			//elijo aleatoriamente cual de esas será el siguiente gen
			if(iguales){
				Random r = new Random();
				ciuCandidato = ciusIguales[r.nextInt(k)];
			}
			
			//Añadimos al hijo su nuevo gen
			hijo.setEdificio(pos, ciuCandidato);	
		}		
	}
	
	private boolean repetido(int pos, Cromosoma hijo, int cand){
		boolean repe = false;
		int i = 0;
		while(!repe && i < pos){
			if(hijo.getEdificio(i) == cand)
				repe = true;
			else
				i++;
		}
		return repe;
	}
	
	void cargarMatriz(String fichero, int [][] dist){
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
			
			boolean sig =false;
			linea = b.readLine();
		
			int f=0;
					
			while((linea=b.readLine())!=null){
						
				tempa = linea.split(delimiter);
				if(tempa.length > 1){
					int t = 0;
					for(int c=0; c < tempa.length; c++){

						if(!sig){
							dist[f][c] = Integer.parseInt(tempa[t]);
						}
						t++;
					}
					f++;
				}else{
					sig=true;
					f=0;
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
}
