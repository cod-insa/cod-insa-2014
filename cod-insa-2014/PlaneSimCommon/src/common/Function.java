package common;

public interface Function<A, R> {
	public R apply (A arg);
	
	public static abstract class Void<A> implements Function<A, java.lang.Void> {
		
		public final java.lang.Void apply (A arg) {
			exec(arg);
			return null;
		}
		
		public abstract void exec (A arg);
		
	}
	
}
