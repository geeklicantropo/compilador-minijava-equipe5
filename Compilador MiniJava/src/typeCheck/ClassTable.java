package typeCheck;

import java.util.Vector;

import Symbol.Symbol;

class ClassTable {
	Vector<ClassRecord> table;
	
	public ClassTable() {
		table = new Vector<ClassRecord>();
	}
	
	public void addClass(String nome, String pai, MethodTable mt, boolean eEstatico) {
		ClassRecord cR = new ClassRecord();
		cR.nome = Symbol.symbol(nome);
		cR.pai = pai;
		cR.mt = mt;
		cR.eEstatico = eEstatico;
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