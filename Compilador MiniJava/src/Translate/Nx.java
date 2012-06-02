package Translate;

import Temp.Label;
import Tree.CONST;
import Tree.ESEQ;

public class Nx extends Exp {
    private Tree.Stm stm;
    
    public Nx (Tree.Stm stm){
    	this.stm = stm;
    }
    
    public Tree.Stm unNx(){
    	return stm;
    }
    
    public Tree.Exp unEx(){
    	return new ESEQ(stm, new CONST(0));
    }
    
    public Tree.Stm unCx(Label t, Label f) {
    	return null;
    }
}