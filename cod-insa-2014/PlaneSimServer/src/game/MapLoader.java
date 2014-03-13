package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import model.Coord;

/**
 * MapLoader
 * Loads a map from a file and creates the bases.
 * 
 * TODO WebInterface will ask for center and zoom information
 * 
 * @author nicolas v
 *
 */
public class MapLoader {

	//IO
	private Scanner scanner;
	private String path_file;

	//general information about the map
	private int basesCount;
	private double center_lat;
	private double center_long;
	private double min_lat;
	private double min_long;
	private double max_lat;
	private double max_long;
	private int web_zoom;
	
	//Specify here available maps locations
	private AvailableMaps map;
	private static String location = "maps/";
	private static String mapFrance = location+"france.map";
	private static String mapUsa = location+"usa.map";
	private static String mapAfrica = location+"africa.map";
	private static String mapAsia = location+"asia.map";
	private static String mapEurope = location+"europe.map";
	
	//The new loaded map to use in the game
	//private Map newmap;
	
	public static class Map {
		
		// What we load from a file is a list of base coordinates
		private List<Coord> newmap;
		
	}
	
	/**
	 * Available maps
	 * @author NVA
	 */
	public enum AvailableMaps{
		FRANCE,
		USA/*,
		AFRICA,
		ASIA,
		EUROPE*/
	};

	/**
	 * Specify here the map you want to load
	 * @param mapname, from enum AvailableMaps
	 */
	public MapLoader(AvailableMaps mapname) 
	{
		switch (mapname) {
		case FRANCE:
			path_file = mapFrance;
			break;
		case USA:
			path_file = mapUsa;
			break;
		/*case ASIA:
			path_file = mapAsia;
			break;
		case EUROPE:
			path_file = mapEurope;
			break;
		case AFRICA:
			path_file = mapAfrica;
			break;*/
		default:
			map = AvailableMaps.FRANCE;
			System.err.println("Map not found!");
		}
		
		try {
			scanner = new Scanner(new File(path_file));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			throw new Error(e);
		}
		
		readFile();
		//return newmap;
		//return readFile();
	}

	/**
	 * Reads the .map file and creates a map and its bases
	 */
	private Map readFile()
	{
		String init_line = scanner.nextLine();
		String[] init_line_sep = init_line.split(",");
		String name;
		
		Map map = new Map();
		
		if(init_line_sep.length == 9)
		{
			name = init_line_sep[0];
			basesCount = Integer.parseInt(init_line_sep[1]);
			center_lat = Double.parseDouble(init_line_sep[2]);
			center_long = Double.parseDouble(init_line_sep[3]);
			min_lat = Double.parseDouble(init_line_sep[4]);
			min_long = Double.parseDouble(init_line_sep[5]);
			max_lat = Double.parseDouble(init_line_sep[6]);
			max_long = Double.parseDouble(init_line_sep[7]);
			web_zoom = Integer.parseInt(init_line_sep[8]);

			System.out.println("New Map : "+name+" centered on "+center_lat+" "+center_long+" with "+basesCount+" bases");
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
			return null;

		}
		else
		{
			//System.err.println("Oops, map cannot be loaded! Check your file's header");
			throw new Error("Oops, map cannot be loaded! Check your file's header");
		}
	}
	
	public static void main(String[] args) {
		MapLoader map = new MapLoader(AvailableMaps.FRANCE);
	}
}




