package control;

import genbridge.CoordData;
import genbridge.MoveCommandData;
import genbridge.WaitCommandData;
import model.Coord;

import command.Command;
import command.MoveCommand;
import command.WaitCommand;

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
	
	
	
}
