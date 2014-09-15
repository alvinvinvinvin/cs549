/*
 * Han Chen
 * CS 549
 * 9/12/2014
 * Assignment 1 - part 1
 */

/*
 * This is the operating class for implementing main functionalities such as 
 * adding new worker, deleting existed worker, displaying specified number of workers etc.
 * There also are some small functions for dealing I/O stream such as numerical
 * checking and so on. For more information please read "README" file.
 */

import java.util.*;

public class operating {
	
	//Connect to database
	static DataStore ds = new DataStore("data.db");
	
	//Main method for implementation all functionalities of this program.
	@SuppressWarnings("resource")
	public static void main(String[] args){
		
		System.out.println("Welcome!");
		
		//Display all workers information first. See more details pleasing go to check this method.
		displayAllWorkers();
		
		while(true)
		{
			//Listing all options.
			System.out.println("Thanks for using. Here are some options: \n"
					+ "1. Adding a new worker. \n"
					+ "2. Deleting a worker. \n"
					+ "3. Display the number of workers in a particular category. \n"
					+ "4. Display the total number of workers. \n"
					+ "5. Exiting program. \n"
					+ "Tips: whenever you want to back to main menu, please enter \"bm\".");
			System.out.println();
			System.out.println("Please enter command [1-5]:");
			
			//Scan user's input and pass it to string "Cmd1" saving it for later to use.
			Scanner scanCmd1 = new Scanner(System.in);
			String Cmd1 = scanCmd1.next();
			
			//If user input "1" which means he is willing to add a new worker.
			if(Cmd1.equals("1")){
				
				//Instantiate a worker object prepared for adding.
				worker newWorker = new worker();
				
				//Obtain new worker's ID by method "enterID". For more details please go to check comments in method.
				int ID = enterID();
				if(ID == 0){
					continue;//User wants to stop entering and go back to main menu. Triggered when user input "bm".
				}
				else
					newWorker.ID = ID;
				
				//Obtain new worker's name by method "enterName". For more details please go to check comments in method.
				String name = enterName();
				if(name.equals("0")){
					continue;//User wants to stop entering and go back to main menu. Triggered when user input "bm".
				}
				else
					newWorker.name = name;
				
				//Obtain new worker's gender by method "enterGender". For more details please go to check comments in method.
				String gender = enterGender();
				if(gender.equals("0")){
					continue;//User wants to stop entering and go back to main menu. Triggered when user input "bm".
				}
				else
					newWorker.gender = gender;
				
				//Obtain new worker's position by method "enterPosition". For more details please go to check comments in method.
				String position = enterPosition();
				if(position.equals("0")){
				 	continue;//User wants to stop entering and go back to main menu. Triggered when user input "bm".
				}
				else
					newWorker.position = position;
				
				//Adding this worker object to database through method "addWorker". For more information please go to check it in "DataStrore.java".
				if (ds.addWorker(newWorker)) {
					System.out.println("Adding successful.");
				}
				
				//This statement basically will not be triggered only if there were something wrong with database.
				else{
					System.out.println("The database may be broken again..Oops.");
				}
				
			}
			
			//User entered "2" willing to delete a worker. For more information please go to check "deleteWorker" method.
			else if(Cmd1.endsWith("2")){
				deleteWorker();
			}
			
			//User entered "3" willing to display workers' number by categories. For more information please go to check "displayByCate" method.
			else if(Cmd1.equals("3")){
				displayByCate();
			}
			
			/*
			 * User entered "4" willing to display number of all workers. However it will show all information of workers.
			 * I use "displayAllWorkers" here because it includes the total number of workers.
			 */
			else if(Cmd1.equals("4")){
				displayAllWorkers();
			}
			
			//User want to quit system.
			else if(Cmd1.equals("5"))
			{
				System.out.println("Thanks for using. Bye!");
				System.exit(0);//System is shut down.
			}
			
			//User entered something else of "1" to "5".
			else{
				System.out.println("Please enter a valid command.");
			}
		}
	}
	
	/*
	 * Return true if "input" is a integer. Using regular expression. 
	 * "\d+" means checking all input characters in string whether is decimal number.
	 * The first "\" is for helping java recognize the special symbol --- the second "\"--- is component of "\d+".
	 * It is referenced by any time user want to input "ID".
	*/
	public static boolean checkInt(String input){
		boolean mat = input.matches("\\d+");
		return mat;
	}
	
