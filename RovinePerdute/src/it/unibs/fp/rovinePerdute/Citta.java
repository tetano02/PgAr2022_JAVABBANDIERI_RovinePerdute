package it.unibs.fp.rovinePerdute;

import java.util.HashMap;
import java.util.Map;

public class Citta {
	
	//Nodo 
	
	private String nome;
	private Posizione pos;
	private Map<Integer,Carburante> collegamenti=new HashMap<>(); //Setta i collegamenti con gli altri nodi e i pesi
	private Veicolo ndTonatiuh;
	private Veicolo ndMetztli;
	
	public Citta() {
		this.pos=new Posizione(); //Altrimenti pos viene riconosciuto come null
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getPosX() {
		return this.pos.getCoordinataX();
	}
	
	public int getPosY() {
		return this.pos.getCoordinataY();
	}
	
	public int getPosH() {
		return this.pos.getCoordinataH();
	}
	
	public void setPosX(int x) {
		this.pos.setCoordinataX(x);
	}
	
	public void setPosY(int y) {
		this.pos.setCoordinataY(y);
	}
	
	public void setPosH(int h) {
		this.pos.setCoordinataH(h);
	}
	
	public Map<Integer,Carburante> getCollegamenti() {
		return collegamenti;
	}
	
	public void setCollegamenti(int id, Carburante carb) {
		collegamenti.put(id, carb);
	}
	
	public Carburante getCarburante(int id) {
		return this.collegamenti.get(id);
	}
	
	public Veicolo getNdTonatiuh() {
		return ndTonatiuh;
	}
	
	public void setNdTonatiuh(Veicolo ndTonatiuh) {
		this.ndTonatiuh = ndTonatiuh;
	}
	
	public Veicolo getNdMetztli() {
		return ndMetztli;
	}
	
	public void setNdMetztli(Veicolo ndMetztli) {
		this.ndMetztli = ndMetztli;
	}

	public Veicolo getVeicolo(int veicolo) {
		if(veicolo==0)
			return this.ndTonatiuh;
		return this.ndMetztli;
	}
	
}
