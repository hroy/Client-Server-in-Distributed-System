import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class AccountProxy implements AccountInterface 
{
	public ROR remoteObject;
	
	public AccountProxy(ROR remoteObject)
	{
		this.remoteObject = remoteObject;
	}

	@Override
	public void addBalance(int amount) 
	{
		remoteObject.setMethodName("addBalance");
		remoteObject.setParameter(Integer.toString(amount));
		
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
			
		}
		catch(Exception ex)
		{
			System.out.println("AccountStub.addBalance(int amount) -> " + ex.toString());
		}
		
	}

	@Override
	public int checkBalance() 
	{
		ROR rObject = this.remoteObject;
		rObject.setMethodName("checkBalance");
		
		try
		{
			Socket socket = new Socket(rObject.getHostAddress(), rObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)rObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return (int)returnObject; 
		}
		catch(Exception ex)
		{
			System.out.println("AccountStub.checkBalance() -> " + ex.toString());
			return -1;
		}
	}

	@Override
	public String buyTicket(String lotteryType, String pickType, String ticketNo) 
	{
		ROR rObject = this.remoteObject;
		rObject.setMethodName("buyTicket");
		rObject.setParameter(lotteryType +"," + pickType + "," + ticketNo);
		try
		{
			Socket socket = new Socket(rObject.getHostAddress(), rObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)rObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return (String)returnObject; 
		}
		catch(Exception ex)
		{
			System.out.println("AccountStub.buyTicket(String lotteryType, String pickType, String ticketNo) -> " + ex.toString());
			return null;
		}
	}

	@Override
	public ArrayList<Ticket> getHistoryTicket() 
	{
		ROR rObject = this.remoteObject;
		rObject.setMethodName("getHistoryTicket");
		
		try
		{
			Socket socket = new Socket(rObject.getHostAddress(), rObject.getHostPort());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject((Object)rObject);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object returnObject = objectInputStream.readObject();
			
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			return (ArrayList<Ticket>)returnObject; 
		}
		catch(Exception ex)
		{
			System.out.println("AccountStub.getHistoryTicket() -> " + ex.toString());
			return null;
		}
	}
	
}
