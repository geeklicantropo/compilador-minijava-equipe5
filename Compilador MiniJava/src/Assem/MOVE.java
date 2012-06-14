package Assem;

import List.List;

public class MOVE extends Instr {
   public Temp.Temp dst;   
   public Temp.Temp src;

   public MOVE(String a, Temp.Temp d, Temp.Temp s) {
      assem=a; dst=d; src=s;
   }
   public List<Temp.Temp> use() {return new List<Temp.Temp>(src,null);}
   public List<Temp.Temp> def() {return new List<Temp.Temp>(dst,null);}
   public Targets jumps()     {return null;}

}
