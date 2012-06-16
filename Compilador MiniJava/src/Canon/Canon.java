package Canon;

import List.List;
import Translate.Frag;
import Translate.FragList;
import Tree.Stm;

class MoveCall extends Tree.Stm {
  Tree.TEMP dst;
  Tree.CALL src;
  MoveCall(Tree.TEMP d, Tree.CALL s) {dst=d; src=s;}
  public List<Tree.Exp> kids() {return src.kids();}
  public Tree.Stm build(List<Tree.Exp> kids) {
	return new Tree.MOVE(dst, src.build(kids));
  }
}   
  
class ExpCall extends Tree.Stm {
  Tree.CALL call;
  ExpCall(Tree.CALL c) {call=c;}
  public List<Tree.Exp> kids() {return call.kids();}
  public Tree.Stm build(List<Tree.Exp> kids) {
	return new Tree.EXPSTM(call.build(kids));
  }
}   
  
class StmExpList {
  Tree.Stm stm;
  List<Tree.Exp> exps;
  StmExpList(Tree.Stm s, List<Tree.Exp> e) {stm=s; exps=e;}
}

public class Canon {
  
 static boolean isNop(Tree.Stm a) {
   return a instanceof Tree.EXPSTM
          && ((Tree.EXPSTM)a).exp instanceof Tree.CONST;
 }

 static Tree.Stm seq(Tree.Stm a, Tree.Stm b) {
    if (isNop(a)) return b;
    else if (isNop(b)) return a;
    else return new Tree.SEQ(a,b);
 }

 static boolean commute(Tree.Stm a, Tree.Exp b) {
    return isNop(a)
        || b instanceof Tree.NAME
        || b instanceof Tree.CONST;
 }

 static Tree.Stm do_stm(Tree.SEQ s) { 
	return seq(do_stm(s.left), do_stm(s.right));
 }

 static Tree.Stm do_stm(Tree.MOVE s) { 
	if (s.dst instanceof Tree.TEMP 
	     && s.src instanceof Tree.CALL) 
		return reorder_stm(new MoveCall((Tree.TEMP)s.dst,
						(Tree.CALL)s.src));
	else if (s.dst instanceof Tree.ESEQ)
	    return do_stm(new Tree.SEQ(((Tree.ESEQ)s.dst).stm,
					new Tree.MOVE(((Tree.ESEQ)s.dst).exp,
						  s.src)));
	else return reorder_stm(s);
 }

 static Tree.Stm do_stm(Tree.EXPSTM s) { 
	if (s.exp instanceof Tree.CALL)
	       return reorder_stm(new ExpCall((Tree.CALL)s.exp));
	else return reorder_stm(s);
 }

 static Tree.Stm do_stm(Tree.Stm s) {
     if (s instanceof Tree.SEQ) return do_stm((Tree.SEQ)s);
     else if (s instanceof Tree.MOVE) return do_stm((Tree.MOVE)s);
     else if (s instanceof Tree.EXPSTM) return do_stm((Tree.EXPSTM)s);
     else return reorder_stm(s);
 }

 static Tree.Stm reorder_stm(Tree.Stm s) {
     StmExpList x = reorder(s.kids());
     return seq(x.stm, s.build(x.exps));
 }

 static Tree.ESEQ do_exp(Tree.ESEQ e) {
      Tree.Stm stms = do_stm(e.stm);
      Tree.ESEQ b = do_exp(e.exp);
      return new Tree.ESEQ(seq(stms,b.stm), b.exp);
  }

 static Tree.ESEQ do_exp (Tree.Exp e) {
       if (e instanceof Tree.ESEQ) return do_exp((Tree.ESEQ)e);
       else return reorder_exp(e);
 }
         
 static Tree.ESEQ reorder_exp (Tree.Exp e) {
     StmExpList x = reorder(e.kids());
     return new Tree.ESEQ(x.stm, e.build(x.exps));
 }

 static StmExpList nopNull = new StmExpList(new Tree.EXPSTM(new Tree.CONST(0)),null);

 static StmExpList reorder(List<Tree.Exp> exps) {
     if (exps==null) return nopNull;
     else {
       Tree.Exp a = exps.head;
       if (a instanceof Tree.CALL) {
         Temp.Temp t = new Temp.Temp();
	 Tree.Exp e = new Tree.ESEQ(new Tree.MOVE(new Tree.TEMP(t), a),
				    new Tree.TEMP(t));
         return reorder(new List<Tree.Exp>(e, exps.tail));
       } else {
	 Tree.ESEQ aa = do_exp(a);
	 StmExpList bb = reorder(exps.tail);
	 if (commute(bb.stm, aa.exp))
	      return new StmExpList(seq(aa.stm,bb.stm), 
				    new List<Tree.Exp>(aa.exp,bb.exps));
	 else {
	   Temp.Temp t = new Temp.Temp();
	   return new StmExpList(
			  seq(aa.stm, 
			    seq(new Tree.MOVE(new Tree.TEMP(t),aa.exp),
				 bb.stm)),
			  new List<Tree.Exp>(new Tree.TEMP(t), bb.exps));
	 }
       }
     }
 }
        
 static List<Tree.Stm> linear(Tree.SEQ s, List<Tree.Stm> l) {
      return linear(s.left,linear(s.right,l));
 }
 static List<Tree.Stm> linear(Tree.Stm s, List<Tree.Stm> l) {
    if (s instanceof Tree.SEQ) return linear((Tree.SEQ)s, l);
    else return new List<Tree.Stm>(s,l);
 }

 static public List<Tree.Stm> linearize(Tree.Stm s) {
    return linear(do_stm(s), null);
 }
 
	public static FragList canonlize(FragList frags ) {
		for(int i = 0 ; i < frags.size(); i++){
			Frag f = frags.get(i);
			f.setBody( Canon.linearize(f.getBody().head));
		}
		return frags;
	}
	
	public static List<Stm> generateCode(StmListList list){
		List<Stm> stml = null;
		StmListList aux;
		for(aux = list; aux!= null; aux = (StmListList) aux.tail){
			List<Stm> aux2 = aux.head;
			for(; aux2!= null; aux2 = (List<Stm>) aux2.tail){
				stml = new List<Stm>(aux2.head, stml);
			}
		}
		return stml;
	}
}
