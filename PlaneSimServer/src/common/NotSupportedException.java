package common;

public class NotSupportedException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotSupportedException() {
	}
	
	public NotSupportedException(String msg) {
		super(msg);
	}

}
