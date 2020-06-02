package Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class SFGTraversal {
	
	private SignalFlowGraph sfg;
	private ArrayList<Path> loops;
	private ArrayList<Path> paths;
	
	public SFGTraversal(SignalFlowGraph g)
	{
		this.sfg = g;
		findLoops();
		sfg.nonTraverseEdges();
		findPaths();
	}
	
	public ArrayList<Path> getLoops()
	{
		return loops;
	}
	
	public ArrayList<Path> getPaths()

	{
		return paths;
	}
	
	public int getNoPaths()
	{
		return paths.size();
	}
	
	public int getNoLoops()
	{
		return loops.size();
	}
	
	public Path getPath(int k)
	{
		if (k < 0 || k >= paths.size())
			throw new IllegalArgumentException("Incorred Path Index");
		
		return paths.get(k);
	}
	
	public String[] getNonTouchingLoopsGains()
	{
		Set<Set<Path>> nonTouchingPairs = this.getNonTouchingLoops();
		String[] gains = new String[nonTouchingPairs.size()];
		int i = 0;
		for (Set<Path> pair : nonTouchingPairs)
		{
			StringBuilder gain = new StringBuilder();
			for (Path l : pair)
				gain.append(l.getGain());
			gains[i++] = gain.toString();
		}
		return gains;
	}
	
	public Set<Path> getLoopsNonTouchingWith(Path loop)
	{
		Set<Path> result = new HashSet<Path>();
		for (Path l : loops)
		{
			List<Integer> nodesInLoop = l.getLoop();
			boolean flag = true;
			for (int i : nodesInLoop)
			{
				if (loop.contains(i))
				{
					flag = false;
					break;
				}
			}
			if (flag)
				result.add(l);
		}
		
		return result;
	}
	
	private void findPaths()
	{
		paths = new ArrayList<Path>();
		Stack<Integer> path = new Stack<Integer>();
		boolean[] marked = new boolean[sfg.size()];
		marked[0] = true;
		path.add(0);
		pathVisit(0,path,marked);
		
	}
	
	private void pathVisit(int source, Stack<Integer> path, boolean[] marked) {
		
		if (source == sfg.size()-1)
		{
			//Reached the sink
			constructPath(path);
			return;
		}
		
		Set<EdgeEquation> adj = sfg.getAdj(source);
		for (EdgeEquation e : adj)
		{
			if (!marked[e.to()])
			{
				path.push(e.to());
				marked[e.to()] = true;
				pathVisit(e.to(),path,marked);
				path.pop();
				marked[e.to()] = false;
			}
		}
	}
	
	private void constructPath(Stack<Integer> pathFound)
	{
		List<Integer> pathToAdd = new LinkedList<Integer>();
		Stack<Integer> path = (Stack<Integer>) pathFound.clone();
		int lastNode = path.pop();
		pathToAdd.add(lastNode);
		StringBuilder func = new StringBuilder();
		while(!path.isEmpty())
		{
			int node = path.pop();
			pathToAdd.add(0, node);
			func.append(sfg.getEdge(node, lastNode).equation());
			lastNode = node;
		}
		
		paths.add(new Path(pathToAdd,func.toString()));
		
	}

	private Set<Set<Path>> getNonTouchingLoops()
	{
		Set<Set<Path>> result = new HashSet<Set<Path>>();
		Object[] arr = loops.toArray();
		for (int i = 0; i < loops.size() - 1; i++)
		{
			for (int j = i + 1; j < loops.size(); j++)
			{
				if (!((Path)arr[i]).isTouching((Path)arr[j]))
				{
					Set<Path> pair = new HashSet<Path>();
					pair.add((Path)arr[i]);
					pair.add((Path)arr[j]);
					result.add(pair);
				}
			}
		}
		
		return result;
	}
	
	private void findLoops()
	{
		loops = new ArrayList<Path>();
		for (int i = 0 ; i < sfg.size(); i++)
		{
			boolean[] marked = new boolean[sfg.size()];
			marked[i] = true;
			Stack<Integer> loop = new Stack<Integer>();
			loop.add(i);
			for (EdgeEquation e : sfg.getAdj(i))
			{
				int to = e.to();
				if (to >= i)
				{
					marked[to] = true;
					loop.add(to);
					loopVisit(i,to,loop,marked);
					loop.pop();
					marked[to] = false;
				}
			}
			
		}
	}
	
	private void loopVisit(int start, int v, Stack<Integer> loop, boolean[] marked)
	{
		if (v == start) // Loop Found
		{
			constructLoop(loop);
			return;
		}
		
		for (EdgeEquation e : sfg.getAdj(v))
		{
			int to = e.to();
			if (to == start)
			{
				loop.push(to);
				if (newLoop(loop))
					constructLoop(loop);
				loop.pop();
				return;
			}
			if (!marked[to])
			{
				marked[to] = true;
				loop.push(e.to());
				loopVisit(start, e.to(), loop,marked);
				loop.pop();
				marked[to] = false;
			}
		}
	}
	
	private boolean newLoop(Stack<Integer> loopFound)
	{
		Stack<Integer> loop = (Stack<Integer>) loopFound.clone();

		for (Path l : loops)
		{
			List<Integer> loopList = l.getLoop();
			if (loop.size() == loopList.size() && loop.size() > 1)
			{
				int index = 1;
				for (index = 1; index < loopList.size(); index++)
				{
					if (loopList.get(index) != loop.pop())
						break;
				}
				if (index == loop.size())
					return false;
			}
		}		
		
		return true;
	}
	
	private void constructLoop(Stack<Integer> loopFound)
	{
		Stack<Integer> loop = (Stack<Integer>) loopFound.clone();
		List<Integer> loopToAdd = new LinkedList<Integer>();
		int lastNode = loop.pop();
		loopToAdd.add(lastNode);
		StringBuilder func = new StringBuilder();
		
		while(!loop.isEmpty())
		{
			int node = loop.pop();
			loopToAdd.add(0, node);
			func.append(sfg.getEdge(node, lastNode).equation());
			lastNode = node;
		}
		
		loops.add(new Path(loopToAdd,func.toString()));
	}

}
