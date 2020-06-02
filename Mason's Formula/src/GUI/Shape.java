package GUI;

public class Shape {
	
	private int x,y;
	private int radius = MainScreen.DIAMETER;

	public Shape(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean contains(double posX, double posY)
	{
		double r = Math.pow(posX-x, 2) + Math.pow(posY-y, 2);
		return r < Math.pow(radius, 2);
	}
	
}
