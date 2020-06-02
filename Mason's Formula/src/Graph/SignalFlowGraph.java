package Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SignalFlowGraph {

	private Set<EdgeEquation>[] g;
	private Set<EdgeEquation>[] in;
	private List<EdgeEquation> edgeList;
	private int nodes;
	
	public SignalFlowGraph(int n)
	{
		this.g = new Set[n];
		this.in = new Set[n];
		for (int i = 0; i< n ;i++)
		{
			g[i] = new HashSet<EdgeEquation>();
			in[i] = new HashSet<EdgeEquation>();
		}
		this.nodes = n;
		this.edgeList = new ArrayList<EdgeEquation>();
	}
	
	public void addEdge(EdgeEquation e)
	{
		if (e == null)
			return;
		
		int from = e.from(), to = e.to();
		if (from < 0 || from >= nodes)
			return;
		
		g[from].add(e);
		in[to].add(e);
		edgeList.add(e);
	}
	
	public void addEdge(int from, int to, String function)
	{
		if (function == null)
			return;
		
		EdgeEquation e = new EdgeEquation(from,to,function);
		addEdge(e);
	}
	
	public Set<EdgeEquation> getAdj(int v)
	{
		if (v < 0 || v >= nodes)
			return null;
		
		return g[v];
	}
	
	public Set<EdgeEquation> getIncident(int v)
	{
		if (v < 0 || v >= nodes)
			return null;
		
		return in[v];
	}
	
	public int size()
	{
		return this.nodes;
	}
	
	public boolean isSink(int v)
	{
		if (v < 0 || v >= nodes)
			return false;
		
		Set<EdgeEquation> outGoing = this.getAdj(v);
		if (outGoing == null || outGoing.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean isSource(int v)
	{
		if (v < 0 || v >= nodes)
			return false;
		
		Set<EdgeEquation> inGoing = this.getIncident(v);
		if (inGoing == null || inGoing.isEmpty())
			return true;
		
		return false;
	}
	
	public void nonTraverseEdges()
	{
		for (EdgeEquation e : edgeList)
			e.setTraversed(false);
	}
	
	public EdgeEquation getEdge(int from, int to)
	{
		if (from < 0 || to < 0 || from >= nodes || to >= nodes)
			return null;
		
		Set<EdgeEquation> edges = this.getAdj(from);
		
		for (EdgeEquation e : edges)
		{
			if (e.to() == to)
				return e;
		}
		
		return null;
	}
	
	public List<EdgeEquation> getAllEdges()
	{
		return edgeList;
	}
}
