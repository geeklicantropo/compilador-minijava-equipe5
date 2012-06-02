package syntaxtree;

public class HelperSyntaxTree {
	public static int getNumberOfVariables(ClassDecl classe) {
		if (classe.getClass()==ClassDeclExtends.class) {
			ClassDeclExtends c = (ClassDeclExtends)classe;
			return c.vl.size();
		} else {
			ClassDeclSimple c = (ClassDeclSimple)classe;
			return c.vl.size();
		}
	}
	
	public static int getNumberOfVariables(String classeString, Program p) {
		ClassDeclList l = p.cl;
		for (int i=0; i<l.size(); i++) {
			ClassDecl cl = l.elementAt(i);
			if (className(cl).equals(classeString)) {
				return getNumberOfVariables(cl);
			}
		}
		return 0;
	}
	
	public static String className(ClassDecl c) {
		if (c.getClass() == ClassDeclSimple.class) {
			return ((ClassDeclSimple)c).i.s;
		} else {
			return ((ClassDeclExtends)c).i.s;
		}
	}
}
