package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.*;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.query.parser.Restriction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class CsvQueryProcessor implements QueryProcessingEngine {

	public CsvQueryProcessor(){}

	private QueryParameter queryParameter;
	public QueryParameter getQueryParameter() {return queryParameter;}
	public void setQueryParameter(QueryParameter queryParameter) {this.queryParameter = queryParameter;}

	public CsvQueryProcessor(QueryParameter queryParameter){this.queryParameter = queryParameter;}

	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) throws IOException {

		FileReader fReader;
		BufferedReader bReader;
		DataSet dataSet = new DataSet();

		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */
		try{
			fReader = new FileReader(queryParameter.getFileName());
			bReader = new BufferedReader(fReader);
		} catch (FileNotFoundException e) {
			fReader = new FileReader("data/ipl.csv");
			bReader = new BufferedReader(fReader);
		}

		/*
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 */
		String[] headerArray = bReader.readLine().split(",");
		bReader.mark(1);

		/*
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while splitting, this might cause exceptions later
		 */
		String[] dataForFieldsArray = (bReader.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 18));

		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		Header headerMap = new Header();
		for(int i = 0; i < headerArray.length; i++){
			headerMap.put(headerArray[i], i);
		}

		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
		 * is having data type <Integer,String> to contain the index of the field and
		 * it's data type. To find the dataType by the field value, we will use
		 * getDataType() method of DataTypeDefinitions class
		 */
		RowDataTypeDefinitions rdtdMap = new RowDataTypeDefinitions();
		for(int i = 0; i < dataForFieldsArray.length; i++){
			rdtdMap.put(i, DataTypeDefinitions.getDataType(dataForFieldsArray[i]));
		}

		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */

		/* reset the buffered reader so that it can start reading from the first line */
		bReader.reset();
		Filter filter = new Filter();
		String oneLineOfWords;
		long setRow = 1;

		/*
		 * skip the first line as it is already read earlier which contained the header
		 */


		/* read one line at a time from the CSV file till we have any lines left */
		while((oneLineOfWords = bReader.readLine()) != null){

			boolean shouldContinue;
			List<Restriction> queryRestrictions = queryParameter.getRestrictions();
			String [] currentLineData = oneLineOfWords.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 18);
			List<Boolean> booleans = new ArrayList<>();

			//check if the current query has restrictions
			if(queryRestrictions == null){
				shouldContinue = true; //if no restrictions, move onto the field selection and corresponding value essentially
			} else {
				//if restrictions exist, cycle through each individual one
				for(Restriction eachRestriction : queryRestrictions){
					//set the index to the index of where said restriction's property name lies within the header map
					int index = headerMap.get(eachRestriction.getPropertyName());
					//add to booleans list the evaluation (t/f) of our current lines value (corresponding to field index of restriction property), the restriction itself, and the data type of the corresponding index in the rdtdmap where that field's datatype is defined
					//basically checking, line by line, restriction by restriction, if the corresponding value of this line does in fact "pass" the restriction statement or not and adding to list
					booleans.add(filter.evaluateExpression(currentLineData[index].trim(), eachRestriction, rdtdMap.get(index)));
				}
				//setting the boolean which tells us whether we should continue or not to the result of solveOperators being passed our booleans list
				//and the query parameter's logical operators themselves
				shouldContinue = solveOperators(booleans, queryParameter.getLogicalOperators());
			}

			if(shouldContinue){

				Row rowMap = new Row();
				List<String> queryFields = queryParameter.getFields();

				for(String eachField : queryFields){
					if(eachField.equals("*")){
						for(int l = 0; l < headerArray.length; l++){
							rowMap.put(headerArray[l].trim(), currentLineData[l]);
						}
					} else {
						//in one row, we are putting each specified field corresponding to the value of our current line which shares the same index as said field itself
						rowMap.put(eachField, currentLineData[headerMap.get(eachField)]);
					}
				}

				//then we are taking this row which is a hashmap, and adding to to our dataset with a corresponding long which increments per row added
				dataSet.put(setRow++, rowMap);

			}


		}

		/*
		 * once we have read one line, we will split it into a String Array. This array
		 * will continue all the fields of the row. Please note that fields might
		 * contain spaces in between. Also, few fields might be empty.
		 */

		/*
		 * if there are where condition(s) in the query, test the row fields against
		 * those conditions to check whether the selected row satifies the conditions
		 */

		/*
		 * from QueryParameter object, read one condition at a time and evaluate the
		 * same. For evaluating the conditions, we will use evaluateExpressions() method
		 * of Filter class. Please note that evaluation of expression will be done
		 * differently based on the data type of the field. In case the query is having
		 * multiple conditions, you need to evaluate the overall expression i.e. if we
		 * have OR operator between two conditions, then the row will be selected if any
		 * of the condition is satisfied. However, in case of AND operator, the row will
		 * be selected only if both of them are satisfied.
		 */

		/*
		 * check for multiple conditions in where clause for eg: where salary>20000 and
		 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
		 */

		/*
		 * if the overall condition expression evaluates to true, then we need to check
		 * if all columns are to be selected(select *) or few columns are to be
		 * selected(select col1,col2). In either of the cases, we will have to populate
		 * the row map object. Row Map object is having type <String,String> to contain
		 * field Index and field value for the selected fields. Once the row object is
		 * populated, add it to DataSet Map Object. DataSet Map object is having type
		 * <Long,Row> to hold the rowId (to be manually generated by incrementing a Long
		 * variable) and it's corresponding Row Object.
		 */

		/* return dataset object */
		bReader.close();
		fReader.close();
		return dataSet;
	}

	private boolean solveOperators(List<Boolean> booleans, List<String> operators) {
		switch (booleans.size()) {
			default: return false;
			//if there is only one boolean in list, return its value to the initial shouldContinue boolean which
			//essentially decides whether to write field--value to the json/gson in the end
			case (1): return booleans.get(0);
			case (2):
				if (operators.get(0).matches("and")) return booleans.get(0) & booleans.get(1);
				else return booleans.get(0) | booleans.get(1);
			case (3):
				int i = operators.indexOf("and");
				switch (i) {
					default:
						return false;
					case (-1):
						return booleans.get(0) | booleans.get(1) | booleans.get(2);
					case (0):
						return booleans.get(0) & booleans.get(1) | booleans.get(2);
					case (1):
						return booleans.get(0) | booleans.get(1) & booleans.get(2);
				}
		}
	}

}
