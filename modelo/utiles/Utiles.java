package modelo.utiles;

import modelo.edificio.Edificio;

public class Utiles {
	
	/*Pasar de bianrio a decimal*/
	public static double bin2dec(boolean[] binary, int l) {
		float dec_value = 0;
		
		for (int i = 0; i < l; i++) {
			dec_value += bool2int(binary[i]) * Math.pow(2, i);
		}
		
		return dec_value;
	}
	
	/*Pasar de booleano a entero*/
	public static int bool2int(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}
	
	/*Formar número entero juntando los datos de un array*/
	public static int juntarEnteros(Edificio [] datos, int l){
		int res = 0;
		for(int i = 0; i < l; i++){
			res += datos[i].getEdificio();
			res *= 10;
		}
		return res;
	}
}
