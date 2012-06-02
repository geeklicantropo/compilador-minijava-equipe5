package Mips;

import Frame.Access;
import Temp.Temp;
import Tree.Exp;
import Tree.TEMP;

public class InReg extends Access {
	Temp temp;
	
	public InReg(Temp t) {
        temp =t;
    }

    public Exp exp(Exp framePtr) {
        return new TEMP(temp);
    }
    
    public String toString() {
        return temp.toString();
    }
}
