package decoder;

import main.Constants;

public class Decoder {
	private int printWidth;
	private int printHeight;
	private char[][] canvas;
	
	private boolean[][] checked;
	
	private String decoded = "";
	
	public Decoder(int width, int height, char[][] canvas) {
		this.printHeight = height;
		this.printWidth = width;
		this.canvas = canvas;
	}
	
	public String getMapFile() {
		return decoded;
	}
	
	public String Decode() {
		if (canvas!= null) {
			int dontcare_width = 0;
			int dontcare_height = 0;
			checked = new boolean[printHeight][printWidth];
			for(int i = 0; i < printHeight; i++) {
				for (int j = 0; j < printWidth; j++) {
					checked[i][j] = false;
				}
			}
			for(int row = 0; row < printHeight-1; row++) {
				dontcare_width = 0;
				for (int col = 0; col < printWidth; col++) {
					if (!checked[row][col]) {
						char c = canvas[row][col];
						
						int realCol = col - dontcare_width;
						int realRow = row - dontcare_height;
						
						if (c == '+') {
							dontcare_height++;
							row++;
							col = -1;
						} else if (c == '|') {
							dontcare_width++;
						} else {
							if (c == 'X') {
			            		decoded+=("Stickman\t" + realCol*main.Constants.multiplier + "\t"
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\t1\n");
							} else if (c == 'Y') {
			            		decoded+=("Stickman\t" + realCol*main.Constants.multiplier + "\t"
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\t2\n");
							}

			            	else if (c == '#') {
			            		decoded+=("Platform\t" + realCol*main.Constants.multiplier + "\t"
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\t\n");
			            	}

			            	else if (c == 'K') {
			            		decoded+=("Key\t" + realCol*main.Constants.multiplier + "\t" 
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\n");
			            	}

			            	else if (c == 'A') {
			            		decoded+=("Door\t" + realCol*main.Constants.multiplier + "\t" 
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\n");
			            	}

			            	else if (c == '?') {
			            		decoded+=("Unknown\t" + realCol*main.Constants.multiplier + "\t" 
			            				+ realRow*main.Constants.multiplier + "\t"
			            				+ getRange(c, row, col) + "\n");
			            	}
						}
					}
				}
			}
		}
		return decoded;
	}
	
	private String getRange(char c, int row, int col) {
		int min_width = printWidth;
		int tempWidth = 0;
		int tempHeight = 0;
		
		// Legnagyobb összefüggő rész megkeresése
		for (int i = 0; i < printWidth; i++) {
			if (canvas[row+i][col] == c) {
				tempHeight++;
			} else {
				break;
			}
			for (int j = 0; j < printHeight; j++) {
				if (canvas[row+i][col+j] == c) {
					tempWidth++;
				} else {
					break;
				}
			}
			if (tempWidth < min_width) {
				min_width = tempWidth;
			}
		}
		tempWidth = min_width;
		
		for (int i = row; i < row + tempHeight; i++) {
			for (int j = col; j < col + tempWidth; j++) {
				checked[i][j] = true;
			}
		}
		
		return tempWidth*main.Constants.multiplier + "\t" + tempHeight*main.Constants.multiplier;
	}
}