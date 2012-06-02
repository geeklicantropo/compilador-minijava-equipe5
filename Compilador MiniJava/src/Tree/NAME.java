package Tree;
import List.List;
import Temp.Label;

public class NAME extends Exp {
  public Label label;
  public NAME(Label l) {label=l;}
  public List<Exp> kids() {return null;}
  public Exp build(List<Exp> kids) {return this;}
}

