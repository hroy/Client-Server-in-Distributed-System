import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;


public class PowerBallProxy implements PowerBallInterface 
{
	ROR remoteObject;
	
	public PowerBallProxy(ROR remoteObject)
	{
		this.remoteObject = remoteObject;
	
	}
	
	@Override
	public String sellTicketQuickPick() 
	{
		remoteObject.setMethodName("sellTicketQuickPick");
		remoteObject.setParameter("");
		
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
			
			return (String)returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PowerBallStub.sellTicketQuickPick() -> " + ex.toString());
			return null;
		}
	}

	@Override
	public Object sellTicketManual(String ticketNo) 
	{
		remoteObject.setMethodName("sellTicketManual");
		remoteObject.setParameter(ticketNo);
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
			System.out.println("PowerBallStub.sellTicketManual(String ticketNo) -> " + ex.toString());
			return false;
		}
	}

	@Override
	public String[] getPastJackPotNumbers() 
	{
		remoteObject.setMethodName("getPastJackPotNumbers");
		remoteObject.setParameter("");
		
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
			
			return (String[])returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PowerBallStub.getPastJackPotNumbers() -> " + ex.toString());
			return null;
		}
	}

	@Override
	public boolean checkWinner(String ticketNo, int roundNo) 
	{
		try
		{
			remoteObject.setMethodName("checkWinner");
			remoteObject.setParameter(roundNo + "," + ticketNo);
			
			Socket socket = new Socket(remoteObject.getHostAddress(), remoteObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)remoteObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return (boolean)returnObject;
		}
		catch(Exception ex)
		{
			System.out.println("PowerBallStub.checkWinner(String ticketNo, int roundNo) -> " + ex.toString());
			return false;
		}
	}
	
	public ROR getRemoteObject()
	{
		return this.remoteObject;
	}
}
