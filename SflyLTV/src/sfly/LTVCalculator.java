/* This class represents the LTV calculator to calculate LTV
*/
package sfly;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class LTVCalculator
 * @author saurav
 *
 */
public class LTVCalculator {
	
	String OutputFile = "./output/out.txt";
	Map<String,Double> userLTV = new TreeMap<String, Double>();
	
	
	/**
	 * @param top
	 * @param D
	 * Calculates the Simple LTV and provides the top x customers list having highest LTV
	 */
	public void TopXSimpleLTVCustomers(int top,EventsList D){	
		
		for(String custId:D.CustomersList.keySet()){
			double totalExp = D.TotalOrdersValue.get(custId);
			int totalVisits = D.TotalSiteVisit.get(custId);
			double expPerVisit =0 ;
			if(totalVisits!=0)
				expPerVisit = (double)totalExp/totalVisits;
			
			Date MaxTime = D.MaxEventDate;
			Date MinTime = D.EventDurationList.get(custId);
			System.out.println(MaxTime);
			System.out.println(MinTime);
			double duration = (MaxTime.getTime()-MinTime.getTime())/(1000*60*60*24);
				
			double num1 = duration%7;
			double num2 = (int)(duration/7);
			if(num1 >0){
				num2 = num2 + 1;
			}else if((int)num1 ==0){
				
				if(!(MinTime.getDay() == 0 && MaxTime.getDay() == 6))
					num2 = num2 + 1;
			}
		
			duration = num2;
			
			double valPerWeek = (expPerVisit)*(totalVisits/((duration==0)? 1 : duration));
			double user_ltv = 52*valPerWeek*10;
			userLTV.put(custId, user_ltv);
			System.out.println("CustId ::" + custId + "  LTV ::" + user_ltv+ "  totalExp ::"+ totalExp+ "  totalVisits::"+totalVisits + "  expPerVisit::" +expPerVisit + "  duration::"+duration); 			
		}
		
		try{
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(OutputFile), "utf-8"));
	              
		Iterator<Map.Entry<String, Double>> itr = entriesSortedByValues(userLTV).iterator();
		for(int i =0;i<top && itr.hasNext();i++){
			Map.Entry<String, Double> e = itr.next();
			 {
				writer.write("Customer::"+e.getKey()+"	"+e.getValue()+"\n");
				writer.newLine(); 
			 }		
		}
		writer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Comparator to Sort the Tree Mop by Values to get Top X LTV customers
	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e2.getValue().compareTo(e1.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}

}
