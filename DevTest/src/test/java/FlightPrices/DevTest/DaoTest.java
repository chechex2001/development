package FlightPrices.DevTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import FlightPrices.DevTest.Model.CitySubCode;
import FlightPrices.DevTest.Model.Flight;
import FlightPrices.DevTest.Model.Passenger;
import FlightPrices.DevTest.Model.PassengerType;
import FlightPrices.DevTest.Service.Service;

public class DaoTest {
	private EmbeddedDatabase db;
	private Service service ;
	private static boolean setUpIsDone = false;
    
    @Before
    public void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	if(!setUpIsDone)
    	{		
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql")
    		.build();
    	service = new Service(db,true);
    	setUpIsDone = true;
    	}
    	else{
    		db = new EmbeddedDatabaseBuilder()
    	    		.setType(EmbeddedDatabaseType.H2)    	    		
    	    		.build();
    	    	service = new Service(db,false);    		
    	}
    	
    }
    
  
    
    @Test
    public void LoadFlightFromOriginToDestinyExampleCase1() throws IOException{   	
    	System.out.println("1 adult, 31 days to the departure date, flying AMS -> FRA");
    	int daysToDeparture=31;
    	System.out.println(service.getServiceCost(CitySubCode.AMS,CitySubCode.FRA, createPassengersByCode(1,0,0), daysToDeparture).toString());
    	List<Flight> flights=service.getFlights(CitySubCode.AMS, CitySubCode.FRA);
    	
    	assertEquals("157.6", String.valueOf(service.getCostForFlight(createPassengersByCode(1,0,0), flights.get(0), daysToDeparture)));
    	assertEquals("198.4", String.valueOf(service.getCostForFlight(createPassengersByCode(1,0,0), flights.get(1), daysToDeparture)));
    	assertEquals("90.4", String.valueOf(service.getCostForFlight(createPassengersByCode(1,0,0), flights.get(2), daysToDeparture)));
    }
    
    @Test
    public void LoadFlightFromOriginToDestinyExampleCase2() throws IOException{   	
    	System.out.println(" 2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST");
    	int daysToDeparture=15;    	
    	System.out.println(service.getServiceCost(CitySubCode.LHR,CitySubCode.IST, createPassengersByCode(2,1,1), daysToDeparture).toString());
    	
    	List<Flight> flights=service.getFlights(CitySubCode.LHR, CitySubCode.IST);
    	assertEquals("806.0", String.valueOf(service.getCostForFlight(createPassengersByCode(2,1,1), flights.get(0), daysToDeparture)));
    	assertEquals("481.19", String.valueOf(service.getCostForFlight(createPassengersByCode(2,1,1), flights.get(1), daysToDeparture)));
    }
    
    @Test
    public void LoadFlightFromOriginToDestinyExampleCase3() throws IOException{   	
    	System.out.println("CDG -> FRA");
    	int daysToDeparture=10;
    	System.out.println(service.getServiceCost(CitySubCode.CDG,CitySubCode.FRA, createPassengersByCode(1,0,0), daysToDeparture).toString());
    	assertEquals("no flights available",service.getServiceCost(CitySubCode.CDG,CitySubCode.FRA, createPassengersByCode(1,0,0), daysToDeparture).toString());
    }
    
    @Test
    public void LoadFlightFromOriginToDestinyExampleCaseRandom() throws IOException{   	
    	
    	for(int i=0;i<=1;i++){
    		System.out.println("RANDOM "+i);
    		int daysToDeparture=new Random().nextInt(31);
    		int origin=new Random().nextInt(CitySubCode.values().length);
    		int destination=new Random().nextInt(CitySubCode.values().length);
    		int adults=new Random().nextInt(5);
    		int childs=new Random().nextInt(5);
    		int infants=new Random().nextInt(5);
    		System.out.println(service.getServiceCost(CitySubCode.values()[origin],CitySubCode.values()[destination], createPassengersByCode(adults,childs,infants), daysToDeparture).toString());
    	}
    	
    	
    		
    }
    
    
    private List<Passenger> createPassengersByCode(int adults, int childs, int infants){
    	List<Passenger> passengers = new ArrayList<Passenger>();
    	int size=adults+childs+infants;
    	for (int i=0;i<size;i++){
    		if(adults>0){
    			Passenger ps=new Passenger(PassengerType.ADULT);
    			passengers.add(ps);
    			adults--;
    		}
    		else
    		if(childs>0){
    			Passenger ps=new Passenger(PassengerType.CHILD);
    			passengers.add(ps);
    			childs--;
    		}
    		else
        		if(infants>0){
        			Passenger ps=new Passenger(PassengerType.INFANT);
        			passengers.add(ps);
        			infants--;
        		}
    			
    	}
    	return passengers; 
    }
    
}
