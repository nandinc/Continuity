package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import decoder.Decoder;

public class getInputFile {
	public static Decoder readTextFile(File file, gui.Gui.Log log) {
		StringBuilder content = new StringBuilder();
		int printHeight = 0;
		int printWidth = 0;
		
		log.doLog("Reading...\n");
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line;
				while ((line = input.readLine()) != null) {
					printHeight++;
					if (printWidth < line.length()) {
						printWidth = line.length();
					}
					content.append(line);
					content.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		log.doLog("Text readed:\n");
		log.doLog(content.toString());
		
		char[][] canvas = new char[printHeight][printWidth];

		String[] split = content.toString().split(System.getProperty("line.separator"));
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < split[i].length(); j++) {
				canvas[i][j] = split[i].charAt(j);
			}
		}
		
		return new decoder.Decoder(printWidth, printHeight, canvas);
	}
	
	public static void writeMapFile(File file, gui.Gui.Log log, String map) {
		//use buffering
	    Writer output;
		try {
			output = new BufferedWriter(new FileWriter(file));
		    try {
		      //FileWriter always assumes default encoding is OK!
		      output.write( map );
		    }
		    finally {
		      output.close();
		    }
		} catch (IOException ex)  {
			ex.printStackTrace();
		}
	}
}
