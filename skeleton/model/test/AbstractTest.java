package model.test;

import java.io.IOException;

/**
 * 
 * @file Szkeleton tesztek ősosztálya
 */
public abstract class AbstractTest implements Test {

	/**
	 * Teszt alapértelmezett neve.
	 */
	protected String testName = "Unknown Test";
	
	/**
	 * Teszt nevének beállítása
	 * @param name teszt neve
	 */
	protected void setName(String name) {
		testName = name;
	}
	
	/**
	 * Teszt nevének lekérdezése
	 * @return teszt neve
	 */
	@Override
	public String getName() {
		return testName;
	}
	
	/**
	 * Teszt futtatása
	 * 
	 * Kiírja a futtatandó teszt nevét,
	 * majd meghívja a teszt runTest() metódusát.
	 * 
	 * A lefutás után billentyűleütésre vár, majd visszatér.
	 */
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

	/**
	 * A konkrét tesztek által megvalósítandó teszt metódus,
	 * ami inicializálja a tesztkörnyezetet és
	 * biztosítja a kezdeti gerjesztést.
	 */
	protected abstract void runTest();
}
