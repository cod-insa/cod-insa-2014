package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

import model.Coord;
import common.CoordConverter;

/**
 * MapLoader
 * Loads a map from a file and creates the bases.
 * @author nicolas v
 *
 */
public class MapLoader {

	//IO
	private Scanner scanner;
	private String path_file;

	//path
	private String location = "/rsc/maps/";
	public static String[] maps = {"france","usa"};//,"europe","asia","africa"};

	//Object for the game
	private World w;
	private Game g;
	private MapInfo m;
	private CoordConverter converter;


	public class MapInfo{
		//general information about the map
		int basesCount;
		double center_lat;
		double center_long;
		double min_lat;
		double min_long;
		double max_lat;
		double max_long;
		int web_zoom;
	}

	/**
	 * Let check if the map name is correct...
	 */
	public static boolean checkMapName(String name)
	{
		for(int i = 0 ; i< maps.length ; i++)
			if(maps[i].equals(name))
				return true;
		return false;
	}

	/**
	 * Specify here the map you want to load
	 * @param mapName, from enum AvailableMaps
	 */
	public MapLoader(Game game, String mapName) 
	{
		this.g = game;
		this.w = game.getWorld();
		this.m = new MapInfo();
		
		
		if(checkMapName(mapName))
		{
			path_file = location+mapName+".map";
			System.out.println(MapLoader.class.getResource(location+mapName+".map"));
			try {
//				scanner = new Scanner(new File(path_file));
				scanner = new Scanner(new File(MapLoader.class.getResource(location+mapName+".map").toURI()));
			} catch (FileNotFoundException | URISyntaxException e) {
				throw new Error(e);
			}
			
			readFile();
		}
		else
		{
			//System.err.println("Map not found");
			Game.log.error("Map not found "+mapName);
		}
	}

	/**
	 * Reads the .map file and creates a map and its bases
	 */
	private void readFile()
	{
		String init_line = scanner.nextLine();
		String[] init_line_sep = init_line.split(",");
		String name;

		if(init_line_sep.length == 9)
		{
			name = init_line_sep[0];
			m.basesCount = Integer.parseInt(init_line_sep[1]);
			m.center_lat = Double.parseDouble(init_line_sep[2]);
			m.center_long = Double.parseDouble(init_line_sep[3]);
			m.min_lat = Double.parseDouble(init_line_sep[4]);
			m.min_long = Double.parseDouble(init_line_sep[5]);
			m.max_lat = Double.parseDouble(init_line_sep[6]);
			m.max_long = Double.parseDouble(init_line_sep[7]);
			m.web_zoom = Integer.parseInt(init_line_sep[8]);
			
			Game.log.info("New Map : "+name+" centered on "+m.center_lat+" "+m.center_long+" with "+m.basesCount+" bases");
			this.converter = new CoordConverter(m.min_lat, m.max_lat, m.min_long, m.max_long);
			this.converter.setWorldDimensions(w.getWidth(),w.getHeight());
			//to fit the admin interface (if not called, 1 is used to multiply values).
			

			String line;
			String[] coord;
			Double latid;
			Double longit;
			Coord.Unique newBaseCoord;
			
			for(int i = 0 ; i < m.basesCount ; i++)
			{
				line = scanner.nextLine();
				coord = line.split(",");
				name = coord[0];
				latid = Double.parseDouble(coord[1]);
				longit = Double.parseDouble(coord[2]);

				//System.out.println("New Base : "+name+" at "+latid+" "+longit);
				Game.log.debug("New Base : "+name+" at "+latid+" "+longit);
				
				newBaseCoord = converter.toCartesianUnique(longit, latid);
				this.w.bases.add(new GameBase(g, newBaseCoord,name));
				
				//System.out.println("New Base : "+newBaseCoord.x()+" "+ newBaseCoord.y());
				//Game.log.debug("New Base : "+newBaseCoord.x()+" "+ newBaseCoord.y());
				//Game.log.debug("New Base : "+latid+" "+longit);
				//Game.log.debug("New Base : "+converter.getLatFromUnique(newBaseCoord)+" "+ converter.getLongFromUnique(newBaseCoord));
				
				
			}
		}
		else
		{
			//System.err.println("Oops, map cannot be loaded! Check your file's header");
			throw new Error("Oops, map cannot be loaded! Check your file's header");
		}
	}

	public CoordConverter getConverter() {
		return converter;
	}

	public MapInfo getM() {
		return m;
	}

	/*public static void main(String[] args) {
		MapLoader map = new MapLoader("france");
	}*/

}




