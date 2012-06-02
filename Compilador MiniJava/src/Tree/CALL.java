package Tree;

import List.List;

public class CALL extends Exp {
	public Exp func;
	public List<Exp> args;
	
	public CALL(Exp f, List<Exp> a) {
//		if (a.tail ==a)
//			System.out.println("Problema na tradução!");
		func=f; 
		args=a;
	}
	
	public List<Exp> kids() {
		return new List<Exp>(func,args);
	}
	
	public Exp build(List<Exp> kids) {
		return new CALL(kids.head,kids.tail);
	}
}

