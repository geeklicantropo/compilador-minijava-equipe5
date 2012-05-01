package typeCheck;

import java.util.Vector;

import Symbol.Symbol;

public class MethodTable {
	Vector<MethodRecord> table;
	
	public MethodTable() {
		table = new Vector<MethodRecord>();
	}
	
	public void addMethodRecord(MethodRecord novo) {
		table.add(novo);
	}
	
	public void addMethod(String nome, String tipo) {
		MethodRecord mR = new MethodRecord();
		mR.nome = Symbol.symbol(nome);
		mR.tipo = tipo;
		table.add(mR);
	}
	
	public MethodRecord getMethodRecord(String nome) {
		Symbol nomeSymbol = Symbol.symbol(nome);
		for (int i=0; i<table.size(); i++) {
			if (table.get(i).nome.equals(nomeSymbol))
				return table.get(i);
		}
		return null;
	}
}
