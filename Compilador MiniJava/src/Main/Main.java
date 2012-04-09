package Main;
import Parser.MiniJavaParser;
import Parser.ParseException;
import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String [] args) {
      try {
          Program root = new MiniJavaParser(System.in).Program();
          root.accept(new PrettyPrintVisitor());
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
