package commandPattern; 

import java.util.Scanner;

/**   
* Filename:    AfterUsing1.java   
* Copyright:   Copyright (c)2014  
* Company:   UWL
* @author: Han Chen
* @version:    1.0   
* @since:  JDK 1.7
* Create at:   Nov 12, 2014 3:27:31 PM   
* Description:  
*   Modified version of example.
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* Nov 12, 2014 Han Chen      1.0     1.0 Version   
*/
public class AfterUsing1 {
	static boolean flag = true;
	public static void main(String[] args){
		Customer customer = new Customer();
		System.out.println("Customer is hungury. Enter 1 for burger, 2 for fish, 3 for all, other for quit.");
		Scanner scan = new Scanner(System.in);
		
		while (flag) {
			int order = scan.nextInt();
			customer.order(order);//There is no waitress so customer called cook directly.
		}
	}
	
	/**
	 * Different menus. And it can send food to customer automatically which is very smart.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Menu1 extends Menu{
		public Menu1(Cook c){
			super(c);
		}
		public void send(){
			c.makeBurger();
		}
	}
	
	static class Menu2 extends Menu{
		public Menu2(Cook c){
			super(c);
		}
		public void send(){
			c.makeFish();
		}
	}
	
	static class Menu3 extends Menu{
		public Menu3(Cook c){
			super(c);
		}
		public void send(){
			c.makeBurger();
			c.makeFish();
		}
	}
	/**
	 * Abstract menu class for contain different menu.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static abstract class Menu{
		protected Cook c;
		private Menu(Cook c){
			this.c = c;
		}
		public abstract void send();
	}
	/**
	 * Utilizing factory pattern to generate different menus and sent the food to customer directly.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Customer{
		private Cook c = new Cook();
		private Menu m;
		public void order(int o){		
			switch(o){
			case 1:
				m = new Menu1(c);
				m.send();
				break;
			case 2:
				m = new Menu2(c);
				m.send();
				break;
			case 3:
				m = new Menu3(c);
				m.send();
				break;
			default:
				flag = false;
				break;
			}
		}
	}
	/**
	 * Cook is only able to cook two dishes: Burger and Fish.
	 * Hmmmm. That's good enough for me.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Cook{
		public void makeBurger(){
			System.out.println("Burger's up.");
		}
		public void makeFish(){
			System.out.println("Fish's up");
		}
	}
}
 