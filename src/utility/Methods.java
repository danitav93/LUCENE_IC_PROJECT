package utility;

import java.util.HashSet;
import java.util.Set;


public class Methods {

	/**
	 * provide method for establish if a string is a date
	 */
	private static Set<String> dates = new HashSet<String>();
	static {
	    for (int year = Constant.MinYearBirthDate; year < Constant.MaxYearBirthDate; year++) {
	        for (int month = 1; month <= 12; month++) {
	            for (int day = 1; day <= daysInMonth(month); day++) {
	                StringBuilder date = new StringBuilder();
	                date.append(String.format("%04d", year));
	                date.append(String.format("%02d", month));
	                date.append(String.format("%02d", day));
	                dates.add(date.toString());
	            }
	        }
	    }
	}

	public static boolean isValidDate(String dateString) {
		String[] parsedDate= dateString.split("/");
		int[] parsedSuposedLength = {4,2,2};
		
	
		String concatenatedDate = new String("");
		for (int i=0;i<parsedDate.length;i++) {
			if (parsedDate[i].length() != parsedSuposedLength[i] ||  !isInteger(parsedDate[i])) {
				return false;
			}	 		
			concatenatedDate = concatenatedDate + parsedDate[i];
		}
	    return dates.contains(concatenatedDate);
	}
	
	private static int daysInMonth(int month) {
		switch (month) {
		case 4:
		case 6:
		case 11:
		case 8:
			return 30;
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 10:
		case 12:
			return 31;
		case 2:
			return 29;
		}
		return 0;
	}
	
	private static boolean isInteger(String s) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(!Character.isDigit(s.charAt(i))) return false;
	    }
	    return true;
	}
	
	
}
