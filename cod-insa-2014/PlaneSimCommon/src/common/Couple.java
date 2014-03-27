package common;

/**
 * Created by LP on 27/03/2014.
 */
public class Couple<A, B> implements ShallowViewable<Couple.View> {
	
	public A first;
	public B second;
	
	public Couple(A first, B second) {
		this.first = first;
		this.second = second;
	}
	
	public Couple(View src) {
		this.first = src.first();
		this.second = src.second();
	}
	
	public class View implements ShallowViewable.ShallowView {
		public A first() { return first; }
		public B second() { return second; }
	}
	
	@Override
	public View shallowView() {
		return new View();
	}
	
	public static <A extends Viewable<AV>, AV extends Viewable.View, B extends Viewable<BV>, BV extends Viewable.View>
	Couple<AV,BV>.View
		view(Couple<A,B> src)
	{
		return new Couple<AV,BV>(src.first.view(), src.second.view()).shallowView();
	}
	
	
	
	public int hashCode() {
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Couple) {
			Couple otherCouple = (Couple) other;
			return
					((  this.first == otherCouple.first ||
							( this.first != null && otherCouple.first != null &&
									this.first.equals(otherCouple.first))) &&
							(	this.second == otherCouple.second ||
									( this.second != null && otherCouple.second != null &&
											this.second.equals(otherCouple.second))) );
		}
		
		return false;
	}
	
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}
	
}