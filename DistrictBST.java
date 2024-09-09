import java.util.Date;

public class DistrictBST {

	private DBSTNode root;
	private int number_of_districts;
		
	public DistrictBST() {
		this.number_of_districts=0;
		this.root=null;
	}
	
//....................................................................................................................
//	int count=0;
//	public int getNumberOfMartyrsInsideDistrict() {
//		count=0;
//		getNumberOfMartyrsInsideDistrict(root);
//		return count;
//	}
//	private void getNumberOfMartyrsInsideDistrict(DBSTNode current) {
//		if(current==null) return ;
//		
//		getNumberOfMartyrsInsideDistrict(current.getLeft());
//		count=count+current.getLocations().getNumberOfMartyrsInsideLocation();
//		getNumberOfMartyrsInsideDistrict(current.getRight());
//	}
	
//...................................................................................................................
	
	public void add(String a) {
		root=add(a,root);
	}
	
	//note: <=0 means that it is smaller or equal
	
	private DBSTNode add(String a , DBSTNode current) {  // this is called a utility method.
		if(current==null) {  // I found an empty place to put the node in
			current =new DBSTNode(a);
			number_of_districts++;
		}
		else {
			if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) <=0 ) // if the value I want to add is less than or equal the current node then that node would be in the left side
				current.setLeft(add(a,current.getLeft()));
			else if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) > 0 )// if the value I want to add is greater than the current node then that node would be in the right side
				current.setRight(add(a,current.getRight()));
		}
		return current;
	}
	
//..............................................................................................................
	
	public void add(DBSTNode a) {
		if(a==null) return;
		root=add(a,root);
	}
	
	//note: <=0 means that it is smaller or equal
	
	private DBSTNode add(DBSTNode a , DBSTNode current) {  // this is called a utility method.
		if(current==null)  // I found an empty place to put the node in
			current =a;
		else {
			if((a.getDistrictName().toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) <=0 ) // if the value I want to add is less than or equal the current node then that node would be in the left side
				current.setLeft(add(a,current.getLeft()));
			else if((a.getDistrictName().toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) > 0 )// if the value I want to add is greater than the current node then that node would be in the right side
				current.setRight(add(a,current.getRight()));
		}
		return current;
	}
	
