package Main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import syntaxtree.Program;
import visitor.PrettyPrintVisitor;
import Assem.Instr;
import Canon.BasicBlocks;
import Canon.Canon;
import Canon.StmListList;
import Canon.TraceSchedule;
import Frame.Frame;
import List.List;
import Mips.MipsFrame;
import Parser.MiniJavaParser;
import Parser.ParseException;
import Symbol.Table;
import Translate.Frag;
import Translate.FragList;
import Translate.Translate;
import Tree.Print;

public class Main {
	static Frame frame;
	static Print printer;
	static MiniJavaParser parser;
	
	public static void main(String [] arg) {
		Reader in=null;
		BufferedReader d = new BufferedReader(new InputStreamReader(System.in)); 
		int opc=0;
		System.out.println("Trabalho Compilador MiniJava");
		System.out.println("Equipe: Samuel Santos, Jeferson Almir," + 
		" Lucas Lourenço, Ildo Ramos.");
		System.out.println("Selecione o fonte a ser compilado: ");
		System.out.println("\n1 - BinarySearch.java");
		System.out.println("2 - BinaryTree.java");
		System.out.println("3 - BubbleSort.java");
		System.out.println("4 - Factorial.java");
		System.out.println("5 - LinearSearch.java");
		System.out.println("6 - LinkedList.java");
		System.out.println("7 - QuickSort.java");
		System.out.println("8 - TreeVisitor.java");
		try {
			String entrada = d.readLine();
			opc = Integer.parseInt(entrada);
		} catch (IOException e2) {
			System.out.println("Erro na leitura da entrada digitada.");
			e2.printStackTrace();
			return;
		}
		
		if (opc < 1 || opc > 8) {
			System.out.println("Entrada inválida");
			return;
		}
			
		String args="";
		
		switch (opc) {
		case 1: args = "files\\BinarySearch.java"; break;
		case 2: args = "files\\BinaryTree.java"; break;
		case 3: args = "files\\BubbleSort.java"; break;
		case 4: args = "files\\Factorial.java"; break;
		case 5: args = "files\\LinearSearch.java"; break;
		case 6: args = "files\\LinkedList.java"; break;
		case 7: args = "files\\QuickSort.java"; break;
		case 8: args = "files\\TreeVisitor.java"; break;
		}
		
		try {
			System.out.println("Compilando... "+args);
			in = new FileReader(args);
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo nao encontrado.");
			e1.printStackTrace();
			return;
		}
		
		try {
			// Parser (Análise léxica + análise sintática)
			parser = new MiniJavaParser(in);
            Program root = MiniJavaParser.Program();
			root.accept(new PrettyPrintVisitor());
			
			// Checagem de tipos
			System.out.println("Pendente.");
			
			// Representação Intermediária
			frame = new MipsFrame();
			Translate tv = new Translate(new Table(), frame, root);
			root.accept(tv);
			printer = new Print(System.out);
			
			// Representação canônica
			FragList frags = tv.getFragList();
			frags = Canon.canonlize(frags);
			
			for(int i = 0 ; i < frags.size(); i++){
				StmListList stml = null;
				StmListList aux;
				Frag f = frags.get(i);
				aux = new BasicBlocks(f.getBody()).blocks;
				int j = 0;
				System.out.println("---INICIO DO FRAG "+i+"---");
				
				for(; aux!= null; aux = (StmListList) aux.tail){
					System.out.println("* Bloco "+j+":");
					j++;
					printer.prStmList(aux.head);
					stml = new StmListList(aux.head, stml);
					if(aux.tail!=null) System.out.println("----------------------------");
				}
				
				in.close();
				
								
				System.out.println("---FIM DO FRAG "+i+"---");
				
			}
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println("Problemas com manipulação de arquivos.");
			e.printStackTrace();
		}
	}
}
