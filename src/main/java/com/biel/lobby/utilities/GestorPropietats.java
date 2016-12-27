package com.biel.lobby.utilities;

import java.io.*;
import java.util.ArrayList;

import org.bukkit.*;

public class GestorPropietats {
	String Ruta = "";
	public  GestorPropietats(String RutaArxiuPropietats){
		Ruta = RutaArxiuPropietats;
	}
	
	private ArrayList<String> LlegirArxiuPropietats(){
		ArrayList<String> Arr = new ArrayList<>();
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
			System.err.println("Error: " + e.getMessage());
		}
		return Arr;
	}
	public boolean ExisteixPropietat(String Nom){
		return ObtenirPropietats().contains(Nom);
	}
	public ArrayList<String> ObtenirPropietats(){
		ArrayList<String> r = new ArrayList<>();
		for (String linia : LlegirArxiuPropietats()){
			String[] parts = linia.split("=");
			String part1 = parts[0]; // [Nom]=valor
			r.add(part1);
		}
		return r;
	}
	public String ObtenirPropietat(String Nom){
		for (String linia : LlegirArxiuPropietats()){
			String[] parts = linia.split("=");
			String part1 = parts[0]; // [Nom]=valor
			String part2 = parts[1]; // Nom=[valor]
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
	public void EstablirPropietat(String Nom, String valor){		
		boolean fet = false;
		ArrayList<String> Arr = LlegirArxiuPropietats();
		File f = new File(Ruta);
		f.delete();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));

			for (String linia : Arr){
				String[] parts = linia.split("=");
				String part1 = parts[0]; // 004
				//String part2 = parts[1]; // 034556
				if (Nom.equals(part1)){
					bw.write(Nom + "=" + valor);
					bw.newLine();
					fet = true;
				}else{
					bw.write(linia);
					bw.newLine();
				}
			}
			if (!fet){
				bw.write(Nom + "=" + valor);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}
	public void EstablirPropietat(String Nom, int valor){
		EstablirPropietat(Nom, Integer.toString(valor));
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
		for (String d : Llista) {
			if (Llista.indexOf(d) != 0){
				text = text + ",";
			}
			text = text + d;
			
		}
		EstablirPropietat(Nom,text);
	}
	public String[] ObtenirLlista(String Nom){
		return ObtenirPropietat(Nom).split(",");
	}
	@Deprecated
	public Location ObtenirLocation(String Nom){
		World world = Bukkit.getServer().getWorlds().get(0);
		return ObtenirLocation(Nom, world);
	}
	public Location ObtenirLocation(String Nom, World world){
		String[] p1 = ObtenirLlista(Nom);    	
    	Location pont1 = new Location(world,Integer.parseInt(p1[0]),Integer.parseInt(p1[1]),Integer.parseInt(p1[2]));
    	return pont1;
	}
	public void EstablirLocation(String Nom, Location loc){
		EstablirPropietat(Nom, Integer.toString((int)loc.getX()) + "," + Integer.toString((int)loc.getY()) + "," + Integer.toString((int)loc.getZ()));
	}
	public void EstablirLocations(String Nom, ArrayList<Location> locations){
		for (Location l : locations){
			EstablirLocation(Nom + "_" + Integer.toString(locations.indexOf(l)), l);
		}
	}
	public  ArrayList<Location> ObtenirLocations(String Nom){
		return ObtenirLocations(Nom, null);
	}
	public  ArrayList<Location> ObtenirLocations(String Nom, World world){
	    int i = 0;	
	    ArrayList<Location> Locs = new ArrayList<>();
		while (true){
			String Propietat =  Nom +  "_" + Integer.toString(i);
			
			if(!ExisteixPropietat(Propietat)){break;}
			Location p = ObtenirLocation(Propietat, world);
			
			Locs.add(p);
			i = i + 1;
			
		}
		return Locs;
	}
	public boolean ExisteixArray(String Nom){
		for (String linia : LlegirArxiuPropietats()){
			String[] parts = linia.split("=");
			String part1 = parts[0]; // 004
			if (part1.startsWith(Nom) && part1.contains("_")){
				return true;
			}
		}
		return false;
	}
	public void EstablirArray(String Nom, ArrayList<String> text){
		for (String t : text){
			EstablirPropietat(Nom + "_" + Integer.toString(text.indexOf(t)), t);
		}
	}
	public  ArrayList<String> ObtenirArray(String Nom){
	    int i = 0;	
	    ArrayList<String> props = new ArrayList<>();
		while (true){
			String Propietat =  Nom +  "_" + Integer.toString(i);
			
			if(!ExisteixPropietat(Propietat)){break;}
			String p = ObtenirPropietat(Propietat);
			
			props.add(p);
			i = i + 1;
			
		}
		return props;
	}
	public void EstablirCuboid(String Nom, Location loc1, Location loc2){
		ArrayList<Location> locs = new ArrayList<>();
		locs.add(loc1);
		locs.add(loc2);
		EstablirLocations(Nom, locs);
	}
	public Cuboid ObtenirCuboid(String Nom, World world){
		ArrayList<Location> locs = ObtenirLocations(Nom, world);
		if (locs.size() != 2){System.out.println("Cuboid mal definit: " + Nom + ", " + Integer.toString(locs.size()) + " location(s)"); return null;}
		return new Cuboid(locs.get(0), locs.get(1));
	}
}
