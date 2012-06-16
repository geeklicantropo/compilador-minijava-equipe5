package Translate;

import java.util.LinkedList;

import syntaxtree.And;
import syntaxtree.ArrayAssign;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.Assign;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.Call;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.False;
import syntaxtree.Formal;
import syntaxtree.HelperSyntaxTree;
import syntaxtree.Identifier;
import syntaxtree.IdentifierExp;
import syntaxtree.IdentifierType;
import syntaxtree.If;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.Print;
import syntaxtree.Program;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.While;
import visitor.PrettyPrintVisitor;
import visitor.TranslateVisitor;
import Frame.Access;
import Frame.Frame;
import List.InterfaceMips;
import List.List;
import Symbol.Binder;
import Symbol.Symbol;
import Symbol.Table;
import Temp.Label;
import Temp.Temp;
import Tree.BINOP;
import Tree.CALL;
import Tree.CJUMP;
import Tree.CONST;
import Tree.ESEQ;
import Tree.JUMP;
import Tree.LABEL;
import Tree.MEM;
import Tree.MOVE;
import Tree.NAME;
import Tree.SEQ;
import Tree.Stm;
import Tree.TEMP;

public class Translate implements TranslateVisitor {
        private Table symbolTable;
        private Frame frame;
        private FragList fragList;
        private String currClass = "";
        private Frame currFrame;
        Program p;

        public Translate(Table table, Frame frame, Program p){
                symbolTable = table;
                fragList = new FragList();
                this.frame = frame;
                this.p = p;
        }
        
        public FragList getFragList(){
                return fragList;
        }

        private void addFrag(Stm stm){          
                fragList.add(new Frag(currFrame,stm));
        }

        public Exp visit(Program n) {
        	n.m.accept(this);
            for(int i=0;i<n.cl.size();i++) 
            	n.cl.elementAt(i).accept(this);
            return null;
        }

        public Exp visit(MainClass n) {
        	currClass = n.i1.s;
            symbolTable.beginScope();
            currFrame = frame.newFrame(Symbol.symbol(n.i1 + "$" + "public static void main"), 
            		new InterfaceMips<Boolean>(new List<Boolean>(Boolean.valueOf(false), null)).ll);
            addFrag(n.s.accept(this).unNx());
            symbolTable.endScope();
            currClass = "";
            return null;
        }

        public Exp visit(ClassDeclSimple n) {
           currClass=n.i.s; 
           symbolTable.beginScope();
           for(int i=0;i<n.vl.size();i++) 
               n.vl.elementAt(i).accept(this);
	       for(int i=0;i<n.ml.size();i++) 
	    	   n.ml.elementAt(i).accept(this);
	       symbolTable.endScope();
	       currClass="";
           return null;
        }

        public Exp visit(ClassDeclExtends n) {
	        currClass=n.i.s;    
	        symbolTable.beginScope();
	        for(int i=0;i<n.ml.size();i++) 
	        	n.ml.elementAt(i).accept(this);
	        symbolTable.endScope();
	        currClass=""; 
	        return null;
        }

        public Exp visit(VarDecl n) {
                Access access = currFrame.allocLocal(false);
                Exp exp = new Ex(access.exp(new TEMP(currFrame.FP())));
                symbolTable.put(Symbol.symbol(n.i.s), exp);
                return exp;
        }

