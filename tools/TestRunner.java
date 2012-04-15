import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;


public class TestRunner {
	protected BufferedReader inputStream;
	protected BufferedReader outputStream;
	protected List<String> realOutput = new LinkedList<String>();
	protected List<String> expectedOutput = new LinkedList<String>();
	protected String diff = "";
	
	/**
	 * Initializes a test runner environment.
	 * @param input Input of the test
	 * @param output Expected output of the test
	 */
	public TestRunner(InputStreamReader input, InputStreamReader output) {
		this.inputStream = new BufferedReader(input);
		this.outputStream = new BufferedReader(output);
	}
	
	public void run() throws IOException {
		ProcessBuilder builder = new ProcessBuilder("./tools/proto_run.bat");
		builder.redirectErrorStream(true);
		Process pr = builder.start();
	
		BufferedReader prInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		OutputStreamWriter prOutput = new OutputStreamWriter(pr.getOutputStream());
		
		String line;
		
		prOutput.write("echoCommands true\n");
		while ((line = this.inputStream.readLine()) != null) {
			prOutput.write(line.trim() + "\n");
		}
		prOutput.write("exit\n");
		prOutput.flush();
		
		while ((line = this.outputStream.readLine()) != null) {
			if (line.trim().isEmpty()) {
				continue;
			}
			
			this.expectedOutput.add(line.trim() + "\n");
		}
		
		while ((line = prInput.readLine()) != null) {
			if (line.equals("> exit")) {
				break;
			}
			
			if (line.trim().isEmpty()) {
				continue;
			}
			
			this.realOutput.add(line.trim() + "\n");
		}
		
		this.doDiff();
	}
	
	public List<String> getOutput() {
		return this.realOutput;
	}
	
	public String getDiff() {
		return this.diff;
	}
	
	public boolean hasPassed() {
		return this.diff.isEmpty();
	}
	
	protected void doDiff() {
		Patch patch = DiffUtils.diff(this.expectedOutput, this.realOutput);
		
        for (Delta delta : patch.getDeltas()) {
        	this.diff += "Expected at line " + delta.getOriginal().getPosition() + ": \n";
        	for (Object line : delta.getOriginal().getLines()) {
        		this.diff += " - " + line.toString(); 
        	}
        	
        	this.diff += "Actual output at line " + delta.getRevised().getPosition() + ": \n";
        	for (Object line : delta.getRevised().getLines()) {
        		this.diff += " + " + line.toString(); 
        	}
        }
	}
}
