/* This class represents Order Entity
*/
package sfly;

import java.util.Date;

/**
 * class Order 
 * @author saurav
 *
 */
public class Order {

	String customerid;
    double amount;
    String key;
    Date event_time;
    
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getEvent_time() {
		return event_time;
	}
	public void setEvent_time(Date event_time) {
		this.event_time = event_time;
	}
    
    
}
