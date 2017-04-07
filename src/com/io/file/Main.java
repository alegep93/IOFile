package com.io.file;

import java.io.IOException;
import java.util.Scanner;
import com.io.file.explorer.Directory;

public class Main {
	public static void main(String[] args) throws IOException {
		String scelta = "", dirPath = "";
		Scanner scan = new Scanner(System.in);
		Directory dir;
		
		dirPath = Directory.rootDir(scan);
		
		printMsg();
		
		while(!"q".equals(scelta = scan.nextLine())){
			dir = new Directory(dirPath);
			
			switch(scelta){
				case "c":
					dir.creaFile(scan);
					printMsg();
				break;
				case "d":
					dir.creaDir(scan);
					printMsg();
				break;
				case "f":
					dir.mostraFile();
					printMsg();
				break;
				case "l":
					dir.showDirList(dirPath);
					printMsg();
				break;
				case "n":
					dirPath = Directory.rootDir(scan);
					printMsg();
				break;
				case "o":
					dir.copyFromFile(scan);
					printMsg();
				break;
				case "s":
					dir.writeOnFile(scan);
					printMsg();
				break;
				case "i":
					dir.occurrencesOnFile(scan);
					printMsg();
				break;
				case "r":
				printMsg();
				break;
			}
		}
		System.out.println("Addio!!!");
		scan.close();
	}
	
	public static void printMsg(){
		System.out.println("----------------------------------------------------------");
		System.out.println("Scegli un'azione tra le seguenti: ");
		System.out.println("  n => Cambia directory di base");
		System.out.println("  c => Crea un file nel percorso specificato");
		System.out.println("  d => Crea una nuova directory");
		System.out.println("  f => Mostra la lista dei file della cartella specificata");
		System.out.println("  l => Mostra la lista delle cartelle e dei file");
		System.out.println("  r => Leggi contenuto file");
		System.out.println("  o => Crea file copia");
		System.out.println("  s => Scrivi su file");
		System.out.println("  i => Occorrenze di parola su file");
		System.out.println("  q => Termina il programma");
	}
}
