import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class PortalServant implements PortalServantInterface 
{
	private static String FILENAME = "AccountManager.ser";
	ROR remoteObject;
	MegaMillionProxy megaMillionPrxy;
	PowerBallProxy powerBallPrxy;
	public HashMap<String, Object> objectList;
	
	public ArrayList<Account> accountList = new ArrayList<Account>();
	
	public PortalServant(String host, int port, String objectId)
	{
		remoteObject = new ROR(host, port, objectId, "PortalServant");
		
		this.loadState();
	}
	
	@Override
	public synchronized Object openAccount(String userName, String password, String objectId) 
	{
		Account account = new Account(remoteObject.getHostAddress(), remoteObject.getHostPort(), objectId, userName, password, megaMillionPrxy.getRemoteObject(), powerBallPrxy.getRemoteObject());
				
		accountList.add(account);
		RemoteRefLocalMap.addObject(account.getRemoteObject().getObjectId(), account);
		
		this.saveState();
		
		return (Object)account.getRemoteObject();
	}

	@Override
	public synchronized Object login(String userName, String password) 
	{
		this.loadState();
		
		for(Account account : accountList)
		{
			if(account.getUserName().equals(userName) && account.getPassword().equals(password))
			{
				return (Object)account.getRemoteObject();
			}
		}
		return (Object)null;
	}

	@Override
	public synchronized Object closeAccount(String userName, String password) 
	{
		for(Account account : accountList)
		{
			if(account.getUserName().equals(userName) && account.getPassword().equals(password))
			{
				accountList.remove(account);
				
				this.saveState();
				
				return (Object)true;
			}
		}
		
		return (Object)false;
	}
	
	public synchronized ROR getRemoteObject()
	{
		return remoteObject;
	}
	
	public synchronized void setMegaMillionPrxy(MegaMillionProxy megaMillionPrxy)
	{
		this.megaMillionPrxy = megaMillionPrxy;
	}
	
	public synchronized void setPowerBallPrxy(PowerBallProxy powerBallPrxy)
	{
		this.powerBallPrxy = powerBallPrxy;
	}
	
	public synchronized void saveState()
	{
		String currentDir = System.getProperty("user.dir");
		
		File file = new File(currentDir+"\\"+ FILENAME);
		try 
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			else
			{
				file.delete();
				file.createNewFile();
			}
			
			FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath()); 
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream); 
			
			objectOutputStream.writeObject(accountList); 
			
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		
		} 
		catch (Exception ex) 
		{
			System.out.println("PortalServant.saveState() :: " + ex.toString());
			ex.printStackTrace();
		}
	}
	
	public synchronized void loadState() 
	{
		File file = new File(FILENAME);
		if(file.exists())
		{
			try 
			{
				FileInputStream fileInputStream = new FileInputStream(FILENAME);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				accountList = (ArrayList<Account>)objectInputStream.readObject();
				
				objectInputStream.close();
				fileInputStream.close();
				
				for (Account account : accountList) 
				{
					System.out.println(account.getUserName() + "  " + account.getPassword() + " "+ account.getHistoryTicket().size());
					RemoteRefLocalMap.addObject(((ROR)account.getRemoteObject()).getObjectId(), account);
				} 
			}
			catch (Exception ex) 
			{
				System.out.println("PortalServant.loadState() :: " +  ex.toString());
			}
		}
	}
}
