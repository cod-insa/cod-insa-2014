import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Base;
import model.Base.FullView;
import model.Coord;
import model.Coord.View;
import model.GameSettings;
import model.OutOfSyncException;
import model.Plane;
import model.Plane.BasicView;
import model.Plane.State;
import model.Plane.Type;
import model.ProgressAxis;
import ai.AbstractAI;
import avions.Chasseur;
import avions.MyPlane;
import avions.MyPlane.Etat;
import avions.Transport;
import command.BuildPlaneCommand;
import common.MapView;

public class RouenAI2 extends AbstractAI {

	View principal;
	View secondaire;

	public RouenAI2(String ip, int port) {
		super(ip, port);
	}

	@Override
	public void think() {
		int nbTransports = 1;
		MapView<Integer, model.Base.BasicView> bases;
		MapView<Integer, Plane.FullView> planes;
		MapView<Integer, BasicView> ennemy_planes;
		Map<Integer, Transport> myTransport = new HashMap<>();
		Map<Integer, Chasseur> myChasseur = new HashMap<>();

		HashSet<Integer> support;
		Iterator<Integer> isp;
		Integer base;
		Base.BasicView centre;


		bases = game.getAllBases();

		support = genSupport(bases);
		centre = selectionBaseSecondaire(bases, support);

		while (true) {
			game.updateSimFrame();

			bases = game.getAllBases();
			planes = game.getMyPlanes();
			ennemy_planes = game.getEnnemyPlanes();

			Iterator<Integer> iSommet = support.iterator();

			int ie;
			int iemax = iSommet.next();
			double emax  = evalCapture(bases.get(iemax),centre);

			while(iSommet.hasNext()){
				ie = iSommet.next();
				if(emax< evalCapture(bases.get(ie),centre)){
					iemax = ie;
					emax = evalCapture(bases.get(ie),centre);
				}
			}

			for (Integer i : planes.keySetView()) {
				MyPlane p = null;
				Plane.FullView plane = planes.get(i);
				if (plane.type == Type.COMMERCIAL) {
					if (myTransport.containsKey(i)) {
						p = myTransport.get(i);
						p.setPlaneServer(planes.get(i));
					} else {
						p = new Transport(planes.get(i), game);
					}
					if(p!= null)
						myTransport.put(i, (Transport) p);
				} else {
					if (myChasseur.containsKey(i)) {
						p = myChasseur.get(i);
						p.setPlaneServer(planes.get(i));
					} else {
						p = new Chasseur(planes.get(i), game);
					}
					if(p!= null)
						myChasseur.put(i, (Chasseur) p);
				}
			}

			List<Integer> llll = new ArrayList<>();
			for (Integer k : myTransport.keySet()) {
				try {
					Transport plane = myTransport.get(k);
					if (plane.getEtat() == Etat.IDLE) {
						plane.setEtat(Etat.ACTIVE);
						plane.setBaseOrig(game.getCountry());



						plane.setBaseDest(bases.get(iemax));
					}
					plane.reagir();
				}
				catch(OutOfSyncException e) {
					System.out.println("out of sync");
					llll.add(k);
				}
			}
			for (Integer integer : llll) {
				myTransport.remove(integer);
			}
			llll = new ArrayList<>();
			for (Integer k : myChasseur.keySet()) {
				try {
					Chasseur plane = myChasseur.get(k);
					if (plane.getEtat() == Etat.IDLE) {
						plane.setEtat(Etat.ACTIVE);
						plane.setBaseOrig(game.getCountry());
						plane.setBaseDest(bases.get(iemax));
						plane.setCible(myTransport.values().iterator().next());
					}
					plane.reagir();
				}
				catch(OutOfSyncException e) {
					System.out.println("out of sync");
					llll.add(k);
				}
			}

			for (Integer integer : llll) {
				myChasseur.remove(integer);
			}
			System.out.println("nbTransport : " + myTransport.size());
			System.out.println("nbChasseur : " + myChasseur.size());
			System.out.println("nb total : " + planes.size());
			for (Integer integer : planes.keySetView()) {
				System.out.println(integer);
			}
			if (myTransport.size() < 1000*GameSettings.MAX_PLANES_PER_COUNTRY) {
				System.out.println("Transport creation");
				game.sendCommand(new BuildPlaneCommand(Plane.Type.COMMERCIAL));
				game.sendCommand(new BuildPlaneCommand(Plane.Type.MILITARY));
			}

			game.updateSimFrame();
		}

	}

