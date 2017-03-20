package FlightPrices.DevTest.Model;

public enum CitySubCode {
	MAD("Madrid"),
	BCN("Barcelona"),
	LHR("London"),
	CDG("Paris"),
	FRA("Frakfurt"),
	IST("Istanbul"),	
	AMS("Amsterdam"),
	FCO("Rome"),
	CPH("Copenhagen");
	
	private String content;
	private CitySubCode(String content) {
		this.content=content;
	}
	public String getContent() {
		return content;
	}
	public static CitySubCode fromString(String iataCode) {
		if (iataCode != null) {
			for (CitySubCode aux : CitySubCode.values()) {
				if (iataCode.equalsIgnoreCase(aux.content)) {
					return aux;
				}
			}
		}
		return null;
	}
}
