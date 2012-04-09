package Symbol;

public class Binder {
	Symbol key;
	Object binding;
	Binder next;
	Binder(Symbol k, Object b, Binder n) {
		key = k;
		binding=b;
		next=n;
	}
}