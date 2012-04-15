import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestGUI {
	protected static String[][] tests = {
		{"1", "Pálya betöltése"},
		{"2", "Stickman mozgatása kereten belül"},
		{"3", "Stickman mozgatása keretek között"},
		{"4", "Stickman kiesése"},
		{"5", "Kulcs felvétele, pálya teljesítése"},
		{"6", "Keret mozgatása"},
		{"7", "Nézetek közötti váltás"}
	};
	
	public static void main(String[] args) {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		String id = null;
		
		while (true) {
			System.out.println("Available tests:");
			TestGUI.listTests();
			
			try {
				while (!TestGUI.testExists(id)) {
					System.out.print("Please choose a test: ");
					id = br.readLine().trim();
				}
				
				TestGUI.runTest(id);
				id = null;
				System.out.print("\n\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected static void listTests() {
		for (String[] test : TestGUI.tests) {
			System.out.println(" - " + test[0] + ": " + test[1]);
		}
	}
	
	protected static void runTest(String id) {
		try {
			TestRunner test = new TestRunner(new FileReader("./tools/resources/tests/input/" + id + ".txt"), new FileReader("./tools/resources/tests/output/" + id + ".txt"));
			
			test.run();

			if (test.hasPassed()) {
				System.out.println("Test has passed.");
			} else {
				System.out.println("Test has failed, see details below.");
				System.out.print(test.getDiff());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static boolean testExists(String id) {
		for (String[] test : TestGUI.tests) {
			if (test[0].equals(id)) {
				return true;
			}
		}
		return false;
	}
}
