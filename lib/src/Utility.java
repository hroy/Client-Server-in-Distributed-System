import java.util.Random;


public class Utility 
{
	public static String generateTicketNumber()
	{
		Random random = new Random();
		long ticket = (long)(random.nextDouble() *  1000000);
		
		String ticketNo = Long.toString(ticket);
		for(; ticketNo.length() < 6;)
		{
			ticketNo = "0"+ticketNo;
		}
		
		return ticketNo;
	}
}
