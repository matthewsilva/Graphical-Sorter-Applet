import java.awt.*;							//	AWT = "Abstract Window Toolkit"
import java.awt.event.*;					//		or "Another Window Toolkit"
import java.applet.*;

/**
 * The class makes use of a collection of items, and provides very basic 
 * operations on that collection, using a GUI with buttons
 * @author nickie
 *
 */
public class DataOrganizer implements MouseListener
{
	/**
	 * Maximum number of Items and maximum height of an Item
	 */
	private final int COLLECTION_SIZE = 10,	MAXIMUM_ITEM_VALUE = 16;	

	/**
	 * So we can repaint whenever a button is clicked
	 */
	Applet theApplet;

	/**
	 * for placing the buttons and collection
	 */
	int x, y;	

	/**
	 * To hold our items
	 */
	DataCollection collection;		

	/**
	 * User interface to make new collection, find max or min
	 * and remove the max or min if it has been selected
	 */
	Abutton randomButton, maximumButton, minimumButton,
	removeButton;			

	/**
	 * Holds where mouse was most recently pressed
	 */
	int lastX, lastY;				

	/**
	 * Default constructor can't do anything without the applet
	 */
	public DataOrganizer() {}

	/**
	 * Uses the given applet to set the scene with the visualization of the
	 * collection of Items and the buttons to manipulate the collection.
	 */
	public DataOrganizer(Applet someApplet)
	{
		theApplet = someApplet;

		theApplet.addMouseListener(this);	//	This class will handle the mouse

		x = 30;
		y = 30;
		randomButton = new Abutton("Random", Color.yellow, x, y);

		y += 1.5*Abutton.BUTTON_HEIGHT;
		maximumButton = new Abutton("Maximum", Color.green, x, y);
		y += 1.1*Abutton.BUTTON_HEIGHT;
		minimumButton = new Abutton("Minimum", Color.cyan, x, y);

		y += 1.5*Abutton.BUTTON_HEIGHT;
		removeButton = new Abutton("Remove", Color.pink, x, y);
		
		
		x += 1.5*Abutton.BUTTON_WIDTH;
		y += 1*Abutton.BUTTON_HEIGHT;
	}

	/**
	 * Generates a collection of 10 Items with random heights
	 */
	public void randomAction()
	{
		collection= new ArrayDataCollection(x, y);				//	We restart with nothing,
		//		then we add random items,
		for (int i = 0; i < COLLECTION_SIZE; i++) {
			Item temp = new Item();
			temp.setValue((int)(1 + MAXIMUM_ITEM_VALUE*Math.random()));
			temp.setColor(Color.orange);
			collection.add(temp);
		}
		collection.reset(null);				//	We make sure nothing is selected
	}

	/**
	 * Finds and selects the tallest Item in the collection.
	 * If there is a tie, chooses the left-most of the tallest
	 * Items
	 */
	public void maximumAction()
	{
		if(collection != null){
			Item maximumItem = null,
			someItem;

			collection.reset();						//	We start at the beginning

			if(collection.hasNext())
				maximumItem = collection.next();

			while (collection.hasNext()) {
				//	What's next?
				someItem = collection.next();

				//	If it is the largest so far, we keep track of it
				if  (someItem.getValue() > maximumItem.getValue()) 
					maximumItem = someItem;				
			}
			collection.reset(maximumItem);
		}
	}

	/**
	 * Finds and selects the shortest Item in the collection.
	 * If there is a tie, chooses the left-most of the shortest
	 * Items
	 */
	public void minimumAction()
	{
		if(collection != null){
			Item minimumItem = null,
			someItem;

			collection.reset();

			if(collection.hasNext())
				minimumItem = collection.next();

			while (collection.hasNext()) {
				//	What's next?
				someItem = (Item) (collection.next());

				//	If it is the smallest so far, we keep track of it
				if (someItem.getValue() < minimumItem.getValue())
					minimumItem = someItem;					
			}

			collection.reset(minimumItem);
		}
	}

	/**
	 * Removes selected Item, if there is any
	 */
	public void removeAction()
	{
		if(collection != null)
			collection.remove();
	}

	/**
	 * Paints the buttons and the collection
	 * @param pane provided by the system when the Frame is set to be
	 * visible or repaint() is invoked.
	 */
	public void paint(Graphics pane)
	{
		if (collection != null)				//	When instantiated,
			collection.paint(pane);			//		we display the element

		if (randomButton != null)
			randomButton.paint(pane);		//		and all of the buttons

		if (maximumButton != null)
			maximumButton.paint(pane);
		if (minimumButton != null)
			minimumButton.paint(pane);

		if (removeButton != null)
			removeButton.paint(pane);
	}

	/**
	 * Checks to see if click was inside a button and, if so, invokes
	 * method corresponding to button clicked
	 */
	public void mouseClicked(MouseEvent event)
	{

		theApplet.showStatus(" ");			//	To reset the status bar

		if (randomButton.isInside(lastX, lastY)) {
			randomAction();
		}

		else if (maximumButton.isInside(lastX, lastY)) {
			maximumAction();
		}
		else if (minimumButton.isInside(lastX, lastY)) {
			minimumAction();
		}

		else if (removeButton.isInside(lastX, lastY)) {
			removeAction();
		}
		
		else  {
			theApplet.showStatus("What?");
		}

		theApplet.repaint();
		//	Handle the mouse click
	}

	/**
	 * Assigns the mouse coordinates immediately so the button
	 * can be flipped even if it's a press but not a click.
	 */
	public void mousePressed(MouseEvent event)
	{
		lastX = event.getX();					//	Update the mouse location
		lastY = event.getY();

		flipWhenInside();						//	Flip any button hit by the mouse
	}

	/**
	 * If the button was pressed, this releases it even if it's
	 * not a mouse click.
	 */
	public void mouseReleased(MouseEvent event)
	{
		flipWhenInside();
	}

	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}

	/**
	 * Presses or releases button visually
	 */
	private void flipWhenInside()
	{
		if (randomButton.isInside(lastX, lastY))
			randomButton.flip();

		else if (maximumButton.isInside(lastX, lastY))
			maximumButton.flip();
		else if (minimumButton.isInside(lastX, lastY))
			minimumButton.flip();

		else if (removeButton.isInside(lastX, lastY))
			removeButton.flip();

		theApplet.repaint();
	}

}	
