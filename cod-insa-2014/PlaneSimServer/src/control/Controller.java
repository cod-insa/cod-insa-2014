package control;

import command.*;
import game.AutoPilot.Mode;
import game.GamePlane;
import game.Game;

import java.util.ArrayList;
import java.util.List;

import players.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import common.NotSupportedException;

public class Controller {
	
	static private Controller single;
	public static Controller get() { return single; }
	
	long period;
	List<Player> players = new ArrayList<Player>();
	//public final PlayerManager pm;
	
	/*
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	*/
	
	// period: the number of game frames between asking the players their commands
	public Controller(/*PlayerManager pm ,*/ long update_period) {
		//this.pm = null;	//pm; FIXME
		this.period = update_period;
		if (single != null)
			throw new Error("Can only create one controller!");
		single = this;
	}
	
	public void update (Game s) {
		if (s.getCurrentFrame()%period == 0)
			for (Player p: players)
				for (Command c: p.flushCommands())
					apply(c, s);
	}
	
	public void apply (Command c, Game s) {
//		if (c instanceof MoveCommand) {
//			MoveCommand mc = (MoveCommand) c;
//			//s.getPlane(mc.planeId).autoPilot.goTo(mc.destination);
//			Plane p = s.getPlane(mc.planeId);
//			if (p == null)
//				System.err.println("Error: cannot find the plane of id "+mc.planeId); // FIXME command verif
//			else p.autoPilot.goTo(mc.destination, Mode.ATTACK_ON_SIGHT); // FIXME get the right mode
//		} else {
//			throw new Error("Unrecognized command!");
//		}
		try { c.match(); }
		catch (MoveCommand mc) {
			s.getPlane(mc.plane.id()).autoPilot.goTo(mc.destination.view, Mode.IGNORE); // FIXME get the right mode
		}
		catch (LandCommand lc) {
			s.getPlane(lc.plane.id()).autoPilot.landAt(s.getBase(lc.base.id()));
		}
//		catch (TakeOffCommand toc) {
//			s.getPlane(toc.planeId).autoPilot.takeOff();
//		}
		catch (WaitCommand wc) {
			// TODO
			// should be something like 
			/*
			Plane p = s.getPlane(wc.planeId);
			p.autoPilot.goTo(p.modelView.position, Mode.ATTACK_ON_SIGHT);
			*/
		}
		catch (FollowCommand fc) {
			s.getPlane(fc.planeSrcId).autoPilot.goTo(s.getPlane(fc.planeTargetId), Mode.IGNORE);
		}
		catch (AttackCommand ac) {
			s.getPlane(ac.planeSrcId).autoPilot.attackSpecific(s.getPlane(ac.planeTargetId));
		}
//		catch (Command def) {
//			throw new NotSupportedException("Unrecognized command");
//		}
		catch (DropMilitarsCommand e) {
			throw new NotImplementedException();
		} catch (StoreFuelCommand e) {
			throw new NotImplementedException();
		} catch (FillFuelTankCommand e) {
			throw new NotImplementedException();
		} catch (LoadResourcesCommand e) {
			throw new NotImplementedException();
		}
		
		
	}
	
	public void addPlayer (Player p) {
		players.add(p);
	}
	
}

























