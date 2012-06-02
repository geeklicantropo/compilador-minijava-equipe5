package List;

public class List<T> {
	public T head;
	public List<T> tail;
	
	public List(T h, List<T> t) {
		head=h; 
		tail=t; 
	}
}
