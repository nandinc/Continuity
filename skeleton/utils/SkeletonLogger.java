package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SkeletonLogger {
	protected static Map<Object, String> objectMap = new HashMap<Object, String>();
	protected static Stack<StackLevel> callStack = new Stack<StackLevel>();
	
	public static void call(Object o, String m) {
		logCall(o, m, new Object[0]);
	}
	
	public static void call(Object o, String m, Object p1) {
		logCall(o, m, new Object[] { p1 });
	}
	
	public static void call(Object o, String m, Object p1, Object p2) {
		logCall(o, m, new Object[] { p1, p2 });
	}
	
	public static void call(Object o, String m, Object p1, Object p2, Object p3) {
		logCall(o, m, new Object[] { p1, p2, p3 });
	}
	
	public static void call(Object o, String m, Object p1, Object p2, Object p3, Object p4) {
		logCall(o, m, new Object[] { p1, p2, p3, p4 });
	}
	
	public static void reply() {
		StackLevel level = callStack.pop();
		System.out.println("<- " + level);
	}
	
	public static void reply(Object object) {
		StackLevel level = callStack.pop();
		System.out.println("<- " + level + " :: " + getObjectString(object));
	}
	
	public static void register(Object object, String name) {
		objectMap.put(object, name);
	}
	
	public static String ask(String question) {
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		
		System.out.println("< " + question);
		System.out.print("> ");
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static boolean yesOrNo(String question) {
		String answer = ask(question + " (y/n)");
		
		return answer.equals("y");
	}
	
	private static void logCall(Object object, String method, Object[] parameters) {
		StackLevel level = new SkeletonLogger.StackLevel(object, method, parameters);
		callStack.push(level);
		
		System.out.println("-> " + level);
	}
	
	private static String getObjectString(Object object) {
		String s;
		
		if (object == null) {
			s = "null";
		} else {
			s = object.getClass().getSimpleName();
			s += "[";
			
			if (objectMap.containsKey(object)) {
				s += objectMap.get(object);
			} else {
				s += object.hashCode();
			}
			
			s += "]";
		}
		
		return s;
	}
	
	protected static class StackLevel {
		private Object object;
		private String method;
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
