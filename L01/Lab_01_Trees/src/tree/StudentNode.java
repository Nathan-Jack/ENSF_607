/**
 * AVL/BST TREE node class
 * 
 * @author Nathan Jack
 * @version 1.1
 * @since Sept 23, 2020
 * 
 * Sources:
 * 	Code base from D2L/Dr. Moshipour
 *  Refactored as required to implement AVL Tree insert and rebalancing
 */

package tree;

public class StudentNode {

	private int id; // key
	private int height; // added for avl tree implementation
	private String name;
	private StudentNode left, right;

	public StudentNode(String name, int id) {

		setName(name);
		setId(id);
		setLeft(null);
		setRight(null);
	}

	@Override
	public String toString() {
		String left;
		String right;
		try {
			left = this.getLeft().getName();
		}  catch (NullPointerException e) {
			left = "null";
		}
		
		try {
			right = this.getRight().getName();
		}  catch (NullPointerException e) {
			right = "null";
		}
		
		return "StudentNode{id="+ this.getId() + ", height="+ this.getHeight() +", name="+ this.getName()+ ", left=" + left + ", right=" + right + "}";
		//return name + ", " + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StudentNode getLeft() {
		return left;
	}

	public void setLeft(StudentNode left) {
		this.left = left;
	}

	public StudentNode getRight() {
		return right;
	}

	public void setRight(StudentNode right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
