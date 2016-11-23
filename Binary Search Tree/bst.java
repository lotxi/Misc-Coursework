/**
 * Assignment #2 - Tim Gottschalk
 * 
 * COMP 352 Section AA
 * 
 * Programming Question Part 1
 * 
 * Binary search tree implementation
 * 
 * @author Tim Gottschalk
 * 
 * 
 * 
 */

class bst {

	Node root;

	private class Node {

		String keyword;
		Record record;
		int size;
		Node l;
		Node r;

		private Node(String k) {
			this.keyword = k;
		}

		private void update(Record r) {
			r.next = record;
			this.record = r;
			size++;

		}

	}

	public bst() {
		this.root = null;
	}

	public void insert(String keyword, FileData fd) {
		Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
		root = insert(keyword, recordToAdd, root);

	}

	private Node insert(String keyword, Record r, Node n) {
		if (n == null) { // keyword not found, create new node.
			n = new Node(keyword);
			n.update(r); // Add record to node
		} else if (keyword.compareTo(n.keyword) < 0) {
			n.l = insert(keyword, r, n.l); // Search left subtree
		} else if (keyword.compareTo(n.keyword) > 0) {
			n.r = insert(keyword, r, n.r); // Search right subtree
		} else {
			n.update(r); // Keyword found, add record to list
		}
		return n;

	}

	public boolean contains(String keyword) {
		return contains(keyword, root); // Call recursive function
	}

	public boolean contains(String keyword, Node n) {
		if (n == null) { // Keyword does not exist in bst
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
			if (keyword.compareTo(current.keyword) < 0) {
				current = current.l; // Search left subtree
			} else if (keyword.compareTo(current.keyword) > 0) {
				current = current.r; // Search right subtree
			} else {
				return current.record; // Keyword found
			}
		}

		return null; // Keyword not found.
	}

	public void delete(String keyword) {
		root = delete(keyword, root); // Call recursive function
	}

	/** Delete a keyword from bst rooted at n if it exists
	 * @param keyword The keyword to remove from the bst
	 * @param n the tree from which to delete 
	 * @return n with keyword removed
	 */
	private Node delete(String keyword, Node n) {
		if (n == null) { // Keyword not found: nothing to delete
		} else if (keyword.compareTo(n.keyword) < 0) {
			n.l = delete(keyword, n.l); // Search left subtree
		} else if (keyword.compareTo(n.keyword) > 0) {
			n.r = delete(keyword, n.r); // Search right subtree
		} else {
			if (n.r == null) { // No right child.
				n = n.l;
			} else { // Node n has a right child
				Node replacement = min(n.r);
				n.keyword = replacement.keyword;
				n.record = replacement.record;
				n.r = delete(replacement.keyword, n.r);
			}

		}
		return n;
	}

	/**
	 * Returns the lowest ordered element in a subtree
	 * 
	 * @param n
	 *            The root of the subtree to search
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
