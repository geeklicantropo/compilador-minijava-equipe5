package List;

import java.util.LinkedList;
import java.util.ListIterator;

public class InterfaceMips<T> {
	public LinkedList<T> ll;
	public List<T> l;
	
	public InterfaceMips(LinkedList<T> llnovo) {
		this.ll = llnovo;
		ListIterator<T> li = ll.listIterator();
		while (li.hasNext()) {
			l = new List<T>(li.next(), l);
		}
	}
	
	public InterfaceMips(List<T> lnovo) {
		this.l = lnovo;
		ll = new LinkedList<T>();
		List<T> it;
		for (it=l; it!=null; it=it.tail) {
			ll.addFirst(it.head);
		}
	}
}
