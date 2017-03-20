package FlightPrices.DevTest.Model;

public class Passenger {
	private PassengerType passengerType;
	private float cost;
	
	
	
	public Passenger(PassengerType passengerType) {
		super();
		this.passengerType = passengerType;
		this.cost = 0;
	}
	public PassengerType getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(PassengerType passengerType) {
		this.passengerType = passengerType;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	
}
