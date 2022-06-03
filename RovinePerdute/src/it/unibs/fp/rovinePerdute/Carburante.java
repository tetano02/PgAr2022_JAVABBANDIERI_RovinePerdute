package it.unibs.fp.rovinePerdute;

public class Carburante {

	private double carburanteTonatiuh;
	private double carburanteMetztli;
	
	public double getCarburanteTonatiuh() {
		return carburanteTonatiuh;
	}
	public void setCarburanteTonatiuh(Citta c1, Citta c2) {
		this.carburanteTonatiuh = Math.sqrt(Math.pow(c1.getPosX()-c2.getPosX(),2)+Math.pow(c1.getPosY()-c2.getPosY(),2));
	}
	public double getCarburanteMetztli() {
		return carburanteMetztli;
	}
	public void setCarburanteMetztli(Citta c1, Citta c2) {
		this.carburanteMetztli = Math.abs(c1.getPosH()-c2.getPosH());
	}
	
	public double getCarburanteVeicolo(int veicolo) {
		if(veicolo==0)
			return this.getCarburanteTonatiuh();
		return this.getCarburanteMetztli();
	}
	
}
