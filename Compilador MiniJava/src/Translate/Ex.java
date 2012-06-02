package Translate;

import Temp.Label;

public class Ex extends Exp {
	private Tree.Exp exp;
    
	public Ex(Tree.Exp exp){
		this.exp = exp;
    }

    public Tree.Stm unCx(Label t, Label f) {
    	return null;
    }

    public Tree.Exp unEx() {
    	return exp;
    }

    public Tree.Stm unNx() {
    	return new Tree.EXPSTM(exp);
    }
}
