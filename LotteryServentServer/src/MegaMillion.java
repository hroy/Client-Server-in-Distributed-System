import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class MegaMillion implements MegaMillionInterface
{
	private int ticketCount;
	private int roundNo;
	private ArrayList<Ticket>ticketList = new ArrayList<Ticket>();
	private HashMap<Integer, String> jackpotNumbers = new HashMap<Integer, String>();
	
	private ROR remoteObject;
	
	public MegaMillion(String host, int port, String objectId)
	{
		remoteObject = new ROR(host, port, objectId, "MegaMillion");
		
		this.ticketCount = 499;
		this.roundNo = 1;
		jackpotNumbers.put(1, Utility.generateTicketNumber());
	}
		
	@Override
	public synchronized String sellTicketQuickPick()
	{
		String ticketNo;
	
		while(true)
		{
			ticketNo = Utility.generateTicketNumber();
			if(!this.ticketAlreadExists(ticketNo))
				break;
		}
		Ticket ticket = new Ticket("MegaMillion", ticketNo, roundNo);
		ticketList.add(ticket);

		this.increaseTicketCount();
		
		return ticket.getRoundNo() + "," + ticket.getTicketNo();
	}
	
	@Override
	public synchronized Object sellTicketManual(String ticketNo)
	{
		if(ticketNo.length() == 6 && !ticketAlreadExists(ticketNo))
		{
			Ticket ticket = new Ticket("MegaMillion", ticketNo, roundNo);
			ticketList.add(ticket);
			
			this.increaseTicketCount();
			
			return (Object)roundNo;
		}
		else
			return (Object)false;
	}
	
	@Override
	public synchronized String[] getPastJackPotNumbers()
	{
		int size = roundNo >= 5 ? 5 : roundNo;
		
		String[] numbers = new String[size];
		for(int i = 0, j = roundNo; i < size; i++, j--)
		{
			numbers[i] = jackpotNumbers.get(j);
		}
		
		return numbers;
	}
	
	@Override
	public synchronized boolean checkWinner(String ticketNo, int roundNo)
	{
		String jacpotNumber = jackpotNumbers.get(roundNo);
		if(jacpotNumber != null)
		{
			if(jacpotNumber.equals(ticketNo))
				return true;
		}
		return false;
	}
	
	public ROR getRemoteObject()
	{
		return remoteObject;
	}
	
	public synchronized void restart()
	{
		this.ticketCount = 0;
		this.roundNo++;
	}
	
	public synchronized void generateJackpotNumber()
	{
		String jackpotNumer = Utility.generateTicketNumber();
		jackpotNumbers.put(roundNo, jackpotNumer);
		
	}
	
	public synchronized boolean ticketAlreadExists(String ticketNo)
	{
		for(Ticket ticket : ticketList)
		{
			if(ticket.getTicketNo().equals(ticketNo) && ticket.getRoundNo() == roundNo)
				return true;
		}
		
		return false;
	}
	
	public synchronized void increaseTicketCount()
	{
		ticketCount++;
		if(ticketCount > 500)
		{
			roundNo++;
			ticketCount = 1;
		}
	}
	
}
