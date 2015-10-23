import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;



public class DefaultDoubleRangeModel implements Serializable, DoubleRangeModel, ChangeListener, ActionListener,WindowListener{

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
	
	JTextArea text = new JTextArea(valLeft + " " + valRight);	
	 
	public DefaultDoubleRangeModel(){
		this(0, 100, 25, 75, false); 
		
	}
	public DefaultDoubleRangeModel( int min, int max, int left, int right, boolean adjusting){
		if ((max>min) && (left <= right) && (min <= left) && (right <= max)){
			setRangeProperties(left, right, min, max, adjusting);
			
			JPanel listPane = new JPanel();
			listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
			
			JLabel sliderLabel = new JLabel("Range", JLabel.CENTER);
	        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        
	        RangeSlider slider = new RangeSlider(RangeSlider.HORIZONTAL,minimum, maximum, valLeft, valRight);
	        slider.addChangeListener(this);
	        slider.setBorder( BorderFactory.createEmptyBorder(0,0,10,0));	                
	        
	        listPane.add(sliderLabel);
	        listPane.add(slider);
	        listPane.add(text);
	        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	        
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
    
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }
    
	public void stateChanged(ChangeEvent e) {
		RangeSlider source = (RangeSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			text.setText(valLeft+ " " + valRight);
		}
		
	}

	public void windowOpened(WindowEvent e) {  }
	public void windowClosing(WindowEvent e) {	}
	public void windowClosed(WindowEvent e) {  }
	public void windowIconified(WindowEvent e) {  }
	public void windowDeiconified(WindowEvent e) {	}
	public void windowActivated(WindowEvent e) {  }
	public void windowDeactivated(WindowEvent e) {	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SliderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DefaultDoubleRangeModel testRange = new DefaultDoubleRangeModel();
                
        //Add content to the window.
        frame.add(testRange);
        //.add(testRange, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
