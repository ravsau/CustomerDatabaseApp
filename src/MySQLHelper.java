

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class MySQLHelper {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String lastName;
	private int counter =0;
	Scanner sc=new Scanner(System.in);
	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			// The MySQL driver is a JAR file that must be in the Build Path
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/customer?user=root&password=password");

			System.out.println("Enter the last name of the customer you want to search");

			lastName=sc.nextLine();



			// Statements allow to issue SQL queries to the database
			preparedStatement = connect.prepareStatement("select * from customer.Customers where LastName=?;");
			preparedStatement.setString(1, lastName);




			resultSet = preparedStatement.executeQuery();


			do{
				writeResultSet(resultSet,counter);

				counter++;
			}
			while(resultSet.next());


			// Result set get the result of the SQL query
			//resultSet = statement.executeQuery("select * from customers.Customers where LastName=?;");
			//writeResultSet(resultSet);
			/*
	            // PreparedStatements can use variables and are more efficient
	            // The primary reason to use prepared statements: More Secure!
	            // Secondary reason: you don't have to worry about quotes in your strings!
	            preparedStatement = connect
	                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
	            // "myuser, webpage, , summary, COMMENTS from feedback.comments");
	            // Parameters start with 1
	            preparedStatement.setString(1, "bart simpson");
	            preparedStatement.setString(2, "mail@domain.com");
	            preparedStatement.setString(3, "www.domain.com");
	            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
	            preparedStatement.setString(5, "Test Summary");
	            preparedStatement.setString(6, "Test Comment");
	            preparedStatement.executeUpdate();

	            preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
	            resultSet = preparedStatement.executeQuery();
	            writeResultSet(resultSet);

	            // Remove again the insert comment
	            preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
	            preparedStatement.setString(1, "Test");
	            preparedStatement.executeUpdate();

	            resultSet = statement.executeQuery("select * from feedback.comments");
	            writeMetaData(resultSet);


			 */

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	/*    private void writeMetaData(ResultSet resultSet) throws SQLException {
	        //  Now get some metadata from the database
	        // Result set get the result of the SQL query

	        System.out.println("The columns in the table are: ");
  Scanner sc=new Scanner(System.in);
	        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	        }
	    }

	 */

	private void writeResultSet(ResultSet resultSet, int index) throws SQLException {
		// ResultSet is initially before the first data set
		int choice=1;
		String newStreet;
		String newCity;
		String newState;
		String newZip;
		while (resultSet.next() && choice==1) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String customerId = resultSet.getString("CustumerID");
			String fullName = resultSet.getString("FullName");


			String lastName = resultSet.getString("LastName");
			System.out.println("Customer ID: " + customerId);
			System.out.println("Name: " + fullName);

			System.out.println("Press 1 to move to another person with the same last name. Press 2 to change the address");

	

			choice=sc.nextInt();
			if (choice==2){
				
			
 
				System.out.println("Enter the new Street Address of the person");

				newStreet=sc.nextLine();
				sc.nextLine();
				System.out.println("Enter the new City of the person");
		
				

				newCity=sc.nextLine();
				sc.nextLine();
				System.out.println("Enter the new State of the person");
		

				newState=sc.nextLine();
				sc.nextLine();
				System.out.println("Enter the new ZIP code of the person");
				sc.nextLine();

				newZip=sc.nextLine();
				preparedStatement = connect
	                    .prepareStatement("insert into  customer.Customers(StreetAddress,City,State,ZipCode) "
	                    		+ "values ( ?, ?, ?, ? )");
				
				preparedStatement.setString(1, newStreet);
	            preparedStatement.setString(2, newCity);
	            preparedStatement.setString(3, newState);
	            preparedStatement.setString(4, newZip);
	            preparedStatement.executeUpdate();
				
				
		
				

			}





			/*
			 * String website = resultSet.getString("webpage");

	            String summary = resultSet.getString("summary");
	            Date date = resultSet.getDate("datum");
	            String comment = resultSet.getString("comments");
	            System.out.println("User: " + user);
	            System.out.println("Website: " + website);
	            System.out.println("summary: " + summary);
	            System.out.println("Date: " + date);
	            System.out.println("Comment: " + comment);

			 */
		}
	}


	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}

