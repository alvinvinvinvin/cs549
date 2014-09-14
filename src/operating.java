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
		
		/*
		 * First level options will guide users to two main operations: 1 for adding new worker; 2 for list information and following operations.
		 *Each while loop is for each level. It will be easier for users going forward or backward to other levels.
		 *You will see a lot of while loops later. Be ware :)
		 */
				
		//Level 1. Main menu.	
		while(true){
			
			//Welcome informations and options for user.
			System.out.println("welcom! \n"
					+ "enter 1 to add new worker \n"
					+ "enter 2 to list all workers \n"
					+ "whenever you want to quit please enter \"quit\"");
			
			//Scan user's input and pass it to string "Cmd1" for following operations.
			Scanner scanCmd1 = new Scanner(System.in);
			String Cmd1 = scanCmd1.next();

			//If user chose "add new worker".	
			if(Cmd1.equals("1")){
				//Instantiate a "worker" to add
				worker newWorker = new worker();
				
				//Jump to level 1.1: "adding level". More details in each method.
				while (true) {
					
					//Level 1.1.1: ID level
					newWorker.ID = enterID();
					//Level 1.1.2: Name level
					newWorker.name = enterName();
					//Level 1.1.3: Gender level
					newWorker.gender = enterGender();
					//Level 1.1.4: Position level
					newWorker.position = enterPosition();
					
					//Add this new worker to database. If it is successful, notice user. More details in "addWorker" method.
					if (ds.addWorker(newWorker)) {
						System.out
								.println("Adding worker successful. To adding more please enter 1. To go back main menu please enter other keys:");
						
						Scanner scanAdd = new Scanner(System.in);
						String CmdAdd = scanAdd.next();
						
						if (CmdAdd.equals("1")) {
							//Doing nothing to go back top of Level 1.1-"Adding level" to continually adding more.
						}
						else{
							//If user entered other key then jump out the "adding level" loop and back to main menu.
							break;
						}
					}
					
					/*
					 * If it is not successful, notice user to try again. The detailed reasons suppose to be shown earlier. 
					 * The program will go back to top of "adding level" to add worker again.
					 * Basically following "else" statement is not reachable.
					 */
					else{
						System.out.println("For some reason, adding is failed. Please try again. \n"
								+ "=========================");
					}
				}
				//End of the "adding level" while loop. 
			}
			//Adding worker ends.
			
			//Level 1.2. List all workers.
			else if(Cmd1.equals("2")){
				
				//I will explain why I use while loop here again later.
				while (true) {
					System.out.println("===========================");
					System.out.println("These are all workers: ");
					
					//More details in each method.
					displayAllWorkers();
					displayByCate();
					
					/*
					 * Deleting option is included in this because it needs entering workers ID to delete.
					 * By listing all workers information it will be easier to look up the ID to delete.
					 */
					System.out
							.println("If you want to delete one of those workers, please enter \"d\"; "
									+ "To back to main menu, please enter other keys.");
					
					//Scan user's input
					Scanner scanDelete = new Scanner(System.in);
					String CmdDelete = scanDelete.next();
					if (CmdDelete.equalsIgnoreCase("d")) {
						
						//More details in method.
						deleteWorker();
					}
					/*
					 * After deleting a worker, system will go back to top of loop to display all workers again
					 * for checking whether deleting worked or not. Moreover, to give user choice to continually deleting.
					 */
					
					else
					{
						//If user entered other command then it will break this loop and go back to main menu.
						break;
					}
				}
			}
			
			//User is willing to quit system.
			else if(Cmd1.equalsIgnoreCase("quit")){
				System.out.println("Thanks for using. Bye!");
				System.exit(0);//whole system is shut down.
			}
			
			//User input invalid commands.
			else{
				System.out.println("please enter valid command: \n"
						+ "=================================");
			}
			//Go back to top of this "while" loop(AKA the main menu).
		}
	}
	
	
	/*
	 * Return true if "input" is a integer. Using regular expression. 
	 * It is referenced by any time user want to input "ID".
	*/
	public static boolean checkInt(String input){
		boolean mat = input.matches("\\d+");
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
			System.out.println("Please enter worker's ID, integer only:");
			
			//Scan user's input, pass it to string "Cmd2" for later on.
			
			Scanner scanCmdID = new Scanner(System.in);
			String CmdID = scanCmdID.next();
			
			//Numerical checking for user's input. See more details in method "checkInt" method.
			if(checkInt(CmdID)){
				/*
				 * If "CmdID" is integer then do the existing checking, 
				 * but first we have to pass it to convert it to integer for function "checkExist".
				 * See more details in DataStore.java.
				 */
				int id = Integer.parseInt(CmdID);//Parse input to integer for checking and storing into database.
				
				if(ds.checkExist(id)){//Checking ID just input whether exists or not.		
					System.out.println("ID exists, please try again:");		
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
			System.out.println("please enter worker's name:");
			
			//Scan user's input and pass it to string "name".
			Scanner scanNm = new Scanner(System.in);
			String name = scanNm.next();
			
			if(name.equals(null)){
				//If "name" is null then output following sentence and go back to top of loop to ask user to enter again.
				System.out.println("User's name couldn't be null, please enter again.");
			}
			else{
				//If "name" is not null then return it as user's name.
				return name;
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
					.println("please enter worker's gender: \"m\" for male, \"f\" for female:");
			
			//Scan user's input and pass it to string "gender".
			Scanner scanGd = new Scanner(System.in);
			String gender = scanGd.next();
			
			//Return "Male" if user chose "m".
			if (gender.equals("m")){
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
					.println("please choose position: enter \"e\" : for employee; enter \"a\" for assistant; enter \"s\" for supervisor:");
			
			//Scan user's input, pass it to string "position", save it for later using.
			Scanner scanPs = new Scanner(System.in);
			String position = scanPs.next();
			
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
		
		System.out.println("ID|Name|Gender|position");
		
		//Display all workers line by line. Split different categories by "|".
		for(int i = 0; i < numW; i++){
			System.out.println(workers[i].ID+"|"+workers[i].name+"|"+workers[i].gender+"|"+workers[i].position);
		}
		System.out.println("The total number of workers is: "+numW);//Display total number of workers.
		System.out.println("===========================");
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
		System.out.println("The number of male workers is: "+rsn.maleNum);
		System.out.println("The number of female workers is: "+rsn.femaleNum);
		System.out.println("The number of employees is: "+rsn.employeeNum);
		System.out.println("The number of assistants is: "+rsn.assistanNum);
		System.out.println("The number of supervisors is: "+rsn.supervisorNum);
		System.out.println("*************************************************************");
	}
	
	/*
	 * Method for deleting a record of worker from database.
	 */
	@SuppressWarnings("resource")
	public static void deleteWorker(){
		while (true) {
			System.out.println("please enter worker's ID you want to delete:");
			
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
			else
			{
				//If user didn't enter an integer then system will ask him to reenter.
				System.out.println("Invalid ID, please try again. ");
			}
		}
	}
}
