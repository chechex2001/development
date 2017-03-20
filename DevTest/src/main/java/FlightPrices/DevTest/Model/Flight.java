package FlightPrices.DevTest.Model;

public class Flight {

	private CitySubCode origin;
	private CitySubCode destination;
	private Plane airline;
	private float price;


	public CitySubCode getOrigin() {
		return origin;
	}

	public void setOrigin(CitySubCode origin) {
		this.origin = origin;
	}

	public CitySubCode getDestination() {
		return destination;
	}

	public void setDestination(CitySubCode destination) {
		this.destination = destination;
	}

	public Plane getAirline() {
		return airline;
	}

	public void setAirline(Plane airline) {
		this.airline = airline;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Flight [origin=" + origin.name() + "-"+origin.getContent()+", destination=" + destination.name() +  "-"+destination.getContent()+", airline=" + airline.toString() + ", Price=" + price
				+ "]";
	}

}
