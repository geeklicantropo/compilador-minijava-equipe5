package Main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import syntaxtree.Program;
import visitor.PrettyPrintVisitor;
import List.List;
import Mips.MipsFrame;
import Parser.MiniJavaParser;
import Parser.ParseException;
import Symbol.Table;
import Translate.Translate;
import Tree.Print;
import Tree.Stm;

public class Main {
	public static void main(String [] arg) {
		Reader in;
		BufferedReader d = new BufferedReader(new InputStreamReader(System.in));  
		String args[] = {"files\\Factorial.java"};
		try {
			System.out.println("Compilando... "+args[0]);
			in = new FileReader(args[0]);
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo nao encontrado.");
			e1.printStackTrace();
			return;
		}
		try {
			MiniJavaParser parser = new MiniJavaParser(in);
            Program root = MiniJavaParser.Program();
			root.accept(new PrettyPrintVisitor());
			Translate tv = new Translate(new Table(), new MipsFrame(), root);
			root.accept(tv);
			Print printer = new Print(System.out);
			for (int i=0; i<tv.getFragList().size(); i++) {
				List<Stm> it = tv.getFragList().get(i).getBody();
				for (List<Stm> sts = it; sts!=null; sts=sts.tail) {
					printer.prStm(tv.getFragList().get(i).getBody().head);
				}
			}
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}
