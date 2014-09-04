import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Account implements AccountInterface, Serializable
{
	private String userName;
	private String password;
	private int balance = 0;
	
	private ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
	String jackpotNumber = "123456";
	
	ROR remoteObject;
	ROR megaRemoteObject;
	ROR powerRemoteObject;
	
	public Account(String host, int port, String objectId, String userName, String password, ROR megaRemoteObject, ROR powerRemtoeObject)
	{
		remoteObject = new ROR(host, port, objectId, "Account");
		this.userName = userName;
		this.password = password;
		this.balance = 100;
		
		this.megaRemoteObject = megaRemoteObject;
		this.powerRemoteObject = powerRemtoeObject;
	}
	
	@Override
	public void addBalance(int amount) 
	{
		balance += amount;
		
		RemoteRefLocalMap.addObject(remoteObject.getObjectId(), this);
	}

	@Override
	public int checkBalance() 
	{
		return balance;
	}

	@Override
	public String buyTicket(String lotteryType, String pickType, String ticketNo) 
	{
		try
		{
			if(balance < 10)
				return null;
			
			if(lotteryType.equals("MegaMillion"))
			{
				MegaMillionProxy megaMillionStub = new MegaMillionProxy(megaRemoteObject);
				if(pickType.equals("quickpick"))
				{
					String result = megaMillionStub.sellTicketQuickPick();
					String[] values = result.split(",");
					
					Ticket ticket = new Ticket(lotteryType, values[1], Integer.parseInt(values[0]));
					
					boolean winner = megaMillionStub.checkWinner(values[1], Integer.parseInt(values[0]));
					
					ticket.setWinner(winner);
					
					ticketList.add(ticket);
					balance -= 10;
					
					System.out.println("Input from MegaMillion: " + result);
				}
				if(pickType.equals("manual"))
				{
					Object resultObject = megaMillionStub.sellTicketManual(ticketNo);
					if(resultObject instanceof Integer)
					{
						Ticket ticket = new Ticket(lotteryType, ticketNo, (Integer)resultObject);
						
						boolean winner = megaMillionStub.checkWinner(ticketNo, (Integer)resultObject);
						
						ticket.setWinner(winner);
						
						ticketList.add(ticket);
						balance -= 10;
						
						System.out.println("Input from MegaMillion: " + (Integer)resultObject);
					}
					else
						System.out.println("Input from MegaMillion: " + (boolean)resultObject);
				}
			}
			else if(lotteryType.equals("PowerBall"))
			{
				PowerBallProxy powerBallStub = new PowerBallProxy(powerRemoteObject);
				if(pickType.equals("quickpick"))
				{
					String result = powerBallStub.sellTicketQuickPick();
					String[] values = result.split(",");
					
					Ticket ticket = new Ticket(lotteryType, values[1], Integer.parseInt(values[0]));
					
					boolean winner = powerBallStub.checkWinner(values[1], Integer.parseInt(values[0]));
					
					ticket.setWinner(winner);
					
					ticketList.add(ticket);
					balance -= 10;
					
					System.out.println("Input from PowerBall: " + result);
				}
				if(pickType.equals("manual"))
				{
					Object resultObject = powerBallStub.sellTicketManual(ticketNo);
					if(resultObject instanceof Integer)
					{
						Ticket ticket = new Ticket(lotteryType, ticketNo, (Integer)resultObject);
						
						boolean winner = powerBallStub.checkWinner(ticketNo, (Integer)resultObject);
						
						ticket.setWinner(winner);
						
						ticketList.add(ticket);
						balance -= 10;
						
						System.out.println("Input from PowerBall: " + (Integer)resultObject);
					}
					else
						System.out.println("Input from PowerBall: " + (boolean)resultObject);
				}
			}
			RemoteRefLocalMap.addObject(remoteObject.getObjectId(), (Object)this);
			
			
			return ticketNo;
		}
		catch(Exception ex)
		{
			System.out.println("Account.buyTicket(String lotteryType, String pickType, String ticketNo) :: " + ex.toString());
			return null;
		}
	}
	
	@Override
	public ArrayList<Ticket> getHistoryTicket() 
	{
		return ticketList;
	}

	public String getUserName() 
	{
		return userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public ROR getRemoteObject()
	{
		return remoteObject;
	}
}
