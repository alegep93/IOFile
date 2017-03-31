package com.io.file.explorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Directory {
	protected String path;
	
	public Directory(){
		this.path = "";
	}
	
	public Directory(String path){
		this.path = path;
	}
	
	public static String rootDir(Scanner scan){
		String dirPath = "";
		System.out.println("Inserisci il percorso della cartella di base:");
		dirPath = scan.nextLine();
		return dirPath;
	}
	
	public void creaFile(Scanner scan){
		String nomeFile = "";
		System.out.println("Inserisci il nome del file da creare: ");
		nomeFile = scan.nextLine();
		File f = new File(this.path + "\\" + nomeFile);
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void creaDir(Scanner scan){
		String nomeDir = "";
		System.out.println("Inserisci il nome della directory da creare: ");
		nomeDir = scan.nextLine();
		File d = new File(this.path + "\\" + nomeDir);
		
		if(!d.exists()){
			try {
				d.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.err.println("Esiste già una cartella con lo stesso nome");
		}
	}
	
	public void mostraFile(){
		File dir = new File(this.path);
		if(dir != null && dir.exists()){
			String[] dirList = dir.list();
			if(dirList != null){
				for(int i=0; i<dirList.length; i++){
					File f = new File(this.path, dirList[i]);
					if(f.exists() && f.isFile()){
						System.out.println("- " + dirList[i]);
					}
				}
			}
		}
	}

	public void showDirList(String path){
		String spazio = slashCounter(path);
		File dir = new File(path);
		if(dir != null && dir.exists()){
			String[] dirList = dir.list();
			if(dirList != null){
				for(int i=0; i<dirList.length; i++){
					File f = new File(path, dirList[i]);
					if(f.exists() && f.isFile()){
						System.out.println(spazio + "- " + dirList[i]);
					}
					else if(f.exists() && f.isDirectory()){
						System.out.println(spazio + "- " + f.getPath());
						showDirList(f.getPath());
					}
				}
			}
		}
	}

	public void copyFromFile(Scanner scan) throws IOException{
		int counter = 1;
		String nomeFile = "", nomeFileCopia = "", linea = "";
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		PrintWriter pw = null;
		
		System.out.println("Scegli il file da copiare");
		mostraFile();
		nomeFile = scan.nextLine();
		
		try{
			fr = new FileReader(this.path + "\\" + nomeFile);
			br = new BufferedReader(fr);
			
			nomeFileCopia = nomeFile.split("\\.")[0] + " - Copia (" + counter + ").txt";
			
			File f = new File(this.path + "\\" + nomeFileCopia);
			while(f.exists()){
				counter++;
				nomeFileCopia = nomeFile.split("\\.")[0] + " - Copia (" + counter + ").txt";
				f = new File(this.path + "\\" + nomeFileCopia);
			}
			
			fw = new FileWriter(this.path + "\\" + nomeFileCopia);
			pw = new PrintWriter(fw);
			
			linea = br.readLine();
			
			while(linea != null){
				pw.println(linea);
				linea = br.readLine();
			}
			
			counter++;
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			fr.close();
			fw.close();
			pw.close();
		}
	}
	
	public void writeOnFile(Scanner scan) throws IOException{
		String nomeFile = "", linea = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			System.out.println("Scegli il nome del file su cui scrivere");
			mostraFile();
			
			nomeFile = scan.nextLine();
			fw = new FileWriter(this.path + "\\" + nomeFile, true);
			pw = new PrintWriter(fw);
			
			System.out.println("Inserisci il testo da scrivere nel file");
			System.out.println("Inserisci * per terminare l'inserimento");
			while(!"*".equals(linea)){
				pw.println(linea);
				linea = scan.nextLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			fw.close();
			pw.close();
		}
	}
	
	public String leggiContFile(Scanner scan) throws IOException{
		String nomeFile = "", linea = "", testo = "";
		FileReader fr = null;
		BufferedReader br = null;
		try{
			System.out.println("Inserisci il nome di uno dei seguenti file");
			mostraFile();
			nomeFile = scan.nextLine();
			fr = new FileReader(this.path + "\\" + nomeFile);
			br = new BufferedReader(fr);
			
			linea = br.readLine();
			
			while(linea != null){
				testo += (linea + " ");
				System.out.println(linea);
				linea = br.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(fr != null)
				fr.close();
		}
		return testo;
	}

	public void occurrencesOnFile(Scanner scan){
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		String testo = "";
		
		try{
			testo = leggiContFile(scan);
			System.out.println("-------------------------------------------");
			String[] textArr = testo.split(" ");
			
			for (int i=0; i<textArr.length; i++) {
				Integer freq = myMap.get(textArr[i]);
				
				if(freq == null)
					freq = 1;
				else
					freq++;
				
				myMap.put(textArr[i], freq);
			}
			for (String key : myMap.keySet()) {
				Integer val = myMap.get(key);
				System.out.println(key + " ha " + val + " occurrences");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String slashCounter(String s){
		String spazio = "";
		for(int i=0; i<s.length(); i++){
			if('\\' == (s.charAt(i))){
				spazio += "  ";
			}
		}
		return spazio;
	}
}
