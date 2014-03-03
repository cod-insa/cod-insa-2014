package control;
import game.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {

	//IO
	private String file;
	private Scanner scanner;
	private String path_file;

	//general information about the map
	private int basesCount;
	private double center_lat;
	private double center_long;
	private double radius;

	//Specify here available maps locations
	private AvailableMaps map;
	private static String location = "maps/";
	private static String mapFrance = location+"france.map";
	private static String mapUsa = location+"france.map";
	private static String mapAfrica = location+"france.map";
	private static String mapAsia = location+"france.map";
	private static String mapEurope = location+"france.map";
	
	//The new loaded map to use in the game
	private Map newmap;

	/**
	 * Available maps
	 * @author NVA
	 */
	public enum AvailableMaps{
		FRANCE,
		USA,
		AFRICA,
		ASIA,
		EUROPE
	};

	/**
	 * By default, loads France.
	 */
	public MapLoader()
	{
		file = "";
		map = AvailableMaps.FRANCE;
	}

	/**
	 * Specify here the map you want to load
	 * @param mapname, from enum AvailableMaps
	 */
	public /*Map*/void loadMap(AvailableMaps mapname) 
	{
		switch (mapname) {
		case FRANCE:
			path_file = mapFrance;
			break;
		case USA:
			path_file = mapUsa;
			break;
		case ASIA:
			path_file = mapAsia;
			break;
		case EUROPE:
			path_file = mapEurope;
			break;
		case AFRICA:
			path_file = mapAfrica;
			break;
		default:
			System.err.println("Map not found!");
		}
		
		try {
			scanner = new Scanner(new File(path_file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		readFile();
		//return newmap;
	}

	/**
	 * Reads the .map file and creates a map and its bases
	 */
	private void readFile()
	{
		String init_line = scanner.nextLine();
		String[] init_line_sep = init_line.split(",");
		String name;

		if(init_line_sep.length == 5)
		{
			name = init_line_sep[0];
			basesCount = Integer.parseInt(init_line_sep[1]);
			center_lat = Double.parseDouble(init_line_sep[2]);
			center_long = Double.parseDouble(init_line_sep[3]);
			radius = Double.parseDouble(init_line_sep[4]);

			System.out.println("New Map : "+name+" centered on "+center_lat+" "+center_long+" radius "+radius+" with "+basesCount+" bases");
			//Map m = new Map(name,center_lat,center_long,radius,basesCount);

			String line;
			String[] coord;
			Double latid;
			Double longit;

			for(int i = 0 ; i < basesCount ; i++)
			{
				line = scanner.nextLine();
				coord = line.split(",");
				name = coord[0];
				latid = Double.parseDouble(coord[1]);
				longit = Double.parseDouble(coord[2]);

				System.out.println("New Base : "+name+" at "+latid+" "+longit);
				//new Base(name,latid,longit);			
			}
			
			//newmap = m;

		}
		else
		{
			System.err.println("Oops, map cannot be loaded! Check your file's header");
		}
	}
}
