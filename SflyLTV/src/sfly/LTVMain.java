/* This class represents the main class to trigger the Events ingestion
 * and LTV calculation
*/
package sfly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Class: LTVMain
 * @author saurav
 *
 */
public class LTVMain {
	
	public static void main(String[] args) throws ParseException{
		
		//Reading the events file
		String inputFile = "./input/file3.txt";
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    
		    //Parsing the Json Array and ingesting the events
		    String events = sb.toString();
		    EventsList D = new EventsList();
		    JsonArray jsonArray = (JsonArray)(new JsonParser().parse(events));	    
		    for(JsonElement e: jsonArray){
		    	System.out.println(e.toString());
		    	LTVIngestEvents ltvingestEvent = new LTVIngestEvents();
		    	ltvingestEvent.ingest(e.toString(),D);
		    }
		    
		    //Calling the function to calculate LTV
		    LTVCalculator ltvCalc = new LTVCalculator();
		    ltvCalc.TopXSimpleLTVCustomers(3,D); 
		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found:"+e);
		}
		catch (IOException e) {
			System.out.println("IO Exception:"+e);
		}	
	}
}
