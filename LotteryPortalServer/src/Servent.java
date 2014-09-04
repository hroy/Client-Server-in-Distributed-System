import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Servent implements Runnable
{
	Socket socket;
	PortalServant portalServant;
	
	public Servent(Socket socket, PortalServant portalServant)
	{
		this.socket = socket;
		this.portalServant = portalServant;
	}

	@Override
	public void run()
	{
		try
		{
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			Object object = objectInputStream.readObject();
			ROR remoteObject = (ROR)object;
			
			System.out.println("Reqest recieved ObjectId: " + remoteObject.getObjectId());
			System.out.println("MethodName: " + remoteObject.getMethodName());
			
			Object localObject = RemoteRefLocalMap.getObject(remoteObject.getObjectId());
			
			
			Object returnObject = null;
			
			if(localObject instanceof PortalServant)
			{
				if(remoteObject.getMethodName().equals("openAccount"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					
					returnObject = portalServant.openAccount(parameters[0], parameters[1], parameters[2]);
					
									}
				else if(remoteObject.getMethodName().equals("login"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					
					returnObject = portalServant.login(parameters[0], parameters[1]);
				}
				else if(remoteObject.getMethodName().equals("closeAccount"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					
					returnObject = portalServant.closeAccount(parameters[0], parameters[1]);
					
				}
			}
			else if(localObject instanceof Account)
			{
				Account accountlocal = (Account)localObject;
				if(remoteObject.getMethodName().equals("addBalance"))
				{
					int amount = Integer.parseInt(remoteObject.getParameter());
					
					accountlocal.addBalance(amount);
				}
				else if(remoteObject.getMethodName().equals("checkBalance"))
				{
					returnObject = (Object)accountlocal.checkBalance();
				}
				else if(remoteObject.getMethodName().equals("buyTicket"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					accountlocal.buyTicket(parameters[0], parameters[1], parameters[2]);
				}
				else if(remoteObject.getMethodName().equals("getHistoryTicket"))
				{
					returnObject = (Object)accountlocal.getHistoryTicket();
				}

				portalServant.saveState();
			}
			
			System.out.println("");
			
			objectOutputStream.writeObject(returnObject);
			
			objectOutputStream.close();
			objectInputStream.close();
			socket.close();
			
		}
		catch(Exception ex)
		{
			System.out.println("WorkerThread.run() :: " + ex.toString());
		}
	}
	
}