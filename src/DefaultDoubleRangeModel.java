import java.io.Serializable;
import java.util.EventListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;


public class DefaultDoubleRangeModel implements Serializable, DoubleRangeModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected transient ChangeEvent changeEvent = null;
	protected EventListenerList listenerList = new EventListenerList();

	  
	protected int minimum = 0;
	protected int maximum = 100;
	
	protected int valLeft = 25;
	protected int valRight = 75;

	protected boolean isAdjusting = false;
	 
	public DefaultDoubleRangeModel(){
		
	}
	public DefaultDoubleRangeModel( int min, int max, int left, int right, boolean adjusting){
		if ((max>min) && (left <= right) && (min <= left) && (right <= max)){
			setRangeProperties(left, right, min, max, adjusting);
		} else {
			throw new IllegalArgumentException("invalid range properties");
		}
	
}
	
	public int getMinimum() {
		return minimum;
	}

	 
	public void setMinimum(int newMinimum) {
		if (newMinimum < this.maximum){
			this.minimum = newMinimum;	
			if (this.valLeft < newMinimum){
				setRangeProperties(valRight, valLeft, newMinimum, maximum, isAdjusting);
			}
		}			
	}

	 
	public int getMaximum() {
		return maximum;
	}

	 
	public void setMaximum(int newMaximum) {
		if (newMaximum > this.minimum){
			this.maximum = newMaximum;	
			if (this.valRight > newMaximum){
				setRangeProperties(valRight, valLeft, minimum, newMaximum, isAdjusting);
			}
		}			
	}

	 
	public int getValueLeft() {
		return valLeft;
	}

	 
	public void setValueLeft(int newValue) {
		if ((newValue >= this.minimum) && (newValue < this.valRight)){
			setRangeProperties(valRight, newValue, minimum, maximum, isAdjusting);	
		}			
	}
	
	public int getValueRight() {
		return valRight;
	}

	 
	public void setValueRight(int newValue) {
		if ((newValue <= this.maximum) && (newValue > this.valLeft)){
			setRangeProperties(newValue, valLeft, minimum, maximum, isAdjusting);	
		}	
	}

	 
	public void setValueIsAdjusting(boolean b) {
		setRangeProperties(valRight, valLeft, minimum, maximum, b);
	}

	 
	public boolean getValueIsAdjusting() {
		return isAdjusting;
	}

	
	
 
	public void setRangeProperties(int valueLeft, int valueRight,  int min, int max,
			boolean adjusting) {
		this.valLeft = valueLeft;
		this.valRight = valueRight;
		this.minimum = min;
		this.maximum = max;
		this.isAdjusting = adjusting;
		fireStateChanged();
	}
	 
	public void addChangeListener(ChangeListener x) {
		this.listenerList.add(ChangeListener.class, x);
		
	}
	 
	public void removeChangeListener(ChangeListener x) {
		 this.listenerList.remove(ChangeListener.class, x);
		
	}
	
	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[])listenerList.getListeners(ChangeListener.class);
	}
	  
	protected void fireStateChanged(){
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -=2 ) {
			if (listeners[i] == ChangeListener.class) {
				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
			}
		}
	}
	
    public String toString()  {
    	String modelString =
    		"valueRight=" + getValueRight() + ", " +
    				"valueLeft=" + getValueLeft() + ", " +
    				"min=" + getMinimum() + ", " +
    				"max=" + getMaximum() + ", " +
    				"adj=" + getValueIsAdjusting();
    	return getClass().getName() + "[" + modelString + "]";
    }
    
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
    	return listenerList.getListeners(listenerType);
    }
}
