import java.util.ArrayList;


public class Client 
{
	
	public static void main(String[] args) 
	{
		NamingProxy namingPrxy = new NamingProxy(Info.NAMING_SERVER_HOST, Info.NAMING_SERVER_PORT);
		ROR remoteObject = (ROR)namingPrxy.lookup("PortalServant");
		
		PortalServantProxy protalServantPrxy = new PortalServantProxy(remoteObject);
		
		remoteObject = (ROR)protalServantPrxy.openAccount("H", "H", "3");
		
		remoteObject = (ROR)protalServantPrxy.login("H", "H");
		
		if(remoteObject != null)
		{
						
			AccountProxy accountPrxy = new AccountProxy(remoteObject);
			
			accountPrxy.addBalance(50);
			System.out.println("Current Balance: " + accountPrxy.checkBalance());
			
			accountPrxy.buyTicket("MegaMillion", "quickpick", null);			
			accountPrxy.buyTicket("PowerBall", "quickpick", null);
			accountPrxy.buyTicket("MegaMillion", "quickpick", null);
			accountPrxy.buyTicket("PowerBall", "quickpick", null);
			accountPrxy.buyTicket("MegaMillion", "quickpick", null);
			accountPrxy.buyTicket("MegaMillion", "manual", "456321");
			accountPrxy.buyTicket("PowerBall", "manual", "654123");
			accountPrxy.buyTicket("MegaMillion", "manual", "123456");
			accountPrxy.buyTicket("PowerBall", "manual", "987654");
			accountPrxy.buyTicket("MegaMillion", "manual", "456789");
			
			ArrayList<Ticket> ticketList  = accountPrxy.getHistoryTicket();
			
			System.out.println("\nTicket No \t Round No \t Lottery Type \t Jackpot Number");
			for(Ticket ticket : ticketList)
			{
				System.out.println(ticket.ticketNo + " \t\t " + ticket.roundNo + " \t\t " + ticket.lotteryType + " \t " + ticket.jackpotNumber);
			}
		}
		else
		{
			System.out.println("Longin failed");
		}
	}
}
