package Assem;

import List.Helper;
import List.List;
import Temp.Label;
import Temp.Temp;

public class OPER extends Instr {
   public List<Temp> dst;   
   public List<Temp> src;
   public Targets jump;

   public OPER(String a, List<Temp> d, List<Temp> s, List<Label> j) {
      assem=a; dst=d; src=s; jump=new Targets(j);
   }
   
   public OPER(String a, Temp[] d, Temp[] s, Label[] j) {
	   Helper<Temp> h1 = new Helper<Temp>();
	   Helper<Label> h2 = new Helper<Label>();
	   assem=a; dst=h1.getList(d); src=h1.getList(s); jump=new Targets(h2.getList(j));
   }
   
   public OPER(String a, List<Temp> d, List<Temp> s) {
      assem=a; dst=d; src=s; jump=null;
   }
   
   public OPER(boolean m,String a, List<Temp> d, List<Temp> s, List<Label> j) {
	      assem=a; dst=d; src=s; jump=new Targets(j); /*jumps = j;*/
	      branch = m;
   }
	   
   public OPER(boolean m,String a, List<Temp> d, List<Temp> s) {
	   assem=a; dst=d; src=s; jump=null;
	   branch = m;
   }

 
   public List<Temp> use() {return src;}
   public List<Temp> def() {return dst;}
   public Targets jumps() {return jump;}

}
