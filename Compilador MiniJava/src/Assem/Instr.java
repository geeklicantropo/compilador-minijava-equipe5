package Assem;

import List.List;

public abstract class Instr {
  public String assem;
  public abstract List<Temp.Temp> use();
  public abstract List<Temp.Temp> def();
  public abstract Targets jumps();
  public boolean branch;

  private Temp.Temp nthTemp(List<Temp.Temp> l, int i) {
    if (i==0) return l.head;
    else return nthTemp(l.tail,i-1);
  }

  private Temp.Label nthLabel(List<Temp.Label> l, int i) {
    if (i==0) return l.head;
    else return nthLabel(l.tail,i-1);
  }

  public String format(Temp.TempMap m) {
    List<Temp.Temp> dst = def();
    List<Temp.Temp> src = use();
    Targets j = jumps();
    List<Temp.Label> jump = (j==null)?null:j.labels;
    StringBuffer s = new StringBuffer();
    int len = assem.length();
    for(int i=0; i<len; i++)
	if (assem.charAt(i)=='`')
	   switch(assem.charAt(++i)) {
              case 's': {int n = Character.digit(assem.charAt(++i),10);
			 s.append(m.tempMap(nthTemp(src,n)));
			}
			break;
	      case 'd': {int n = Character.digit(assem.charAt(++i),10);
			 s.append(m.tempMap(nthTemp(dst,n)));
			}
 			break;
	      case 'j': {int n = Character.digit(assem.charAt(++i),10);
			 s.append(nthLabel(jump,n).toString());
			}
 			break;
	      case '`': s.append('`'); 
			break;
              default: throw new Error("bad Assem format");
       }
       else s.append(assem.charAt(i));

    return s.toString();
  }


}
