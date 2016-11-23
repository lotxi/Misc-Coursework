
/**
 * Assignment #2 - Tim Gottschalk
 * 
 * COMP 352 Section AA
 * 
 * Programming Question Part v
 * 
 * An array-based implementation of a Positional NodeList ADT. Builds a
 * dynamically sized array of PNode objects which contain an element (integer)
 * and a position (integer) which matches their index. Maintains pointers for
 * last and first nodes and allows traversal using prev() and next() methods.
 * 
 * @author Tim Gottschalk
 * 
 * 
 * 
 */
public class NodeList {
	private PNode first; // Points to the node in position 0, null if empty
	private PNode last; // Points to the last node in the list (position
						// size-1), null
	// if empty
	private PNode[] array; // Array containing the PNodes of the list
	private int size; // The number of PNodes in the NodeList
	long expansions; // The number of times the array has been expanded (for
						// testing purposes only)
	private boolean doubleExpansion; // The setting for the current expansion
										// rule: see setExpansionRule()

	public NodeList() {
		first = null;
		last = null;
		array = new PNode[1];
		doubleExpansion = true;
		size = 0;
		expansions = 0;
	}

	/**
	 * @return The first PNode in the NodeList
	 */
	public PNode first() {
		if (size == 0) {
			System.out.println("Cannot return first: List is empty");
			return new PNode(-1, -1);
		}
		return first;
	}

	/**
	 * @return The last PNode in the NodeList
	 */
	public PNode last() {
		if (size == 0) {
			System.out.println("Cannot return last: List is empty");
			return new PNode(-1, -1);
		}
		return last;

	}

	/**
	 * Check if the number of nodes in the list is more than 80% of the capacity
	 * of the underlying array, and expand the array if so
	 * 
	 * @return the size of the nodelist
	 */
	private int checkSize() {
		if (size >= 0.8 * array.length) {
			expand();
		}
		return size;

	}

	/**
	 * Expand the array based on the selected expansion rule. If the rule is set
	 * to true, expand the array by doubling the size. If the rule is set to
	 * false, expand the array by 10 entries
	 */
	private void expand() {
		PNode[] temp;
		if (doubleExpansion == true) {
			temp = new PNode[array.length * 2];
			for (int i = 0; i <= last.position; i++) {
				temp[i] = array[i];
			}
		} else {
			temp = new PNode[array.length + 10];
			for (int i = 0; i <= last.position; i++) {
				temp[i] = array[i];
			}
		}
		expansions++;
		array = temp;

	}

	/**
	 * Set the rule for expansion of the underlying array.
	 * 
	 * @param c
	 *            the selected expansion rule, 'c' for constant, 'd' for double
	 */
	public void setExpansionRule(char c) {
		if (c == 'c') {
			doubleExpansion = false;
		} else if (c == 'd') {
			doubleExpansion = true;
		} else {
			System.out.println("Unable to set expansion rule: invalid entry.");
		}
	}

	/**
	 * Set the element of PNode p to e and return the element previously stored
	 * in p
	 * 
	 * @param p
	 *            The PNode to modify
	 * @param e
	 *            The element to apply to PNode p
	 * @return the element previously stored in PNode p
	 */
	public int set(PNode p, int e) {

		int oldElement = p.getElement();
		p.setElement(e);
		return oldElement;
	}

	/**
	 * Insert a new node into the list before the specified PNode and with the
	 * specified element. Shift all PNodes later in the list up by 1 index and
	 * update their positions
	 * 
	 * 
	 * @param p
	 *            The PNode to insert a new node before
	 * @param e
	 *            The element to store in the new PNode
	 * @return the newly created PNode
	 */
	public PNode addBefore(PNode p, int e) {
		checkSize();
		int pos = p.getPosition();
		if (pos == 0) {
			return addFirst(e);
		} else {
			if (size > 0) {
				int index = last.position;
				while (index >= pos) {
					array[index + 1] = array[index];
					array[index + 1].setPosition(index + 1);
					index--;
				}
			}
		}
		array[pos] = new PNode(e, pos);
		size++;
		return array[pos];
	}

	/**
	 * Insert a new node into the list after the specified PNode and with the
	 * specified element. Shift all PNodes later in the list up by 1 index and
	 * update their positions
	 * 
	 * @param p
	 *            The PNode to insert a new node after
	 * @param e
	 *            The element to store in the new PNode
	 * @return the newly created PNode
	 */
	public PNode addAfter(PNode p, int e) {
		checkSize();
		int pos = p.getPosition();
		if (pos == size - 1) {
			return addLast(e);
		} else {
			int index = last.getPosition();
			while (index > pos) {
				array[index + 1] = array[index];
				array[index + 1].setPosition(index + 1);
				index--;
			}
			array[pos + 1] = new PNode(e, pos + 1);
			size++;
			return array[pos + 1];

		}
	}

