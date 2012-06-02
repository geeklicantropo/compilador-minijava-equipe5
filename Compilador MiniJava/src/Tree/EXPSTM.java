package Tree;

import List.List;

public class EXPSTM extends Stm {
  public Exp exp; 
  public EXPSTM(Exp e) {exp=e;}
  public List<Exp> kids() {return new List<Exp>(exp,null);}
  public Stm build(List<Exp> kids) {
    return new EXPSTM(kids.head);
  }
//  @Override
//  public String print() {
//          return exp.print();
//  }
}

