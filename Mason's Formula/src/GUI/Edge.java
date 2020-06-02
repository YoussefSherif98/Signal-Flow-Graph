package GUI;

public class Edge {
	
	private Shape from,to;
	private String function;
	
	public Edge(Shape from, Shape to, String function) {
		super();
		this.from = from;
		this.to = to;
		this.function = function;
	}

	public Shape getFrom() {
		return from;
	}

	public Shape getTo() {
		return to;
	}

	public String getFunction() {
		return function;
	}

}
