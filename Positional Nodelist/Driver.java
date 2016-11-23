
/**
 * Assignment #2 - Tim Gottschalk
 * 
 * COMP 352 Section AA
 * 
 * Programming Question Part vi, vii
 * 
 * A series of test-cases to verify that each method for interacting with the
 * NodeList class is functional. In addition, a series of tests to compare the
 * times to build a large NodeList using different expansion rules
 * 
 * @author Tim Gottschalk
 * 
 * 
 * 
 */

public class Driver {

	public static void main(String[] args) {
		System.out.println("");
		// Populate a Nodelist with random integers to test methods.
		NodeList test = new NodeList();
		System.out.println("Populating Test Array...");
		for (int i = 0; i < 10; i++) {
			test.addLast((int) (Math.random() * 100));
		}
		test.printList();

		// Test each of the methods for interacting with the NodeList
		System.out.println("\n---------------------------------------");
		System.out.println("Begin Method Tests");
		System.out.println("---------------------------------------");
		System.out.println("\nfirst(): points to " + test.first());
		System.out.println("\nLast(): points to " + test.last());
		System.out.println("\nnext(test.first()): points to " + test.next(test.first()));
		System.out.println("\nprev(test.last()): points to " + test.prev(test.last()));
		System.out.println("\nset(test.first(),29): returns " + test.set(test.first(), 29)
				+ " and causes first() to return " + test.first());
		System.out.println("\naddFirst(45): returns " + test.addFirst(45));
		System.out.println("\naddLast(56): returns " + test.addLast(56));
		System.out.println("\naddBefore(test.last(),33): returns " + test.addBefore(test.last(), 33));
		System.out.println("\naddAfter(test.last(),72): returns " + test.addAfter(test.last(), 72));
		System.out.println("\ndelete(test.first()): returns " + test.delete(test.first())
				+ " and causes first() to return " + test.first());
		test.swap(test.first(), test.last());
		System.out.println("\nswap(test.first(),test.last()): causes first() to return " + test.first()
				+ " and last() to return " + test.last());
		test.truncate();
		System.out.println("\ntruncate(): underlying array has been truncated to " + test.getSize());

		// Print out the results of the above modifications
		test.printList();

		// Begin test of expansion rule processing time
		System.out.println("\n\n---------------------------------------");
		System.out.println("Begin Expansion Tests");
		System.out.println("---------------------------------------");

		System.out.println("\nn=10");
		System.out.print("\nDoubling: ");
		expansionTest('d', 10);
		System.out.print("\nConstant: ");
		expansionTest('c', 10);

		System.out.println("---------------------------------------");
		System.out.println("n=100");
		System.out.print("\nDoubling: ");
		expansionTest('d', 100);
		System.out.print("\nConstant: ");
		expansionTest('c', 100);

		System.out.println("---------------------------------------");
		System.out.println("n=1000");
		System.out.print("\nDoubling: ");
		expansionTest('d', 1000);
		System.out.print("\nConstant: ");
		expansionTest('c', 1000);

		System.out.println("---------------------------------------");
		System.out.println("n=10000");
		System.out.print("\nDoubling: ");
		expansionTest('d', 10000);
		System.out.print("\nConstant: ");
		expansionTest('c', 10000);

		System.out.println("---------------------------------------");
		System.out.println("n=100000");
		System.out.print("\nDoubling: ");
		expansionTest('d', 100000);
		System.out.print("\nConstant: ");
		expansionTest('c', 100000);

		System.out.println("---------------------------------------");
		System.out.println("n=1000000");
		System.out.print("\nDoubling: ");
		expansionTest('d', 1000000);
		System.out.print("\nConstant: ");
		expansionTest('c', 1000000);

	}

	/**
	 * Create a nodelist of the specified size using the specified expansion
	 * rule in order to test the time needed for different expansion rules
	 * 
	 * @param extype
	 *            the type of expansion rule, 'c' for constant or 'd' for
	 *            doubling
	 * @param n
	 *            the number of nodes to place in the nodelist
	 */
	private static void expansionTest(char extype, int n) {
		NodeList exTest = new NodeList();
		exTest.setExpansionRule(extype);
		long startTime, endTime, elapsed;
		startTime = System.nanoTime();
		for (int i = 0; i <= n; i++) {
			exTest.addLast(0);
		}
		endTime = System.nanoTime();
		elapsed = endTime - startTime;
		System.out.print(elapsed + "ns");
		System.out.println("\nExpansions: " + exTest.expansions);

	}

}
