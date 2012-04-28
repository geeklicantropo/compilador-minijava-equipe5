package typeCheck;

import Symbol.EconomicTable;
import Symbol.Symbol;

public class ClassRecord {
	Symbol nome;
	String pai;
	MethodTable mt;
	boolean eEstatico;
	EconomicTable atributos;
	
	public ClassRecord() {
		atributos = new EconomicTable();
	}
	
	public void addAtributo(String ident, String tipo) {
		atributos.put(Symbol.symbol(ident), tipo);
	}
	
	public String getNome() {
		return nome.toString();
	}
}
