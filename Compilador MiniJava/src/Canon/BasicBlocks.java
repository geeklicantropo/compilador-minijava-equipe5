package Canon;

import List.List;

public class BasicBlocks {
  public StmListList blocks;
  public Temp.Label done;

  private StmListList lastBlock;
  private List<Tree.Stm> lastStm;

  private void addStm(Tree.Stm s) {
	lastStm = lastStm.tail = new List<Tree.Stm>(s,null);
  }

  private void doStms(List<Tree.Stm> l) {
      if (l==null) 
	doStms(new List<Tree.Stm>(new Tree.JUMP(done), null));
      else if (l.head instanceof Tree.JUMP 
	      || l.head instanceof Tree.CJUMP) {
	addStm(l.head);
	mkBlocks(l.tail);
      } 
      else if (l.head instanceof Tree.LABEL)
           doStms(new List<Tree.Stm>(new Tree.JUMP(((Tree.LABEL)l.head).label), 
	  			   l));
      else {
	addStm(l.head);
	doStms(l.tail);
      }
  }

  void mkBlocks(List<Tree.Stm> l) {
     if (l==null) return;
     else if (l.head instanceof Tree.LABEL) {
	lastStm = new List<Tree.Stm>(l.head,null);
        if (lastBlock==null)
  	   lastBlock= blocks= new StmListList(lastStm,null);
        else
  	   lastBlock = lastBlock.tail = new StmListList(lastStm,null);
	doStms(l.tail);
     }
     else mkBlocks(new List<Tree.Stm>(new Tree.LABEL(new Temp.Label()), l));
  }
   

  public BasicBlocks(List<Tree.Stm> stms) {
    done = new Temp.Label();
    mkBlocks(stms);
  }
}
