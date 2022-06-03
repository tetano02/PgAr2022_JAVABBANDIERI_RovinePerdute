package it.unibs.fp.rovinePerdute;

import java.util.ArrayList;

public class Mappa {
	
	//Grafo 
	
	//La classe Mappa � chiamata cos� solo per concetto essendo una Mappa di citt�, essa si comporta semplicemente come un contenitore
	//Per semplicit� al posto di identificare ogni id come una key in una struttura map, esso corrisponde all'index nell'array
	//Le interazioni tra le citt� sono salvate dentro ogni citt�, risparmiando cos� spazio rispetto a una matrice ingombrante
	private ArrayList<Citta> elencoCitta = new ArrayList<>();
	private boolean daEsplorare;
	
	public boolean isDaEsplorare() {
		return daEsplorare;
	}

	public void setDaEsplorare(boolean daEsplorare) {
		this.daEsplorare = daEsplorare;
	}

	public void aggiungiCitta(Citta c) {
		this.elencoCitta.add(c);
	}
	
	public Citta getCitta(int id) {
		return elencoCitta.get(id);
	}
	
	public ArrayList<Citta> getElencoCitta() {
		return elencoCitta;
	}
}
