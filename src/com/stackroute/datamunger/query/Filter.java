package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.Restriction;

import java.text.SimpleDateFormat;

//This class contains methods to evaluate expressions
public class Filter {
	
	/* 
	 * The evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */

	public boolean evaluateExpression(String fieldValue, Restriction restriction, String dataType){
		switch(restriction.getCondition()){
			case "=" : return isEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case "!=" : return isNotEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case ">=" : return isGreaterThanOrEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case "<=" : return isLessThanOrEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case ">" : return isGreaterThan(fieldValue, restriction.getPropertyValue(), dataType);
			default : return isLessThan(fieldValue, restriction.getPropertyValue(), dataType);
		}
	}

	//Method containing implementation of equalTo operator
	public boolean isEqualTo(String fieldValue, String restrictionValue, String dataType){

		switch (dataType){
			case "java.lang.Integer":
				return Integer.parseInt(fieldValue) == Integer.parseInt(restrictionValue);
			case "java.util.Date":
				SimpleDateFormat formatter = new SimpleDateFormat(dateFormat(fieldValue));
				try {
					return (formatter.parse(fieldValue).compareTo(formatter.parse(restrictionValue))) == 0;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			case "java.lang.String":
				return fieldValue.compareTo(restrictionValue) == 0;
			//default for empty objects
			default:
				return false;
		}

	}

	//Method containing implementation of notEqualTo operator
	public boolean isNotEqualTo(String fieldValue, String restrictionValue, String dataType){
		return !isEqualTo(fieldValue, restrictionValue, dataType);
	}

	//Method containing implementation of greaterThan operator
	public boolean isGreaterThan(String fieldValue, String restrictionValue, String dataType){

		switch (dataType){
			case "java.lang.Integer":
				return Integer.parseInt(fieldValue) > Integer.parseInt(restrictionValue);
			case "java.util.Date":
				SimpleDateFormat formatter = new SimpleDateFormat(dateFormat(fieldValue));
				try {
					return (formatter.parse(fieldValue).compareTo(formatter.parse(restrictionValue))) > 0;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			case "java.lang.String":
				return fieldValue.compareTo(restrictionValue) == 0;
			//default for empty objects
			default:
				return false;
		}

	}
	
	//Method containing implementation of greaterThanOrEqualTo operator
	public boolean isGreaterThanOrEqualTo(String fieldValue, String restrictionValue, String dataType){
		return isEqualTo(fieldValue, restrictionValue, dataType) | isGreaterThan(fieldValue, restrictionValue, dataType);
	}

	//Method containing implementation of lessThan operator
	public boolean isLessThan(String fieldValue, String restrictionValue, String dataType){
		return !(isGreaterThan(fieldValue, restrictionValue, dataType) | isEqualTo(fieldValue, restrictionValue, dataType));
	}

	//Method containing implementation of lessThanOrEqualTo operator
	public boolean isLessThanOrEqualTo(String fieldValue, String restrictionValue, String dataType){
		return (isLessThan(fieldValue, restrictionValue, dataType) | isEqualTo(fieldValue, restrictionValue, dataType));
	}

	private String dateFormat(String date) {
		String format;
		if(date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}"))
			format = "dd/mm/yyyy";
		else if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
			format = "yyyy-mm-dd";
		else if(date.matches("[0-9]{2}-[a-z]{3}-[0-9]{2}"))
			format = "dd-mon-yy";
		else if(date.matches("[0-9]{2}-[a-z]{3}-[0-9]{4}"))
			format ="dd-mon-yyyy";
		else if(date.matches("[0-9]{2}-[a-z]*-[0-9]{2}"))
			format = "dd-month-yy";
		else
			format ="dd-month-yyyy";
		return format;
	}
	
}
