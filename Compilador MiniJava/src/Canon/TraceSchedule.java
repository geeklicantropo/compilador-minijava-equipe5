package Canon;

import List.List;
import Temp.Label;
import Tree.Stm;

public class TraceSchedule {

  public List<Tree.Stm> stms;
  BasicBlocks theBlocks;
  java.util.Dictionary<Temp.Label, List<Tree.Stm>> table = new java.util.Hashtable<Label, List<Stm>>();

  List<Tree.Stm> getLast(List<Tree.Stm> block) {
     List<Tree.Stm> l=block;
     while (l.tail.tail!=null)  l=l.tail;
     return l;
  }

  void trace(List<Tree.Stm> l) {
   for(;;) {
     Tree.LABEL lab = (Tree.LABEL)l.head;
     table.remove(lab.label);
     List<Tree.Stm> last = getLast(l);
     Tree.Stm s = last.tail.head;
     if (s instanceof Tree.JUMP) {
	Tree.JUMP j = (Tree.JUMP)s;
        List<Tree.Stm> target = (List<Tree.Stm>)table.get(j.targets.head);
	if (j.targets.tail==null && target!=null) {
               last.tail=target;
	       l=target;
        }
	else {
	  last.tail.tail=getNext();
	  return;
        }
     }
     else if (s instanceof Tree.CJUMP) {
	Tree.CJUMP j = (Tree.CJUMP)s;
        List<Tree.Stm> t = (List<Tree.Stm>)table.get(j.iftrue);
        List<Tree.Stm> f = (List<Tree.Stm>)table.get(j.iffalse);
        if (f!=null) {
	  last.tail.tail=f; 
	  l=f;
	}
        else if (t!=null) {
	  last.tail.head=new Tree.CJUMP(Tree.CJUMP.notRel(j.relop),
					j.left,j.right,
					j.iffalse,j.iftrue);
	  last.tail.tail=t;
	  l=t;
        }
        else {
	  Temp.Label ff = new Temp.Label();
	  last.tail.head=new Tree.CJUMP(j.relop,j.left,j.right,
					j.iftrue,ff);
	  last.tail.tail=new List<Tree.Stm>(new Tree.LABEL(ff),
		           new List<Tree.Stm>(new Tree.JUMP(j.iffalse),
					    getNext()));
	  return;
        }
     }
     else throw new Error("Bad basic block in TraceSchedule");
    }
  }

  List<Tree.Stm> getNext() {
      if (theBlocks.blocks==null) 
	return new List<Tree.Stm>(new Tree.LABEL(theBlocks.done), null);
      else {
	 List<Tree.Stm> s = theBlocks.blocks.head;
	 Tree.LABEL lab = (Tree.LABEL)s.head;
	 if (table.get(lab.label) != null) {
          trace(s);
	  return s;
         }
         else {
	   theBlocks.blocks = theBlocks.blocks.tail;
           return getNext();
         }
      }
  }

  public TraceSchedule(BasicBlocks b) {
    theBlocks=b;
    for(StmListList l = b.blocks; l!=null; l=l.tail)
       table.put(((Tree.LABEL)l.head.head).label, l.head);
    stms=getNext();
    table=null;
  }
}


