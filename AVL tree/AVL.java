/**
 * Assignment #3 - Tim Gottschalk
 * 
 * COMP 352 Section AA
 * 
 * Programming Question Part 2
 * 
 * A modified version of the bst class from part 1, the node class now has a
 * height attribute and methods have been added for the 4 types of rotations
 * required to maintain an AVL tree. The delete method has been removed as it is
 * not part of this assignment 
 * 
 * @author Tim Gottschalk
 * 
 * 
 * 
 */
class AVL {

	Node root;

	private class Node {

		String keyword;
		Record record;
		int size;
		Node l;
		Node r;
		int height; // The height of the Node.

		private Node(String k) {
			this.keyword = k;
			// Instantialize a new Node with keyword k.
		}

		private void update(Record r) {
			r.next = record;
			this.record = r;
			size++;
			/*
			 * Adds the Record r to the linked list of records. You do not have
			 * to check if the record is already in the list.
			 */
		}

	}

	/**
	 * Initialize AVL tree
	 */
	public AVL() {
		this.root = null;
	}

	/**
	 * Adds record to correct node in AVL tree if it exists, creates new node
	 * and adds to AVL tree if it does not
	 * 
	 * @param keyword
	 *            the keyword to add the record to
	 * @param fd
	 *            The Data of the record to be added
	 */
	public void insert(String keyword, FileData fd) {
		Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
		root = insert(keyword, recordToAdd, root);

	}

	private Node insert(String keyword, Record r, Node n) {
		if (n == null) {
			n = new Node(keyword); // Keyword not found, create new node
			n.update(r);
		} else if (keyword.compareTo(n.keyword) < 0) {
			n.l = insert(keyword, r, n.l);
			if (height(n.l) - height(n.r) >= 2) {
				if (keyword.compareTo(n.l.keyword) < 0) {
					n = rotateL(n); // Rotate with left child
				} else {
					n = rotateL2(n); // Double rotate with left child
				}

			}

		} else if (keyword.compareTo(n.keyword) > 0) {
			n.r = insert(keyword, r, n.r);
			if (height(n.r) - height(n.l) >= 2) {
				if (keyword.compareTo(n.keyword) > 0) {
					n = rotateR(n); // Rotate with right child
				} else {
					n = rotateR2(n); // Double rotate with right child
				}
			}
		} else { // Keyword located, add record to list
			n.update(r);
		}
		n.height = 1 + Integer.max(height(n.l), height(n.r)); // Update n height
		return n;

	}

	private int height(Node n) {
		if (n == null)
			return 0; // Node is empty, return height 0
		else
			return n.height;
	}

	/**
	 * Return true if keyword node exists in AVL tree
	 * 
	 * @param keyword
	 * @return true if keyword exists in AVL tree, false otherwise
	 */
	public boolean contains(String keyword) {
		return contains(keyword, root);

	}

	public boolean contains(String keyword, Node n) {
		if (n == null) { // Keyword not found
			return false;
		} else if (keyword.compareTo(n.keyword) < 0) { // Search left subtree
			return contains(keyword, n.l);
		} else if (keyword.compareTo(n.keyword) > 0) { // Search right subtree
			return contains(keyword, n.r);
		}

		return false;
	}

	public Record get_records(String keyword) {
		Node current = root;
		while (current != null) {
			if (keyword.compareTo(current.keyword) < 0) { // Search left subtree
				current = current.l;
			} else if (keyword.compareTo(current.keyword) > 0) { // Search right
																	// subtree
				current = current.r;
			} else {
				return current.record; // Keyword found, return initial record
			}
		}

		return null; // Keyword not found
	}

	/**
	 * Perform a single rotation with n and its left child to balance the tree
	 * 
	 * @param n
	 *            The unbalanced node requiring rotation
	 * @return the updated subtree
	 */
	private Node rotateL(Node n) { // Perform Single rotation with left child of
									// n
		Node toRotate = n.l;
		n.l = toRotate.r;
		toRotate.r = n;
		n.height = 1 + Integer.max(height(n.l), height(n.r)); // Update heights
		toRotate.height = 1 + Integer.max(height(toRotate.l), n.height);
		return toRotate;

	}

	/**
	 * Perform a single rotation with n and its right child to balance the tree
	 * 
	 * @param n
	 *            The unbalanced node requiring rotation
	 * @return the updated subtree
	 */
	private Node rotateR(Node n) {
		Node toRotate = n.r;
		n.r = toRotate.l;
		toRotate.l = n;
		n.height = 1 + Integer.max(height(n.l), height(n.r)); // Update heights
		toRotate.height = 1 + Integer.max(height(toRotate.r), n.height);
		return toRotate;

	}

	/**
	 * Perform a double rotation with n and its right child to balance the tree
	 * 
	 * @param n
	 *            The unbalanced node requiring rotation
	 * @return the updated subtree
	 */
	private Node rotateR2(Node n) {
		n.r = rotateL(n.r);
		return rotateR(n);

	}

	/**
	 * Perform a double rotation with n and its left child to balance the tree
	 * 
	 * @param n
	 *            The unbalanced node requiring rotation
	 * @return the updated subtree
	 */
	private Node rotateL2(Node n) {
		n.l = rotateR(n.l);
		return rotateL(n);

	}

	/**
	 * Returns the lowest ranked node in the subtree n
	 * 
	 * @param n
	 *            the subtree to be evaluated
	 * @return the lowest ranked node in n
	 */
	public Node min(Node n) {
		while (n.l != null) {
			n = n.l;
		}
		return n;
	}

	public void print() {
		print(root);
	}

	private void print(Node t) {
		if (t != null) {
			print(t.l);
			System.out.println(t.keyword);
			Record r = t.record;
			while (r != null) {
				System.out.printf("\t%s\n", r.title);
				r = r.next;
			}
			print(t.r);
		}
	}

}
