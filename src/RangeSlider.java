import java.io.Serializable;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
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

	protected int orientation;
	
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
     
     public void  setModel(DoubleRangeModel newModel) {
    	 DoubleRangeModel oldModel = getModel();
    	 if (oldModel != null) {
    		 oldModel.removeChangeListener(changeListener);
    	 }
    	 sliderModel = newModel;
    	 if (newModel != null) {
    		 newModel.addChangeListener(changeListener);
    		 if (accessibleContext != null) {
    			 accessibleContext.firePropertyChange(AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
    					 (oldModel == null ? null : new DefaultDoubleRangeModel(oldModel.getMinimum(), oldModel.getMaximum(),
    							 oldModel.getValueLeft(), oldModel.getValueRight(), oldModel.getValueIsAdjusting())),
    					 (newModel == null ? null : new DefaultDoubleRangeModel(newModel.getMinimum(), newModel.getMaximum(),
    							 newModel.getValueLeft(), newModel.getValueRight(), newModel.getValueIsAdjusting()))
    							 );
    		 }
    	 }
    	 firePropertyChange("model", oldModel, sliderModel);
     }
     
     public RangeSlider(int orientation, int min, int max, int left, int right){
         this.orientation = orientation;
         sliderModel = new DefaultDoubleRangeModel(min, max, left, right, false);
         sliderModel.addChangeListener(changeListener);
         updateUI();
     }
     
     public boolean getValueIsAdjusting() {
         return getModel().getValueIsAdjusting();
     }
     
     
     
}
