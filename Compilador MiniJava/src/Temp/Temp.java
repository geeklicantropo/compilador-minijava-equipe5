package Temp;

public class Temp  {
   private static int count;
   private int num;
   public String toString() {return "t" + num;}
   public Temp() { 
     num=count++;
   }
   public String print() {
       return "t"+ Integer.toString(num);
   }
}

