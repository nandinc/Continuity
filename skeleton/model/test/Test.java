package model.test;

/**
 * 
 * @file Szkeleton tesztek interface
 */
public interface Test {
	/**
	 * Teszt futtatása
	 */
	public void run();
	
	/**
	 * Teszt nevének lekérdezése
	 * @return teszt neve
	 */
	public String getName();
}
