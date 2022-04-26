package com.stackroute.datamunger.query;

import java.time.LocalDate;
import java.util.regex.Pattern;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {

	//method stub
	public static String getDataType(String input) {

		Object obj = new Object();

		Pattern digitCheck = Pattern.compile("\\p{Digit}");
		Pattern allDigits = Pattern.compile("(\\p{Digit})[\\p{Digit}]*");

		if(input.isEmpty()) {
			return "java.lang.Object";
		} else if(digitCheck.matcher(input).region(0,1).matches()) {
			if(allDigits.matcher(input).matches()) {
				return "java.lang.Integer";
			} else {
				return "java.util.Date";
			}
		} else {
			return "java.lang.String";
		}
	
		// checking for Integer
		
		// checking for floating point numbers
		
		// checking for date format dd/mm/yyyy
		
		// checking for date format mm/dd/yyyy
		
		// checking for date format dd-mon-yy
		
		// checking for date format dd-mon-yyyy
		
		// checking for date format dd-month-yy
		
		// checking for date format dd-month-yyyy
		
		// checking for date format yyyy-mm-dd
		
		//return obj;
	}
	
}