	/*
	 * Return true if "input" is pure alphabetic string. Using regular expression.
	 * [A-Za-z] will match all the alphabets (both lower case and upper case).
	 * "+$" means it will keep tracking every single characters until the end of the string to check it by rules showed before.
	 * This is for checking the input while user is entering new worker's name.
	 */
	public static boolean checkAlpha(String input){
		boolean mat = input.matches("[A-Za-z]+$");
		return mat;
	}
	
	/*
	 * Method for helping dealing some checking processes 
	 * while user want to input "ID" to the system.
	*/
	@SuppressWarnings("resource")
	public static int enterID(){
		while(true){			
			/*
			 * User is going to be allowed to enter information of this new worker individually.
			 * The first one is ID, it is integer only.
			 */
			System.out.println("Please enter worker's ID, integer only. \n"
					+ "\"0\" is invalid ID. If you enter \"0\", it will go back to main menu:");
			
			//Scan user's input, pass it to string "Cmd2" for later on.
			Scanner scanCmdID = new Scanner(System.in);
			String CmdID = scanCmdID.next();
			
			if(CmdID.equalsIgnoreCase("bm")){
				return 0;
			}
			
			//Numerical checking for user's input. See more details in method "checkInt" method.
			else if(checkInt(CmdID)){
				/*
				 * If "CmdID" is integer then do the existing checking, 
				 * but first we have to pass it to convert it to integer for function "checkExist".
				 * See more details in DataStore.java.
				 */
				int id = Integer.parseInt(CmdID);//Parse input to integer for checking and storing into database.
				
				if(ds.checkExist(id)){//Checking ID just input whether exists or not.		
					System.out.println("ID exists. Here is all occupied ID: \n"
							+ ds.existingID()+"\n"
							+ " please try other numbers again:");		
			     }
			     else{
			    	return id;//Finishing checking process then return the value.
			     }
			}
			else{
				//If input is not valid integer then program will go back to the top of this loop to let user enter it again.
				System.out.println("please enter valid integer to be ID number: ");
			}
		}
	}
	
	/*
	 * Function for checking whether user's input is null or 
	 * not of user's name while adding new worker.
	 */
	@SuppressWarnings("resource")
	public static String enterName(){
		while(true){
			System.out.println("please enter worker's name. \n"
					+ "Alphabetic characters only. \n"
					+ "Entering \"bm\" will go back to main menu:");
			
			//Scan user's input and pass it to string "name".
			Scanner scanNm = new Scanner(System.in);
			String name = scanNm.next();
			
			if(name.equalsIgnoreCase("bm")){
				return "0";
			}
			
			//Check whether the name is alphabetic characters only
			if (checkAlpha(name)) {
				if (name.equals(null)) {
					//If "name" is null then output following sentence and go back to top of loop to ask user to enter again.
					System.out
							.println("User's name couldn't be null, please enter again.");
				} else {
					//If "name" is not null then return it as user's name.
					return name;
				}
			}
			else{
				System.out.println("Alphabetic characters only. Please try again.");
			}
		}
	}
	
	/*
	 * A method for allowing user to choose different gender 
	 * by entering different command while adding new worker..
	 */
	@SuppressWarnings("resource")
	public static String enterGender(){
		while (true) 
		{
			System.out
					.println("please enter worker's gender. \n"
							+ "\"m\" for male, \"f\" for female: \n"
							+ "Entering \"bm\" will go back to main menu.");
			
			//Scan user's input and pass it to string "gender".
			Scanner scanGd = new Scanner(System.in);
			String gender = scanGd.next();
			
			if(gender.equalsIgnoreCase("bm")){
				return "0";
			}
			//Return "Male" if user chose "m".
			else if (gender.equals("m")){
				return "Male";
			}
			
			//Return "Female" if user chose "f".
			else if(gender.equals("f")){
				return "Female";
			}
			
			//If user entered other words it will ask user to enter again by going back to top of loop.
			else {
				System.out
						.println("please enter valid command(\"m\" or \"f\"): ");
			}
		}
	}
	
