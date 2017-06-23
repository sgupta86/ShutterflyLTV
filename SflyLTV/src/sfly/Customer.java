/* This class represents Customer Entity
*/
package sfly;

import java.util.Date;

/**
 * class Customer
 * @author saurav
 *
 */
public class Customer {

	String last_name;
	String adr_city;
	String adr_state;
	String key;
	Date event_time;
	
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAdr_city() {
		return adr_city;
	}
	public void setAdr_city(String adr_city) {
		this.adr_city = adr_city;
	}
	public String getAdr_state() {
		return adr_state;
	}
	public void setAdr_state(String adr_state) {
		this.adr_state = adr_state;
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
