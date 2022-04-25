package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * */
public class Restriction {

	private String propertyName;
	public String getPropertyName() {return propertyName;}
	public void setPropertyName(String propertyName) {this.propertyName = propertyName;}

	private String propertyValue;
	public String getPropertyValue() {return propertyValue;}
	public void setPropertyValue(String propertyValue) {this.propertyValue = propertyValue;}

	private String condition;
	public String getCondition() {return condition;}
	public void setCondition(String condition) {this.condition = condition;}

	// Write logic for constructor
	public Restriction(String propertyName, String propertyValue, String condition) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.condition = condition;
	}

	public Restriction(){}

	@Override
	public String toString() {
		return "Restriction{" +
				"name='" + propertyName + '\'' +
				", value='" + propertyValue + '\'' +
				", condition='" + condition + '\'' +
				'}';
	}

}
