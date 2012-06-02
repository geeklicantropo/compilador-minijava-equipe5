package syntaxtree;
import visitor.TranslateVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Exp {
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  
  public abstract Translate.Exp accept(TranslateVisitor v);
}
