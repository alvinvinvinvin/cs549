package commandPattern;   
/**   
* Filename:    BeforeUsing.java   
* Copyright:   Copyright (c)2014  
* Company:   UWL
* @author: Han Chen
* @version:    1.0   
* @since:  JDK 1.7
* Create at:   Nov 12, 2014 3:15:15 PM   
* Description:  
*   A simple example without using command pattern.
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* Nov 12, 2014 Han Chen      1.0     1.0 Version   
*/     
public class BeforeUsing {
	public static void main(String[] args){
		Customer customer = new Customer();
		System.out.println("Customer is hungury.");
		customer.yellingForFood();//Customer is yelling around.
	}
	/**
	 * A very hungry customer willing to get some food.
	 * @author Han Chen
	 *
	 * Nov 12, 2014
	 */
	static class Customer{
		public void yellingForFood(){
			System.out.println("Is anybody here to give me some food?");
			Cook c = new Cook();
			System.out.println("Cook is ready to cook.");
			//Cook starts to cook all dishes he knows one by one.
			c.makeBurger();
			c.makeFish();
			//More food....
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
 