//..............................................................................................................	

	public void remove(String a) {
		root= remove(a,root);
	}
	
	private DBSTNode remove(String a, DBSTNode current) {
		if(current==null) return null; // this means that the BST is empty ,, value does not exist in the system
		if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) > 0 )
			current.setRight(remove(a,current.getRight()));
		else if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) < 0)
			current.setLeft(remove(a,current.getLeft()));
		else {  //the value Iam serching for to delete exists in the system
			if(current.getLeft()!=null && current.getRight()!=null) { //the node we want to delete has left and right
				current.setDistrictName(findMin(current.getRight()).getDistrictName());
				current.setRight(remove(a,current.getRight()));				
			}
			else {  //the node we want to delete has zero or one child 
				if(current.getLeft()!=null) {
					current=current.getLeft();
					number_of_districts--;
				}
				else {
					current=current.getRight();
					number_of_districts--;
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
	
	private void kthLargestElement(DBSTNode current, int k) {
		if(current==null) return;
		else {
			kthLargestElement(current.getRight(),k);
			 counter++;			
			if(counter==k) {
				result = current.getDistrictName(); 
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
	
	private void kthSmallestElement(DBSTNode current, int k) {
		if(current==null) return;
		else {
			 kthSmallestElement(current.getLeft(),k);
			 counter++;			
			if(counter==k) {
				result = current.getDistrictName(); 
				return; 
			}
			 kthSmallestElement(current.getRight(),k);
		}
	}
	
	
//...............................................................................................................
	
	public DBSTNode findMax() {
		return findMax(root);
	}
	
	private DBSTNode findMax(DBSTNode current) {
		if(current.getRight()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMax(current.getRight());  
		}
		
	}
	
//..............................................................................................................
	
	public DBSTNode findMin() {
		return findMin(root);
	}
	
	private DBSTNode findMin(DBSTNode current) {
		if(current.getLeft()==null) // if there is no right child then the current node itself is the maximum element in the BST
			return current;
		else {
			return findMin(current.getLeft());  
		}
		
	}
	
//..............................................................................................................
	
	public String findMaxValue() {
		return findMaxValue(root).getDistrictName();
	}
	
	private DBSTNode findMaxValue(DBSTNode current) {
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
	
	private boolean contains(String a , DBSTNode current) {
		if(current==null) return false;
		
		
		if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return contains(a,current.getLeft());
		}
		else if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) >0 )
			return contains(a,current.getRight());
		
		return true;
	}
	
//.............................................................................................................
	
	public DBSTNode find(String a) {
		return find(a,root);
	}
	
	private DBSTNode find(String a , DBSTNode current) {
		if(current==null) return null;
		else if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) <0 ) {   //if the passed value by the method's parameter is less than the current's value 
			return find(a,current.getLeft());
		}
		else if((a.toLowerCase()).compareTo(current.getDistrictName().toLowerCase() ) >0 )
			return find(a,current.getRight());
		else
		return current;
	}
	
//..............................................................................................................
	
	public DBSTNode DistrictWhichContainsLocation(String a) { //IN THE WHOLE SYSTEM
		return DistrictWhichContainsLocation(root,a);
	}
	
	public LBSTNode searchLocationInWholeSystem(String a) { //IN THE WHOLE SYSTEM
		DBSTNode n=DistrictWhichContainsLocation(root,a);
		if(n!=null)
			return n.getLocations().find(a);
		else    //location does not exist in the whole system
			return null;
	}
	
	public LBSTNode searchLocationInDistrict(String dis,String a) { //IN THE WHOLE SYSTEM
		DBSTNode n =DistrictWhichContainsLocation(root,a) ;
		if(n!=null)   //if the district node is null that means that the district does not exist
			return n.getLocations().find(a);
		return null;
	}
	
	public DBSTNode DistrictWhichContainsLocation( DBSTNode current,String a) {
		if(current==null) {System.out.println("got");return null;}
		
		else if(current.getLocations().contains(a)) {
			return current;
		}
		else {

	        DBSTNode leftResult = DistrictWhichContainsLocation(current.getLeft(),a);
	        if (leftResult != null) {
	            return leftResult; 
	        }
	        System.out.println(current);
	        return DistrictWhichContainsLocation(current.getRight(),a);
	    }
		
	}
	
//..............................................................................................................
	
	public void deleteMartyrInWholeSystem(String name) {
		deleteMartyrInWholeSystem(root,name);
	}
	
	private void deleteMartyrInWholeSystem(DBSTNode n,String name) {
		if(n==null) return;
		deleteMartyrInWholeSystem(n.getLeft(),name);
		n.deleteMartyrInsideDistrict(name);
		deleteMartyrInWholeSystem(n.getRight(),name);
	}
	
//.............................................................................................................
	
	public boolean isMartyrContainsInWholeSystem(String name) {
		return isMartyrContainsInWholeSystem(root,name);
	}
	private boolean isMartyrContainsInWholeSystem(DBSTNode n,String name) {
		if(n==null) return false;
		
		boolean a=isMartyrContainsInWholeSystem(n.getLeft(),name);
		boolean b=isMartyrContainsInWholeSystem(n.getRight(),name);
		
		return n.districtContainsMartyr(name)|| a|| b;
	}
//.............................................................................................................
	
	 DBSTNode searching_for_district=null;
	public DBSTNode getDistrictWhichContainsMartyr(String name) {
		searching_for_district=null;		
		getDistrictWhichContainsMartyr(root,name);
		return searching_for_district;
	}
	private void getDistrictWhichContainsMartyr(DBSTNode n,String name) {
		if(n==null) return;
		getDistrictWhichContainsMartyr(n.getLeft(),name);
		if(n.districtContainsMartyr(name)) {
			searching_for_district=n;
			return;
			}
		getDistrictWhichContainsMartyr(n.getRight(),name);
	}
	
//.............................................................................................................
	
	DateNode searching_for_date=null;
	public DateNode getDateInWholSystem(Date d) {
		searching_for_date=null;		
		getDateInWholSystem(root,d);
		return searching_for_date;
	}
	public void getDateInWholSystem(DBSTNode n,Date d) {
		if(n==null) return;
		getDateInWholSystem(n.getLeft(),d);
		getDateInWholeLocations(n.getLocations().getRoot(),d);
		getDateInWholSystem(n.getRight(),d);
	}
	public void getDateInWholeLocations(LBSTNode n,Date d) {
		if(n==null) return;
		getDateInWholeLocations(n.getLeft(),d);
		
		if(n.getDateBST().contains(d))
		searching_for_date=n.getDateBST().find(d);
		
		getDateInWholeLocations(n.getRight(),d);
	}

	
	
//..............................................................................................................
	
	public void printInorder() {
		printInorder(root);
		System.out.println();
	}
	
	private void printInorder(DBSTNode node)
    {
        if (node == null)  //this is the base case....
            return;
 
        printInorder(node.getLeft());
        System.out.print(node.getDistrictName() + " ");
        node.getLocations().printInorder();
        
        printInorder(node.getRight());
    }

//..................................................................................................................	
	
	public DBSTNode getRoot() {
		return root;
	}

	public void setRoot(DBSTNode root) {
		this.root = root;
	}
	
}