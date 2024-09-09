import java.util.Date;

public class BST {

	private BSTNode root;
	public int number_of_dates = 0;

	public BST() {
		this.number_of_dates = 0;
		this.root = null;
	}

//...................................................................................................................

	public void add(Martyr a) {
		root = add(a, root);
	}

	// note:for compare to <=0 means that it is smaller or equal

	private BSTNode add(Martyr a, BSTNode current) { // this is called a utility method.
		if (current == null) { // I found an empty place to put the node in
			current = new BSTNode(a);
			number_of_dates++;
		} else if ((a).compareTo(current.getData()) <= 0) // if the value I want to add is less than or equal the
															// current node then that node would be in the left side
			current.setLeft(add(a, current.getLeft()));
		else if ((a).compareTo(current.getData()) > 0)// if the value I want to add is greater than the current node
														// then that node would be in the right side
			current.setRight(add(a, current.getRight()));

		return current;
	}

//..............................................................................................................	

	public void remove(Martyr a) {
		root = remove(a, root);
	}

	private BSTNode remove(Martyr a, BSTNode current) {
		if (current == null)
			return null; // this means that the BST is empty ,, value does not exist in the system
		if ((a).compareTo(current.getData()) > 0)
			current.setRight(remove(a, current.getRight()));
		else if ((a).compareTo(current.getData()) < 0)
			current.setLeft(remove(a, current.getLeft()));
		else { // the value Iam serching for to delete exists in the system
			if (current.getLeft() != null && current.getRight() != null) { // the node we want to delete has left and
																			// right
				current.setData(findMin(current.getRight()).getData());
				current.setRight(remove(a, current.getRight()));
			} else { // the node we want to delete has zero or one child
				if (current.getLeft() != null) {
					current = current.getLeft();
					number_of_dates--;
				} else {
					current = current.getRight();
					number_of_dates--;
				}

			}
		}

		return current;
	}

//...............................................................................................................

	public Martyr kthLargestElement(int k) {
		counter = 0;
		result = new Martyr();
		try {
			kthLargestElement(root, k);
		} catch (NullPointerException ex) {
			System.out.println("empty tree");
		}
		return result;
	}

	private void kthLargestElement(BSTNode current, int k) {
		if (current == null)
			return;
		else {
			kthLargestElement(current.getRight(), k);
			counter++;
			if (counter == k) {
				result = current.getData();
				return;
			}
			kthLargestElement(current.getLeft(), k);
		}
	}

//.................................................................................................................

	private Martyr result = new Martyr();
	private int counter = 0;

	public Martyr kthSmallestElement(int k) {
		counter = 0;
		result = new Martyr();
		try {
			kthSmallestElement(root, k);
		} catch (NullPointerException ex) {
			System.out.println("empty tree");
		}
		return result;
	}

	private void kthSmallestElement(BSTNode current, int k) {
		if (current == null)
			return;
		else {
			kthSmallestElement(current.getLeft(), k);
			counter++;
			if (counter == k) {
				result = current.getData();
				return;
			}
			kthSmallestElement(current.getRight(), k);
		}
	}

//...............................................................................................................

	public BSTNode findMax() {
		if (root == null)
			return null;
		return findMax(root);
	}

	private BSTNode findMax(BSTNode current) {
		if (current.getRight() == null) // if there is no right child then the current node itself is the maximum
										// element in the BST
			return current;
		else {
			return findMax(current.getRight());
		}

	}

//..............................................................................................................

	public BSTNode findMin() {
		if (root == null)
			return null;
		return findMin(root);
	}

	private BSTNode findMin(BSTNode current) {
		if (current.getLeft() == null) // if there is no right child then the current node itself is the maximum
										// element in the BST
			return current;
		else {
			return findMin(current.getLeft());
		}

	}

//..............................................................................................................

	public Martyr findMaxValue() {
		if (root == null)
			return null;
		return findMaxValue(root).getData();
	}

	private BSTNode findMaxValue(BSTNode current) {
		if (current.getLeft() == null) // if there is no right child then the current node itself is the maximum
										// element in the BST
			return current;
		else {
			return findMaxValue(current.getLeft());
		}

	}

//..............................................................................................................	

	public boolean contains(Martyr a) {
		if (root == null) {
			return false;
		}
		return contains(a, root);
	}

	private boolean contains(Martyr a, BSTNode current) {
		if (current == null)
			return false;
		if ((a).compareTo(current.getData()) < 0) { // if the passed value by the method's parameter is less than the
													// current's value
			return contains(a, current.getLeft());
		} else if ((a).compareTo(current.getData()) > 0)
			return contains(a, current.getRight());

		return true;
	}

//.............................................................................................................

	public BSTNode find(Martyr a) {
		return find(a, root);
	}

	private BSTNode find(Martyr a, BSTNode current) {
		if (current.getData().equals(a)) // if the current's value equals the passed value by the method's parameter I
											// will return the node of that value
			return current;
		else if ((a).compareTo(current.getData()) <= 0) { // if the passed value by the method's parameter is less than
															// the current's value
			return find(a, current.getLeft());
		} else if ((a).compareTo(current.getData()) > 0)
			return find(a, current.getRight());

		return null;
	}

//..............................................................................................................

	public void printInorder() {
		printInorder(root);
		System.out.println();
	}

	private void printInorder(BSTNode node) {
		if (node == null) // this is the base case....
			return;

		
		printInorder(node.getLeft());
		System.out.print(node.getData() + " ,, ");
		node.getMartyrs().print();

		printInorder(node.getRight());
	}

//..................................................................................................................	
	int count = 0;

	public int getNumberOfMartyrsInsideDate() {
		count = 0;
		getNumberOfMartyrsInsideDate(root);
		return count;
	}

	private void getNumberOfMartyrsInsideDate(BSTNode current) {
		if (current == null)
			return;

		getNumberOfMartyrsInsideDate(current.getLeft());
		count = count + current.getMartyrs().getSize();
		getNumberOfMartyrsInsideDate(current.getRight());
	}

//...................................................................................................................	
	public BSTNode getRoot() {
		return root;
	}

	public void setRoot(BSTNode root) {
		this.root = root;
	}

}