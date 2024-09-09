import java.util.Date;

public class DateBST {

	private DateNode root;
	public int number_of_dates=0;
	
	public DateBST() {
		this.number_of_dates=0;
		this.root=null;
	}
	
	
//...................................................................................................................
	
	public void add(Date a) {
		root=add(a,root);
	}
	
	//note:for compare to <=0 means that it is smaller or equal
	
	private DateNode add(Date a , DateNode current) {  // this is called a utility method.
		if(current==null) {  // I found an empty place to put the node in
			current =new DateNode(a);
			number_of_dates++;
		}
		else if((a).compareTo(current.getDate() ) <=0 ) // if the value I want to add is less than or equal the current node then that node would be in the left side
			current.setLeft(add(a,current.getLeft()));
		else if((a).compareTo(current.getDate() ) > 0 )// if the value I want to add is greater than the current node then that node would be in the right side
			current.setRight(add(a,current.getRight()));
		
		return current;
	}
	
//..............................................................................................................	

	public void remove(Date a) {
		root= remove(a,root);
	}
	
	private DateNode remove(Date a, DateNode current) {
		if(current==null) return null; // this means that the BST is empty ,, value does not exist in the system
		if((a).compareTo(current.getDate() ) >0 )
			current.setRight(remove(a,current.getRight()));
		else if((a).compareTo(current.getDate() ) <0 )
			current.setLeft(remove(a,current.getLeft()));
		else {  //the value Iam serching for to delete exists in the system
			if(current.getLeft()!=null && current.getRight()!=null) { //the node we want to delete has left and right
				current.setDate(findMin(current.getRight()).getDate());
				current.setRight(remove(a,current.getRight()));
			}
			else {  //the node we want to delete has zero or one child 
				if(current.getLeft()!=null) {
					current=current.getLeft();
					number_of_dates--;
				}
				else {
					current=current.getRight();
					number_of_dates--;
				}
				
			}
		}
		
		return current;
	}
	
//...............................................................................................................
	

	
	public Date kthLargestElement(int k) {
		counter=0;
		result=new Date();
			try {
				kthLargestElement(root,k);
			}catch(NullPointerException ex) {
				System.out.println("empty tree");
			}
		return result;
	}
	
	private void kthLargestElement(DateNode current, int k) {
		if(current==null) return;
		else {
			kthLargestElement(current.getRight(),k);
			 counter++;			
			if(counter==k) {
				result = current.getDate(); 
				return; 
			}
			kthLargestElement(current.getLeft(),k);
		}
	}
	
//.................................................................................................................
	
	private Date result=new Date();
	private int counter=0;
	
	public Date kthSmallestElement(int k) {
		counter=0;
		result=new Date();
			try {
				kthSmallestElement(root,k);
			}catch(NullPointerException ex) {
				System.out.println("empty tree");
			}
		return result;
	}
	
	private void kthSmallestElement(DateNode current, int k) {
		if(current==null) return;
		else {
			 kthSmallestElement(current.getLeft(),k);
			 counter++;			
			if(counter==k) {
				result = current.getDate(); 
				return; 
			}
			 kthSmallestElement(current.getRight(),k);
		}
	}
	
	
//...............................................................................................................
	
	public DateNode findMax() {
		if(root==null) return null;
		return findMax(root);
	}
	
	private DateNode findMax(DateNode current) {
		if(current.getRight()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMax(current.getRight());  
		}
		
	}
	
//.................................................................................................................
	
	
	
//..............................................................................................................
	
	public DateNode findMin() {
		if(root==null) return null;
		return findMin(root);
	}
	
	private DateNode findMin(DateNode current) {
		if(current.getLeft()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMin(current.getLeft());  
		}
		
	}
	
//..............................................................................................................
	
	public Date findMaxValue() {
		if(root==null) return null;
		return findMaxValue(root).getDate();
	}
	
	private DateNode findMaxValue(DateNode current) {
		if(current.getLeft()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMaxValue(current.getLeft());  
		}
		
	}

//..............................................................................................................	
	
	public boolean contains(Date a) {
		if(root==null) {
			return false;
		}
		return contains(a,root);
	}
	
	private boolean contains(Date a , DateNode current) {
		if(current==null) return false;
		if((a).compareTo(current.getDate() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return contains(a,current.getLeft());
		}
		else if((a).compareTo(current.getDate() ) >0 )
			return contains(a,current.getRight());
		
		return true;
	}
	
//.............................................................................................................
	
	public DateNode find(Date a) {
		return find(a,root);
	}
	
	private DateNode find(Date a , DateNode current) {
		if(current.getDate().equals(a))  //if the current's value equals the passed value by the method's parameter I will return the node of that value
			return current;
		else if((a).compareTo(current.getDate() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return find(a,current.getLeft());
		}
		else if((a).compareTo(current.getDate() ) >0 )
			return find(a,current.getRight());
		
		return null;
	}
	
//..............................................................................................................
	
	public void printInorder() {
		printInorder(root);
		System.out.println();
	}
	
	private void printInorder(DateNode node)
    {
        if (node == null)  //this is the base case....
            return;
 
        
        printInorder(node.getLeft());
        System.out.print(node.getDate()+ " ,, ");
        node.getMartyrs().print();
        
        printInorder(node.getRight());
    }

//..................................................................................................................	
	int count=0;
	public int getNumberOfMartyrsInsideDate() {
		count=0;
		getNumberOfMartyrsInsideDate( root);
		return count;
	}
	private void getNumberOfMartyrsInsideDate(DateNode current) {
		if(current==null) return ;
		
		getNumberOfMartyrsInsideDate(current.getLeft());
		count=count+current.getMartyrs().getSize();
		getNumberOfMartyrsInsideDate(current.getRight());
	}
	
//...................................................................................................................	
	public DateNode getRoot() {
		return root;
	}

	public void setRoot(DateNode root) {
		this.root = root;
	}
	
}