	/**
	 * Add a new PNode at the end of the NodeList with element e.
	 * 
	 * @param e
	 *            The element to store in the PNode
	 * @return The newly created PNode
	 */
	public PNode addLast(int e) {
		checkSize(); // Resize the array if needed
		int index = size;
		array[index] = new PNode(e, index);
		size++;
		last = array[index];
		if (size == 1) {
			first = last;
		}
		return last;

	}

	/**
	 * Add a new PNode at the beginning of the NodeList with element e. Shift
	 * all other PNodes up by 1 index and update their positions as needed
	 * 
	 * @param e
	 *            the element to store in the PNode
	 * @return the newly created PNode
	 */
	public PNode addFirst(int e) {
		checkSize();
		if (size > 0) {
			for (int i = last.position; i >= 0; i--) {
				array[i + 1] = array[i];
				array[i + 1].setPosition(i + 1);
			}
		}
		array[0] = new PNode(e, 0);
		first = array[0];
		if (size == 1) {
			last = first;
		}
		size++;
		return array[0];
	}

	/**
	 * Remove a PNode from the list. Shift all PNodes later in the list one
	 * space to the left and update their position attributes
	 * 
	 * @param p
	 *            re
	 * @return
	 */
	public int delete(PNode p) {
		if (size == 0) {
			System.out.println("Unable to delete: List is empty.");
			return -1;
		}

		int temp = p.getElement();
		int index = p.getPosition();
		while (index < size - 1) {
			array[index] = array[index + 1];
			array[index].setPosition(index);
			index++;
		}
		array[size - 1] = null;
		size--;
		if (size == 0) {
			first = null;
			last = null;
		} else {
			first = array[0];
			last = array[size - 1];
		}
		return temp;
	}

	public void swap(PNode p1, PNode p2) {
		int temp = p1.getElement();
		p1.setElement(p2.getElement());
		p2.setElement(temp);

	}

	/**
	 * Return the PNode next in the list after PNode p. If p is the last node in
	 * the list, display a message and return a special error node
	 * 
	 * @param p
	 *            the PNode directly before the one to be returned
	 * @return the PNode after p
	 */
	public PNode next(PNode p) {
		// Display an error and return a special node if the next node does not
		// exist. This was added to avoid a Null Pointer Exception because
		// exception handling is not within the scope of this assignment
		if (p.position == size - 1) {
			System.out.println("Unable to fetch next: Does not exist");
			return new PNode(-1, -1);
		}
		return array[p.position + 1];
	}

	/**
	 * Return the previous PNode in the list before PNode p. If p is the first
	 * node in the list, display a message and return a special error node
	 * 
	 * @param p
	 *            the PNode directly after the one to be returned
	 * @return the PNode before p
	 */
	public PNode prev(PNode p) {
		// Display an error and return a special node if the previous node does
		// not
		// exist. This was added to avoid a Null Pointer Exception because
		// exception handling is not within the scope of this assignment
		if (p.getPosition() <= 0) {
			System.out.println("Unable to fetch previous: Does not exist");
			return new PNode(-1, -1);
		}
		return array[p.position - 1];
	}

	/**
	 * Reduce the size of the underlying array to exactly match the size of the
	 * NodeList by creating a new array of the needed size and transferring all
	 * elements
	 */
	public void truncate() {
		if (array.length > size) {
			PNode[] truncatedArray = new PNode[size];
			for (int i = 0; i < size; i++) {
				truncatedArray[i] = array[i];
			}
			array = truncatedArray;
		}

	}

	/**
	 * Print the full contents of the NodeList. For testing purposes
	 */
	public void printList() {

		if (size == 0) {
			System.out.println("Nothing to display: List is empty");
		} else {
			System.out.println("---------------------------------------");
			System.out.println("NodeList size is " + size);
			System.out.println("NodeList Contents: \n");
			for (int i = 0; i < size; i++) {

				System.out.println("Position:" + i + ", Element: " + array[i].getElement());

			}

		}
	}

	/**
	 * @return the number of PNodes in the NodeList
	 */
	public int getSize() {
		return size;

	}

	/**
	 * @return the number of times array has been expanded
	 */
	public long getExpansions() {
		return expansions;
	}

	/**
	 * The PNode inner class stores an element (in this case, an integer) and a
	 * position which matches the node's index in the underlying array. It
	 * includes getters and setters to access and modify these attributes when
	 * we manipulate the NodeList
	 * 
	 * @author Tim Gottschalk
	 *
	 */
	public class PNode {
		private int element;
		private int position;

		public PNode(int element, int position) {
			this.element = element;
			this.position = position;
		}

		public int getElement() {
			return element;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public void setElement(int element) {
			this.element = element;
		}

		public int getPosition() {
			return position;
		}

		public String toString() {
			return "Position " + getPosition() + " storing element " + getElement();
		}

	}
	
	

}
