package Tree;

import List.List;

public class ESEQ extends Exp {
  public Stm stm;
  public Exp exp;
  public ESEQ(Stm s, Exp e) {stm=s; exp=e;}
  public List<Exp> kids() {throw new Error("kids() not applicable to ESEQ");}
  public Exp build(List<Exp> kids) {throw new Error("build() not applicable to ESEQ");}
//  @Override
//  public String print() {
//          return "ESEQ("+ stm.print()+ " ,"+exp.print()+ ")";
//  }
}