        public Exp visit(MethodDecl n) {
                Stm varDeclStm = null, stm = null;
                symbolTable.beginScope();
                
                //prepara lista de bools que representam os parametros formais
                LinkedList<Boolean> boolList = new LinkedList<Boolean>();
                boolList.addFirst(Boolean.valueOf(false));
                for(int i=0; i<n.fl.size(); i++){
                	boolList.addFirst(Boolean.valueOf(false));
                }
                
                currFrame = frame.newFrame(Symbol.symbol(currClass+"$"+n.i.s), boolList);
                for(int i=0; i<n.fl.size(); i++) {
                	n.fl.elementAt(i).accept(this);
                }
                
                //chama visitor para variaveis locais e faz uma sequencia de comandos de declaracao de variaveis
                if(n.vl.size()>0) {
                        varDeclStm = n.vl.elementAt(0).accept(this).unNx();
                        for (int i=1; i<n.vl.size(); i++)  
                        	varDeclStm = new SEQ(varDeclStm, n.vl.elementAt(i).accept(this).unNx());
                }
                
                //cria statements para os comandos do corpo do metodo
                if(n.sl.size()>0){
                        if(varDeclStm==null){
                                stm = n.sl.elementAt(0).accept(this).unNx();
                        }
                        else stm = new SEQ(varDeclStm, n.sl.elementAt(0).accept(this).unNx());
                        for(int i=1; i<n.sl.size(); i++)  stm = new SEQ(stm, n.sl.elementAt(i).accept(this).unNx());
                }
                if(varDeclStm==null && stm==null) return new Ex(new CONST(0));
                symbolTable.endScope();
                addFrag(stm);
                return new Nx(stm);
        }

        public Exp visit(Formal n) {
                Access access = currFrame.allocLocal(false);
                Exp exp = new Ex(access.exp(new TEMP(currFrame.FP())));
                symbolTable.put(Symbol.symbol(n.i.s), exp);
                return exp;
        }

        public Exp visit(IntArrayType n) {
                return new Ex(new CONST(0));
        }

        public Exp visit(BooleanType n) {
                return new Ex(new CONST(0));
        }

        public Exp visit(IntegerType n) {
                return new Ex(new CONST(0));
        }

        public Exp visit(IdentifierType n) {
                return new Ex(new CONST(0));
        }

        public Exp visit(Block n) {
                Stm stm = null;         
                if(n.sl.size()>0){
                        stm = n.sl.elementAt(0).accept(this).unNx();
                        for(int i=1; i<n.sl.size(); i++)  stm = new SEQ(stm, n.sl.elementAt(i).accept(this).unNx());
                }
                if(stm==null) return new Ex(new CONST(0));              
                return new Nx(stm);
        }

        public Exp visit(If n) { 
                Label t = new Label();
                Label f = new Label();
                Label join = new Label();
                return new Nx(new SEQ( new CJUMP(CJUMP.LT, n.e.accept(this).unEx(), new CONST(1), t, f),
                	new SEQ(new LABEL(f), 
                			new SEQ( n.s2.accept(this).unNx(),
                				new SEQ( new JUMP(new NAME(join),new List<Label>(join,null)),
                					new SEQ(new LABEL(t),new SEQ(n.s1.accept(this).unNx(), 
                						new LABEL(join))))))));
        }

        public Exp visit(While n) {
        	Label test = new Label();
        	Label t = new Label();
            Label f = new Label();
            return new 
            	Nx(new SEQ 
            		(new SEQ
            			(new SEQ(new LABEL(test),
            				(new CJUMP(CJUMP.LT, n.e.accept(this).unEx(), 
                                        new CONST(1),t,f))),
                            (new SEQ( new LABEL(t),n.s.accept(this).unNx()))),
                            	new LABEL(f)));
        }

        public Exp visit(Print n) {
            return new Ex(currFrame.externalCall("print", 
            	new InterfaceMips<Tree.Exp>(new List<Tree.Exp>(n.e.accept(this).unEx(), null)).ll));
        }

        public Exp visit(Assign n) {
                if(getAccess(Symbol.symbol(n.i.s)) == null) n.accept(new PrettyPrintVisitor());
                return new Nx(new MOVE(getAccess(Symbol.symbol(n.i.s)).unEx(), n.e.accept(this).unEx()));
        }

        public Exp visit(ArrayAssign n) {
                return new Nx(new MOVE(new MEM(new BINOP(BINOP.PLUS,getAccess(Symbol.symbol(n.i.s)).unEx(), n.e1.accept(this).unEx())), n.e2.accept(this).unEx()));
        }

        public Exp visit(And n) {
                return new Ex(new BINOP(BINOP.AND,n.e1.accept(this).unEx(),n.e2.accept(this).unEx()));
        }

