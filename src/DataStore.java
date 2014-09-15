/*
 * Han Chen
 * CS 549
 * 9/12/2014
 * Assignment 1 - part 1
 */

/*
 * This is a class for communicating data among program and database. It integrated couple functions for adding,
 * deleting, checking, searching data etc. For more details please look into each method.
 */

import java.sql.*;

public class DataStore {
	
	//Set static value for table name. Once we change the name of table then we could change our code easily.
	private static final String TABLE_NAME = "worker";
	
	//Instantiate a Connection for later usage.
	Connection con;

	/*
	 * Constructor for instantiating a "DataStore" in "operating.java". 
	 * The connection method is basically fixed by SQlite JDBC driver, so please don't change it.
	 */
	public DataStore(String db) {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + db);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Searching database to check whether ID exists or not. if it does then return true. OW return false.
	public boolean checkExist(int ID){
		try{
			Statement s =con.createStatement();
			ResultSet r = s.executeQuery("select ID from "+TABLE_NAME+" where ID = '"+ID+"'");
			if(r.next())
				return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	//Searching in database for adding a new worker.
	public boolean addWorker(worker newWorker){
		
		//Pass parameters to each variable for storing in database.
		int ID = newWorker.ID;
		String name = newWorker.name;
		String gender = newWorker.gender;
		String position = newWorker.position;
		try{
			Statement s = con.createStatement();
			s.executeUpdate("insert into "+TABLE_NAME+" values('"+ID+"','"+name+"','"+gender+"','"+position+"')");
			return true;//If it is successful, this method will return true. OW return false.
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	//Querying database for counting quantity of all workers
	public int getNumWorker(){
		
		//Set a integer for counting number of workers.
		int num = 0;
		try{
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select * from "+TABLE_NAME);
			
			//Increasing variable "num" when each record is found.
			while(r.next()){
				num++;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return num;
	}
	
	//Querying all workers information and add them all to an array of "worker".
	public worker[] getAllWorker(){
		
		//Declare a integer "i" for pointing each element in array.
		int i = 0;
		
		//Using "getNumWorker" to get total number of workers to set length while declaring array.
		int numWorker = getNumWorker();
		worker[] newWorkers = new worker[numWorker];
		
		//Instantiate every element in array.
		for(int j=0;j<numWorker;j++){
			newWorkers[j] = new worker();
		}
		try{
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select * from "+TABLE_NAME);
			
			/*
			 * Using methods such as "getInt" and "getString" of ResultSet "r" to read each record of specific column
			 * and pass each value to corresponding property of each element in array continually when 
			 * condition "r.next()" of "while" loop is true. The "i++" I mentioned earlier is for moving pointer to next element in array.
			 */
			while(r.next()){
				newWorkers[i].ID = r.getInt(1);
				newWorkers[i].name = r.getString("name");
				newWorkers[i].gender = r.getString("gender");
				newWorkers[i].position = r.getString("position");
				i++;
			}
		}catch(SQLException e){
			System.out.println("Querying all workers problem in DataStore");
			System.out.println(e.getMessage());
		}
		return newWorkers;//Return assigned worker array.
	}
	
	//Instantiate a resultNum to store number of workers queried from database by different categories.
	public resultNum numByCategory(){
		resultNum rsn = new resultNum();
		try{
			Statement s = con.createStatement();
			
			/*
			 * Querying number of different categories of workers from database by SQL statements
			 * then pass the value to matched property of resultNum "rsn".
			 */
			//Number of male employees.
			ResultSet rme = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Male' and position='Employee'");
			rsn.maleEmployeeNum = rme.getInt(1);
			
			//Number of male assistants.
			ResultSet rma = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Male' and position='Assistant'");
			rsn.maleAssistantNum = rma.getInt(1);
					
			//Number of male supervisors.
			ResultSet rms = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Male' and position='Supervisor'");
			rsn.maleSupervisorNum = rms.getInt(1);
			
			//Number of female employees.
			ResultSet rfe = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Female' and position='Employee'");
			rsn.femaleEmpoyeeNum = rfe.getInt(1);
			
			//Number of female assistants.
			ResultSet rfa = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Female' and position='Assistant'");
			rsn.femaleAssistantNum = rfa.getInt(1);
			
			//Number of female supervisors.
			ResultSet rfs = s.executeQuery("select count(*) from "+TABLE_NAME+" where gender='Female' and position='Supervisor'");
			rsn.femaleSupervisorNum = rfs.getInt(1);
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return rsn;//
	}
	
	//Method for deleting worker. If there is no exception it will return true. OW it will return false.
	public boolean deleteWorker(int ID){
		try {
			Statement s = con.createStatement();
			s.executeUpdate("delete from "+TABLE_NAME+" where ID='"+ID+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Method for listing all existing IDs for user who want to pick a ID to be new worker' ID.
	public String existingID(){
		String existingID = "";
		try {
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select ID from "+TABLE_NAME);
			
			//Assembly all ID one by one into a string split by ",".
			while(r.next()){
				existingID = existingID + r.getInt(1)+",";
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existingID;
	}

}
