package prop.assignment0;

import java.util.Arrays;

public class Util {
	public static void appendTabs(StringBuilder builder, int tabs) {
		for (int i = 0; i<tabs; i++) 
			builder.append("\t");
	}
	
	public static Object[] expandArray(Object[] arr) {
		return Arrays.copyOf(arr, arr.length+2);
	}
	
}
