package commandPattern; 

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**   
* Filename:    AfterUsing2.java   
* Copyright:   Copyright (c)2014  
* Company:   UWL
* @author: Han Chen
* @version:    1.0   
* @since:  JDK 1.7
* Create at:   Nov 12, 2014 3:27:31 PM   
* Description:  
* 	Version 2 of command pattern example.
*   Menu cannot send itself to cook directly, so there must be a class called "waitress" to help customer
*   finish the order process and send the specific menu to cook. Moreover, waitress can help customer
*   to record the sequence of orders thereby to implement "undo", "redo", or ordering particular 
*   menu couple times. Same way, we also can set different of cooks if you don't like current one.
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* Nov 12, 2014 Han Chen      1.0     1.0 Version   
*/
public class AfterUsing2 {
	static boolean flag = true;
	static int money = 0;
	static Customer customer = new Customer();
	static Waitress w = new Waitress();
	public static void main(String[] args){
		customer.order();
	}
	/**
	 * Different menus containing different type of orders.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Menu1 extends Menu{
		public Menu1(Cook c){
			super(c);
		}
		public void contained(){
			c.makeBurger();
		}
		public String getPrice(){
			return "-----10 dollars.";
		}
	}
	
	static class Menu2 extends Menu{
		public Menu2(Cook c){
			super(c);
		}
		public void contained(){
			c.makeFish();
		}
		public String getPrice(){
			return "-----12 dollars.";
		}
	}
	
	static class Menu3 extends Menu{
		public Menu3(Cook c){
			super(c);
		}
		public void contained(){
			c.makeBurger();
			c.makeFish();
		}
		public String getPrice(){
			return "-----22 dollars.";
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
		public abstract void contained();
		public abstract String getPrice();
	}
	
	static class Customer{
		private Cook c = new Cook();
		/**
		 * Customer wants to order food. He doesn't care how the food comes. He only talks to waitress.
		 * @param w Waitress called by customer.
		 * @param order The order made by customer.
		 */
		public void order(){
			System.out.println("Customer is hungury. Enter 1 for burger, 2 for fish, 3 for all, other for quit.");
			Scanner scan = new Scanner(System.in);
			
			while (flag) {
				int order = scan.nextInt();
				//Customer called that waitress and gave her an order. 
				//Waitress will record the order into an empty menu.
				w.getOrder(convert(order));
				if(flag == false) break;//If customer didn't order anything or would like to quit.
				w.sendOrder();//This waitress sent the menu to cook.
			}
			//Display all orders which customer has ordered.
			System.out.println("Here are all orders: ");
			w.sendAllOrders();
			System.out.println("Checking out.......");
			System.out.println("The total price is: "+(money/2)+" dollars.");
			System.out.println("Thank you.");
			System.out.println("Bye.");
			
			/**
			 * TODO:
			 * If you want, you can make more different menus or different method to redo or undo the
			 * orders.
			 * 
			 */
		}
		
		private Menu convert(int o){
			switch(o){
			case 1:
				return new Menu1(c);
			case 2:
				return new Menu2(c);
			case 3:
				return new Menu3(c);
			default:
				flag = false;
				break;
			}
			return null;
		}
	}
	
	/**
	 * Waitress will be invoker to receive different command from customer and send the commands
	 * to cook. I used sort of factory pattern here as well to generate different menus.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Waitress{
		private Menu m;
		private List<Menu> mList = new ArrayList<Menu>();
		/**
		 * Waitress writes specific menu into an empty menu.
		 * @param o The order from customer.
		 */
		public void getOrder(Menu m){		
			if (m != null) {
				this.m = m;
				mList.add(m);
			}
		}
		/**
		 * Send the finished menu to cook.
		 */
		public void sendOrder(){
			m.contained();
		}
		/**
		 * Display all orders which customer has ordered previously.
		 */
		public void sendAllOrders(){
			int i =0;
			if (mList != null) {
				for (Menu m : mList) {
					System.out.println("Order " + (i + 1) + ":");
					m.contained();
					System.out.println(m.getPrice());
					i++;
				}
			}
			System.out.println("So the total number of orders is: "+i);
			System.out.println("Please check out.");
		}
	}
	/**
	 * This cook is only able to cook two dishes: Burger and Fish.
	 * Hmmmm. That's good enough for me.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Cook{
		public void makeBurger(){
			System.out.println("Burger's up.");
			money += 10;
		}
		public void makeFish(){
			System.out.println("Fish's up");
			money +=12;
		}
	}
}
 