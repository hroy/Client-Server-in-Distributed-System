import java.util.HashMap;


public class RemoteRefLocalMap 
{
	private HashMap<String, Object> objectList = new HashMap<String, Object>();
	
	public RemoteRefLocalMap()
	{
		
	}
	
	public void addRemoteObject(String objectId, Object object)
	{
		objectList.put(objectId, object);
		
	}
	
	public Object getRemoteObject(String objectId)
	{
		return objectList.get(objectId);
	}
}
