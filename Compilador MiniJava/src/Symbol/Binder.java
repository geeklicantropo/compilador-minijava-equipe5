package Symbol;

public class Binder {
	Symbol key;
	Object binding;
	Binder next;
	boolean isMethod;
	Binder(Symbol k, Object b, Binder n) {
		key = k;
		binding=b;
		next=n;
		isMethod=false;
	}
	
	Binder(Symbol k, Object b, boolean isM, Binder n) {
		key = k;
		binding=b;
		next=n;
		isMethod=isM;
	}
}