import java.awt.*;							//	AWT = "Abstract Window Toolkit"

/**
 * A DataCollection implemented using an array.
 * @author nickie
 *
 */
public class ArrayDataCollection implements DataCollection
{
	/**
	 * Holds the items
	 */
	private Item [] theItems;		

	/**
	 * The number of Items currently in the collection
	 */
	private int size;

	/**
	 * The lower left point of the first Item in the collection
	 */
	private int x, y;	

	/**
	 * The index where the selected Item is.
	 * If no Item is selected, selected is set to -1.
	 */
	private int selected;					

	/**
	 * Sets initial capacity of array to 15,
	 * none are in collection, none are selected.
	 */
	public ArrayDataCollection()
	{										//	Instantiate an array of a
		theItems = new Item[15];			//	beginning default size.
		size = 0;							//	There are no items yet
		selected = -1;						//		and no selected item either

	}

	/**
	 * Sets first Item in collection to coordinates passed.
	 * No Items are in the collection, none are selected.
	 * @param someX left
	 * @param someY bottom
	 */
	public ArrayDataCollection(int someX, int someY){
		this();
		x = someX;
		y = someY;
	}

	/**
	 * Updates which Item is selected, including unhighlighting the
	 * previously selected Item and highlighting the newly selected
	 * Item. If no Item is selected, selected is set to -1.
	 */
	private void changeSelected(int newSelected){
		if(selected != -1){
			theItems[selected].highlight(false);
		}
		if(newSelected != -1 && newSelected < size){
			theItems[newSelected].highlight(true);
			selected =  newSelected;
		}else
			selected = -1;
	}

	/**
	 * Resets the selected item to the beginning
	 * of the collection.
	 */
	public void reset()
	{
		changeSelected(0);					//	We go back to the beginning

	}

	//
	//	r e s e t
	//	=========
	//
	//	Defines the selected item to be the given item
	//		(if it is part of the collection).
	//
	public void reset(Item someItem)
	{
		// Start out with nothing selected
		changeSelected(-1);		

		//If someItem is null, we're done. Otherwise, find 
		//someItem in the collection (if it's there).
		if(someItem != null){
			int i = 0;

			//Stop the loop when we find someItem
			while ( (selected == -1) && (i < size) ) {
				if ( theItems[i] == someItem )
					changeSelected(i);
				i++;
			}
		}	
	}

	/**
	 * Adds the given Item to the collection.
	 * That item becomes the item currently selected.
	 */
	public void add(Item someItem)
	{
		if(size==theItems.length){
			Item[] temp = new Item[theItems.length * 2];
			for(int i = 0; i< theItems.length; i++)
				temp[i] = theItems[i];
			theItems = temp;
		}
		//set the location of the item to follow the previous
		someItem.setLocation(x + size * Item.OVERALL_WIDTH, y);
		theItems[size] = someItem;		//		Set the new element as		
		changeSelected(size++);				//	selected
	}

	/**
	 * Paints all items in the collection
	 * Items are painted from left to right
	 * in the order in which they were added.
	 */
	public void paint(Graphics pane)
	{
		for(int i = 0; i<size; i++)
			theItems[i].paint(pane);
	}

	/**
	 * Determines whether there is a selected item
	 * (in other words, it returns true if an invocation
	 * to the next method would return an Item).
	 */
	public boolean hasNext()
	{
		return (selected > -1);
	}

	/**
	 * Returns the selected Item (if any Item is selected) and
	 * sets the next one (in the order in which the Items were
	 * added) as the selected Item. If there are no Items that
	 * were added after the selected Item, then after next()
	 * no Item will be selected.
	 * @return selected Item or null, if no Item is selected
	 */
	public Item next()
	{
		Item result = null;

		//If an Item is selected, save that Item
		//and advance to the next one in the collection
		if (selected > -1){
			result = theItems[selected];
			changeSelected(selected + 1);
		}
		return result;
	}

	/**
	 * Removes the selected item (if any).
	 * No item is selected any more.
	 */
	public void remove()
	{
		if (selected > -1) {
			for (int i = selected ; i < size-1; i++){
				//move it down in the collection
				theItems[i] = theItems[i+1];
				//and move its location one to the left
				theItems[i].setX(theItems[i].getX()-Item.OVERALL_WIDTH);
			}
			size--;
			
			//We don't need to unhighlight the removed Item
			//or highlight any new Item, so just set selected
			//directly.
			selected = -1;
		}
	}






}	// end DataCollection
