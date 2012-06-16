package Frame;

import java.util.LinkedList;

import Symbol.Symbol;
import Temp.Label;
import Temp.Temp;
import Temp.TempMap;

public abstract class Frame implements TempMap { 
    public Label name;
    public LinkedList<Access> formals;
    public abstract Frame newFrame(Symbol name, LinkedList<Boolean> args);
    public abstract int wordSize();
    public abstract Access allocLocal(boolean escape);
    public abstract Temp FP();
    public abstract Temp RV();
    public abstract Tree.Exp externalCall(String func, LinkedList<Tree.Exp> args);
//    public abstract String tempMap(Temp temp);
//    public abstract Temp[] registers();
//    public abstract Tree.Exp externallCall(String func, List<Tree.Exp> args);
//    public abstract Tree.Stm procEntryExit1(Tree.Stm body);
//    public abstract List<Assem.Instr> procEntryExit2(List<Assem.Instr> body);
//    abstract public Proc procEntryExit3(List<Assem.Instr> body);
//    abstract public List<Assem.Instr> codegen(Tree.Stm stm);
}
