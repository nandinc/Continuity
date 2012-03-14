package model.test;

import java.io.IOException;

public abstract class AbstractTest implements Test {

	protected String testName = "Unknown Test";
	
	protected void setName(String name) {
		testName = name;
	}
	
	@Override
	public String getName() {
		return testName;
	}
	
	@Override
	public void run() {
		System.out.println("Testing " + testName + "...\n");
		runTest();
		
		try {
			System.out.println("\nFinished. Press any key to continue");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract void runTest();
}
