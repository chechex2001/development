package FlightPrices.DevTest.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import FlightPrices.DevTest.Model.AirlinesSubCode;
import FlightPrices.DevTest.Model.CitySubCode;
import FlightPrices.DevTest.Model.Flight;
import FlightPrices.DevTest.Model.Plane;

public class FlightDaoImpl implements FlightDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public FlightDaoImpl(EmbeddedDatabase db) {
		super();	
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(db);
	}

	@Override
	public List<Flight> getFlightByOriginAndDestination(CitySubCode origin, CitySubCode destination) {
		String sql = "SELECT * FROM FLIGHT WHERE origin=:origin and destination=:destination";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("origin", String.valueOf(origin));
		params.put("destination", String.valueOf(destination));
		MapSqlParameterSource paramsSql = new MapSqlParameterSource(params);
		List<Flight> flights = namedParameterJdbcTemplate.query(sql, paramsSql, new FlightMapper());
		return flights;
	}

	@Override
	public void saveFlight(Flight flight) {
		String sql = "INSERT INTO FLIGHT VALUES (NULL,:origin,:destination,:airline, :price)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("origin", String.valueOf(flight.getOrigin()));
		params.put("destination", String.valueOf(flight.getDestination()));
		params.put("airline", String.valueOf(flight.getAirline().toString()));
		params.put("price", flight.getPrice());
		MapSqlParameterSource paramsSql = new MapSqlParameterSource(params);
		namedParameterJdbcTemplate.update(sql, paramsSql);
	}

	@Override
	public Flight getFlightByAirline(Plane airline) {
		String sql = "SELECT * FROM FLIGHT WHERE airline=':airline'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airline", airline.toString());
		MapSqlParameterSource paramsSql = new MapSqlParameterSource(params);
		Flight result = namedParameterJdbcTemplate.queryForObject(sql, paramsSql, new FlightMapper());
		return result;
	}

	@Override
	public List<Flight> getAllFlight() {
		String sql = "SELECT * FROM FLIGHT ";
		List<Flight> flights = namedParameterJdbcTemplate.query(sql, new FlightMapper());
		return flights;
	}

	private static final class FlightMapper implements RowMapper<Flight> {

		public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {

			Flight flight = new Flight();
			Plane plane = new Plane();
			String airline = rs.getString("airline");
			plane.setCode(AirlinesSubCode.valueOf(airline.substring(0, 2)));
			plane.setNumber(Integer.valueOf(airline.substring(2)));
			flight.setAirline(plane);
			flight.setDestination(CitySubCode.valueOf(rs.getString("destination")));
			flight.setOrigin(CitySubCode.valueOf(rs.getString("origin")));
			flight.setPrice(rs.getLong("price"));
			return flight;
		}
	}

}
