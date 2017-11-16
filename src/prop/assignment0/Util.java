package prop.assignment0;

public class Util {
	public static void appendTabs(StringBuilder builder, int tabs) {
		for (int i = 0; i<tabs; i++) 
			builder.append("\t");
	}	
	
	/**
	 * @param varName - the variable to find
	 * @param arr - the object array to search in
	 * @return {@code null} if varName is not present,<br/> the value of the varName as a {@code Double} if it is present.
	 * 
	 */
	public static Double findVariableValue(String varName, Object[] arr) {
		for (int i = 0; i < arr.length-1; i+=2) {
			Object object = arr[i];
			if (object instanceof String && ((String) object).equals(varName))
				return (Double) arr[i+1];
		}
		return null;
	}
}
