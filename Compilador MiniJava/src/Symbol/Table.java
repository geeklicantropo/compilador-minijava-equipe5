package Symbol;

public class Table {
	final int SIZE = 512;
	Binder table[] = new Binder[SIZE];
	Binder top; 
	Symbol marcador = Symbol.symbol("#m");
	java.util.Enumeration keys;
		
	public void put(Symbol key, Object value) {
		int index=key.hashCode()%SIZE;
		table[index]= new Binder(key, value, table[index]);
		top = new Binder(key, value, top);
	}
	
	public Object get(Symbol key) {
		int index = key.hashCode();
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
			int index = top.key.hashCode();
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
	
	public java.util.Enumeration keys() {
		return keys;
	}
}
