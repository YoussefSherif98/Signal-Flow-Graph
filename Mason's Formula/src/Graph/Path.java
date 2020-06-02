package Graph;
import java.util.List;

public class Path {

	private List<Integer> l;
	private String gain;
	
	public Path(List<Integer> loop, String func)
	{
		this.l = loop;
		this.gain = func;
	}
	
	public String getGain()
	{
		return this.gain;
	}
	
	public List<Integer> getLoop()
	{
		return this.l;
	}
	
	
	public boolean contains(int v)
	{
		for (Integer i : this.l)
		{
			if (i == v)
				return true;
		}
		return false;
	}
	
	public boolean isTouching(Path loop)
	{
		for (Integer i : loop.getLoop())
		{
			if (this.contains(i))
				return true;
		}
		return false;
	}
}
