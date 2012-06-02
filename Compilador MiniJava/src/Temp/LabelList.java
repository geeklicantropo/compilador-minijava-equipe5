package Temp;

public class LabelList {
   public Label head;
   public LabelList tail;
   public LabelList(Label h, LabelList t) {head=h; tail=t;}
   public String print() {
       String rt = new String();
       for(LabelList l = this; l != null; l = (LabelList) l.tail ){
               rt += l.head.print()+ " ";
       }
       return rt;
   }
}

