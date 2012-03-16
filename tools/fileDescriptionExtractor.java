import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.sun.javadoc.*;

public class fileDescriptionExtractor {

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
		String descriptionReport = new String();

		ClassDoc[] classDocs = root.classes();
		for (ClassDoc classDoc : classDocs) {
			descriptionReport += "\t\t\t\t\t";
			
			File classFile = getFileByClassdoc(classDoc);
			if (classFile != null) {
				SimpleDateFormat date = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");
				String lastModified = date.format(classFile.lastModified());
				
				
				descriptionReport += classFile.getName();
				descriptionReport += "\t& ";
				descriptionReport += classFile.length() + " byte";
				descriptionReport += "\t& ";
				descriptionReport += lastModified;
				descriptionReport += "\t& ";
			} else {
				descriptionReport += "% TODO no file found for class: "
					+ classDoc.containingPackage() + "." + classDoc.typeName();
			}
			
			Tag[] tags = classDoc.tags();
			boolean fileTagFound = false;
			for (Tag tag : tags) {
				if (tag.name().compareTo("@file") == 0) {
					descriptionReport += tag.text() + "\t";
					fileTagFound = true;
					break;
				}
			}
			
			if (!fileTagFound) {
				descriptionReport += "TODO file description \t";
			}
			
			descriptionReport += "\\\\ \\hline\n";
		}
		
		//System.out.println(descriptionReport);
		String filePath = "../docs/06/06.tex";
		String placeholder = "%GENERATOR:FILE_LIST";
		String outputContent = placeholder + "\n" + descriptionReport + "\n\t\t\t\t\t" + placeholder;
		writeContentToPlaceholdersIntoFile(filePath, placeholder, outputContent);
		
		return true;
	}
	
	private static File getFileByClassdoc(ClassDoc doc) {
		String fullClassName = doc.containingPackage() + "." + doc.typeName();
		String path = fullClassName.replace(".", "/");
		
		File classFile = null;
		classFile = new File("../src/"+path+".java");
		if (!classFile.exists()) {
			classFile = new File("../skeleton/"+path+".java");
		}
		if (!classFile.exists()) {
			System.err.println("No file found for class: " + fullClassName);
			System.err.println("Path tried: " + classFile.getPath());
		}
		
		return classFile;
	}
}
