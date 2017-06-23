#Shutterfly Customer LTV using In-Memory Data Structure

The approach used in the program is that each event is processed in the order in the same order as input file and are stored in their respective Entity Objects in the Hash Map(in-memory data Structure). This Data Structure is continously updated based on the events and stores data at Customer level (CustomerId). The time frame is calculated based on when the customer first visits the site and the total time length of all the events.


#Classes Overview
-- LTVMain: Main Class that triggers event ingestion and LTV Calculation

-- LTVIngestEvents: Class for ingesting the events into in-memory Data Structure

-- LTVCalculator: Class for calculating the LTV

-- EventsList: Class for in-memory Data Structure

-- Customer: Class for Customer entity

-- SiteVisit:- class for site visit entity

-- Order:- class for order entity

-- Image:- class for image entity



#Important methods

LTVIngestEvents(e,D) : For ingesting events

TopXSimpleLTVCustomers(x,D) : For calculating LTV



#Assumptions:


-- Input and Output paths are pre-defined but can be made as user input for production versions



#Complexity and perforamance:

HasMap is used as in-memory Data Structure as its Complexity is O(1)

This program is meant for small data sets. For Large Data Sets, Big Data Technology can be used for processing the streams and events parallely and in memory using Spark which is much powerful for processing large datasets
