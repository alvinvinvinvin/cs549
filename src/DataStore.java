/*
 * Han Chen
 * CS 549
 * 9/12/2014
 * Assignment 1 - part 1
 */

/*
 * This is a class for communicating data among program and database. It integrated couple functions for adding,
 * deleting, checking, searching data etc. For more
 */

import java.sql.*;

public class DataStore {
	
	private static final String TABLE_NAME = "worker";
	Connection con;

	public DataStore(String db) {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + db);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean checkExist(int ID){
		try{
			Statement s =con.createStatement();
			ResultSet r = s.executeQuery("select ID from "+TABLE_NAME+" where ID = '"+ID+"'");
			if(r.next())
				return true;
			else return false;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean addWorker(worker newWorker){
		int ID = newWorker.ID;
		String name = newWorker.name;
		String gender = newWorker.gender;
		String position = newWorker.position;
		try{
			Statement s = con.createStatement();
			s.executeUpdate("insert into "+TABLE_NAME+" values('"+ID+"','"+name+"','"+gender+"','"+position+"')");
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public int getNumWorker(){
		int num = 0;
		try{
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select * from "+TABLE_NAME);
			while(r.next()){
				num++;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return num;
	}
	
	public worker[] getAllWorker(){
		int i = 0;
		int numWorker = getNumWorker();
		worker[] newWorkers = new worker[numWorker];
		for(int j=0;j<numWorker;j++){
			newWorkers[j] = new worker();
		}
		try{
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select * from "+TABLE_NAME);
			while(r.next()){
				newWorkers[i].ID = r.getInt(1);
				newWorkers[i].name = r.getString("name");
				newWorkers[i].gender = r.getString("gender");
				newWorkers[i].position = r.getString("position");
				i++;
			}
		}catch(SQLException e){
			System.out.println("123");
			System.out.println(e.getMessage());
		}
		return newWorkers;
	}
	
	public resultNum numByCategory(){
		resultNum rsn = new resultNum();
		try{
			Statement s = con.createStatement();
			ResultSet rgm = s.executeQuery("select * from "+TABLE_NAME+" where gender='Male'");
			while(rgm.next()){
				rsn.maleNum++;
			}
			ResultSet rgf = s.executeQuery("select * from "+TABLE_NAME+" where gender='Female'");
			while(rgf.next()){
				rsn.femaleNum++;
			}
			ResultSet rpe = s.executeQuery("select * from "+TABLE_NAME+" where position='Employee'");
			while(rpe.next()){
				rsn.employeeNum++;
			}
			ResultSet rpa = s.executeQuery("select * from "+TABLE_NAME+" where position='Assistant'");
			while(rpa.next()){
				rsn.assistanNum++;
			}
			ResultSet rps = s.executeQuery("select * from "+TABLE_NAME+" where position='Supervisor'");
			while(rps.next()){
				rsn.supervisorNum++;
			}
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return rsn;
	}
	
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

}
