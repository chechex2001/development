package FlightPrices.DevTest.Dao;

import java.util.List;

import FlightPrices.DevTest.Model.Airlines;
import FlightPrices.DevTest.Model.Plane;

public interface AirlineDao {
	
	public Airlines getInfantPriceByAirline(Plane airline);
	public List<Airlines> getAllInfantPrices();

}
