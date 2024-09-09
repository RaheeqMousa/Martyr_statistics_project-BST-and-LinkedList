public class LBSTNode {
    private  String location_name;
    private  LBSTNode left;
    private LBSTNode right;
    private DateBST dates;
    
    public LBSTNode() {
    	
    }
    
    public LBSTNode(String n) {
        this.location_name = n;
        this.left = null;
        this.right = null;
        dates=new DateBST();
    }
    
    public LBSTNode(String n,LBSTNode l,LBSTNode r) {
        this.location_name = n;
        this.left = l;
        this.right = r;
        dates=new DateBST();
    }

	public String getLocationName() {
		return this.location_name;
	}

	public void setLocationName(String d) {
		this.location_name = d;
	}

	public LBSTNode getLeft() {
		return left;
	}

	public void setLeft(LBSTNode left) {
		this.left = left;
	}

	public LBSTNode getRight() {
		return right;
	}

	public void setRight(LBSTNode right) {
		this.right = right;
	}
	
	public DateBST getDateBST() {
		return dates;
	}

	public void setDateBST(DateBST d) {
		this.dates = d;
	}

//...............................................................................
	public boolean locationContainsMartyr(String str) {
	
		return dateContainsMartyr(this.getDateBST().getRoot(),str);
		
	}
	
	public boolean dateContainsMartyr(DateNode l,String str) {
		if(l==null) return false;
		
		boolean a= dateContainsMartyr(l.getLeft(),str);
		boolean b= dateContainsMartyr(l.getRight(),str);
		return l.getMartyrs().contains(str) || a|| b;	
		
	}
	
//................................................................................
	
	//this to check if the martyr data "name,age,location,district,gender,date" iexists in the current location or not
	public boolean locationContainsMartyrData(Martyr r) {  
		  
		return dateContainsMartyrData(this.getDateBST().getRoot(),r);
		
	}
	
	public boolean dateContainsMartyrData(DateNode l,Martyr r) {
		if(l==null) return false;
		
		boolean a= dateContainsMartyrData(l.getLeft(),r);
		boolean b= dateContainsMartyrData(l.getRight(),r);
		return l.getMartyrs().contains(r) || a|| b;	
		
	}
	
//................................................................................
	
	public void setAllMartyrWithNewLocationName(String new_location_name) {
		traverseDates(new_location_name,this.getDateBST().getRoot());
	}
	
	private void traverseDates(String new_location_name,DateNode n ) {
		if(n==null) return;
		traverseDates(new_location_name,n.getLeft());
		traverseMartyrs(new_location_name,n.getMartyrs().getHead());
		traverseDates(new_location_name,n.getRight());
	}
	private void traverseMartyrs(String new_location_name,Node n ) {
		if(n==null) return;
		
		Node move=n;
		while(move!=null) {
			((Martyr)move.getData()).setLocation(new_location_name);
			move=move.getNext();
		}
	}
	
//................................................................................
	
	public void deleteMartyrInsideLocation(String martyr_name) {  
		//this method is to delete martyr in the location depending on his/her name
		traverseDatesToDeleteMartyr(martyr_name,this.getDateBST().getRoot());
	}
	
	private void traverseDatesToDeleteMartyr(String martyr_name,DateNode n ) {
		if(n==null) return;
		traverseDatesToDeleteMartyr(martyr_name,n.getLeft());
		if(n.getMartyrs().contains(martyr_name))
			n.getMartyrs().deleteMartyrByName(martyr_name);
		traverseDatesToDeleteMartyr(martyr_name,n.getRight());
	}
	
//................................................................................
	
	public void deleteMartyrInsideLocation(Martyr r) {
		traverseDatesToDeleteMartyr(r,this.getDateBST().getRoot());
	}
	
	private void traverseDatesToDeleteMartyr(Martyr r,DateNode n ) {
		if(n==null) return;
		traverseDatesToDeleteMartyr(r,n.getLeft());
		if(n.getMartyrs().contains(r)) {
		boolean del=	n.getMartyrs().deleteMartyrByWholeData(r);	//delete martyr depending on her/his all data
		if(del==true)	System.out.println("martyr deleted inside location\n\n");
		else if(del==false) System.out.println("martyr had not deleted inside location\n\n");
			}
		traverseDatesToDeleteMartyr(r,n.getRight());
	}
	
//................................................................................	

	Martyr searched_for_martyr=null;
	public Martyr searchMartyrInsideLocation(String name) {
		searched_for_martyr=null;
		searchMartyrInsideDate(this.getDateBST().getRoot(),name);
		if(searched_for_martyr!=null)
		System.out.println("martryr found inside location");
		return	searched_for_martyr;
	}	
	
	
	private void searchMartyrInsideDate(DateNode d,String name) {
		if(d==null) return;
		searchMartyrInsideDate(d.getLeft(),name);
		
		if(d.getMartyrs().contains(name));
		searched_for_martyr=(Martyr)d.getMartyrs().searchByFullName(name);
		
		searchMartyrInsideDate(d.getRight(),name);
	}
//...............................................................................	
	
	@Override
	public String toString() {
		return "Node [location_name=" + location_name + ", left=" + left + ", right=" + right + "]";
	}
    
    
}