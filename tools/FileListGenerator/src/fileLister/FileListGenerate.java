package fileLister;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileListGenerate {
	private static ArrayList<File> fileList = new ArrayList<File>();
	public static String generatedList = "";
	
	public static String run(File f) {
		generatedList = "";
		getFiles(f);
		
		generatedList = setOutput();
		
		fileList.clear();
		
		return generatedList;
	}
	
	private static void getFiles(File f) {
		File[] files = f.listFiles();
		
		for (File file : files) {
			if (file.isDirectory()) {
				getFiles(file);
			} else {
				fileList.add(file);
			}
		}
	}
	
	private static String setOutput() {
		String generatedList = "";
		for (File file : fileList) {
			if (file != null && file.isFile()) {
				String[] nameType = file.getName().split("\\.");
			
				SimpleDateFormat date = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");
				
				generatedList += "\t\t\t\t\t\t";

				if (nameType.length == 2 && nameType[1].compareTo("java") != 0) {
					generatedList += "%TODO ";
				} else if (nameType.length == 1) {
					generatedList += "%TODO ";
				}
				
				// Név
				generatedList += file.getName();
				
				generatedList += "\t\t&\t";
				// Méret
				generatedList += file.length() + " bytes";
				generatedList += "\t&\t";
				
				// Módosítva
				generatedList += date.format(new Date(file.lastModified()));
				generatedList += "\t&\t";
				
				// Leírás
				if (nameType.length == 2 && nameType[1].compareTo("java") == 0) {
					generatedList += nameType[0] + " osztály\t";
				} else {
					generatedList += "TODO Description.";
				}
				
				generatedList += "\\\\ \\hline\n";
			}
		}
		return generatedList;
	}
}
