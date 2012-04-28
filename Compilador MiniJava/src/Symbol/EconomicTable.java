package Symbol;

//a classe economicTable não usa beginScope nem EndScope
public class EconomicTable {
	final int SIZE = 50;
	Binder table[] = new Binder[SIZE];
	
	public void put(Symbol key, Object value) {
		int index=key.hashCode()%SIZE;
		table[index]= new Binder(key, value, table[index]);
	}
	
	public Object get(Symbol key) {
		int index = key.hashCode()%SIZE;
		for (Binder b = table[index]; b!=null; b=b.next) {
			if (key.equals(b.key)) 
				return b;
		}
		return null;
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
