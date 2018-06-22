import java.awt.Graphics;							//	AWT = "Abstract Window Toolkit"

/**
 * This interface allows items to be added
 * and removed from a collection of items.
 * It also allows traversing the collection one item at a time,
 * selecting each item in turn.
 * This traversal defines an implicit ordering of the items in
 * the collection, imposed by the order in which the items
 * were added to the collection.
 * @author nickie
 *
 */
public interface DataCollection
{
	/**
	 * By default, the reset method resets the selected item to the beginning
	 * of the collection.
	 */
	public void reset();
	
	/**
	 * Defines the selected item to be the given item
	 * (if it is part of the collection).
	 */
	public void reset(Item someItem);

	/**
	 * Adds the given Item to the collection.
	 * That item becomes the item currently selected.
	 */
	public void add(Item someItem);

	/**
	 * Paints all items in the collection
	 * Items are painted from left to right
	 * in the order in which they were added.
	 */
	public void paint(Graphics pane);

	/**
	 * Determines whether there is a selected item
	 * (in other words, it returns true if an invocation
	 * to the next method would return an Item).
	 */
	public boolean hasNext();

	/**
	 * Returns the selected Item (if any Item is selected) and
	 * sets the next one (in the order in which the Items were
	 * added) as the selected Item. If there are no Items that
	 * were added after the selected Item, then after next()
	 * no Item will be selected.
	 * @return selected Item or null, if no Item is selected
	 */
	public Item next();

	/**
	 * Removes the selected item (if any).
	 * No item is selected any more.
	 */
	public void remove();

}
