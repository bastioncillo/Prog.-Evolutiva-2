package modelo.edificio;

import java.util.Random;

public class Edificio {
	private int edificio;
	
	public Edificio(){}
	
	public Edificio(int l){
		Random r = new Random();
		boolean ser = false;
		
		while(!ser){
			int x = r.nextInt(l+1);
			if(x != 0){
				this.edificio = x;
				ser = true;
			}
		}
		
		
	}
	
	/*public Edificio(int l){
		Random r = new Random();
		
		int x = r.nextInt(l);
		this.edificio = x;	
		
	}*/
	public int getEdificio(){
		return this.edificio;
	}
	
	public void setEdificio(int c){
		this.edificio = c;
	}
	
	public Edificio clone(){
		Edificio clon = new Edificio();
		clon.edificio = this.edificio;
		return clon;
	}
}
