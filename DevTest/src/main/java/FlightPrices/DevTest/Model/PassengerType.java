package FlightPrices.DevTest.Model;

public enum PassengerType {
	ADULT("adult"),
	CHILD("child"),
	INFANT("infant");
	
	private String content;
	private PassengerType(String content) {
		this.content=content;
	}
	public String getContent() {
		return content;
	}
	public static PassengerType fromString(String iataCode) {
		if (iataCode != null) {
			for (PassengerType aux : PassengerType.values()) {
				if (iataCode.equalsIgnoreCase(aux.content)) {
					return aux;
				}
			}
		}
		return null;
	}
	
	
	
	
}
