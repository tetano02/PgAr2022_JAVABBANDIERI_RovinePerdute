package it.unibs.fp.rovinePerdute;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class GestioneXML {
	
	private static XMLStreamReader xml5, xml12 , xml50 , xml200 , xml2000 , xml10000;
	
	private static void leggiFileXML() {
		XMLInputFactory xmlif = null;  
		try {
		xmlif = XMLInputFactory.newInstance();
		xml5 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_5.xml"));
		xml12 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_12.xml"));
		xml50 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_50.xml"));
		xml200 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_200.xml"));
		xml2000 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_2000.xml"));
		xml10000 = xmlif.createXMLStreamReader("inputPersone.xml", new FileInputStream("PgAr_Map_10000.xml"));
		} catch (Exception e) {
		System.out.println("Impossibile leggere tutti i file");
		System.out.println(e.getMessage());
		}
	}
		
	private static void inizializzaMappa(XMLStreamReader file, Mappa mappa) throws XMLStreamException {
		int idCorrente=0; //Nota: idCorrente sarà uguale alla posizione attuale dell'array
		while (file.hasNext()) { // continua a leggere finché ha eventi a disposizione
			switch (file.getEventType()) { // switch sul tipo di evento
			case XMLStreamConstants.START_ELEMENT:
				if(file.getLocalName().equals("city")) {
					mappa.aggiungiCitta(new Citta());
					for(int i=0; i<file.getAttributeCount(); i++) {
						switch(file.getAttributeLocalName(i)) {
						//Non serve salvare l'id, in quanto esso corrisponde con la posizione nell'ArrayList
						case "id":
							if(Utility.convertiInt(file.getAttributeValue(i))) {
								idCorrente=Integer.parseInt(file.getAttributeValue(i)); //Incremento dell'idCorrente
							}
							if(idCorrente!=0) {
								//Utile per il calcolo del percorso minimo, -1 sta per infinito
								mappa.getCitta(idCorrente).setNdTonatiuh(new Veicolo(-1));
								mappa.getCitta(idCorrente).setNdMetztli(new Veicolo(-1));
							}
							else {
								mappa.getCitta(idCorrente).setNdTonatiuh(new Veicolo(0)); //Il nodo 0 viene inizializzato a carburanteSpeso=0
								mappa.getCitta(idCorrente).setNdMetztli(new Veicolo(0));
							}
							break;
						case "name":
							mappa.getCitta(idCorrente).setNome(file.getAttributeValue(i));
							break;
						case "x":
							if(Utility.convertiInt(file.getAttributeValue(i)))
								mappa.getCitta(idCorrente).setPosX(Integer.parseInt(file.getAttributeValue(i)));
							break;
						case "y":
							if(Utility.convertiInt(file.getAttributeValue(i)))
								mappa.getCitta(idCorrente).setPosY(Integer.parseInt(file.getAttributeValue(i)));
							break;
						case "h":
							if(Utility.convertiInt(file.getAttributeValue(i)))
								mappa.getCitta(idCorrente).setPosH(Integer.parseInt(file.getAttributeValue(i)));
							break;
						}
					}
				}
				else if(file.getLocalName().equals("link")) {
					int id=0;
					if(Utility.convertiInt(file.getAttributeValue(0))) {//link ha un solo attributo
						id=Integer.parseInt(file.getAttributeValue(0));
					}
					mappa.getCitta(idCorrente).setCollegamenti(id, new Carburante()); //Setta i collegamenti
				}
			break;
			}
			file.next();
		}
		
		//Dopo aver inizializzato tutte le città ne determina i legami tra di esse
		//Questa operazione viene svolta dopo perchè se una città si legasse con una non ancora inizializzata ci sarebbe un errore
		for(int i=0; i<mappa.getElencoCitta().size(); i++) {
			for(int key:mappa.getCitta(i).getCollegamenti().keySet()) {
				mappa.getCitta(i).getCarburante(key).setCarburanteTonatiuh(mappa.getCitta(i), mappa.getCitta(key));
				mappa.getCitta(i).getCarburante(key).setCarburanteMetztli(mappa.getCitta(i), mappa.getCitta(key));
			}
		}
	}
	
	
	//Modifica tutte le mappe già esistenti e da esplorare, inserendo al loro interno le città e le interazioni tra di loro
	public static void modificaMappe() throws XMLStreamException {
		System.out.println("Analizzando le mappe...");
		leggiFileXML();
		for(int i=0; i<GestioneRovine.getElencoMappe().size(); i++) {
			if(GestioneRovine.getElencoMappe().get(i).isDaEsplorare()) {
				switch(i) {
				case 0:
					inizializzaMappa(xml5, GestioneRovine.getElencoMappe().get(i));
				break;
				case 1:
					inizializzaMappa(xml12, GestioneRovine.getElencoMappe().get(i));
				break;
				case 2:
					inizializzaMappa(xml50, GestioneRovine.getElencoMappe().get(i));
				break;
				case 3:
					inizializzaMappa(xml200, GestioneRovine.getElencoMappe().get(i));
				break;
				case 4:
					inizializzaMappa(xml2000, GestioneRovine.getElencoMappe().get(i));
				break;
				case 5:
					inizializzaMappa(xml10000, GestioneRovine.getElencoMappe().get(i));
				break;
				}
			}
		}
		
		for(int i=0; i<GestioneRovine.getElencoMappe().size(); i++) {
			if(!GestioneRovine.getElencoMappe().get(i).isDaEsplorare()) {
				GestioneRovine.getElencoMappe().remove(i);
				i--; //Per non perdere il controllo di una posizione
			}
		}
	}
	
	public static void outputPercorsoMinimo() throws XMLStreamException {
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlPercorso = null; //File output
		try{
			xmlof = XMLOutputFactory.newInstance();
			xmlPercorso = xmlof.createXMLStreamWriter(new FileOutputStream("Routes.xml"),"utf-8");
			xmlPercorso.writeStartDocument("utf-8", "1.0");
		}catch(Exception e) {
			System.out.println("Errore nell'inizializzazione del file di output");
		}
		xmlPercorso.writeStartElement("rovinePerdute");
		//Commenti per chiarire l'XML
		xmlPercorso.writeComment("cities riporta il numero di città attraversate tra campo base e Rovine Perdute, loro sono escluse");
		xmlPercorso.writeComment("campo base e Rovine Perdute vengono riportate nell'elenco delle <city> solo per una maggiore leggibilità del percorso");
		for(int i=0; i<GestioneRovine.getElencoMappe().size(); i++) {
			int posizioneFinale=GestioneRovine.getMappa(i).getElencoCitta().size()-1;
			xmlPercorso.writeStartElement("routes");
			xmlPercorso.writeAttribute("città", Utility.converti( GestioneRovine.getMappa(i).getElencoCitta().size()));
			for(int k=0; k<=1; k++) {
				int pos=posizioneFinale;
				int cittaAttraversate=GestioneRovine.getMappa(i).getCitta(posizioneFinale).getVeicolo(k).getCittaAttraversate();
				xmlPercorso.writeStartElement("route");
				xmlPercorso.writeAttribute("team",Utility.getNomeVeicolo(k));
				xmlPercorso.writeAttribute("cost",Utility.converti(GestioneRovine.getMappa(i).getCitta(posizioneFinale).getVeicolo(k).getCarburanteConsumato()));
				xmlPercorso.writeAttribute("cities", Utility.converti(cittaAttraversate));
				ArrayList<Integer> posizioni=new ArrayList<>();
				do {
					posizioni.add(pos);
					pos=GestioneRovine.getIdPrecedente(i, pos, k);
				}while(pos>=0);
				for(int dim=posizioni.size()-1; dim>=0; dim--) {
					xmlPercorso.writeStartElement("city");
					xmlPercorso.writeAttribute("id",Utility.converti(posizioni.get(dim)));
					xmlPercorso.writeAttribute("name", GestioneRovine.getMappa(i).getCitta(posizioni.get(dim)).getNome());
					xmlPercorso.writeEndElement();
				}
				xmlPercorso.writeEndElement();
			}
			xmlPercorso.writeEndElement();
		}
		xmlPercorso.writeEndElement();
		xmlPercorso.writeEndDocument();
		xmlPercorso.close(); //Chiusura del documento
		System.out.println("Esplorazione completata! Corri a vedere i risultati!");
	}
}
