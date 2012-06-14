package List;

public class Helper<T> {
	// transforma um vetor em uma lista
	public List<T> getList(T[] ts) {
		List<T> lista = null;
		for (int i=ts.length-1; i>=0; i--) {
			lista = new List<T>(ts[i], lista);
		}
		return lista;
	}
}
