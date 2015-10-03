package com.biel.FastSurvival.Utils;

import java.io.*;
import java.util.ArrayList;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class GestorPropietats {
	String Ruta = "";
	public  GestorPropietats(String RutaArxiuPropietats){
		Ruta = RutaArxiuPropietats;
	}
	private ArrayList<String> LlegirArxiuPropietats(){
		ArrayList<String> Arr = new ArrayList<String>();
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(Ruta);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				Arr.add(strLine);

			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			//System.err.println("Error: " + e.getMessage());
		}
		return Arr;
	}
	public boolean ExisteixPripietat(String Nom){
		for (String linia : LlegirArxiuPropietats()){
			String[] parts = linia.split("=");
			String part1 = parts[0]; // 004
			if (Nom.equals(part1)){
				return true;
			}
		}
		return false;
	}
	public String ObtenirPropietat(String Nom){
		for (String linia : LlegirArxiuPropietats()){
			String[] parts = linia.split("=");
			String part1 = parts[0]; // 004
			if(parts.length <= 1){return "0";}
			String part2 = parts[1]; // 034556
			if (Nom.equals(part1)){
				return part2;
			}
		}
		EstablirPropietat(Nom,"0");
		return "0";


	}
	public int ObtenirPropietatInt(String Nom){
		return Integer.parseInt(ObtenirPropietat(Nom));
	}
	public Double ObtenirPropietatDouble(String Nom){
		return Double.parseDouble(ObtenirPropietat(Nom));
	}
	public long ObtenirPropietatLong(String Nom){
		return Long.parseLong(ObtenirPropietat(Nom));
	}
	public void EstablirPropietat(String Nom, String valor){		
		boolean fet = false;
		ArrayList<String> Arr = LlegirArxiuPropietats();
		File f = deleteFile();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));

			for (String linia : Arr){
				String[] parts = linia.split("=");
				String part1 = parts[0]; // 004
				//String part2 = parts[1]; // 034556
				if (Nom.equals(part1) == true){
					bw.write(Nom + "=" + valor);
					bw.newLine();
					fet = true;
				}else{
					bw.write(linia);
					bw.newLine();
				}
			}
			if (fet == false){
				bw.write(Nom + "=" + valor);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}

	public File deleteFile() {
		File f = getFile();
		f.delete();
		return f;
	}
	public File getFile() {
		File f = new File(Ruta);
		return f;
	}
	public Boolean exists(){
		return getFile().exists();
	}
	public void EstablirPropietat(String Nom, int valor){
		EstablirPropietat(Nom, Integer.toString(valor));
	}
	public void EstablirPropietat(String Nom, long valor){
		EstablirPropietat(Nom, Long.toString(valor));
	}
	public void EstablirPropietat(String Nom, Double valor){
		EstablirPropietat(Nom, Double.toString(valor));
	}
	public void IncrementarPropietat(String Nom, int quantitat){
		String prop = ObtenirPropietat(Nom);
		int num = Integer.parseInt(prop);
		num = num + quantitat;
		EstablirPropietat(Nom, num);
	}
	public void IncrementarPropietat(String Nom){
		IncrementarPropietat(Nom, 1);
	}
	public void EstablirLlista(String Nom, ArrayList<String> Llista){
		String text = "";
		Boolean first = true;
		for (String d : Llista) {
			if (!first){
				text = text + ",";
			}
			first = false;
			text = text + d;

		}

		EstablirPropietat(Nom,text);
		//1,23,4
	}
	public String[] ObtenirLlista(String Nom){
		String str = ObtenirPropietat(Nom);
		if (str.isEmpty() || str.equals("0")){return new String[0];}
		return str.split(",");
	}
	//	@Deprecated
	//	public Location ObtenirLocation(String Nom){
	//		World world = Bukkit.getServer().getWorlds().get(0);
	//		return ObtenirLocation(Nom, world);
	//	}
	public Location ObtenirLocation(String Nom){
		try {
			String[] p1 = ObtenirLlista(Nom);   
			if (p1.length == 0){return null;}
			Location pont1 = new Location(Bukkit.getWorlds().get(Integer.parseInt(p1[3])),Integer.parseInt(p1[0]),Integer.parseInt(p1[1]),Integer.parseInt(p1[2]));
			return pont1;
		} catch (Exception e) {
			Bukkit.broadcastMessage("ObtenirLocation error");
			return null;
		}
	}
	public void EstablirLocation(String Nom, Location loc){
		EstablirPropietat(Nom, Integer.toString((int)loc.getX()) + "," + Integer.toString((int)loc.getY()) + "," + Integer.toString((int)loc.getZ()) + "," + Integer.toString(Bukkit.getWorlds().indexOf(loc.getWorld())));
	}
	public void EstablirLocations(String Nom, ArrayList<Location> locations){
		for (Location l : locations){
			EstablirLocation(Nom + "_" + Integer.toString(locations.indexOf(l)), l);
		}
	}

	public  ArrayList<Location> ObtenirLocations(String Nom){
		int i = 0;	
		ArrayList<Location> Locs = new ArrayList<Location>();
		while (true){
			String Propietat =  Nom +  "_" + Integer.toString(i);

			if(ExisteixPripietat(Propietat) == false){break;}			
			Location p = ObtenirLocation(Propietat);

			Locs.add(p);
			i = i + 1;

		}
		return Locs;
	}
	public void EstablirArray(String Nom, ArrayList<String> text){
		for (String t : text){
			EstablirPropietat(Nom + "_" + Integer.toString(text.indexOf(t)), t);
		}
	}
	public  ArrayList<Integer> ObtenirArrayInt(String Nom){
		return Utils.getIntArrayFromStringArray(ObtenirArray(Nom));
	}
	public  ArrayList<String> ObtenirArray(String Nom){
		int i = 0;	
		ArrayList<String> props = new ArrayList<String>();
		while (true){
			String Propietat =  Nom +  "_" + Integer.toString(i);

			if(ExisteixPripietat(Propietat) == false){break;}			
			String p = ObtenirPropietat(Propietat);

			props.add(p);
			i = i + 1;

		}
		return props;
	}
	public void EstablirCuboid(String Nom, Location loc1, Location loc2){
		ArrayList<Location> locs = new ArrayList<Location>();
		locs.add(loc1);
		locs.add(loc2);
		EstablirLocations(Nom, locs);
	}
	public Cuboid ObtenirCuboid(String Nom){
		ArrayList<Location> locs = ObtenirLocations(Nom);
		return new Cuboid(locs.get(0), locs.get(1));
	}

	public void EstablirPropietat(String nom, Boolean v) {
		EstablirPropietat(nom, v.toString());

	}
	public Boolean ObtenirPropietatBoolean(String nom) {
		return Boolean.parseBoolean(ObtenirPropietat(nom));

	}
}
