package it.unibs.fp.rovinePerdute;

import java.util.Scanner;

public class Utility {
	
	//Classe con metodi utili in generale
	
	private static Scanner lettore=new Scanner(System.in);
	
	public static boolean convertiInt(String str) {
		try {
			Integer.parseInt(str);
		}catch(Exception e) {
			System.out.println("Errore, non è un numero, problema nei file XML");
			return false;
		}
		return true;
	}
	
	public static int sceltaMappa() {
		boolean valido=true;
		int num = 0;
		do {
			try {
				System.out.println("Inserisci la mappa che vuoi esplorare:");
				num=lettore.nextInt();
				valido=true;
			}catch(Exception e) {
				lettore.nextLine();
				valido=false;
			}
			if(num<0 || num>6) {
				valido=false;
			}
			if(!valido)
				System.out.println("Scelta non valida, riprova!");
		}while(valido==false);
		return num;
	}
	
	public static String getNomeVeicolo(int veicolo) {
		if(veicolo==0)
			return "Tonatiuh";
		return "Metztli";
	}
	
	public static String converti(double num) {
		String str=Double.toString(num);
		int pos=str.indexOf('.');
		if(str.length()-pos>4)
			return str.substring(0,pos)+str.substring(pos,pos+4); //Approssimo a 3 cifre
		else
			return str;
	}
	
	public static String converti(int num) {
		return Integer.toString(num);
	}
}
