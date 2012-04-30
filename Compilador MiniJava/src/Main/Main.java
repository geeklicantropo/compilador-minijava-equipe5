package Main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import syntaxtree.Program;
import visitor.PrettyPrintVisitor;
import Parser.MiniJavaParser;
import Parser.ParseException;

public class Main {
	public static void main(String [] args) {
		Reader in;
		if (args.length==0) { 
			System.out.println("Digite o caminho do arquivo como argumento para o programa");
			return;
		}
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
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}
