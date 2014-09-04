
public interface NamingInterface 
{
	public void bind(String name, Object ROR);
	public void delete(String name);
	public Object lookup(String name);
}
