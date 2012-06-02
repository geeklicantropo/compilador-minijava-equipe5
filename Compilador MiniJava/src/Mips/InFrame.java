package Mips;

import Frame.Access;
import Tree.BINOP;
import Tree.CONST;
import Tree.Exp;
import Tree.MEM;

public class InFrame extends Access {
	int offset; 
	
	public InFrame (int off) {
        offset = off;
    }

    public Exp exp(Exp framePtr) {
        if ( offset >= 0 )
            return new MEM(new BINOP(BINOP.PLUS, framePtr, new CONST(offset)));
        else
            return new MEM(new BINOP(BINOP.MINUS, framePtr, new CONST(-offset)));
    }

    public String toString() {
        Integer offset = new Integer(this.offset);
        return offset.toString();
    }
}
