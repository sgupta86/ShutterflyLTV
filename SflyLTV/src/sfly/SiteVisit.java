/* This class represents SiteVisit Entity
*/
package sfly;

import java.util.Date;
import java.util.HashMap;

/**
 * class SiteVisit
 * @author saurav
 *
 */
public class SiteVisit {

	String customer_id;
    HashMap<String, String> tags;
    String key;
    Date eventTime;
    
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public HashMap<String, String> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, String> tags) {
		this.tags = tags;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}   
}
