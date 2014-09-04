public interface PowerBallInterface 
{
	public String sellTicketQuickPick();
	public Object sellTicketManual(String ticketNo);
	public String[] getPastJackPotNumbers();
	public boolean checkWinner(String ticketNo, int roundNo);
}
