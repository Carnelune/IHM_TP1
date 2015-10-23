import javax.swing.event.ChangeListener;


public interface DoubleRangeModel {

	public int getMinimum();
	public void setMinimum(int newMinimum);
	public int getMaximum();
	public void setMaximum(int newMaximum);
	public int getValueLeft();
	public void setValueLeft(int newValue);
	public int getValueRight();
	public void setValueRight(int newValue);
	public void setValueIsAdjusting(boolean b);
	public boolean getValueIsAdjusting();
	public void setRangeProperties(int valueLeft, int valueRight,  int min, int max,
			boolean adjusting);
	public void addChangeListener(ChangeListener x);
	public void removeChangeListener(ChangeListener x) ;
	
}
