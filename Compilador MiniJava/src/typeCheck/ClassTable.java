package typeCheck;

import java.util.Vector;

import Symbol.Symbol;

class ClassTable {
	Vector<ClassRecord> table;
	
	public ClassTable() {
		table = new Vector<ClassRecord>();
	}
	
	public void addClassRecord(ClassRecord novo) {
		table.add(novo);
	}
	
	public void addClass(String nome, String pai, MethodTable mt) {
		ClassRecord cR = new ClassRecord();
		cR.nome = Symbol.symbol(nome);
		cR.pai = pai;
		cR.mt = mt;
		table.add(cR);
	}
	
	public ClassRecord getClassRecord(String nome) {
		Symbol nomeSymbol = Symbol.symbol(nome);
		for (int i=0; i<table.size(); i++) {
			if (table.get(i).nome.equals(nomeSymbol))
				return table.get(i);
		}
		return null;
	}
}