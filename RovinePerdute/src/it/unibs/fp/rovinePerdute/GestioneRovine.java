package it.unibs.fp.rovinePerdute;

import java.util.ArrayList;
import java.util.Stack;

public class GestioneRovine {
	
	//Classe che gestisce l'interfaccia del programma e le strutture dati maggiori
	
	private static final double EPSILON = 0.001;
	private static final int NUMERO_MAPPE = 6;
	public static ArrayList<Mappa> elencoMappe=new ArrayList<>(); //Ogni mappa viene posizionata in un array, così da essere più facilmente reperibili
	private static ArrayList <String> menu=new ArrayList <>();
	private static Stack<Integer> controlloPercorso=new Stack<>();
	private static int indiceCorrente; //Tiene conto dell'indice attuale in elencoMappe, utile per non dover invocare ogni volta negli argomenti dei metodi della generazione del percorso l'indice corrente
	
	public static void inizializzaMappe() {
		for(int i=0; i<NUMERO_MAPPE; i++) {
			GestioneRovine.elencoMappe.add(new Mappa());
			GestioneRovine.elencoMappe.get(i).setDaEsplorare(false);
		}
	}
	
	public static void menu() {
		inizializzaMappe();
		System.out.println("Benvenuto esploratore, andiamo alla ricerca delle rovine perdute!");
		System.out.println("Le mappe sono molte, per alcune la fatica sarà minore, per altre ci sarà da sudare!\nQuali vuoi visitare?");
		inizializzaMenu();
		int scelta=0;
		boolean valido=false;
		do {
		stampaMenu();
		scelta=Utility.sceltaMappa();
		if(scelta!=0) {
			valido=true;
			modificaMenu(scelta-1);
			elencoMappe.get(scelta-1).setDaEsplorare(true);
		}
		if(!valido) {
			System.out.println("Scegli almeno una mappa!");
			scelta=-1; //Evita l'uscita dal ciclo
		}
		}while(scelta!=0 && menu.size()>1);
		System.out.println("L'esplorazione ha inizio!");
	}
	
	private static void inizializzaMenu() {
		menu.add("1)Mappa con 5 città");
		menu.add("2)Mappa con 13 città");
		menu.add("3)Mappa con 50 città");
		menu.add("4)Mappa con 200 città");
		menu.add("5)Mappa con 2000 città");
		menu.add("6)Mappa con 10000 città");
		menu.add("0)Basta così, andiamo ad esplorare!");
	}
	
	private static void modificaMenu(int i) {
		menu.set(i, ""); //Toglie la riga da stampare senza intaccare l'ordine delle scelte
	}
	
	private static void stampaMenu() {
		System.out.print("\n");
		for(int i=0; i<menu.size(); i++)
			if(!menu.get(i).equals(""))
				System.out.println(menu.get(i));
	}
	
	public static ArrayList<Mappa> getElencoMappe(){
		return elencoMappe;
	}
	
	public static Mappa getMappa(int i) {
		return elencoMappe.get(i);
	}
	
	//Implementazione dell'algoritmo del percorso minimo di dijkstra
	
	//Svolge l'algoritmo due volte per ogni mappa, una volta per veicolo. 
	
	//Tonatiuh viene riconosciuto con l'indice 0
	//Metzli viene riconosciuto con l'indice 1
	
	public static void generaPercorsoMinimoVeicolo() {
		System.out.println("Generando il percorso minimo...");
		for(int k=0; k<=1; k++)
			generaPercorsoMinimo(k);
	}
	
	//Essendo un procedimento molto corposo abbiamo preferito usare l'iterazione invece della ricorsione in quanto essa fa risparmiare leggermente memoria e risulta essere meno pesante
		
