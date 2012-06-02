package Tree;

import List.List;

public class TEMP extends Exp {
  public Temp.Temp temp;
  public TEMP(Temp.Temp t) {temp=t;}
  public List<Exp> kids() {return null;}
  public Exp build(List<Exp> kids) {return this;}
}

