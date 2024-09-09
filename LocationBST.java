public class LocationBST {

	private LBSTNode root;
	private int number_of_locations;
		
	public LocationBST() {
		this.number_of_locations=0;
		this.root=null;
	}
	
//...................................................................................................................
	
	public int getNumberOfLocations() {
		return number_of_locations;
	}
	
//...................................................................................................................
	
	public void add(String a) {
		root=add(a,root);
	}
	
	//note: <=0 means that it is smaller or equal
	
	private LBSTNode add(String a , LBSTNode current) {  // this is called a utility method.
		if(current==null) {  // I found an empty place to put the node in
			current =new LBSTNode(a);
			number_of_locations++;
		}
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) <=0 ) // if the value I want to add is less than or equal the current node then that node would be in the left side
			current.setLeft(add(a,current.getLeft()));
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) > 0 )// if the value I want to add is greater than the current node then that node would be in the right side
			current.setRight(add(a,current.getRight()));
		
		return current;
	}
	
//..............................................................................................................	

	public void remove(String a) {
		root= remove(a,root);
	}
	
	private LBSTNode remove(String a, LBSTNode current) {
		if(current==null) return null; // this means that the BST is empty ,, value does not exist in the system
		if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) > 0 )
			current.setRight(remove(a,current.getRight()));
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) < 0 )
			current.setLeft(remove(a,current.getLeft()));
		else {  //the value Iam serching for to delete exists in the system
			if(current.getLeft()!=null && current.getRight()!=null) { //the node we want to delete has left and right
				current.setLocationName(findMin(current.getRight()).getLocationName());
				current.setRight(remove(a,current.getRight()));
			}
			else {  //the node we want to delete has zero or one child 
				if(current.getLeft()!=null) {
					current=current.getLeft();
					number_of_locations--;
				}
				else {
					current=current.getRight();
					number_of_locations--;
				}	
				
			}
		}
		
		return current;
	}
	
//...............................................................................................................
	

	
	public String kthLargestElement(int k) {
		counter=0;
		result="";
			try {
				kthLargestElement(root,k);
			}catch(NullPointerException ex) {
				System.out.println("empty tree");
			}
		return result;
	}
	
	private void kthLargestElement(LBSTNode current, int k) {
		if(current==null) return;
		else {
			kthLargestElement(current.getRight(),k);
			 counter++;			
			if(counter==k) {
				result = current.getLocationName(); 
				return; 
			}
			kthLargestElement(current.getLeft(),k);
		}
	}
	
//.................................................................................................................
	
	private String result="";
	private int counter=0;
	
	public String kthSmallestElement(int k) {
		counter=0;
		result="";
			try {
				kthSmallestElement(root,k);
			}catch(NullPointerException ex) {
				System.out.println("empty tree");
			}
		return result;
	}
	
	private void kthSmallestElement(LBSTNode current, int k) {
		if(current==null) return;
		else {
			 kthSmallestElement(current.getLeft(),k);
			 counter++;			
			if(counter==k) {
				result = current.getLocationName(); 
				return; 
			}
			 kthSmallestElement(current.getRight(),k);
		}
	}
	
	
//...............................................................................................................
	
	public LBSTNode findMax() {
		return findMax(root);
	}
	
	private LBSTNode findMax(LBSTNode current) {
		if(current.getRight()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMax(current.getRight());  
		}
		
	}
	
//..............................................................................................................
	
	public LBSTNode findMin() {
		return findMin(root);
	}
	
	private LBSTNode findMin(LBSTNode current) {
		if(current.getLeft()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMin(current.getLeft());  
		}
		
	}
	
//..............................................................................................................
	
	public String findMaxValue() {
		return findMaxValue(root).getLocationName();
	}
	
	private LBSTNode findMaxValue(LBSTNode current) {
		if(current.getLeft()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMaxValue(current.getLeft());  
		}
		
	}

//..............................................................................................................	
	
	public boolean contains(String a) {
		if(root==null) return false;
		return contains(a,root);
	}
	
	private boolean contains(String a , LBSTNode current) {
		if(current==null) return false;
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return contains(a,current.getLeft());
		}
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) >0 )
			return contains(a,current.getRight());
		
		return true;
	}
	
//.............................................................................................................
	
	public LBSTNode find(String a) {
		return find(a,root);
	}
	
	private LBSTNode find(String a , LBSTNode current) {
		if(current.getLocationName().equalsIgnoreCase(a))  //if the current's value equals the passed value by the method's parameter I will return the node of that value
			return current;
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return find(a,current.getLeft());
		}
		else if((a.toLowerCase()).compareTo(current.getLocationName().toLowerCase() ) >0 )
			return find(a,current.getRight());
		
		return null;
	}
	
//..............................................................................................................
	
	public void printInorder() {
		printInorder(root);
		System.out.println();
	}
	
	private void printInorder(LBSTNode node)
    {
        if (node == null)  //this is the base case....
            return;
 
        printInorder(node.getLeft());
        System.out.print(node.getLocationName() + " ,, ");
        node.getDateBST().printInorder();
        
        printInorder(node.getRight());
    }

//..................................................................................................................
	int count=0;
	public int getNumberOfMartyrsInsideLocation() {
		count=0;
		getNumberOfMartyrsInsideLocation(root);
		return count;
	}
	private void getNumberOfMartyrsInsideLocation(LBSTNode current) {
		if(current==null) return ;
		
		getNumberOfMartyrsInsideLocation(current.getLeft());
		count=count+current.getDateBST().getNumberOfMartyrsInsideDate();
		getNumberOfMartyrsInsideLocation(current.getRight());
	}
	
//..................................................................................................................
	
	
	public LBSTNode getRoot() {
		return root;
	}

	public void setRoot(LBSTNode root) {
		this.root = root;
	}
	
}