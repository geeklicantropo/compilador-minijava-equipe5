package syntaxtree;
import visitor.TranslateVisitor;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Not extends Exp {
  public Exp e;
  
  public Not(Exp ae) {
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
  
  public Translate.Exp accept(TranslateVisitor v){
	  return v.visit(this);
  }
}
