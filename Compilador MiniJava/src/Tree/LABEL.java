package Tree;
import List.List;
import Temp.Label;

public class LABEL extends Stm { 
  public Label label;
  public LABEL(Label l) {label=l;}
  public List<Exp> kids() {return null;}
  public Stm build(List<Exp> kids) {
    return this;
  }
//  @Override
//  public String print() {
//          return "LABEL("+ label.print()+" )";
//  }
}

