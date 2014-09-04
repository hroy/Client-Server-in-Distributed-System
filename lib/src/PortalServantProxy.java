import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class PortalServantProxy implements PortalServantInterface 
{
	public ROR remoteObject;
	
	public PortalServantProxy(ROR remoteObject)
	{
		this.remoteObject = remoteObject;
	}

	@Override
	public synchronized Object openAccount(String userName, String password, String objectId) 
	{
		remoteObject.setMethodName("openAccount");
		remoteObject.setParameter(userName +"," + password + "," + objectId);
		
		try
		{
			Socket socket = new Socket(remoteObject.getHostAddress(), remoteObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)remoteObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PortalServantStub.openAccount(String userName, String password, String objectId) -> " + ex.toString());
		}
		
		return null;
	}

	@Override
	public synchronized Object login(String userName, String password) 
	{
		remoteObject.setMethodName("login");
		remoteObject.setParameter(userName +"," + password);
		
		try
		{
			Socket socket = new Socket(remoteObject.getHostAddress(), remoteObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)remoteObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			if(returnObject == null)
			{
				return null;
			}
			
			return returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PortalServantStub.login(String userName, String password) -> " + ex.toString());
		}
		
		return null;
	}

	@Override
	public synchronized Object closeAccount(String userName, String password) 
	{
		remoteObject.setMethodName("closeAccount");
		remoteObject.setParameter(userName +"," + password);
		
		try
		{
			Socket socket = new Socket(remoteObject.getHostAddress(), remoteObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)remoteObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			if((boolean)returnObject == true)
			{
				System.out.println("close account success");
			}
			else
			{
				System.out.println("close account fail");
			}
			
			return returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PortalServantStub.closeAccount(String userName, String password) -> " + ex.toString());
		}
		
		return null;
	}

}
