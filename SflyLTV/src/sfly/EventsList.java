/* This class represents the different events
 * CUSTOMER
 * SITE_VISIT
 * ORDER
 * IMAGE
 * and represents in-memory Data Structure
*/
package sfly;

import java.util.Date;
import java.util.HashMap;


/**
 * EventsList
 * @author saurav
 *
 */
public class EventsList {

	HashMap<String,Customer> CustomersList = new HashMap<String,Customer>();
	HashMap<String,SiteVisit> SiteVisitList = new HashMap<String,SiteVisit>();
	HashMap<String,Integer> TotalSiteVisit = new HashMap<String,Integer>();
	HashMap<String,Order> OrdersList = new HashMap<String,Order>();
	HashMap<String,Double> TotalOrdersValue = new HashMap<String,Double>();
	HashMap<String,Date> EventDurationList = new HashMap<String,Date>();
	HashMap<String,ImageUpload> ImageUploadList = new HashMap<String,ImageUpload>();
	Date MaxEventDate;
}
