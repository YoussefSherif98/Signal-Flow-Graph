package Graph;
import java.util.ArrayList;

public class TransferFunctionCalculator {

	private static SFGTraversal traversal;
	/*
	public static void main(String[] args) {
		
		
		System.out.println("Please enter the number of nodes: ");
		Scanner in = new Scanner(System.in);
		int nodes = in.nextInt();
		
		SignalFlowGraph sfg = new SignalFlowGraph(nodes);
		
		System.out.println("Please enter the number of edges");
		int edges = in.nextInt();
		
		System.out.println("Enter all edges in the following form");
		System.out.println("from to (transfer_function)");
		in.nextLine();
		for (int i=0; i<edges; i++)
		{
			String line = in.nextLine();
			String[] fields = line.split(" ");
			int from = Integer.parseInt(fields[0]);
			int to = Integer.parseInt(fields[1]);
			String transferFunction = fields[2];
			if (transferFunction.charAt(0)!='(')
				transferFunction = "("+transferFunction;
			if (transferFunction.charAt(transferFunction.length()-1) !=')')
				transferFunction = transferFunction + ")";
			
			sfg.addEdge(from, to, transferFunction);
			System.out.println("Added edge "+from+"->"+to+"   "+transferFunction);
		}
		in.close();
		
		/*
		SignalFlowGraph sfg = new SignalFlowGraph(5);
		sfg.addEdge(0, 1, "(f0)");
		sfg.addEdge(1,2,"(f1)");
		sfg.addEdge(2, 3, "(f2)");
		sfg.addEdge(3,4,"(f3)");
		sfg.addEdge(2, 1, "(g1)");
		sfg.addEdge(3, 3, "(g2)");
		sfg.addEdge(3, 2, "(g3)");
		sfg.addEdge(4, 4, "(g4)");
		sfg.addEdge(1, 3, "(f5)");
		
		SFGTraversal traversal = new SFGTraversal(sfg);
		
		String numerator = getNumerator(traversal);
		System.out.println("Numerator is "+numerator);
		String denominator = getDenominator(traversal);
		System.out.println("Denominator is "+denominator);

	}
	*/
	
	public TransferFunctionCalculator(SFGTraversal t)
	{
		traversal = t;
	}
	
	public static String getNumerator()
	{
		StringBuilder numerator = new StringBuilder();
		
		numerator.append("(");
		for (int i = 0; i<traversal.getNoPaths(); i++)
		{
			Path p = traversal.getPath(i);
			numerator.append("(");
			numerator.append(p.getGain());
			numerator.append(")(1-(");
			for (Path deltaK : traversal.getLoopsNonTouchingWith(p))
			{
				numerator.append(deltaK.getGain());
				numerator.append("+");
			}
			if (numerator.charAt(numerator.length()-1) == '(')
				numerator.delete(numerator.length()-4, numerator.length());
			else
				numerator.setCharAt(numerator.length()-1, ')');
			numerator.append(")+");
		}
		numerator.setCharAt(numerator.length()-1, ')');
		
		return numerator.toString();
	}
	
	public static String getDenominator()
	{
		StringBuilder denominator = new StringBuilder();
		denominator.append("(1-(");
		ArrayList<Path> loops = traversal.getLoops();
		for (int i = 0; i < loops.size(); i++)
		{
			denominator.append("(");
			denominator.append(loops.get(i).getGain());
			denominator.append(")+");
		}
		if (denominator.charAt(denominator.length()-1) == '(')
			denominator.delete(denominator.length()-1, denominator.length());
		denominator.setCharAt(denominator.length()-1,')');
		String[] nonTouchingLoops = traversal.getNonTouchingLoopsGains();
		denominator.append("+(");
		for (String s : nonTouchingLoops)
		{
			denominator.append("(");
			denominator.append(s);
			denominator.append(")+");
		}
		if (denominator.charAt(denominator.length()-1) == '(')
			denominator.delete(denominator.length()-1, denominator.length());
		denominator.setCharAt(denominator.length()-1, ')');
		denominator.append(")");
		
		return denominator.toString();
	}

}
