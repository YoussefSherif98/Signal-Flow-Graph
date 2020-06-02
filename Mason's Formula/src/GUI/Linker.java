package GUI;

import Graph.*;

public class Linker {

	private SignalFlowGraph sfg;
	
	public Linker(int n)
	{
		sfg = new SignalFlowGraph(n);
	}
	
	public void addEdge (int from, int to, String function)
	{
		sfg.addEdge(from, to, function);
	}
	
	public Shape[] getShapes()
	{
		int n = sfg.size();
		
		int UP = MainScreen.HEIGHT/3;
		int DOWN = 2*MainScreen.HEIGHT/3;
		
		int disp = (MainScreen.WIDTH-100)/(n/2);
		int downX = 50;
		int upX = downX+(disp/2);
		
		Shape[] shapes = new Shape[n];
		for (int i=0 ; i < n ; i++)
		{
			if (i%2 != 0)
			{
				shapes[i] = new Shape(upX,UP);
				upX += disp;
			}
			else
			{
				shapes[i] = new Shape(downX,DOWN);
				downX += disp;
			}
		}
		
		return shapes;
	}

	public String showOutput() {
		
		TransferFunctionCalculator calc = new TransferFunctionCalculator(new SFGTraversal(sfg));
		String numerator = calc.getNumerator();
		String denominator = calc.getDenominator();
		return (numerator+" / "+denominator);
	}
}
