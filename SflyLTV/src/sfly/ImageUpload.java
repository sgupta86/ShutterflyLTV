/* This class represents Image Entity
*/
package sfly;

import java.util.Date;

/**
 * class ImageUpload
 * @author saurav
 *
 */
public class ImageUpload {

	String customer_id;
    String cameraMake;
    String cameraModel;
    String key;
    Date event_time;
    
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCameraMake() {
		return cameraMake;
	}
	public void setCameraMake(String cameraMake) {
		this.cameraMake = cameraMake;
	}
	public String getCameraModel() {
		return cameraModel;
	}
	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
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
	public void setEvent_time(Date date) {
		this.event_time = date;
	}
    
    
}
