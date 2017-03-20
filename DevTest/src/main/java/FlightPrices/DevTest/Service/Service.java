package FlightPrices.DevTest.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import FlightPrices.DevTest.Dao.AirlineDaoImpl;
import FlightPrices.DevTest.Dao.FlightDaoImpl;
import FlightPrices.DevTest.Model.Airlines;
import FlightPrices.DevTest.Model.AirlinesSubCode;
import FlightPrices.DevTest.Model.CitySubCode;
import FlightPrices.DevTest.Model.Flight;
import FlightPrices.DevTest.Model.Passenger;
import FlightPrices.DevTest.Model.PassengerType;
import FlightPrices.DevTest.Model.Plane;

public class Service {

	private FlightDaoImpl daoRFlights;
	private AirlineDaoImpl daoAirlinesInfantsPrices;
	

	
	

	public Service(EmbeddedDatabase db, boolean loadData) {
		super();		
		daoRFlights = new FlightDaoImpl(db);
		daoAirlinesInfantsPrices = new AirlineDaoImpl(db);
		try {
			if(loadData)loadCsv();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//printAllFlights();
		//printAllAirlines();
	}

	private void printAllFlights() {
		List<Flight> fls = daoRFlights.getAllFlight();
		for (Flight flight : fls) {
			System.out.println(flight.toString());
		}

	}

	public void printAllFlights(List<Flight> fls) {

		for (Flight flight : fls) {
			System.out.println(flight.toString());
		}

	}
	
	private void printAllAirlines(){
		List<Airlines> arl = daoAirlinesInfantsPrices.getAllInfantPrices();
		for (Airlines airlines : arl) {
			System.out.println(airlines.getAirline()+", "+airlines.getAirline().getContent()+", "+airlines.getInfantPrice());
		}
	}

	public void loadCsv() throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new FileReader("src/main/resources/input.txt"));
		String line;
		while ((line = in.readLine()) != null) {
			String[] val = line.split(",");
			Flight flight = new Flight();
			Plane plane = new Plane();
			String airline = val[2];
			plane.setCode(AirlinesSubCode.valueOf(airline.substring(0, 2)));
			plane.setNumber(Integer.valueOf(airline.substring(2)));
			flight.setAirline(plane);
			flight.setDestination(CitySubCode.valueOf(val[1]));
			flight.setOrigin(CitySubCode.valueOf(val[0]));
			flight.setPrice(Long.valueOf(val[3]));
			daoRFlights.saveFlight(flight);
		}
		in.close();

	}

	public List<Flight> getFlights(CitySubCode origin, CitySubCode destination){
		return  daoRFlights.getFlightByOriginAndDestination(origin, destination);
	}
	
	public StringBuffer getServiceCost(CitySubCode origin, CitySubCode destination, List<Passenger> passengers,
			int daysToDeparture) {
		StringBuffer output= new StringBuffer();
		List<Flight> fl = getFlights(origin, destination);
		
		if(fl.isEmpty()){
			output.append("no flights available");			
			return output;}
		printAllFlights(fl);
		
		
		
		
		for (Flight flight : fl) {
			output.append(flight.getAirline().toString() + ", "
					+ getCostForFlight(passengers, flight, daysToDeparture) + "€  \n");
		}
		return output;
	}

	/**
	 * return for each passenger each cost for the trip inherit in the datamodel
	 * via reference
	 * 
	 * @param passengers
	 *            group of people in the trip
	 * @param fl
	 *            flight to ship
	 * @param daysToDeparture
	 *            ...
	 * @return the amount for all passengers for the trip
	 */
	public float getCostForFlight(List<Passenger> passengers, Flight fl, int daysToDeparture) {
		float cost = 0, retCost = 0;

		for (Passenger passenger : passengers) {
			if (passenger.getPassengerType().equals(PassengerType.ADULT)) {
				cost = getValueFromPercentes(fl.getPrice(), getPercentsByDay(daysToDeparture));
			}

			else if (passenger.getPassengerType().equals(PassengerType.CHILD)) {
				cost = getValueFromPercentes(fl.getPrice(), getPercentsByDay(daysToDeparture));
				cost = getValueFromPercentes(cost, 67);
			}
			else if (passenger.getPassengerType().equals(PassengerType.INFANT)) {
				cost = daoAirlinesInfantsPrices.getInfantPriceByAirline(fl.getAirline()).getInfantPrice();
			}
			retCost += cost;
			passenger.setCost(cost);

		}
		return round(retCost,2);
	}
	
	private static float round(float d, int decimalPlace) {
	    return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_EVEN).floatValue();
	}
	private float getValueFromPercentes(float base, int percent) {
		return (base * percent) / 100;
	}

	private int getPercentsByDay(int days) {

		if (days <= 2)
			return 150;
		if (days >= 3 && days <= 15)
			return 120;
		if (days >= 16 && days <= 30)
			return 100;
		else
			return 80;
	}

}
