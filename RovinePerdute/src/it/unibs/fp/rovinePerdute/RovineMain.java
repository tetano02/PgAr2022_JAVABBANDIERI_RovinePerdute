package it.unibs.fp.rovinePerdute;

import javax.xml.stream.XMLStreamException;

public class RovineMain {

	public static void main(String[] args) throws XMLStreamException{
		GestioneRovine.menu();
		GestioneXML.modificaMappe();
		GestioneRovine.generaPercorsoMinimoVeicolo();
		GestioneXML.outputPercorsoMinimo();
	}
}
