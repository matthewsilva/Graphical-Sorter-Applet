import java.awt.*;							//	AWT = "Abstract Window Toolkit"
import java.awt.event.*;					//		or "Another Window Toolkit"
import java.applet.*;

public class SortableDataOrganizer extends DataOrganizer{

	/**
	 * Sorts the Collection when pressed
	 */
	private Abutton sortButton;
	
	/**
	 * Default constructor can't do anything without the applet
	 */
	public SortableDataOrganizer() {}

	/**
	 * Uses the given applet to set the scene with the visualization of the
	 * collection of Items and the buttons to manipulate the collection.
	 */
	public SortableDataOrganizer(Applet someApplet)
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
		
		y += 1.1*Abutton.BUTTON_HEIGHT;
		sortButton = new Abutton("Sort", Color.yellow, x, y);


		x += 1.5*Abutton.BUTTON_WIDTH;
		y += 1*Abutton.BUTTON_HEIGHT;
	}
	
	/**
	 * Sorts the current DataCollection from smallest to largest
	 * and then selects nothing.
	 */
	public void sortAction() {
		if (collection != null) {
			collection.reset();
			DataCollection sorted = mergeSort(collection);	// Uses merge sort to sort collection
			sorted.reset();
			collection= new ArrayDataCollection(x, y);	// Clear the original DataCollection
			while (sorted.hasNext()) {	// Refill it with the sorted data
				collection.add(sorted.next());
			}
			collection.reset(null);						// Select nothing
		}
	}
		
	
	/**
	 * Recursively sorts the given DataCollection using
	 * merge sort.
	 * @param DataCollection to be sorted
	 * @return Same DataCollection but sorted from smallest to largest
	 */
	private DataCollection mergeSort(DataCollection collection) {
		
		if (size(collection) > 1) {	// Recursive step splits the array recursively and merges halves
			DataCollection firstPart = firstPart(collection);
			DataCollection secondPart = secondPart(collection);
			firstPart = mergeSort(firstPart);	// Recurse onto first half
			secondPart = mergeSort(secondPart);	// Recurse onto second half
			return merge (firstPart, secondPart);	// Sort first and second half by merging
		}
		else
			return collection;	// Base case returns a DataCollection of size 1
	}
	
	/**
	 * Returns the size of the given DataCollection.
	 * Resets the collection before and after calculating the size.
	 * @return The number of elements in the DataCollection
	 * i.e. 0 if empty
	 * @param The DataCollection to size
	 */
	private int size(DataCollection collection) {
		int size = 0;
		collection.reset();
		while(collection.hasNext()) {
			size++;
			collection.next();
		}
		collection.reset();
		return size;
	}
	
	/**
	 * Returns a new DataCollection composed of the first half of the given DataCollection.
	 * The first half is rounded up in size if the number of elements is odd.
	 * @param DataCollection to examine
	 * @return DataCollection containing the first half of
	 * the original DataCollection
	 */
	private DataCollection firstPart(DataCollection collection) {
		int size = size(collection);
		DataCollection result = new ArrayDataCollection();
		int middle = (int) Math.ceil((double) size / 2);	// Find the middle, rounded up
		for (int i = 0; i < middle ; i++)
			result.add(collection.next());	// Add up to the middle
		return result;
	}

	/**
	 * Returns a new DataCollection composed of the second half of the given DataCollection.
	 * The second half is rounded down in size if the number of elements is odd.
	 * @param DataCollection to examine
	 * @return DataCollection containing the second half of
	 * the original DataCollection
	 */
	private DataCollection secondPart(DataCollection collection) {
		int size = size(collection);
		DataCollection result = new ArrayDataCollection();
		int middle = (int) Math.ceil((double) size / 2);	// Find the middle, rounded up
		for (int i = 0; i < middle ; i++)
			collection.next();	// Iterate through the first half
		for (int i = middle; i < size; i++)
				result.add(collection.next());	// Add the second half
		return result;
	}
	
	/**
	 * Merges two given DataCollections such that their Comparable entries
	 * are added to a new DataCollection in ascending order.
	 * This new DataCollection is returned.
	 * @param First DataCollection to merge
	 * @param Second DataCollection to merge
	 * @return Merged DataCollections with entries in ascending order
	 */
	private DataCollection merge(DataCollection first, DataCollection second) {
		DataCollection result = new ArrayDataCollection();
		first.reset();
		second.reset();
		Item firstItem = first.next();
		Item secondItem = second.next();
		while (firstItem != null || secondItem != null) {	// While the end isn't reached on both
			if(firstItem == null) {	// Special case to add from second if first has ended
				result.add(secondItem);
				secondItem = second.next();
			}
			else if (secondItem == null) {	// Special case to add from first if second has ended
				result.add(firstItem);
				firstItem = first.next();
			}
			else if (firstItem.compareTo(secondItem) <= 0) {	// Add from first if its value is less
				result.add(firstItem);
				firstItem = first.next();
			}
			else {	// Add from second if its value is less
				result.add(secondItem);
				secondItem = second.next();
			}		
		}
		return result;
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
		
		if (sortButton != null)
			sortButton.paint(pane);
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
		
		else if (sortButton.isInside(lastX, lastY)) {
			sortAction();
		}

		else  {
			theApplet.showStatus("What?");
		}

		theApplet.repaint();
		//	Handle the mouse click
	}

	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}

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
		else if (sortButton.isInside(lastX, lastY))
			sortButton.flip();
		theApplet.repaint();
	}

}	

