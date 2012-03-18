package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A szkeleton futásának követésére szolgáló logger osztály.
 *
 * Statikus osztály, melynek használatával követhető a szkeleton hívási
 * hierarchiája, illetve felületet nyújt a felhasználóval való kommunikációra.
 * A kimenetet a konzolra írja és a könnyebb követhetőség érdekében space alapú
 * behúzást használ. A kimenet formátuma a kapcsolódó dokumentációban van
 * definiálva.
 * 
 * @file Szkeleton kommunikáció követése
 */
public class SkeletonLogger {
	/**
	 * A regisztrált objektumok és nevük közötti asszociációt nyilvántartó map.
	 */
	protected static Map<Object, String> objectMap = new HashMap<Object, String>();
	
	/**
	 * A hívási stacket nyilvántartó stack.
	 */
	protected static Stack<StackLevel> callStack = new Stack<StackLevel>();
	
	/**
	 * Regisztrálja a metódushívást, illetve megjeleníti a meghívott metódus
	 * paramétereit (hívott osztály/objektum, metódusnév, paraméterek).
	 * @param o Hívott objektum
	 * @param m Metódus neve
	 */
	public static void call(Object self, String method) {
		logCall(self, method, new Object[0]);
	}
	
	/**
	 * Regisztrálja a metódushívást, illetve megjeleníti a meghívott metódus
	 * paramétereit (hívott osztály/objektum, metódusnév, paraméterek).
	 * @param self Hívott objektum
	 * @param method Metódus neve
	 * @param p1 Első paraméter
	 */
	public static void call(Object self, String method, Object p1) {
		logCall(self, method, new Object[] { p1 });
	}
	
	/**
	 * Regisztrálja a metódushívást, illetve megjeleníti a meghívott metódus
	 * paramétereit (hívott osztály/objektum, metódusnév, paraméterek).
	 * @param self Hívott objektum
	 * @param method Metódus neve
	 * @param pi i. paraméter
	 */
	public static void call(Object self, String method, Object p1, Object p2) {
		logCall(self, method, new Object[] { p1, p2 });
	}
	
	/**
	 * Regisztrálja a metódushívást, illetve megjeleníti a meghívott metódus
	 * paramétereit (hívott osztály/objektum, metódusnév, paraméterek).
	 * @param self Hívott objektum
	 * @param method Metódus neve
	 * @param pi i. paraméter
	 */
	public static void call(Object self, String method, Object p1, Object p2, Object p3) {
		logCall(self, method, new Object[] { p1, p2, p3 });
	}
	
	/**
	 * Regisztrálja a metódushívást, illetve megjeleníti a meghívott metódus
	 * paramétereit (hívott osztály/objektum, metódusnév, paraméterek).
	 * @param self Hívott objektum
	 * @param method Metódus neve
	 * @param pi i. paraméter
	 */
	public static void call(Object self, String method, Object p1, Object p2, Object p3, Object p4) {
		logCall(self, method, new Object[] { p1, p2, p3, p4 });
	}
	
	/**
	 * Shorthand az objektumok létrehozásához, mely beregisztrálja az
	 * objektumot, megjeleníti, hogy meghívták a konstruktorát, illetve rögtön
	 * vissza is tér.
	 * @param self Létrehozott objektum
	 * @param name Objektum neve
	 */
	public static void create(Object self, String name) {
		register(self, name);
		call(self, "<<create>>");
//		back();
	}
	
	/**
	 * Függvényből való visszatérés esetén hívandó, amikor nincs visszatérési
	 * érték.
	 * @note Jobb elnevezésre ötleteket várok.
	 */
	public static void back() {
		StackLevel level = callStack.pop();
		printIndentation(callStack.size());
		System.out.println("<- " + level);
	}
	
	/**
	 * Függvényből való visszatérés esetén hívandó, amikor van visszatérési
	 * érték.
	 * @param object Visszatérési érték
	 */
	public static void back(Object object) {
		StackLevel level = callStack.pop();
		printIndentation(callStack.size());
		System.out.println("<- " + level + " :: " + getObjectString(object));
	}
	
	/**
	 * Objektum regisztrálása és elnevezése
	 * @param object Regisztrálandó/elnevezendő objektum
	 * @param name Objektum neve
	 */
	public static void register(Object object, String name) {
		objectMap.put(object, name);
	}
	
	/**
	 * Kérdést tesz fel a felhasználónak, és visszaadja a választ.
	 * @param question Kérdés szövege
	 * @return A felhasználó válasza
	 */
	public static String ask(String question) {
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		
		printIndentation(callStack.size());
		System.out.println("< " + question);
		printIndentation(callStack.size());
		System.out.print("> ");
		
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * Eldöntendő kérdést tesz fel a felhasználónak, és visszaadja a választ.
	 * @param question Eldöntendő kérdés
	 * @return Válasz
	 */
	public static boolean askYesOrNo(String question) {
		String answer = ask(question + " (y/n)");
		
		return answer.equals("y") || answer.equals("yes") || answer.equals("1");
	}
	
	/**
	 * Sima szöveget jelenít meg a kimeneten.
	 * @param message Üzenet
	 */
	public static void log(String message) {
		printIndentation(callStack.size());
		System.out.println("< " + message);
	}
	
	/**
	 * Elvégxi egy metódushívás loggolását.
	 * @param object Hívott objektum
	 * @param method Hívott metódus neve
	 * @param parameters Paraméterek listája
	 */
	private static void logCall(Object object, String method, Object[] parameters) {
		StackLevel level = new SkeletonLogger.StackLevel(object, method, parameters);
		callStack.push(level);
		
		printIndentation(callStack.size() - 1);
		System.out.println("-> " + level);
	}
	
	/**
	 * Visszaadja egy objektum szöveges reprezentációját, mely tartalmazza az
	 * objektum osztályát, illetve a korábban regisztrált nevét.
	 * @param object Objektum
	 * @return Objektum szöveges reprezentációja
	 */
	private static String getObjectString(Object object) {
		String s;
		
		if (object == null) {
			s = "null";
		} else {
			s = object.getClass().getSimpleName();
			s += "[";
			
			if (objectMap.containsKey(object)) {
				// Show the boject's previously registered name
				s += objectMap.get(object);
			} else {
				// If the object hasn't been registered, than show its hash code so that it's identifiable
				s += object.hashCode();
			}
			
			s += "]";
		}
		
		return s;
	}
	
	/**
	 * Új teszteset futtatása előtt a Stackek üríthetőek.
	 */
	public static void clearStack() {
		callStack.clear();
		objectMap.clear();
	}
	
	/**
	 * Helper metódus a behúzás generálásához.
	 * @param level Behúzás szintje
	 */
	private static void printIndentation(int level) {
		for (int i = 0; i < level * 2; i++) {
			System.out.print(" ");
		};
	}
	
	/**
	 * Belső helper osztály, mely egy metódushívási szintet reprezentál.
	 */
	protected static class StackLevel {
		/** Hívott objektum */
		private Object object;
		/** Hívott metódus neve */
		private String method;
		/** Metódushívás paraméterei */
		private Object[] parameters;
		
		public StackLevel(Object object, String method, Object[] parameters) {
			this.object = object;
			this.method = method;
			this.parameters = parameters;
		}
		
		public String toString() {
			String s;
			
			s = getObjectString(this.object);
			s += "." + method + "(";
			for (int i = 0; i < this.parameters.length; i++) {
				if (i != 0) {
					s += ", ";
				}
				s += getObjectString(this.parameters[i]);
			}
			s += ")";
			
			return s;
		}
	}
}
