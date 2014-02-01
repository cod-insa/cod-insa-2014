package common;

import java.util.Iterator;
import java.util.List;

public class ListView<T> implements Iterable<T> {
	
	List<T> ls;
	
	public ListView(List<T> ls) {
		this.ls = ls;
	}

	@Override
	public Iterator<T> iterator() {
		return ls.iterator();
	}
	
	public T get (int i) {
		return ls.get(i);
	}

}
