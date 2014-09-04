import java.net.ServerSocket;
import java.net.Socket;

public class NamingMain 
{	
	public static void main(String[] args) 
	{
		Socket socket;
		Naming mapTable = new Naming();
		try
		{
			ServerSocket serverSocket = new ServerSocket(Info.NAMING_SERVER_PORT);
			
			while(true)
			{
				socket = serverSocket.accept();
				
				Servent workerThread = new Servent(socket, mapTable);
				Thread thread = new Thread(workerThread);
				thread.start();
				
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		
	}
}