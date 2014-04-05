package common;

public class NotSupportedException extends RuntimeException { //Exception {
	
	private static final long serialVersionUID = 1L;

	public NotSupportedException() {
	}
	
	public NotSupportedException(String msg) {
		super(msg);
	}
	
	public static NotSupportedException notImplementedYet() {
		return new NotSupportedException("Not implemented yet!");
	}

}
