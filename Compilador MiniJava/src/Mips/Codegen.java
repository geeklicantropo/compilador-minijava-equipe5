package Mips;

import Frame.Frame;
import List.List;

public class Codegen {
        Frame frame;
        
        public Codegen(Frame f) {
                frame=f;
        }
        
        private List<Assem.Instr> ilist=null, last=null;
        
//        private void emit(Assem.Instr inst) {
//                if (last!=null)
//                        last = last.tail = new List<Assem.Instr>(inst,null);
//                else last = ilist = new List<Assem.Instr>(inst,null);
//        }
        
        void munchStm(Tree.Stm s) {

        }
        
        Temp.Temp munchExp(Tree.Exp s) {
                
                return null;
        }
        
        List<Assem.Instr> codegen(Tree.Stm s) {
                List<Assem.Instr> l;
                munchStm(s);
                l = ilist;
                ilist = last = null;
                return l;
        }
}