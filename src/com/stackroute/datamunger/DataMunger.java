package com.stackroute.datamunger;

import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.writer.JsonWriter;

import java.util.Scanner;

public class DataMunger {

	public static void main(String[] args) {

/*
here we take in the initial user's query (via scanner),
then we pass that user query to the Query Class' executeQuery method
which calls on the CsvQueryProcessor to getResultSet(of said user query) from the user's query and matching data in our file
the CsvQueryProcessor calls on different internal/lower-level methods itself to access different parts of the query input/user query
and stores each part in the QueryParameter's members as one entire object, which itself relies on QueryParser and its own individual methods
which implement the actual logic of performing operations on the user's query string to then later set as QueryParameter

in CsvQueryProcessor, we collect a Header map and RowDataTypeDefinitions map
to later utilize in finding each value (string) of each line's corresponding field value/data type to then
pass to Filter along with the user's query restriction.
------------------
(in between here (above) we also rely on DataTypeDefinitions which contains the logic for checking each header and
corresponding value to associate the specific header with its correct data type)
-------------------
Once we pass the field value/data type & the user's query restriction(s) to filter's evaluateExpression,
it then relies on its own individual methods which correspond to the restrictions associated conditional operator
each individual operator will return a value to let CsvQueryProcessor know if the given value satisfies the restriction or not
*NOTE: the data type is passed to filter so that we can let each evaluateExpression relied on method know how to actually
compare the given value and see if it does indeed satisfy said restriction

Once we know if a certain value should be included in the final resultSet, by relying on Filter and an additional method in CsvQueryProcessor
which helps us to further determine if a value should be included based on restrictions AND the logical operators of a user's query,
we write a map of the field and its passing, corresponding particular line value (which again, must satisfy restrictions and "logicals")
which is then associated with a long value, to be added to an instance of DataSet which is a LinkedHashMap of longs and rows

the resultSet which is a DataSet instance is passed to query, which is passed to JsonWriter's writeToJson method
which utilized a 3rd party library to take in that resultSet which is agAIN passed to Gson's (3rd party) toJson() method
we try to write to a new external file in our data folder, and JsonWriter returns a boolean indicating whether it successfully executed
the entire process successfully, essentially.

then we close the scanner and are finally done.
 */

		
		// Read the query from the user
		Scanner scanForQuery = new Scanner(System.in);
		String userQuery = scanForQuery.nextLine();

		/*
		 * Instantiate Query class. This class is responsible for: 1. Parsing the query
		 * 2. Select the appropriate type of query processor 3. Get the resultSet which
		 * is populated by the Query Processor
		 */
		Query query = new Query();
		

		/*
		 * Instantiate JsonWriter class. This class is responsible for writing the
		 * ResultSet into a JSON file
		 */
		JsonWriter writeSetToJson = new JsonWriter();
		
		/*
		 * call executeQuery() method of Query class to get the resultSet. Pass this
		 * resultSet as parameter to writeToJson() method of JsonWriter class to write
		 * the resultSet into a JSON file
		 */
		writeSetToJson.writeToJson(query.executeQuery(userQuery));

		scanForQuery.close();
		
	}
}
