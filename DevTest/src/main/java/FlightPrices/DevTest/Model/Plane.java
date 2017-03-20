package FlightPrices.DevTest.Model;

public class Plane {

	private AirlinesSubCode code;
	private int number;
	
	
	public AirlinesSubCode getCode() {
		return code;
	}
	public void setCode(AirlinesSubCode code) {
		this.code = code;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return code+ String.valueOf(number);
	}
	
	
	
}
