import com.sun.javadoc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class javaToLatex extends Doclet {

	private static String readFileAsString(String filePath) throws java.io.IOException {
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    FileInputStream f = new FileInputStream(filePath);
	    f.read(buffer);
	    return new String(buffer);
	}
	
	private static void writeStringIntoFile(String content, String filePath) throws java.io.IOException {
		File file = new File(filePath);
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
	}
	
	public static boolean start(RootDoc root) {
		String latexPlaceholder = "%GENERATOR:CLASS_DESCRIPTIONS";
		String targetFile = "../docs/03/03.tex";
		String originalLatexSource = null;
		String generatedDoc = latexPlaceholder + "\n";
		
		try {
			originalLatexSource = readFileAsString(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClassDoc[] classes = root.classes();
		Arrays.sort(classes);
		for (ClassDoc cd : classes) {
			generatedDoc += "\t\t\\subsubsection{" + cd.name() + "}";
			if (cd.isInterface()) {
				generatedDoc += " Interfész.\n";
			} else if (cd.isAbstract()) {
				generatedDoc += " Absztrakt osztály.\n";
			} else {
				generatedDoc += "\n";
			}

			generatedDoc += "\t\t\t\\begin{description}\n";
			generatedDoc += "\t\t\t\t\\item[Felelősség]";
			generatedDoc += cd.commentText().replace('\n', ' ');
			
			if (cd.commentText().length() < 5) {
				generatedDoc += " % TODO document responsibility of " + cd.name() + "\n";
			} else {
				generatedDoc += "\n";
			}
			
			generatedDoc += "\n\t\t\t\t\\item[Ősosztályok] " + getSupers(cd) + ".\n";
			
			if (!cd.isInterface()) {
				generatedDoc += "\t\t\t\t\\item[Interfészek] " + getIfaces(cd) + "\n";
				generatedDoc += "\t\t\t\t\\item[Attribútumok]$\\ $\n\t\t\t\t\t\\begin{description}\n";
				generatedDoc += printFields(cd.fields());
				generatedDoc += "\t\t\t\t\t\\end{description}\n";
			}
			generatedDoc += "\t\t\t\t\\item[Metódusok]$\\ $\n\t\t\t\t\t\\begin{description}\n";
			generatedDoc += printMembers(cd.methods());
			generatedDoc += "\t\t\t\t\t\\end{description}\n";
			generatedDoc += "\t\t\t\\end{description}\n";
			generatedDoc += "\n";
		}
		
		generatedDoc += latexPlaceholder;
		String[] latexSourceParts = originalLatexSource.split(latexPlaceholder);

		if (latexSourceParts.length != 3) {
			System.err.println("Placeholders not found.");
		} else {
			latexSourceParts[1] = generatedDoc;
			String finalLatexSource = latexSourceParts[0] + latexSourceParts[1] + latexSourceParts[2];
			
			try {
				writeStringIntoFile(finalLatexSource, targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	private static String getIfaces(ClassDoc cd) {
		ClassDoc[] ifs = cd.interfaces();
		String s = null;
		Arrays.sort(ifs);
		for (ClassDoc iface : ifs) {
			s = (s == null ? "" : s + ", ") + iface.name();
		}
		return s == null ? "(nincs)" : (s + ".");
	}

	private static String getSupers(ClassDoc cl) {
		ClassDoc sup = cl.superclass();
		if (sup != null) {
			return getSupers(sup) + " $\\rightarrow{}$ " + cl.name();
		} else {
			return cl.name();
		}

	}

	private static String printFields(FieldDoc[] mems) {
		String generatedFieldDoc = new String();
		Arrays.sort(mems);
		for (FieldDoc mem : mems) {
			generatedFieldDoc += "\t\t\t\t\t\t\\item[\\texttt{" + mem.modifiers() + " "
				+ getTypeName(mem.type()) + " " + mem.name().replace("_", "\\_") + "}]";
			generatedFieldDoc += mem.commentText().replace('\n', ' ');
			if (mem.commentText().length() < 5) {
				generatedFieldDoc += "% TODO\n";
			} else {
				generatedFieldDoc += "\n";
			}
		}
		
		
		if (mems.length == 0) {
			generatedFieldDoc += "\t\t\t\t\t\t\\item[] (nincs)\n";
		}
		
		return generatedFieldDoc;
	}

	static String printMembers(MethodDoc[] mems) {
		String generatedMemberDoc = new String();
		Arrays.sort(mems);
		for (MethodDoc mem : mems) {
			if (mem.name().compareTo("toString") != 0) {
				generatedMemberDoc += "\t\t\t\t\t\t\\item[\\texttt{" + mem.modifiers() + " "
					+ getTypeName(mem.returnType()) + " "
					+ mem.name().replace("_", "\\_") + "(" + getParams(mem).replace("_", "\\_") + ")}] \\hfill \\\\";
				generatedMemberDoc += mem.commentText().replace('\n', ' ');
				if (mem.commentText().length() < 5) {
					generatedMemberDoc += "\n\t\t\t\t\t\t% TODO document " + mem.name();
				}
			}
			generatedMemberDoc += "\n";
		}
		
		if (mems.length == 0) {
			generatedMemberDoc += "\t\t\t\t\t\t\\item[] (nincs)\n";
		}
		
		return generatedMemberDoc;
	}

	static private String getTypeName(Type t) {
		String name = t.typeName();
		String par = "";
		try {
			TypeVariable[] args = t.asClassDoc().typeParameters();
			for (TypeVariable arg : args) {
				if (par.length() > 0) {
					par += ", ";
				}
				par += getTypeName(arg);
			}
		} catch (Exception e) {
			par = "";
		}
		if (par.length() > 0) {
			name += "<" + par + ">";
		}
		name += t.dimension();
		return name;
	}

	private static String getParams(MethodDoc mem) {
		Parameter[] pars = mem.parameters();
		String ret = "";
		for (Parameter p : pars) {
			if (ret.length() > 0) {
				ret += ", ";
			}
			ret += getTypeName(p.type()) + " " + p.name();
		}
		return ret.replace("_", "\\_");
	}
}