package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {

	private String field;
	public String getField() {return field;}
	public void setField(String field) {this.field = field;}

	private String function;
	public String getFunction() {return function;}
	public void setFunction(String function) {this.function = function;}

	// Write logic for constructor
	public AggregateFunction(String field, String function) {
		this.field = field;
		this.function = function;
	}

	public AggregateFunction(){}

	@Override
	public String toString() {
		return "AggregateFunction{" +
				"field='" + field + '\'' +
				", function='" + function + '\'' +
				'}';
	}

}
