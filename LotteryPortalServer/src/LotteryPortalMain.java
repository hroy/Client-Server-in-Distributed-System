import java.awt.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class LotteryPortalMain 
{
	
	public static void main(String[] args) 
	{
		try
		{
			HashMap<String, Object> objectList = new HashMap<String, Object>();
			
			NamingProxy namingPrxy = new NamingProxy(Info.NAMING_SERVER_HOST, Info.NAMING_SERVER_PORT);
			
			ROR remoteObject = (ROR)namingPrxy.lookup("MegaMillion");
			MegaMillionProxy megaMillionPrxy = new MegaMillionProxy(remoteObject);
			
			remoteObject = (ROR)namingPrxy.lookup("PowerBall");
			PowerBallProxy powerBallPrxy = new PowerBallProxy(remoteObject);
					
			
			PortalServant portalServant = new PortalServant(Info.PORTAL_SERVER_HOST, Info.PORTAL_SERVER_PORT, "1");
			portalServant.setMegaMillionPrxy(megaMillionPrxy);
			portalServant.setPowerBallPrxy(powerBallPrxy);
			
			RemoteRefLocalMap.addObject(portalServant.getRemoteObject().getObjectId(), portalServant);
			
			namingPrxy.bind("PortalServant", portalServant.getRemoteObject());
			
			
			ServerSocket serverSocket = new ServerSocket(Info.PORTAL_SERVER_PORT);
			while(true)
			{
				Socket socket = serverSocket.accept();
				
				Servent servent = new Servent(socket, portalServant);
				Thread thread = new Thread(servent);
				thread.start();
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	
}
