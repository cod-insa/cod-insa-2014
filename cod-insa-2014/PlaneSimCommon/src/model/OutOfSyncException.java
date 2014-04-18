package model;

/**
 * Created by LP on 18/04/2014.
 */
public class OutOfSyncException extends RuntimeException {
	public OutOfSyncException(String msg) {
		super("You tried to access out of sync data: "+msg);
	}
}
