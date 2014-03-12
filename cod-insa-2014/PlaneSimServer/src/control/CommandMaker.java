package control;

import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.WaitCommandData;
import model.Coord;
import command.*;

public class CommandMaker {
	
	static public Coord make(CoordData data) {
		return new Coord(data.x, data.y);
	}
	
	static public Command make(MoveCommandData data) {
		return new MoveCommand(data.pc.idPlane, make(data.posDest).view());
	}

	static public Command make(WaitCommandData data) {
		return new WaitCommand(data.pc.idPlane);
	}
	

	static public Command make(LandCommandData data) {
		return new LandCommand(data.pc.idPlane, data.idBase);
	}	
	
}
