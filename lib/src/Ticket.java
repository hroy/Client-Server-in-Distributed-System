import java.io.Serializable;


public class Ticket implements Serializable
{
	public String lotteryType;
	public String ticketNo;
	public boolean jackpotNumber;
	public int roundNo;
	
	public Ticket(String lotteryType, String ticketNo, int roundNo)
	{
		this.lotteryType = lotteryType;
		this.ticketNo = ticketNo;
		this.roundNo = roundNo;
	}
	
	public void setWinner(boolean jackpotNumber)
	{
		this.jackpotNumber = jackpotNumber;
	}
	
	public String getTicketNo()
	{
		return ticketNo;
	}
	
	public int getRoundNo()
	{
		return this.roundNo;
	}
}
