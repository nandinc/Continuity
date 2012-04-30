package texHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TexHandler {
	public static void run(File f) {
		String generatedContent = fileLister.FileListGenerate.generatedList;
		String filePath = f.getAbsolutePath();
		String placeholder = "%GENERATOR:FILE_LIST";
		String outputContent = placeholder + "\n" + generatedContent + "\n\t\t\t\t\t" + placeholder;
		writeContentToPlaceholdersIntoFile(filePath, placeholder, outputContent);
	}

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
}
