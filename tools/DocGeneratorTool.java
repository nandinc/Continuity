import com.sun.javadoc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Run configurations
 *  - main class: com.sun.tools.javadoc.Main
 *  - arguments: -doclet DocGeneratorTool -s ../src -all
 */
public class DocGeneratorTool extends Doclet {
	//protected static String targetPath = "../docs/08/08.tex";
	protected static String targetPath = "c:\\Berci\\Programozas\\Continuity\\Continuity\\docs\\08\\08.tex";
	
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
	
	private static void writeContentToPlaceholdersIntoFile(String filePath, String placeholder, String content) {
		String originalFileContent = null;
		
		try {
			originalFileContent = readFileAsString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] fileParts = originalFileContent.split(placeholder);
		
		if (fileParts.length != 3) {
			System.err.println("Placeholders (" +placeholder+ ") not found in file: " + filePath);
			return;
		}
		
		fileParts[1] = content;
		String finalContent = fileParts[0] + fileParts[1] + fileParts[2];
		
		try {
			writeStringIntoFile(finalContent, filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean start(RootDoc root) {
		//String latexResponsibilityPlaceholder = "%GENERATOR:CLASS_RESPONSIBILITY";
		String latexDescribePlaceholder = "%GENERATOR:CLASS_DESCRIPTIONS";
		String targetFile = targetPath;
		//String generatedResponsibilityDoc = latexResponsibilityPlaceholder + "\n";
		String generatedDescribeDoc = latexDescribePlaceholder + "\n";
		
		//generatedResponsibilityDoc += "\t\t\\begin{description}\n";
		
		ClassDoc[] classes = root.classes();
		Arrays.sort(classes);
		for (ClassDoc cd : classes) {
			// responsibility
			//generatedResponsibilityDoc += "\t\t\t\\item[" + cd.name() + "] ";
			boolean responsibilityFound = false;
			String responsibility = "";
			
			for (Tag tag : cd.tags()) {
				if (tag.name().compareTo("@responsibility") == 0) {
					responsibilityFound = true;
					//generatedResponsibilityDoc += tag.text() + "\n";
					responsibility = tag.text();
					break;
				}
			}
			
			if (!responsibilityFound) {
				//generatedResponsibilityDoc += "% TODO\n";
			}
			
			// description
			generatedDescribeDoc += "\t\t\\subsubsection{" + cd.name().replace("_", "\\_") + "}";
			if (cd.isInterface()) {
				generatedDescribeDoc += " Interfész.\n";
			} else if (cd.isAbstract()) {
				generatedDescribeDoc += " Absztrakt osztály.\n";
			} else {
				generatedDescribeDoc += "\n";
			}
			
			//generatedDescribeDoc += "\t\t\t\t";
			//generatedDescribeDoc += cd.commentText().replace('\n', ' ');
			
			generatedDescribeDoc += "\t\t\t\\begin{description}\n";
			/*if (cd.commentText().length() < 5) {
				generatedDescribeDoc += " % TODO document responsibility of " + cd.name() + "\n";
			} else {
				generatedDescribeDoc += "\n";
			}*/
			
			// Responsibility
			if (responsibilityFound) {
				generatedDescribeDoc += "\n\t\t\t\t\\item[Felelősség] " + responsibility + "\n";
			} else {
				generatedDescribeDoc += "\n\t\t\t\t%\\item[Felelősség] % TODO \n";
			}
			
			String superclass = getSupers(cd);
			// has no 'real' superclass
			if (superclass.compareTo(cd.name()) == 0) {
				superclass = "(nincs)";
			}
			generatedDescribeDoc += "\n\t\t\t\t\\item[Ősosztályok] " + superclass + "\n";
			
			if (!cd.isInterface()) {
				generatedDescribeDoc += "\t\t\t\t\\item[Interfészek] " + getIfaces(cd) + "\n";
				
				if (cd.fields().length > 0) {
					generatedDescribeDoc += "\t\t\t\t\\item[Attribútumok]$\\ $\n\t\t\t\t\t\\begin{description}\n";
					generatedDescribeDoc += printFields(cd.fields());
					generatedDescribeDoc += "\t\t\t\t\t\\end{description}\n";
				} else {
					generatedDescribeDoc += "\t\t\t\t\\item[Attribútumok] (nincs)\n";
				}
			}
			
			if (cd.methods().length > 0) {
				generatedDescribeDoc += "\t\t\t\t\\item[Metódusok]$\\ $\n\t\t\t\t\t\\begin{description}\n";
				generatedDescribeDoc += printMembers(cd.methods());
				generatedDescribeDoc += "\t\t\t\t\t\\end{description}\n";
			} else {
				generatedDescribeDoc += "\t\t\t\t\\item[Metódusok] (nincs)\n";
			}
			
			generatedDescribeDoc += "\t\t\t\\end{description}\n";
			generatedDescribeDoc += "\n";
		}
		
		//generatedResponsibilityDoc += "\t\t\\end{description}\n" + latexResponsibilityPlaceholder;
		generatedDescribeDoc += latexDescribePlaceholder;
		
		//writeContentToPlaceholdersIntoFile(targetFile, latexResponsibilityPlaceholder, generatedResponsibilityDoc);
		writeContentToPlaceholdersIntoFile(targetFile, latexDescribePlaceholder, generatedDescribeDoc);
		
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
		
		// get supers super recursively if super exists and it's not Object (java.lang.Object)
		if (sup != null && cl.superclass().name().compareTo("Object") != 0) {
			return getSupers(sup) + " $\\rightarrow{}$ " + cl.name().replace("_", "\\_");
		} else {
			return cl.name().replace("_", "\\_");
		}

	}

	private static String printFields(FieldDoc[] mems) {
		String generatedFieldDoc = new String();
		Arrays.sort(mems);
		for (FieldDoc mem : mems) {
			generatedFieldDoc += "\t\t\t\t\t\t\\item[\\texttt{" + getVisibility(mem) + (mem.isStatic() ? "\\underline{" : "")
				+ mem.name().replace("_", "\\_") + " : " + getTypeName(mem.type()) + (mem.isStatic() ? "}" : "") + "}]";
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
				
				// make not documented members unvisible
				//if (mem.commentText().length() < 5) {
					//generatedMemberDoc += "%";
				//}
				
				generatedMemberDoc += "\t\t\t\t\t\t\\item[\\texttt{" + getVisibility(mem)  + (mem.isStatic() ? "\\underline{" : "")
					+ mem.name().replace("_", "\\_") + "(" + getParams(mem) + ")";
				if (!(mem.returnType().toString().equals("void"))) {
					generatedMemberDoc += " : " + getTypeName(mem.returnType());
				}
				generatedMemberDoc += (mem.isStatic() ? "}" : "") + "}] \\hfill \\\\";
				generatedMemberDoc += mem.commentText().replace('\n', ' ');
				if (mem.commentText().length() < 5) {
					generatedMemberDoc += "\n\t\t\t\t\t\t% TODO document " + mem.name();
				}
			}
			generatedMemberDoc += " \n";
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
		return name.replace("_", "\\_");
	}

	private static String getParams(MethodDoc mem) {
		Parameter[] pars = mem.parameters();
		String ret = "";
		for (Parameter p : pars) {
			if (ret.length() > 0) {
				ret += ", ";
			}
			ret += p.name() + " : " + getTypeName(p.type());
		}
		return ret;
	}
	
	private static String getVisibility(ProgramElementDoc mem) {
		if (mem.isPublic()) {
			return "+";
		} else if (mem.isProtected()) {
			return "\\#";
		} else if (mem.isPrivate()) {
			return "-";
		} else {
			return "";
		}
	}
	
	private static String getModifiersSansVisibility(ProgramElementDoc mem) {
		return mem.modifiers().replace("public", "").replace("protected", "").replace("private", "").replace("  ", " ").trim();
	}
}