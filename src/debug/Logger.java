package debug;

/**
 * A prototípus üzeneteit jeleníti meg a felhasználói felületen 
 * 
 * @responsibility Hibakeresést elősegítő üzenetek megjelenítése
 */
public class Logger {
    
    /**
     * Status üzenetek puffere
     */
    static StringBuilder statusBuffer = new StringBuilder();
    
    /**
     * Map üzenetek kimeneti puffere
     */
    static StringBuilder mapBuffer = new StringBuilder();
    
    /**
     * Státusz üzenet megjelenítése
     * @param status Üzenet 
     */
	public static void logStatus(String status) {
		System.out.println(status);
	    /*statusBuffer.append(status);
	    statusBuffer.append("\n");*/
	}
	
	/**
	 * Pályakép megjelenítése
	 * @param map
	 */
	public static void logMap(String map) {
	    mapBuffer.append(map);
	}
	
	/**
	 * Kimeneti puffer kivezetése a kimenetre 
	 */
	public static void flush() {
	    System.out.print(statusBuffer.toString());
	    System.out.print(mapBuffer.toString());
	    
	    reset();
	}
	
	/**
	 * Kimenet ürítése
	 */
	public static void reset() {
	    statusBuffer = new StringBuilder();
        mapBuffer = new StringBuilder();
	}
}
