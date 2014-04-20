package game;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Coord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.CoordConverter;

/**
 * MapLoader
 * Loads a map from a file and creates the bases.
 * @author nicolas v
 *
 */
public class MapLoader {

	static final Logger log = LoggerFactory.getLogger(MapLoader.class);
	
	//IO
	private Scanner scanner;
	private String path_file;

	//path
	private String location = "rsc/maps/";
	//public static String[] maps = {"france","usa","europe","middleeast"};

	//Object for the game
	private World w;
	private Game g;
	private MapInfo m;
	private CoordConverter converter;


	public class MapInfo{
		//general information about the map
		String name;
		int basesCount;
		int axisCount;
		int max_num_player;
		double center_lat;
		double center_long;
		double min_lat;
		double min_long;
		double max_lat;
		double max_long;
		int web_zoom;
		double coef_coord;
		
		public int getBasesCount() {
			return basesCount;
		}
		public int getAxisCount() {
			return axisCount;
		}
		
		public double getCenter_lat() {
			return center_lat;
		}
		public double getCenter_long() {
			return center_long;
		}
		public int getWeb_zoom() {
			return web_zoom;
		}
		public String getName() {
			return name;
		}
		
	}

	/**
	 * Let check if the map name is correct...
	 */
	/*public static boolean checkMapName(String name)
	{
		for(int i = 0 ; i< maps.length ; i++)
			if(maps[i].equals(name))
				return true;
		return false;
	}*/

	/**
	 * Specify here the map you want to load
	 * @param mapName, from enum AvailableMaps
	 */
	public MapLoader(Game game, String mapName) throws FileNotFoundException,URISyntaxException
	{
		this.g = game;
		this.w = game.getWorld();
		this.m = new MapInfo();
		this.m.name = mapName;
		
		
		/*if(checkMapName(mapName))
		{*/
			//System.out.println(MapLoader.class.getResource(location+mapName+".map"));
			//try {
//				scanner = new Scanner(new File(path_file));
				
				// Need an InputStream to make it works in a Jar.
				InputStream f = MapLoader.class.getClassLoader().getResourceAsStream(location+mapName+".map");
				if (f == null)
					throw new FileNotFoundException("Map not found exception");
				scanner = new Scanner(f);
			/*} catch (FileNotFoundException | URISyntaxException e) {
				throw new Error(e);
			}*/
			
			readFile();
		//}
		/*else
		{
			//System.err.println("Map not found");
			log.error("Map not found "+mapName);
		}*/
	}

	/**
	 * Reads the .map file and creates a map and its bases
	 */
	private void readFile()
	{
		String init_line = scanner.nextLine();
		String[] init_line_sep = init_line.split(",");
		String name;

		if(init_line_sep.length == 12)
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
			m.axisCount = Integer.parseInt(init_line_sep[9]);
			m.max_num_player = Integer.parseInt(init_line_sep[10]);
			m.coef_coord = Double.parseDouble(init_line_sep[11]);
			
			log.info("New Map : "+name+" centered on "+m.center_lat+" "+m.center_long+" with "+m.basesCount+" bases");
			

			this.converter = new CoordConverter(m.min_lat, m.max_lat, m.min_long, m.max_long,m.coef_coord*Settings.WORLD_ZOOM);
			w.width = converter.getWidth();
			w.height = converter.getHeight();
			
			
			//to fit the admin interface (if not called, 1 is used to multiply values).
			
			String line;
			String[] coord;
			Double latid;
			Double longit;
			Coord.Unique newBaseCoord;
			
			for (int i = 0; i < m.max_num_player;i++)
			{
				line = scanner.nextLine();
				coord = line.split(",");
				name = coord[0];
				latid = Double.parseDouble(coord[1]);
				longit = Double.parseDouble(coord[2]);
				newBaseCoord = converter.toCartesianUnique(longit, latid);
				GameCountry gc = new GameCountry(g, newBaseCoord,name,i+1);
				gc.model().ownerId(i+1); // FIXME Do better ?
				this.w.countries.add(gc);
			}
			/*this.converter = new CoordConverter(m.min_lat, m.max_lat, m.min_long, m.max_long,Settings.WORLD_ZOOM);
			w.width = converter.getWidth();
			w.height = converter.getHeight();*/
			Map<String,GameBase> basesByName = new HashMap<String,GameBase>();
			
			for(int i = 0 ; i < m.basesCount ; i++)
			{
				line = scanner.nextLine();
				coord = line.split(",");
				name = coord[0];
				latid = Double.parseDouble(coord[1]);
				longit = Double.parseDouble(coord[2]);

				//System.out.println("New Base : "+name+" at "+latid+" "+longit);
				////
				//log.debug("New Base : "+name+" at "+latid+" "+longit);
				////
				
				newBaseCoord = converter.toCartesianUnique(longit, latid);
				GameBase gb = new GameBase(g, newBaseCoord,name);
				
				this.w.bases.add(gb);
				basesByName.put(name, gb);
				
				//System.out.println("New Base : "+newBaseCoord.x()+" "+ newBaseCoord.y());
				//Game.log.debug("New Base : "+newBaseCoord.x()+" "+ newBaseCoord.y());
				//Game.log.debug("New Base : "+latid+" "+longit);
				//Game.log.debug("New Base : "+converter.getLatFromUnique(newBaseCoord)+" "+ converter.getLongFromUnique(newBaseCoord));
			}
			
			String[] axis;
			for (int i = 0; i < m.axisCount; i++) {
				line = scanner.nextLine();
				axis = line.split(",");
				// axis[0] : base1
				// axis[1] : base2
				
				
				
				if (basesByName.containsKey(axis[0]) && basesByName.containsKey(axis[1]))
					new GameAxis(g, basesByName.get(axis[0]), basesByName.get(axis[1]));
				else
					throw new Error("Base name is unknown");
					
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




