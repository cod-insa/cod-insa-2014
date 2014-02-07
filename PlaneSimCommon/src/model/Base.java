package model;

import java.io.Serializable;

public class Base extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Base(int id, Coord pos) {
		//super(new View(),id,pos);
		super(id,pos);
	}
}