        public Exp visit(LessThan n) {
                Label t = new Label();
                Label f = new Label();
                Temp r = new Temp();
                return new Ex(new ESEQ (new SEQ( new MOVE(new TEMP(r), new CONST(1)), 
                                        new SEQ( new CJUMP(CJUMP.LT, n.e1.accept(this).unEx(), n.e2.accept(this).unEx(), t, f),
                                                new SEQ(new LABEL(f), 
                                                        new SEQ(new MOVE(new TEMP(r), new CONST(0)),
                                                                new LABEL(t))))), new TEMP(r)));
        }

        public Exp visit(Plus n) {
                return new Ex(new BINOP(BINOP.PLUS,n.e1.accept(this).unEx(),n.e2.accept(this).unEx()));
        }

        public Exp visit(Minus n) {
                return new Ex(new BINOP(BINOP.MINUS,n.e1.accept(this).unEx(),n.e2.accept(this).unEx()));
        }

        public Exp visit(Times n) {
                return new Ex(new BINOP(BINOP.MUL,n.e1.accept(this).unEx(),n.e2.accept(this).unEx()));
        }

        public Exp visit(ArrayLookup n) {
                return new Ex(new BINOP(BINOP.PLUS,n.e1.accept(this).unEx(), n.e2.accept(this).unEx()));
        }

        public Exp visit(ArrayLength n) {
                //a primeira posicao do vetor eh o tamanho
                return new Ex(new BINOP(BINOP.PLUS,n.e.accept(this).unEx(), new CONST(0)));
        }

        public Exp visit(Call n) {
                Exp caller = n.e.accept(this);
                List<Tree.Exp> expList = new List<Tree.Exp>(caller.unEx(), null);
                for(int i=n.el.size()-1; i>=0; i--){
                	expList = new List<Tree.Exp>(n.el.elementAt(i).accept(this).unEx(), expList);
                }
                return new Ex(new CALL(caller.unEx(), expList));
        }

        public Exp visit(IntegerLiteral n) {
                return new Ex(new CONST(n.i));
        }

        public Exp visit(True n) {
                return new Ex(new CONST(1));
        }

        public Exp visit(False n) {
                return new Ex(new CONST(0));
        }

        public Exp visit(IdentifierExp n) {
                Symbol symbol = Symbol.symbol(n.s);
                Exp exp =  getAccess(symbol);
                return new Ex(exp.unEx());
        }

        public Exp visit(This n) {
            List<Access> accList = new InterfaceMips<Access>(currFrame.formals).l;
            Access thisAccess = accList.head;
            
            while(accList.tail!=null){
            	thisAccess = accList.head;
                accList = accList.tail;
            }
            return new Ex(thisAccess.exp(new TEMP(currFrame.FP())));
        }

        public Exp visit(NewArray n) {
        	return new Ex(currFrame.externalCall("initArray",
        	new InterfaceMips<Tree.Exp>(
            new List<Tree.Exp>(new BINOP(BINOP.MUL, 
            	new BINOP(BINOP.PLUS, n.e.accept(this).unEx() , new CONST(1)),
            		new CONST(currFrame.wordSize())),null)).ll));
        }

        public Exp visit(NewObject n) {
            return new Ex(currFrame.externalCall("allocRecord",
            new InterfaceMips<Tree.Exp>(
            	new List<Tree.Exp>(new BINOP(BINOP.MUL,
            		new CONST(HelperSyntaxTree.getNumberOfVariables(n.i.s, p)+1),
            			new CONST(currFrame.wordSize())),null)).ll));
        }

        public Exp visit(Not n) {
                  return new Ex(new BINOP(BINOP.MINUS, new CONST(1), 
                 (n.e.accept(this)).unEx()));
        }

        public Exp visit(Identifier n) {
                return getAccess(Symbol.symbol(n.s));
        }

        private Exp getAccess(Symbol s) {
                Binder aux = (Binder)symbolTable.get(s);
                return (Exp)aux.binding;
        }        
}