/**
 * AVL/BST TREE driver class
 * 
 * @author Nathan Jack
 * @version 1.1
 * @since Sept 23, 2020
 * 
 * Sources:
 * 	Code base from D2L/Dr. Moshipour
 */

package tree;

import java.util.Arrays;

public class TreeApp {
	public static void main(String[] args) {

		System.out.println("Tree App");

		StudentTree myTree = new StudentTree();

		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("Sarah", 10)));
		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("Bob", 5)));
		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("Sam", 2)));
		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("Joe", 27)));

		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("a", 17)));
		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("b", 12)));
		myTree.setRoot(myTree.insert(myTree.getRoot(), new StudentNode("c", 20)));

		myTree.printPreOrder();

		System.out.println("Searching for things... \n");
		int val = 0;
		try {
			for (int i : Arrays.asList(10, 2, 17, 11)) {
				// updating val so the catch function knows what value triggered the null
				// pointer exception
				val = i;
				System.out.println(myTree.search(myTree.getRoot(), i).getId() + " exists in the Tree");
			}
		} catch (NullPointerException e) {
			System.out.println(val + " does not exist in the tree");
		}

		// Testing non AVL delete. Works on root and with invalid keys
		int[] del = { 10, 11, 17 };

		for (int i = 0; i < 3; i++) {
			System.out.println("\nDeleting " + del[i] + " ... \n");

			myTree.setRoot(myTree.delete(myTree.getRoot(), del[i]));

			myTree.printPreOrder();
		}
	}

}
