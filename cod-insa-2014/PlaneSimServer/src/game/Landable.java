package game;

import model.AbstractBase;

public interface Landable {
	public AbstractBase model();
	public MaterialGameEntity asMaterialGameEntity();
	public int landingCapacity();
}
