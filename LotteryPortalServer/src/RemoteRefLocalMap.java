import java.util.HashMap;


public class RemoteRefLocalMap 
{
	public static HashMap<String, Object> objectList = new HashMap<String, Object>();
	
	
	public synchronized static void addObject(String objectId, Object object)
	{
		objectList.put(objectId, object);
	}
	
	public synchronized static Object getObject(String objectId)
	{
		return objectList.get(objectId);
	}
}
