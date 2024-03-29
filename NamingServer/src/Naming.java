import java.util.HashMap;


public class Naming implements NamingInterface
{
	private HashMap<String, Object> objectTable = new HashMap<String, Object>();
	
	
	@Override
	public synchronized void bind(String name, Object ROR)
	{
		if (!objectTable.containsKey(name)) 
		{
			objectTable.put(name, ROR);
		}
	}
	
	public synchronized void delete(String name){}
	
	@Override
	public synchronized Object lookup(String name)
	{
		return objectTable.get(name);
	}
}
