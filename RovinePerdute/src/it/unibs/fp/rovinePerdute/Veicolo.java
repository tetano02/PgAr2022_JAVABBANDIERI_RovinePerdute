package it.unibs.fp.rovinePerdute;

public class Veicolo {

	//Classe d'aiuto per l'algoritmo del percorso minimo
	
	private Citta precedente;
	private double carburanteConsumato;
	private boolean controllato;
	private int cittaAttraversate;
	
	public Veicolo(int carb) {
		this.carburanteConsumato=carb;
		this.controllato=false;
		this.cittaAttraversate=-1; //Conta solo le città attraversate in mezzo
	}
	
	public Citta getPrecedente() {
		return precedente;
	}
	
	public void setPrecedente(Citta precedente) {
		this.precedente = precedente;
	}
	
	public double getCarburanteConsumato() {
		return carburanteConsumato;
	}
	
	public void setCarburanteConsumato(double carburanteConsumato) {
		this.carburanteConsumato = carburanteConsumato;
	}

	public boolean getControllato() {
		return controllato;
	}

	public void setControllato(boolean controllato) {
		this.controllato = controllato;
	}

	public int getCittaAttraversate() {
		return cittaAttraversate;
	}

	public void setCittaAttraversate(int cittaAttraversate) {
		this.cittaAttraversate = cittaAttraversate;
	}
}
