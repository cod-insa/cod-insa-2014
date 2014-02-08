package common;



public class Accessors {
	
	/*
	 * Accessor interface
	 */
	static public interface Access<T> extends RAccess<T>, WAccess<T> { }
	
	/*
	 * Faillible Accessor interface; accesses can throw special Failure exceptions
	 */
	static public interface FAccess<T> extends FRAccess<T>, FWAccess<T> { }
	

	static public interface RAccess<T> {
		
		public T get();
		
	}
	
	static public interface WAccess<T> {
		
		public void set (T obj);
		
	}

	static class AccessFailureException extends Exception {
		private static final long serialVersionUID = 1L;
		public AccessFailureException() { super(); }
		public AccessFailureException(String msg) { super(msg); }
		public AccessFailureException(Exception cause) { super(cause); }
	}
	static public interface FRAccess<T> {
		static class Failure extends AccessFailureException {
			private static final long serialVersionUID = 1L;
			public Failure() { super(); }
			public Failure(String msg) { super(msg); }
			public Failure(Exception cause) { super(cause); }
		}
		public T get() throws FRAccess.Failure;
	}
	static public interface FWAccess<T> {
		static class Failure extends AccessFailureException {
			private static final long serialVersionUID = 1L;
			public Failure() { super(); }
			public Failure(String msg) { super(msg); }
			public Failure(Exception cause) { super(cause); }
		}
		public void set (T obj) throws FWAccess.Failure;
	}
	
}