	/*
	 * @return la base concernee par la position ou null sinon
	 */
	public Base.BasicView contientBase(Coord c,
			MapView<Integer, Base.BasicView> bases) {
		for (Base.BasicView b : bases.valuesView()) {
			if (b.position == c.view()) {
				return b;
			}
		}
		return null;
	}

	public HashSet<Integer> genSupport(MapView<Integer, Base.BasicView> bases) {
		Integer idBase;
		Integer idBase1 = -1;
		HashSet<Integer> support = new HashSet<Integer>();
		HashSet<Integer> sbases = new HashSet<Integer>();
		Iterator<Base.BasicView> i1 = bases.valuesView().iterator();
		Iterator<Integer> i;
		Iterator<ProgressAxis.Oriented> iAxe;
		boolean found;
		while (i1.hasNext()) {
			sbases.add(i1.next().id());
		}

		i = sbases.iterator();
		while (i.hasNext()) {// tant qu'il y a des bases dans sbases
			idBase = i.next();
			// on vérifie qu'au moins un des voisins est n'est pas dans le
			// support
			iAxe = bases.get(idBase).axes().iterator();
			found = false;
			while (iAxe.hasNext()) {
				idBase1 = iAxe.next().next().id();
				if (!support.contains(idBase1)) {
					found = true;
					break;
				}

			}

			if (found) {// on a trouvé deux bases qui ne sont pas dans le
				// support, on les ajoutes et on les supprime du set
				support.add(idBase);
				support.add(idBase1);
				sbases.remove(idBase);
				sbases.remove(idBase1);
			} else {// la base sélectionée n'est pas dans le support et n'est
				// connectée à aucune base hors du support
				sbases.remove(idBase);
			}
			i = sbases.iterator();
		}

		// on retire les sommets du support qui sont à l'intérieur de celui-ci
		i = support.iterator();
		while (i.hasNext()) {
			idBase = i.next();
			iAxe = bases.get(idBase).axes().iterator();
			found = false;
			while (iAxe.hasNext()) {// on cherche un sommet qui n'est pas dans
				// le support
				idBase1 = iAxe.next().next().id();
				if (!support.contains(idBase1)) {
					found = true;
					break;
				}
			}

			if (!found) {
				support.remove(idBase);
				i = support.iterator();

			}
		}

		return support;
	}

	public double distance(Coord.View a, Coord b) {
		return Math.sqrt((a.x() - b.x()) * (a.x() - b.x()) + (a.y() - b.y())
				* (a.y() - b.y()));
	}

	public double distance(Coord.View a, Coord.View b) {
		return Math.sqrt((a.x() - b.x()) * (a.x() - b.x()) + (a.y() - b.y())
				* (a.y() - b.y()));
	}

	public double evalCapture(Base.BasicView base, Base.BasicView baseSecondaire) {
		double temps = distance(base.position, baseSecondaire.position)
				/ model.Plane.Type.COMMERCIAL.maxSpeed;
		int nbVoisins = base.axes().size();

		//if(base.ownerId() == game.getCountry().ownerId()){
		if(base instanceof Base.FullView){
			return nbVoisins /( temps * (((Base.FullView)base).fuelInStock()+1));
		}
		else{
			if(base.id() == baseSecondaire.id())
				return Double.MAX_VALUE;
			else
				return nbVoisins / temps;
		}
	}

	public Base.BasicView selectionBaseSecondaire(
			MapView<Integer, Base.BasicView> bases, HashSet<Integer> support) {
		// Calcul des coordonnées barycentriques
		double x = 0., y = 0.;
		double dmin;
		Integer idBase, idMin;
		View c;
		Coord co;
		Iterator<Integer> i = support.iterator();
		while (i.hasNext()) {
			c = bases.get(i.next()).position;
			x += c.x();
			y += c.y();
		}
		x /= support.size();
		y /= support.size();

		co = new Coord(x, y);
		// Calcul de la base la plus proche de ces coordonnées
		i = support.iterator();
		idMin = i.next();
		dmin = distance(bases.get(idMin).position, co);
		while (i.hasNext()) {
			idBase = i.next();
			if (dmin > distance(bases.get(idBase).position, co)) {
				idMin = idBase;
				dmin = distance(bases.get(idMin).position, co);
			}
		}
		return bases.get(idMin);
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage : java AttackAI ip port");
			System.exit(1);
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);

		AbstractAI ai = new RouenAI(ip, port);
		ai.think();
	}
}
