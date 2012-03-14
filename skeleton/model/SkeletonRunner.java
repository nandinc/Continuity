package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.test.Test;

public class SkeletonRunner {

	private static List<Test> tests = new ArrayList<Test>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Continuity -- Skeleton Test Runner");
		System.out.println("==================================");
		
		loadTests();
		askForTest();
	}

	private static void loadTests() {
		//testNames.add("model.test.LoadGameTest");
		List<String> testNames = getTestNames();
		
		try {
			for (String className : testNames) {
				Class<?> testClass = Class.forName(className);
				
				Object test = null;
				try {
					test = testClass.newInstance();
				} catch (InstantiationException e) {
					// let this pass
					// class is this Class represents an abstract class, 
					// an interface, an array class, a primitive type, or void; 
					// or the class has no nullary constructor; 
					// or the instantiation failed for some other reason. 
				}
				
				if (test instanceof Test) {
					tests.add((Test)test);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static List<String> getTestNames() {
		String packageName = "model.test.";
		List<String> testNames = new LinkedList<String>();
		
		URL runnerPath = SkeletonRunner.class.getResource("SkeletonRunner.class");
		File testRoot = new File(runnerPath.getPath().toString().replace("SkeletonRunner.class", "") + "/test");
		
		if (testRoot.isDirectory()) {
			String[] children = testRoot.list();
			
			for (String child : children) {
				testNames.add(packageName + child.replace(".class", ""));
			}
		} else {
			System.err.println("Test root directory not found, trying:");
			System.err.println(testRoot);
		}
		
		return testNames;
	}
	
	private static void askForTest() {
		System.out.println("----------------");
		System.out.println("Available Tests:");
		System.out.println("----------------");
		
		System.out.println("0: Exit");
		int i = 1;
		for (Test test : tests) {
			System.out.print(i);
			System.out.print(". ");
			System.out.println(test.getName());
			
			i++;
		}	
		
		System.out.println("----------------");
		System.out.println("");
		System.out.print("Please choose a test: ");
		
		Integer testIndex = null;
		try {
			// read input
			BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
			);
			
			// validate input
			testIndex = Integer.parseInt(br.readLine());

		} catch (NumberFormatException ne) {
			System.err.println("Please enter a number!\n");
			askForTest();
			return;
		} catch (IOException ioe) {
			System.err.println("I/O Error: " + ioe);
			askForTest();
			return;
		}
		
		if (testIndex == 0) {
			System.exit(0);
		} else if (testIndex < 0 || testIndex > tests.size()) {
			System.err.println("Please enter a valid test number! (got: " + testIndex + ")\n");
			askForTest();
			return;
		} else {
			System.out.println("~~~~~~~~~~~~~~~~~~~~\n");
			tests.get(testIndex - 1).run();
		}
		
		System.out.println("");
		System.out.println("");
		askForTest();
	}
}
