package FlightPrices.DevTest.Dao;

import java.util.List;

import FlightPrices.DevTest.Model.CitySubCode;
import FlightPrices.DevTest.Model.Flight;
import FlightPrices.DevTest.Model.Plane;

public interface FlightDao {
	public List<Flight> getFlightByOriginAndDestination(CitySubCode origin, CitySubCode destination);
	public Flight getFlightByAirline(Plane airline);
	public List<Flight> getAllFlight();
	public void saveFlight(Flight flight);	
	
}
