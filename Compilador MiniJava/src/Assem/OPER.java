package Assem;

import List.Helper;
import List.List;

public class OPER extends Instr {
   public List<Temp.Temp> dst;   
   public List<Temp.Temp> src;
   public Targets jump;

   public OPER(String a, List<Temp.Temp> d, List<Temp.Temp> s, List<Temp.Label> j) {
      assem=a; dst=d; src=s; jump=new Targets(j);
   }
   
   public OPER(String a, Temp.Temp[] d, Temp.Temp[] s, Temp.Label[] j) {
	   Helper<Temp.Temp> h1 = new Helper<Temp.Temp>();
	   Helper<Temp.Label> h2 = new Helper<Temp.Label>();
	   assem=a; dst=h1.getList(d); src=h1.getList(s); jump=new Targets(h2.getList(j));
   }
   
   public OPER(String a, List<Temp.Temp> d, List<Temp.Temp> s) {
      assem=a; dst=d; src=s; jump=null;
   }

   public List<Temp.Temp> use() {return src;}
   public List<Temp.Temp> def() {return dst;}
   public Targets jumps() {return jump;}

}
