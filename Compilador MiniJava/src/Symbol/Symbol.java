package Symbol;

public class Symbol {
	private String name;
	private static java.util.Dictionary dict = new java.util.Hashtable();
	
	public Symbol(String n) {
		name=n;
	}
	
	public String toString() {
		return name;
	}
		
	public static Symbol symbol(String n) {
		String u = n.intern();
		Symbol s = (Symbol)dict.get(u);
		
		if (s==null) {
			s = new Symbol(u);
			dict.put(u, s);
		}
		
		return s;
	}
}