	public static void generaPercorsoMinimo(int veicolo) {
		for(int i=0; i<elencoMappe.size(); i++) {
			indiceCorrente=i;
			controlloPercorso.push(0);
			do {
				int keyAttuale=controlloPercorso.peek();
				if(getMappa(i).getElencoCitta().get(keyAttuale).getVeicolo(veicolo).getControllato()==false)
					CalcoloPercorsoMinimo(keyAttuale,veicolo);
				controlloPercorso.pop();
				//Aggiunta dei nodi connessi, se non sono stati controllati, nella stack
				for(int key:getMappa(i).getElencoCitta().get(keyAttuale).getCollegamenti().keySet()) {
					if(getMappa(i).getElencoCitta().get(key).getVeicolo(veicolo).getControllato()==false)
						controlloPercorso.add(key);
				}
			}while(controlloPercorso.size()>0);
		}
	}
	
	private static void CalcoloPercorsoMinimo(int indicePartenza, int veicolo) {
		for(int key:getMappa(indiceCorrente).getCitta(indicePartenza).getCollegamenti().keySet()) {
			//Somma del carburante speso fino al nodo precedente e quello che spende ad arrivare dal nodo precedente al suo figlio
			double carburanteSpeso=getMappa(indiceCorrente).getCitta(indicePartenza).getVeicolo(veicolo).getCarburanteConsumato()+getMappa(indiceCorrente).getCitta(indicePartenza).getCarburante(key).getCarburanteVeicolo(veicolo);
			//Carburante minimo trovato per arrivare al figlio (Infinito (-1) se non ancora inizializzato)
			double carburanteAttuale=getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).getCarburanteConsumato();
			if(Math.abs(carburanteSpeso-carburanteAttuale)<EPSILON) {//Altro modo per dire se sono uguali, è per evitare complicazioni essendo un confronto tra double
				controllaCarburanteUguale(indicePartenza, key, veicolo);
			}else if(carburanteAttuale<0  || carburanteSpeso<carburanteAttuale){
				aggiornaCitta(key,indicePartenza,veicolo);
				getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setCarburanteConsumato(carburanteSpeso);
				getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setPrecedente(getMappa(indiceCorrente).getCitta(indicePartenza));
				getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setControllato(false); //Se viene modificato un nodo è da ricontrollare
			}
		}
		getMappa(indiceCorrente).getCitta(indicePartenza).getVeicolo(veicolo).setControllato(true); //Nodo controllato
	}
	
	private static int numeroCittaDaOrigine(int pos, int veicolo) {
		return getMappa(indiceCorrente).getCitta(pos).getVeicolo(veicolo).getCittaAttraversate();
	}
	
	private static void controllaCarburanteUguale(int indicePartenza, int key, int veicolo) {
		if(numeroCittaDaOrigine(indicePartenza,veicolo)<numeroCittaDaOrigine(getIdPrecedente(indiceCorrente,key,veicolo),veicolo)) {
			aggiornaCitta(key,indicePartenza,veicolo);
			getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setPrecedente(getMappa(indiceCorrente).getCitta(indicePartenza));
		}else if(numeroCittaDaOrigine(indicePartenza,veicolo)==numeroCittaDaOrigine(getIdPrecedente(indiceCorrente,key,veicolo),veicolo)) {//Se sono uguali le distanze metti la key maggiore
			if(indicePartenza>getIdPrecedente(indiceCorrente,key,veicolo)) {
				aggiornaCitta(key,indicePartenza,veicolo);
				getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setPrecedente(getMappa(indiceCorrente).getCitta(indicePartenza));
			}
		}
	}
	
	private static void aggiornaCitta(int key, int id, int veicolo) {
		int cittaAttraversatePrecedente=getMappa(indiceCorrente).getCitta(id).getVeicolo(veicolo).getCittaAttraversate();
		getMappa(indiceCorrente).getCitta(key).getVeicolo(veicolo).setCittaAttraversate(cittaAttraversatePrecedente+1);
	}
	
	public static int getIdPrecedente(int mappa, int id, int veicolo) {
		return getMappa(mappa).getElencoCitta().indexOf(getMappa(mappa).getCitta(id).getVeicolo(veicolo).getPrecedente());
	}
	
}
