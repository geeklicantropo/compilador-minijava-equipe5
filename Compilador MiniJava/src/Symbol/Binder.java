package Symbol;

public class Binder {
	public Symbol key;
	public Object binding;
	Binder next;
	public Binder(Symbol k, Object b, Binder n) {
		key = k;
		binding=b;
		next=n;
	}
}