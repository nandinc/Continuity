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

/**
 * Szkeleton tesztek futtatása
 * 
 * Betölti a mellette található test csomagból az összes
 * Test felületet megvalósító osztályt, és felkínálja őket futtatásra.
 * Felhasználói utasításra lefuttatja a kiválasztott tesztet. 
 * 
 * @warning The correct functionality of this class depends on it's placement,
 * because of the automatic test explore procedure. 
 * Move this carefully or don't move it at all.
 * 
 * @file Szkeleton tesztek futtatókörnyezete
 */
public class SkeletonRunner {

	/**
	 * Tesztek listája
	 */
	private static List<Test> tests = new ArrayList<Test>();
	
	/**
	 * Program belépőpontja
	 * 
	 * Összegyűjti a rendelkezésre álló teszteket
	 * és felkínálja őket.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Continuity -- Skeleton Test Runner");
		System.out.println("==================================");
		
		loadTests();
		askForTest();
	}

	/**
	 * Betölti a rendelkezésre álló teszteket
	 */
	private static void loadTests() {
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
					// If instantiation succeeded and object
					// implements Test, add it to tests
					tests.add((Test)test);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Kigyűjti a futtató osztály melletti 'test' mappából
	 * az összes fájlt, és megbecsüli a tartalmazott osztály nevét.
	 * 
	 * @return A talált fájlokban található osztályok becsült neve
	 */
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
	
	/**
	 * Kilistázza az elérhető teszteket,
	 * és vár a felhasználó választására.
	 * 
	 * Szabályos választás esetén futtatja a kiválasztott tesztet.
	 * 0 választás esetén kilép.
	 */
	private static void askForTest() {
		System.out.println("----------------");
		System.out.println("Available Tests:");
		System.out.println("----------------");
		
		//
		// LIST TESTS
		//
		System.out.println("0: Exit");
		int i = 1;
		for (Test test : tests) {
			System.out.print(i);
			System.out.print(". ");
			System.out.println(test.getName());
			
			i++;
		}	
		
		System.out.println("----------------");
		System.out.print("\nPlease choose a test: ");
		
		//
		// READ INPUT
		//
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
		
		//
		// EXECUTE INPUT
		//
		if (testIndex == 0) {
			System.exit(0);
		} else if (testIndex < 0 || testIndex > tests.size()) {
			System.err.println("Please enter a valid test number! (got: " + testIndex + ")\n");
			askForTest();
			return;
		} else {
			System.out.println("~~~~~~~~~~~~~~~~~~~~\n");
			// run choosen test
			tests.get(testIndex - 1).run();
		}
		
		System.out.print("\n\n");
		askForTest();
	}
}
