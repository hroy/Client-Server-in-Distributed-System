import java.util.ArrayList;


public interface AccountInterface
{
	public void addBalance(int amount);
	public int checkBalance();
	public String buyTicket(String lotteryType, String pickType, String ticketNo);
	public ArrayList<Ticket> getHistoryTicket();
}
