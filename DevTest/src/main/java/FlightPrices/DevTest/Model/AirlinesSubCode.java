package FlightPrices.DevTest.Model;

public enum AirlinesSubCode {
	IB("Iberia"),
	BA("British Airways"),
	LH("Lufthansa"),
	FR("Ryanair"),
	VY("Vueling"),
	TK("Turkish Airlines"),
	U2("Easyjet");
	
	private String content;
	private AirlinesSubCode(String content) {
		this.content=content;
	}
	public String getContent() {
		return content;
	}
	public static AirlinesSubCode fromString(String iataCode) {
		if (iataCode != null) {
			for (AirlinesSubCode aux : AirlinesSubCode.values()) {
				if (iataCode.equalsIgnoreCase(aux.content)) {
					return aux;
				}
			}
		}
		return null;
	}
	
}

