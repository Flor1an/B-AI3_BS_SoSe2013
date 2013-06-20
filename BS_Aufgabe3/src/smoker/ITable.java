package smoker;
import java.util.List;


public interface ITable {

	public void putOnTable(List<String> ingredients) throws InterruptedException;
	
	public void smoke(Smoker smoker) throws InterruptedException;
}
