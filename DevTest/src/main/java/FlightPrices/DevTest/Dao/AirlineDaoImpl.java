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

import FlightPrices.DevTest.Model.Airlines;
import FlightPrices.DevTest.Model.AirlinesSubCode;
import FlightPrices.DevTest.Model.Plane;

public class AirlineDaoImpl implements AirlineDao{
	

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public AirlineDaoImpl(EmbeddedDatabase db) {
		super();		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(db);
	}
	
	
	@Override
	public Airlines getInfantPriceByAirline(Plane airline) {
		String sql = "SELECT * FROM AIRLINES_INFANT_PRICES WHERE iataCode=:iataCode";
		Map<String, Object> params = new HashMap<String, Object>();		
		params.put("iataCode", String.valueOf(airline.getCode()));		
		MapSqlParameterSource paramsSql = new MapSqlParameterSource(params);
		Airlines airlinInfantPrice=namedParameterJdbcTemplate.queryForObject(sql, paramsSql, new AirlinetMapper());
		return airlinInfantPrice;
	}

	@Override
	public List<Airlines> getAllInfantPrices() {
		String sql = "SELECT * FROM AIRLINES_INFANT_PRICES ";
		List<Airlines> airlines = namedParameterJdbcTemplate.query(sql, new AirlinetMapper());
		return airlines;
		
	}
	
	
	
	
	private static final class AirlinetMapper implements RowMapper<Airlines> {

		public Airlines mapRow(ResultSet rs, int rowNum) throws SQLException {
			Airlines airline = new Airlines();			
			String iataCode = rs.getString("iataCode");
			airline.setAirline(AirlinesSubCode.valueOf(iataCode));
			airline.setInfantPrice(rs.getLong("price"));
			return airline;
		}
	}
}
