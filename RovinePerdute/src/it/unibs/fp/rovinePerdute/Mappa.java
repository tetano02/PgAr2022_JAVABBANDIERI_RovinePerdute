package it.unibs.fp.rovinePerdute;

import java.util.ArrayList;

public class Mappa {
	
	//Grafo 
	
	//La classe Mappa è chiamata così solo per concetto essendo una Mappa di città, essa si comporta semplicemente come un contenitore
	//Per semplicità al posto di identificare ogni id come una key in una struttura map, esso corrisponde all'index nell'array
	//Le interazioni tra le città sono salvate dentro ogni città, risparmiando così spazio rispetto a una matrice ingombrante
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
