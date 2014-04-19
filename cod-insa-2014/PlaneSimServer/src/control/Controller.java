package control;

import game.AutoPilot.Mode;
import game.Game;
import game.GameCountry;
import game.GamePlane;

import java.util.ArrayList;
import java.util.List;

import model.OutOfSyncException;
import players.Player;

import command.AttackCommand;
import command.BuildPlaneCommand;
import command.CancelRequestCommand;
import command.Command;
import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.FollowCommand;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;

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
					apply(c, s, p.id);
	}
	
	public void apply (Command c, Game s, int ai_id) {
//		if (c instanceof MoveCommand) {
//			MoveCommand mc = (MoveCommand) c;
//			//s.getPlane(mc.planeId).autoPilot.goTo(mc.destination);
//			Plane p = s.getPlane(mc.planeId);
//			if (p == null)
//				System.err.println("Error: cannot find the plane of id "+mc.planeId); 
//			else p.autoPilot.goTo(mc.destination, Mode.ATTACK_ON_SIGHT); // FIXME get the right mode
//		} else {
//			throw new Error("Unrecognized command!");
//		}
		try {
			try {
				c.match();
			} catch (MoveCommand mc) {
				s.getPlane(mc.plane.id()).autoPilot.goTo(mc.destination.view, Mode.IGNORE); // FIXME get the right mode
			} catch (LandCommand lc) {

				s.getPlane(lc.plane.id()).autoPilot.landAt(s.getLandable(lc.base.id()));
			}
			//		catch (TakeOffCommand toc) {
			//			s.getPlane(toc.planeId).autoPilot.takeOff();
			//		}
			catch (WaitCommand wc) {
				GamePlane gp = s.getPlane(wc.plane.id());
				gp.autoPilot.goTo(gp.model().position(), Mode.ATTACK_ON_SIGHT);
			} catch (FollowCommand fc) {
				s.getPlane(fc.planeSrc.id()).autoPilot.goTo(s.getPlane(fc.planeTarget.id()), Mode.IGNORE);
			} catch (AttackCommand ac) {
				s.getPlane(ac.planeSrc.id()).autoPilot.attackSpecific(s.getPlane(ac.planeTarget.id()));
			} catch (DropMilitarsCommand dmc) {
				// Something like :
				// s.getPlane(ac.planeSrc.id()).autoPilot.depositAt(-dmc.quantity,dmc.baseTarget);

				s.getPlane(dmc.planeSrc.id()).autoPilot.dropMilitaryAt(s.getBase(dmc.baseTarget.id()), dmc.quantity);

				// Then call this to drop units when plan is over the base and the drop is done
				// s.getPlane(dmc.planeSrc.id()).exchangeResources(-dmc.quantity, 0, false);
			} catch (FillFuelTankCommand fftc) {
				// TODO check fuel required (cf plane capa & base qty)
				s.getPlane(fftc.planeSrc.id()).fillTank(fftc.quantity); // TODONE must take time
			} catch (ExchangeResourcesCommand lrc) {
				// TODO check ressources
				s.getPlane(lrc.planeSrc.id()).exchangeResources(lrc.militarQuantity, lrc.fuelQuantity, lrc.deleteResources);
			} catch (BuildPlaneCommand e) {
				GameCountry country = s.getCountryByAiId(ai_id);
				country.buildPlane(country.new GameRequest(e.requestedType));
			} catch (CancelRequestCommand crc) {
				throw new Error("Not Implemented");
			}
		} catch (Game.EntityNotFound | OutOfSyncException e) {
			Game.log.debug(e.getMessage());
		}
	}
	
	public void addPlayer (Player p) {
		players.add(p);
	}
	
}

























