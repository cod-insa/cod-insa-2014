package common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class CloneView <T /* extends Cloneable */ > {
	
	final T obj;
	
	public CloneView (T obj) {
		this.obj = obj;
	}
	
	
	// Extremely ugly hack resulting from the poor design of Java's Cloneable interface:
	
	/*
	@SuppressWarnings("unchecked")
	public T get ()
			throws	NoSuchMethodException,
					SecurityException,
					IllegalAccessException,
					IllegalArgumentException,
					InvocationTargetException
	{
		Method clone = Object.class.getMethod("clone"); 
		return (T) clone.invoke(obj);
	}
	*/
	
	@SuppressWarnings("unchecked")
	public T get () {
		Method clone;
		try {
			clone = Object.class.getMethod("clone");
			return (T) clone.invoke(obj);
		} catch ( NoSuchMethodException
				| SecurityException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e ) {
			throw new RuntimeException("Could not clone object of this type!", e);
		} 
	}
	
}




