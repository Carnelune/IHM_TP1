import java.io.Serializable;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class RangeSlider extends JComponent implements SwingConstants, Accessible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected DoubleRangeModel sliderModel;
	protected ChangeListener changeListener = createChangeListener();
	protected transient ChangeEvent changeEvent = null;
	
    private class ModelListener implements ChangeListener, Serializable {
        public void stateChanged(ChangeEvent e) {
        	fireStateChanged();
        }
    }
    
    protected ChangeListener createChangeListener() {
    	return new ModelListener();
    }
    
    public void addChangeListener(ChangeListener l) {
    	listenerList.add(ChangeListener.class, l);
    }
    
    public void removeChangeListener(ChangeListener l) {
    	listenerList.remove(ChangeListener.class, l);
    }

     public ChangeListener[] getChangeListeners() {
    	 return (ChangeListener[])listenerList.getListeners(
    			 ChangeListener.class);
     }

     protected void fireStateChanged() {
    	 Object[] listeners = listenerList.getListenerList();
    	 for (int i = listeners.length - 2; i >= 0; i -= 2) {
    		 if (listeners[i]==ChangeListener.class) {
    			 if (changeEvent == null) {
    				 changeEvent = new ChangeEvent(this);
    			 }
    			 ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
    		 }
    	 }
     }

     public DoubleRangeModel getModel() {
    	 return sliderModel;
     }
     
}
