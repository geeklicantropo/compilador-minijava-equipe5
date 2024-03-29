package Symbol;

public class Table {
	final int SIZE = 256;
	Binder table[] = new Binder[SIZE];
	Binder top; 
	Symbol marcador = Symbol.symbol("#m");
		
	public void put(Symbol key, Object value) {
		int index=key.hashCode()%SIZE;
		table[index]= new Binder(key, value, table[index]);
		top = new Binder(key, value, top);
	}
	
	public Object get(Symbol key) {
		int index = key.hashCode()%SIZE;
		for (Binder b = table[index]; b!=null; b=b.next) {
			if (key.equals(b.key)) 
				return b;
		}
		return null;
	}
	
	public void beginScope() {
		top = new Binder(marcador, null, top);
	}
	
	public void endScope() {
		while(top.key!=marcador) {
			int index = top.key.hashCode()%SIZE;
			for (Binder b = table[index]; b!=null; b=b.next) {
				if (top.key.equals(b.key)) {
					b = b.next;
					break;
				}
			}
			top = top.next;
		}
		top = top.next;
	}
	
	public ListKeys keys() {
		ListKeys keys = new ListKeys();
		keys.key = null;
		for (int i=0; i<SIZE; i++) {
			for (Binder b = table[i]; b!=null;b = b.next) {
				keys.next = keys.key;
				keys.key = b.key;
			}
		}
		
		return keys;
	}
}