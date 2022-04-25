package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.Restriction;

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
		return false;
	}

	//Method containing implementation of notEqualTo operator
	public boolean isNotEqualTo(String fieldValue, String restrictionValue, String dataType){
		return false;
	}

	//Method containing implementation of greaterThan operator
	public boolean isGreaterThan(String fieldValue, String restrictionValue, String dataType){
		return false;
	}
	
	//Method containing implementation of greaterThanOrEqualTo operator
	public boolean isGreaterThanOrEqualTo(String fieldValue, String restrictionValue, String dataType){
		return false;
	}

	//Method containing implementation of lessThan operator
	public boolean isLessThan(String fieldValue, String restrictionValue, String dataType){
		return false;
	}

	//Method containing implementation of lessThanOrEqualTo operator
	public boolean isLessThanOrEqualTo(String fieldValue, String restrictionValue, String dataType){
		return false;
	}
	
}
