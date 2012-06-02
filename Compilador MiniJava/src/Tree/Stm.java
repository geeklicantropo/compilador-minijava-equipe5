package Tree;

import List.List;

abstract public class Stm {
	abstract public List<Exp> kids();
	abstract public Stm build(List<Exp> kids);
//    abstract public String print();
}

