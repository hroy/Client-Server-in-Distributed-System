import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Servent  implements Runnable
{
	Naming naming;
	Socket socket;
	
	public Servent(Socket socket, Naming naming)
	{
		this.naming = naming;
		this.socket = socket;
	}
	
	public void run()
	{
		try
		{
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			
			Object object = (Object)objectInputStream.readObject();
			
			if(object instanceof ROR)
			{
				ROR remoteObject = (ROR)object;
				
				String methodName = remoteObject.getMethodName(); 
				
				if(methodName.equals("bind"))
				{
					String alias = remoteObject.getAlias();
					naming.bind(alias, object);
					
					System.out.println("bind Request for " + remoteObject.getAlias() + "("+remoteObject.getObjectId()+")");
				}
				
			}
			else if(object instanceof String)
			{
				String objectName = (String)object;
				
				Object lookupObject = naming.lookup(objectName);
				if(lookupObject != null)
				{
					System.out.println("lookup Request for " + objectName+"("+((ROR)lookupObject).getObjectId()+")");
				}
				else
				{
					System.out.println("No data found");
				}
				
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(lookupObject);
				
				objectOutputStream.close();
			}
			
			inputStream.close();
			objectInputStream.close();
			socket.close();
		}
		catch(Exception ex)
		{
			System.out.println("WorkerThread.run() -> " + ex.toString());
		}
	}
	
	
}
