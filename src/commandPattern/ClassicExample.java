package commandPattern;   

import java.util.ArrayList;
import java.util.List;

/**   
* Filename:    ClassicExample.java   
* Copyright:   Copyright (c)2014  
* Company:   UWL
* @author: Han Chen
* @version:    1.0   
* @since:  JDK 1.7
* Create at:   Nov 12, 2014 2:19:19 PM   
* Description:  
*   A classic example about command pattern.
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* Nov 12, 2014 Han Chen      1.0     1.0 Version   
*/     
public class ClassicExample {
	
	public static void main(String[] args){
		Client client = new Client();
		client.commanding();
	}
	/**
	 * Client wants to send a command. 
	 * He doesn't care how the command is going to be executed and who will execute the command.
	 * He only talks to invoker. Invoker will receive the concrete command from client and figure out
	 * how to send this command and who is this command's receiver by himself.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Client{
		public void commanding(){
			System.out.println("Client is willing to call an invoker to send a ConcreteCommand to Receiver.");
			Invoker invoker = new Invoker();//Invoker is ready to receive command from client.
			//A concrete command containing the specific receiver is generated, and invoker received this command.
			System.out.println("Invoker received command.");
			invoker.receiveCommand(new ConcreteCommand(new Receiver()));
			invoker.sendCommand();//Invoker sent this command.
			
			//Invoker can also receive different type of command and record them into a list.
			//Thereby this way can implement "undo", "redo", or send specific commands couple times.
			//Since in this current example there is only one type command "ConcreteCommand",
			//it can only repeat the same command. If you want add more types of command then you
			//will see the difference.
			for(int i=0;i<4;i++){
				System.out.println("Invoker received command: "+(i+1));
				invoker.receiveCommand(new ConcreteCommand(new Receiver()));
			}
			invoker.sendFirstCommand();
			invoker.sendLastCommand();
			invoker.sendAllCommand();
		}
	}
	
	/**
	 * Abstract command class to generate different command as factory.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static abstract class Command{
		protected Receiver r;
		public Command(Receiver r){
			this.r = r;
		}
		public abstract void execute();
	}
	
	/**
	 * Receiver who will execute the command eventually.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Receiver{
		public void action(){
			System.out.println("Receiver received command and did action.");
		}
	}
	
	/**
	 * Invoker who will receive command from client, record it, and send it to receiver.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Invoker{
		private Command c;
		private List<Command> cList = new ArrayList<Command>();
		public void receiveCommand(Command c){
			this.c = c;
			this.cList.add(c);
		}
		public void sendCommand(){
			System.out.println("Invoker is sending command to receiver.");
			c.execute();
		}
		public void sendFirstCommand(){
			if (cList != null) {
				cList.get(0).execute();
			}
		}
		public void sendLastCommand(){
			if (cList != null) {
				int n = cList.size() - 1;
				cList.get(n).execute();
			}
		}
		public void sendAllCommand(){
			if(cList != null){
				for(Command c: cList){
					c.execute();
				}
			}
		}
	}
	
	/**
	 * Concrete command which extends from command and contain specific content.
	 * Different concrete command has different execute methods.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class ConcreteCommand extends Command{
		public ConcreteCommand(Receiver r){
			super(r);
		}
		public void execute(){
			System.out.println("ConcreteCommand is about asking \"r\" to do \"action()\"");
			r.action();
		}
	}
}
 