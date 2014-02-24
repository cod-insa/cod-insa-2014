package common;

/*
 * Contract: ALL CLASSES IMPLEMENTING THIS MUST PERFORM DEEP COPIES
 * 
 */

public interface Copyable<T> {
	/*
	public final class Unique<T extends Copyable<T>> {
		public common.Unique<T> make(T original) {
			return new common.Unique.Copy<T>(original);
		}
	}
	*/
	
	/*
	 * Returns an independent object sharing NO mutable data with the original
	 * Note that sharing Immutable<T> objects is fine (or any other immutable object)
	 */
	T copy();
	
}
