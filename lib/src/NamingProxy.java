import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class NamingProxy implements NamingInterface
{
	String hostIP;
	int port;
	
	public NamingProxy(String hostIP, int port)
	{
		this.hostIP = hostIP;
		this.port = port;
	}
	
	public void bind(String name, Object remoteObject)
	{
		try
		{
			((ROR)remoteObject).setMethodName("bind");
			((ROR)remoteObject).setAlias(name);
			
			//System.out.println(((RemoteObject)ROR).getAlias());
			
			Socket socket = new Socket(hostIP, port);
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(remoteObject);
			
			objectOutputStream.close();
			socket.close();
			
		}
		catch(Exception ex)
		{
			System.out.println("NamingStub.bind(String name, Object ROR) -> " + ex.toString());
		}
	}
	public void delete(String name){}
	
	@Override
	public Object lookup(String name)
	{
		try
		{
			Socket socket = new Socket(hostIP, port);
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)name);
			
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object object = objectInputStream.readObject(); 
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return object;
		}
		catch(Exception ex)
		{
			//System.out.println("NamingStub.lookup(String name) -> " + ex.toString());
			ex.printStackTrace();
			return null;
		}
	}
	
	
}
