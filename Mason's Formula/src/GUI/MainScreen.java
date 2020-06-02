package GUI;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainScreen extends BasicGameState {
	int posX, posY;
	int n = 0;
	boolean flag = false, showedOutput = false;
	Linker linker;
	Shape[] shapes;
	ArrayList<Edge> edges;
	
	boolean isShapeSelected = false;
	int shapeSelected;	
	
	String output;
	
	public static final int WIDTH = 1350;
	public static final int HEIGHT = 800;
	public static final int DIAMETER = 50;
	
	private double firstClick = -1;

	public MainScreen(int state) throws SlickException {
		String input = JOptionPane.showInputDialog("Enter the number of nodes in the Signal Flow Graph");
		n = Integer.parseInt(input);
		linker = new Linker(n);
		shapes = new Shape[n];
		edges = new ArrayList<Edge>();
	}

	public void init(GameContainer gc, StateBasedGame sbg )throws SlickException {
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, 1350, 820);
		g.setColor(Color.black);
		
		
		shapes = linker.getShapes();
		for (int i = 0 ; i < shapes.length; i++)
		{
			Shape s = shapes[i];
			g.drawOval(s.getX(), s.getY(), DIAMETER, DIAMETER);
			g.drawString("y"+Integer.toString(i+1), s.getX(), s.getY()+DIAMETER+5);
		}
		
		if (isShapeSelected)
		{
			Shape s = shapes[shapeSelected];
			g.fillOval(s.getX(), s.getY(), DIAMETER, DIAMETER);
		}
		
		for (Edge e : edges)
		{
			Shape from = e.getFrom(), to = e.getTo();
			int startX = from.getX()+DIAMETER/2, startY = from.getY()+DIAMETER/2;
			int endX = to.getX()+DIAMETER/2, endY = to.getY()+DIAMETER/2;
			g.drawGradientLine(startX, startY,Color.black, endX, endY,Color.green);
			g.drawString(e.getFunction(), (startX+endX)/2, (startY+endY)/2);
		}
		
		if (showedOutput)
		{
			g.drawString("The overall Transfer Function is : ",5,700);
			g.drawString(output, 5, 720);
		}

	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(Mouse.isButtonDown(0)){
			posX=Mouse.getX();
			posY=820-Mouse.getY();
			for (int i =0; i< shapes.length; i++)
			{
				Shape s = shapes[i];
				if (s.contains(posX, posY))
				{
					
					if (!isShapeSelected)
					{
						isShapeSelected = true;
						shapeSelected = i;
						firstClick = System.currentTimeMillis();
					}
					else if (firstClick!= -1 && System.currentTimeMillis()-firstClick > 1000)
					{
						String function = JOptionPane.showInputDialog("Enter transfer function from y"+(shapeSelected+1)+" to y"+(i+1));
						edges.add(new Edge(shapes[shapeSelected],shapes[i],function));
						linker.addEdge(shapeSelected, i, function);
						isShapeSelected = false;
						firstClick = -1;
					}
				}
			}
		}
		
		if (Mouse.isButtonDown(1))
			flag = true;
		if (flag && !showedOutput)
			{
				output = linker.showOutput();
				showedOutput = true;
				System.out.println(output);
			}
		
	}

	public void window(String window, int delta, StateBasedGame sbg) {}

	public int getID() {return 0;}

}