	/*
	 * Function for letting user choose different 
	 * positions while adding new worker.
	 */
	@SuppressWarnings("resource")
	public static String enterPosition(){
		while (true) {
			System.out
					.println("please choose position: enter \"e\" : for employee; enter \"a\" for assistant; enter \"s\" for supervisor: \n"
							+ "Entering \"bm\" will go back to main menu.");
			
			//Scan user's input, pass it to string "position", save it for later using.
			Scanner scanPs = new Scanner(System.in);
			String position = scanPs.next();
			
			if(position.equalsIgnoreCase("bm")){
				return "0";
			}
			//If user chose "e" then return "Employee" to be new worker's position.
			if (position.equals("e")){
				return "Employee";
			}
			
			//If user chose "a" then return "Assistant" to be new worker's position.
			else if(position.equals("a")){
				return "Assistant";
			}
			
			//If user chose "s" then return "Supervisor" to be new worker's position.
			else if(position.equals("s")) {
				return "Supervisor";
			} 
			
			//If user entered invalid command then notice user that and go back to top of loop to ask user reenter.
			else {
				System.out
						.println("please enter valid command(\"e\", \"a\", or \"s\")");
			}
		}
	}
	
	/*
	 * Method for displaying all workers' information on screen.
	 */
	public static void displayAllWorkers(){
		
		//Getting the total number of workers for displaying. See more details in "getNumWorker" method in "DataStore.java".
		int numW = ds.getNumWorker();
		
		//Instantiate an array of "worker" for receiving all workers' information generated by method "getAllWorker".
		worker[] workers = new worker[numW];
		workers = ds.getAllWorker();//See more details in "getAllWorker" method in "DataStore.java".
		
		System.out.println("Here is all workers' information: \n");
		System.out.println("ID|Name|Gender|position");
		
		//Display all workers line by line. Split different categories by "|".
		for(int i = 0; i < numW; i++){
			System.out.println(workers[i].ID+"|"+workers[i].name+"|"+workers[i].gender+"|"+workers[i].position);
		}
		System.out.println();
		System.out.println("The total number of workers is: "+numW);//Display total number of workers.
		System.out.println();
	}
	
	/*
	 * Display number of workers by individual categories.
	 */
	public static void displayByCate(){
		
		//Instantiate "resultNum" to store different numbers. For detailed structure of this class please go to "resultNum.java".
		resultNum rsn = new resultNum();
		
		//The method "numByCategory" will search database to get those numbers we need. For more details please check "DataStore.java".
		rsn = ds.numByCategory();
		
		//Display those values line by line.
		System.out.println("The number of male employees is: "+rsn.maleEmployeeNum);
		System.out.println("The number of male assistants is: "+rsn.maleAssistantNum);
		System.out.println("The number of male supervisors is: "+rsn.maleSupervisorNum);
		System.out.println("The number of female employees is: "+rsn.femaleEmpoyeeNum);
		System.out.println("The number of female assistants is: "+rsn.femaleAssistantNum);
		System.out.println("The number of female supervisors is: "+rsn.femaleSupervisorNum);
		System.out.println();
	}
	
	/*
	 * Method for deleting a record of worker from database.
	 */
	@SuppressWarnings("resource")
	public static void deleteWorker(){
		while (true) {
			System.out.println("please enter worker's ID you want to delete. \n "
					+ "To quit deleting process please enter \"bm\":");
			
			//Scan user's input. ID is the unique property of worker so it is easier to be used for deleting specific user.
			Scanner scanDeID = new Scanner(System.in);
			String CmdID = scanDeID.next();
			
			//Checking user's input whether it is a integer or not.
			if (checkInt(CmdID)) {
				
				//If it is integer then convert string of input to integer.
				int ID = Integer.parseInt(CmdID);
				
				//Checking whether the ID user input exists or not.
				if (ds.checkExist(ID))
				{
					//If the ID user input exists the delete the record by this ID. For more details please check "deleteWorker" method in "DataStore.java".
					if (ds.deleteWorker(ID)) 
					{
						//Noticing user deleting is successful and jumping out of the deleting level loop.
						System.out.println("Deleting successful!");
						break;
					} 
					else 
					{
						//This statement should not be reached only if something wrong happened during deleting process in database.
						System.out.println("Oops, deleting failed!");
					}
				} 
				else 
				{
					//If ID does not exist then notice user to enter other IDs.
					System.out.println("ID does not exist. Please try again.");
				}
			} 
			
			//If user regrets to delete and enters "b", program will go back to top of "listing all worker" loop (level 1.2).
			else if (CmdID.equalsIgnoreCase("bm")){
				break;
			}
			
			else
			{
				//If user didn't enter an integer then system will ask him to reenter.
				System.out.println("Invalid ID, please try again. ");
			}
		}
	}
}
