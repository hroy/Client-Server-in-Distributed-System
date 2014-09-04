
public interface PortalServantInterface 
{
	public Object openAccount(String userName, String password, String objectId);
	public Object login(String userName, String password);
	public Object closeAccount(String userName, String password);
}
