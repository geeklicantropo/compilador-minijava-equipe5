package Tree;

import List.List;

public class SEQ extends Stm {
  public Stm left, right;
  public SEQ(Stm l, Stm r) { left=l; right=r; }
  public List<Exp> kids() {throw new Error("kids() not applicable to SEQ");}
  public Stm build(List<Exp> kids) {throw new Error("build() not applicable to SEQ");}
  
//  @Override
//  public String print() {
//          String rt = "SEQ( "+left.print() + ", "+ right.print()+ " )";
//          return rt;
//  }
}

