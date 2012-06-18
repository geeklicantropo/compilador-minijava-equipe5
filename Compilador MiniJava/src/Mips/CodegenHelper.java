package Mips;

import Assem.Instr;
import Frame.Frame;
import List.List;

public class CodegenHelper {
    static public List<Instr> codegen(List<Tree.Stm> stmList, Frame frame) {
    	Codegen codegen = new Codegen(frame);
    	List<Instr> instrs = null;
    	List<Instr> aux;
    	for(; stmList!= null; stmList = (List<Tree.Stm>) stmList.tail){
    		aux = codegen.codegen(stmList.head);
    		for(; aux!= null; aux = aux.tail){
    			instrs = new List<Instr>(aux.head, instrs);
				}
			}
    	return instrs;
    }
}
