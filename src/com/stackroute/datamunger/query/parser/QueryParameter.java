package com.stackroute.datamunger.query.parser;

import java.util.List;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {

//	public String getFileName() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<String> getFields() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<Restriction> getRestrictions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public String getBaseQuery() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<AggregateFunction> getAggregateFunctions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<String> getLogicalOperators() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<String> getGroupByFields() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public List<String> getOrderByFields() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	private String queryType;
	public String getQUERY_TYPE() {return queryType;}
	public void setQueryType(String queryType) {this.queryType = queryType;}

	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {this.fileName = fileName;}

	private String baseQuery;
	public String getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(String baseQuery) {this.baseQuery = baseQuery;}

	private List<Restriction> restrictions;
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(List<Restriction> restrictions) {this.restrictions = restrictions;}

	private List<String> logicalOperators;
	public List<String> getLogicalOperators() {
		return logicalOperators;
	}
	public void setLogicalOperators(List<String> logicalOperators){this.logicalOperators = logicalOperators;}

	private List<String> fields;
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {this.fields = fields;}

	private List<AggregateFunction> aggregateFunctions;
	public List<AggregateFunction> getAggregateFunctions() {return aggregateFunctions;}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions){this.aggregateFunctions = aggregateFunctions;}

	private List<String> groupByFields;
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	public void setGroupByFields(List<String> groupByFields){this.groupByFields = groupByFields;}

	private List<String> orderByFields;
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	public void setOrderByFields(List<String> orderByFields){this.orderByFields = orderByFields;}

		

	
}
