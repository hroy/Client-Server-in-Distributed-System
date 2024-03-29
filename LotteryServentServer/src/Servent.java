import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Servent implements Runnable
{
	Socket socket;
	RemoteRefLocalMap remoteReferenceModule;
	
	public Servent(Socket socket, RemoteRefLocalMap remoteReferenceModule)
	{
		this.socket = socket;
		this.remoteReferenceModule = remoteReferenceModule;
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
			
			Object localObject = remoteReferenceModule.getRemoteObject(remoteObject.getObjectId());
			
			Object returnObject = null;
			if(localObject instanceof MegaMillion)
			{
				MegaMillion megaMillionLocal = (MegaMillion)localObject;
				if(remoteObject.getMethodName().equals("sellTicketQuickPick"))
				{
					returnObject = (Object)megaMillionLocal.sellTicketQuickPick();
				
				}
				else if(remoteObject.getMethodName().equals("sellTicketManual"))
				{
					String ticketNo = remoteObject.getParameter();
					returnObject = megaMillionLocal.sellTicketManual(ticketNo);
					
					
				}
				else if(remoteObject.getMethodName().equals("getPastJackPotNumbers"))
				{
					
					returnObject = (Object)megaMillionLocal.getPastJackPotNumbers();
					
				}
				else if(remoteObject.getMethodName().equals("checkWinner"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					returnObject = (Object)megaMillionLocal.checkWinner(parameters[1], Integer.parseInt(parameters[0]));
				}
				
			}
			if(localObject instanceof PowerBall)
			{
				PowerBall powerBallLocal = (PowerBall)localObject;
				if(remoteObject.getMethodName().equals("sellTicketQuickPick"))
				{
					returnObject = (Object)powerBallLocal.sellTicketQuickPick();
				}
				else if(remoteObject.getMethodName().equals("sellTicketManual"))
				{
					String ticketNo = remoteObject.getParameter();
					returnObject = (Object)powerBallLocal.sellTicketManual(ticketNo);
					
				}
				else if(remoteObject.getMethodName().equals("getPastJackPotNumbers"))
				{
					
					returnObject = (Object)powerBallLocal.getPastJackPotNumbers();
					
				}
				else if(remoteObject.getMethodName().equals("checkWinner"))
				{
					String[] parameters = remoteObject.getParameter().split(",");
					returnObject = (Object)powerBallLocal.checkWinner(parameters[1], Integer.parseInt(parameters[0]));
				}
			}
				
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
