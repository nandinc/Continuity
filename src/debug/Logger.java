package debug;

/**
 * A prototípus üzeneteit jeleníti meg a felhasználói felületen 
 * 
 * @responsibility Hibakeresést elősegítő üzenetek megjelenítése
 */
public class Logger {
    /**
     * Státusz üzenet megjelenítése
     * @param status Üzenet 
     */
	public static void logStatus(String status) {
		System.out.println(status);
	}
}
