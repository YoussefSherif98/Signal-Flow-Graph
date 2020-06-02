package Graph;

public class EdgeEquation {

	private int from, to;
	private String equation;
	private boolean traversed;
	
	public EdgeEquation(int from, int to, String equation)
	{
		this.from = from;
		this.to = to;
		this.equation = equation;
	}
	
	public int from()
	{
		return this.from;
	}
	
	public int to()
	{
		return this.to;
	}
	
	public String equation()
	{
		return this.equation;
	}

	public boolean isTraversed() {
		return traversed;
	}

	public void setTraversed(boolean traversed) {
		this.traversed = traversed;
	}
}
