/* This class represents the events ingestion class to
 * ingest and update events data into in-memory data structure
*/
package sfly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class LTVIngestEvents
 * @author saurav
 *
 */
public class LTVIngestEvents {
			
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	String eventTime,custId;

	/**
	 * @param evnt 
	 * @param D 
	 * @throws ParseException
	 * Ingests the event evnt into in-memory Data Structure D
	 */
	public void ingest(String evnt,EventsList D) throws ParseException{	
		
		JsonObject event = (JsonObject)new JsonParser().parse(evnt);		
		String eventType = event.get("type").getAsString();
		
		updateCustDuration(event,D);
		if(eventType.equals("CUSTOMER")){
			updateCustomer(event,D);
		}
		else if(event.get("type").getAsString().equals("ORDER")){
			updateOrder(event,D);
		}
		else if(event.get("type").getAsString().equals("SITE_VISIT")){
			updateSiteVisit(event,D);
		}
		else{
			updateImage(event,D);
		}
	}

	/**
	 * @param event
	 * @param D
	 * @throws ParseException
	 * Updates the SiteVisit for Customer into in-memory Data Structure D
	 */
	private void updateSiteVisit(JsonObject event, EventsList D) throws ParseException {
			
		custId = event.get("customer_id").getAsString();
		
		if(!D.TotalSiteVisit.containsKey(custId))
			D.TotalSiteVisit.put(custId, 1);
		else {
			int curTotalSiteVisit = D.TotalSiteVisit.get(custId);
			D.TotalSiteVisit.put(custId,curTotalSiteVisit);
		}
		
		String siteKey = event.get("type").getAsString();
		if(!D.SiteVisitList.containsKey(custId+":"+siteKey)){
			
			SiteVisit sitevisit = new SiteVisit();
			sitevisit.setKey(siteKey+custId);
			sitevisit.setCustomer_id(custId);
			sitevisit.setEventTime(sdf.parse(event.get("event_time").getAsString()));

			HashMap<String,String> tagList = new HashMap<String,String>();
			JsonArray jsonArray = event.get("tags").getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				for (Map.Entry<String, JsonElement> entry: jsonArray.get(i).getAsJsonObject().entrySet()) {
					tagList.put(entry.getKey(), entry.getValue().toString());
				}
			}
			sitevisit.setTags(tagList);
		}
	}
	

	/**
	 * @param event
	 * @param D
	 * @throws ParseException
	 * Updates the customer time frame for LTV calculation
	 */
	private void updateCustDuration(JsonObject event,EventsList D) throws ParseException {
		
		eventTime = event.get("event_time").getAsString();
		
		if(event.get("type").getAsString().equals("CUSTOMER"))
			custId = event.get("key").getAsString();
		else
			custId = event.get("customer_id").getAsString();
		
		Date curEventDate = sdf.parse(eventTime);				
		if(D.EventDurationList.containsKey(custId)){			
			Date startEventDate = D.EventDurationList.get(custId);					
			if(curEventDate.before(startEventDate)){
				D.EventDurationList.put(custId,curEventDate);
			}					
		}
		else
			D.EventDurationList.put(custId, curEventDate);
			
		if (D.MaxEventDate == null)
			D.MaxEventDate = curEventDate;
		else if(curEventDate.after(D.MaxEventDate))
			D.MaxEventDate = curEventDate;	
	}
	
	
	/**
	 * @param event
	 * @param D
	 * @throws ParseException
	 * Updates the Customer attributes into in-memory Data Structure D
	 */
	private void updateCustomer(JsonObject event,EventsList D) throws ParseException {
		
		custId = event.get("key").getAsString();
		
		if(!D.CustomersList.containsKey(custId)){
			Customer customer = new Customer();
			customer.setKey(event.get("key").getAsString());
            customer.setEvent_time(sdf.parse(event.get("event_time").getAsString()));
            customer.setAdr_city(event.get("adr_city").getAsString());
            customer.setLast_name(event.get("last_name").getAsString());
            customer.setAdr_state(event.get("adr_state").getAsString());
            D.CustomersList.put(custId, customer);
		}
		else if(event.get("verb").getAsString().equals("UPDATE")){						
			Date prevEventTime = D.CustomersList.get(custId).getEvent_time();
			Date curEventTime = sdf.parse(event.get("event_time").getAsString());
			if(curEventTime.after(prevEventTime)){
				Customer customer = new Customer();
				customer.setKey(event.get("key").getAsString());
	            customer.setEvent_time(sdf.parse(event.get("event_time").getAsString()));
	            customer.setAdr_city(event.get("adr_city").getAsString());
	            customer.setLast_name(event.get("last_name").getAsString());
	            customer.setAdr_state(event.get("adr_state").getAsString());
	            D.CustomersList.put(custId, customer);
			}		
		}        		
	}
	
	
	/**
	 * @param event
	 * @param D
	 * @throws ParseException
	 * Updates the order details for the Customer into in-memory Data Structure D
	 */
	private void updateOrder(JsonObject event, EventsList D) throws ParseException {
		
		custId = event.get("customer_id").getAsString();
		String orderId = event.get("key").getAsString();
		if(!D.OrdersList.containsKey(custId+":"+orderId)){
		Order order = new Order();
		order.setKey(orderId);
		order.setCustomerid(custId);
		order.setAmount(Double.parseDouble(event.get("total_amount").getAsString().split(" ")[0]));
		order.setEvent_time(sdf.parse(event.get("event_time").getAsString()));
		D.OrdersList.put(custId+orderId,order);	
		double curTotalOrdersValue;
		if(!D.TotalOrdersValue.containsKey(custId))
			curTotalOrdersValue = 0.0;
		else
			curTotalOrdersValue = D.TotalOrdersValue.get(custId);
		D.TotalOrdersValue.put(custId, curTotalOrdersValue + Double.parseDouble(event.get("total_amount").getAsString().split(" ")[0]));
		}
		
		else if(event.get("verb").getAsString().equals("UPDATE")){						
			Date prevEventTime = D.OrdersList.get(custId+orderId).getEvent_time();
			Date curEventTime = sdf.parse(event.get("event_time").getAsString());
			if(curEventTime.after(prevEventTime)){
				Order order = D.OrdersList.get(custId+orderId);
				double PrevOrderValue = order.getAmount();
				double curTotalOrderValue = D.TotalOrdersValue.get(custId);
				order.setAmount(Double.parseDouble(event.get("total_amount").getAsString().split(" ")[0]));
				order.setEvent_time(sdf.parse(event.get("event_time").getAsString()));
				D.OrdersList.put(custId+orderId,order);
				D.TotalOrdersValue.put(custId,curTotalOrderValue + Double.parseDouble(event.get("total_amount").getAsString().split(" ")[0]) - PrevOrderValue);
			}		
		}      
	}
	

	/**
	 * @param event
	 * @param D
	 * @throws ParseException
	 * Updates the images data into in-memory Data Structure D
	 */
	private void updateImage( JsonObject event, EventsList D) throws ParseException {
		
		custId = event.get("customer_id").getAsString();
		ImageUpload imgUpload = new ImageUpload();
		imgUpload.setKey(event.get("key").getAsString());
		imgUpload.setCustomer_id(custId);
		imgUpload.setCameraMake(event.get("camera_make").getAsString());
		imgUpload.setCameraModel(event.get("camera_model").getAsString());
		imgUpload.setEvent_time(sdf.parse(event.get("event_time").getAsString()));
		D.ImageUploadList.put(event.get("key").getAsString(), imgUpload);
	}
}
