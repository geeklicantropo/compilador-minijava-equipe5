package typeCheck;

import Symbol.EconomicTable;
import Symbol.Symbol;

public class MethodRecord {
	Symbol nome;
	String tipo;
	EconomicTable parametros;
	boolean eEstatico;
	
	public MethodRecord() {
		parametros = new EconomicTable();
	}
	
	public void addParametro(String ident, String tipo) {
		parametros.put(Symbol.symbol(ident), tipo);
	}
	
	public String getNome() {
		return nome.toString();
	}
}
