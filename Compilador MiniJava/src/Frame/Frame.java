package Frame;

import java.util.LinkedList;

import List.List;
import Symbol.Symbol;
import Temp.Label;
import Temp.Temp;

public abstract class Frame {
    public Label name;
    public LinkedList<Access> formals;
    public abstract Frame newFrame(Symbol name, LinkedList<Boolean> args);
    public abstract int wordSize();
    public abstract Access allocLocal(boolean escape);
    public abstract Temp FP();
    public abstract Temp RV();
    public abstract Tree.Exp externalCall(String func, LinkedList<Tree.Exp> args);
//    public abstract String tempMap(Temp temp);
}