package Tree;

import List.List;

public class CONST extends Exp {
  public int value;
  public CONST(int v) {value=v;}
  public List<Exp> kids() {return null;}
  public Exp build(List<Exp> kids) {return this;}
}

