package Assem;

import List.List;

public class LABEL extends Instr {
   public Temp.Label label;

   public LABEL(String a, Temp.Label l) {
      assem=a; label=l;
   }

   public List<Temp.Temp> use() {return null;}
   public List<Temp.Temp> def() {return null;}
   public Targets jumps()     {return null;}

}
